package android.basketballapp;

import androidx.appcompat.app.AppCompatActivity;

import android.basketballapp.entity.Shot;
import android.basketballapp.entity.ShotAndSpot;
import android.basketballapp.entity.Spot;
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
    private int current = 0;
    private TransitionDrawable[] order;
    private TableLayout reportTable;
    private int totalMakes = 0;
    private int totalShots = 0;
    private TextView[] spotResults;
    private int numberOfShots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        Spot[] spots = new Spot[]{
                new Spot(1, "Left corner"),
                new Spot(1, "Right corner"),
                new Spot(1, "Right 45"),
                new Spot(1, "Left 45"),
                new Spot(1, "Top")
        };

        numberOfShots = getIntent().getIntExtra("numberOfShots", 0) * spots.length;

        Map<Spot, int[]> spotShots = new HashMap<>();
        spotShots.put(spots[0], new int[]{0,0});
        spotShots.put(spots[1], new int[]{0,0});
        spotShots.put(spots[2], new int[]{0,0});
        spotShots.put(spots[3], new int[]{0,0});
        spotShots.put(spots[4], new int[]{0,0});

        ImageView spot1 = findViewById(R.id.spot1);
        ImageView spot2 = findViewById(R.id.spot2);
        ImageView spot3 = findViewById(R.id.spot3);
        ImageView spot4 = findViewById(R.id.spot4);
        ImageView spot5 = findViewById(R.id.spot5);
        TransitionDrawable transition1 = (TransitionDrawable) spot1.getDrawable();
        TransitionDrawable transition2 = (TransitionDrawable) spot2.getDrawable();
        TransitionDrawable transition3 = (TransitionDrawable) spot3.getDrawable();
        TransitionDrawable transition4 = (TransitionDrawable) spot4.getDrawable();
        TransitionDrawable transition5 = (TransitionDrawable) spot5.getDrawable();

        order = new TransitionDrawable[]{transition1, transition5, transition4, transition2, transition3};
        order[current].startTransition(TRANSITION_TIME);

        TextView tv1 = findViewById(R.id.tv1);
        TextView tv2 = findViewById(R.id.tv2);
        TextView tv3 = findViewById(R.id.tv3);
        TextView tv4 = findViewById(R.id.tv4);
        TextView tv5 = findViewById(R.id.tv5);

        spotResults = new TextView[]{tv1, tv5, tv4, tv2, tv3};

        reportTable = findViewById(R.id.report_table);
        List<ShotAndSpot> shots = new ArrayList<ShotAndSpot>();

        Button makeButton = findViewById(R.id.make);
        Button missButton = findViewById(R.id.miss);

        makeButton.setOnClickListener((v -> {
            if(totalShots < numberOfShots) {
                Spot currentSpot = spots[current];
                int spotMakes = spotShots.get(currentSpot)[0];
                int spotTotal = spotShots.get(currentSpot)[1];
                spotShots.put(currentSpot, new int[]{spotMakes + 1, spotTotal + 1});

                totalMakes += 1;
                totalShots += 1;

                ShotAndSpot shotAndSpot = new ShotAndSpot(new Shot(1, 1, true), currentSpot);
                shotAndSpot.shot.madeFromSpot = spotShots.get(shotAndSpot.spot)[0];
                shotAndSpot.shot.takenFromSpot = spotShots.get(shotAndSpot.spot)[1];
                shotAndSpot.shot.madeTotal = totalMakes;
                shotAndSpot.shot.takenTotal = totalShots;

                shots.add(shotAndSpot);

                updateTable(shotAndSpot);
                updateTextViews(shotAndSpot);
                updateOrder();
            }
        }));

        missButton.setOnClickListener(v -> {
            if(totalShots < numberOfShots) {
                Spot currentSpot = spots[current];
                int spotMakes = spotShots.get(currentSpot)[0];
                int spotTotal = spotShots.get(currentSpot)[1];
                spotShots.put(currentSpot, new int[]{spotMakes, spotTotal + 1});

                totalShots += 1;

                ShotAndSpot shotAndSpot = new ShotAndSpot(new Shot(1, 1, false), currentSpot);
                shotAndSpot.shot.madeFromSpot = spotShots.get(shotAndSpot.spot)[0];
                shotAndSpot.shot.takenFromSpot = spotShots.get(shotAndSpot.spot)[1];
                shotAndSpot.shot.madeTotal = totalMakes;
                shotAndSpot.shot.takenTotal = totalShots;

                shots.add(shotAndSpot);

                updateTable(shotAndSpot);
                updateTextViews(shotAndSpot);
                updateOrder();
            }
        });

    }

    private void updateOrder() {
        order[current].reverseTransition(TRANSITION_TIME);
        current = (current + 1) % order.length;
        order[current].startTransition(TRANSITION_TIME);
    }

    private void updateTextViews(ShotAndSpot shotAndSpot) {
        spotResults[current].setText(shotAndSpot.shot.madeFromSpot + "/" + shotAndSpot.shot.takenFromSpot);
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
