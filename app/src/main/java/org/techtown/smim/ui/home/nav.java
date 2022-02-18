package org.techtown.smim.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import com.github.mikephil.charting.charts.LineChart; 
import com.github.mikephil.charting.components.Description; 
import com.github.mikephil.charting.components.XAxis; 
import com.github.mikephil.charting.components.YAxis; 
import com.github.mikephil.charting.data.Entry; 
import com.github.mikephil.charting.data.LineData; 
import com.github.mikephil.charting.data.LineDataSet; 
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet; 
import com.github.mikephil.charting.utils.ColorTemplate; 
import com.github.mikephil.charting.charts.LineChart;

import org.techtown.smim.R;

import java.util.ArrayList;

public class nav extends AppCompatActivity {
    LineChart mpLineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_header);

        mpLineChart = findViewById(R.id.lineChart);
        LineDataSet lineDataSet1 = new LineDataSet(dataValues1(), "Data Set 1");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

        LineData data = new LineData(dataSets);
        mpLineChart.setData(data);
        mpLineChart.invalidate();
    }

    private ArrayList<Entry> dataValues1(){
        ArrayList<Entry> dataVals = new ArrayList<Entry>();

        dataVals.add(new Entry(0, 20));
        dataVals.add(new Entry(1, 30));
        dataVals.add(new Entry(2, 40));
        dataVals.add(new Entry(3, 50));
        dataVals.add(new Entry(4, 30));

        return dataVals;
    }
}

