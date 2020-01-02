package android.basketballapp.viewmodel.factory;

import android.app.Application;
import android.basketballapp.viewmodel.TrainingListViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TrainingListViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private int drillId;
    private int playerId;

    public TrainingListViewModelFactory(Application application, int drillId, int playerId) {
        this.application = application;
        this.drillId = drillId;
        this.playerId = playerId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TrainingListViewModel(application, drillId, playerId);
    }
}
