package org.techtown.smim.ui.notifications;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.techtown.smim.R;
import org.techtown.smim.database.group;
import org.techtown.smim.database.iexercise;
import org.techtown.smim.ui.dashboard.ExerciseAdapter;
import org.techtown.smim.ui.dashboard.GroupExercisePlay;
import org.techtown.smim.ui.dashboard.GroupList;
import org.techtown.smim.ui.dashboard.GroupListAdapter;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomExerciseMerge extends AppCompatActivity {

    public static final int number22 = 13246;
    public static int num0=0;
    public static int num1=0;
    public static int num2=0;
    public static int num3=0;
    public static int num4=0;

    RecyclerView rv;
    CustomExerciseChoiceAdapter cadapter;
    ItemTouchHelper helper;

    public List<Long> getList = new ArrayList<>();
    public static List<iexercise> list = new ArrayList<>();
    ArrayList<Integer> value;
    Long mem_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_exercise_merge);

        Intent getIntent = getIntent();
        if(getIntent != null){
          value = getIntent.getIntegerArrayListExtra("key");
          mem_num = getIntent.getLongExtra("mem_num", 0L);
        }

        for(int i=0; i<value.size(); i++) {
            getList.add(Long.valueOf(value.get(i)));
        }

        rv = findViewById(R.id.rv);
        //RecyclerView의 레이아웃 방식을 지정
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(manager);

        //RecyclerView의 Adapter 세팅
        cadapter = new CustomExerciseChoiceAdapter();
        rv.setAdapter(cadapter);

        //ItemTouchHelper 생성
        helper = new ItemTouchHelper(new ItemTouchHelperCallback(cadapter));
        //RecyclerView에 ItemTouchHelper 붙이기
        helper.attachToRecyclerView(rv);

        RequestQueue requestQueue;
        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);
        // Start the queue
        requestQueue.start();

        String url = "http://52.78.235.23:8080/iexercise";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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
                Type listType = new TypeToken<ArrayList<iexercise>>(){}.getType();
                list = gson.fromJson(changeString, listType);

                for(int i = 0; i< list.size(); i++) {
                    for(int j=0; j<value.size(); j++) {
                        if(list.get(i).ie_num == getList.get(j)) {
                            int image = getResources().getIdentifier(list.get(i).ie_image , "drawable", getPackageName());
                            cadapter.addItem(new CustomExerciseChoice(image, list.get(i).ie_name, list.get(i).ie_part, 0));
                        }
                    }
                }
                rv.setAdapter(cadapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);

        /*
        CustomExerciseChoice ex1 = new CustomExerciseChoice(R.drawable.push_up,"푸시업","가슴",0);
        CustomExerciseChoice ex2 = new CustomExerciseChoice(R.drawable.pull_up,"풀업","등",0);
        CustomExerciseChoice ex3 = new CustomExerciseChoice(R.drawable.plank,"플랭크","복부",0);
        CustomExerciseChoice ex4 = new CustomExerciseChoice(R.drawable.dumbbell_shoulder_press,"덤벨 숄더 프레스","어깨",0);
        CustomExerciseChoice ex5 = new CustomExerciseChoice(R.drawable.dumbbell_lateral_raise,"덤벨 레터럴 레이즈","어깨",0);
        CustomExerciseChoice ex6 = new CustomExerciseChoice(R.drawable.dumbbell_bentover_lateral_raise, "벤트오버 덤벨 레터럴 레이즈","어깨");
        CustomExerciseChoice ex7 = new CustomExerciseChoice(R.drawable.dumbbell_curl, "덤벨 컬","팔(이두)");
        CustomExerciseChoice ex8 = new CustomExerciseChoice(R.drawable.triceps_dumbbell_extension, "덤벨 삼두 익스텐션","팔(삼두)");
        CustomExerciseChoice ex9 = new CustomExerciseChoice(R.drawable.squat, "에어 스쿼트","하체");
        CustomExerciseChoice ex10 = new CustomExerciseChoice(R.drawable.dumbbell_lunge, "덤벨 런지","하체");
        */


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://52.78.235.23:8080/list";
                Map map = new HashMap();
                for(int k=0; k<cadapter.getItemCount(); k++) {
                    map.put("name" + (k+1), cadapter.getItem(k).cgetName());
                    map.put("count" + (k+1), cadapter.getItem(k).cgetCount());
                }
                JSONObject params = new JSONObject(map);

                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject obj) {
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
                objectRequest.setTag("post");
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(objectRequest);
                Intent intent = new Intent(getApplicationContext(), TimerFragment.class);
               // startActivityForResult(intent, number22);
            }
        });



        cadapter.setOnItemClicklistener(new CustomExerciseChoiceAdapter.OnPersonItemClickListener(){
            @Override
            public void onItemClick(CustomExerciseChoice x, int position) {
                if (position ==0){
                    num0++;
                    x.count = num0;
                    cadapter.setItem(position,x);
                    cadapter.notifyDataSetChanged();}
                if (position ==1){
                    num1++;
                    x.count = num1;
                    cadapter.setItem(position,x);
                    cadapter.notifyDataSetChanged();}
                if (position ==2){
                    num2++;
                    x.count = num2;
                    cadapter.setItem(position,x);
                    cadapter.notifyDataSetChanged();}
                if (position ==3){
                    num3++;
                    x.count = num3;
                    cadapter.setItem(position,x);
                    cadapter.notifyDataSetChanged();}
                if (position ==4){
                    num4++;
                    x.count = num4;
                    cadapter.setItem(position,x);
                    cadapter.notifyDataSetChanged();}
            }});

        cadapter.setOnItemsClicklistener(new CustomExerciseChoiceAdapter.OnItemsClickListener(){
            @Override
            public void onItemsClick(CustomExerciseChoice x, int position) {
                if (position ==0){
                    num0--;
                    x.count = num0;
                    cadapter.setItem(position,x);
                    cadapter.notifyDataSetChanged();}
                if (position ==1){
                    num1--;
                    x.count = num1;
                    cadapter.setItem(position,x);
                    cadapter.notifyDataSetChanged();}
                if (position ==2){
                    num2--;
                    x.count = num2;
                    cadapter.setItem(position,x);
                    cadapter.notifyDataSetChanged();}
                if (position ==3){
                    num3--;
                    x.count = num3;
                    cadapter.setItem(position,x);
                    cadapter.notifyDataSetChanged();}
                if (position ==4) {
                    num4--;
                    x.count = num4;
                    cadapter.setItem(position, x);
                    cadapter.notifyDataSetChanged();
                }

            }});



    }
/*
    public static void addmethod(CustomExerciseChoice x,  int position){
        if (position ==0){
            num0++;
            x.count = num0;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
        if (position ==1){
            num1++;
            x.count = num1;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
        if (position ==2){
            num2++;
            x.count = num2;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
        if (position ==3){
            num3++;
            x.count = num3;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
        if (position ==4){
            num4++;
            x.count = num4;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
    }

    public static void minusmethod(CustomExerciseChoice x,  int position){
        if (position ==0){
            num0--;
            x.count = num0;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
        if (position ==1){
            num1--;
            x.count = num1;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
        if (position ==2){
            num2--;
            x.count = num2;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
        if (position ==3){
            num3--;
            x.count = num3;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
        if (position ==4){
            num4--;
            x.count = num4;
            cadapter.setItem(position,x);
            cadapter.notifyDataSetChanged();}
    }*/
}
