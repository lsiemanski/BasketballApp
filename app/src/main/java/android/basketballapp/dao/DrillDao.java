package android.basketballapp.dao;

import android.basketballapp.entity.Drill;
import android.basketballapp.entity.DrillAndCategory;
import android.basketballapp.entity.DrillAndSpots;
import android.basketballapp.entity.Spot;

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

    @Query("SELECT drills.*, categories.* FROM drills INNER JOIN categories ON drills.categoryId = categories.category_id")
    LiveData<List<DrillAndCategory>> getAllDrillsWithCategories();

    @Query("SELECT * FROM drills WHERE drill_id=:id")
    LiveData<DrillAndSpots> getDrillWithSpots(int id);

    @Query("SELECT * FROM drills WHERE drill_id=:id")
    Drill getDrill(int id);

//    @Query("SELECT * FROM spots WHERE drillId=:drillId")
//    List<Spot> getSpotsForDrill(int drillId);
}
