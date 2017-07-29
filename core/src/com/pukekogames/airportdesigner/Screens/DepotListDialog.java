package com.pukekogames.airportdesigner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Helper.ClassTranslation.BuildingType;
import com.pukekogames.airportdesigner.Main;
import com.pukekogames.airportdesigner.Objects.Prices;

import java.util.ArrayList;


/**
 * Created by Marko Rapka on 25.07.2017.
 */
public class DepotListDialog extends Dialog {
    private final Main main;
    private ArrayList<String> content = null;
    private com.badlogic.gdx.scenes.scene2d.ui.List list;
    com.badlogic.gdx.scenes.scene2d.ui.Label label;


    public DepotListDialog(Main main, ArrayList<String> content, String title, Skin skin) {
        super(title, skin);
        this.main = main;
        this.content = content;
        padTop(30); // set padding on top of the dialog title
        float factor = 1;
        if (GameInstance.Settings().isStartedOnMobile) {
            factor = 2;
        }
        getButtonTable().defaults().height(60 * factor).pad(0, 20, 10, 20); // set buttons height
        getButtonTable().defaults().width(300);
        setModal(true);
        setMovable(false);

        BitmapFont font = new BitmapFont(Gdx.files.internal("ArialBasic.fnt"), Gdx.files.internal("ArialBasic.png"), false);

        if (GameInstance.Settings().isStartedOnMobile) {
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

    private void setPrice() {
        String result = (String) list.getSelected();
        long price = 0;
        switch (result) {
            case "BusDepot":
                price = Prices.BuildBusDepot;
                break;
            case "CateringDepot":
                price = Prices.BuildCateringDepot;
                break;
            case "CrewBusDepot":
                price = Prices.BuildCrewbusDepot;
                break;
            case "TankDepot":
                price = Prices.BuildTankDepot;
                break;
            case "BaggageDepot":
                price = Prices.BuildBaggageDepot;
                break;
            case "Tower":
                price = Prices.BuildTower;
                break;
            case "Terminal":
                price = Prices.BuildTerminal;
                break;
            default:
                price = 9999999L;
                break;

        }
        String msg = String.format("%1$d Euro", price);
        label.setText(msg);
    }

    @Override
    protected void result(Object object) {
        super.result(object);
        if (object.equals("Yes")) {
            if (GameInstance.Settings().buildMode == 3) {
                BuildingType buildingType = null;
                long price = 0;
                String result = (String) list.getSelected();
                switch (result) {
                    case "BusDepot":
                        buildingType = BuildingType.busDepot;
                        price = Prices.BuildBusDepot;
                        break;
                    case "CateringDepot":
                        buildingType = BuildingType.cateringDepot;
                        price = Prices.BuildCateringDepot;
                        break;
                    case "CrewBusDepot":
                        buildingType = BuildingType.crewBusDepot;
                        price = Prices.BuildCrewbusDepot;
                        break;
                    case "TankDepot":
                        buildingType = BuildingType.tankDepot;
                        price = Prices.BuildTankDepot;
                        break;
                    case "BaggageDepot":
                        buildingType = BuildingType.baggageDepot;
                        price = Prices.BuildBaggageDepot;
                        break;
                    case "Tower":
                        buildingType = BuildingType.tower;
                        price = Prices.BuildTower;
                        break;
                    case "Terminal":
                        buildingType = BuildingType.terminal;
                        price = Prices.BuildTerminal;
                        break;
                    default:
                        buildingType = BuildingType.busDepot;
                        price = 9999999L;
                        break;

                }

                GameInstance.Settings().buildDepot = buildingType;
                GameInstance.Settings().buildPrice = price;
                GameInstance.Settings().selectionCompleted = true;
            }
        }
    }
}
