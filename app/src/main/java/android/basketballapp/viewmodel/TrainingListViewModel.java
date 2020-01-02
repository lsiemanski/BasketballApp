package android.basketballapp.viewmodel;

import android.app.Application;
import android.basketballapp.entity.Training;
import android.basketballapp.repository.DrillRepository;
import android.basketballapp.repository.TrainingRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.List;

public class TrainingListViewModel extends AndroidViewModel {

    private TrainingRepository trainingRepository;
    private LiveData<List<Training>> trainings;
    private MediatorLiveData<List<Training>> _trainings;

    private int elementsTakenByView = 0;

    public TrainingListViewModel(@NonNull Application application, int drillId, int playerId) {
        super(application);

        trainingRepository = new TrainingRepository(application);
        trainings = trainingRepository.getTrainings(drillId, playerId);
        _trainings = new MediatorLiveData<>();
        _trainings.addSource(trainings, value -> _trainings.setValue(value));
    }

    public LiveData<List<Training>> getTrainings() {
        return _trainings;
    }

    public boolean isTrainingRemaining() {
        return elementsTakenByView < _trainings.getValue().size();
    }

    public Training getNextTraining() {
        return _trainings.getValue().get(elementsTakenByView++);
    }
}
