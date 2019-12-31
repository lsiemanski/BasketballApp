package android.basketballapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.basketballapp.entity.Training;
import android.basketballapp.utils.BlockedCursorEditText;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class DrillActivity extends AppCompatActivity {

    private final static String WEB_ASSETS_PATH = "file:///android_asset/web/";
    private String drillName;
    private int pickerValue;
    private static final int MAX_PICKER_VALUE = 50;
    private static final int MIN_PICKER_VALUE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drill);
        Intent parentIntent = getIntent();
        drillName = parentIntent.getStringExtra("drillName");
        this.setTitle(drillName);

        WebView webView = findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(WEB_ASSETS_PATH + parentIntent.getStringExtra("htmlFile"));

        Button startButton = findViewById(R.id.start_training);
        Intent intent = new Intent(this, TrainingActivity.class);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pickerValue > 0) {
                    intent.putExtra("drillId", getIntent().getIntExtra("drillId", 0));
                    intent.putExtra("playerId", getIntent().getIntExtra("playerId", 0));
                    intent.putExtra("numberOfShots", pickerValue);
                    startActivity(intent);
                }
            }
        });

        Button minusButton = findViewById(R.id.minus);
        Button plusButton = findViewById(R.id.plus);
        BlockedCursorEditText picker = findViewById(R.id.picker);
        pickerValue = Integer.parseInt(picker.getText().toString());

        picker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("0")) {
                    picker.setText("");
                    pickerValue = 0;
                } else if(hasLeadingZeros(s.toString())) {
                    picker.setText(trimLeadingZeros(s.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("") && !s.toString().equals("0")) {
                    int newValue = Integer.parseInt(s.toString());
                    if(newValue > MAX_PICKER_VALUE) {
                        pickerValue = MAX_PICKER_VALUE;
                        picker.setText(Integer.toString(pickerValue));
                        //Toast.makeText(getApplicationContext(), "Maksymalna wartosc to: " + Integer.toString(MAX_PICKER_VALUE), Toast.LENGTH_SHORT);
                    } else if(newValue < MIN_PICKER_VALUE) {
                        pickerValue = MIN_PICKER_VALUE;
                        picker.setText(Integer.toString(pickerValue));
                        //Toast.makeText(getApplicationContext(), "Minimalna wartosc to: " + Integer.toString(MIN_PICKER_VALUE), Toast.LENGTH_SHORT);
                    } else {
                        pickerValue = newValue;
                    }
                } else {
                    pickerValue = 0;
                }
            }
        });

        minusButton.setOnClickListener(v -> {
            if(pickerValue != MIN_PICKER_VALUE)
                pickerValue -= 1;
            picker.setText(Integer.toString(pickerValue));
        });

        plusButton.setOnClickListener(v -> {
            if(pickerValue != MAX_PICKER_VALUE)
                pickerValue += 1;
            picker.setText(Integer.toString(pickerValue));
        });
    }

    //TODO: zrobic klase Utils na to
    private String trimLeadingZeros(String s) {
        return s.replaceFirst("^0+(?!$)", "");
    }

    private boolean hasLeadingZeros(String s) {
        return s.startsWith("0");
    }
}
