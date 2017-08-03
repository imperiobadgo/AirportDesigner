package com.pukekogames.airportdesigner.Screens.TimeTabelContent;

import com.badlogic.gdx.utils.Array;
import com.pukekogames.airportdesigner.Objects.Airlines.PlannedArrival;

/**
 * Created by Marko Rapka on 31.07.2017.
 */
public class TimeSlot {

    private PlannedArrival item;

    private Array<SlotListener> slotListeners = new Array<SlotListener>();

    public TimeSlot(PlannedArrival item) {
        this.item = item;
    }

    public boolean isEmpty() {
        return item == null;
    }

    public boolean add(PlannedArrival item) {
        if (this.item == item || this.item == null) {
            this.item = item;
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

    @Override
    public String toString() {
        return "Slot[" + item + "]";
    }
}
