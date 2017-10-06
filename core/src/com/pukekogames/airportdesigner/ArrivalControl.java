package com.pukekogames.airportdesigner;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Objects.Vehicles.Airplane;

import java.util.ArrayList;

/**
 * Created by Marko Rapka on 22.09.2017.
 */
public class ArrivalControl {
//    ArrayList<Table> tableList;
    Table table;
    ScrollPane pane;
    Skin skin;
    Label.LabelStyle style;
    private int maxCount = 5;

    public ArrivalControl(Label.LabelStyle style,Skin skin) {
        this.skin = skin;
        this.style = style;
        table = new Table(skin);
        pane = new ScrollPane(table,skin);
//        tableList = new ArrayList<>();

    }

    public Table getTable() {
        return table;
    }

    public ScrollPane getPane() {
        return pane;
    }

    public void updateContent() {
        System.out.println("update arrival control...");
        table.clearChildren();
        ArrayList<Airplane> airplanes = GameInstance.Airport().getNextAirplanes();
        if (airplanes.size() > maxCount){
            int difference = airplanes.size() - maxCount;
            table.add(new Label(difference + " more...", style)).expandY().fillY();
            table.row();
        }
        for (int i = Math.min(airplanes.size() - 1, maxCount); i >= 0; i--) {
            Airplane airplane = airplanes.get(i);
            Table rowTable = new Table(skin);

//            table.add(new Image(texture1)).expandY().fillY();
            rowTable.add(new Label("", style)).width(10f).expandY().fillY();// a spacer
            rowTable.add(new Label(airplane.getCallSign(), style)).expandY().fillY();
            rowTable.add(new Label("", style)).width(10f).expandY().fillY();// a spacer
//            table.add(new Label(airplane., skin)).expandY().fillY();
//            tableList.add(table);
            table.add(rowTable).align(Align.left);
            table.row();
        }
    }

}
