package com.petsinmind;

import java.util.UUID;

// Dimitris
public class Pet {
    private UUID petID;
    private String name;
    private String type;
    private String size;
    private Integer age;

    private UUID ownerID;

    // Initial constructor
    public Pet() {
        this.name = null;
        this.type = null;
        this.size = null;
        this.age = null;
    }

    // Constructor with parameters
    public Pet(UUID petID) {
        this.petID = petID;
    }

    public Pet(String petName, String petType, String petSize, Integer petAge) {
        this.petID = UUID.randomUUID();
        this.name = petName;
        this.type = petType;
        this.size = petSize;
        this.age = petAge;
    }

    // Getters and Setters
    public String getPetID() {
        return petID;
    }

    public void setPetID(String petID) {
        this.petID = petID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public UUID getPetID() {
        return petID;
    }

    public void setPetID(UUID petID) {
        this.petID = petID;
    }

    public UUID getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(UUID ownerID) {
        this.ownerID = ownerID;
    }

    // End of getters and setters

    // toString method
    public String toString() {
        return "Pet{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", age='" + age + '\'' +
                '}';
    }

}