package android.basketballapp.utils;

public class DrillUtils {
    public static boolean canOpenTrainingList(String drillName) {
        return drillName.equals(DrillNames.FIVE_POSITION_3_POINT_SHOOTING) || drillName.equals(DrillNames.FREE_THROW_SHOOTING);
    }
}
