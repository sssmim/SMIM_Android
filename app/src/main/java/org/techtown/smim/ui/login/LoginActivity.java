package org.techtown.smim.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

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

public class LoginActivity extends AppCompatActivity {

    private ArrayList<personal> list = new ArrayList<>();

    private EditText getId;
    private Button checkId;

    private boolean check = false;

    Dialog dialog;

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
        setContentView(R.layout.login_main);

        getId = findViewById(R.id.getID);
        getId.setFilters(new InputFilter[] {filter});

        checkId = findViewById(R.id.finish);

        checkId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = getId.getText().toString();
                if (Id.length() <= 0) {
                    dialog = new Dialog(LoginActivity.this);       // Dialog 초기화
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                    dialog.setContentView(R.layout.id_dialog);
                    dialog.show();
                    Button cancel = dialog.findViewById(R.id.btnCancel5);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss(); // 다이얼로그 닫기
                        }
                    });
                } else {
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
                                    Log.d("test_Login", "login");
                                    check = true;
                                    Long temp = list.get(i).mem_num;
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity_checkPwd.class);
                                    intent.putExtra("ID", Id);
                                    startActivity(intent);
                                    finish();
                                    break;
                                }
                            }

                            if(check == false) {
                                Log.d("test_not_yet", "false");
                                dialog = new Dialog(LoginActivity.this);       // Dialog 초기화
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                                dialog.setContentView(R.layout.login_dialog);
                                dialog.show();
                                Button make = dialog.findViewById(R.id.btnGo);
                                make.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Log.d("test_setPwd", "setPwd");
                                        Intent intent = new Intent(getApplicationContext(), LoginActivity_setPwd.class);
                                        intent.putExtra("ID", Id);
                                        startActivity(intent);
                                        finish();
                                        dialog.dismiss();
                                    }
                                });
                                Button cancel = dialog.findViewById(R.id.btnCancel3);
                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Log.d("test_cancel", "cancel");
                                        dialog.dismiss(); // 다이얼로그 닫기
                                    }
                                });
                            }
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
}
