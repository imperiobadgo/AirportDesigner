package com.pukekogames.airportdesigner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;

/**
 * Created by Marko Rapka on 21.07.2017.
 */
public class ExitDialog extends Dialog {

    public ExitDialog(String title, Skin skin) {
        super(title, skin);
    }

    public ExitDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }

    public ExitDialog(String title, WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    {
        padTop(60); // set padding on top of the dialog title
        getButtonTable().defaults().height(60).pad(0,20,10,20); // set buttons height
        getButtonTable().defaults().width(200);
        setModal(true);
        setMovable(false);
        text("Do you quit?");
        button("Yes", "Yes");
        button("No", "No");

    }

    @Override
    public float getPrefWidth() {
        // force dialog width
        return 640f;
    }

    @Override
    public float getPrefHeight() {
        // force dialog height
        return 320f;
    }

    @Override
    protected void result(Object object) {
        super.result(object);
        if (object.equals("Yes")){
            Gdx.app.exit();
        }else if(object.equals("No")){
            GameInstance.Airport().setPauseSimulation(false);
        }

    }
}
