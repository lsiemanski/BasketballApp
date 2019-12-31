package android.basketballapp.dao;

import android.basketballapp.entity.Shot;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface ShotDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Shot shot);
}
