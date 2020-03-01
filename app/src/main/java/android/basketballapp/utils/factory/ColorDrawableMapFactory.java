package android.basketballapp.utils.factory;

import android.basketballapp.R;
import android.basketballapp.utils.DrillNames;

import java.util.LinkedHashMap;
import java.util.Map;

public class ColorDrawableMapFactory {
    private ColorDrawableMapFactory() {}

    public static Map<Integer, Double> getDrawableMap(String drillName) {
        Map<Integer, Double> drawableMap = new LinkedHashMap<>();
        switch (drillName) {
            case DrillNames.FIVE_POSITION_3_POINT_SHOOTING:
                drawableMap.put(R.drawable.spot_red, 0.2);
                drawableMap.put(R.drawable.spot_yellow, 0.3);
                drawableMap.put(R.drawable.spot_light_green, 0.4);
                break;
            case DrillNames.FREE_THROW_SHOOTING:
                drawableMap.put(R.drawable.spot_red, 0.6);
                drawableMap.put(R.drawable.spot_yellow, 0.7);
                drawableMap.put(R.drawable.spot_light_green, 0.75);
                break;
            default:
                return null;
        }
        return drawableMap;
    }
}
