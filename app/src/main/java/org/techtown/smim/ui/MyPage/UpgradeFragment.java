package org.techtown.smim.ui.MyPage;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.techtown.smim.R;
import org.techtown.smim.database.board;
import org.techtown.smim.database.comment;
import org.techtown.smim.database.personal;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UpgradeFragment extends Fragment {

    public List<personal> list = new ArrayList<>();
    public List<personal> list1 = new ArrayList<>();
    public List<personal> p1 = new ArrayList<>();
    List<board> list2 = new ArrayList<>();
    List<comment> list3 = new ArrayList<>();
    String  Id;
    Integer boardcount=0;
    Integer commentcount=0;
    Integer point=0;
    ImageView gradei;
    Integer realgrade=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =inflater.inflate(R.layout.fragment_upgrade, container, false);


        Bundle bundle = getArguments();
        Long mem_num = bundle.getLong("mem_num");

        gradei = root.findViewById(R.id.gradeimg);
        TextView upgrade = root.findViewById(R.id.upgradeg);
        TextView name = root.findViewById(R.id.upgradename);
        TextView grade = root.findViewById(R.id. upgradeg);
        TextView boardc = root.findViewById(R.id.nowboard);
        TextView commentc = root.findViewById(R.id.nowcomment);
        TextView points = root.findViewById(R.id.nowpoint);
        TextView pointn = root.findViewById(R.id.nextpoint);
        TextView boardn = root.findViewById(R.id.nextboard);
        TextView commentn = root.findViewById(R.id.nextcomment);

        RequestQueue requestQueue1;
        Cache cache1 = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network1 = new BasicNetwork(new HurlStack());
        requestQueue1 = new RequestQueue(cache1, network1);
        requestQueue1.start();

        String url1 = "http://52.78.235.23:8080/personal";

        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
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
                list1 = gson.fromJson(changeString, listType);
                for(int i=0; i<list1.size(); i++) {

                    if (list1.get(i).mem_num.compareTo(mem_num) == 0) {

                        name.setText(list1.get(i).name);
                        grade.setText(list1.get(i).grade.toString());
                        realgrade=list1.get(i).grade;
                        if(list1.get(i).grade==6){
                            int image2 = getResources().getIdentifier("sixth", "drawable",getActivity().getPackageName());
                            gradei.setImageResource(image2);
                            pointn.setText(String.valueOf(200));
                            boardn.setText(String.valueOf(5));
                            commentn.setText(String.valueOf(5));

                        }
                        else if(list1.get(i).grade==1){
                            int image2 = getResources().getIdentifier("first", "drawable",getActivity().getPackageName());
                            gradei.setImageResource(image2);
                            pointn.setText("최고등급입니다");
                            boardn.setText("최고등급입니다");
                            commentn.setText("최고등급입니다");

                        }
                        else if(list1.get(i).grade==2){
                            int image2 = getResources().getIdentifier("second", "drawable",getActivity().getPackageName());
                            gradei.setImageResource(image2);
                            pointn.setText(String.valueOf(2001));
                            boardn.setText(String.valueOf(50));
                            commentn.setText(String.valueOf(50));
                        }
                        else if(list1.get(i).grade==3){
                            int image2 = getResources().getIdentifier("third", "drawable",getActivity().getPackageName());
                            gradei.setImageResource(image2);
                            pointn.setText(String.valueOf(1501));
                            boardn.setText(String.valueOf(40));
                            commentn.setText(String.valueOf(40));
                        }
                        else if(list1.get(i).grade==4){
                            int image2 = getResources().getIdentifier("fourth", "drawable",getActivity().getPackageName());
                            gradei.setImageResource(image2);
                            pointn.setText(String.valueOf(1001));
                            boardn.setText(String.valueOf(30));
                            commentn.setText(String.valueOf(30));
                        }
                        else if(list1.get(i).grade==5){
                            int image2 = getResources().getIdentifier("fifth", "drawable",getActivity().getPackageName());
                            gradei.setImageResource(image2);
                            pointn.setText(String.valueOf(501));
                            boardn.setText(String.valueOf(15));
                            commentn.setText(String.valueOf(15));
                        }



                        if(list1.get(i).total_point == null) {
                            point=0;
                            points.setText("0");
                        }else{
                            point=list1.get(i).total_point;
                            points.setText(list1.get(i).total_point.toString());}


                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue1.add(stringRequest1);


        RequestQueue requestQueue2;
        Cache cache2 = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network2 = new BasicNetwork(new HurlStack());
        requestQueue2 = new RequestQueue(cache2, network2);
        requestQueue2.start();

        String url2 = "http://52.78.235.23:8080/board";

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
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
                Type listType = new TypeToken<ArrayList<board>>(){}.getType();
                list2 = gson.fromJson(changeString, listType);
                for(int i=0; i<list2.size(); i++) {

                    if (list2.get(i).p_num.compareTo(mem_num) == 0) {

                        boardcount+=1;


                    }
                }
                boardc.setText(boardcount.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue2.add(stringRequest2);


        RequestQueue requestQueue3;
        Cache cache3 = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network3 = new BasicNetwork(new HurlStack());
        requestQueue3 = new RequestQueue(cache3, network3);
        requestQueue3.start();

        String url3 = "http://52.78.235.23:8080/comment";

        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, url3, new Response.Listener<String>() {
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
                Type listType = new TypeToken<ArrayList<comment>>(){}.getType();
                list3 = gson.fromJson(changeString, listType);
                for(int i=0; i<list3.size(); i++) {

                    if (list3.get(i).p_num.compareTo(mem_num) == 0) {

                        commentcount+=1;


                    }
                }
                commentc.setText(commentcount.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue3.add(stringRequest3);


        TextView buttonu = root.findViewById(R.id.textup);
        buttonu.setOnClickListener(new View.OnClickListener() {
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
                        p1 = gson.fromJson(changeString, listType);

                        Integer index = 0;

                        for(int i=0; i<p1.size(); i++) {
                            if(p1.get(i).mem_num == mem_num) {
                                index = i;
                            }
                        }

                        if(realgrade==6){
                            if(point>=200&&commentcount>=5&&boardcount>=5){
                                Map map = new HashMap();
                                map.put("id", p1.get(index).id);
                                map.put("pwd", p1.get(index).pwd);
                                map.put("name", p1.get(index).name);
                                map.put("interest", p1.get(index).interest);
                                map.put("group_num", p1.get(index).group_num);
                                map.put("point",p1.get(index).point);
                                map.put("question",p1.get(index).question);
                                map.put("answer",p1.get(index).answer);
                                map.put("grade",5);

                                JSONObject params = new JSONObject(map);

                                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url + "/" + p1.get(index).mem_num.toString(), params,
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
                                int image2 = getResources().getIdentifier("fifth", "drawable",getActivity().getPackageName());
                                gradei.setImageResource(image2);
                                upgrade.setText(String.valueOf(5));
                                pointn.setText(String.valueOf(501));
                                boardn.setText(String.valueOf(15));
                                commentn.setText(String.valueOf(15));
                            }

                        }
                        else if(realgrade==5){
                            if(point>=501&&commentcount>=15&&boardcount>=15){
                                Map map = new HashMap();
                                map.put("id", p1.get(index).id);
                                map.put("pwd", p1.get(index).pwd);
                                map.put("name", p1.get(index).name);
                                map.put("interest", p1.get(index).interest);
                                map.put("group_num", p1.get(index).group_num);
                                map.put("point",p1.get(index).point);
                                map.put("question",p1.get(index).question);
                                map.put("answer",p1.get(index).answer);
                                map.put("grade",4);

                                JSONObject params = new JSONObject(map);

                                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url + "/" + p1.get(index).mem_num.toString(), params,
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
                                int image2 = getResources().getIdentifier("fourth", "drawable",getActivity().getPackageName());
                                gradei.setImageResource(image2);
                                upgrade.setText(String.valueOf(4));
                                pointn.setText(String.valueOf(1001));
                                boardn.setText(String.valueOf(30));
                                commentn.setText(String.valueOf(30));
                            }

                        }
                        else if(realgrade==4){
                            if(point>=1001&&commentcount>=30&&boardcount>=30){
                                Map map = new HashMap();
                                map.put("id", p1.get(index).id);
                                map.put("pwd", p1.get(index).pwd);
                                map.put("name", p1.get(index).name);
                                map.put("interest", p1.get(index).interest);
                                map.put("group_num", p1.get(index).group_num);
                                map.put("point",p1.get(index).point);
                                map.put("question",p1.get(index).question);
                                map.put("answer",p1.get(index).answer);
                                map.put("grade",3);

                                JSONObject params = new JSONObject(map);

                                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url + "/" + p1.get(index).mem_num.toString(), params,
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
                                int image2 = getResources().getIdentifier("third", "drawable",getActivity().getPackageName());
                                gradei.setImageResource(image2);
                                upgrade.setText(String.valueOf(3));
                                pointn.setText(String.valueOf(1501));
                                boardn.setText(String.valueOf(40));
                                commentn.setText(String.valueOf(40));
                            }

                        }
                        else if(realgrade==3){
                            if(point>=1501&&commentcount>=40&&boardcount>=40){
                                Map map = new HashMap();
                                map.put("id", p1.get(index).id);
                                map.put("pwd", p1.get(index).pwd);
                                map.put("name", p1.get(index).name);
                                map.put("interest", p1.get(index).interest);
                                map.put("group_num", p1.get(index).group_num);
                                map.put("point",p1.get(index).point);
                                map.put("question",p1.get(index).question);
                                map.put("answer",p1.get(index).answer);
                                map.put("grade",2);

                                JSONObject params = new JSONObject(map);

                                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url + "/" + p1.get(index).mem_num.toString(), params,
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
                                int image2 = getResources().getIdentifier("second", "drawable",getActivity().getPackageName());
                                gradei.setImageResource(image2);
                                upgrade.setText(String.valueOf(2));
                                pointn.setText(String.valueOf(2001));
                                boardn.setText(String.valueOf(50));
                                commentn.setText(String.valueOf(50));
                            }

                        }
                        else if(realgrade==2){
                            if(point>=2001&&commentcount>=50&&boardcount>=50){
                                Map map = new HashMap();
                                map.put("id", p1.get(index).id);
                                map.put("pwd", p1.get(index).pwd);
                                map.put("name", p1.get(index).name);
                                map.put("interest", p1.get(index).interest);
                                map.put("group_num", p1.get(index).group_num);
                                map.put("point",p1.get(index).point);
                                map.put("question",p1.get(index).question);
                                map.put("answer",p1.get(index).answer);
                                map.put("grade",1);

                                JSONObject params = new JSONObject(map);

                                JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url + "/" + p1.get(index).mem_num.toString(), params,
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
                                int image2 = getResources().getIdentifier("first", "drawable",getActivity().getPackageName());
                                gradei.setImageResource(image2);
                                upgrade.setText(String.valueOf(1));
                                pointn.setText("최고등급입니다");
                                boardn.setText("최고등급입니다");
                                commentn.setText("최고등급입니다");

                            }

                        }
                       else if(realgrade==1){
                            Toast.makeText(requireContext(),"더 이상 업그레이드할 등급이 없습니다.", Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                requestQueue.add(stringRequest);


            }
        });




        return root;
    }


}