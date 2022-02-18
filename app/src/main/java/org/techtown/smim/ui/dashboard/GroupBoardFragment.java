package org.techtown.smim.ui.dashboard;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import org.techtown.smim.CustomDialog;
import org.techtown.smim.R;
import org.techtown.smim.database.board;
import org.techtown.smim.database.gexercise;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GroupBoardFragment extends Fragment {
    Dialog dialog;
    Long mem_num;
    Long group_num;
    Integer count=0;
    List<board> list2 = new ArrayList<>();
    public List<Long> boardid = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =inflater.inflate(R.layout.fragment_group_board, container, false);


        Bundle bundle = getArguments();
        if(bundle != null) {
            mem_num = bundle.getLong("mem_num");
            group_num = bundle.getLong("group_num");
            Log.d("test_dashboradFragment1", String.valueOf(mem_num));
        }
        try {
            Thread.sleep(25); //0.025초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = root.findViewById(R.id.boardre);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getActivity().getApplicationContext(),new LinearLayoutManager(getContext()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        BoardAdapter adapter = new BoardAdapter();

        adapter.clearItem();
        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

       String url2 = "http://52.78.235.23:8080/board";

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
                Type listType = new TypeToken<ArrayList<board>>(){}.getType();
                list2 = gson.fromJson(changeString2, listType);


                for(int i = 0; i< list2.size(); i++) {
                    if(list2.get(i).group_num.compareTo(group_num) == 0) {

                        adapter.addItem(new Board(list2.get(i).title, list2.get(i).main));
                        boardid.add(list2.get(i).board_id);
                    }
                }



                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(stringRequest2);


        FloatingActionButton button =(FloatingActionButton)root.findViewById(R.id.boardmake);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                BoardmakeFragment f = new BoardmakeFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("group_num", group_num);
                bundle.putLong("mem_num", mem_num);
                f.setArguments(bundle);
                transaction.replace(R.id.container,f).addToBackStack(null);
                transaction.commit();


            }
        });

        adapter.setOnItemClicklistener(new BoardAdapter.OnPersonItemClickListener() {
            @Override
            public void onItemClick(BoardAdapter.ViewHolder holder, View view, ArrayList<Board> items, int position) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                CommentmakeFragment f = new CommentmakeFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("group_num", group_num);
                bundle.putLong("mem_num", mem_num);
                bundle.putLong("boardid",boardid.get(position));
                f.setArguments(bundle);
                transaction.replace(R.id.container,f).addToBackStack(null);
                transaction.commit();
            }
        });



        return root;
    }


}