package bysj.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsoupTest {
    public static void main(String[] args) throws IOException {
//        test1();
        getTheAreaFSXS(getAreasSpell());
    }

    private static void test1() {
        File input = new File("/home/zj/IdeaProjects/biyesheji/src/main/resources/test.html");
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
                String diqu_ping_yu = diqu
                        .attr("href")
                        .split("//")[1]
                        .split("/")[1];//获取拼音
                String linkText = diqu.text();//获取汉语
                result.put(diqu_ping_yu, linkText);
//                System.out.println("diqu_ping_yu: =>  " + diqu_ping_yu + "   linkTextt: " + linkText);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取各个省份的分数线
     * @param sheng_fens 传入一个hashmap，其中装的为<省份拼音，省份汉语>
     */
    private static void getTheAreaFSXS(HashMap<String, String> sheng_fens) {
        System.out.println("共有 " + sheng_fens.size() +" 个省！");
        sheng_fens
                .keySet()
                .forEach(sheng_fen -> {
                    try {
                        System.out.println("当前省份为： " + sheng_fens.get(sheng_fen));
                        Document doc = Jsoup.connect("http://www.gaokao.com/" + sheng_fen + "/fsx/")
                                .timeout(3000)
                                .get();
                        Element content = doc.body();// 获取body中的内容
                        //文科或者不分文理科
                        Elements wk_or_N_wl = content.getElementsByClass("blue ft14 txtC").next();
                        int size = wk_or_N_wl.size();
                        wk_or_N_wl.forEach(table ->{
//                            System.out.println(" html =>  " + table.html());
                            table.getElementsByTag("tr")
                                    .forEach(tr ->{
//                                        Elements ths = tr.getElementsByTag("th");
                                        tr.getElementsByTag("td").forEach(
                                                td ->{
                                                    if(isContainChinese(td.text())){
                                                        System.out.println("\n"+String.format("%-6s", td.text()));
                                                    }else {
                                                        System.out.print(String.format("%-6s", td.text()));
                                                       }

                                                }
                                        );
//                                        System.out.println("");
                                    });
                        });
                        System.out.println();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    /**
     * 判断字符串中是否包含中文
     * @param str
     * 待校验字符串
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
/**
 * 2018	2017	2016	2015	2014	2013	2012	2011	2010	2009
 * 一本	546	    518   	518  	513 	526 	507 	539 	543 	533 	548
 * 二本	476	    452	    460  	462 	478 	459 	492 	496 	492 	507
 */