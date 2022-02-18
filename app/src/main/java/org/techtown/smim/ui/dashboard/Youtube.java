package org.techtown.smim.ui.dashboard;

public class Youtube {
    String title;
    int image;


    public Youtube(String title, int image){
        this.title = title;
        this.image = image;
    }

    public int getImage() {
           return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void  setTitle(String title) {
        this.title =title;
    }

    public int getImageRes() {
        return image;
    }

    public void setImageRes(int image) {
        this.image = image;
    }
}
