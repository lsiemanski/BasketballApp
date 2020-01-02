package android.basketballapp.viewmodel;

import android.app.Application;
import android.basketballapp.entity.DrillAndSpots;
import android.basketballapp.entity.Shot;
import android.basketballapp.entity.ShotAndSpot;
import android.basketballapp.entity.Spot;
import android.basketballapp.entity.TrainingAndShots;
import android.basketballapp.repository.DrillRepository;
import android.basketballapp.repository.TrainingRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;
import java.util.List;

public class TrainingSummaryViewModel extends AndroidViewModel {

    private TrainingRepository trainingRepository;
    private DrillRepository drillRepository;

    private LiveData<TrainingAndShots> trainingAndShots;
    private LiveData<DrillAndSpots> drillAndSpots;

    private MediatorLiveData<TrainingAndShots> _trainingAndShots;
    private MediatorLiveData<DrillAndSpots> _drillAndSpots;

    private List<SpotAndResults> spotAndResults;
    private List<ShotAndSpot> shotAndSpots;

    private int shotsTakenByView = 0;

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

    public TrainingSummaryViewModel(@NonNull Application application, int trainingId, int drillId) {
        super(application);

        trainingRepository = new TrainingRepository(application);
        drillRepository = new DrillRepository(application);

        trainingAndShots = trainingRepository.getTrainingAndShots(trainingId);
        _trainingAndShots = new MediatorLiveData<>();
        _trainingAndShots.addSource(trainingAndShots, value -> _trainingAndShots.setValue(value));

        drillAndSpots = drillRepository.getDrillAndSpots(drillId);
        _drillAndSpots = new MediatorLiveData<>();
        _drillAndSpots.addSource(drillAndSpots, value -> _drillAndSpots.setValue(value));
    }

    public void countMakesAndMisses() {
        DrillAndSpots drillAndSpots = _drillAndSpots.getValue();
        TrainingAndShots trainingAndShots = _trainingAndShots.getValue();

        drillAndSpots.spots.sort(new Spot.SortByOrder());

        spotAndResults = new ArrayList<>();

        for(int i = 0; i < drillAndSpots.spots.size(); i++) {
            spotAndResults.add(new SpotAndResults(drillAndSpots.spots.get(i)));
        }

        shotAndSpots = mapShotsToSpots(trainingAndShots.shots, drillAndSpots.spots);

        int totalMakes = 0;
        int totalShots = 0;

        for(int i = 0; i < shotAndSpots.size(); i++) {
            ShotAndSpot shotAndSpot = shotAndSpots.get(i);
            SpotAndResults currentSpotAndResults = findSpotAndResult(shotAndSpot.spot);

            if(shotAndSpot.shot.isMade) {
                totalMakes++;
                currentSpotAndResults.made++;
            }

            shotAndSpot.shot.madeTotal = totalMakes;
            shotAndSpot.shot.takenTotal = ++totalShots;
            currentSpotAndResults.taken++;

            shotAndSpot.shot.madeFromSpot = currentSpotAndResults.made;
            shotAndSpot.shot.takenFromSpot = currentSpotAndResults.taken;
        }
    }

    public boolean isShotsRemaining() {
        return shotsTakenByView < shotAndSpots.size();
    }

    public ShotAndSpot getNextShotAndSpot() {
        return shotAndSpots.get(shotsTakenByView++);
    }

    public int getMadeFromSpot(int index) {
        return spotAndResults.get(index).made;
    }

    public int getTakenFromSpot(int index) {
        return spotAndResults.get(index).taken;
    }

    private List<ShotAndSpot> mapShotsToSpots(List<Shot> shots, List<Spot> spots) {
        List<ShotAndSpot> shotAndSpots = new ArrayList<>();

        for(int i = 0; i < shots.size(); i++) {
            Shot shot = shots.get(i);
            shotAndSpots.add(new ShotAndSpot(shot, mapSpot(shot, spots)));
        }

        return shotAndSpots;
    }

    private Spot mapSpot(Shot shot, List<Spot> spots) {
        for(int i = 0; i < spots.size(); i++) {
            Spot spot = spots.get(i);
            if(spot.spotId == shot.spotId)
                return spot;
        }
        return null;
    }

    private SpotAndResults findSpotAndResult(Spot spot) {
        for(int i = 0; i < spotAndResults.size(); i++) {
            SpotAndResults spotAndResult = spotAndResults.get(i);
            if(spotAndResult.spot.equals(spot))
                return spotAndResult;
        }
        return null;
    }

    public LiveData<DrillAndSpots> getDrillAndSpots() {
        return _drillAndSpots;
    }

    public LiveData<TrainingAndShots> getTrainingAndShots() {
        return _trainingAndShots;
    }
}
