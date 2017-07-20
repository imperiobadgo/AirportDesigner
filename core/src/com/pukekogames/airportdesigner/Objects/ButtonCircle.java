package com.pukekogames.airportdesigner.Objects;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Helper.Alignment;
import com.pukekogames.airportdesigner.Objects.Buildings.Depot;
import com.pukekogames.airportdesigner.UiManager;

import javax.swing.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Marko Rapka on 17.07.2017.
 */
public class ButtonCircle extends GameObject{

    private UiManager uiManager;
    private GameObject targetObject;
    private CopyOnWriteArrayList<ImageButton> buttons = new CopyOnWriteArrayList<>();
    private int radius = 40;
    private int cooldown;

    private float diffWidth;
    private float diffHeight;

    public ButtonCircle(UiManager uiManager){
        super(Alignment.Table, 0, 0);
        this.uiManager = uiManager;
        cooldown = GameInstance.Settings().SelectionTime;
        radius = GameInstance.Settings().circleButtonWidth * 2;
    }

    @Override
    public void tick() {
        cooldown--;
        if (buttons.size() == 0 || targetObject == null) return;
        float anglePerButton = 360 / buttons.size();

        float x , y;

        if (targetObject instanceof Depot){
            Depot depot = (Depot) targetObject;
            x = depot.getRoad().getCenterPosition().x;
            y = depot.getRoad().getCenterPosition().y;
        }else{
            x = targetObject.getX();
            y = targetObject.getY();
        }


        for (int i = 0; i < buttons.size(); i++) {



            UiManager.worldPos.set(x, y, 0);
            UiManager.screenPos.set(UiManager.worldPos);

            uiManager.projectVector(UiManager.screenPos);

            int xPos = (int) Math.round(Math.cos(Math.toRadians((270 + anglePerButton * i) % 360)) * radius + UiManager.screenPos.x);
            int yPos = (int) Math.round(Math.sin(Math.toRadians((270 + anglePerButton * i) % 360)) * radius + UiManager.screenPos.y);
            try {
                ImageButton button = buttons.get(i);
                int width = (int) button.getWidth();
                int height = (int) button.getHeight();

                button.setPosition(xPos + diffWidth - width / 2, yPos + diffHeight - height / 2);
            }catch (Exception e){
                //problem between mainthread and event
                return;
            }
        }
    }

    public void setObject(GameObject object){
        targetObject = object;
        cooldown = GameInstance.Settings().SelectionTime;
    }

    public void setDiffs(float width, float height){
        diffHeight = height;
        diffWidth = width;
    }

    public void addButton(ImageButton button) {
        buttons.add(button);
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setTargetObject(GameObject targetObject) {
        this.targetObject = targetObject;
    }

    public void clearButtons(){
        buttons.clear();
    }

    public CopyOnWriteArrayList<ImageButton> getButtons() {
        return buttons;
    }

    public boolean shouldRemove() {
        return cooldown < 0 && buttons.size() > 0;
    }

    public void setCooldown() {
        cooldown = 0;
    }
}
