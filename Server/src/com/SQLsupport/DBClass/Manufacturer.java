package com.SQLsupport.DBClass;

import java.io.Serializable;

public class Manufacturer implements Serializable {
    private int id_manufacturer;
    private String name;
    private String country;
    private String email;
    private double rating;
    private int number_of_ratings;

    public Manufacturer(int id_manufacturer, String name, String country, String email, double rating, int number_of_ratings) {
        this.id_manufacturer = id_manufacturer;
        this.name = name;
        this.country = country;
        this.email = email;
        this.rating = rating;
        this.number_of_ratings = number_of_ratings;
    }

    public int getId_manufacturer() {
        return id_manufacturer;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    public double getRating() {
        return rating;
    }

    public int getNumber_of_ratings() {
        return number_of_ratings;
    }
}
