package com.pukekogames.airportdesigner.Drawing;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pukekogames.airportdesigner.Objects.ClickableGameObject;
import com.pukekogames.airportdesigner.Objects.GameObject;
import com.pukekogames.airportdesigner.Objects.Roads.Road;
import com.pukekogames.airportdesigner.TextureLoader;

/**
 * Created by Marko Rapka on 08.06.2017.
 */
public class DrawManager {

    public static void draw(SpriteBatch batch, GameObject object) {
        if (object instanceof Road) {
            Road road = (Road) object;
            DrawRoads.draw(batch, road);
        } else if (object instanceof ClickableGameObject) {
            ClickableGameObject clickableGameObject = (ClickableGameObject) object;
            Texture texture = TextureLoader.Instance().getTexture(clickableGameObject.getImageID());

            if (texture != null) {

                DrawManager.drawBatch(batch, texture, clickableGameObject.getX() / 50 + 300, clickableGameObject.getY() / 50 + 200, clickableGameObject.getHeading() - 90, 0.1f);


            }
        }
    }

    static void drawBatch(SpriteBatch batch, Texture tex, float x, float y, float rotation, float scale) {
        batch.draw(tex, x, y, tex.getWidth() / 2, tex.getHeight() / 2, tex.getWidth(), tex.getHeight(),
                scale, scale, rotation, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
    }
}
