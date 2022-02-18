package org.techtown.smim.ui.dashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.smim.R;
import org.techtown.smim.database.group;
import org.techtown.smim.database.ietime;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindGroup_test extends Fragment {
    public static final int num1 = 326;
    public static final int num2 = 328;

    private DashboardViewModel dashboardViewModel;

    public List<group> list = new ArrayList<>();
    public List<Long> list2 = new ArrayList<>();

    Long mem_num;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = (View)inflater.inflate(R.layout.group_find, container, false);
       // Button post = (Button)root.findViewById(R.id.post);

        Bundle bundle = getArguments();
        mem_num = bundle.getLong("mem_num");
        Log.d("test_FindGroup_test", String.valueOf(mem_num));

        try {
            Thread.sleep(25); //0.025초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.RecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        GroupListAdapter adapter = new GroupListAdapter();
        Spinner s = root.findViewById(R.id.spinner2);
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
               if(position==0){
                   adapter.clearItem();
                   RequestQueue requestQueue;
                   Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
                   Network network = new BasicNetwork(new HurlStack());
                   requestQueue = new RequestQueue(cache, network);
                   requestQueue.start();

                   String url = "http://52.78.235.23:8080/organization";

                   StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           // 한글깨짐 해결 코드
                           String changeString = new String();
                           try {
                               changeString = new String(response.getBytes("8859_1"),"utf-8");
                           } catch (UnsupportedEncodingException e) {
                               e.printStackTrace();
                           }
                           Gson gson = new GsonBuilder().setPrettyPrinting().create();
                           Type listType = new TypeToken<ArrayList<group>>(){}.getType();
                           list = gson.fromJson(changeString, listType);

                           for(int i = 0; i< list.size(); i++) {
                               list2.add(list.get(i).group_num);

                                   adapter.addItem(new GroupList(list.get(i).group_name, list.get(i).group_desc));}


                           recyclerView.setAdapter(adapter);
                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                       }
                   });

                   requestQueue.add(stringRequest);
               }
               if(position==1){
                   adapter.clearItem();
                   RequestQueue requestQueue;
                   Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
                   Network network = new BasicNetwork(new HurlStack());
                   requestQueue = new RequestQueue(cache, network);
                   requestQueue.start();

                   String url = "http://52.78.235.23:8080/organization";

                   StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           // 한글깨짐 해결 코드
                           String changeString = new String();
                           try {
                               changeString = new String(response.getBytes("8859_1"),"utf-8");
                           } catch (UnsupportedEncodingException e) {
                               e.printStackTrace();
                           }
                           Gson gson = new GsonBuilder().setPrettyPrinting().create();
                           Type listType = new TypeToken<ArrayList<group>>(){}.getType();
                           list = gson.fromJson(changeString, listType);

                           for(int i = 0; i< list.size(); i++) {
                               list2.add(list.get(i).group_num);
                               if(list.get(i).group_category.compareTo("헬스") == 0){
                                   adapter.addItem(new GroupList(list.get(i).group_name, list.get(i).group_desc));}
                           }

                           recyclerView.setAdapter(adapter);
                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                       }
                   });

                   requestQueue.add(stringRequest);
               }
               if(position==2){
                   adapter.clearItem();
                   RequestQueue requestQueue;
                   Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
                   Network network = new BasicNetwork(new HurlStack());
                   requestQueue = new RequestQueue(cache, network);
                   requestQueue.start();

                   String url = "http://52.78.235.23:8080/organization";

                   StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           // 한글깨짐 해결 코드
                           String changeString = new String();
                           try {
                               changeString = new String(response.getBytes("8859_1"),"utf-8");
                           } catch (UnsupportedEncodingException e) {
                               e.printStackTrace();
                           }
                           Gson gson = new GsonBuilder().setPrettyPrinting().create();
                           Type listType = new TypeToken<ArrayList<group>>(){}.getType();
                           list = gson.fromJson(changeString, listType);

                           for(int i = 0; i< list.size(); i++) {
                               list2.add(list.get(i).group_num);
                               if(list.get(i).group_category.compareTo("필라테스") == 0){
                                   adapter.addItem(new GroupList(list.get(i).group_name, list.get(i).group_desc));}
                           }

                           recyclerView.setAdapter(adapter);
                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                       }
                   });

                   requestQueue.add(stringRequest);
               }
                if(position==3){
                   adapter.clearItem();
                   RequestQueue requestQueue;
                   Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
                   Network network = new BasicNetwork(new HurlStack());
                   requestQueue = new RequestQueue(cache, network);
                   requestQueue.start();

                   String url = "http://52.78.235.23:8080/organization";

                   StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                           // 한글깨짐 해결 코드
                           String changeString = new String();
                           try {
                               changeString = new String(response.getBytes("8859_1"),"utf-8");
                           } catch (UnsupportedEncodingException e) {
                               e.printStackTrace();
                           }
                           Gson gson = new GsonBuilder().setPrettyPrinting().create();
                           Type listType = new TypeToken<ArrayList<group>>(){}.getType();
                           list = gson.fromJson(changeString, listType);

                           for(int i = 0; i< list.size(); i++) {
                               list2.add(list.get(i).group_num);
                               if(list.get(i).group_category.compareTo("요가") == 0){
                                   adapter.addItem(new GroupList(list.get(i).group_name, list.get(i).group_desc));}
                           }

                           recyclerView.setAdapter(adapter);
                       }
                   }, new Response.ErrorListener() {
                       @Override
                       public void onErrorResponse(VolleyError error) {
                       }
                   });

                   requestQueue.add(stringRequest);

               }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });


        FloatingActionButton button =(FloatingActionButton)root.findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                makegroup1 fragment2 = new  makegroup1();
                Bundle bundle = new Bundle();
                bundle.putLong("mem_num", mem_num);
                fragment2.setArguments(bundle);
                transaction.replace(R.id.container, fragment2).addToBackStack(null);
                transaction.commit();
            }
        });

        adapter.setOnItemClicklistener(new GroupListAdapter.OnPersonItemClickListener(){
        @Override
        public void onItemClick(GroupListAdapter.ViewHolder holder, View view, int position)
        {   FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            DashboardFragment f = new DashboardFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("Obj", position);
            bundle.putLong("Group_num", list.get(position).group_num);
            bundle.putLong("mem_num", mem_num);
            f.setArguments(bundle);
            transaction.replace(R.id.container,f).addToBackStack(null);
            transaction.commit();
        } });
        return root;
    }
}
