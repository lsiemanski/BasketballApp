package android.basketballapp.utils;

public class StringUtils {
    public static String trimLeadingZeros(String s) {
        return s.replaceFirst("^0+(?!$)", "");
    }

    public static boolean hasLeadingZeros(String s) {
        return s.startsWith("0");
    }
}
