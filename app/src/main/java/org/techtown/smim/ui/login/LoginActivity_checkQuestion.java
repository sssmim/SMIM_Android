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
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.techtown.smim.MainActivity;
import org.techtown.smim.R;
import org.techtown.smim.database.personal;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class LoginActivity_checkQuestion extends AppCompatActivity {

    private ArrayList<personal> list = new ArrayList<>();

    String Id;
    TextView question;
    String server_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_checkquestion);

        Intent intent1 = getIntent();
        Id = intent1.getStringExtra("ID");

        question = findViewById(R.id.question);

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
                    if(Id.compareTo(list.get(i).id) == 0) {
                        question.setText(list.get(i).question);
                        server_answer = list.get(i).answer;
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

        Button fin = findViewById(R.id.finish);
        fin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ans = findViewById(R.id.pwd2);
                String answer = ans.getText().toString();
                if(server_answer.compareTo(answer) == 0) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity_set2ndPwd.class);
                    intent.putExtra("ID", Id);
                    startActivity(intent);
                    finish();
                } else {
                    Dialog dialog = new Dialog(LoginActivity_checkQuestion.this);       // Dialog 초기화
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                    dialog.setContentView(R.layout.pwd_dialog);
                    TextView txt = dialog.findViewById(R.id.txt);
                    txt.setText("현재 입력하신 답변이 \n 회원가입 시 입력했던 답변과 다릅니다!");
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
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(LoginActivity_checkQuestion.this, LoginActivity_checkPwd.class); //지금 액티비티에서 다른 액티비티로 이동하는 인텐트 설정
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //인텐트 플래그 설정
        intent.putExtra("ID", Id);
        startActivity(intent);  //인텐트 이동
        finish();   //현재 액티비티 종료
    }
}

