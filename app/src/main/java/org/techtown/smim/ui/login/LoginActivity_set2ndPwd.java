package org.techtown.smim.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class LoginActivity_set2ndPwd extends AppCompatActivity {

    private ArrayList<personal> list = new ArrayList<>();

    private EditText getPwd1;
    private EditText getPwd2;
    private Button checkPwd;

    private boolean check = false;

    Dialog dialog;

    String Id;
    String pwd;
    String name;
    String interest;
    String question;
    String answer;
    Long group_num;
    Integer point;

    //영어만 입력받게 설정해줌.
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_set);

        TextView txt = findViewById(R.id.textView4);
        txt.setText("비밀번호를 재설정합니다.");

        Intent intent1 = getIntent();
        Id = intent1.getStringExtra("ID");

        getPwd1 = findViewById(R.id.pwd1);
        getPwd1.setFilters(new InputFilter[] {filter});
        getPwd1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    if(getPwd1.length() < 6) {
                        dialog = new Dialog(LoginActivity_set2ndPwd.this);       // Dialog 초기화
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
        getPwd2= findViewById(R.id.pwd2);
        getPwd2.setFilters(new InputFilter[] {filter});
        checkPwd = findViewById(R.id.finish);
        checkPwd.setText("비밀번호 재설정");

        checkPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Pwd1 = getPwd1.getText().toString();
                String Pwd2 = getPwd2.getText().toString();

                if(Pwd1.compareTo(Pwd2) != 0) {
                    dialog = new Dialog(LoginActivity_set2ndPwd.this);       // Dialog 초기화
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                    dialog.setContentView(R.layout.login_setpwd_dialog);
                    dialog.show();
                    Button cancel = dialog.findViewById(R.id.btnCancel3);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss(); // 다이얼로그 닫기
                        }
                    });
                } else if (Pwd1.length() < 6){
                    dialog = new Dialog(LoginActivity_set2ndPwd.this);       // Dialog 초기화
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
                } else {
                    pwd = Pwd1;

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

                            int index = 0;

                            for(int i=0; i<list.size(); i++) {
                                if(Id.compareTo(list.get(i).id) == 0) {
                                    index = i;
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
                            map.put("pwd", pwd);
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
                            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                            queue.add(objectRequest);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("ID" , Id);
                            startActivity(intent);
                            finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
                    requestQueue.add(stringRequest);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginActivity_set2ndPwd.this, LoginActivity_checkQuestion.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        intent.putExtra("ID", Id);
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }
}

