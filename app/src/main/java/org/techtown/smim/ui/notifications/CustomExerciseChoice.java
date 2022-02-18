package org.techtown.smim.ui.notifications;

public class CustomExerciseChoice {
    int cimage;
    String cname;
    String cpart;
    int count;

    public CustomExerciseChoice(int cimage, String cname, String cpart,int count){
        this.cimage = cimage;
        this.cname = cname;
        this.cpart = cpart;
        this.count = count;
    }

    public int cgetImage() { return cimage; }

    public void csetImage(int image) { this.cimage = cimage; }

    public String cgetName() { return cname; }

    public void csetName(String cname) { this.cname = cname; }

    public String cgetPart() { return cpart; }

    public void csetPart(String cpart) { this.cpart = cpart; }


    public int cgetCount() { return count; }

    public void csetCount(int count) { this.count = count; }
}
