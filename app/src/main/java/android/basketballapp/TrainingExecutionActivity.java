package android.basketballapp;

import androidx.lifecycle.ViewModelProviders;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.basketballapp.entity.ShotAndSpot;
import android.basketballapp.viewmodel.TrainingViewModel;
import android.basketballapp.viewmodel.factory.TrainingViewModelFactory;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class TrainingExecutionActivity extends TrainingActivity {

    private static final int TRANSITION_TIME = 200;

    private Button makeButton, missButton;
    private TrainingViewModel trainingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent parentIntent = getIntent();
        int drillId = parentIntent.getIntExtra("drillId", 0);
        int playerId = parentIntent.getIntExtra("playerId", 0);
        int numberOfShots = parentIntent.getIntExtra("numberOfShots", 0);

        trainingViewModel = ViewModelProviders.of(this, new TrainingViewModelFactory(this.getApplication(), drillId)).get(TrainingViewModel.class);

        trainingViewModel.getDrillAndSpots().observe(this, drillAndSpots ->
                trainingViewModel.initTraining(drillId, playerId, numberOfShots)
        );
    }

    @Override
    protected void initBottomButtonsView() {
        LinearLayout footer = findViewById(R.id.footer);

        View makeMissButtonsView = getLayoutInflater().inflate(R.layout.make_miss_buttons, footer, false);
        footer.addView(makeMissButtonsView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

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
        spotLayouts[current].reverseTransition(TRANSITION_TIME);
        spotLayouts[next].startTransition(TRANSITION_TIME);
    }

    private void updateTextViews(ShotAndSpot shotAndSpot, int current) {
        spotLayouts[current].update(shotAndSpot.shot.madeFromSpot, shotAndSpot.shot.takenFromSpot);
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
                trainingSummaryActivityStartIntent.putExtra("drillName", getIntent().getStringExtra("drillName"));
                activity.startActivity(trainingSummaryActivityStartIntent);
                activity.finish();
            }
        };

        asyncTask.execute();
    }
}
