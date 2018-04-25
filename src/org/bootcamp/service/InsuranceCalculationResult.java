package org.bootcamp.service;

public final class InsuranceCalculationResult {
    private final String id;
    private final String vehicleTypeName;
    private final double cost;

    public InsuranceCalculationResult(String id, String vehicleTypeName, double cost) {
        this.id = id;
        this.vehicleTypeName = vehicleTypeName;
        this.cost = cost;
    }

    public String getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }

    public String getVehicleTypeName() {
        return vehicleTypeName;
    }
}
