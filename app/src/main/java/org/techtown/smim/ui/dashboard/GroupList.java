package org.techtown.smim.ui.dashboard;

public class GroupList {
    String grouptitle;
    String groupdesc;

    public GroupList(String grouptitle, String groupdesc) {
        this.grouptitle = grouptitle;
        this.groupdesc = groupdesc;
    }

    public String getGroupTitle() {
        return grouptitle;
    }

    public void setGroupTitle(String grouptitle) {
        this.grouptitle = grouptitle;
    }

    public String getGroupDesc() { return groupdesc; }

    public void setGroupDesc(String groupdesc) {
        this.groupdesc = groupdesc;
    }

}


