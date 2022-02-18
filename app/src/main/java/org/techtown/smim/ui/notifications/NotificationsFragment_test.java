package org.techtown.smim.ui.notifications;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Button;
import android.widget.Toast;
import android.app.Person;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import org.techtown.smim.database.gexercise;
import org.techtown.smim.database.group;
import org.techtown.smim.database.iexercise;
import org.techtown.smim.ui.dashboard.DashboardFragment;
import org.techtown.smim.ui.dashboard.Exercise;
import org.techtown.smim.ui.dashboard.ExercisePlan;
import org.techtown.smim.ui.dashboard.GroupList;
import org.techtown.smim.ui.dashboard.GroupListAdapter;
import org.techtown.smim.ui.dashboard.YoutubePlan;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment_test extends Fragment {

    //private NotificationsViewModel notificationsViewModel;
    public static final int customexerciseplan = 1;
    public static final int number = 7;

    private List<CustomExercise> mExerciseList;
    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;

    public List<iexercise> list = new ArrayList<>();
    public static ArrayList<Integer> m = new ArrayList<>();
    public List<String> list2 = new ArrayList<>();

    Long mem_num;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //notificationsViewModel =
        //        new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        super.onCreate(savedInstanceState);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView);

        Bundle bundle = getArguments();
        mem_num = bundle.getLong("mem_num");
        Log.d("test_CrawlingPage", String.valueOf(mem_num));

        m.clear();

        RequestQueue requestQueue;
        Cache cache = new DiskBasedCache(getActivity().getCacheDir(), 1024 * 1024); // 1MB cap
        Network network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache, network);
        requestQueue.start();

        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        final CustomExerciseAdapter adapter = new CustomExerciseAdapter();
        adapter.clearItem();

        String url = "http://52.78.235.23:8080/iexercise";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
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

                for(int i = 0; i< list.size(); i++) {
                    String description = list.get(i).ie_dsec.replace("\\r\\n", "\r\n");
                    int image = getResources().getIdentifier(list.get(i).ie_image , "drawable", getContext().getPackageName());
                    list2.add(list.get(i).ie_dsec);
                    adapter.addItem(new CustomExercise(list.get(i).ie_name, list.get(i).ie_part, image,false));
                }

                recyclerView.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);

        //adapters.addItem(new CustomExercise("푸시업", "가슴", R.drawable.push_up,"1. 양팔을 가슴 옆에 두고 바닥에 엎드립니다. \n 2. 복근과 둔근에 힘을 준 상태로 팔꿈치를 펴며 올라옵니다. \n  3. 천천히 팔꿈치를 굽히며 시작 자세로 돌아갑니다."));
        //adapters.addItem(new CustomExercise("풀업", "등", R.drawable.pull_up,"1. 팔을 어깨너비만큼 벌리고, 손바닥이 앞을 바라본 상태로 바를 잡고 매달립니다. \n 2. 가슴을 편 상태로 바를 구부려 준다는 느낌으로 팔을 당겨 올라갑니다. \n 3. 상체가 흔들리지 않도록 자세를 유지하면서 내려옵니다."));
        //adapters.addItem(new CustomExercise("플랭크", "복부", R.drawable.plank,"1. 손목과 팔꿈치를 바닥에 댄 상태로 바닥에 엎드립니다. \n 2. 팔꿈치와 어깨를 동일선상에 높은 상태에서, 복부와 엉덩이에 힘을 주며 몸을 밀어 올립니다. \n 3. 등을 평평하게 만든다는 생각으로 팔꿈치로 바닥을 밀며 자세를 유지합니다."));
        //adapters.addItem(new CustomExercise("덤벨 숄더 프레스", "어깨", R.drawable.dumbbell_shoulder_press,"1. 양손에 덤벨을 잡고 팔을 옆으로 벌려서, 덤벨을 머리와 나란히 위치시킵니다. \n 2. 어깨를 아래 방향으로 지그시 누르면서 덤벨을 수직 방향으로 밀어올립니다. \n 3. 어깨의 자극을 느끼면서, 덤벨을 내려 시작 자세로 돌아옵니다."));
        //adapters.addItem(new CustomExercise("덤벨 레터럴 레이즈", "어깨", R.drawable.dumbbell_lateral_raise,"1. 양발을 어깨너비로 적당히 벌리고, 가슴을 편 상태로 덤벨을 잡습니다. \n 2. 측면 어깨(삼각근)에 자극을 느끼면서 팔꿈치를 올린다는 생각으로 덤벨을 들어 올립니다. \n 3. 측면 삼각근의 자극을 느끼면서 천천히 팔을 내립니다."));
        //adapters.addItem(new CustomExercise("벤트오버 덤벨 레터럴 레이즈", "어깨", R.drawable.dumbbell_bentover_lateral_raise,"1. 양발을 어깨너비로 적당히 벌리고, 허리를 곱게 편 상태로 상체를 숙여줍니다. \n 2. 팔꿈치를 살짝 굽힌 상태에서, 덤벨을 든 양손을 어깨와 1자가 되게끔 들어 올립니다. \n 3. 어깨 뒤편의 자극을 느끼면서 덤벨을 천천히 내려줍니다."));
        //adapters.addItem(new CustomExercise("덤벨 컬", "팔(이두)", R.drawable.dumbbell_curl,"1. 양발을 어깨너비로 적당히 벌리고 양손에 덤벨을 잡습니다. (이때, 어깨는 내리고 팔꿈치를 옆구리에 밀착하듯 붙여줍니다.) \n 2. 이두근으로만 덤벨을 들어 올린다는 생각으로 팔을 구부립니다. (이때, 팔꿈치가 움직이지 않도록 고정된 상태를 유지합니다.) \n 3. 구부린 팔을 펴면서 시작 자세로 돌아갑니다."));
        //adapters.addItem(new CustomExercise("덤벨 삼두 익스텐션", "팔(삼두)", R.drawable.triceps_dumbbell_extension,"1. 양팔을 골반 너비로 적당히 벌리고, 덤벨을 양손으로 잡고 섭니다. \n 2. 덤벨을 잡은 양팔의 팔꿈치를 직각으로 만들면서 머리 뒤로 넘깁니다 \n 3. 팔꿈치를 고정한 상태로 덤벨을 위로 들어 올립니다."));
        //adapters.addItem(new CustomExercise("에어 스쿼트", "하체", R.drawable.squat,"1. 다리를 어깨너비만큼 벌리고 허리를 펴고 곱게 섭니다. \n 2. 가슴을 편 상태로 엉덩이를 뒤로 빼며 앉습니다. (이때 양팔을 앞으로 벌려주면 균형을 잡기 쉽습니다) \n 3. 발바닥으로 지면을 밀고 일어나면서 시작 자세로 돌아옵니다.\n"));
        //adapters.addItem(new CustomExercise("덤벨 런지", "하체", R.drawable.dumbbell_lunge,"1. 덤벨을 양손에 들고 상체를 곧게 편 상태로, 양발을 앞뒤로 벌리고 섭니다. \n 2. 시선은 정면을 향한 상태에서, 두 무릎이 90도를 이룰 때까지 엉덩이를 낮춰 줍니다. \n 3. 무게중심을 어느 한 방향으로 쏠리지 않게 유지하면서 무릎을 펴며 올라옵니다."));

        Button imageButton = root.findViewById(R.id.customplan);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                CustomExerciseMergeFragment MergeFragment  = new CustomExerciseMergeFragment();
                Bundle bundle = new Bundle();
                bundle.putLong("mem_num",mem_num);
                bundle.putIntegerArrayList("key", m);
                Log.e("Te",String.valueOf(m.size()));
                MergeFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.container, MergeFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        adapter.setOnItemClicklistener(new CustomExerciseAdapter.OnPersonItemClickListener(){
            @Override
            public void onItemClick(CustomExerciseAdapter.ViewHolder holder, View view, int position)
            {   CustomExercise item = adapter.getItem(position);
                //Toast.makeText(getContext(),"아이템 선택 "+position, Toast.LENGTH_LONG).show();
                Toast.makeText(getContext(),"아이템 선택 "+position, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(requireContext(), ExerciseInfo.class);
                intent.putExtra("obj", position);
                startActivity(intent);
               // startActivityForResult(intent, number);
            } });
        return root;
    }

    public static void ischeck(int position){
         m.add(position);
    }
    public static void uncheck(int position){
        m.remove(position);
    }

}