package com.pukekogames.airportdesigner.Screens;

import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.pukekogames.airportdesigner.Helper.Command;
import com.pukekogames.airportdesigner.Main;

/**
 * Created by Marko Rapka on 23.07.2017.
 */
public class ConfirmDialog extends Dialog {

    Main main;
    String content;
    Command positiveMethod;
    Command negativeMethod;

    public ConfirmDialog(Main main,String content, String title, Skin skin) {
        super(title, skin);
        this.main = main;
        this.content = content;
        text(content);
    }

    public ConfirmDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }

    public ConfirmDialog(String title, Window.WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    {
        padTop(60); // set padding on top of the dialog title
        getButtonTable().defaults().height(60).pad(0,20,10,20); // set buttons height
        getButtonTable().defaults().width(200);
        setModal(true);
        setMovable(false);

        button("Yes", "Yes");
        button("No", "No");

    }

    @Override
    public float getPrefWidth() {
        // force dialog width
        return 500f;
    }

    @Override
    public float getPrefHeight() {
        // force dialog height
        return 200f;
    }

    public void setOnPositiveResult(Command method) {
        this.positiveMethod = method;
    }

    public void setOnNegativeResult(Command method) {
        this.negativeMethod = method;
    }

    @Override
    protected void result(Object object) {
        super.result(object);
        if (object.equals("Yes")){
            if (positiveMethod != null){
                positiveMethod.execute(object);
            }
        }else if(object.equals("No")){
            if (negativeMethod != null){
                negativeMethod.execute(object);
            }
        }

    }

}
