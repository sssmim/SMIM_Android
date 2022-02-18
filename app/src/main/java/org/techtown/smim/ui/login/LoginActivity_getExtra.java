package org.techtown.smim.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.techtown.smim.MainActivity;
import org.techtown.smim.R;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class LoginActivity_getExtra extends AppCompatActivity {

    private EditText getName;
    private Button finish;

    Dialog dialog;

    String Id;
    String pwd;
    String question;
    String answer;

    //한글만 입력받게 설정
    public InputFilter filterKor = new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            Pattern ps = Pattern.compile("^[ㄱ-가-힣]+$");
            if (!ps.matcher(source).matches()) {
                return "";
            }
            return null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_getextra);

        Intent intent1 = getIntent();
        Id = intent1.getStringExtra("ID");
        pwd = intent1.getStringExtra("PWD");
        question = intent1.getStringExtra("QUESTION");
        answer = intent1.getStringExtra("ANSWER");

        CheckBox interest1 = findViewById(R.id.interest1);
        CheckBox interest2 = findViewById(R.id.interest2);
        CheckBox interest3 = findViewById(R.id.interest3);

        getName = findViewById(R.id.getNAME);
        //getName.setFilters(new InputFilter[] {filterKor});
        finish = findViewById(R.id.finish);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String interest = "";

                if(interest1.isChecked()) {
                    interest += (interest1.getText().toString() + ",");
                }
                if(interest2.isChecked()) {
                    interest += (interest2.getText().toString() + ",");
                }
                if(interest3.isChecked()) {
                    interest += (interest3.getText().toString() + ",");
                }

                String name = getName.getText().toString();

                String url = "http://52.78.235.23:8080/personal";
                Map map = new HashMap();
                map.put("id", Id);
                map.put("pwd", pwd);
                map.put("name", name);
                map.put("interest", interest);
                map.put("point", 0);
                map.put("total_point", 0);
                map.put("question", question);
                map.put("answer", answer);
                map.put("grade", 6);
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
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(objectRequest);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("ID" , Id);
                startActivity(intent);
                finish();
            }
        });
    }
}
