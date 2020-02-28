package android.basketballapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.basketballapp.adapter.TrainingSummaryListAdapter;
import android.basketballapp.entity.Training;
import android.basketballapp.viewmodel.TrainingListViewModel;
import android.basketballapp.viewmodel.TrainingSummaryViewModel;
import android.basketballapp.viewmodel.factory.TrainingListViewModelFactory;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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
    TrainingSummaryListAdapter adapter;
    TableLayout tableLayout;
    RecyclerView recyclerView;
    TextView noDataTextView;
    List<Training> trainingList;
    Application application;
    FragmentActivity fragmentActivity;
    TrainingSummaryViewModel trainingSummaryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_chart);

        fragmentActivity = this;
        application = this.getApplication();
        recyclerView = findViewById(R.id.training_summary_recycler_view);
        noDataTextView = findViewById(R.id.no_data_text_view);
        tableLayout = findViewById(R.id.header_table);

        Button okButton = findViewById(R.id.ok);
        okButton.setOnClickListener(v -> finish());

        adapter = new TrainingSummaryListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent parentIntent = getIntent();
        int drillId = parentIntent.getIntExtra("drillId", 0);
        int playerId = parentIntent.getIntExtra("playerId", 0);

        LineChart chart = findViewById(R.id.line_chart);

        trainingListViewModel = ViewModelProviders.of(this,
                new TrainingListViewModelFactory(this.getApplication(), drillId, playerId))
                .get(TrainingListViewModel.class);

        trainingListViewModel.getTrainings().observe(this, trainings ->  {
            trainingList = trainings;
            setTrainings(chart, trainings);
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
                noDataTextView.setVisibility(View.GONE);
                tableLayout.setVisibility(View.VISIBLE);

                int entryX = (int)e.getX();
                Training training = trainingList.get(entryX);

                trainingSummaryViewModel = new TrainingSummaryViewModel(application, training.trainingId, drillId);
                trainingSummaryViewModel.getDrillAndSpots().observe(fragmentActivity, drillAndSpots ->
                    trainingSummaryViewModel.getTrainingAndShots().observe(fragmentActivity, trainingAndShots -> {
                        trainingSummaryViewModel.countMakesAndMisses();
                        adapter.setShots(trainingSummaryViewModel.getShotAndSpots());
                    })
                );
            }

            @Override
            public void onNothingSelected() {
                noDataTextView.setVisibility(View.VISIBLE);
                tableLayout.setVisibility(View.GONE);
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
                if(value >= 0 && value == Math.round(value))
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
        dataSet.setValueTextSize(12f);

        ValueFormatter valueFormatter = new DefaultValueFormatter(2);
        dataSet.setValueFormatter(valueFormatter);
    }

}
