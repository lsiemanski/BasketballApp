package android.basketballapp.database;

import android.basketballapp.dao.CategoryDao;
import android.basketballapp.dao.DrillDao;
import android.basketballapp.dao.PlayerDao;
import android.basketballapp.dao.ShotDao;
import android.basketballapp.dao.SpotDao;
import android.basketballapp.dao.TrainingDao;
import android.basketballapp.entity.Category;
import android.basketballapp.entity.Drill;
import android.basketballapp.entity.Player;
import android.basketballapp.entity.Shot;
import android.basketballapp.entity.Spot;
import android.basketballapp.entity.Training;
import android.basketballapp.utils.DrillNames;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Player.class, Category.class, Drill.class, Training.class, Spot.class, Shot.class}, version=1, exportSchema = false)
public abstract class BasketballAppRoomDatabase extends RoomDatabase {

    public abstract PlayerDao playerDao();
    public abstract CategoryDao categoryDao();
    public abstract DrillDao drillDao();
    public abstract TrainingDao trainingDao();
    public abstract SpotDao spotDao();
    public abstract ShotDao shotDao();

    private static volatile BasketballAppRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static BasketballAppRoomDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (BasketballAppRoomDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                                    BasketballAppRoomDatabase.class,
                                             "basketball_app_database")
                            .addCallback(roomDatabaseCallback)
                            .build();
                }
            }
        }

        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriterExecutor.execute(() -> {
                CategoryDao categoryDao = INSTANCE.categoryDao();
                categoryDao.deleteAll();
                Category shooting = new Category("Shooting", "shooting_drill");
                categoryDao.insert(shooting);
                Category dribbling = new Category("Dribbling", "dribbling_drill");
                categoryDao.insert(dribbling);
                DrillDao drillDao = INSTANCE.drillDao();
                drillDao.deleteAll();
                shooting = categoryDao.getCategory("Shooting");
                dribbling = categoryDao.getCategory("Dribbling");
                Drill drill = new Drill(DrillNames.FIVE_POSITION_3_POINT_SHOOTING, "5_position_drill.html", shooting.id);
                drillDao.insert(drill);
                Drill drill1 = new Drill(DrillNames.FREE_THROW_SHOOTING, "free_throw_shooting_drill.html", shooting.id);
                drillDao.insert(drill1);
                Drill drill2 = new Drill(DrillNames.RAY_ALLEN_DRILL, "ray_allen_drill.html", shooting.id);
                drillDao.insert(drill2);
                Drill drill3 = new Drill(DrillNames.FORM_SHOOTING, "form_shooting.html", shooting.id);
                drillDao.insert(drill3);
                Drill drill4 = new Drill(DrillNames.WARMUP_DRIBBLING, "warmup_dribbling.html", dribbling.id);
                drillDao.insert(drill4);
                SpotDao spotDao = INSTANCE.spotDao();
                spotDao.deleteAll();
                spotDao.insert(new Spot(1, "Left corner", 1));
                spotDao.insert(new Spot(1, "Right corner", 2));
                spotDao.insert(new Spot(1, "Right 45", 3));
                spotDao.insert(new Spot(1, "Left 45", 4));
                spotDao.insert(new Spot(1, "Top", 5));
                spotDao.insert(new Spot(2, "Free throw", 1));
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
