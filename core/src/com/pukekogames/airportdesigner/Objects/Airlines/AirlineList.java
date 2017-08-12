package com.pukekogames.airportdesigner.Objects.Airlines;

/**
 * Created by Marko Rapka on 19.03.2017.
 */
public class AirlineList {

    public static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVW";

    private static Object[][] airlines = {
            {"Air Berlin", "AB", "airberlin"},
            {"Lufthansa", "LH", "lufthansa"},
            {"Emirates", "EK", "emirates"},
            {"United", "UD", "united"},
            {"North Air", "NA", "northcentral"},
            {"Jet Airlines", "JE", "jetairways"},
            {"American Airlines", "AA", "americanairlines"},
            {"Eastern Air", "EA", "chinaeastern"}
    };

    public static String getAirlineName(int id){
        if (id < 0 || id > airlines.length) return "Airline";
        return (String) airlines[id][0];
    }

    public static String getAirlineCode(int id){
        if (id < 0 || id > airlines.length) return "AIR";
        return (String) airlines[id][1];
    }
    public static String getAirlineFileName(int id){
        if (id < 0 || id > airlines.length) return "airline";
        return (String) airlines[id][2];
    }

    public static int maxIndex(){
        return airlines.length;
    }
}
