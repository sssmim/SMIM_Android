package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.techtown.smim.R;

import java.util.HashMap;
import java.util.Map;

public class MakeGroup extends AppCompatActivity {
    public static final int num = 922;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_make);
        TextView groupname = findViewById(R.id.name);
        TextView groupdesc = findViewById(R.id.groupdesc);
    //private Button button;
    Button button =findViewById(R.id.confirm);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String url = "http://52.78.235.23:8080/organization";
            Map map = new HashMap();
            map.put("group_name", groupname.getText().toString());
            map.put("group_desc", groupdesc.getText().toString());
            map.put("group_category", "and test33");
            JSONObject params = new JSONObject(map);

            JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject obj) {
                            Log.e("dd", "ss");
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
            objectRequest.setTag("post");
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(objectRequest);
            //finish();

            //FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //FindGroup_test fragment2 = new FindGroup_test();
           /// transaction.replace(R.id.gf, fragment2);
            //transaction.commit();
            Intent intent = new Intent(getApplicationContext(), DashboardTrial.class);
            startActivityForResult(intent, 101);


        }

    });

}



}
