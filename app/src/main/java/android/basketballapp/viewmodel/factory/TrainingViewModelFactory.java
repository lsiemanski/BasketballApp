package android.basketballapp.viewmodel.factory;

import android.app.Application;
import android.basketballapp.viewmodel.TrainingViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class TrainingViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private int drillId;

    public TrainingViewModelFactory(Application application, int drillId) {
        this.application = application;
        this.drillId = drillId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new TrainingViewModel(application, drillId);
    }
}
