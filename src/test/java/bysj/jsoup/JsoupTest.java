package bysj.jsoup;

import bysj.model.Grade_Line;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsoupTest {

    //当前使用省份数据集
    private final static String[] sheng_fens = {"hebei", "ningxia", "guizhou", "xinjiang", "beijing",
            "fujian", "heilongjiang", "guangxi", "qinghai", "jiangsu",
            "yunnan", "henan", "hunan", "sx", "anhui",
            "tianjin", "jiangxi", "hubei", "chongqing", "gansu",
            "sichuan", "neimenggu"};//sx(山西)
    //数据集不规则省份，暂时存放
    private final static String[] not_in_sheng_fens = {"hainan", "zhejiang", "xizang", "liaoning",
            "shanxi", "shandong", "jilin", "shanghai", "guangdong"};//shanxi（陕西）

    public static void main(String[] args) throws IOException {
//        test1();
        ArrayList<Grade_Line> results = getTheAreaFSXS(getAreasSpell());
        results.forEach(grade_line -> System.out.println(grade_line.toString()));
    }

    private static void test1() {
        File input =
                new File("/home/zj/IdeaProjects/biyesheji/src/main/resources/test.html");
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
            Element content = doc.getElementById("content");
            Elements links = content.getElementsByTag("a");
            for (Element link : links) {
                String linkHref = link.attr("href");
                String linkText = link.text();
                System.out.println("linkHref: " + linkHref + "   linkTextt: " + linkText);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取各个省份高考网站设置的拼音标识，用于后续抓取该地区的分数线，拼凑url
     *
     * @return 返回hashmap，各个省份的《拼音 ， 汉语》
     */
    private static HashMap<String, String> getAreasSpell() {
        HashMap<String, String> result = new HashMap<>();
        try {
            //获取各个省份的拼音标识，用于后续的分数线查找的拼凑url地址
            Document doc = Jsoup.connect("http://www.gaokao.com/guangdong/fsx/")
                    .timeout(3000)
                    .get();
            Element content = doc.body();// 获取body中的内容
            //获取页面中类名 classs 为 bp20的标签中的数据
            Elements diqus = content.getElementsByClass("bp20")
                    .get(0)   //选取第一列元素值即可
                    .children() //遍历其孩子属性
                    .get(1)   // 获取含有地址的那一项的值
                    .children();
            for (Element diqu : diqus) {
                //过滤掉非法数据段
                if (diqu.attr("href")
                        .split("//").length < 2) continue;
                //获取地区的拼音，用于后续的url查找对应地区的分数线
                String di_qu_ping_yin = diqu
                        .attr("href")
                        .split("//")[1]
                        .split("/")[1];//获取拼音
                String linkText = diqu.text();//获取汉语
                if (Arrays.asList(sheng_fens).contains(di_qu_ping_yin))
                    result.put(di_qu_ping_yin, linkText);
//                System.out.println("diqu_ping_yu: =>  " + diqu_ping_yu + "   linkTextt: " + linkText);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取各个省份的分数线
     *
     * @param sheng_fens 传入一个hashmap，其中装的为<省份拼音，省份汉语>
     */
    private static ArrayList<Grade_Line> getTheAreaFSXS(HashMap<String, String> sheng_fens) {


        System.out.println("共有 " + sheng_fens.size() + " 个省！");

        ArrayList<String> shengs = new ArrayList<>();

        ArrayList<Grade_Line> grade_lines = new ArrayList<>();//保存成绩属性数据

        sheng_fens
                .keySet()
                .forEach(sheng_fen -> {
                    try {
                        Grade_Line grade_line = new Grade_Line();
                        grade_line.setSheng_fen(sheng_fens.get(sheng_fen));
                        grade_line.setSf_ping_yin(sheng_fen);

//                        System.out.println("当前省份为： " + sheng_fens.get(sheng_fen));
                        shengs.add(sheng_fen);
                        String[] years = {""};
                        String[] yi_ben = {""};
                        String[] er_ben = {""};

                        Document doc = Jsoup.connect("http://www.gaokao.com/" + sheng_fen + "/fsx/")
                                .timeout(3000)
                                .get();
                        Element content = doc.body();// 获取body中的内容
                        //文科或者不分文理科
                        Elements wk_or_N_wl = content.getElementsByClass("blue ft14 txtC").next();

                        wk_or_N_wl.forEach(table -> {
//                            System.out.println(" html =>  " + table.html());

                            Elements trs = table.getElementsByTag("tr");

//                            System.out.println();
//                            //年份
//                            trs.forEach(trh -> {
//                                Elements ths = trh.getElementsByTag("th");//获取年份
//                                ths.forEach(th ->
//                                {
//                                    years[0] += th.text() + ",";
////                                    System.out.print(String.format("%-6s", th.text()));
//                                });
//                            });

                            //分数线
                            int i=0;
                            for(Element tr : trs){
                                if (i==0){
                                    years[0] += tr.text();i++;
                                }else if (i==1){
                                    yi_ben[0] += tr.text();i++;
                                }else if (i==2){
                                    er_ben[0] += tr.text();i++;
                                }
                            }
//                            trs.forEach(tr -> {
//                                final int[] indexs = {0};
//                                System.out.println("tr == "+ tr.text());
//
//                                tr.getElementsByTag("td").forEach(
//                                        td -> {
////                                            System.out.println("**** " + td.text());
////                                            if (isContainChinese(td.text()) && indexs[0] < 3) {
////                                                System.out.println(" index = "+ indexs[0] + String.format("%-6s", td.text()));
////                                                indexs[0] += 1;
////                                            } else if (indexs[0] < 2) {
////                                                if (indexs[0] == 1)
////                                                    yi_ben[0] += td.text() + ",";
////                                                if (indexs[0] == 2)
////                                                    er_ben[0] += td.text() + ",";
////                                                System.out.print(String.format("%-6s", td.text()));
////                                            }
//                                        }
//                                );
//                            });
                        });
                        grade_line.setYears(years[0]);
                        grade_line.setYi_ben(yi_ben[0]);
                        grade_line.setEr_ben(er_ben[0]);
                        grade_lines.add(grade_line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        return grade_lines;
    }

    /**
     * 判断字符串中是否包含中文
     *
     * @param str 待校验字符串
     * @return 是否为中文
     * @warn 不能校验是否为中文标点符号
     */
    private static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
}