package android.basketballapp.converter;

import android.basketballapp.entity.Player;

import androidx.room.TypeConverter;

import static android.basketballapp.entity.Player.Position.CENTER;
import static android.basketballapp.entity.Player.Position.POINT_GUARD;
import static android.basketballapp.entity.Player.Position.POWER_FORWARD;
import static android.basketballapp.entity.Player.Position.SHOOTING_GUARD;
import static android.basketballapp.entity.Player.Position.SMALL_FORWARD;

public class PositionConverter {

    @TypeConverter
    public static Player.Position toPosition(int code) {
        if(code == POINT_GUARD.getCode()) {
            return POINT_GUARD;
        } else if(code == SHOOTING_GUARD.getCode()) {
            return SHOOTING_GUARD;
        } else if(code == SMALL_FORWARD.getCode()) {
            return SMALL_FORWARD;
        } else if(code == POWER_FORWARD.getCode()) {
            return POWER_FORWARD;
        } else if(code == CENTER.getCode()) {
            return CENTER;
        } else {
            throw new IllegalArgumentException("Could not recognize position!");
        }
    }

    @TypeConverter
    public static int toInt(Player.Position position) {
        return position.getCode();
    }
}
