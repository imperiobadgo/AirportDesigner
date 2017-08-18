package com.pukekogames.airportdesigner.Screens.TimeTabelContent;

import com.badlogic.gdx.utils.Array;
import com.pukekogames.airportdesigner.Objects.Airlines.PlannedArrival;

/**
 * Created by Marko Rapka on 31.07.2017.
 */
public class TimeSlot {

    private final int hour;
    private final int gateIndex;
    private PlannedArrival item;

    private Array<SlotListener> slotListeners = new Array<SlotListener>();

    public TimeSlot(PlannedArrival item, int hour, int gateIndex) {
        this.item = item;
        this.hour = hour;
        this.gateIndex = gateIndex;
    }

    public boolean isEmpty() {
        return item == null;
    }

    public boolean add(PlannedArrival item) {
        if (this.item == item || this.item == null) {
            this.item = item;
            item.setPlannedGateIndex(gateIndex);
            item.setHour(hour);
            notifyListeners();
            return true;
        }

        return false;
    }

    public boolean remove() {
        if (item != null) {
            item = null;
            notifyListeners();
            return true;
        }

        return false;
    }

    public int getHour() {
        return hour;
    }

    public void addListener(SlotListener slotListener) {
        slotListeners.add(slotListener);
    }

    public void removeListener(SlotListener slotListener) {
        slotListeners.removeValue(slotListener, true);
    }

    private void notifyListeners() {
        for (SlotListener slotListener : slotListeners) {
            slotListener.hasChanged(this);
        }
    }

    public PlannedArrival getItem() {
        return item;
    }

    public int getGateIndex(){
        return gateIndex;
    }


    @Override
    public String toString() {
        return "Slot[" + item + "]";
    }
}
