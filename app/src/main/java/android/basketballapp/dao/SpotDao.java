package android.basketballapp.dao;

import android.basketballapp.entity.Spot;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface SpotDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Spot spot);

    @Query("DELETE FROM spots")
    void deleteAll();
}
