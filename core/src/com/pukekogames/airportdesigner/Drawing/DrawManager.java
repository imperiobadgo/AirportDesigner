package com.pukekogames.airportdesigner.Drawing;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pukekogames.airportdesigner.Objects.ClickableGameObject;
import com.pukekogames.airportdesigner.Objects.GameObject;
import com.pukekogames.airportdesigner.Objects.Roads.Road;
import com.pukekogames.airportdesigner.Objects.Vehicles.Airplane;
import com.pukekogames.airportdesigner.Objects.Vehicles.Vehicle;
import com.pukekogames.airportdesigner.Screens.GameScreen;
import com.pukekogames.airportdesigner.TextureLoader;

/**
 * Created by Marko Rapka on 08.06.2017.
 */
public class DrawManager {

    public static void draw(SpriteBatch batch, GameObject object) {
        if (object instanceof Road) {
            Road road = (Road) object;
            DrawRoads.draw(batch, road);
        }else if (object instanceof Vehicle){
            Vehicle vehicle = (Vehicle) object;
            Texture texture = TextureLoader.Instance().getTexture(vehicle.getImageID());

            if (texture != null) {

                float scale = 3f;
                if (vehicle instanceof Airplane) scale = 9f;

                DrawManager.drawBatch(batch, texture, vehicle.getAlign_X(), vehicle.getAlign_Y(), vehicle.getHeading() - 90, scale);


            }

        } else if (object instanceof ClickableGameObject) {
            ClickableGameObject clickableGameObject = (ClickableGameObject) object;
            Texture texture = TextureLoader.Instance().getTexture(clickableGameObject.getImageID());

            if (texture != null) {



                DrawManager.drawBatch(batch, texture, clickableGameObject.getX(), clickableGameObject.getY(), clickableGameObject.getHeading() - 90, 1f);


            }
        }
    }

    public static void drawBatch(SpriteBatch batch, Texture tex, float x, float y, float rotation, float scale) {
//        x /= 50;
//        y /= 50;
        batch.draw(tex, x, y, GameScreen.WORLD_WIDTH_METERS / 2, GameScreen.WORLD_HEIGHT_METERS / 2, GameScreen.WORLD_WIDTH_METERS, GameScreen.WORLD_HEIGHT_METERS,
                scale, scale, rotation, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
    }

    public static void drawBatch(SpriteBatch batch, Texture tex, float x, float y,float rotXTexture, float rotYTexture, float rotation, float scale) {
        batch.draw(tex, x, y, rotXTexture, rotYTexture, GameScreen.WORLD_WIDTH_METERS, GameScreen.WORLD_HEIGHT_METERS,
                scale, scale, rotation, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
    }

    public static void drawBatch(SpriteBatch batch, Texture tex, float x, float y,float rotXTexture, float rotYTexture, float rotation, float scaleX, float scaleY) {
        batch.draw(tex, x, y, rotXTexture, rotYTexture, GameScreen.WORLD_WIDTH_METERS, GameScreen.WORLD_HEIGHT_METERS,
                scaleX, scaleY, rotation, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
    }
}
