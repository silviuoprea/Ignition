package com.example.ignition;

public class DataModel {
    private String fuelConsumption;
    private String duration;
    private String distance;
    private int id;

    public String getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(String fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDistance() {
        return duration;
    }

    public void setDistance(String distance) {
        this.duration = distance;
    }
    public DataModel(String fuelConsumption, String distance, String duration) {
        this.fuelConsumption = fuelConsumption;
        this.duration = duration;
        this.distance = distance;
    }

}
