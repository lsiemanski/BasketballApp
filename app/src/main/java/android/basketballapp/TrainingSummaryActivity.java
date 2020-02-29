package android.basketballapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.basketballapp.adapter.TrainingSummaryListAdapter;
import android.basketballapp.utils.SpotLayout;
import android.basketballapp.viewmodel.TrainingSummaryViewModel;
import android.basketballapp.viewmodel.factory.TrainingSummaryViewModelFactory;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;

public class TrainingSummaryActivity extends AppCompatActivity {

    private TrainingSummaryViewModel trainingSummaryViewModel;

    private SpotLayout[] spotLayouts;
    private Button okButton;
    private RecyclerView trainingSummaryRecyclerView;

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
        trainingSummaryViewModel.getDrillAndSpots().observe(lifecycleOwner, drillAndSpots -> {
            trainingSummaryViewModel.getTrainingAndShots().observe(lifecycleOwner, trainingAndShots -> {
                trainingSummaryViewModel.countMakesAndMisses();
                adapter.setShots(trainingSummaryViewModel.getShotAndSpots());

                updateSpotLayouts();
            });
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

        okButton.setOnClickListener(v -> finish());
    }

    private void updateSpotLayouts() {
        for(int i = 0; i < spotLayouts.length; i++) {
            updateSpotLayout(i, trainingSummaryViewModel.getMadeFromSpot(i), trainingSummaryViewModel.getTakenFromSpot(i));
        }
    }

    private void updateSpotLayout(int index, int made, int taken) {
        spotLayouts[index].textView.setText(made + "/" + taken);
        spotLayouts[index].imageView.setImageDrawable(getColorDrawable((float) made/taken));
    }

    private Drawable getColorDrawable(float percent) {
        Resources resources = getResources();
        if(percent <= 0.21)
            return resources.getDrawable(R.drawable.spot_red, getTheme());
        if(percent <= 0.31)
            return resources.getDrawable(R.drawable.spot_yellow, getTheme());
        if(percent <= 0.41)
            return resources.getDrawable(R.drawable.spot_light_green, getTheme());
        return resources.getDrawable(R.drawable.spot_green, getTheme());
    }

}
