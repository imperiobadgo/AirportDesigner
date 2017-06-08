package com.pukekogames.airportdesigner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.pukekogames.airportdesigner.Objects.Images;

import java.util.ArrayList;

/**
 * Created by Marko Rapka on 08.06.2017.
 */
public class TextureLoader {

    private static TextureLoader ourInstance = new TextureLoader();

    public static TextureLoader Instance() {
        return ourInstance;
    }

    Texture[] textures = new Texture[100];

    public void loadTextures(){
        textures[Images.indexAirplaneSmall] = new Texture(Gdx.files.internal("airplane_small.png"));
        textures[Images.indexAirplaneCessna] = new Texture(Gdx.files.internal("airplane_cessna.png"));
        textures[Images.indexAirplaneA320] = new Texture(Gdx.files.internal("airplane_a320.png"));
        textures[Images.indexAirplaneA320Czech] = new Texture(Gdx.files.internal("airplane_a320_czech.png"));
        textures[Images.indexAirplaneB737Alaska] = new Texture(Gdx.files.internal("airplane_b737_alaska.png"));
        textures[Images.indexAirplane777] = new Texture(Gdx.files.internal("airplane_777.png"));
        textures[Images.indexAirplane747] = new Texture(Gdx.files.internal("airplane_b747.png"));
        textures[Images.indexAirplane380] = new Texture(Gdx.files.internal("airplane_a380.png"));

        textures[Images.indexBus] = new Texture(Gdx.files.internal("bus.png"));
        textures[Images.indexCateringTruck] = new Texture(Gdx.files.internal("cateringtruck.png"));
        textures[Images.indexCrewBus] = new Texture(Gdx.files.internal("crewbus.png"));
        textures[Images.indexTankTruck] = new Texture(Gdx.files.internal("tanktruck.png"));
        textures[Images.indexBaggageTruck] = new Texture(Gdx.files.internal("baggagetruck.png"));

        textures[Images.indexRunwayEnd] = new Texture(Gdx.files.internal("runway_end.jpg"));
        textures[Images.indexRunwayMiddle] = new Texture(Gdx.files.internal("runway_middle.jpg"));
        textures[Images.indexTaxiway] = new Texture(Gdx.files.internal("taxiway.jpg"));
        textures[Images.indexStreet] = new Texture(Gdx.files.internal("street.jpg"));
        textures[Images.indexParkGate] = new Texture(Gdx.files.internal("parkgate.jpg"));

        textures[Images.indexBusDepot] = new Texture(Gdx.files.internal("depot_bus.png"));
        textures[Images.indexCateringDepot] = new Texture(Gdx.files.internal("depot_catering.png"));
        textures[Images.indexCrewBusDepot] = new Texture(Gdx.files.internal("depot_crewbus.png"));
        textures[Images.indexTankDepot] = new Texture(Gdx.files.internal("depot_tank.png"));
        textures[Images.indexBaggageDepot] = new Texture(Gdx.files.internal("depot_baggage.png"));
        textures[Images.indexTower] = new Texture(Gdx.files.internal("tower.png"));
        textures[Images.indexTerminal] = new Texture(Gdx.files.internal("terminal.png"));
    }

    public Texture getTexture(int textureIndex){
        if (textureIndex > 0 && textureIndex < textures.length) return textures[textureIndex];
        return null;
    }
}
