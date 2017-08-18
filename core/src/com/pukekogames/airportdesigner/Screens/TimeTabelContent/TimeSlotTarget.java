package com.pukekogames.airportdesigner.Screens.TimeTabelContent;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.pukekogames.airportdesigner.Objects.Airlines.PlannedArrival;

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
        int hour = payloadSlot.getHour();
        PlannedArrival selectedArrival = payloadSlot.getItem();
//        selectedArrival.
        int currentHour = selectedArrival.getHour();
        int targetHour = selectedArrival.getTargetHour();
        int maxOffset = selectedArrival.getMaxHourOffset();
        boolean isMoveable = false;

        if (targetHour + maxOffset >= targetSlot.getHour() && targetHour - maxOffset <= targetSlot.getHour()){
            getActor().setColor(Color.WHITE);
            isMoveable = true;
        }else{
            getActor().setColor(Color.BLACK);

            isMoveable = false;
        }

//        if (targetHour - maxOffset > 0 && targetHour + maxOffset < 23){
////            currentHour += amount;
//            if (targetHour + maxOffset < targetSlot.getHour()){
//                currentHour = targetHour + maxOffset;
//            }else{
//                isMoveable = true;
//            }
//            if (targetHour - maxOffset > targetSlot.getHour()){
//                currentHour = targetHour - maxOffset;
//            }else{
//                isMoveable = true;
//            }
////            selectedArrival.setHour(currentHour);
////            updateTimeSlotCount();
//        }
        // if (targetSlot.getItem() == payloadSlot.getItem() ||
        // targetSlot.getItem() == null) {
//        getActor().setColor(Color.WHITE);
        return isMoveable;
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
