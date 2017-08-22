package com.pukekogames.airportdesigner.Objects.Vehicles.VehicleData;

import com.pukekogames.airportdesigner.Objects.Images;

/**
 * Created by Marko Rapka on 17.04.2017.
 */
public class AirplaneDataA320Czech extends AirplanePerformance {

    public AirplaneDataA320Czech(){
        StandardAirplane();
        airplaneID = 1;
        imageID = Images.indexAirplaneA320Czech;
        category = 3;
    }
}
