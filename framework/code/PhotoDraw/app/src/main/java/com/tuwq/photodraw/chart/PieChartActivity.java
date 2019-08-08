package com.tuwq.photodraw.chart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.tuwq.photodraw.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PieChartActivity extends Activity {
    @Bind(R.id.linechart)
    PieChart linechart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);
        ButterKnife.bind(this);

        linechart.setData(getData());
        linechart.animateXY(3000,3000);
        linechart.setCenterText("center");
    }

    private PieData getData() {
        String[] xVals = new String[]{"Android 2.3","Android 3.0","Android 4.0","Android 5.0"};

        List<Entry> yVals = new ArrayList<>();
        yVals.add(new Entry(4,0));
        yVals.add(new Entry(2,1));
        yVals.add(new Entry(1,2));
        yVals.add(new Entry(1,3));

        PieDataSet dataSet = new PieDataSet(yVals,"安卓");
        dataSet.setColors(new int[]{Color.GREEN,Color.BLUE,Color.YELLOW,Color.RED});
        PieData piedata = new PieData(xVals,dataSet);
        return piedata;
    }
}
