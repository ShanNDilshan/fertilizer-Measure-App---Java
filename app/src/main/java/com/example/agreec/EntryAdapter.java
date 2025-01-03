package com.example.agreec;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {

    private Context context;
    private List<Entry> entryList;
    private FirebaseFirestore firestore;

    public EntryAdapter(Context context, List<Entry> entryList) {
        this.context = context;
        this.entryList = entryList;
        this.firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.entry_card, parent, false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        Entry entry = entryList.get(position);

        holder.tvDate.setText("Date: " + entry.getDate());
        holder.tvFertilizerCategory.setText("Category: " + entry.getFertilizerCategory());
        holder.tvFertilizerAmount.setText("Amount: " + entry.getFertilizerAmount());
        holder.tvPlantType.setText("Plant Type: " + entry.getPlantType());

        // Handle the Update button click
        holder.btnUpdate.setOnClickListener(v -> {
            // Get the document ID of the clicked entry
            String documentID = entry.getId();

            // Pass the document ID to UpdateEntryActivity using Intent
            Intent intent = new Intent(context, UpdateEntryActivity.class);
            intent.putExtra("DOCUMENT_ID", documentID); // Send the document ID
            context.startActivity(intent);
        });

        // Handle the Delete button click
        holder.btnDelete.setOnClickListener(v -> {
            firestore.collection("entries").document(entry.getId())
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(context, "Entry Deleted", Toast.LENGTH_SHORT).show();
                        entryList.remove(position);
                        notifyItemRemoved(position);
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    public static class EntryViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvFertilizerCategory, tvFertilizerAmount, tvPlantType;
        Button btnUpdate, btnDelete;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvFertilizerCategory = itemView.findViewById(R.id.tvFertilizerCategory);
            tvFertilizerAmount = itemView.findViewById(R.id.tvFertilizerAmount);
            tvPlantType = itemView.findViewById(R.id.tvPlantType);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
