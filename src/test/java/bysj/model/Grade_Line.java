package bysj.model;

/**
 * 分数线模型
 */
public class Grade_Line {

    private String sheng_fen;//省份
    private String sf_ping_yin;//省份拼音
    private String years; //年份，使用逗号","隔开
    private String yi_ben; //一本线，使用逗号","隔开
    private String er_ben;  //二本线，使用逗号","隔开

    public Grade_Line() {
    }

    public Grade_Line(String sheng_fen, String sf_ping_yin, String years, String yi_ben, String er_ben) {
        this.sheng_fen = sheng_fen;
        this.sf_ping_yin = sf_ping_yin;
        this.years = years;
        this.yi_ben = yi_ben;
        this.er_ben = er_ben;
    }

    public String getSheng_fen() {
        return sheng_fen;
    }

    public void setSheng_fen(String sheng_fen) {
        this.sheng_fen = sheng_fen;
    }

    public String getSf_ping_yin() {
        return sf_ping_yin;
    }

    public void setSf_ping_yin(String sf_ping_yin) {
        this.sf_ping_yin = sf_ping_yin;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getYi_ben() {
        return yi_ben;
    }

    public void setYi_ben(String yi_ben) {
        this.yi_ben = yi_ben;
    }

    public String getEr_ben() {
        return er_ben;
    }

    public void setEr_ben(String er_ben) {
        this.er_ben = er_ben;
    }

    @Override
    public String
    toString() {
        return "Grade_Line{" +
                "sheng_fen='" + sheng_fen + '\'' +
                ", sf_ping_yin='" + sf_ping_yin + '\'' +
                ", years='" + years + '\'' +
                ", yi_ben='" + yi_ben + '\'' +
                ", er_ben='" + er_ben + '\'' +
                '}';
    }
}
