package com.pukekogames.airportdesigner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Main;
import com.pukekogames.airportdesigner.Objects.Airlines.Airline;
import com.pukekogames.airportdesigner.Objects.Airlines.AirlineList;
import com.pukekogames.airportdesigner.Objects.Vehicles.Airplane;
import com.pukekogames.airportdesigner.TextureLoader;


/**
 * Created by Marko Rapka on 19.08.2017.
 */
public class AirplaneInfoDialog extends Dialog {

    Main main;

    public AirplaneInfoDialog(Main main, String title, Skin skin) {
        super(title, skin);
        this.main = main;

        padTop(60); // set padding on top of the dialog title
        getButtonTable().defaults().height(60).pad(0,20,10,20); // set buttons height
        getButtonTable().defaults().width(200);



        setModal(true);
        setMovable(false);

        button("Yes", "Yes");
        button("No", "No");

    }

    public void setContent(Airplane airplane){
        getContentTable().clear();
        TextureAtlas icons = main.assets.get("airlines/airlines.atlas", TextureAtlas.class);
        TextureRegion image;
        String content = "";
        Airline airline = airplane.getAirline();
        if (airline != null) {
            image = icons.findRegion(AirlineList.getAirlineFileName(airline.getId()));
            content = airplane.getCallSign();
        } else {
            image = icons.findRegion("airline");
        }
        Texture tex = TextureLoader.createTextureWithText(content, image, main.font,128,128,5,30);

        getContentTable().add(new Image(tex));
    }

    @Override
    public float getPrefWidth() {
        // force dialog width
        return Gdx.graphics.getWidth() * 0.3f;
    }

    @Override
    public float getPrefHeight() {
        // force dialog height
        return Gdx.graphics.getHeight() * 0.7f;
    }

    @Override
    protected void result(Object object) {
        super.result(object);
//        if (object.equals("Yes")){
//            if (positiveMethod != null){
//                positiveMethod.execute(object);
//            }
//        }else if(object.equals("No")){
//            if (negativeMethod != null){
//                negativeMethod.execute(object);
//            }
//        }
        GameInstance.Airport().setPauseSimulation(false);
    }
}
