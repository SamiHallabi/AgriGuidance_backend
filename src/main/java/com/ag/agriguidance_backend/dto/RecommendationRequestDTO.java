package com.ag.agriguidance_backend.dto;

public class RecommendationRequestDTO {
    private String plantName;
    private double surface;
    private String problemDescription;

    // Getters and setters
    public String getPlantName() { return plantName; }
    public void setPlantName(String plantName) { this.plantName = plantName; }

    public double getSurface() { return surface; }
    public void setSurface(double surface) { this.surface = surface; }

    public String getProblemDescription() { return problemDescription; }
    public void setProblemDescription(String problemDescription) { this.problemDescription = problemDescription; }
}
