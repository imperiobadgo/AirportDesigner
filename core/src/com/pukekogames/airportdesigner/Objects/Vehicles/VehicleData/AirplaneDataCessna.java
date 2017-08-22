package com.pukekogames.airportdesigner.Objects.Vehicles.VehicleData;

import com.pukekogames.airportdesigner.Objects.Images;

/**
 * Created by Marko Rapka on 19.03.2017.
 */
public class AirplaneDataCessna extends AirplanePerformance {

    public AirplaneDataCessna(){
        SmallAirplane();
        airplaneID = 6;
        imageID = Images.indexAirplaneCessna;
        category = 2;
    }
}
