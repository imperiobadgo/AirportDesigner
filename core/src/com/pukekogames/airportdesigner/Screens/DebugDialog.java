package com.pukekogames.airportdesigner.Screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Main;
import com.pukekogames.airportdesigner.Settings;

/**
 * Created by Marko Rapka on 10.09.2017.
 */
public class DebugDialog extends Dialog {

    public DebugDialog(Main main, String title, Skin skin) {
        super(title, skin);

//        setModal(true);
//        setMovable(false);

        TextButton showRoadButton = new TextButton("ShowRoads", getSkin());
        final Label showRoadLabel = new Label("" + GameInstance.Settings().DebugShowRoad, getSkin());

        showRoadButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameInstance.Settings().DebugShowRoad = !GameInstance.Settings().DebugShowRoad;
                showRoadLabel.setText("" + GameInstance.Settings().DebugShowRoad);
            }
        });

        TextButton showAirplaneButton = new TextButton("ShowAirplanes", getSkin());
        final Label showAirplanesLabel = new Label("" + GameInstance.Settings().DebugShowAirplanes, getSkin());

        showAirplaneButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameInstance.Settings().DebugShowAirplanes = !GameInstance.Settings().DebugShowAirplanes;
                showAirplanesLabel.setText("" + GameInstance.Settings().DebugShowAirplanes);
            }
        });

        TextButton showVehiclePathButton = new TextButton("ShowVehiclePath", getSkin());
        final Label showVehiclePathLabel = new Label("" + GameInstance.Settings().DebugShowAirplanes, getSkin());

        showVehiclePathButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameInstance.Settings().DebugShowVehiclePath = !GameInstance.Settings().DebugShowVehiclePath;
                showVehiclePathLabel.setText("" + GameInstance.Settings().DebugShowVehiclePath);
            }
        });

        TextButton showVehicleHeadingButton = new TextButton("ShowVehicleHeading", getSkin());
        final Label showVehicleHeadingLabel = new Label("" + GameInstance.Settings().DebugShowAirplanes, getSkin());

        showVehicleHeadingButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameInstance.Settings().DebugShowVehicleHeading = !GameInstance.Settings().DebugShowVehicleHeading;
                showVehicleHeadingLabel.setText("" + GameInstance.Settings().DebugShowVehicleHeading);
            }
        });

        TextButton showTableButton = new TextButton("ShowTables", getSkin());
        final Label showTableLabel = new Label("" + GameInstance.Settings().DebugShowTables, getSkin());

        showTableButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameInstance.Settings().DebugShowTables = !GameInstance.Settings().DebugShowTables;
                showTableLabel.setText("" + GameInstance.Settings().DebugShowTables);
                Settings.Instance().uiManager.showTableDebug(GameInstance.Settings().DebugShowTables);
            }
        });

        getContentTable().add(showRoadButton);
        getContentTable().add(showRoadLabel);
        getContentTable().row().padBottom(10);
        getContentTable().add(showAirplaneButton);
        getContentTable().add(showAirplanesLabel);
        getContentTable().row().padBottom(10);
        getContentTable().add(showVehiclePathButton);
        getContentTable().add(showVehiclePathLabel);
        getContentTable().row().padBottom(10);
        getContentTable().add(showVehicleHeadingButton);
        getContentTable().add(showVehicleHeadingLabel);
        getContentTable().row().padBottom(10);
        getContentTable().add(showTableButton);
        getContentTable().add(showTableLabel);
        getContentTable().row().padBottom(10);

        button("OK", "OK");
    }

    @Override
    public float getPrefWidth() {
        // force dialog width
        return 500f;
    }

    @Override
    public float getPrefHeight() {
        // force dialog height
        return 600f;
    }

    @Override
    protected void result(Object object) {
        super.result(object);
        GameInstance.Airport().setPauseSimulation(false);

    }
}
