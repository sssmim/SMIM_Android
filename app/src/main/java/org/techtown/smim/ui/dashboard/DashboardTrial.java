package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.techtown.smim.R;
import org.techtown.smim.database.group;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DashboardTrial extends AppCompatActivity {
    public static final int REQUEST_CODE_MENU = 101;

    //private DashboardViewModel dashboardViewModel;

    public TextView name;
    public TextView info;

    public List<group> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);

        name = findViewById(R.id.name);
        info = findViewById(R.id.info);

        //dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        //View root = inflater.inflate(R.layout.fragment_dashboard, container, false);


        RequestQueue requestQueue;

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();

        String url = "http://52.78.235.23:8080/organization";

        // Formulate the request and handle the response.
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
                name.setText(list.get(0).group_name);
                info.setText(list.get(0).group_desc);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);

        RecyclerView recyclerView =findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ExerciseAdapter adapter = new ExerciseAdapter();

        //adapter.addItem(new  Exercise("3시" ,"4시", "상체"));
        //adapter.addItem(new  Exercise("4시" ,"5시", "하체"));
        //adapter.addItem(new  Exercise("5시" ,"6시", "상체"));

        recyclerView.setAdapter(adapter);

        Button button = findViewById(R.id.groupplan);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExercisePlan.class);
                startActivityForResult(intent, REQUEST_CODE_MENU);
            }
        });

        //return root;
    }
}