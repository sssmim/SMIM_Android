package org.techtown.smim.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.smim.R;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    static ArrayList<Comment> items = new ArrayList<Comment>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.commnet_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Comment item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearItem() {
        items.clear();
    }

    public void addItem(Comment item) {
        items.add(item);
    }

    public void setItems(ArrayList<Comment> items) {
        this.items = items;
    }

    public Comment getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Comment item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {


        TextView textView2;

        public ViewHolder(View itemView) {
            super(itemView);
            textView2 = itemView.findViewById(R.id.ccomment);


/*
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(BoardAdapter.ViewHolder.this, v, items, position);
                    } } });*/
        }

        public void setItem(Comment item) {
            textView2.setText(item.getSub());


        }

    }
}