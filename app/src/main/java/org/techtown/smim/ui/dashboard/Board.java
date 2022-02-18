package org.techtown.smim.ui.dashboard;

public class Board {
    String title;
    String comment;

    public Board(String title, String comment){
        this.title=title;
        this.comment = comment;
    }

    public String getTitle() {
        return title;
    }

    public void  setTitle(String title) {
        this.title = title;
    }


    public String getComment() {
        return comment;
    }
    public void  setComment(String comment) {
        this.comment = comment;
    }

}
