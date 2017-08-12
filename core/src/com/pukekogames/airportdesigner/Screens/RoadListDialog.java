package com.pukekogames.airportdesigner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Helper.ClassTranslation.RoadType;
import com.pukekogames.airportdesigner.Helper.Command;
import com.pukekogames.airportdesigner.Helper.GameSave;
import com.pukekogames.airportdesigner.Main;
import com.pukekogames.airportdesigner.Objects.Prices;

import java.util.ArrayList;

/**
 * Created by Marko Rapka on 24.07.2017.
 */
public class RoadListDialog extends Dialog {
    private final Main main;
    private ArrayList<String> content = null;
    private List list;
    Label label;


    public RoadListDialog(Main main, ArrayList<String> content, String title, Skin skin) {
        super(title, skin);
        this.main = main;
        this.content = content;

        padTop(30); // set padding on top of the dialog title
        float factor = 1;
        if (Main.IS_STARTED_ON_MOBILE) {
            factor = 2;
        }
        getButtonTable().defaults().height(60 * factor).pad(0, 20, 10, 20); // set buttons height
        getButtonTable().defaults().width(300);
        setModal(true);
        setMovable(false);

        BitmapFont font = new BitmapFont(Gdx.files.internal("ArialBasic.fnt"), Gdx.files.internal("ArialBasic.png"), false);

        if (Main.IS_STARTED_ON_MOBILE) {
            font.getData().setScale(1.8f);
        }

        Drawable drawable = getSkin().getDrawable("default-rect-pad");

        List.ListStyle style = new List.ListStyle(font, Color.CYAN, Color.WHITE, drawable);
        list = new List(style);
        label = new Label("", getSkin());

        if (content != null) list.setItems(content.toArray());

//        final ConfirmDialog dialog = new ConfirmDialog(main, "Overwrite Save?", "", getSkin());
//
//        dialog.setOnPositiveResult(new Command() {
//            @Override
//            public void execute(Object object) {
//                saveAirport();
//            }
//        });
//
        final Dialog caller = this;
        list.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                selectedSlot = list.getSelectedIndex();
//                caller.hide();
                setPrice();
            }
        });

        ScrollPane scrollPane = new ScrollPane(list, getSkin());

        getContentTable().add(scrollPane).expand().fill().colspan(2);
        getContentTable().row().padBottom(10f);
        getContentTable().add(label).colspan(2);
        getContentTable().row().padBottom(10f);
        button("Select", "Yes");
        setPrice();
    }

    @Override
    public float getPrefWidth() {
        // force dialog width
        return 480f;
    }

    @Override
    public float getPrefHeight() {
        // force dialog height
        return 480f;
    }

    private void setPrice(){
        String result = (String) list.getSelected();
        long price = 0;
        switch (result) {
            case "Taxiway":
                price = Prices.TaxiwayCostPerHundred;
                break;
            case "Runway":
                price = Prices.RunwayCostPerHundred;
                break;
            case "Street":
                price = Prices.StreetCostPerHundred;
                break;
            case "ParkGate":
                price = Prices.ParkGateBuildPrice;
                break;

        }
        String msg = String.format("%1$d Euro", price);
        label.setText(msg);
    }

    @Override
    protected void result(Object object) {
        super.result(object);
        if (object.equals("Yes")) {
            if (GameInstance.Settings().buildMode == 1) {
                RoadType roadType = null;
                long price = 0;
                String result = (String) list.getSelected();
                switch (result) {
                    case "Taxiway":
                        roadType = RoadType.taxiway;
                        price = Prices.TaxiwayCostPerHundred;
                        break;
                    case "Runway":
                        roadType = RoadType.runway;
                        price = Prices.RunwayCostPerHundred;
                        break;
                    case "Street":
                        roadType = RoadType.street;
                        price = Prices.StreetCostPerHundred;
                        break;
                    case "ParkGate":
                        roadType = RoadType.parkGate;
                        price = Prices.ParkGateBuildPrice;
                        break;

                }
                if (roadType != null) {
                    GameInstance.Settings().buildRoad = roadType;
                    GameInstance.Settings().buildPrice = price;
                    GameInstance.Settings().buildRoadSelected = true;
                    Screen screen = main.getScreen();
                    if (screen instanceof GameScreen){
                        GameScreen gameScreen = (GameScreen) screen;
                        gameScreen.getUiManager().setSelectableRoadIntersections(null);
                    }
                }
            }
        }

    }

}
