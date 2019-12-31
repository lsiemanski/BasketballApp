package android.basketballapp.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TrainingAndShots {

    @Embedded
    public Training training;

    @Relation(parentColumn = "training_id", entityColumn = "trainingId")
    public List<Shot> shots;
}
