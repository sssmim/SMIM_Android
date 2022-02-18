package org.techtown.smim.ui.home;

public class ActivityList {
    String name;
    String stime;
    String etime;

    public ActivityList(String name, String stime,String etime) {
        this.name = name;
        this.stime = stime;
        this.etime = etime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEtime() {
        return etime;
    }

    public void setEtime(String etime) {
        this.etime = etime;
    }
}
