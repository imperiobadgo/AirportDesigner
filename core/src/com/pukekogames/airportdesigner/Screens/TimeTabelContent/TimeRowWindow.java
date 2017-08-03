package com.pukekogames.airportdesigner.Screens.TimeTabelContent;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.pukekogames.airportdesigner.Main;

/**
 * Created by Marko Rapka on 31.07.2017.
 */
public class TimeRowWindow extends Window {

    TimeTableScreen tableScreen;

    public TimeRowWindow(Main main, TimeTableScreen screen, TimeRow timeRow, DragAndDrop dragAndDrop, Skin skin) {
        super("Inventory...", skin);

        tableScreen = screen;

        TextButton closeButton = new TextButton("X", skin);
        closeButton.addListener(new HidingClickListener(this));
        getTitleTable().add(closeButton).height(getPadTop());
//        getButtonTable().add(closeButton).height(getPadTop());

        setPosition(400, 100);
        defaults().space(8);
        row().fill().expandX();

        int i = 0;
        for (TimeSlot slot : timeRow.getTimeSlots()) {
            SlotActor slotActor = new SlotActor(main, screen, skin, slot);
            dragAndDrop.addSource(new TimeSlotSource(main, dragAndDrop, slotActor));
            dragAndDrop.addTarget(new TimeSlotTarget(slotActor));
            add(slotActor);

            i++;
            if (i % 5 == 0) {
                row();
            }
        }

        pack();

        // it is hidden by default
        setVisible(false);
    }

}
