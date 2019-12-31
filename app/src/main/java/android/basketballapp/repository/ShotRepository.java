package android.basketballapp.repository;

import android.app.Application;
import android.basketballapp.dao.ShotDao;
import android.basketballapp.database.BasketballAppRoomDatabase;
import android.basketballapp.entity.Shot;

public class ShotRepository {
    private ShotDao shotDao;

    public ShotRepository(Application application) {
        BasketballAppRoomDatabase db = BasketballAppRoomDatabase.getDatabase(application);
        shotDao = db.shotDao();
    }

    public void insert(Shot shot) {
        shotDao.insert(shot);
    }
}
