package org.techtown.smim.ui.MyPage;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import org.techtown.smim.database.group;
import org.techtown.smim.database.personal;
import org.techtown.smim.ui.dashboard.GroupList;
import org.techtown.smim.ui.login.LoginActivity;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyPage extends Fragment {

    public List<personal> list = new ArrayList<>();
    public List<personal> list1 = new ArrayList<>();
    List<board> list2 = new ArrayList<>();
    List<comment> list3 = new ArrayList<>();
    String  Id;
    Integer boardcount=0;
    Integer commentcount=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_my_page, container, false);

        Bundle bundle = getArguments();
        Long mem_num = bundle.getLong("mem_num");


        TextView name = root.findViewById(R.id.mypagename);
        TextView id = root.findViewById(R.id.mypageid);
        TextView grade = root.findViewById(R.id.mypagegrade);
        TextView canpoint = root.findViewById(R.id.canpoint);
        TextView totalpoint = root.findViewById(R.id.totalpoint);
        TextView boardc = root.findViewById(R.id.mypageboard);
        TextView commentc = root.findViewById(R.id.mypagecomment);

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
                        grade.setText(list1.get(i).grade.toString());
                        name.setText(list1.get(i).name);
                        id.setText(list1.get(i).id);

                        if(list1.get(i).point == null) {
                            canpoint.setText("0");
                        }else{
                            canpoint.setText(list1.get(i).point.toString());}
                        if(list1.get(i).total_point == null) {
                            totalpoint.setText("0");
                        }else{
                            totalpoint.setText(list1.get(i).total_point.toString());}



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
                boardcount=0;
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
                commentcount=0;
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

        ViewGroup layout4 = (ViewGroup) root.findViewById(R.id.upgrade);
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                UpgradeFragment fragment = new  UpgradeFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("mem_num", mem_num);
                fragment.setArguments(bundle);
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
        });

        ViewGroup layout5 = (ViewGroup) root.findViewById(R.id.buyit);
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                SalesItem fragment = new  SalesItem();
                Bundle bundle = new Bundle();
                bundle.putLong("mem_num", mem_num);
                fragment.setArguments(bundle);
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
        });



        ViewGroup layout1 = (ViewGroup) root.findViewById(R.id.changePwd);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                ChangePwd fragment = new ChangePwd();
                Bundle bundle = new Bundle();
                bundle.putLong("mem_num", mem_num);
                fragment.setArguments(bundle);
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            }
        });

        ViewGroup layout2 = (ViewGroup) root.findViewById(R.id.logout);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        ViewGroup layout3 = (ViewGroup) root.findViewById(R.id.deleteid);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //다이얼로그 띄워서 "회원탈퇴(Text)" 입력하게 한 후 [확인] 클릭 시 회원탈퇴 진행
                Dialog dialog = new Dialog(getContext());       // Dialog 초기화
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                dialog.setContentView(R.layout.deleteid);
                dialog.show();
                EditText txt = dialog.findViewById(R.id.checkText);
                Button fin = dialog.findViewById(R.id.fin1);
                fin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String text = txt.getText().toString();
                        if(text.compareTo("회원탈퇴") == 0) {

                            RequestQueue requestQueue;
                            Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
                            Network network = new BasicNetwork(new HurlStack());
                            requestQueue = new RequestQueue(cache, network);
                            requestQueue.start();

                            String url = "http://52.78.235.23:8080/personal";

                            Bundle bundle = new Bundle();

                            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url + "/" + mem_num.toString(), new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                }
                            });
                            requestQueue.add(stringRequest);

                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            dialog.dismiss(); // 다이얼로그 닫기
                        }
                    }
                });

                Button cancel = dialog.findViewById(R.id.cancel4);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss(); // 다이얼로그 닫기
                    }
                });
            }
        });

        return root;
    }
}