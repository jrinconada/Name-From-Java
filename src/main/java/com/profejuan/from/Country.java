
package com.profejuan.from;

import java.util.List;
import java.util.Locale;

public class Country {
    final String id;
    final double probability; 

    public Country(String id, double probability) {
        this.id = id;
        this.probability = probability;
    }

    @Override
    public String toString() {
        return isoCodeToName(id) + " " + Math.round(probability * 100) + "%";
    }

    private String isoCodeToName(String code) {                
        var locale = new Locale("", code);
        return locale.getDisplayCountry();
    }
   
    static String[] toStringList(List<Country> countries) {
        String[] countriesArray = new String[countries.size()];
        for (int i = 0; i < countries.size(); i++) {
            countriesArray[i] = countries.get(i).toString();            
        }
        return countriesArray;
    }
}
