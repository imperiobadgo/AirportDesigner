package com.pukekogames.airportdesigner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Helper.Command;
import com.pukekogames.airportdesigner.Helper.CommonMethods;
import com.pukekogames.airportdesigner.Helper.GameSave;
import com.pukekogames.airportdesigner.Main;

import java.util.ArrayList;

/**
 * Created by Marko Rapka on 23.07.2017.
 */
public class SaveDialog extends Dialog {

    Main main;

    Stage callerStage;
    int selectedSlot;
    Label label;

    public SaveDialog(Main main, Stage stage, String title, Skin skin) {
        super(title, skin);
        this.main = main;
        callerStage = stage;
    }

    public SaveDialog(String title, Skin skin, String windowStyleName) {
        super(title, skin, windowStyleName);
    }

    public SaveDialog(String title, Window.WindowStyle windowStyle) {
        super(title, windowStyle);
    }

    {
        padTop(30); // set padding on top of the dialog title
        float factor = 1;
        if (GameInstance.Settings().isStartedOnMobile){
            factor = 2;
        }
        getButtonTable().defaults().height(60 * factor).pad(0, 20, 10, 20); // set buttons height
        getButtonTable().defaults().width(300);
        setModal(true);
        setMovable(false);
        final List list = new List(getSkin());
        label = new Label("", getSkin());

        ArrayList<String> myStringArray = new ArrayList<String>();
        for (int i = 0; i < GameSave.SAVECOUNT; i++) {
            myStringArray.add("Save" + i);
        }

        list.setItems(myStringArray.toArray());


        final ConfirmDialog dialog = new ConfirmDialog(main, "Overwrite Save?", "", getSkin());

        dialog.setOnPositiveResult(new Command() {
            @Override
            public void execute(Object object) {
                saveAirport();
            }
        });

        list.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectedSlot = list.getSelectedIndex();
            }
        });

        ScrollPane scrollPane = new ScrollPane(list, getSkin());

        getContentTable().add(scrollPane).expand().fill().colspan(2);
        getContentTable().row().padBottom(10f);
        getContentTable().add(label).colspan(2);
        getContentTable().row().height(60 * factor).width(100 * factor);
        TextButton saveButton = new TextButton("Save", getSkin());
        TextButton loadButton = new TextButton("Load", getSkin());

//        saveButton.setHeight(400f);
//        loadButton.setHeight(100f);

        getContentTable().add(saveButton).padBottom(10);
        getContentTable().add(loadButton).padBottom(10);

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (GameSave.Instance().gameInstances[selectedSlot] == null) {
                    saveAirport();
                } else {
                    if (GameSave.Instance().gameInstances[selectedSlot].equals(GameInstance.Instance())) {

                        dialog.show(callerStage);

                    } else {
                        saveAirport();
                    }
                }
            }
        });

        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadAirport();
            }
        });

        setCurrentSaveText();
        button("Back to Menu", "Yes");
        button("Continue", "No");

    }

    void saveAirport() {
        int currentSlot = selectedSlot;
        GameSave.Instance().currentSlot = currentSlot;
        setCurrentSaveText();
        GameSave.Instance().gameInstances[currentSlot] = GameInstance.Instance();
    }

    void loadAirport() {
        int currentSlot = selectedSlot;
        GameSave.Instance().currentSlot = currentSlot;
        setCurrentSaveText();
        GameInstance.setGameInstance(GameSave.Instance().gameInstances[currentSlot]);
        CommonMethods.loadAllObjectReferences(GameInstance.Airport());
    }

    private void setCurrentSaveText() {
        String msg = String.format("Current save %1$d", GameSave.Instance().currentSlot);
        label.setText(msg);
    }

    @Override
    public float getPrefWidth() {
        // force dialog width
        return 800f;
    }

    @Override
    public float getPrefHeight() {
        // force dialog height
        return 480f;
    }

    @Override
    protected void result(Object object) {
        super.result(object);
        if (object.equals("Yes")) {
            main.setScreen(new MainMenuScreen(main));
        } else if (object.equals("No")) {
            GameInstance.Airport().setPauseSimulation(false);
        }

    }

}
