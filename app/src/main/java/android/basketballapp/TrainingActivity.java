package android.basketballapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.basketballapp.adapter.TrainingSummaryListAdapter;
import android.basketballapp.entity.ShotAndSpot;
import android.basketballapp.utils.SpotLayout;
import android.basketballapp.viewmodel.TrainingViewModel;
import android.basketballapp.viewmodel.factory.TrainingViewModelFactory;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

public class TrainingActivity extends AppCompatActivity {

    private static final int TRANSITION_TIME = 200;

    private SpotLayout[] spotLayouts;
    private Button makeButton, missButton;
    private RecyclerView trainingSummaryRecyclerView;
    private TrainingSummaryListAdapter adapter;

    private TrainingViewModel trainingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        Intent parentIntent = getIntent();
        int drillId = parentIntent.getIntExtra("drillId", 0);
        int playerId = parentIntent.getIntExtra("playerId", 0);
        int numberOfShots = parentIntent.getIntExtra("numberOfShots", 0);

        initViewModel(drillId);
        initView();

        adapter = new TrainingSummaryListAdapter(this);
        trainingSummaryRecyclerView.setAdapter(adapter);
        trainingSummaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        trainingViewModel.getDrillAndSpots().observe(this, drillAndSpots ->
            trainingViewModel.initTraining(drillId, playerId, numberOfShots)
        );
    }

    private void initViewModel(int drillId) {
        trainingViewModel = ViewModelProviders.of(this, new TrainingViewModelFactory(this.getApplication(), drillId)).get(TrainingViewModel.class);
    }

    private void initView() {
        SpotLayout spotLayout1 = new SpotLayout(findViewById(R.id.spot1), findViewById(R.id.tv1));
        SpotLayout spotLayout2 = new SpotLayout(findViewById(R.id.spot2), findViewById(R.id.tv2));
        SpotLayout spotLayout3 = new SpotLayout(findViewById(R.id.spot3), findViewById(R.id.tv3));
        SpotLayout spotLayout4 = new SpotLayout(findViewById(R.id.spot4), findViewById(R.id.tv4));
        SpotLayout spotLayout5 = new SpotLayout(findViewById(R.id.spot5), findViewById(R.id.tv5));

        spotLayouts = new SpotLayout[] {spotLayout1, spotLayout5, spotLayout4, spotLayout2, spotLayout3};
        spotLayouts[0].getDrawable().startTransition(TRANSITION_TIME);

        trainingSummaryRecyclerView = findViewById(R.id.training_summary_recycler_view);

        makeButton = findViewById(R.id.make);
        missButton = findViewById(R.id.miss);

        makeButton.setOnClickListener((v -> {
            if(!trainingViewModel.isOver())
                updateView(trainingViewModel.addShotMade(), trainingViewModel.getPrevious(), trainingViewModel.getCurrent());
        }));

        missButton.setOnClickListener(v -> {
            if(!trainingViewModel.isOver())
                updateView(trainingViewModel.addShotMissed(), trainingViewModel.getPrevious(), trainingViewModel.getCurrent());
        });
    }

    private void updateView(ShotAndSpot shotAndSpot, int current, int next) {
        if(trainingViewModel.isOver())
            saveAndStartSummary();

        adapter.addShot(shotAndSpot);
        updateTextViews(shotAndSpot, current);
        updateSpotLayouts(current, next);
    }

    private void updateSpotLayouts(int current, int next) {
        spotLayouts[current].getDrawable().reverseTransition(TRANSITION_TIME);
        spotLayouts[next].getDrawable().startTransition(TRANSITION_TIME);
    }

    private void updateTextViews(ShotAndSpot shotAndSpot, int current) {
        spotLayouts[current].textView.setText(shotAndSpot.shot.madeFromSpot + "/" + shotAndSpot.shot.takenFromSpot);
    }

    @Override
    public void onBackPressed() {
        if(trainingViewModel.hasAnyData()) {
            saveAndStartSummary();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    private void saveAndStartSummary() {
        Activity activity = this;

        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, Void> asyncTask = new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                trainingViewModel.saveTrainingData();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Intent trainingSummaryActivityStartIntent = new Intent(getApplicationContext(), TrainingSummaryActivity.class);
                trainingSummaryActivityStartIntent.putExtra("trainingId", trainingViewModel.getTrainingId());
                trainingSummaryActivityStartIntent.putExtra("drillId", trainingViewModel.getDrillId());
                activity.startActivity(trainingSummaryActivityStartIntent);
                activity.finish();
            }
        };

        asyncTask.execute();
    }


}
