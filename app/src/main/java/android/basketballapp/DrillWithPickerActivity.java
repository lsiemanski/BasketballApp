package android.basketballapp;

import android.basketballapp.intentfactory.TrainingActivityIntentFactory;
import android.basketballapp.utils.BlockedCursorEditText;
import android.basketballapp.utils.StringUtils;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

public class DrillWithPickerActivity extends BasicDrillActivity {
    private int pickerValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int minPickerValue = getIntent().getIntExtra("minPickerValue", 1);
        int maxPickerValue = getIntent().getIntExtra("maxPickerValue", 10);

        Button startButton = findViewById(R.id.start_training);
        startButton.setOnClickListener(v -> {
            if(pickerValue > 0) {
                Intent intent = TrainingActivityIntentFactory
                        .create(getApplicationContext(),
                                drillName,
                                getIntent().getIntExtra("drillId", 0),
                                getIntent().getIntExtra("playerId", 0),
                                pickerValue);
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
                    if(newValue > maxPickerValue) {
                        pickerValue = maxPickerValue;
                        picker.setText(Integer.toString(pickerValue));
                        Toast.makeText(getApplicationContext(), "Maximum value is " + maxPickerValue, Toast.LENGTH_SHORT).show();
                    } else if(newValue < minPickerValue) {
                        pickerValue = minPickerValue;
                        picker.setText(Integer.toString(pickerValue));
                        Toast.makeText(getApplicationContext(), "Minimum value is " + minPickerValue, Toast.LENGTH_SHORT).show();
                    } else {
                        pickerValue = newValue;
                    }
                } else {
                    pickerValue = 0;
                }
            }
        });

        minusButton.setOnClickListener(v -> {
            if(pickerValue != minPickerValue)
                pickerValue -= 1;
            picker.setText(Integer.toString(pickerValue));
        });

        plusButton.setOnClickListener(v -> {
            if(pickerValue != maxPickerValue)
                pickerValue += 1;
            picker.setText(Integer.toString(pickerValue));
        });
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_drill_with_picker;
    }
}
