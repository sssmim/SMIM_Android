package org.techtown.smim;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.techtown.smim.database.personal;
import org.techtown.smim.ui.MyPage.MyPage;
import org.techtown.smim.ui.StatisticPage;
import org.techtown.smim.ui.dashboard.DashboardFragment1;
import org.techtown.smim.ui.dashboard.FindGroup_test;
import org.techtown.smim.ui.home.HomeFragment;
import org.techtown.smim.ui.notifications.CrawlingPage;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private HomeFragment homeFragment = new HomeFragment();
    //private FindGroup findGroup = new FindGroup();
    private FindGroup_test findGroup_test = new FindGroup_test();
    private DashboardFragment1 dashboardFragment1 = new DashboardFragment1();
    private CrawlingPage crawlingPage = new CrawlingPage();
    private StatisticPage statisticPage =new StatisticPage();
    private MyPage myPage =new MyPage();
    public Long mem_num;
    public Long group_num;
    public List<personal> list = new ArrayList<>();
    public String id;
    Long point;
    View header;

    //ArrayList<Entry> dataVals = new ArrayList<Entry>();
    //ArrayList<ILineDataSet> dataSets = new ArrayList<>();
    //LineDataSet lineDataSet1;
    //LineData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent1 = getIntent();
        if(intent1 != null) {
            id = intent1.getStringExtra("ID");
            point = intent1.getLongExtra("point" , 0L);
        }

        try {
            Thread.sleep(25); //0.025초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url = "http://52.78.235.23:8080/personal";

        Bundle bundle = new Bundle();
        Bundle bundle2 = new Bundle();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 한글깨짐 해결 코드
                String changeString = new String();
                try {
                    changeString = new String(response.getBytes("8859_1"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type listType = new TypeToken<ArrayList<personal>>() {
                }.getType();
                list = gson.fromJson(changeString, listType);

                for(int i=0; i<list.size(); i++) {
                    if(id.compareTo(list.get(i).id) == 0) {
                        mem_num = list.get(i).mem_num;
                        Log.d("test_MainActivity", String.valueOf(mem_num));
                        break;
                    }
                }

                bundle.putLong("mem_num", mem_num);
                bundle2.putLong("mem_num", mem_num);
                bundle2.putLong("point", point);

                Log.d("test_mainActivity" , String.valueOf(mem_num));

                homeFragment.setArguments(bundle2);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container, homeFragment).commitAllowingStateLoss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);

        //homeFragment.setArguments(bundle);
        findGroup_test.setArguments(bundle);
        crawlingPage.setArguments(bundle);
        dashboardFragment1.setArguments(bundle);
        statisticPage.setArguments(bundle);
        myPage.setArguments(bundle);
        //FragmentTransaction transaction = fragmentManager.beginTransaction();
        //transaction.replace(R.id.container, homeFragment).commitAllowingStateLoss();

        BottomNavigationView bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.navigation_home:
                    transaction.replace(R.id.container, homeFragment).commitAllowingStateLoss();

                    break;
                case R.id.navigation_dashboard: {
                    RequestQueue requestQueue;
                    Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
                    Network network = new BasicNetwork(new HurlStack());
                    requestQueue = new RequestQueue(cache, network);
                    requestQueue.start();

                    String url = "http://52.78.235.23:8080/personal";

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // 한글깨짐 해결 코드
                            String changeString = new String();
                            try {
                                changeString = new String(response.getBytes("8859_1"), "utf-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            Gson gson = new GsonBuilder().setPrettyPrinting().create();
                            Type listType = new TypeToken<ArrayList<personal>>() {
                            }.getType();
                            list = gson.fromJson(changeString, listType);

                            for(int i=0; i<list.size(); i++) {
                                if(list.get(i).mem_num.compareTo(mem_num) == 0) {
                                    group_num = list.get(i).group_num;
                                }
                            }
                            Log.d("test_MainActivity" , String.valueOf(group_num));

                            if(group_num == null || group_num.compareTo(0L) == 0) {
                                transaction.replace(R.id.container, findGroup_test).commitAllowingStateLoss();
                            } else {
                                transaction.replace(R.id.container, dashboardFragment1).commitAllowingStateLoss();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                    requestQueue.add(stringRequest);

                    //transaction.replace(R.id.container, findGroup_test).commitAllowingStateLoss();
                    break;
                }
                case R.id.navigation_notifications:
                    transaction.replace(R.id.container,crawlingPage).commitAllowingStateLoss();
                    break;
                case R.id.statistics:
                    transaction.replace(R.id.container, statisticPage).commitAllowingStateLoss();
                    break;
                case R.id.mypage:
                    transaction.replace(R.id.container, myPage).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }
}