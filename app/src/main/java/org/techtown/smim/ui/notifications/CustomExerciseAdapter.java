package org.techtown.smim.ui.notifications;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.graphics.Color;
import android.util.Log;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.ChipGroup;

import org.techtown.smim.R;
import org.techtown.smim.ui.dashboard.GroupListAdapter;

import java.util.ArrayList;

public class CustomExerciseAdapter extends RecyclerView.Adapter<CustomExerciseAdapter.ViewHolder> implements OnExerciseClickListener {
    static ArrayList<CustomExercise> itemList = new ArrayList<CustomExercise>();
    private static final String TAG = "MainCustomExerciseAdapter";

    OnExerciseClickListener listener;
    static OnPersonItemClickListener listener2;

    public interface OnPersonItemClickListener {
        public void onItemClick(CustomExerciseAdapter.ViewHolder holder, View view, int position); }

    public void setOnItemClicklistener(OnPersonItemClickListener listener2){ this.listener2 = listener2; }

    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.custom_exercise_list, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        // Log.d(TAG, "onBindViewHolder: position ▶ " + position);
        CustomExercise item = itemList.get(position);
        holder.setItem(item);


    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void clearItem() {
        itemList.clear();
    }

    public void addItem(CustomExercise item) {
        itemList.add(item);
    }

    public void setItems(ArrayList<CustomExercise> items) {
        this.itemList = items;
    }

    public CustomExercise getItem(int position) { return itemList.get(position); }

    public void setItem(int position, CustomExercise item) {
        itemList.set(position, item);
    }

    public void setOnItemClickListener(OnExerciseClickListener listener) {
        this.listener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nametextview;
        TextView partextview;
        CheckBox icheckbox;
        static ImageView imageView1;
        CustomExercise x=null;
        public ViewHolder(View itemView) {
            super(itemView);

            nametextview = itemView.findViewById(R.id.nametextview);
            partextview = itemView.findViewById(R.id.partextview);
            imageView1 = itemView.findViewById(R.id.imageView1);
            icheckbox = itemView.findViewById(R.id.icheckbox);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener2 != null) {
                        listener2.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });



           // int position = getAdapterPosition();
            //icheckbox.setChecked();
             ArrayList<Integer> m=new ArrayList<>();
            icheckbox.setOnCheckedChangeListener(null);
            icheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //set your object's last status
                    int position = getAdapterPosition();
                    icheckbox.setSelected(isChecked);
                    if(icheckbox.isSelected()==true){
                        NotificationsFragment_test.ischeck(position);
                    }else{
                        NotificationsFragment_test.uncheck(position);
                    }
                                                           }});


        }

        //setItem 부분
        public void setItem(CustomExercise item) {
            nametextview.setText(item.igetName());
            partextview.setText(item.igetPart());
            imageView1.setImageResource(item.igetImageRes());
            icheckbox.setChecked(item.isSelected());
        }

    }
}