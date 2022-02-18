package org.techtown.smim.ui.notifications;

public class ListData {
    private String tv_name;     //title
    private String tv_cases;    //img
    private String tv_cases_p;  //link
    private String tv_deaths;   //tag
    private String tv_recovered;//writer

    public ListData(String tv_name, String tv_cases, String tv_cases_p, String tv_deaths, String tv_recovered) {
        this.tv_name = tv_name;
        this.tv_cases = tv_cases;
        this.tv_cases_p = tv_cases_p;
        this.tv_deaths = tv_deaths;
        this.tv_recovered = tv_recovered;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_cases() {
        return tv_cases;
    }

    public void setTv_cases(String tv_cases) {
        this.tv_cases = tv_cases;
    }

    public String getTv_cases_p() {
        return tv_cases_p;
    }

    public void setTv_cases_p(String tv_cases_p) {
        this.tv_cases_p = tv_cases_p;
    }

    public String getTv_deaths() {
        return tv_deaths;
    }

    public void setTv_deaths(String tv_deaths) {
        this.tv_deaths = tv_deaths;
    }

    public String getTv_recovered() {
        return tv_recovered;
    }

    public void setTv_recovered(String tv_recovered) {
        this.tv_recovered = tv_recovered;
    }
}
