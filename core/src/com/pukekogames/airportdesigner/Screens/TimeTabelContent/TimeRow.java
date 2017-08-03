package com.pukekogames.airportdesigner.Screens.TimeTabelContent;

import com.badlogic.gdx.utils.Array;
import com.pukekogames.airportdesigner.GameContent;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Objects.Airlines.PlannedArrival;

/**
 * Created by Marko Rapka on 31.07.2017.
 */
public class TimeRow {

    private Array<TimeSlot> slots;

    public TimeRow() {
        slots = new Array<TimeSlot>(24);
        for (int i = 0; i < 24; i++) {
            slots.add(new TimeSlot(null));
        }

//        // create some random items
//        for (TimeSlot slot : slots) {
//            slot.add(Item.values()[MathUtils.random(0, Item.values().length - 1)], 1);
//        }
        GameInstance.Settings().gameType = 1;
        GameContent.setNewGame();
        PlannedArrival[] arrivals = GameInstance.AirlineManager().getPlannedArrivals();
        for (PlannedArrival arrival : arrivals) {
            if (arrival.isAccepted()){
                int hour = arrival.getHour();
                TimeSlot slot = slots.get(hour);
                slot.add(arrival);
            }
        }

//        // create a few random empty slots
//        for (int i = 0; i < 3; i++) {
//            Slot randomSlot = slots.get(MathUtils.random(0, slots.size - 1));
//            randomSlot.take(randomSlot.getAmount());
//        }
    }


    public boolean store(PlannedArrival item, int amount) {
        // first check for a slot with the same item type
        TimeSlot itemSlot = firstSlotWithItem(item);
        if (itemSlot != null) {
            itemSlot.add(item);
            return true;
        } else {
            // now check for an available empty slot
            TimeSlot emptySlot = firstSlotWithItem(null);
            if (emptySlot != null) {
                emptySlot.add(item);
                return true;
            }
        }

        // no slot to add
        return false;
    }

    public Array<TimeSlot> getTimeSlots() {
        return slots;
    }

    private TimeSlot firstSlotWithItem(PlannedArrival item) {
        for (TimeSlot slot : slots) {
            if (slot.getItem() == item) {
                return slot;
            }
        }

        return null;
    }

}
