package android.basketballapp.viewmodel.factory;

import android.app.Application;
import android.basketballapp.viewmodel.TrainingSummaryViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TrainingSummaryViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private int trainingId;
    private int drillId;

    public TrainingSummaryViewModelFactory(Application application, int trainingId, int drillId) {
        this.application = application;
        this.trainingId = trainingId;
        this.drillId = drillId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TrainingSummaryViewModel(application, trainingId, drillId);
    }
}
