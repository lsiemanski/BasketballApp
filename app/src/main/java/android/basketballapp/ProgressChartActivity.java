package android.basketballapp;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;

public class ProgressChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_chart);

        LineChart chart = findViewById(R.id.line_chart);

        chart.getAxisRight().setEnabled(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        chart.getAxisLeft().setXOffset(10f);
        chart.setMinOffset(10f);

        chart.getDescription().setEnabled(false);
        chart.setHighlightPerDragEnabled(false);
        //chart.setHighlightPerTapEnabled(false);

        chart.setDrawGridBackground(false);
        chart.setBorderWidth(3f);

        List<Entry> entries = new ArrayList<>();

        entries.add(new Entry(0, (float)22/50));
        entries.add(new Entry(1, (float)22/50));
        entries.add(new Entry(2, (float)21/50));
        entries.add(new Entry(3, (float)20/50));
        entries.add(new Entry(4, (float)23/50));
        entries.add(new Entry(5, (float)28/50));
        entries.add(new Entry(6, (float)21/50));
        entries.add(new Entry(7, (float)25/50));
        entries.add(new Entry(8, (float)23/50));
        entries.add(new Entry(9, (float)22/50));
        entries.add(new Entry(10, (float)24/50));
        entries.add(new Entry(11, (float)28/50));
        entries.add(new Entry(12, (float)29/50));
        entries.add(new Entry(13, (float)32/50));
        entries.add(new Entry(14, (float)24/50));
        entries.add(new Entry(15, (float)23/50));
        entries.add(new Entry(16, (float)28/50));
        entries.add(new Entry(17, (float)27/50));
        entries.add(new Entry(18, (float)25/50));
        entries.add(new Entry(19, (float)26/50));
        entries.add(new Entry(20, (float)33/50));
        entries.add(new Entry(21, (float)32/50));
        entries.add(new Entry(22, (float)31/50));
        entries.add(new Entry(23, (float)27/50));
        entries.add(new Entry(24, (float)32/50));
        entries.add(new Entry(25, (float)30/50));

        IndexAxisValueFormatter formatter = new IndexAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if(value == Math.round(value))
                    return getValues()[(int)value];
                return "";
            }
        };
        formatter.setValues(new String[] {"01-01-2020", "02-01-2020", "02-01-2020", "03-01-2020", "04-01-2020", "06-01-2020", "07-01-2020", "09-01-2020", "10-01-2020",
                "01-01-2020", "02-01-2020", "02-01-2020", "03-01-2020", "04-01-2020", "06-01-2020", "07-01-2020", "09-01-2020", "10-01-2020",
                "01-01-2020", "02-01-2020", "02-01-2020", "03-01-2020", "04-01-2020", "06-01-2020", "07-01-2020", "09-01-2020"});

        chart.getXAxis().setValueFormatter(formatter);
        chart.getXAxis().setLabelRotationAngle(60f);
        chart.getXAxis().setGranularity(1f);
        chart.setExtraOffsets(0f,0f,30f, 0f);

        LineDataSet dataSet = new LineDataSet(entries, "MAKE%");

        dataSet.setColor(Color.RED);
        dataSet.setCircleColor(Color.RED);
        //dataSet.setCircleHoleColor(Color.RED);
        dataSet.setCircleRadius(8f);
        dataSet.setLineWidth(3f);
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                System.out.println(e.getX() + " " + e.getY());
            }

            @Override
            public void onNothingSelected() {

            }
        });

        ValueFormatter valueFormatter = new DefaultValueFormatter(2);

        dataSet.setValueFormatter(valueFormatter);

        chart.setData(new LineData(dataSet));
    }

}
