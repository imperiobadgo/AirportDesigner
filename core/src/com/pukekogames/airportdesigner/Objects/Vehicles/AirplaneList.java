package com.pukekogames.airportdesigner.Objects.Vehicles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.pukekogames.airportdesigner.Objects.Airlines.AirlineList;
import com.pukekogames.airportdesigner.TextureLoader;

/**
 * Created by Marko Rapka on 22.08.2017.
 */
public class AirplaneList {

    private static Object[][] airplanes = {
            {"A320", "AB"},
            {"A320 Czech", "LH"},
            {"A380", "EK"},
            {"B737", "UD"},
            {"B747", "NA"},
            {"B777", "JE"},
            {"Cessna", "AA"},
            {"Small", "EA"}
    };

    public static void getTexture(Airplane airplane) {
        Texture texture = TextureLoader.Instance().getTexture(airplane.getImageID());
        Texture baseTexture;
        Texture logoTexture;
        Texture t;

        switch (airplane.getPerformance().getAirplaneID()) {
            case 0:
                baseTexture = new Texture(Gdx.files.internal("airplanes\\airplane_a320_color.png"));
                logoTexture = new Texture(Gdx.files.internal("airplanes\\airplane_a320_logo.png"));
                t = AirlineList.setbaseColor(texture,baseTexture, logoTexture, airplane.getAirline());
                break;
            case 1:
                baseTexture = new Texture(Gdx.files.internal("airplanes\\airplane_a320_czech_color.png"));
                logoTexture = new Texture(Gdx.files.internal("airplanes\\airplane_a320_czech_logo.png"));
                t = AirlineList.setbaseColor(texture,baseTexture, logoTexture, airplane.getAirline());
                break;
            case 2:
                baseTexture = new Texture(Gdx.files.internal("airplanes\\airplane_a380_color.png"));
                logoTexture = new Texture(Gdx.files.internal("airplanes\\airplane_a380_logo.png"));
                t = AirlineList.setbaseColor(texture,baseTexture, logoTexture, airplane.getAirline());
                break;
            case 3:
                baseTexture = new Texture(Gdx.files.internal("airplanes\\airplane_b737_alaska_color.png"));
                logoTexture = new Texture(Gdx.files.internal("airplanes\\airplane_b737_alaska_logo.png"));
                t = AirlineList.setbaseColor(texture,baseTexture, logoTexture, airplane.getAirline());
                break;
            case 4:
                baseTexture = new Texture(Gdx.files.internal("airplanes\\airplane_b747_color.png"));
                logoTexture = new Texture(Gdx.files.internal("airplanes\\airplane_b747_logo.png"));
                t = AirlineList.setbaseColor(texture,baseTexture, logoTexture, airplane.getAirline());
                break;
            case 5:
                baseTexture = new Texture(Gdx.files.internal("airplanes\\airplane_777_color.png"));
                logoTexture = new Texture(Gdx.files.internal("airplanes\\airplane_777_logo.png"));
                t = AirlineList.setbaseColor(texture,baseTexture, logoTexture, airplane.getAirline());
                break;
            case 6:
                t = texture;
                break;
            case 7:
                t = texture;
                break;
            default:
                t = texture;
                break;
        }
        airplane.setTexture(t);
    }
}
