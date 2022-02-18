package org.techtown.smim.ui.MyPage;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputFilter;
import android.text.Spanned;
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
import org.techtown.smim.MainActivity;
import org.techtown.smim.R;
import org.techtown.smim.database.personal;
import org.techtown.smim.ui.dashboard.ExercisePlanFragment;
import org.techtown.smim.ui.login.LoginActivity;
import org.techtown.smim.ui.login.LoginActivity_set2ndPwd;
import org.techtown.smim.ui.login.LoginActivity_setPwd;
import org.techtown.smim.ui.login.LoginActivity_setQuestion;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


public class ChangePwd extends Fragment {

    public List<personal> list = new ArrayList<>();

    Dialog dialog;
    Dialog dialog1;
    Dialog dialog2;
    Dialog dialog3;

    Long mem_num;

    String Id;
    String pwd;
    String name;
    String interest;
    String question;
    String answer;
    Long group_num;
    Integer point;

    String nowPwd;

    public InputFilter filter= new InputFilter() {
        public  CharSequence filter(CharSequence source, int start, int end,
                                    Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.changepwd, container, false);

        Bundle bundle = getArguments();
        mem_num = bundle.getLong("mem_num");

        Button change = root.findViewById(R.id.change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText now = root.findViewById(R.id.nowPwd);
                now.setFilters(new InputFilter[] {filter});
                nowPwd = now.getText().toString();
                EditText change1 = root.findViewById(R.id.changePwd);
                change1.setFilters(new InputFilter[] {filter});
                change1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(!hasFocus) {
                            if(change1.length() < 6) {
                                dialog = new Dialog(getContext());       // Dialog 초기화
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                                dialog.setContentView(R.layout.pwd_dialog);
                                dialog.show();
                                Button cancel = dialog.findViewById(R.id.btnCancel4);
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss(); // 다이얼로그 닫기
                                    }
                                });
                            }
                        }
                    }
                });
                String changePwd1 = change1.getText().toString();
                EditText change2 = root.findViewById(R.id.changePwd2);
                change2.setFilters(new InputFilter[] {filter});
                String changePwd2 = change2.getText().toString();

                RequestQueue requestQueue;
                Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
                Network network = new BasicNetwork(new HurlStack());
                requestQueue = new RequestQueue(cache, network);
                requestQueue.start();

                String url = "http://52.78.235.23:8080/personal";

                Bundle bundle = new Bundle();

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
                            if(mem_num.compareTo(list.get(i).mem_num) == 0) {
                                pwd = list.get(i).pwd;

                                if(nowPwd.compareTo(pwd) != 0) {
                                    dialog1 = new Dialog(getContext());       // Dialog 초기화
                                    dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                                    dialog1.setContentView(R.layout.login_setpwd_dialog);
                                    TextView txt = dialog1.findViewById(R.id.txt2);
                                    txt.setText("입력하신 현재 비밀번호가 \n 기존 비밀번호와 일치하지 않습니다.");
                                    dialog1.show();
                                    Button cancel1 = dialog1.findViewById(R.id.btnCancel3);
                                    cancel1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            now.setText("");
                                            dialog1.dismiss(); // 다이얼로그 닫기
                                        }
                                    });
                                } else if(changePwd1.compareTo(changePwd2) != 0) {
                                    dialog2 = new Dialog(getContext());       // Dialog 초기화
                                    dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                                    dialog2.setContentView(R.layout.login_setpwd_dialog);
                                    dialog2.show();
                                    Button cancel2 = dialog2.findViewById(R.id.btnCancel3);
                                    cancel2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog2.dismiss(); // 다이얼로그 닫기
                                        }
                                    });
                                } else if (changePwd1.length() < 6){
                                    dialog3 = new Dialog(getContext());       // Dialog 초기화
                                    dialog3.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                                    dialog3.setContentView(R.layout.pwd_dialog);
                                    dialog3.show();
                                    Button cancel3 = dialog3.findViewById(R.id.btnCancel4);
                                    cancel3.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog3.dismiss(); // 다이얼로그 닫기
                                        }
                                    });
                                } else {
                                    String newPwd = changePwd1;

                                    RequestQueue requestQueue2;
                                    Cache cache2 = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
                                    Network network2 = new BasicNetwork(new HurlStack());
                                    requestQueue2 = new RequestQueue(cache, network);
                                    requestQueue2.start();

                                    String url2 = "http://52.78.235.23:8080/personal";

                                    StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
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

                                            int index = 0;

                                            for(int i=0; i<list.size(); i++) {
                                                if(mem_num.compareTo(list.get(i).mem_num) == 0) {
                                                    index = i;
                                                    Id = list.get(i).id;
                                                    name = list.get(i).name;
                                                    interest = list.get(i).interest;
                                                    question = list.get(i).question;
                                                    answer = list.get(i).answer;
                                                    group_num = list.get(i).group_num;
                                                    point = list.get(i).point;
                                                }
                                            }

                                            Map map = new HashMap();
                                            map.put("id", Id);
                                            map.put("pwd", newPwd);
                                            map.put("name", name);
                                            map.put("interest", interest);
                                            map.put("group_num", group_num);
                                            map.put("point", point);
                                            map.put("question", question);
                                            map.put("answer", answer);
                                            JSONObject params = new JSONObject(map);

                                            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, url + "/" + list.get(index).mem_num.toString(), params,
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
                                            RequestQueue queue = Volley.newRequestQueue(getContext());
                                            queue.add(objectRequest);

                                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                                            intent.putExtra("ID" , Id);
                                            startActivity(intent);
                                            getActivity().finish();
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                        }
                                    });
                                    requestQueue2.add(stringRequest2);
                                }
                                break;
                            }
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