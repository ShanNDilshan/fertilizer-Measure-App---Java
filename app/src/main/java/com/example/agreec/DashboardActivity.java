package com.example.agreec;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private Button btnAddEntry, btnViewEntry;
    private RecyclerView recyclerView;
    private EntryAdapter entryAdapter;
    private List<Entry> entryList;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_screen);

        // Initialize UI components
        btnAddEntry = findViewById(R.id.btnAddEntry);
        btnViewEntry = findViewById(R.id.btnViewEntry);
        recyclerView = findViewById(R.id.recyclerView); // RecyclerView to show the list
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        entryList = new ArrayList<>();
        entryAdapter = new EntryAdapter(this, entryList);
        recyclerView.setAdapter(entryAdapter);

        firestore = FirebaseFirestore.getInstance();

        // Add Entry button click listener
        btnAddEntry.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AddEntryActivity.class);
            startActivity(intent);
        });

        // View Entry button click listener
        btnViewEntry.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ViewEntryActivity.class);
            startActivity(intent);
        });

        // Listen to changes in the Firestore collection in real-time
//        firestore.collection("entries")
//                .orderBy("date", Query.Direction.ASCENDING) // Adjust this based on your needs
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
//                        if (e != null) {
//                            Toast.makeText(DashboardActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                            return;
//                        }
//
//                        // Clear current data and add updated data
//                        entryList.clear();
//                        for (DocumentSnapshot snapshot : value.getDocuments()) {
//                            Entry entry = snapshot.toObject(Entry.class);
//                            if (entry != null) {
//                                entry.setId(snapshot.getId()); // Setting the document ID
//                                entryList.add(entry);
//                            }
//                        }
//
//                        // Notify the adapter to refresh the RecyclerView
//                        entryAdapter.notifyDataSetChanged();
//                    }
//                });
    }
}
