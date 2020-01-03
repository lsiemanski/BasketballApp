package android.basketballapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.basketballapp.adapter.TrainingSummaryListAdapter;
import android.basketballapp.entity.DrillAndSpots;
import android.basketballapp.entity.ShotAndSpot;
import android.basketballapp.entity.TrainingAndShots;
import android.basketballapp.viewmodel.TrainingSummaryViewModel;
import android.basketballapp.viewmodel.factory.TrainingSummaryViewModelFactory;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class TrainingSummaryActivity extends AppCompatActivity {

    private TrainingSummaryViewModel trainingSummaryViewModel;

    private SpotLayout[] spotLayouts;
    private Button okButton;
    private RecyclerView trainingSummaryRecyclerView;

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
        setContentView(R.layout.activity_training_summary);

        Intent parentIntent = getIntent();
        int trainingId = parentIntent.getIntExtra("trainingId", 0);
        int drillId = parentIntent.getIntExtra("drillId", 0);

        initView();
        final TrainingSummaryListAdapter adapter = new TrainingSummaryListAdapter(this);
        trainingSummaryRecyclerView.setAdapter(adapter);
        trainingSummaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        trainingSummaryViewModel = ViewModelProviders.of(this, new TrainingSummaryViewModelFactory(this.getApplication(), trainingId, drillId)).get(TrainingSummaryViewModel.class);

        LifecycleOwner lifecycleOwner = this;
        trainingSummaryViewModel.getDrillAndSpots().observe(lifecycleOwner, new Observer<DrillAndSpots>() {
            @Override
            public void onChanged(DrillAndSpots drillAndSpots) {
                trainingSummaryViewModel.getTrainingAndShots().observe(lifecycleOwner, new Observer<TrainingAndShots>() {
                    @Override
                    public void onChanged(TrainingAndShots trainingAndShots) {
                        trainingSummaryViewModel.countMakesAndMisses();
                        adapter.setShots(trainingSummaryViewModel.getShotAndSpots());

                        updateSpotLayouts();
                    }
                });
            }
        });
    }

    private void initView() {
        SpotLayout spotLayout1 = new SpotLayout(findViewById(R.id.spot1), findViewById(R.id.tv1));
        SpotLayout spotLayout2 = new SpotLayout(findViewById(R.id.spot2), findViewById(R.id.tv2));
        SpotLayout spotLayout3 = new SpotLayout(findViewById(R.id.spot3), findViewById(R.id.tv3));
        SpotLayout spotLayout4 = new SpotLayout(findViewById(R.id.spot4), findViewById(R.id.tv4));
        SpotLayout spotLayout5 = new SpotLayout(findViewById(R.id.spot5), findViewById(R.id.tv5));

        spotLayouts = new SpotLayout[] {spotLayout1, spotLayout5, spotLayout4, spotLayout2, spotLayout3};

        trainingSummaryRecyclerView = findViewById(R.id.training_summary_recycler_view);

        okButton = findViewById(R.id.ok);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updateSpotLayouts() {
        for(int i = 0; i < spotLayouts.length; i++) {
            updateSpotLayout(i, trainingSummaryViewModel.getMadeFromSpot(i), trainingSummaryViewModel.getTakenFromSpot(i));
        }
    }

    private void updateSpotLayout(int index, int made, int taken) {
        spotLayouts[index].textView.setText(made + "/" + taken);
    }

}
