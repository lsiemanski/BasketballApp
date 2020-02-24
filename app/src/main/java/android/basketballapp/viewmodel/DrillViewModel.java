package android.basketballapp.viewmodel;

import android.app.Application;
import android.basketballapp.entity.Category;
import android.basketballapp.entity.DrillAndCategory;
import android.basketballapp.repository.CategoryRepository;
import android.basketballapp.repository.DrillRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DrillViewModel extends AndroidViewModel {

    private CategoryRepository categoryRepository;
    private DrillRepository drillRepository;
    private LiveData<List<DrillAndCategory>> allDrills;

    public DrillViewModel(Application application) {
        super(application);
        drillRepository = new DrillRepository(application);
        categoryRepository = new CategoryRepository(application);
        allDrills = drillRepository.getAllDrills();
    }

    public LiveData<List<DrillAndCategory>> getAllDrills() {
        return allDrills; }

    public Category getCategory(int id) {
        return categoryRepository.getCategory(id);
    }
}
