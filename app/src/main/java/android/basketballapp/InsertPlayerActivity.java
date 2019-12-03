package android.basketballapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.basketballapp.entity.Player;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InsertPlayerActivity extends AppCompatActivity {

    private static final int GET_FROM_GALLERY = 1;
    private Bitmap bitmap;
    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_player);

        Spinner positionSpinner = findViewById(R.id.position_spinner);

        List<String> positions = new ArrayList<>();

        for(Player.Position pos : Player.Position.values()) {
            positions.add(pos.getDescription());
        }

        positionSpinner.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, positions));

        Button uploadImageButton = findViewById(R.id.upload_image_button);
        uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uploadImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(uploadImageIntent, GET_FROM_GALLERY);
            }
        });

        Button addPlayerButton = findViewById(R.id.insert_player_button);
        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextName = findViewById(R.id.name_text_input_edit_text);
                EditText editTextSurname = findViewById(R.id.surname_text_input_edit_text);
                EditText editTextHeight = findViewById(R.id.height_text_input_edit_text);

                if(validateEntries(editTextName, editTextSurname, editTextHeight)) {
                    Intent replyIntent = new Intent();
                    replyIntent.putExtra("name", editTextName.getText().toString());
                    replyIntent.putExtra("surname", editTextSurname.getText().toString());
                    replyIntent.putExtra("height", Integer.parseInt(editTextHeight.getText().toString()));
                    replyIntent.putExtra("position", positionSpinner.getSelectedItem().toString());
                    replyIntent.putExtra("image", selectedImage == null ? null : selectedImage.toString());
                    setResult(RESULT_OK, replyIntent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch(FileNotFoundException e) {
                e.printStackTrace();
            } catch(IOException e) {
                e.printStackTrace();
            }

            ImageView imageView = findViewById(R.id.profile_picture);
            imageView.setImageBitmap(bitmap);
        }
    }

    private boolean validateEntries(EditText... editTexts) {
        boolean isOk = true;

        for(EditText editText : editTexts) {
            if(editText.getText().toString().equals("")) {
                editText.setError("Input must not be empty!");
                isOk = false;
            }
        }

        return isOk;
    }
}
