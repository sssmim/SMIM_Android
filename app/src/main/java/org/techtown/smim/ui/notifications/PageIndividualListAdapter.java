package org.techtown.smim.ui.notifications;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.techtown.smim.R;
import org.techtown.smim.ui.dashboard.GroupListAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PageIndividualListAdapter extends RecyclerView.Adapter<PageIndividualListAdapter.ViewHolder> {
    ArrayList<PageIndividualList> items = new ArrayList<PageIndividualList>();

    static PageIndividualListAdapter.OnNewsItemClickListener listener;


    public interface OnNewsItemClickListener {
        public void onItemClick(PageIndividualListAdapter.ViewHolder holder, View view, int position); }

    public void setOnItemClicklistener(PageIndividualListAdapter.OnNewsItemClickListener listener){ this.listener = listener; }

    public void onItemClick(PageIndividualListAdapter.ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder,view,position); } }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.individual_info, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        PageIndividualList item = items.get(position);
        viewHolder.setItem(item);

        viewHolder.textView.setText(items.get(position).info_title);
        viewHolder.textView2.setText(items.get(position).info_desc);
       // viewHolder.imageView.setImageResource(items.get(position).image_view);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(PageIndividualList item) {
        items.add(item);
    }

    public void clearItem() {items.clear();}

    public void setItems(ArrayList<PageIndividualList> items) {
        this.items = items;
    }

    public PageIndividualList getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, PageIndividualList item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;
        ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.info_title);
            textView2 = itemView.findViewById(R.id.info_desc);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(PageIndividualListAdapter.ViewHolder.this, v, position);
                    } } });
        }

        public void setItem(PageIndividualList item) {
            textView.setText(item.getInfoTitle());
            textView2.setText(item.getInfoDesc());

           // Glide.with(itemView.getContext()).load(item.getImageView()).thumbnail(0.1f).into(imageView);
            Glide.with(itemView.getContext()).load(item.getImageView()).thumbnail(0.6f).into(imageView);

        }
    }

}
