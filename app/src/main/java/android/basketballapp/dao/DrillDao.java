package android.basketballapp.dao;

import android.basketballapp.entity.Drill;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DrillDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Drill drill);

    @Query("SELECT * FROM drills")
    LiveData<List<Drill>> getAllDrills();

    @Query("DELETE FROM drills")
    void deleteAll();
}
