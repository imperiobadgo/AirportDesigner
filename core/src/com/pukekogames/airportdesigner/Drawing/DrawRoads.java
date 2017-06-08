package com.pukekogames.airportdesigner.Drawing;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pukekogames.airportdesigner.Objects.Roads.Road;
import com.pukekogames.airportdesigner.TextureLoader;

/**
 * Created by Marko Rapka on 08.06.2017.
 */
public class DrawRoads {

    static void draw(SpriteBatch batch, Road road){
        Texture texture = TextureLoader.Instance().getTexture(road.getImageID());

        if (texture != null){
            for (int i = 0; i < 10; i++) {
                DrawManager.drawBatch(batch,texture, road.getX() / 50 + 200,road.getY() / 50, road.getHeading() + i, 0.05f);
            }

        }
    }
}
