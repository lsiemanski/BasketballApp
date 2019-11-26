package android.basketballapp.viewmodel;

import android.app.Application;
import android.basketballapp.entity.Player;
import android.basketballapp.repository.PlayerRepository;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PlayerViewModel extends AndroidViewModel {

    private PlayerRepository playerRepository;
    private List<Player> allPlayersList;
    private LiveData<List<Player>> allPlayers;
    private MediatorLiveData<List<Player>> shownPlayers;
    private boolean nameAscending = false;
    private boolean dateAscending = true;
    private boolean positionAscending = true;

    public PlayerViewModel(Application application) {
        super(application);
        playerRepository = new PlayerRepository(application);
        allPlayers = playerRepository.getAllPlayers();
        shownPlayers = new MediatorLiveData<>();
        shownPlayers.addSource(allPlayers, value -> shownPlayers.setValue(value));
    }

    public LiveData<List<Player>> getAllPlayers() {
        return shownPlayers;
    }

    public void insert(Player player) {
        playerRepository.insert(player);
    }

    public void filter(Player.Position position) {
        if(allPlayersList == null)
            allPlayersList = shownPlayers.getValue();

        List<Player> filteredPlayers = new ArrayList<>();
        for(Player player : allPlayersList) {
            if(player.getPosition() == position) {
                filteredPlayers.add(player);
            }
        }

        shownPlayers.setValue(filteredPlayers);
    }

    public void showAll() {
        shownPlayers.setValue(allPlayersList);
        resetOrderFlags();
        sortByName();
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
        shownPlayers.getValue().sort(comparator);
        if(!ascending)
            Collections.reverse(shownPlayers.getValue());
        shownPlayers.setValue(shownPlayers.getValue());

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
