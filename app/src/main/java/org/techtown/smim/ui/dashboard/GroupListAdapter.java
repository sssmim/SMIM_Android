package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.techtown.smim.R;

import java.util.ArrayList;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContentProviderCompat.requireContext;

public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.ViewHolder> {
    public static final int number = 1099;
    ArrayList<GroupList> items = new ArrayList<GroupList>();
    static OnPersonItemClickListener listener;

    public interface OnPersonItemClickListener {
        public void onItemClick(GroupListAdapter.ViewHolder holder, View view, int position); }

    public void setOnItemClicklistener(OnPersonItemClickListener listener){ this.listener = listener; }


    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position); } }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.group_list, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        GroupList item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(GroupList item) {
        items.add(item);
    }

    public void clearItem() { items.clear(); }

    public void setItems(ArrayList<GroupList> items) {
        this.items = items;
    }

    public GroupList getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, GroupList item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView textView;
        TextView textView2;


        public ViewHolder(View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.imageView1);
            textView = itemView.findViewById(R.id.grouptitle);
            textView2 = itemView.findViewById(R.id.groupdesc);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this, v, position);
                    } } });

        }

        public void setItem(GroupList item) {
            int red = (int)(Math.random()*255);
            int green = (int)(Math.random()*255);
            int blue = (int)(Math.random()*255);
            iv.setColorFilter(Color.rgb(red, green, blue));
            textView.setText(item.getGroupTitle());
            textView2.setText(item.getGroupDesc());
        }
    }

}
