package com.pukekogames.airportdesigner.Objects.Vehicles.VehicleData;

/**
 * Created by Marko Rapka on 17.05.2016.
 */
public abstract class AirplanePerformance extends VehiclePerformance {
    private static final long serialVersionUID = -6418368711708720226L;

    protected static final float smallCollisionRadius = 250;
    protected static final float bigCollisionRadius = 300;

    public float takeOffSpeed;
    public float landingSpeed;
    public float taxiSpeed;
    public float sinkrate;
    public float climbrate;
    public float collisionRadius;

    protected int airplaneID;
    protected int imageID;
    protected int category;
    protected boolean needTerminal;

    public int getAirplaneID(){return airplaneID;}

    public int getImageID(){
        return imageID;
    }

    public int getCategory(){
        return category;
    }

    public boolean NeedingTerminal(){
        return needTerminal;
    }

    protected void StandardAirplane(){
        maxSpeed = 40f;
        takeOffSpeed = 25f;
        landingSpeed = 20f;
        taxiSpeed = 10f;
        acceleration = 0.05f;
        deceleration = 0.2f;
        sinkrate = 4f;
        climbrate = 6f;
        turnRate = 2f;
        targetPointDistance = 400;
        collisionRadius = bigCollisionRadius;
        needTerminal = true;
    }

    protected void SmallAirplane(){
        maxSpeed = 25f;
        takeOffSpeed = 17f;
        landingSpeed = 15f;
        taxiSpeed = 7f;
        acceleration = 0.03f;
        deceleration = 0.1f;
        sinkrate = 2f;
        climbrate = 3f;
        turnRate = 2f;
        targetPointDistance = 400;
        collisionRadius = smallCollisionRadius;
        needTerminal = false;
    }
}
