package android.basketballapp.intentfactory;

import android.basketballapp.BasicDrillActivity;
import android.basketballapp.DrillWithPickerActivity;
import android.basketballapp.utils.DrillNames;
import android.content.Context;
import android.content.Intent;

public class DrillActivityIntentFactory {

    private DrillActivityIntentFactory() {}

    public static Intent create(Context context, String htmlFile, String drillName, int drillId, int playerId) {
        Intent drillActivityIntent;
        switch (drillName) {
            case DrillNames.FIVE_POSITION_3_POINT_SHOOTING:
                drillActivityIntent = new Intent(context, DrillWithPickerActivity.class);
                drillActivityIntent.putExtra("minPickerValue", 1);
                drillActivityIntent.putExtra("maxPickerValue", 50);
                break;
            case DrillNames.FREE_THROW_SHOOTING:
                drillActivityIntent = new Intent(context, DrillWithPickerActivity.class);
                drillActivityIntent.putExtra("minPickerValue", 1);
                drillActivityIntent.putExtra("maxPickerValue", 1000);
                break;
            case DrillNames.RAY_ALLEN_DRILL:
                drillActivityIntent = new Intent(context, BasicDrillActivity.class);
                break;
            case DrillNames.FORM_SHOOTING:
                drillActivityIntent = new Intent(context, BasicDrillActivity.class);
                break;
            case DrillNames.WARMUP_DRIBBLING:
                drillActivityIntent = new Intent(context, BasicDrillActivity.class);
                break;
            default:
                return null;
        }

        drillActivityIntent.putExtra("htmlFile", htmlFile);
        drillActivityIntent.putExtra("drillName", drillName);
        drillActivityIntent.putExtra("drillId", drillId);
        drillActivityIntent.putExtra("playerId", playerId);
        return drillActivityIntent;
    }
}
