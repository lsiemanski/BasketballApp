package android.basketballapp.entity;

import android.basketballapp.converter.DateConverter;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(tableName="trainings", foreignKeys = {
        @ForeignKey(entity = Drill.class,
                parentColumns = "drill_id",
                childColumns = "drillId"),
        @ForeignKey(entity = Player.class,
                parentColumns = "player_id",
                childColumns = "playerId")
})
public class Training {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "training_id")
    public int trainingId;

    @NonNull
    @ColumnInfo(name = "drillId")
    public int drillId;

    @NonNull
    @ColumnInfo(name = "playerId")
    public int playerId;

    @ColumnInfo(name = "totalMakes")
    public int totalMakes;

    @ColumnInfo(name = "totalShots")
    public int totalShots;

    @TypeConverters(DateConverter.class)
    @ColumnInfo(name="trainingDate")
    public Date date;

    public Training(int drillId, int playerId) {
        this.drillId = drillId;
        this.playerId = playerId;
        date = new Date();
    }
}
