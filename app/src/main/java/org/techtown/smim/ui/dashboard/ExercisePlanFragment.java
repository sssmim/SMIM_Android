package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.techtown.smim.R;
import org.techtown.smim.ui.notifications.CustomExerciseChoice;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//eee
public class ExercisePlanFragment extends Fragment {
    public static final int plantoyoutbe = 102;
    public static final int plantomain = 10;

    public String youtubeurl;
    public TextView days;
    public TextView startHour;
    public TextView startMin;
    public TextView endHour;
    public TextView endMin;
    public TextView planMemo;

    public String realurl;
    public Long mem_num;
    public Long group_num;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_exercise_plan, container, false);

        Bundle bundle = getArguments();
        if(String.valueOf(bundle.getLong("mem_num")) != null) {
            mem_num = bundle.getLong("mem_num");
        }
        if(String.valueOf(bundle.getLong("group_num")) != null) {
            group_num = bundle.getLong("group_num");
        }
        if(bundle.getString("realurl") != null) {
            realurl = bundle.getString("realurl");
            Log.d("test_realurl", realurl);
        }

        days = root.findViewById(R.id.days);
        startHour = root.findViewById(R.id.starthour);
        startMin = root.findViewById(R.id.startmin);
        endHour = root.findViewById(R.id.endthour);
        endMin = root.findViewById(R.id.endmin);
        planMemo = root.findViewById(R.id.planmemo);

        CalendarView calendar = (CalendarView) root.findViewById(R.id.calendarView);
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd");
        Date time = new Date();

        String time1 = format1.format(time);
        days.setText(time1);
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String setDay = year + "-" + (month+1) + "-" + dayOfMonth;
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(setDay);
                    String newString = new SimpleDateFormat("yyyy-MM-dd").format(date);
                    days.setText(newString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        Button button = root.findViewById(R.id.chooseyoutube);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(requireContext(), YoutubePlan.class);
                //startActivityForResult(intent, plantoyoutbe);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                YoutubePlanFragment fragment3 = new YoutubePlanFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("mem_num", mem_num);
                bundle.putLong("group_num", group_num);
                fragment3.setArguments(bundle);
                transaction.replace(R.id.container, fragment3);
                transaction.commit();
            }
        });

        Button button3 = root.findViewById(R.id.addplan1);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                DashboardFragment1 fragment2 = new DashboardFragment1();
                transaction.replace(R.id.container, fragment2);
                transaction.commit();
            }});



        Button button1 = root.findViewById(R.id.addplan);
        button1.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   String cchour="";
                   Integer chour=null;
                   String ccehour="";
                   Integer cehour=null;
                   String rstartmin="";
                   Integer startmin=null;
                   String rendmin="";
                   Integer endmin=null;
                   try {
                       chour= Integer.parseInt(startHour.getText().toString());
                       cehour=Integer.parseInt(endHour.getText().toString());
                       startmin=Integer.parseInt(startMin.getText().toString());
                       endmin=Integer.parseInt(endMin.getText().toString());

                       if ((chour / 10) < 1) {
                           if((cehour / 10) < 1){
                               cchour = "0" + chour;
                               ccehour = "0"+ cehour;
                               if ((startmin / 10) < 1) {
                                   if((endmin / 10) < 1){
                                       rstartmin = "0" + startmin;
                                       rendmin = "0"+ endmin;
                                   }
                                   else{
                                       rstartmin = "0" + startmin;
                                       rendmin = endMin.getText().toString();
                                   }

                               } else {
                                   if((endmin / 10) < 1){
                                       rstartmin = startMin.getText().toString();
                                       rendmin = "0"+ endmin;
                                   }
                                   else{
                                       rstartmin = startMin.getText().toString();
                                       rendmin = endMin.getText().toString();
                                   }

                               }
                           }
                           else{
                               cchour = "0" + chour;
                               ccehour = endHour.getText().toString();
                               if ((startmin / 10) < 1) {
                                   if((endmin / 10) < 1){
                                       rstartmin = "0" + startmin;
                                       rendmin = "0"+ endmin;
                                   }
                                   else{
                                       rstartmin = "0" + startmin;
                                       rendmin = endMin.getText().toString();
                                   }

                               } else {
                                   if((endmin / 10) < 1){
                                       rstartmin = startMin.getText().toString();
                                       rendmin = "0"+ endmin;
                                   }
                                   else{
                                       rstartmin = startMin.getText().toString();
                                       rendmin = endMin.getText().toString();
                                   }

                               }
                           }

                       } else {
                           if((cehour / 10) < 1){
                               cchour = startHour.getText().toString();
                               ccehour = "0"+ cehour;
                               if ((startmin / 10) < 1) {
                                   if((endmin / 10) < 1){
                                       rstartmin = "0" + startmin;
                                       rendmin = "0"+ endmin;
                                   }
                                   else{
                                       rstartmin = "0" + startmin;
                                       rendmin = endMin.getText().toString();
                                   }

                               } else {
                                   if((endmin / 10) < 1){
                                       rstartmin = startMin.getText().toString();
                                       rendmin = "0"+ endmin;
                                   }
                                   else{
                                       rstartmin = startMin.getText().toString();
                                       rendmin = endMin.getText().toString();
                                   }

                               }
                           }
                           else{
                               cchour = startHour.getText().toString();
                               ccehour = endHour.getText().toString();
                               if ((startmin / 10) < 1) {
                                   if((endmin / 10) < 1){
                                       rstartmin = "0" + startmin;
                                       rendmin = "0"+ endmin;
                                   }
                                   else{
                                       rstartmin = "0" + startmin;
                                       rendmin = endMin.getText().toString();
                                   }

                               } else {
                                   if((endmin / 10) < 1){
                                       rstartmin = startMin.getText().toString();
                                       rendmin = "0"+ endmin;
                                   }
                                   else{
                                       rstartmin = startMin.getText().toString();
                                       rendmin = endMin.getText().toString();
                                   }

                               }
                           }

                       }
                       //Toast.makeText(getApplicationContext(), cchour, Toast.LENGTH_LONG).show();
                       //Log.e("Test", cchour);
                   } catch (NumberFormatException e) {

                   } catch (Exception e) {

                   }

                   if(cchour==""||rstartmin==""||ccehour==""||rendmin==""||planMemo.getText().toString()==""){
                       Toast.makeText(requireContext(),"빈칸이 입력되었습니다.", Toast.LENGTH_LONG).show();
                   }
                   else if((Integer.parseInt(cchour)<0||Integer.parseInt(cchour)>24)||(Integer.parseInt(ccehour)<0||Integer.parseInt(ccehour)>24)||(Integer.parseInt(rstartmin)<0||Integer.parseInt(rstartmin)>60)||(Integer.parseInt(rendmin)<0||Integer.parseInt(rendmin)>60)) {
                       Toast.makeText(requireContext(),"시는 0~24시,분은 0~60분안으로 설정해주세요", Toast.LENGTH_LONG).show();
                   }
                  else if(realurl==""||realurl==null){
                       Toast.makeText(requireContext(),"유튜브동영상을 선택해주세요", Toast.LENGTH_LONG).show();
                   }
                   else{

                       String url = "http://52.78.235.23:8080/gexercise";
                       Map map = new HashMap();
                       map.put("ge_date", days.getText().toString());
                       String start_time = cchour + ":" + rstartmin + ":00";
                       map.put("ge_start_time", start_time);
                       String end_time = ccehour + ":" + rendmin + ":00";
                       map.put("ge_end_time", end_time);
                       map.put("ge_desc", planMemo.getText().toString());
                       map.put("video_url", realurl);
                       map.put("group_num", group_num);
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
                       Toast.makeText(requireContext(), "추가되었습니다", Toast.LENGTH_LONG).show();
                       startHour.setText("");
                       endHour.setText("");
                       startMin.setText("");
                       endMin.setText("");
                       planMemo.setText("");
                       days.setText("");

                       FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                       DashboardFragment1 fragment2 = new DashboardFragment1();
                       Bundle bundle = new Bundle();
                       bundle.putLong("mem_num", mem_num);
                       bundle.putLong("group_num", group_num);
                       fragment2.setArguments(bundle);
                       transaction.replace(R.id.container, fragment2);
                       transaction.commit();
                   }

               }
           }
        );
        return root;
    }


}
