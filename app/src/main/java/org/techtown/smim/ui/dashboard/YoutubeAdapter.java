package org.techtown.smim.ui.dashboard;

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

public class YoutubeAdapter extends RecyclerView.Adapter<YoutubeAdapter.ViewHolder> {
    ArrayList<Youtube> items = new ArrayList<Youtube>();
     static YoutubeAdapter.OnYoutubeItemClickListener listener1;

    public interface OnYoutubeItemClickListener {
        public void onItemClick(YoutubeAdapter.ViewHolder holder, View view, int position); }

    public void setOnItemClicklistener(YoutubeAdapter.OnYoutubeItemClickListener listener){ this.listener1 = listener; }


    public void onItemClick(YoutubeAdapter.ViewHolder holder, View view, int position) {
        if(listener1 != null){
            listener1.onItemClick(holder,view,position); } }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.youtube_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Youtube item = items.get(position);
        viewHolder.setItem(item);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Youtube item) {
        items.add(item);
    }
public void clearItem() { items.clear();}
    public void setItems(ArrayList<Youtube> items) {
        this.items = items;
    }

    public Youtube getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Youtube item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        static ImageView imageView1;

        public ViewHolder(View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.youtubename);
            imageView1 = itemView.findViewById(R.id.youtubeimage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();


                    if(listener1 != null){
                        listener1.onItemClick(ViewHolder.this, v, position);
                    } } });
        }

        public void setItem(Youtube item) {
            textView1.setText(item.getTitle());
            imageView1.setImageResource(item.getImageRes());
        }

    }
}