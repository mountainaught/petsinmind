package com.petsinmind;

// Dimitris
public class Pet {
    private String name;
    private String type;
    private String size;
    private String age;

    // Initial constructor
    public Pet() {
        this.name = null;
        this.type = null;
        this.size = null;
        this.age = null;
    }

    // Constructor with parameters
    public Pet(String name, String type, String size, String age) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.age = age;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
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