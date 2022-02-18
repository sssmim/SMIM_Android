package org.techtown.smim.ui.dashboard;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import org.techtown.smim.database.video;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentmakeFragment extends Fragment {
    Long mem_num;
    Long group_num;
    Long position;
    String postcomment;
    List<comment> list2 = new ArrayList<>();
    private ListView listView;
    private ListViewAdapter adapter;
    List<board> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_commentmake, container, false);

        Bundle bundle = getArguments();
        if(bundle != null) {
            mem_num = bundle.getLong("mem_num");
            group_num = bundle.getLong("group_num");
           position = bundle.getLong("boardid");
            Log.d("test_dashboradFragment1", String.valueOf(mem_num));
        }

        listView = (ListView) root.findViewById(R.id.listView1);

        adapter = new ListViewAdapter(getContext());

        adapter.clearItem();
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        TextView titles = root.findViewById(R.id.ctitle);
        TextView contents = root.findViewById(R.id.ccontent);
        String url3 = "http://52.78.235.23:8080/board";

        StringRequest stringRequest3 = new StringRequest(Request.Method.GET, url3, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                // 한글깨짐 해결 코드
                String changeString2 = new String();
                try {
                    changeString2 = new String(response.getBytes("8859_1"),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type listType = new TypeToken<ArrayList<board>>(){}.getType();
                list = gson.fromJson(changeString2, listType);


                for(int i = 0; i< list.size(); i++) {
                    if(list.get(i).board_id.compareTo(position) == 0) {
                      titles.setText(list.get(i).title);
                      contents.setText(list.get(i).main);

                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(stringRequest3);





        adapter.clearItem();
        String url2 = "http://52.78.235.23:8080/comment";

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(String response) {
                // 한글깨짐 해결 코드
                String changeString2 = new String();
                try {
                    changeString2 = new String(response.getBytes("8859_1"),"utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Type listType = new TypeToken<ArrayList<comment>>(){}.getType();
                list2 = gson.fromJson(changeString2, listType);


                for(int i = 0; i< list2.size(); i++) {
                    if(list2.get(i).board_num.compareTo(position) == 0) {

                        adapter.addItem(new Comment(list2.get(i).sub));

                    }
                }


                listView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(stringRequest2);




        EditText comment = root.findViewById(R.id.makecomment);

        Button register = root.findViewById(R.id.registercomment);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                postcomment = comment.getText().toString();
                Log.e("c", postcomment);
                String url = "http://52.78.235.23:8080/comment";
                Map map = new HashMap();

                map.put("p_num", mem_num);
                map.put("board_num", position);
                map.put("sub", postcomment);
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
                RequestQueue queue = Volley.newRequestQueue(requireContext());
                queue.add(objectRequest);
                comment.setText("");
                adapter.addItem(new Comment(postcomment));
                adapter.notifyDataSetChanged();

            }
        });


        return root;
    }


}