package com.example.agreec;

public class Entry {
    private String id;
    private String date;
    private String fertilizerCategory;
    private String fertilizerAmount;
    private String plantType;

    // Default constructor required for Firestore
    public Entry() {
        // Firestore needs this constructor for mapping data from Firestore to the object
    }

    // Constructor with parameters for creating new instances
    public Entry(String id, String date, String fertilizerCategory, String fertilizerAmount, String plantType) {
        this.id = id;
        this.date = date;
        this.fertilizerCategory = fertilizerCategory;
        this.fertilizerAmount = fertilizerAmount;
        this.plantType = plantType;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getFertilizerCategory() {
        return fertilizerCategory;
    }

    public String getFertilizerAmount() {
        return fertilizerAmount;
    }

    public String getPlantType() {
        return plantType;
    }

    // Setters (If you want to allow updating the values after object creation)
    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFertilizerCategory(String fertilizerCategory) {
        this.fertilizerCategory = fertilizerCategory;
    }

    public void setFertilizerAmount(String fertilizerAmount) {
        this.fertilizerAmount = fertilizerAmount;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }
}
