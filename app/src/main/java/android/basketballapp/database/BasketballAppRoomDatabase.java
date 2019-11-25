package android.basketballapp.database;

import android.basketballapp.dao.PlayerDao;
import android.basketballapp.entity.Player;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Player.class}, version=1, exportSchema = false)
public abstract class BasketballAppRoomDatabase extends RoomDatabase {

    public abstract PlayerDao playerDao();

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
            });
        }
    };
}
