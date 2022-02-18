package org.techtown.smim.ui.login;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.techtown.smim.R;

import java.util.regex.Pattern;

public class LoginActivity_setQuestion extends AppCompatActivity {

    Dialog dialog;

    String Id;
    String pwd;

    String question;
    String answer;

    //영어만 입력받게 설정해줌.
    public InputFilter filter= new InputFilter() {
        public CharSequence filter(CharSequence source, int start, int end,
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
        setContentView(R.layout.login_setquestion);

        Intent intent1 = getIntent();
        Id = intent1.getStringExtra("ID");
        pwd = intent1.getStringExtra("PWD");

        Button next = findViewById(R.id.finish);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner s = findViewById(R.id.spinner);
                question = s.getSelectedItem().toString();

                EditText ans = findViewById(R.id.pwd2);
                answer = ans.getText().toString();

                Log.e("test_gg", answer);
                if(answer.compareTo("") == 0) {
                    dialog = new Dialog(LoginActivity_setQuestion.this);       // Dialog 초기화
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                    dialog.setContentView(R.layout.pwd_dialog);
                    TextView txt = dialog.findViewById(R.id.txt);
                    txt.setText("답변을 입력해주세요!");
                    dialog.show();

                    Button cancel = dialog.findViewById(R.id.btnCancel4);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss(); // 다이얼로그 닫기
                        }
                    });
                } else {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity_getExtra.class);
                    intent.putExtra("ID" , Id);
                    intent.putExtra("PWD" , pwd);
                    intent.putExtra("QUESTION", question);
                    intent.putExtra("ANSWER", answer);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
