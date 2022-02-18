package org.techtown.smim.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.techtown.smim.R;

public class FindGroup extends Fragment {
    public static final int num1 = 326;
    public static final int num2 = 328;

    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = (View)inflater.inflate(R.layout.group_find, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.RecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        org.techtown.smim.ui.dashboard.GroupListAdapter adapter = new org.techtown.smim.ui.dashboard.GroupListAdapter();

        adapter.addItem(new org.techtown.smim.ui.dashboard.GroupList("아침 운동", "7시 미라클모닝"));
        adapter.addItem(new org.techtown.smim.ui.dashboard.GroupList("직장인 오세요", "6시이후 저녁운동"));

        recyclerView.setAdapter(adapter);

        //private button button;
        FloatingActionButton button =(FloatingActionButton)root.findViewById(R.id.floatingActionButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), MakeGroup.class);
                startActivityForResult(intent, num1);
            }
        });

        /*Button button2 =(Button)root.findViewById(R.id.btn_move);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(requireContext(), DashboardTrial.class);
               //startActivityForResult(intent, num2);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                DashboardFragment fragment2 = new DashboardFragment();
                transaction.replace(R.id.container, fragment2);
                transaction.commit();
            }
        });*/

        return root;
    }
}
