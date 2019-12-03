package android.basketballapp.viewmodel;

import android.app.Application;
import android.basketballapp.entity.Drill;
import android.basketballapp.repository.DrillRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DrillViewModel extends AndroidViewModel {

    private DrillRepository drillRepository;
    private LiveData<List<Drill>> allDrills;

    public DrillViewModel(Application application) {
        super(application);
        drillRepository = new DrillRepository(application);
        allDrills = drillRepository.getAllDrills();
    }

    public LiveData<List<Drill>> getAllDrills() { return allDrills; }
}
