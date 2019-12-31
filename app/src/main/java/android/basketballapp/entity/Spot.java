package android.basketballapp.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Comparator;

@Entity(tableName="spots", foreignKeys =
        @ForeignKey(entity = Drill.class,
                    parentColumns = "drill_id",
                    childColumns = "drillId"))
public class Spot {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "spot_id")
    public int spotId;

    @NonNull
    @ColumnInfo(name = "drillId")
    public int drillId;

    @NonNull
    @ColumnInfo(name="description")
    public String description;

    @NonNull
    @ColumnInfo(name="order")
    public int order;

    public Spot(int drillId, @NonNull String description, int order) {
        this.drillId = drillId;
        this.description = description;
        this.order = order;
    }

    public static class SortByOrder implements Comparator<Spot> {
        @Override
        public int compare(Spot spot1, Spot spot2) {
            return spot2.order - spot2.order;
        }
    }
}
