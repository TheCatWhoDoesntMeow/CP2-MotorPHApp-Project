package com.practices.week2;

public class MobilePhone {
    //attributes
    private String brand;
    private String model;

    // Constructor
    public MobilePhone(String brand, String model) {
        this.brand = brand;
        this.model = model;
    }

    public void isTurningOn() {
        System.out.println(brand + " is turning on...");
    }

    public void isTurningOff() {
        System.out.println(brand + " low battery. Shutting down!");
    }

    // Getters
    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    // Setters
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    // Main method
    public static void main(String[] args) {
        // Create a MobilePhone object
        MobilePhone myPhone = new MobilePhone("Tecno", "Camon18Pro");

        // Call methods
        myPhone.isTurningOn();
        myPhone.isTurningOff();

        // Print brand and model
        System.out.println("Brand: " + myPhone.getBrand());
        System.out.println("Model: " + myPhone.getModel());
    }
}
