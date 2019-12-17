package android.basketballapp.entity;

import android.basketballapp.converter.DateConverter;
import android.basketballapp.converter.PositionConverter;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Comparator;
import java.util.Date;

@Entity(tableName="players")
public class Player {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "player_id")
    public int playerId;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;

    @NonNull
    @ColumnInfo(name = "surname")
    public String surname;

    @TypeConverters(PositionConverter.class)
    @ColumnInfo(name = "position")
    public Position position;

    @ColumnInfo(name = "height")
    public int height;

    @ColumnInfo(name="image")
    public String image;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name="joined")
    public Date joined;

    public static SortByName SORT_BY_NAME_COMPARATOR = new SortByName();
    public static SortByPosition SORT_BY_POSITION_COMPARATOR = new SortByPosition();
    public static SortByDateJoined SORT_BY_DATE_JOINED_COMPARATOR = new SortByDateJoined();

    public Player(@NonNull String name, @NonNull String surname, @NonNull Position position, @NonNull int height) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.height = height;
        joined = new Date();
    }

    public enum Position {
        POINT_GUARD(1, "Point guard"),
        SHOOTING_GUARD(2, "Shooting guard"),
        SMALL_FORWARD(3, "Small forward"),
        POWER_FORWARD(4, "Power forward"),
        CENTER(5, "Center");

        private int code;
        private String description;

        Position(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static Position fromDescription(String description) {
            for(Position pos : values()) {
                if(pos.getDescription().equals(description))
                    return pos;
            }
            return null;
        }
    }

    public int getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Position getPosition() { return position; }

    public int getHeight() {
        return height;
    }

    public String getImage() { return image; }

    @Override
    public String toString() {
        return "Player{" +
                "playerId=" + playerId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", position=" + position +
                ", height=" + height +
                ", image='" + image + '\'' +
                ", joined=" + joined +
                '}';
    }

    static class SortByName implements Comparator<Player> {
        @Override
        public int compare(Player player1, Player player2) {
            return player1.surname.compareTo(player2.surname);
        }
    }

    static class SortByPosition implements Comparator<Player> {
        @Override
        public int compare(Player player1, Player player2) {
            return player1.position.getCode() - player2.position.getCode();
        }
    }

    static class SortByDateJoined implements Comparator<Player> {
        @Override
        public int compare(Player player1, Player player2) {
            return (int)(player1.joined.getTime() - player2.joined.getTime());
        }
    }
}
