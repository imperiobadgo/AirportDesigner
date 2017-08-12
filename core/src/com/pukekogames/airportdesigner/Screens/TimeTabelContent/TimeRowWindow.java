package com.pukekogames.airportdesigner.Screens.TimeTabelContent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.GdxBuild;
import com.pukekogames.airportdesigner.Main;

import java.util.ArrayList;

/**
 * Created by Marko Rapka on 31.07.2017.
 */
public class TimeRowWindow extends Dialog {

    TimeTableScreen tableScreen;

    public TimeRowWindow(Main main, TimeTableScreen screen, Skin skin) {
        super("Inventory...", skin);

        tableScreen = screen;

        TextButton closeButton = new TextButton("X", skin);
        closeButton.addListener(new HidingClickListener(this));
        getTitleTable().add(closeButton).height(getPadTop());
//        getButtonTable().add(closeButton).height(getPadTop());

//        setPosition(400, 100);
        defaults().space(8);
        row().fill().expandX();

        Table scrollTable = new Table();


        ArrayList<TimeRow> timeRows = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            timeRows.add(new TimeRow());
        }

        for (TimeRow timeRow : timeRows) {
            int i = 0;
            DragAndDrop dragAndDrop = new DragAndDrop();
            for (TimeSlot slot : timeRow.getTimeSlots()) {
                SlotActor slotActor = new SlotActor(main, screen, skin, slot);
                dragAndDrop.addSource(new TimeSlotSource(main, dragAndDrop, slotActor));
                dragAndDrop.addTarget(new TimeSlotTarget(slotActor));
                scrollTable.add(slotActor);

                i++;
//            if (i % 5 == 0) {
//                row();
//            }
            }
            scrollTable.row();
            scrollTable.add().padBottom(100);
            scrollTable.row();
        }



        ScrollPane scrollPane = new ScrollPane(scrollTable);
        scrollPane.setFlickScroll(true);
        add(scrollPane).fill().expand();
        pack();

        // it is hidden by default
//        setVisible(false);

        setModal(true);
        setMovable(false);
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
}
