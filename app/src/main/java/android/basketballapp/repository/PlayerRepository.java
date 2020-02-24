package android.basketballapp.repository;

import android.app.Application;
import android.basketballapp.dao.PlayerDao;
import android.basketballapp.database.BasketballAppRoomDatabase;
import android.basketballapp.entity.Player;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PlayerRepository {
    private PlayerDao playerDao;
    private LiveData<List<Player>> allPlayers;

    public PlayerRepository(Application application) {
        BasketballAppRoomDatabase db = BasketballAppRoomDatabase.getDatabase(application);
        playerDao = db.playerDao();
        allPlayers = playerDao.getAllPlayers();
    }

    public LiveData<List<Player>> getAllPlayers() {
        return allPlayers;
    }

    public void insert(Player player) {
        playerDao.insert(player);
    }
}
