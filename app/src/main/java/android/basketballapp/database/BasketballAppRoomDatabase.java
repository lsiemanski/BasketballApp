package android.basketballapp.database;

import android.basketballapp.dao.CategoryDao;
import android.basketballapp.dao.DrillDao;
import android.basketballapp.dao.PlayerDao;
import android.basketballapp.entity.Category;
import android.basketballapp.entity.Drill;
import android.basketballapp.entity.DrillAndCategory;
import android.basketballapp.entity.Player;
import android.basketballapp.entity.Shot;
import android.basketballapp.entity.Spot;
import android.basketballapp.entity.Training;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Player.class, Category.class, Drill.class, Training.class, Spot.class, Shot.class}, version=1, exportSchema = false)
public abstract class BasketballAppRoomDatabase extends RoomDatabase {

    public abstract PlayerDao playerDao();
    public abstract CategoryDao categoryDao();
    public abstract DrillDao drillDao();

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
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriterExecutor.execute(() -> {

//                PlayerDao dao = INSTANCE.playerDao();
//                dao.deleteAll();
//                Player player = new Player("Michael", "Jordan", Player.Position.SHOOTING_GUARD, 198);
//                player.image = "jordan.png";
//                dao.insert(player);
//                Player player2 = new Player("Kobe", "Bryant", Player.Position.SHOOTING_GUARD, 201);
//                dao.insert(player2);
//                Player player3 = new Player("Łukasz", "Koszarek", Player.Position.POINT_GUARD, 187);
//                dao.insert(player3);
//                Player player4 = new Player("Marcin", "Gortat", Player.Position.CENTER, 213);
//                dao.insert(player4);
//                Player player5 = new Player("Mateusz", "Ponitka", Player.Position.SMALL_FORWARD, 203);
//                dao.insert(player5);
//                Player player6 = new Player("Tim", "Duncan", Player.Position.POWER_FORWARD, 210);
//                dao.insert(player6);
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
                Drill drill = new Drill("5 position 3-point shooting", "5_position_drill.html", shooting.id);
                drillDao.insert(drill);
                Drill drill2 = new Drill("Ray Allen drill", "ray_allen_drill.html", shooting.id);
                drillDao.insert(drill2);
                Drill drill3 = new Drill("Form shooting", "", shooting.id);
                drillDao.insert(drill3);
                Drill drill4 = new Drill("Warmup dribbling", "", dribbling.id);
                drillDao.insert(drill4);
            });
        }
    };
}
