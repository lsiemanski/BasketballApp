package android.basketballapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.basketballapp.adapter.PlayerListAdapter;
import android.basketballapp.converter.PositionConverter;
import android.basketballapp.entity.Player;
import android.basketballapp.viewmodel.PlayerViewModel;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PlayerViewModel playerViewModel;
    private static final int CREATE_NEW_PLAYER = 1;
    private MenuItem showAllItem;
    private MenuItem sortByPositionItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final PlayerListAdapter adapter = new PlayerListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);

        playerViewModel.getAllPlayers().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> players) {
                adapter.setPlayers(players);
            }
        });

//        Drawable drawable = getResources().getDrawable(R.drawable.floor_dark, getTheme());
//        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

//        Button save = findViewById(R.id.save);
//
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ContextWrapper cw = new ContextWrapper(getApplicationContext());
//                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
//                File file = new File(directory, "floor-dark.png");
//                System.out.println(file.getAbsolutePath());
//                try {
//                    FileOutputStream fos = new FileOutputStream(file);
//                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//                    fos.flush();
//                    fos.close();
//                    System.out.println("DONE\n");
//                } catch(IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.players_menu, menu);
        showAllItem = menu.findItem(R.id.show_all);
        sortByPositionItem = menu.findItem(R.id.sort_by_position);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_player:
                openAddPlayerActivity();
                return true;

            case R.id.sort_by_name:
                playerViewModel.sortByName();
                return true;

            case R.id.sort_by_date:
                playerViewModel.sortByDate();
                return true;

            case R.id.sort_by_position:
                playerViewModel.sortByPosition();
                return true;

            case R.id.show_all:
                sortByPositionItem.setVisible(true);
                showAllItem.setVisible(false);
                playerViewModel.showAll();
                return true;

            case R.id.only_point_guards:
                showAllItem.setVisible(true);
                sortByPositionItem.setVisible(false);
                playerViewModel.filter(Player.Position.POINT_GUARD);
                return true;

            case R.id.only_shooting_guards:
                showAllItem.setVisible(true);
                sortByPositionItem.setVisible(false);
                playerViewModel.filter(Player.Position.SHOOTING_GUARD);
                return true;

            case R.id.only_small_forwards:
                showAllItem.setVisible(true);
                sortByPositionItem.setVisible(false);
                playerViewModel.filter(Player.Position.SMALL_FORWARD);
                return true;

            case R.id.only_power_forwards:
                showAllItem.setVisible(true);
                sortByPositionItem.setVisible(false);
                playerViewModel.filter(Player.Position.POWER_FORWARD);
                return true;

            case R.id.only_centers:
                showAllItem.setVisible(true);
                sortByPositionItem.setVisible(false);
                playerViewModel.filter(Player.Position.CENTER);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openAddPlayerActivity() {
        Intent openInsertPlayerActivity = new Intent(this, InsertPlayerActivity.class);
        startActivityForResult(openInsertPlayerActivity, CREATE_NEW_PLAYER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CREATE_NEW_PLAYER && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            String surname = data.getStringExtra("surname");
            int height = data.getIntExtra("height", 0);
            Player.Position position = Player.Position.fromDescription(data.getStringExtra("position"));

            Player newPlayer = new Player(name, surname, position, height);
            String image = data.getStringExtra("image");

            if(image != null) {
                String imageFilename = new SimpleDateFormat("yyyyMMddHHmmss'.png'").format(new Date());
                saveImage(imageFilename, image);
                newPlayer.image = imageFilename;
            }

            playerViewModel.insert(newPlayer);
        }
    }

    private void saveImage(String filename, String image) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(image));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }

        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
