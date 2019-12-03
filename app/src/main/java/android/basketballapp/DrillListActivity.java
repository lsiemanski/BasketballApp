package android.basketballapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.basketballapp.adapter.DrillListAdapter;
import android.basketballapp.entity.Drill;
import android.basketballapp.viewmodel.DrillViewModel;
import android.os.Bundle;

import java.util.List;

public class DrillListActivity extends AppCompatActivity {

    private DrillViewModel drillViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drill_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final DrillListAdapter adapter = new DrillListAdapter(this, getIntent().getIntExtra("playerId", 0));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        drillViewModel = ViewModelProviders.of(this).get(DrillViewModel.class);

        drillViewModel.getAllDrills().observe(this, new Observer<List<Drill>>() {
            @Override
            public void onChanged(List<Drill> drills) { adapter.setDrills(drills); }
        });
    }
}
