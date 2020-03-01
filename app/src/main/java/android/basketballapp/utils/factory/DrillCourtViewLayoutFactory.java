package android.basketballapp.utils.factory;

import android.basketballapp.R;
import android.basketballapp.utils.DrillNames;

public class DrillCourtViewLayoutFactory {
    private DrillCourtViewLayoutFactory() {}

    public static int create(String drillName) {
        switch (drillName) {
            case (DrillNames.FIVE_POSITION_3_POINT_SHOOTING):
                return R.layout.drill_5_position_shooting;
            case (DrillNames.FREE_THROW_SHOOTING):
                return R.layout.drill_free_throw_shooting;
            default:
                return 0;
        }
    }
}
