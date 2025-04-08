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
    public Pet(String name, String type, String size, Integer age, UUID ownerID) {
        this.petID = UUID.randomUUID();
        this.name = name;
        this.type = type;
        this.size = size;
        this.age = age;
        this.ownerID = ownerID;
    }

    // Getters and Setters
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

    public UUID getPetID() { return petID; }

    public void setPetID(UUID petID) { this.petID = petID; }

    public UUID getOwnerID() { return ownerID; }

    public void setOwnerID(UUID ownerID) { this.ownerID = ownerID; }

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