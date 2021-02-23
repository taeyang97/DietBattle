package com.example.dietbattle;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import com.example.dietbattle.database.BattleDay;
import com.example.dietbattle.database.BattleEnemyDay;
import com.example.dietbattle.database.User;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;




public class BattleInfoFragment extends Fragment {

    ArrayList<Integer> my = new ArrayList<Integer>();
    ArrayList<Integer> enemy = new ArrayList<Integer>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_battle_fragment_info, container, false);

        LineChart lineChart = (LineChart) view.findViewById(R.id.chart);

//        userDB();
//        battleDay();

        LineDataSet lineDataSet = new LineDataSet(dataValues(), "나");
        LineDataSet lineDataSetEnermy = new LineDataSet(dataValuesEnermy(), "적");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        dataSets.add(lineDataSetEnermy);

        // 나의 그래프 셋팅
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet.setColor(Color.parseColor("#000000"));
        lineDataSet.setLineWidth(3);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setDrawValues(false);
        lineDataSet.setFillColor(Color.BLACK);
        lineDataSet.setValueTextColor(Color.RED);
        lineDataSet.setFillFormatter((dataSet, dataProvider) -> Color.RED);

        // 적의 그래프 셋팅
        lineDataSetEnermy.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSetEnermy.setColor(Color.parseColor("#ff0000"));
        lineDataSetEnermy.setLineWidth(3);
        lineDataSetEnermy.setDrawCircles(false);
        lineDataSetEnermy.setDrawValues(false);

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);

        //X축 설정
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setTypeface(Typeface.DEFAULT_BOLD);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);

        //Y축 왼쪽
        YAxis yLAxis = lineChart.getAxisLeft();
        yLAxis.setTextColor(Color.BLACK);
        yLAxis.setTypeface(Typeface.DEFAULT_BOLD);
        yLAxis.setLabelCount(5);
        yLAxis.setAxisMaximum(5);
        yLAxis.setAxisMinimum(0);

        //Y축 오른쪽
        YAxis yRAxis = lineChart.getAxisRight();
        yRAxis.setDrawLabels(false);
        yRAxis.setDrawAxisLine(false);
        yRAxis.setDrawGridLines(false);

        // 왼쪽 하단 그래프 나타낼 목록
        Legend legend = lineChart.getLegend();
        legend.setEnabled(true);
        legend.setTextColor(Color.BLACK);
        legend.setTextSize(10);
        legend.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "dalmoori.ttf"));
        legend.setForm(Legend.LegendForm.LINE);
        legend.setFormSize(20);
        legend.setXEntrySpace(20);

        int color[] = new int[]{Color.BLACK, Color.RED};
        String[] Name = {"나", "적"};

        // 배열을 통해 나와 적의 색을 바꿀 수 있다.
        LegendEntry[] legendEntry = new LegendEntry[2];
        for (int i = 0; i < legendEntry.length; i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = color[i];
            entry.label = Name[i];
            legendEntry[i] = entry;
        }
        legend.setCustom(legendEntry);

        //오른쪽 하단 설명 글씨

        /*Description description = new Description();
        description.setText("스쿼트");
        description.setTextSize(20);
        description.setXOffset(20);
        description.setYOffset(20);
        description.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),"dalmoori.ttf"));*/

        //차트의 설정
        lineChart.setBackgroundColor(Color.parseColor("#ffffff"));
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setDrawGridBackground(true);
//        lineChart.setDescription(description);
        lineChart.setNoDataText("데이터가 없습니다.");
        lineChart.setDrawBorders(true);
        lineChart.setBorderColor(Color.BLACK);
        lineChart.setBorderWidth(5);
        lineChart.setNoDataTextColor(Color.parseColor("#000000"));
        lineChart.animateY(1000, Easing.EasingOption.EaseInCubic);
        lineChart.invalidate();

        return view;
    }

    //나의 그래프 데이터
    private ArrayList<Entry> dataValues() {
        my=((BattleRoom)getActivity()).deliverMy();
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(1, my.get(6)));
        entries.add(new Entry(2, my.get(5)));
        entries.add(new Entry(3, my.get(4)));
        entries.add(new Entry(4, my.get(3)));
        entries.add(new Entry(5, my.get(2)));
        entries.add(new Entry(6, my.get(1)));
        entries.add(new Entry(7, my.get(0)));
        return entries;
    }

    // 적의 그래프 데이터
    private ArrayList<Entry> dataValuesEnermy() {
        enemy=((BattleRoom)getActivity()).deliverEnemy();
        ArrayList<Entry> entriesEnemy = new ArrayList<>();
        entriesEnemy.add(new Entry(1, enemy.get(6)));
        entriesEnemy.add(new Entry(2, enemy.get(5)));
        entriesEnemy.add(new Entry(3, enemy.get(4)));
        entriesEnemy.add(new Entry(4, enemy.get(3)));
        entriesEnemy.add(new Entry(5, enemy.get(2)));
        entriesEnemy.add(new Entry(6, enemy.get(1)));
        entriesEnemy.add(new Entry(7, enemy.get(0)));
        return entriesEnemy;
    }


}