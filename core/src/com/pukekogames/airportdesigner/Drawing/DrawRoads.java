package com.pukekogames.airportdesigner.Drawing;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Affine2;
import com.pukekogames.airportdesigner.Helper.Geometry.PointFloat;
import com.pukekogames.airportdesigner.Helper.Geometry.PointInt;
import com.pukekogames.airportdesigner.Objects.Roads.Road;
import com.pukekogames.airportdesigner.Screens.GameScreen;
import com.pukekogames.airportdesigner.TextureLoader;

/**
 * Created by Marko Rapka on 08.06.2017.
 */
public class DrawRoads {

    public static void draw(SpriteBatch batch, Road road){
        Texture texture = TextureLoader.Instance().getTexture(road.getImageID());
        float length = road.getLength();

        float biggerFactor = 1.7f;
        float scale = 5f;
        if (texture != null){

            int imageLength = texture.getWidth();
            int imageHeight = (int) Math.round(texture.getHeight());

            road.setDimension(imageLength, imageHeight);

            PointFloat renderCenter = road.getCenterPosition();

            int imageCount = Math.round(length / imageLength);
            float scaleX = (length / (imageCount * imageLength)) * scale;


            for (int i = 0; i < imageCount; i++) {

                float radian = (float) ((road.getHeading() ) * (Math.PI/180)); // Convert to radians

                float len =  (renderCenter.x - (length / 2) + (i * imageLength) + imageLength / 2);

                float rotatedX = (float) (Math.cos(radian) * (len - renderCenter.x) - Math.sin(radian) * (renderCenter.y-renderCenter.y) + renderCenter.x);

                float rotatedY = (float) (Math.sin(radian) * (len - renderCenter.x) + Math.cos(radian) * (renderCenter.y - renderCenter.y) + renderCenter.y);

                DrawManager.drawBatch(batch,texture, rotatedX,rotatedY,
                        GameScreen.WORLD_WIDTH_METERS / 2, GameScreen.WORLD_HEIGHT_METERS / 2,
                        road.getHeading(), scaleX * 1.17f, scale);

            }

        }
    }
}
