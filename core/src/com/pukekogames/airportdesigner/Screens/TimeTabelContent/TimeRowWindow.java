package com.pukekogames.airportdesigner.Screens.TimeTabelContent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.GdxBuild;
import com.pukekogames.airportdesigner.GameContent;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Main;
import com.pukekogames.airportdesigner.Objects.Airlines.PlannedArrival;

import java.util.ArrayList;

/**
 * Created by Marko Rapka on 31.07.2017.
 */
public class TimeRowWindow extends Dialog {

    private Skin skin;
    private Stage stage;
    private Main main;
    private ScrollPane scrollPane;

    public TimeRowWindow(Main main, Stage stage, Skin skin) {
        super("", skin);

        this.main = main;
        this.stage = stage;
        this.skin = skin;
//        TextButton closeButton = new TextButton("X", skin);
//        closeButton.addListener(new HidingClickListener(this));
//        getTitleTable().add(closeButton).height(getPadTop());
//        getButtonTable().add(closeButton).height(getPadTop());

//        setPosition(400, 100);
        defaults().space(8);
        row().fill().expandX();

        Table scrollTable = new Table();

        scrollPane = new ScrollPane(scrollTable, skin);
        scrollPane.setFadeScrollBars(false);


        getContentTable().add(scrollPane).fill().expand();
        pack();

        // it is hidden by default
//        setVisible(false);
        getContentTable().row().padBottom(10f);
        button("OK", "Yes");

        setModal(true);
        setMovable(false);
        setFillParent(true);
    }

    public void setupScreen() {
        Table scrollTable = new Table();

//        GameInstance.Settings().gameType = 1;
//        GameContent.setNewGame();

        ArrayList<TimeRow> timeRows = new ArrayList<>();
        int numberOfGates = GameInstance.Airport().getAllGates().size();
        for (int i = 0; i < numberOfGates; i++) {
            timeRows.add(new TimeRow(i));
        }

        PlannedArrival[] arrivals = GameInstance.AirlineManager().getPlannedArrivals();
        for (PlannedArrival arrival : arrivals) {
            if (arrival.isAccepted()) {
                int hour = arrival.getHour();
                int gateIndex = arrival.getPlannedGateIndex();
                if (gateIndex == -1 || gateIndex > timeRows.size()) {
                    boolean inserted = false;
                    int currentIndex = 0;
                    //put planned arrival in next free time slot
                    while (!inserted && currentIndex < timeRows.size()) {
                        TimeRow currentTimeRow = timeRows.get(currentIndex);
                        if (currentTimeRow.getTimeSlots().get(hour).add(arrival)) {
                            inserted = true;
                        }
                        currentIndex += 1;
                    }
                } else {
                    TimeRow timeRow = timeRows.get(gateIndex);
                    timeRow.getTimeSlots().get(hour).add(arrival);
                }
//                timeRows.add(new TimeRow());
//                TimeSlot slot = slots.get(hour);
//                slot.add(arrival);
            }
        }
        scrollTable.row().padTop(100);
        DragAndDrop dragAndDrop = new DragAndDrop();
        for (int i = 0; i < timeRows.size(); i++) {
            TimeRow timeRow = timeRows.get(i);
            Label label = new Label("Gate " + (i + 1), skin);
            scrollTable.add(label).padRight(50);
            Table rowTable = new Table();


            for (TimeSlot slot : timeRow.getTimeSlots()) {
                SlotActor slotActor = new SlotActor(main, stage, skin, slot);
                dragAndDrop.addSource(new TimeSlotSource(main, dragAndDrop, slotActor));
                dragAndDrop.addTarget(new TimeSlotTarget(slotActor));
//                rowTable.add(slotActor);
                scrollTable.add(slotActor);


            }
//            ScrollPane rowPane = new ScrollPane(rowTable, skin);
//            rowPane.setBounds(0,0,500,100);
//            scrollTable.add(rowPane);
            scrollTable.row();
            scrollTable.add().padBottom(100);
            scrollTable.row();
        }

        scrollPane.setWidget(scrollTable);
//        ScrollPane verticalScrollPane = new ScrollPane(scrollTable, skin);
//        verticalScrollPane.setFadeScrollBars(false);
    }

    @Override
    public float getPrefWidth() {
        // force dialog width
        return Gdx.graphics.getWidth() * 0.8f;
    }

    @Override
    public float getPrefHeight() {
        // force dialog width
        return Gdx.graphics.getHeight() * 0.8f;
    }

    @Override
    protected void result(Object object) {
        super.result(object);
        if (object.equals("Yes")) {
            GameInstance.Airport().setPauseSimulation(false);
        }
    }
}
