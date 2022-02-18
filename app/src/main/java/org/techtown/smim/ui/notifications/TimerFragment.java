package org.techtown.smim.ui.notifications;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

import org.json.JSONException;
import org.json.JSONObject;
import org.techtown.smim.R;
import org.techtown.smim.database.ielist;
import org.techtown.smim.database.ietime;
import org.techtown.smim.database.iexercise;
import org.techtown.smim.database.personal;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TimerFragment extends Fragment {

    private TextView exercise_name;
    private TextView countdownText;
    private TextView secText;

    private CountDownTimer countDownTimer;

    private long time = 0;
    private long tempTime = 0;

    private Integer index = 0;
    private Integer total = 0;

    private boolean firstState = true;

    public List<ielist> list = new ArrayList<>();
    public List<iexercise> list2 = new ArrayList<>();
    public List<String> nameList = new ArrayList<>();
    public List<Integer> countList = new ArrayList<>();
    public List<String> secList = new ArrayList<>();
    public List<ietime> ietList = new ArrayList<>();

    public Integer num = 0;
    public String getTotal;

    Long mem_num;

    Date date1;
    Date date2;
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.KOREA);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timer,container,false);

        Bundle bundle = getArguments();
        if(bundle != null) {
            mem_num = bundle.getLong("mem_num");
        }

        try {
            Thread.sleep(25); //0.025초 대기
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        exercise_name =  (TextView)view.findViewById(R.id.exercisename);
        countdownText =  (TextView)view.findViewById(R.id.countTextView);
        secText =  (TextView)view.findViewById(R.id.secTextView);

        String url2 = "http://52.78.235.23:8080/iexercise";

        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, url2, new Response.Listener<String>() {
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
                Type listType = new TypeToken<ArrayList<iexercise>>() {
                }.getType();
                list2 = gson.fromJson(changeString, listType);

                String url = "http://52.78.235.23:8080/list";

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
                        Type listType = new TypeToken<ArrayList<ielist>>() {
                        }.getType();
                        list = gson.fromJson(changeString, listType);

                        nameList.add(list.get(list.size()-1).name1);
                        total++;
                        if(list.get(list.size()-1).name2 != null) {
                            nameList.add(list.get(list.size()-1).name2);
                            total++;
                        } if(list.get(list.size()-1).name3 != null) {
                            nameList.add(list.get(list.size()-1).name3);
                            total++;
                        } if(list.get(list.size()-1).name4 != null) {
                            nameList.add(list.get(list.size()-1).name4);
                            total++;
                        } if(list.get(list.size()-1).name5 != null) {
                            nameList.add(list.get(list.size()-1).name5);
                            total++;
                        }

                        Log.d("test_nameListSize", String.valueOf(nameList.size()));

                        if (nameList.size() != 0) {
                            exercise_name.setText(nameList.get(0));
                        }

                        countList.add(list.get(list.size()-1).count1);
                        if(list.get(list.size()-1).count2 != null) {
                            countList.add(list.get(list.size()-1).count2);
                        } if(list.get(list.size()-1).name3 != null) {
                            countList.add(list.get(list.size()-1).count3);
                        } if(list.get(list.size()-1).name4 != null) {
                            countList.add(list.get(list.size()-1).count4);
                        } if(list.get(list.size()-1).name5 != null) {
                            countList.add(list.get(list.size()-1).count5);
                        }

                        if (countList.size() != 0) {
                            countdownText.setText(Integer.toString(countList.get(0)));
                        }

                        for(int i=0; i<total; i++) {
                            for(int j=0; j<list2.size(); j++) {
                                if(list2.get(j).ie_name.compareTo(nameList.get(i)) == 0) {
                                    secList.add(list2.get(j).ie_sec);
                                }
                            }
                        }

                        if (secList.size() != 0) {
                            secText.setText(secList.get(0));
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

                requestQueue.add(stringRequest);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        requestQueue.add(stringRequest1);

        Button btnComplete =  (Button) view.findViewById(R.id.btnComplete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        Button button =  (Button) view.findViewById(R.id.gonext);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameList.size() > index + 1) {
                    index++;
                    exercise_name.setText(nameList.get(index));
                }
                if (countList.size() > index) {
                    countdownText.setText(Integer.toString(countList.get(index)));
                }
                if (secList.size() > index) {
                    secText.setText(secList.get(index));
                }
            }
        });

        Button button2 =  (Button) view.findViewById(R.id.goprev);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(index-1 >= 0) {
                    index--;
                }
                if (nameList.size() != 0) {
                    exercise_name.setText(nameList.get(index));
                }
                if (countList.size() != 0) {
                    countdownText.setText(Integer.toString(countList.get(index)));
                }
                if (secList.size() != 0) {
                    secText.setText(secList.get(index));
                }
            }
        });

        Button button3 =  (Button) view.findViewById(R.id.start);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(firstState) {
                   Long now = System.currentTimeMillis();
                   date1 = new Date(now);
                   Log.d("test_startTime" , String.valueOf(date1));
                   firstState = false;
               }

                String second = secText.getText().toString();
                time = (Long.parseLong(second) * 1000) + 1000;
                secText.setText(Long.toString(time));

                countDownTimer = new CountDownTimer(time, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        tempTime = millisUntilFinished;
                        updateTimer();
                    }

                    @Override
                    public void onFinish() {
                        Log.d("test_onFinish", "Finish");
                    }
                }.start();
            }
            void updateTimer() {
                int seconds = (int) tempTime % 3600000 % 60000 / 1000;
                if (seconds == 0) {
                    String temp = countdownText.getText().toString();
                    if(Integer.parseInt(temp) > 1) {
                        seconds = Integer.parseInt(secList.get(index));
                        countdownText.setText(String.valueOf(Integer.parseInt(temp) - 1));
                        countDownTimer.start();
                    } else if(Integer.parseInt(temp) == 1 && index + 1 < total) {
                        index++;
                        exercise_name.setText(nameList.get(index));
                        countdownText.setText(Integer.toString(countList.get(index)));
                        tempTime = Long.parseLong(secList.get(index));
                        Log.d("test_tempTime", String.valueOf(tempTime));
                        seconds = Integer.parseInt(secList.get(index));
                        time = tempTime;
                        secText.setText(Integer.toString(seconds));
                        countDownTimer.cancel();
                    } else if(index + 1 == total){
                        Log.d("test_1", String.valueOf(seconds));
                        secText.setText(Integer.toString(seconds));
                    }
                } else {
                    secText.setText(Integer.toString(seconds));
                }
            }
        });

        Button button4 =  (Button) view.findViewById(R.id.pause);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
            }
        });

        return view;
    }

    void show(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("개인 운동 시간 측정이 완료되었습니다!");

        builder.setNegativeButton("확인",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Long now = System.currentTimeMillis();
                        date2 = new Date(now);
                        Log.d("test_endTime" , String.valueOf(date2));

                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
                        String getDay = dateFormat1.format(date1);

                        Long diff = date2.getTime() - date1.getTime();
                        String diffTime = dateFormat.format(diff);
                        Log.d("test_diff" , diffTime);

                        RequestQueue requestQueue;
                        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
                        Network network = new BasicNetwork(new HurlStack());
                        requestQueue = new RequestQueue(cache, network);
                        requestQueue.start();

                        String url = "http://52.78.235.23:8080/ietime";

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
                                Type listType = new TypeToken<ArrayList<ietime>>() {
                                }.getType();
                                ietList = gson.fromJson(changeString, listType);

                                for(int i=0; i<ietList.size(); i++) {
                                    if (mem_num.compareTo(Long.valueOf(ietList.get(i).p_num)) == 0) {
                                        String getDayTemp = dateFormat1.format(ietList.get(i).daily_record);
                                        if (getDay.compareTo(getDayTemp) == 0) {
                                            num = ietList.get(i).iet_num;
                                            getTotal = ietList.get(i).daily_total;
                                            break;
                                        }
                                    }
                                }

                                if(num == 0) {
                                    final JSONObject jsonObject = new JSONObject();
                                    try {
                                        jsonObject.put("p_num", mem_num);
                                        jsonObject.put("daily_record", getDay);
                                        jsonObject.put("daily_total", diffTime);
                                    } catch (JSONException e) {
                                        // handle exception
                                    }
                                    JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                                            new Response.Listener<JSONObject>()
                                            {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    // response
                                                    Log.d("Response", response.toString());
                                                }
                                            },
                                            new Response.ErrorListener()
                                            {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    // error
                                                    Log.d("Error.Response", error.toString());
                                                }
                                            }) {

                                        @Override
                                        public String getBodyContentType() {
                                            return "application/json; charset=UTF-8";
                                        }
                                    };
                                    requestQueue.add(putRequest);
                                } else {
                                    String addTimeString = "";
                                    final JSONObject jsonObject = new JSONObject();
                                    try {
                                        Date time1 = dateFormat.parse(getTotal);
                                        Date time2 = dateFormat.parse(diffTime);
                                        Date kor = dateFormat.parse("09:00:00");
                                        long addTime = time1.getTime() + time2.getTime() - kor.getTime();
                                        addTimeString = dateFormat.format(addTime);
                                        Log.d("test_Time" , addTimeString);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        jsonObject.put("p_num", mem_num);
                                        jsonObject.put("daily_record", getDay);
                                        jsonObject.put("daily_total", addTimeString);
                                    } catch (JSONException e) {
                                    }
                                    Log.d("test_checkNum", String.valueOf(num));
                                    JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url + "/" + num, jsonObject,
                                            new Response.Listener<JSONObject>()
                                            {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    // response
                                                    Log.d("Response", response.toString());
                                                }
                                            },
                                            new Response.ErrorListener()
                                            {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    // error
                                                    Log.d("Error.Response", error.toString());
                                                }
                                            }) {

                                        @Override
                                        public String getBodyContentType() {
                                            return "application/json; charset=UTF-8";
                                        }
                                    };
                                    requestQueue.add(putRequest);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                        requestQueue.add(stringRequest);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        CrawlingPage crawlfragment = new CrawlingPage();
                        Bundle bundle = new Bundle();
                        bundle.putLong("mem_num",mem_num);
                        crawlfragment.setArguments(bundle);
                        fragmentTransaction.replace(R.id.container, crawlfragment);
                        fragmentTransaction.commit();
                    }
                });
        builder.show();
    }
}
