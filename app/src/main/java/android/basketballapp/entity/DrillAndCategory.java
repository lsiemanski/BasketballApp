package android.basketballapp.entity;

import androidx.room.Embedded;

public class DrillAndCategory {

    @Embedded
    public Drill drill;

    @Embedded
    public Category category;

    public DrillAndCategory(Drill drill, Category category) {
        this.drill = drill;
        this.category = category;
    }
}
