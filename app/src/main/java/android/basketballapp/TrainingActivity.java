package android.basketballapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.basketballapp.entity.DrillAndSpots;
import android.basketballapp.entity.Shot;
import android.basketballapp.entity.ShotAndSpot;
import android.basketballapp.entity.Spot;
import android.basketballapp.entity.TrainingAndShots;
import android.basketballapp.viewmodel.TrainingViewModel;
import android.basketballapp.viewmodel.factory.TrainingViewModelFactory;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.graphics.drawable.shapes.Shape;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainingActivity extends AppCompatActivity {

    private static final int TRANSITION_TIME = 200;

    private TableLayout reportTable;
    private SpotLayout[] spotLayouts;
    private Button makeButton, missButton;

    private TrainingViewModel trainingViewModel;
    TrainingAndShots trainingAndShots;

    private class SpotLayout {
        ImageView imageView;
        TextView textView;

        SpotLayout(ImageView imageView, TextView textView) {
            this.imageView = imageView;
            this.textView = textView;
        }

        TransitionDrawable getDrawable() {
            return (TransitionDrawable) imageView.getDrawable();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        Intent parentIntent = getIntent();
        int drillId = parentIntent.getIntExtra("drillId", 0);
        int playerId = parentIntent.getIntExtra("playerId", 0);
        int numberOfShots = parentIntent.getIntExtra("numberOfShots", 0);

        initViewModel(getIntent(), drillId);
        initView();

        trainingViewModel.getDrillAndSpots().observe(this, new Observer<DrillAndSpots>() {
            @Override
            public void onChanged(DrillAndSpots drillAndSpots) {
                trainingViewModel.initTraining(drillId, playerId, numberOfShots);
            }
        });
    }

    private void initViewModel(Intent parentIntent, int drillId) {
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

        reportTable = findViewById(R.id.report_table);

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
        updateTable(shotAndSpot);
        updateTextViews(shotAndSpot, current);
        updateSpotLayouts(current, next);

        if(trainingViewModel.isOver())
            trainingViewModel.saveTrainingData();

    }

    private void updateSpotLayouts(int current, int next) {
        spotLayouts[current].getDrawable().reverseTransition(TRANSITION_TIME);
        spotLayouts[next].getDrawable().startTransition(TRANSITION_TIME);
    }

    private void updateTextViews(ShotAndSpot shotAndSpot, int current) {
        spotLayouts[current].textView.setText(shotAndSpot.shot.madeFromSpot + "/" + shotAndSpot.shot.takenFromSpot);
    }

    private void updateTable(ShotAndSpot shotAndSpot) {
        LayoutInflater inflater = getLayoutInflater();
        TableRow row = (TableRow) inflater.inflate(R.layout.shot_table_row, null);
        TextView positionTextView = row.findViewById(R.id.shot_table_position);
        TextView resultTextView = row.findViewById(R.id.shot_table_result);
        TextView spotTextView = row.findViewById(R.id.shot_table_spot);
        TextView totalTextView = row.findViewById(R.id.shot_table_total);
        positionTextView.setText(shotAndSpot.spot.description);
        if(shotAndSpot.shot.isMade) {
            resultTextView.setText("MAKE");
            resultTextView.setTextColor(getResources().getColor(R.color.green_spot_dark, getTheme()));
        } else {
            resultTextView.setText("MISS");
            resultTextView.setTextColor(getResources().getColor(R.color.red_spot_dark, getTheme()));
        }

        spotTextView.setText(shotAndSpot.shot.madeFromSpot + "/" + shotAndSpot.shot.takenFromSpot);
        totalTextView.setText(shotAndSpot.shot.madeTotal + "/" + shotAndSpot.shot.takenTotal);
        reportTable.addView(row, 0);
    }
}
