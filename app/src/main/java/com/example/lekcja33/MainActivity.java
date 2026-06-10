package com.example.lekcja33;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etDays;
    private RadioGroup rgTransport;
    private CheckBox cbGuide;
    private CheckBox cbBreakfast;
    private Button btnCalculate;

    private static final int BASE_PRICE_PER_DAY = 120;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDays = findViewById(R.id.etDays);
        rgTransport = findViewById(R.id.rgTransport);
        cbGuide = findViewById(R.id.cbGuide);
        cbBreakfast = findViewById(R.id.cbBreakfast);
        btnCalculate = findViewById(R.id.btnCalculate);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateTotalCost();
            }
        });
    }

    private void calculateTotalCost() {
        String daysInput = etDays.getText().toString().trim();
        if (daysInput.isEmpty()) {
            Toast.makeText(MainActivity.this, getString(R.string.error_empty_days), Toast.LENGTH_SHORT).show();
            return; // Przerwanie wykonywania metody
        }

        int days = Integer.parseInt(daysInput);
        int totalCost = 0;

        totalCost += days * BASE_PRICE_PER_DAY;

        int selectedTransportId = rgTransport.getCheckedRadioButtonId();
        if (selectedTransportId == R.id.rbOwn) {
            totalCost += 0;
        } else if (selectedTransportId == R.id.rbBus) {
            totalCost += 100;
        } else if (selectedTransportId == R.id.rbPlane) {
            totalCost += 500;
        }

        if (cbGuide.isChecked()) {
            totalCost += 150;
        }

        if (cbBreakfast.isChecked()) {
            totalCost += 30 * days;
        }

        showSummaryDialog(days, totalCost);
    }

    private void showSummaryDialog(int days, int totalCost) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dialog_title));

        String message = getString(R.string.dialog_message, days, totalCost);
        builder.setMessage(message);
        builder.setPositiveButton(getString(R.string.dialog_ok), null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}