package com.example.agreec;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewEntryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EntryAdapter adapter;
    private List<Entry> entryList;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);

        recyclerView = findViewById(R.id.recyclerViewEntries);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        entryList = new ArrayList<>();
        adapter = new EntryAdapter(this, entryList);
        recyclerView.setAdapter(adapter);

        firestore = FirebaseFirestore.getInstance();

        // Fetch data from Firestore
        firestore.collection("entries")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Entry entry = new Entry(
                                document.getId(),
                                document.getString("date"),
                                document.getString("fertilizerCategory"),
                                document.getString("fertilizerAmount"),
                                document.getString("plantType")
                        );
                        entryList.add(entry);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    // Handle error
                });
    }
}
