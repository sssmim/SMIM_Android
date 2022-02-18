package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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
import org.techtown.smim.CustomDialog;
import org.techtown.smim.R;
import org.techtown.smim.database.gexercise;
import org.techtown.smim.database.group;
import org.techtown.smim.database.personal;
import org.techtown.smim.ui.home.HomeFragment;
import org.techtown.smim.ui.notifications.CustomExerciseChoice;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;

public class DashboardFragment extends Fragment {
    public static final int REQUEST_CODE_MENU = 101;

    public TextView name;
    public TextView group_count;
    public TextView info;
    public ArrayList<Exercise> list222 = new ArrayList<>();
    public List<group> list = new ArrayList<>();
    public List<gexercise> list2 = new ArrayList<>();
    public List<personal> list3 = new ArrayList<>();
    public List<Long> ge_numlist = new ArrayList<>();
    public List<String> urlList = new ArrayList<>();
    public List<String> idlist = new ArrayList<>();
    public List<Integer> pointlist = new ArrayList<>();

    Integer po;
    Long group_num;
    Long mem_num;
    Integer group_mem_count;

    private HorizontalCalendar horizontalCalendar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // dashboardViewModel =
        //   new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        name = root.findViewById(R.id.name);
        group_count = root.findViewById(R.id.groupcount);
        info = root.findViewById(R.id.info);

        Bundle bundle = getArguments();
        po =  bundle.getInt("Obj");
        group_num = bundle.getLong("Group_num");
        mem_num = bundle.getLong("mem_num");

        try {
            Thread.sleep(25); //0.025초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        ExerciseAdapter adapter = new ExerciseAdapter();
        adapter.clearItem();

        adapter.clearItem();
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
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
                    changeString = new String(response.getBytes("8859_1"),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type listType = new TypeToken<ArrayList<personal>>(){}.getType();
                list3 = gson.fromJson(changeString, listType);

                if(group_num == null || group_num.compareTo(0L) == 0) {
                    for(int i=0; i<list3.size(); i++) {
                        Log.d("test_mem", String.valueOf(mem_num));
                        if (list3.get(i).mem_num.compareTo(mem_num) == 0) {
                            group_num = list3.get(i).group_num;

                            Log.d("test_gro", String.valueOf(group_num));
                        }
                    }
                }

                group_mem_count = 0;

                for(int i=0; i<list3.size(); i++) {
                    if(list3.get(i).group_num != null) {
                        if (list3.get(i).group_num.compareTo(group_num) == 0) {
                            group_mem_count++;
                        }
                    }
                }

                group_count.setText(String.valueOf(group_mem_count));


                pointlist.clear();
                idlist.clear();

                Integer count=0;
                for(int i=0; i<list3.size(); i++) {
                    if (list3.get(i).group_num!=null){
                        if (list3.get(i).group_num.compareTo(group_num) == 0) {
                            if (list3.get(i).total_point==null){
                                pointlist.add(0);
                            } else{
                                pointlist.add(list3.get(i).total_point);}
                            idlist.add(list3.get(i).name);
                            count++;
                        }
                    }

                }

                Integer[] pointarray = new Integer[pointlist.size()];
                for(int i=0;i<pointlist.size();i++) {
                    pointarray[i]=pointlist.get(i);
                }

                String[] idarray = new String[pointlist.size()];
                for(int i=0;i<idlist.size();i++) {
                    idarray[i]=idlist.get(i);
                }

                Integer temp;
                String tempString;
                for(int i=0;i<pointarray.length;i++){
                    for(int j=i+1;j<pointarray.length;j++){
                        if(pointarray[i]<pointarray[j]){

                            temp = pointarray[i];
                            pointarray[i]=pointarray[j];
                            pointarray[j]=temp;

                            tempString = idarray[i];
                            idarray[i]=idarray[j];
                            idarray[j]=tempString;
                        }

                    }
                }
                ArrayList<Integer> realpoint = new ArrayList<>();
                for(Integer item : pointarray){
                    realpoint.add(item);
                }

                ArrayList<String> realid = new ArrayList<>();
                for(String item : idarray){
                    realid.add(item);
                }

                TextView v = root.findViewById(R.id.r1);
                if(realid.size() > 0 && realpoint.get(0) != 0) {
                    v.setText(realid.get(0));
                }
                else{
                    v.setText("");
                }
                TextView v1 = root.findViewById(R.id.r2);
                if(realid.size() > 1 && realpoint.get(1) != 0) {
                    v1.setText(realid.get(1));
                } else {
                    v1.setText("");
                }
                TextView v2 = root.findViewById(R.id.r3);
                if(realid.size() > 2 && realpoint.get(2) != 0) {
                    v2.setText(realid.get(2));
                } else {
                    v2.setText("");
                }

                String url0 = "http://52.78.235.23:8080/organization";

                StringRequest stringRequest0 = new StringRequest(Request.Method.GET, url0, new Response.Listener<String>() {
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

                        for(int i=0; i<list.size(); i++) {
                            Log.d("test_group", String.valueOf(group_num));
                            if(list.get(i).group_num.compareTo(group_num) == 0) {
                                name.setText(list.get(i).group_name);
                                info.setText(list.get(i).group_desc);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

                requestQueue.add(stringRequest0);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(stringRequest);

        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        horizontalCalendar = new HorizontalCalendar.Builder(root, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(5)
                .dayNameFormat("EEE")
                .dayNumberFormat("dd")
                .monthFormat("MMM")
                .textSize(14f, 24f, 14f)
                .showDayName(true)
                .showMonthName(true)

                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                adapter.clearItem();
                //Toast.makeText(getContext(), DateFormat.getDateInstance().format(date) + " is selected!", Toast.LENGTH_SHORT).show();
                String url2 = "http://52.78.235.23:8080/gexercise";

                StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        // 한글깨짐 해결 코드
                        String changeString2 = new String();
                        try {
                            changeString2 = new String(response.getBytes("8859_1"),"utf-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Gson gson = new GsonBuilder().setPrettyPrinting().create();
                        Type listType = new TypeToken<ArrayList<gexercise>>(){}.getType();
                        list2 = gson.fromJson(changeString2, listType);

                        for(int i = 0; i< list2.size(); i++) {
                            if(list2.get(i).group_num.compareTo(group_num) == 0) {
                                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
                                String Date1 = transFormat.format(date);
                                String Date2 = transFormat.format(list2.get(i).ge_date);
                                if(Date2.compareTo(Date1) == 0) {
                                    urlList.add(list2.get(i).video_url);
                                    adapter.addItem(new Exercise(list2.get(i).ge_date, list2.get(i).ge_start_time, list2.get(i).ge_end_time, list2.get(i).ge_desc));
                                    ge_numlist.add(list2.get(i).ge_num);
                                }
                            }
                        }

                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

                requestQueue.add(stringRequest2);
            }

        });

        Button refresh_button = root.findViewById(R.id.join);
        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue;
                Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
                Network network = new BasicNetwork(new HurlStack());
                requestQueue = new RequestQueue(cache, network);
                requestQueue.start();

                String url = "http://52.78.235.23:8080/personal";

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
                        Type listType = new TypeToken<ArrayList<personal>>(){}.getType();
                        list3 = gson.fromJson(changeString, listType);

                        Integer index = 0;

                        for(int i=0; i<list3.size(); i++) {
                            if(list3.get(i).mem_num == mem_num) {
                                index = i;
                            }
                        }

                        Map map = new HashMap();
                        map.put("id", list3.get(index).id);
                        map.put("pwd", list3.get(index).pwd);
                        map.put("name", list3.get(index).name);
                        map.put("interest", list3.get(index).interest);
                        map.put("group_num", group_num.intValue());
                        map.put("point",list3.get(index).point);

                        JSONObject params = new JSONObject(map);

                        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url + "/" + list3.get(index).mem_num.toString(), params,
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
                        requestQueue.add(objectRequest);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                requestQueue.add(stringRequest);

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                DashboardFragment1 fragment1 = new DashboardFragment1();
                Bundle bundle = new Bundle();
                bundle.putLong("mem_num", mem_num);
                bundle.putLong("group_num", group_num);
                fragment1.setArguments(bundle);
                transaction.replace(R.id.container, fragment1);
                transaction.commit();
            }
        });


        return root;
    }


}
