package bysj.model;

import java.util.HashMap;

/**
 * 省份分数线信息
 */
public class ShengFenInfo {

    private String shengfen; //省份
    private HashMap<Integer, Float> grade_info; //各个年份的分数信息<年，分数线>
    private String bei_zhu; //备注信息

    public ShengFenInfo() {
    }

    public ShengFenInfo(String shengfen, HashMap<Integer, Float> grade_info, String bei_zhu) {
        this.shengfen = shengfen;
        this.grade_info = grade_info;
        this.bei_zhu = bei_zhu;
    }

    public String getShengfen() {
        return shengfen;
    }

    public void setShengfen(String shengfen) {
        this.shengfen = shengfen;
    }

    public HashMap<Integer, Float> getGrade_info() {
        return grade_info;
    }

    public void setGrade_info(HashMap<Integer, Float> grade_info) {
        this.grade_info = grade_info;
    }

    public String getBei_zhu() {
        return bei_zhu;
    }

    public void setBei_zhu(String bei_zhu) {
        this.bei_zhu = bei_zhu;
    }

    @Override
    public String toString() {
        return "ShengFenInfo{" +
                "shengfen='" + shengfen + '\'' +
                ", grade_info=" + grade_info +
                ", bei_zhu='" + bei_zhu + '\'' +
                '}';
    }
}
