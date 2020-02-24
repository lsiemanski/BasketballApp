package android.basketballapp.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="shots", foreignKeys = {
        @ForeignKey(entity=Training.class,
                    parentColumns = "training_id",
                    childColumns = "trainingId"),
        @ForeignKey(entity=Spot.class,
                    parentColumns = "spot_id",
                    childColumns = "spotId")})
public class Shot {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "shot_id")
    public int shotId;

    @NonNull
    @ColumnInfo(name = "trainingId")
    public int trainingId;

    @NonNull
    @ColumnInfo(name = "spotId")
    public int spotId;

    @NonNull
    @ColumnInfo(name="isMade")
    public boolean isMade;

    @Ignore
    public int madeFromSpot;

    @Ignore
    public int takenFromSpot;

    @Ignore
    public int madeTotal;

    @Ignore
    public int takenTotal;

    public Shot(int trainingId, int spotId, boolean isMade) {
        this.trainingId = trainingId;
        this.spotId = spotId;
        this.isMade = isMade;
    }
}
