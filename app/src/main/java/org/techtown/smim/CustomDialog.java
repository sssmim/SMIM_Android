package org.techtown.smim;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.techtown.smim.ui.dashboard.Exercise;
import org.techtown.smim.ui.dashboard.ExerciseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CustomDialog extends Dialog {


    private Context mContext;
    public Long position;
    ArrayList<Exercise> items;
    ExerciseAdapter adapter;
    int adapterposition;
    Long mem_num;
    public CustomDialog(Context mContext,Long mem_num,int adapterposition, Long position, ArrayList<Exercise> items,ExerciseAdapter adapter) {
        super(mContext);
        this.mContext = mContext;
        this.mem_num=mem_num;
        this.position = position;
        this.items= items;
        this.adapterposition=adapterposition;
        this.adapter= adapter;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);


        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getContext().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        Button btnres = findViewById(R.id.btnres);
        Button btndel = findViewById(R.id.btndel);


        btnres.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://52.78.235.23:8080/reservation";
                Map map = new HashMap();
                map.put("pnum", mem_num);
                map.put("gnum",position);
                JSONObject params = new JSONObject(map);

                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject obj) {
                                Toast.makeText(mContext,"예약되었습니다", Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        }) {

                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=UTF-8";
                    }
                };
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(objectRequest);

                dismiss();
            }
        });
        btndel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://52.78.235.23:8080/gexercise/" + position;

                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(mContext,"삭제되었습니다", Toast.LENGTH_SHORT).show();
                        items.remove(adapterposition);
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

                requestQueue.add(stringRequest);





                dismiss();
            }
        });

    }




}