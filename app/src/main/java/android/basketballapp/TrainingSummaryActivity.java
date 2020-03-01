package android.basketballapp;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import android.basketballapp.utils.factory.ColorDrawableMapFactory;
import android.basketballapp.viewmodel.TrainingSummaryViewModel;
import android.basketballapp.viewmodel.factory.TrainingSummaryViewModelFactory;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Map;

public class TrainingSummaryActivity extends TrainingActivity {

    private TrainingSummaryViewModel trainingSummaryViewModel;
    private Button okButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent parentIntent = getIntent();
        int trainingId = parentIntent.getIntExtra("trainingId", 0);
        int drillId = parentIntent.getIntExtra("drillId", 0);

        trainingSummaryViewModel = ViewModelProviders.of(this, new TrainingSummaryViewModelFactory(this.getApplication(), trainingId, drillId)).get(TrainingSummaryViewModel.class);

        LifecycleOwner lifecycleOwner = this;
        trainingSummaryViewModel.getDrillAndSpots().observe(lifecycleOwner, drillAndSpots ->
            trainingSummaryViewModel.getTrainingAndShots().observe(lifecycleOwner, trainingAndShots -> {
                trainingSummaryViewModel.countMakesAndMisses();
                adapter.setShots(trainingSummaryViewModel.getShotAndSpots());

                updateSpotLayouts();
            })
        );
    }

    @Override
    protected void initBottomButtonsView() {
        LinearLayout footer = findViewById(R.id.footer);

        View okButtonView = getLayoutInflater().inflate(R.layout.ok_button, footer, false);
        footer.addView(okButtonView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        okButton = findViewById(R.id.ok);
        okButton.setOnClickListener(v -> finish());
    }

    private void updateSpotLayouts() {
        for(int i = 0; i < spotLayouts.length; i++) {
            updateSpotLayout(i, trainingSummaryViewModel.getMadeFromSpot(i), trainingSummaryViewModel.getTakenFromSpot(i));
        }
    }

    private void updateSpotLayout(int index, int made, int taken) {
        spotLayouts[index].update(made, taken);
        spotLayouts[index].setColorDrawable(getColorDrawable((double) made/taken));
    }

    private Drawable getColorDrawable(double percent) {
        Map<Integer, Double> drawableMap = ColorDrawableMapFactory.getDrawableMap(getIntent().getStringExtra("drillName"));
        for(Map.Entry<Integer, Double> entry : drawableMap.entrySet()) {
            if(percent < entry.getValue())
                return getResources().getDrawable(entry.getKey(), getTheme());
        }

        return getResources().getDrawable(R.drawable.spot_green, getTheme());
    }

}
