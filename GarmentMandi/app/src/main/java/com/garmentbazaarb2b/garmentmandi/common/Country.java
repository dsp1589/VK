package com.garmentbazaarb2b.garmentmandi.common;

/**
 * Created by lokendra on 4/26/16.
 */
public class Country {

    private int country_id;
    private String country_name;
    private String country_code;
    private String currency_symbol;

    public Country() {
    }

    public Country(int country_id, String country_name, String country_code, String currency_symbol, String currency_code) {
        this.country_id = country_id;
        this.country_name = country_name;
        this.country_code = country_code;
        this.currency_symbol = currency_symbol;
        this.currency_code = currency_code;
    }

    public int getCountry_id() {
        return country_id;
    }

    public void setCountry_id(int country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getCurrency_symbol() {
        return currency_symbol;
    }

    public void setCurrency_symbol(String currency_symbol) {
        this.currency_symbol = currency_symbol;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    private String currency_code;




}
