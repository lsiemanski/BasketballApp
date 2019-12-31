package android.basketballapp.viewmodel;

import android.app.Application;
import android.basketballapp.entity.DrillAndSpots;
import android.basketballapp.entity.Shot;
import android.basketballapp.entity.ShotAndSpot;
import android.basketballapp.entity.Spot;
import android.basketballapp.entity.Training;
import android.basketballapp.repository.DrillRepository;
import android.basketballapp.repository.TrainingRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

public class TrainingViewModel extends AndroidViewModel {

    private TrainingRepository trainingRepository;
    private DrillRepository drillRepository;

    private LiveData<DrillAndSpots> drillAndSpots;
    private MediatorLiveData<DrillAndSpots> _drillAndSpots;
    private Training currentTraining;
    private List<ShotAndSpot> shotAndSpots;

    private int previous;
    private int current;

    private int numberOfSpots;
    private int totalMakes;
    private int totalShots;
    private List<SpotAndResults> spotAndResults;

    public TrainingViewModel(@NonNull Application application, int drillId) {
        super(application);
        application.getApplicationContext();
        trainingRepository = new TrainingRepository(application);

        drillRepository = new DrillRepository(application);
        drillAndSpots = drillRepository.getDrillAndSpots(drillId);
        _drillAndSpots = new MediatorLiveData<>();
        _drillAndSpots.addSource(drillAndSpots, value -> _drillAndSpots.setValue(value));
    }

    class SpotAndResults {
        Spot spot;
        int made;
        int taken;

        SpotAndResults(Spot spot) {
            this.spot = spot;
            made = 0;
            taken = 0;
        }
    }

    public void initTraining(int drillId, int playerId, int numberOfShots) {
        DrillAndSpots drillAndSpots = _drillAndSpots.getValue();

        drillAndSpots.spots.sort(new Spot.SortByOrder());

        spotAndResults = new ArrayList<>();

        for(int i = 0; i < drillAndSpots.spots.size(); i++) {
            spotAndResults.add(new SpotAndResults(drillAndSpots.spots.get(i)));
        }

        currentTraining = new Training(drillId, playerId);
        shotAndSpots = new ArrayList<>();

        numberOfSpots = drillAndSpots.spots.size();
        current = 0;
        totalMakes = 0;
        totalShots = 0;
    }

    public ShotAndSpot addShotMade() {
        SpotAndResults currentSpot = spotAndResults.get(current);

        currentSpot.made++; currentSpot.taken++;
        totalMakes++; totalShots++;

        updateCurrent();

        ShotAndSpot shotAndSpot = new ShotAndSpot(new Shot(1, currentSpot.spot.spotId, true), currentSpot.spot);
        updateShotAndSpot(shotAndSpot, currentSpot);

        return shotAndSpot;
    }

    public ShotAndSpot addShotMissed() {
        SpotAndResults currentSpot = spotAndResults.get(current);

        currentSpot.taken++;
        totalShots++;

        updateCurrent();

        ShotAndSpot shotAndSpot = new ShotAndSpot(new Shot(1, currentSpot.spot.spotId, false), currentSpot.spot);
        updateShotAndSpot(shotAndSpot, currentSpot);

        return shotAndSpot;
    }

    private void updateShotAndSpot(ShotAndSpot shotAndSpot, SpotAndResults spotAndResults) {
        shotAndSpot.shot.madeFromSpot = spotAndResults.made;
        shotAndSpot.shot.takenFromSpot = spotAndResults.taken;
        shotAndSpot.shot.madeTotal = totalMakes;
        shotAndSpot.shot.takenTotal = totalShots;
    }

    private int generateNext(int current) {
        return (current + 1) % numberOfSpots;
    }

    private void updateCurrent() {
        previous = current;
        current = generateNext(current);
    }

    public int getPrevious() {
        return previous;
    }

    public int getCurrent() {
        return current;
    }

    public LiveData<DrillAndSpots> getDrillAndSpots() {
        return _drillAndSpots;
    }
}
