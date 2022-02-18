package org.techtown.smim.ui.notifications;

public class CustomExercise {
    String iname;
    String ipart;
    int iimageRes;
    boolean isSelected;

    public CustomExercise(String iname, String ipart, int iimageRes,boolean isSelected) {
        this.iname = iname;
        this.ipart = ipart;
        this.iimageRes = iimageRes;
        this.isSelected = isSelected;
    }

    public String igetName() {
        return iname;
    }

    public void isetName(String iname) {
        this.iname = iname;
    }

    public String igetPart() {
        return ipart;
    }

    public void isetPart(String ipart) {
        this.ipart = ipart;
    }

    public int igetImageRes() {
        return iimageRes;
    }

    public void isetImageRes(int iimageRes) {
        this.iimageRes = iimageRes;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


}
