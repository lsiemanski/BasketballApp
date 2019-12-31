package android.basketballapp.repository;

import android.app.Application;
import android.basketballapp.dao.TrainingDao;
import android.basketballapp.database.BasketballAppRoomDatabase;
import android.basketballapp.entity.Training;

public class TrainingRepository {
    private TrainingDao trainingDao;

    public TrainingRepository(Application application) {
        BasketballAppRoomDatabase db = BasketballAppRoomDatabase.getDatabase(application);
        trainingDao = db.trainingDao();
    }
}
