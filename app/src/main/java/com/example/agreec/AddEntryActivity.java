package com.example.agreec;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddEntryActivity extends AppCompatActivity {

    private EditText etDate, etFertilizerCategory, etFertilizerAmount, etPlantType;
    private Button btnSubmit;

    // Firebase instances
    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);

        // Initialize Firebase Firestore and Auth
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        // Initialize UI components
        etDate = findViewById(R.id.etDate);
        etFertilizerCategory = findViewById(R.id.etFertilizerCategory);
        etFertilizerAmount = findViewById(R.id.etFertilizerAmount);
        etPlantType = findViewById(R.id.etPlantType);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Date Picker Dialog
        etDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(AddEntryActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        etDate.setText(selectedDate);
                    }, year, month, day);
            datePickerDialog.show();
        });

        // Submit Button Logic
        btnSubmit.setOnClickListener(v -> {
            String date = etDate.getText().toString();
            String category = etFertilizerCategory.getText().toString();
            String amount = etFertilizerAmount.getText().toString();
            String plantType = etPlantType.getText().toString();
            String userId = firebaseAuth.getCurrentUser() != null ? firebaseAuth.getCurrentUser().getUid() : null;

            if (date.isEmpty() || category.isEmpty() || amount.isEmpty() || plantType.isEmpty()) {
                Toast.makeText(AddEntryActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (userId == null) {
                Toast.makeText(AddEntryActivity.this, "User not logged in!", Toast.LENGTH_SHORT).show();
            } else {
                // Prepare data for Firestore
                Map<String, Object> entryData = new HashMap<>();
                entryData.put("date", date);
                entryData.put("fertilizerCategory", category);
                entryData.put("fertilizerAmount", amount);
                entryData.put("plantType", plantType);
                entryData.put("userId", userId); // Store current user's UID
                entryData.put("timestamp", System.currentTimeMillis()); // Store current time as a timestamp

                // Save data to Firestore
                firestore.collection("entries")
                        .add(entryData) // Automatically generates a unique ID
                        .addOnSuccessListener(documentReference -> {
                            // Show success toast
                            Toast.makeText(AddEntryActivity.this, "Entry Submitted Successfully", Toast.LENGTH_SHORT).show();

                            // Clear the input fields
                            etDate.setText("");
                            etFertilizerCategory.setText("");
                            etFertilizerAmount.setText("");
                            etPlantType.setText("");

                            // Redirect to DashboardActivity
                            Intent intent = new Intent(AddEntryActivity.this, DashboardActivity.class);
                            startActivity(intent);

                            // Finish current activity
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(AddEntryActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        });
            }
        });
    }
}
