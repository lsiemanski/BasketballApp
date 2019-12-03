package android.basketballapp.repository;

import android.app.Application;
import android.basketballapp.dao.DrillDao;
import android.basketballapp.database.BasketballAppRoomDatabase;
import android.basketballapp.entity.Drill;
import android.basketballapp.entity.Player;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DrillRepository {
    private DrillDao drillDao;
    private LiveData<List<Drill>> allDrills;

    public DrillRepository(Application application) {
        BasketballAppRoomDatabase db = BasketballAppRoomDatabase.getDatabase(application);
        drillDao = db.drillDao();
        allDrills = drillDao.getAllDrills();
    }

    public LiveData<List<Drill>> getAllDrills() { return allDrills; }

    public void insert(Drill drill) {
        BasketballAppRoomDatabase.databaseWriterExecutor.execute(() -> {
            drillDao.insert(drill);
        });
    }
}
