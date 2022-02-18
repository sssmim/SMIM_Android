package org.techtown.smim.ui.MyPage;

public class ItemList {
    int iimage;
    String grouptitle;
    String groupdesc;
    String prev;
    String now;

    public ItemList(int iimage,String grouptitle, String groupdesc, String prev, String now) {
        this.iimage = iimage;
        this.grouptitle = grouptitle;
        this.groupdesc = groupdesc;
        this.prev = prev;
        this.now = now;

    }

    public String getGroupTitle() { return grouptitle; }

    public void setGroupTitle(String grouptitle) {
        this.grouptitle = grouptitle;
    }

    public String getGroupDesc() { return groupdesc; }

    public void setGroupDesc(String groupdesc) {
        this.groupdesc = groupdesc;
    }

    public String getPrev() { return prev; }

    public void setPrev(String prev) {
        this.prev = prev;
    }

    public String getNow() { return now; }

    public void setNow(String now) {
        this.now = now;
    }

    public int getIimage() { return iimage; }

    public void setIimage(int iimage) {
        this.iimage = iimage;
    }

}