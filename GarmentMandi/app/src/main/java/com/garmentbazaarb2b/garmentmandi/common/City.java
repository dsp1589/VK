package com.garmentbazaarb2b.garmentmandi.common;

/**
 * Created by lokendra on 4/26/16.
 */
public class City {

    private int city_id;
    private String city_name;

    public City() {
    }

    public City(int city_id, String city_name) {
        this.city_id = city_id;
        this.city_name = city_name;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
