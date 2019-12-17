package android.basketballapp.entity;

import androidx.room.Embedded;

public class ShotAndSpot {

    @Embedded
    public Shot shot;

    @Embedded
    public Spot spot;

    public ShotAndSpot(Shot shot, Spot spot) {
        this.shot = shot;
        this.spot = spot;
    }
}
