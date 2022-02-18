package org.techtown.smim.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
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

import org.techtown.smim.R;
import org.techtown.smim.database.group;
import org.techtown.smim.database.iexercise;
import org.techtown.smim.ui.dashboard.GroupList;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ExerciseInfo extends AppCompatActivity {

    public static final int number922 = 1327;

    public TextView name;
    static ImageView image;
    public TextView desc;

    public List<iexercise> list = new ArrayList<>();

    Integer po;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_popup);

        Intent intent = getIntent();
        po =  intent.getIntExtra("obj", 0);

        name = findViewById(R.id.name);
        image = findViewById(R.id.image);
        desc = findViewById(R.id.desc);

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        String url = "http://52.78.235.23:8080/iexercise";

        // Formulate the request and handle the response.
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
                Type listType = new TypeToken<ArrayList<iexercise>>(){}.getType();
                list = gson.fromJson(changeString, listType);

                name.setText(list.get(po).ie_name);
                int image2 = getResources().getIdentifier(list.get(po).ie_image , "drawable", getPackageName());
                image.setImageResource(image2);
                String description = list.get(po).ie_dsec.replace("\\r\\n", "\r\n");
                desc.setText(description);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
        ImageButton imageButton = findViewById(R.id.exit);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
