package android.basketballapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.basketballapp.adapter.TrainingListAdapter;
import android.basketballapp.viewmodel.TrainingListViewModel;
import android.basketballapp.viewmodel.factory.TrainingListViewModelFactory;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TrainingListActivity extends AppCompatActivity {

    private TrainingListViewModel trainingListViewModel;
    private TrainingListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_list);

        Intent parentIntent = getIntent();
        int drillId = parentIntent.getIntExtra("drillId", 0);
        int playerId = parentIntent.getIntExtra("playerId", 0);
        String drillName = parentIntent.getStringExtra("drillName");

        TextView drillNameTextView = findViewById(R.id.drill_name);
        drillNameTextView.setText(drillName);

        RecyclerView recyclerView = findViewById(R.id.trainings_recycler_view);
        adapter = new TrainingListAdapter(this, drillId);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        trainingListViewModel = ViewModelProviders.of(this, new TrainingListViewModelFactory(this.getApplication(), drillId, playerId)).get(TrainingListViewModel.class);

        trainingListViewModel.getTrainings().observe(this, trainings -> adapter.setTrainings(trainings));

        Button okButton = findViewById(R.id.ok);
        okButton.setOnClickListener(v -> finish());

        Button progressChartButton = findViewById(R.id.progress_chart_button);
        progressChartButton.setOnClickListener(v -> {
            if(adapter.getItemCount() < 2) {
                Toast.makeText(getApplicationContext(), "Progress chart not available! You need to have at least 2 trainings to generate progress chart.", Toast.LENGTH_SHORT).show();
            } else {
                Intent startProgressChartActivity = new Intent(getApplicationContext(), ProgressChartActivity.class);
                startProgressChartActivity.putExtra("drillId", drillId);
                startProgressChartActivity.putExtra("playerId", playerId);
                startActivity(startProgressChartActivity);
            }
        });
    }
}
