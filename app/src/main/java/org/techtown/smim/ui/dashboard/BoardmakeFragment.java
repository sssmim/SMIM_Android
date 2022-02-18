package org.techtown.smim.ui.dashboard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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

public class BoardmakeFragment extends Fragment {
    Long mem_num;
    Long group_num;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_boardmake, container, false);

        Bundle bundle = getArguments();
        if(bundle != null) {
            mem_num = bundle.getLong("mem_num");
            group_num = bundle.getLong("group_num");
            Log.d("test_dashboradFragment1", String.valueOf(mem_num));
        }


                Button register = root.findViewById(R.id.bdregister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText title = root.findViewById(R.id.bdtitle);
                String posttitle=title.getText().toString();
                //Log.e("title",posttitle);
                EditText comment = root.findViewById(R.id.bdcomment);
                String postcomment=comment.getText().toString();
                //Log.e("c",postcomment);
                String url = "http://52.78.235.23:8080/board";
                Map map = new HashMap();
                map.put("group_num", group_num);
                map.put("p_num", mem_num);
                map.put("title",posttitle);
                map.put("main",postcomment);
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

                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                GroupBoardFragment fragment2 = new  GroupBoardFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("group_num", group_num);
                bundle.putLong("mem_num", mem_num);
                fragment2.setArguments(bundle);
                transaction.replace(R.id.container, fragment2);
                transaction.commit();




            }
        });

        return root;
    }
}