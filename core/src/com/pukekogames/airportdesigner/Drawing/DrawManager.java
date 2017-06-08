package com.pukekogames.airportdesigner.Drawing;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pukekogames.airportdesigner.Objects.GameObject;
import com.pukekogames.airportdesigner.Objects.Roads.Road;

/**
 * Created by Marko Rapka on 08.06.2017.
 */
public class DrawManager {

    public static void draw(SpriteBatch batch, GameObject object){
        if (object instanceof Road){
            Road road = (Road) object;
            DrawRoads.draw(batch, road);
        }
    }

    static void drawBatch(SpriteBatch batch, Texture tex, float x, float y, float rotation, float scale ){
        batch.draw(tex, x, y,tex.getWidth() / 2, tex.getHeight() / 2, tex.getWidth(), tex.getHeight(),
                scale,scale,rotation, 0,0,tex.getWidth(),tex.getHeight(),false, false);
    }
}
