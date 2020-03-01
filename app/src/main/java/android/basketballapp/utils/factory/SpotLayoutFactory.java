package android.basketballapp.utils.factory;

import android.basketballapp.R;
import android.basketballapp.utils.DrillNames;
import android.basketballapp.utils.SpotLayout;
import android.view.View;

public class SpotLayoutFactory {
    private SpotLayoutFactory() {}

    public static SpotLayout[] createSpotLayouts(String drillName, View view) {
        switch (drillName) {
            case (DrillNames.FIVE_POSITION_3_POINT_SHOOTING):
                SpotLayout spotLayout1 = new SpotLayout(view.findViewById(R.id.spot1), view.findViewById(R.id.tv1));
                SpotLayout spotLayout2 = new SpotLayout(view.findViewById(R.id.spot2), view.findViewById(R.id.tv2));
                SpotLayout spotLayout3 = new SpotLayout(view.findViewById(R.id.spot3), view.findViewById(R.id.tv3));
                SpotLayout spotLayout4 = new SpotLayout(view.findViewById(R.id.spot4), view.findViewById(R.id.tv4));
                SpotLayout spotLayout5 = new SpotLayout(view.findViewById(R.id.spot5), view.findViewById(R.id.tv5));
                return new SpotLayout[] {spotLayout1, spotLayout5, spotLayout4, spotLayout2, spotLayout3};
            case (DrillNames.FREE_THROW_SHOOTING):
                return new SpotLayout[] {new SpotLayout(view.findViewById(R.id.spot1), view.findViewById(R.id.tv1))};
            default:
                return null;
        }
    }
}
