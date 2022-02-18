package org.techtown.smim.ui.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.smim.R;

import java.util.ArrayList;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {
    ArrayList<ActivityList> items = new ArrayList<ActivityList>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.main_list_detail, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ActivityList item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ActivityList item) {
        items.add(item);
    }
    public void clearItem(){items.clear();}
    public void setItems(ArrayList<ActivityList> items) {
        this.items = items;
    }

    public ActivityList getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, ActivityList item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView hs;
        TextView he;
        TextView hd;

        public ViewHolder(View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.imageView);
            hs= itemView.findViewById(R.id.hs);
            he = itemView.findViewById(R.id.he);
            hd = itemView.findViewById(R.id.hd);
        }

        public void setItem(ActivityList item) {
            int red = (int)(Math.random()*255);
            int green = (int)(Math.random()*255);
            int blue = (int)(Math.random()*255);
            iv.setColorFilter(Color.rgb(red, green, blue));
            hd.setText(item.getName());
            hs.setText(item.getStime());
            he.setText(item.getEtime());
        }
    }

}
