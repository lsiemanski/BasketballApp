package android.basketballapp.viewmodel;

import android.app.Application;
import android.basketballapp.entity.Training;
import android.basketballapp.repository.TrainingRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TrainingListViewModel extends AndroidViewModel {

    private TrainingRepository trainingRepository;
    private LiveData<List<Training>> trainings;

    public TrainingListViewModel(@NonNull Application application, int drillId, int playerId) {
        super(application);

        trainingRepository = new TrainingRepository(application);
        trainings = trainingRepository.getTrainings(drillId, playerId);
    }

    public LiveData<List<Training>> getTrainings() {
        return trainings;
    }

}
