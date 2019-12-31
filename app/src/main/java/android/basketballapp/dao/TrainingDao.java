package android.basketballapp.dao;

import android.basketballapp.entity.Drill;
import android.basketballapp.entity.Training;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TrainingDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Training training);

    @Query("SELECT * FROM trainings")
    LiveData<List<Training>> getAllDrills();

    @Query("DELETE FROM trainings")
    void deleteAll();
}
