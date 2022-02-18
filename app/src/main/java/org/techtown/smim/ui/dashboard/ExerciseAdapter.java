package org.techtown.smim.ui.dashboard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.smim.R;
import org.techtown.smim.ui.notifications.CustomExerciseMerge;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {
    static ArrayList<Exercise> items = new ArrayList<Exercise>();
    static ExerciseAdapter.OnPersonItemClickListener listener;

    public interface OnPersonItemClickListener {
        public void onItemClick(ExerciseAdapter.ViewHolder holder, View view,ArrayList<Exercise> items, int position); }

    public void setOnItemClicklistener(ExerciseAdapter.OnPersonItemClickListener listener){ this.listener = listener; }


    public void onItemClick(ExerciseAdapter.ViewHolder holder, View view,ArrayList<Exercise> items, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,items,position); } }


    static ExerciseAdapter.OnItemsClickListener listener3;

    public interface OnItemsClickListener {
        public void onItemsClick( int position); }

    public void setOnItemsClicklistener(ExerciseAdapter.OnItemsClickListener listener3){ this.listener3 = listener3; }


    public void onItemsClick(int position) {
        if(listener3 != null){
            listener3.onItemsClick(position); } }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.excercise_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Exercise item = items.get(position);
        viewHolder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void clearItem() {
        items.clear();
    }

    public void addItem(Exercise item) {
        items.add(item);
    }

    public void setItems(ArrayList<Exercise> items) {
        this.items = items;
    }

    public Exercise getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Exercise item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView1;
        TextView textView2;
        TextView textView3;
        Button grouplay;
        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            grouplay =itemView.findViewById(R.id.groupplay);

           grouplay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener3 != null){
                        listener3.onItemsClick( position);
                }}
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ExerciseAdapter.ViewHolder.this, v, items, position);
                    } } });
        }

        public void setItem(Exercise item) {
            textView.setText(item.getGe_start_time().toString());
            textView1.setText(item.getGe_end_time().toString());
            textView2.setText(item.getGe_desc());
            Date temp = item.getGe_date();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
            String to = transFormat.format(temp);
            textView3.setText(to);
        }

    }
}