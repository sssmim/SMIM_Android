package org.techtown.smim.ui.dashboard;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.techtown.smim.R;

import java.util.ArrayList;

class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Comment> listItems = new ArrayList<Comment>();

    public ListViewAdapter(Context context){
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int i) {
        return listItems.get(i);
    }
    public void clearItem(){listItems.clear();}
    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // item.xml 레이아웃을 inflate해서 참조획득
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.commnet_item, parent, false);
        }


        TextView txt_sub = (TextView)convertView.findViewById(R.id.ccomment);

       Comment listItem = listItems.get(position);


        txt_sub.setText(listItem.getSub());


        return convertView;
    }
    public void addItem(Comment item) {
        listItems.add(item);
    }

}