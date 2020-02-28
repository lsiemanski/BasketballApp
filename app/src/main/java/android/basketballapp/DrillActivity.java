package android.basketballapp;

import androidx.appcompat.app.AppCompatActivity;

import android.basketballapp.utils.BlockedCursorEditText;
import android.basketballapp.utils.StringUtils;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

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
        startButton.setOnClickListener(v -> {
            if(pickerValue > 0) {
                intent.putExtra("drillId", getIntent().getIntExtra("drillId", 0));
                intent.putExtra("playerId", getIntent().getIntExtra("playerId", 0));
                intent.putExtra("numberOfShots", pickerValue);
                startActivity(intent);
                finish();
            }
        });

        Button minusButton = findViewById(R.id.minus);
        Button plusButton = findViewById(R.id.plus);
        BlockedCursorEditText picker = findViewById(R.id.picker);
        pickerValue = Integer.parseInt(picker.getText().toString());

        picker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("0")) {
                    picker.setText("");
                    pickerValue = 0;
                } else if(StringUtils.hasLeadingZeros(s.toString())) {
                    picker.setText(StringUtils.trimLeadingZeros(s.toString()));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("") && !s.toString().equals("0")) {
                    int newValue = Integer.parseInt(s.toString());
                    if(newValue > MAX_PICKER_VALUE) {
                        pickerValue = MAX_PICKER_VALUE;
                        picker.setText(Integer.toString(pickerValue));
                        Toast.makeText(getApplicationContext(), "Maximum value is " + MAX_PICKER_VALUE, Toast.LENGTH_SHORT).show();
                    } else if(newValue < MIN_PICKER_VALUE) {
                        pickerValue = MIN_PICKER_VALUE;
                        picker.setText(Integer.toString(pickerValue));
                        Toast.makeText(getApplicationContext(), "Minimum value is " + MIN_PICKER_VALUE, Toast.LENGTH_SHORT).show();
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
}
