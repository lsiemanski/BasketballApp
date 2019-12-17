package android.basketballapp.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName="spots", foreignKeys =
        @ForeignKey(entity = Drill.class,
                    parentColumns = "drill_id",
                    childColumns = "drillId"))
public class Spot {

    @PrimaryKey
    @ColumnInfo(name = "spot_id")
    public int spotId;

    @NonNull
    @ColumnInfo(name = "drillId")
    public int drillId;

    @NonNull
    @ColumnInfo(name="description")
    public String description;

    public Spot(int drillId, @NonNull String description) {
        this.drillId = drillId;
        this.description = description;
    }
}
