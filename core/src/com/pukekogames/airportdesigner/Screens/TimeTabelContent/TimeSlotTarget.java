package com.pukekogames.airportdesigner.Screens.TimeTabelContent;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;

/**
 * Created by Marko Rapka on 31.07.2017.
 */
public class TimeSlotTarget extends DragAndDrop.Target {

    private TimeSlot targetSlot;

    public TimeSlotTarget(SlotActor actor) {
        super(actor);
        targetSlot = actor.getSlot();
        getActor().setColor(Color.LIGHT_GRAY);
    }

    @Override
    public boolean drag(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
        TimeSlot payloadSlot = (TimeSlot) payload.getObject();
        // if (targetSlot.getItem() == payloadSlot.getItem() ||
        // targetSlot.getItem() == null) {
        getActor().setColor(Color.WHITE);
        return true;
        // } else {
        // getActor().setColor(Color.DARK_GRAY);
        // return false;
        // }
    }

    @Override
    public void drop(DragAndDrop.Source source, DragAndDrop.Payload payload, float x, float y, int pointer) {
    }

    @Override
    public void reset(DragAndDrop.Source source, DragAndDrop.Payload payload) {
        getActor().setColor(Color.LIGHT_GRAY);
    }

}
