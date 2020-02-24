package android.basketballapp.dao;

import android.basketballapp.entity.Training;
import android.basketballapp.entity.TrainingAndShots;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TrainingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Training training);

    @Query("SELECT * FROM trainings")
    LiveData<List<Training>> getAllTrainings();

    @Query("DELETE FROM trainings")
    void deleteAll();

    @Query("SELECT * FROM trainings WHERE training_id=:id")
    LiveData<TrainingAndShots> getTrainingAndShots(int id);

    @Query("SELECT * FROM trainings WHERE drillId=:drillId AND playerId=:playerId ORDER BY trainingDate DESC")
    LiveData<List<Training>> getTrainings(int drillId, int playerId);
}
