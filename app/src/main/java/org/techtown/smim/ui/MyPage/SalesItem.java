package org.techtown.smim.ui.MyPage;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.techtown.smim.R;
import org.techtown.smim.database.board;
import org.techtown.smim.database.comment;
import org.techtown.smim.database.group;
import org.techtown.smim.database.iexercise;
import org.techtown.smim.database.item;
import org.techtown.smim.database.personal;
import org.techtown.smim.ui.dashboard.DashboardFragment;
import org.techtown.smim.ui.dashboard.GroupList;
import org.techtown.smim.ui.dashboard.GroupListAdapter;
import org.techtown.smim.ui.dashboard.makegroup1;
import org.techtown.smim.ui.login.LoginActivity;
import org.techtown.smim.ui.notifications.CustomExerciseChoice;
import org.techtown.smim.ui.notifications.CustomExerciseChoiceAdapter;
import org.techtown.smim.ui.notifications.ItemTouchHelperCallback;
import org.techtown.smim.ui.notifications.TimerFragment;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SalesItem extends Fragment {

    public List<personal> list = new ArrayList<>();
    public List<personal> list1 = new ArrayList<>();
    public List<item> list5 = new ArrayList<>();
    List<board> list2 = new ArrayList<>();
    List<comment> list3 = new ArrayList<>();
    String  Id;
    Integer boardcount=0;
    Integer commentcount=0;
    static ImageView image;
    double flo = 1.0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_buyit, container, false);

        Bundle bundle = getArguments();
        Long mem_num = bundle.getLong("mem_num");


        TextView name = root.findViewById(R.id.mypagename);
        TextView id = root.findViewById(R.id.mypageid);
        TextView grade = root.findViewById(R.id.mypagegrade);

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

                        if(list1.get(i).grade == 5){ flo = 0.997;}
                        if(list1.get(i).grade == 4){ flo = 0.975;}
                        if(list1.get(i).grade == 3){ flo = 0.95;}
                        if(list1.get(i).grade == 2){ flo = 0.93;}
                        if(list1.get(i).grade == 1){ flo = 0.90;}

                        if(list1.get(i).point == null) {
                            boardc.setText("0");
                        }else{
                            boardc.setText(list1.get(i).point.toString());}
                        if(list1.get(i).total_point == null) {
                            commentc.setText("0");
                        }else{
                            commentc.setText(list1.get(i).total_point.toString());}


                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue1.add(stringRequest1);





        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.buy_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ItemListAdapter adapter = new ItemListAdapter();


        RequestQueue requestQueue5;
        Cache cache5 = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network5 = new BasicNetwork(new HurlStack());
        requestQueue5 = new RequestQueue(cache5, network5);
        requestQueue5.start();

        String url5 = "http://52.78.235.23:8080/item";

        StringRequest stringRequest5 = new StringRequest(Request.Method.GET, url5, new Response.Listener<String>() {
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
                Type listType = new TypeToken<ArrayList<item>>(){}.getType();
                list5 = gson.fromJson(changeString, listType);

                for(int i = 0; i< list5.size(); i++) {
                    int image2 = getResources().getIdentifier(list5.get(i).item_img,"drawable",getContext().getPackageName());

                   int pri = list5.get(i).item_price;
                   int mul= (int)(pri*flo);
                    adapter.addItem(new ItemList(image2, list5.get(i).item_sort,list5.get(i).item_name, Integer.toString(pri) ,Integer.toString(mul)));

                }
                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Add the request to the RequestQueue.
        requestQueue5.add(stringRequest5);




        return root;
    }
}