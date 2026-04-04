package com.example.project_final;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddDeadlineActivity extends AppCompatActivity {

    EditText etTitle;
    Spinner spSubject, spPriority;
    TextView tvDate;
    Button btnSave;

    String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adddeadlineactivity);

        // Initialize views
        etTitle = findViewById(R.id.etTitle);
        spSubject = findViewById(R.id.spSubject);
        spPriority = findViewById(R.id.spPriority);
        tvDate = findViewById(R.id.tvDate);
        btnSave = findViewById(R.id.btnSave);

        // Setup Spinners
        setupSpinners();

        // Date Picker
        tvDate.setOnClickListener(v -> openDatePicker());

        // Save Button
        btnSave.setOnClickListener(v -> saveData());
    }

    // 📌 Spinner Setup
    private void setupSpinners() {

        ArrayAdapter<CharSequence> subjectAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.subjects,
                android.R.layout.simple_spinner_dropdown_item
        );

        spSubject.setAdapter(subjectAdapter);

        ArrayAdapter<CharSequence> priorityAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.priorities,
                android.R.layout.simple_spinner_dropdown_item
        );

        spPriority.setAdapter(priorityAdapter);
    }

    // 📅 Date Picker
    private void openDatePicker() {
        Calendar c = Calendar.getInstance();

        new DatePickerDialog(this, (view, year, month, day) -> {
            month++; // months start from 0
            selectedDate = year + "-" + format(month) + "-" + format(day);
            tvDate.setText(selectedDate);
        },
                c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    // 💾 Save Data
    private void saveData() {

        String title = etTitle.getText().toString().trim();
        String subject = spSubject.getSelectedItem().toString();
        String priority = spPriority.getSelectedItem().toString();

        // Validation
        if (title.isEmpty()) {
            etTitle.setError("Enter task title");
            return;
        }

        if (selectedDate.isEmpty()) {
            Toast.makeText(this, "Select date", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert into DB
        DBHelper db = new DBHelper(this);
        Deadline d = new Deadline(title, subject, selectedDate, priority);
        db.insertDeadline(d);

        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();

        finish(); // go back to main screen
    }
    // Format date (01, 02, etc.)
    private String format(int val) {
        return val < 10 ? "0" + val : String.valueOf(val);
    }

}
