package android.basketballapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.basketballapp.adapter.TrainingSummaryListAdapter;
import android.basketballapp.utils.SpotLayout;
import android.basketballapp.utils.factory.DrillCourtViewLayoutFactory;
import android.basketballapp.utils.factory.SpotLayoutFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public abstract class TrainingActivity extends AppCompatActivity {
    protected SpotLayout[] spotLayouts;

    protected RecyclerView trainingSummaryRecyclerView;
    protected TrainingSummaryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        initView();
        this.setTitle(getIntent().getStringExtra("drillName"));

        adapter = new TrainingSummaryListAdapter(this);
        trainingSummaryRecyclerView.setAdapter(adapter);
        trainingSummaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void initView() {
        initDrillCourtView();
        initShotListView();
        initBottomButtonsView();
    }

    protected void initDrillCourtView() {
        LinearLayout subLayout = findViewById(R.id.sub_layout);
        View drillCourtView = getLayoutInflater().inflate(DrillCourtViewLayoutFactory.create(getIntent().getStringExtra("drillName")), subLayout, false);
        subLayout.addView(drillCourtView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        spotLayouts = SpotLayoutFactory.createSpotLayouts(getIntent().getStringExtra("drillName"), drillCourtView);
    }

    protected void initShotListView() {
        LinearLayout subLayout = findViewById(R.id.sub_layout);

        View shotListView = getLayoutInflater().inflate(R.layout.shot_list_container, subLayout, false);
        subLayout.addView(shotListView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        trainingSummaryRecyclerView = findViewById(R.id.training_summary_recycler_view);
    }

    protected abstract void initBottomButtonsView();
}
