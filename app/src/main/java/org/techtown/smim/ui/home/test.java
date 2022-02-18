package org.techtown.smim.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.techtown.smim.R;
import org.techtown.smim.database.group;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test extends AppCompatActivity {

    public List<group> list = new ArrayList<>();

    public String test = "test1";

    public TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView = findViewById(R.id.result);

        String url="http://52.78.235.23:8080/organization";
        Map map=new HashMap();
        map.put("group_name", "and test");
        map.put("group_desc", "and test22");
        map.put("group_category", "and test33");
        map.put("view_count", 22);
        map.put("authority", 1);
        JSONObject params = new JSONObject(map);

        JsonObjectRequest objectRequest=new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject obj) {
                        textView.setText("success");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){

            @Override
            public String getBodyContentType() {
                return "application/json; charset=UTF-8";
            }
        };
        objectRequest.setTag("post");
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(objectRequest);
    }
}