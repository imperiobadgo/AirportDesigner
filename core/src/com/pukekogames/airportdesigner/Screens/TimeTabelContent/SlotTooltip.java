package com.pukekogames.airportdesigner.Screens.TimeTabelContent;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * Created by Marko Rapka on 31.07.2017.
 */
public class SlotTooltip extends Window implements SlotListener {

    private Skin skin;

    private TimeSlot slot;

    public SlotTooltip(TimeSlot slot, Skin skin) {
        super("", skin);
        this.slot = slot;
        this.skin = skin;
        hasChanged(slot);
        slot.addListener(this);
        setVisible(false);
    }

    @Override
    public void hasChanged(TimeSlot slot) {
        if (slot.isEmpty()) {
            setVisible(false);
            return;
        }

        // title displays the amount
//        setTitle(slot.getAmount() + "x " + slot.getItem());
        clear();
        Label label = new Label(String.format("%1$d Uhr: %2$s %3$s",slot.getItem().getHour(),slot.getItem().getAirline().getAirlineName(), slot.getItem().getCallSign()), skin);
        add(label);
        pack();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        // the listener sets this to true in case the slot is hovered
        // however, we don't want that in case the slot is empty
        if (slot.isEmpty()) {
            super.setVisible(false);
        }
    }
}
