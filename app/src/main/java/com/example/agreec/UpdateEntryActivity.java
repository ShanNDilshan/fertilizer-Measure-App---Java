package com.example.agreec;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateEntryActivity extends AppCompatActivity {

    private EditText etFertilizerCategory, etFertilizerAmount, etPlantType;
    private Button btnSubmitUpdate;
    private FirebaseFirestore firestore;
    private String documentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_entry);

        // Initialize views and Firestore
        etFertilizerCategory = findViewById(R.id.etFertilizerCategory);
        etFertilizerAmount = findViewById(R.id.etFertilizerAmount);
        etPlantType = findViewById(R.id.etPlantType);
        btnSubmitUpdate = findViewById(R.id.btnSubmitUpdate);
        firestore = FirebaseFirestore.getInstance();

        // Get the document ID passed from the previous activity
        documentID = getIntent().getStringExtra("DOCUMENT_ID");

        // Check if the document ID is not null
        if (documentID == null) {
            Toast.makeText(this, "Document ID not found", Toast.LENGTH_SHORT).show();
            finish(); // Exit the activity if there's no document ID
        }

        // Button click listener for submitting the update
        btnSubmitUpdate.setOnClickListener(v -> {
            // Get updated values from EditTexts
            String fertilizerCategory = etFertilizerCategory.getText().toString().trim();
            String fertilizerAmount = etFertilizerAmount.getText().toString().trim();
            String plantType = etPlantType.getText().toString().trim();

            // Check if any field is empty
            if (fertilizerCategory.isEmpty() || fertilizerAmount.isEmpty() || plantType.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create a map of updated values
            DocumentReference docRef = firestore.collection("entries").document(documentID);
            docRef.update(
                    "fertilizerCategory", fertilizerCategory,
                    "fertilizerAmount", fertilizerAmount,
                    "plantType", plantType
            ).addOnSuccessListener(aVoid -> {
                Toast.makeText(this, "Entry updated successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateEntryActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish(); // Close the activity after a successful update
            }).addOnFailureListener(e -> {
                Toast.makeText(this, "Failed to update entry: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        });
    }
}
