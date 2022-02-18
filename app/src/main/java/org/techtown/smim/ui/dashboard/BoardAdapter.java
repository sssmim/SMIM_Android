package org.techtown.smim.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.smim.R;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    static ArrayList<Board> items = new ArrayList<Board>();
    static BoardAdapter.OnPersonItemClickListener listener;

    public interface OnPersonItemClickListener {
        public void onItemClick(BoardAdapter.ViewHolder holder, View view, ArrayList<Board> items, int position); }

    public void setOnItemClicklistener(BoardAdapter.OnPersonItemClickListener listener){ this.listener = listener; }


    public void onItemClick(BoardAdapter.ViewHolder holder, View view, ArrayList<Board> items, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,items,position); } }

/*
    static BoardAdapter.OnItemsClickListener listener3;

    public interface OnItemsClickListener {
        public void onItemsClick( int position); }

    public void setOnItemsClicklistener(BoardAdapter.OnItemsClickListener listener3){ this.listener3 = listener3; }


    public void onItemsClick(int position) {
        if(listener3 != null){
            listener3.onItemsClick(position); } }


*/


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.boardlist_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Board item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearItem() {
        items.clear();
    }

    public void addItem(Board item) {
        items.add(item);
    }

    public void setItems(ArrayList<Board> items) {
        this.items = items;
    }

    public Board getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Board item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView1;
        TextView textView2;

        public ViewHolder(View itemView) {
            super(itemView);
            textView2 = itemView.findViewById(R.id.boardtitle);
            textView1 = itemView.findViewById(R.id.boardcomment);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(BoardAdapter.ViewHolder.this, v, items, position);
                    } } });
        }

        public void setItem(Board item) {
            textView2.setText(item.getTitle());
            textView1.setText(item.getComment());

        }

    }
}