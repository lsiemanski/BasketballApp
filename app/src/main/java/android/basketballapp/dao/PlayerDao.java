package android.basketballapp.dao;

import android.basketballapp.entity.Player;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Player player);

    @Query("DELETE FROM players")
    void deleteAll();

    @Query("SELECT * FROM players ORDER BY surname ASC, name ASC")
    LiveData<List<Player>> getAllPlayers();

    @Query("SELECT * FROM players WHERE player_id=:id")
    Player getPlayer(int id);
}
