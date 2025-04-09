package com.petsinmind;

// Dimitris
public class Pet {
    private String petID;
    private String name;
    private String type;
    private String size;
    private Integer age;

    // Initial constructor
    public Pet() {
        this.name = null;
        this.type = null;
        this.size = null;
        this.age = null;
    }

    // Constructor with parameters
    public Pet(String name, String type, String size, Integer age) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.age = age;
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