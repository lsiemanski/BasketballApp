package android.basketballapp.utils.factory;

import android.basketballapp.TrainingExecutionActivity;
import android.basketballapp.utils.DrillNames;
import android.content.Context;
import android.content.Intent;

public class TrainingActivityIntentFactory {
    private TrainingActivityIntentFactory() {}

    public static Intent create(Context context, String drillName, int drillId, int playerId, int numberOfShots) {
        Intent drillActivityIntent;
        switch (drillName) {
            case DrillNames.FIVE_POSITION_3_POINT_SHOOTING:
                drillActivityIntent = new Intent(context, TrainingExecutionActivity.class);
                break;
            case DrillNames.FREE_THROW_SHOOTING:
                drillActivityIntent = new Intent(context, TrainingExecutionActivity.class);
                break;
            default:
                return null;
        }

        drillActivityIntent.putExtra("drillName", drillName);
        drillActivityIntent.putExtra("drillId", drillId);
        drillActivityIntent.putExtra("playerId", playerId);
        drillActivityIntent.putExtra("numberOfShots", numberOfShots);
        return drillActivityIntent;
    }
}
