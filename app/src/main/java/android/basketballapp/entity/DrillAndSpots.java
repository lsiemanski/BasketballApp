package android.basketballapp.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class DrillAndSpots {

    @Embedded
    public Drill drill;

    @Relation(parentColumn = "drill_id", entityColumn = "drillId")
    public List<Spot> spots;
}
