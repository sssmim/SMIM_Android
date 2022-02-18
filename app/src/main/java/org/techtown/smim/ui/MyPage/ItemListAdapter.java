package org.techtown.smim.ui.MyPage;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.techtown.smim.R;
import org.techtown.smim.database.board;
import org.techtown.smim.database.comment;
import org.techtown.smim.database.item;
import org.techtown.smim.database.personal;
import org.techtown.smim.ui.notifications.CustomExerciseChoice;
import org.techtown.smim.ui.notifications.CustomExerciseChoiceAdapter;

import java.util.ArrayList;
import java.util.List;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.ContentProviderCompat.requireContext;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {
    ArrayList<ItemList> items = new ArrayList<ItemList>();
    public List<personal> list = new ArrayList<>();
    public List<personal> list1 = new ArrayList<>();
    public List<item> list5 = new ArrayList<>();
    List<board> list2 = new ArrayList<>();
    List<comment> list3 = new ArrayList<>();
    String  Id;
    Integer boardcount=0;
    Integer commentcount=0;
    static ImageView image;
    double flo = 1.0;
    int point = 312;
    public ItemListAdapter(){
    }

    static ItemListAdapter.OnPersonItemClickListener listener;

    public interface OnPersonItemClickListener {
        public void onItemClick(ItemList cec, int position); }

    public void setOnItemClicklistener(ItemListAdapter.OnPersonItemClickListener listener){ this.listener = listener; }


    public void onItemClick(ItemList cec, int position) {
        if(listener != null){
            listener.onItemClick(cec,position); } }



    static ItemListAdapter.OnItemsClickListener listener1;

    public interface OnItemsClickListener {
        public void onItemsClick(ItemList cec, int position); }

    public void setOnItemsClicklistener(ItemListAdapter.OnItemsClickListener listener1){ this.listener1 = listener1; }


    public void onItemsClick(ItemList cec, int position) {
        if(listener1 != null){
            listener1.onItemsClick(cec,position); } }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.sales_item, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ItemList item = items.get(position);
        viewHolder.setItem(item);
        viewHolder.onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(ItemList item) {
        items.add(item);
    }

    public void clearItem() {
        items.clear();
    }

    public void setItems(ArrayList<ItemList> items) {
        this.items = items;
    }

    public ItemList getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, ItemList item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView textView;
        TextView textView2;

        TextView price_prev;
        TextView price_now;


        Button mis;
        ItemList x = null;


        public ViewHolder(View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.list_image);
            textView = itemView.findViewById(R.id.list_name);
            textView2 = itemView.findViewById(R.id.list_part);
            price_prev = itemView.findViewById(R.id.tv_pre);
            price_now = itemView.findViewById(R.id.tv_now);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(x, position);
                    }
                }
            });
            mis= itemView.findViewById(R.id.btn_minus);
            mis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        int position = getAdapterPosition();

                    if(Integer.parseInt(price_now.getText().toString()) <= 312 ){
                        Toast.makeText(itemView.getContext(), "구매 완료", Toast.LENGTH_LONG).show();
                    }
                   else {
                        Toast.makeText(itemView.getContext(), "포인트 부족", Toast.LENGTH_LONG).show();
                    }
                   // Toast.makeText(itemView.getContext(), Integer.toString(position), Toast.LENGTH_LONG).show();
                    // position 을 0,1,2순서로 받기 -> DB에 그대로 반영, 구매 시 리스트에서 삭제
                        if(listener1 != null){
                            listener1.onItemsClick(x, position);
                        }

                }
            });
        }

        public void setItem(ItemList item) {
            price_prev.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        }

        public void onBind(ItemList person) {
            iv.setImageResource(person.getIimage());
            textView.setText(person.getGroupTitle());
            textView2.setText(String.valueOf(person.getGroupDesc()));
            price_prev.setText(person.getPrev());
            price_now.setText(person.getNow());
            this.x=person;

        }
    }

}
