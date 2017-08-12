package com.pukekogames.airportdesigner.Screens.TimeTabelContent;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.pukekogames.airportdesigner.Helper.Alignment;
import com.pukekogames.airportdesigner.Main;
import com.pukekogames.airportdesigner.Objects.Airlines.Airline;
import com.pukekogames.airportdesigner.Objects.Airlines.AirlineList;
import com.pukekogames.airportdesigner.Objects.Airlines.PlannedArrival;

/**
 * Created by Marko Rapka on 31.07.2017.
 */
public class TimeSlotSource extends DragAndDrop.Source {

    private TimeSlot sourceSlot;
    private Main main;
    private DragAndDrop dragAndDrop;

    public TimeSlotSource(Main main,DragAndDrop dragAndDrop, SlotActor actor) {
        super(actor);
        this.main = main;
        this.dragAndDrop = dragAndDrop;
        this.sourceSlot = actor.getSlot();
    }

    @Override
    public DragAndDrop.Payload dragStart(InputEvent event, float x, float y, int pointer) {
        if (sourceSlot.getItem() == null) {
            return null;
        }

        DragAndDrop.Payload payload = new DragAndDrop.Payload();
        TimeSlot payloadSlot = new TimeSlot(sourceSlot.getItem());
        sourceSlot.remove();
        payload.setObject(payloadSlot);
        Airline airline = payloadSlot.getItem().getAirline();
        TextureAtlas icons = main.assets.get("airlines/airlines.atlas", TextureAtlas.class);
        TextureRegion icon = icons.findRegion(AirlineList.getAirlineFileName(airline.getId()));


        Actor dragActor = new Image(icon);
        payload.setDragActor(dragActor);

        Actor validDragActor = new Image(icon);
        // validDragActor.setColor(0, 1, 0, 1);
        dragAndDrop.setDragActorPosition(dragActor.getWidth() - x , dragActor.getHeight() - y - dragActor.getHeight());
        payload.setValidDragActor(validDragActor);


        Actor invalidDragActor = new Image(icon);
        // invalidDragActor.setColor(1, 0, 0, 1);
        payload.setInvalidDragActor(invalidDragActor);

//        Drawable image = new SpriteDrawable(new Sprite(new Texture("badlogic.jpg")));
//
//        Actor dragActor =  new Image(image);
//        payload.setDragActor(dragActor);
//
//        Actor validDragActor = new Image(image);
//         validDragActor.setColor(0, 1, 0, 1);
//        payload.setValidDragActor(validDragActor);
//
//        Actor invalidDragActor = new Image(image);
//         invalidDragActor.setColor(1, 0, 0, 1);
//        payload.setInvalidDragActor(invalidDragActor);

        return payload;
    }

    @Override
    public void dragStop(InputEvent event, float x, float y, int pointer, DragAndDrop.Payload payload, DragAndDrop.Target target) {
        TimeSlot payloadSlot = (TimeSlot) payload.getObject();
        if (target != null) {
            TimeSlot targetSlot = ((SlotActor) target.getActor()).getSlot();
            if (targetSlot.getItem() == null) {
                targetSlot.add(payloadSlot.getItem());
            } else {
                sourceSlot.add(payloadSlot.getItem());
//                PlannedArrival targetType = targetSlot.getItem();
//                int targetAmount = targetSlot.getAmount();
//                targetSlot.take(targetAmount);
//                targetSlot.add(payloadSlot.getItem(), payloadSlot.getAmount());
//                sourceSlot.add(targetType, targetAmount);
            }
        } else {
            sourceSlot.add(payloadSlot.getItem());
        }
    }
}
