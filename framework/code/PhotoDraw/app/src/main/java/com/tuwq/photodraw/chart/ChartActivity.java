package com.tuwq.photodraw.chart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.tuwq.photodraw.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChartActivity extends Activity {

    @Bind(R.id.linechart)
    LineChart linechart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);
        ButterKnife.bind(this);
        // 设置没有数据的提示
        linechart.setNoDataText("没有数据");
        // 设置数据
        linechart.setData(getData());

        // 修改图表的描述
        linechart.setDescription("我的图表");
        // 设置动画
//        linechart.animateXY(3000,3000);
        YAxis left = linechart.getAxisLeft();
        left.setAxisMaxValue(1000);
    }

    private LineData getData() {
        // 创建x轴坐标系
        List<String> xVals = ChartData.generateXVals(0,1000);
        // 创建线的集合
        List<ILineDataSet> dataSets = new ArrayList<>();

       /* // 创建一条线
        List<Entry> entrys = new ArrayList<>();
        LineDataSet iLineDataSet = new LineDataSet(entrys,"线条一");
        for (int i = 0; i < 1000; i++) {
            // 创建一个点,参数1 指y坐标值，参数2 指x坐标系
            Random random = new Random();
            int nextInt = random.nextInt(10);
            Entry entry = new Entry(300+nextInt,i);
            entrys.add(entry);
        }
        dataSets.add(iLineDataSet);*/

        // 创建一条线
        List<Entry> entrys2 = new ArrayList<>();
        LineDataSet iLineDataSet2 = new LineDataSet(entrys2,"线条二");
        for (int i = 0; i < 1000; i++) {
            // 创建一个点,参数1 指y坐标值，参数2 指x坐标系
            Random random = new Random();
            int nextInt = random.nextInt(10);
            Entry entry = new Entry(300+nextInt,i);
            entrys2.add(entry);
        }
        dataSets.add(iLineDataSet2);

        iLineDataSet2.setColor(Color.RED);
        iLineDataSet2.setDrawCircles(false);
        iLineDataSet2.setDrawValues(false);


        LineData linedata = new LineData(xVals,dataSets);
        return linedata;
    }
}
