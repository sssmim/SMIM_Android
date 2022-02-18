package org.techtown.smim.ui.dashboard;

import java.util.Date;

public class Exercise {
    Date ge_date;
    String ge_start_time;
    String ge_end_time;
    String ge_desc;

    public Exercise(Date ge_date, String ge_start_time, String ge_end_time, String ge_desc){
        this.ge_date = ge_date;
        this.ge_start_time = ge_start_time;
        this.ge_end_time = ge_end_time;
        this.ge_desc = ge_desc;
    }

    public Date getGe_date() {
        return ge_date;
    }

    public void  setGe_date(Date ge_date) {
        this.ge_date = ge_date;
    }

    public String getGe_start_time() {
        return ge_start_time;
    }

    public void  setGe_start_time(String ge_start_time) {
    this.ge_start_time = ge_start_time;
    }

    public String getGe_end_time() {
        return ge_end_time;
    }

    public void  setGe_end_time(String ge_end_time) {
        this.ge_end_time = ge_end_time;
    }

    public String getGe_desc() {
        return ge_desc;
    }

    public void  setGe_desc(String ge_desc) {
        this.ge_desc = ge_desc;
    }
}
