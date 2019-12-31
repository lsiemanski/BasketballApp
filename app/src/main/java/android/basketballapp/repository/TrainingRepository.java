package android.basketballapp.repository;

import android.app.Application;
import android.basketballapp.dao.TrainingDao;
import android.basketballapp.database.BasketballAppRoomDatabase;
import android.basketballapp.entity.Training;
import android.basketballapp.entity.TrainingAndShots;

import androidx.lifecycle.LiveData;

public class TrainingRepository {
    private TrainingDao trainingDao;

    public TrainingRepository(Application application) {
        BasketballAppRoomDatabase db = BasketballAppRoomDatabase.getDatabase(application);
        trainingDao = db.trainingDao();
    }

    public long insert(Training training) {
        return trainingDao.insert(training);
    }

    public LiveData<TrainingAndShots> getTrainingAndShots(int id) {
        return trainingDao.getTrainingAndShots(id);
    }
}
