package com.garmentbazaarb2b.garmentmandi.interfaces;

import com.garmentbazaarb2b.garmentmandi.common.City;
import com.garmentbazaarb2b.garmentmandi.common.Country;

import java.util.List;

/**
 * Created by lokendra on 4/25/16.
 */
public interface IcRegister {
    void CountryFetched(List<Country> countries);


    void OnCityFetched(List<City> cities);



    void OnVerificationCodeFetched();

    void OnDataSaved(String status);

}


