package android.basketballapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.basketballapp.entity.Training;
import android.basketballapp.viewmodel.TrainingListViewModel;
import android.basketballapp.viewmodel.factory.TrainingListViewModelFactory;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgressChartActivity extends AppCompatActivity {

    TrainingListViewModel trainingListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_chart);

        Intent parentIntent = getIntent();
        int drillId = parentIntent.getIntExtra("drillId", 0);
        int playerId = parentIntent.getIntExtra("playerId", 0);

        LineChart chart = findViewById(R.id.line_chart);

        trainingListViewModel = ViewModelProviders.of(this, new TrainingListViewModelFactory(this.getApplication(), drillId, playerId)).get(TrainingListViewModel.class);

        trainingListViewModel.getTrainings().observe(this, new Observer<List<Training>>() {
            @Override
            public void onChanged(List<Training> trainings) {
                setTrainings(chart, trainings);
            }
        });

        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        chart.getAxisLeft().setXOffset(10f);
        chart.setMinOffset(10f);

        chart.getDescription().setEnabled(false);
        chart.setHighlightPerDragEnabled(false);

        chart.setDrawGridBackground(false);
        chart.setBorderWidth(3f);

        chart.getXAxis().setLabelRotationAngle(60f);
        chart.getXAxis().setGranularity(1f);
        chart.setExtraOffsets(0f,0f,30f, 0f);

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                System.out.println(e.getX() + " " + e.getY());
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void setTrainings(LineChart chart, List<Training> trainings) {
        Collections.reverse(trainings);
        List<Entry> entries = new ArrayList<>();
        String[] dates = new String[trainings.size()];

        for(int i = 0; i < trainings.size(); i++) {
            Training training = trainings.get(i);
            entries.add(new Entry(i, (float)training.totalMakes/training.totalShots));
            dates[i] = (new SimpleDateFormat("dd-MM-yy").format(training.date));
        }

        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if(value == Math.round(value))
                    return getValues()[(int)value];
                return "";
            }
        };
        formatter.setValues(dates);
        chart.getXAxis().setValueFormatter(formatter);

        LineDataSet dataSet = new LineDataSet(entries, "MAKE%");
        configureDataSet(dataSet);

        chart.setData(new LineData(dataSet));
    }

    private void configureDataSet(LineDataSet dataSet) {
        dataSet.setColor(Color.RED);
        dataSet.setCircleColor(Color.RED);
        dataSet.setCircleRadius(8f);
        dataSet.setLineWidth(3f);

        ValueFormatter valueFormatter = new DefaultValueFormatter(2);
        dataSet.setValueFormatter(valueFormatter);
    }

}
