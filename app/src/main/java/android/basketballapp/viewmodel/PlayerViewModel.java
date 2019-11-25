package android.basketballapp.viewmodel;

import android.app.Application;
import android.basketballapp.entity.Player;
import android.basketballapp.repository.PlayerRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayerViewModel extends AndroidViewModel {

    private PlayerRepository playerRepository;
    private LiveData<List<Player>> allPlayers;
    private MediatorLiveData<List<Player>> _allPlayers;
    private boolean nameAscending = false;
    private boolean dateAscending = true;
    private boolean positionAscending = true;

    public PlayerViewModel(Application application) {
        super(application);
        playerRepository = new PlayerRepository(application);
        allPlayers = playerRepository.getAllPlayers();
        _allPlayers = new MediatorLiveData<>();
        _allPlayers.addSource(allPlayers, value -> _allPlayers.setValue(value));
    }

    public LiveData<List<Player>> getAllPlayers() {
        return _allPlayers;
    }

    public void insert(Player player) {
        playerRepository.insert(player);
    }

    public void sortByName() {
        boolean order = nameAscending;
        sort(order, Player.SORT_BY_NAME_COMPARATOR);
        resetOrderFlags();
        nameAscending = !order;
    }

    public void sortByPosition() {
        boolean order = positionAscending;
        sort(order, Player.SORT_BY_POSITION_COMPARATOR);
        resetOrderFlags();
        positionAscending = !order;
    }

    public void sortByDate() {
        boolean order = dateAscending;
        sort(order, Player.SORT_BY_DATE_JOINED_COMPARATOR);
        resetOrderFlags();
        dateAscending = !order;
    }

    private void sort(boolean ascending, Comparator<? super Player> comparator) {
        _allPlayers.getValue().sort(comparator);
        if(!ascending)
            Collections.reverse(_allPlayers.getValue());
        _allPlayers.setValue(_allPlayers.getValue());

        nameAscending = !nameAscending;
        dateAscending = true;
        positionAscending = true;
    }

    private void resetOrderFlags() {
        nameAscending = true;
        dateAscending = true;
        positionAscending = true;
    }
}
