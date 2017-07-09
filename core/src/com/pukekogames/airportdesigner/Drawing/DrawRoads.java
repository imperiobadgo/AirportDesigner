package com.pukekogames.airportdesigner.Drawing;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Vector2;
import com.pukekogames.airportdesigner.Helper.Geometry.PointFloat;
import com.pukekogames.airportdesigner.Helper.Geometry.PointInt;
import com.pukekogames.airportdesigner.Objects.Images;
import com.pukekogames.airportdesigner.Objects.Roads.Road;
import com.pukekogames.airportdesigner.Objects.Roads.Runway;
import com.pukekogames.airportdesigner.Objects.Roads.Taxiway;
import com.pukekogames.airportdesigner.Screens.GameScreen;
import com.pukekogames.airportdesigner.TextureLoader;

/**
 * Created by Marko Rapka on 08.06.2017.
 */
public class DrawRoads {

    static Color TAXIWAYCOLOR = new Color(158 / 255f, 158 / 255f, 158 / 255f,1);
    static Color RUNWAYCOLOR = new Color(139 / 255f, 133 / 255f, 133 / 255f,1);
    static Color STREETCOLOR = new Color(157 / 255f, 157 / 255f, 157 / 255f,1);


    public static void draw(SpriteBatch batch, Road road){
        Texture texture = TextureLoader.Instance().getTexture(road.getImageID());
        Texture runwayMiddle = TextureLoader.Instance().getTexture(Images.indexRunwayMiddle);
        float length = road.getLength();

        float biggerFactor = 1.7f;
        float scale = 5f;
        scale = 1f;
        if (texture != null){

            int imageLength = texture.getWidth();
            int imageHeight = (int) Math.round(texture.getHeight());

            road.setDimension(imageLength, imageHeight);

            PointFloat renderCenter = road.getCenterPosition();

            int imageCount = Math.round(length / imageLength);
            float scaleX = (length / (imageCount * imageLength)) * scale;


            for (int i = 0; i < imageCount; i++) {

                float radian = (float) ((road.getHeading() ) * (Math.PI/180)); // Convert to radians

                float len =  ((i * imageLength * scaleX));

                if (scaleX > 1){
                    //no command, why this works
                    len += imageLength * scaleX / 1.5f;
                }else{
                    //no command, why this works
                    len += imageLength * scaleX / 3;
                }

                float rotatedX = (float) (Math.cos(radian) * ((length * scaleX / 2) - len) + renderCenter.x);

                float rotatedY = (float) (Math.sin(radian) * ((length * scaleX / 2) - len) + renderCenter.y);

                if (road instanceof Runway && i != 0 && i != imageCount - 1){

                    DrawManager.drawBatch(batch,runwayMiddle, rotatedX,rotatedY,
                            texture.getWidth() / 2, texture.getHeight() / 2,
                            road.getHeading(), scaleX, scale);

                }else{
                    float heading = road.getHeading();
                    if (i == 0){
                        //rotate the runway ending at one side
                        heading = (heading + 180) % 360;
                    }

                    DrawManager.drawBatch(batch,texture, rotatedX,rotatedY,
                            texture.getWidth() / 2, texture.getHeight() / 2,
                            heading, scaleX, scale);
                }

            }

        }
    }

    public static void drawLines(SpriteBatch batch, Road road) {
        if (!(road instanceof Taxiway)) return;
        ShapeRenderer renderer = DrawManager.getShapeRenderer();
        renderer.setColor(Color.GOLD);
        renderer.line(road.getStartPosition().x, road.getStartPosition().y, road.getEndPosition().x, road.getEndPosition().y);

//        renderer.setColor(Color.BLACK);
//        Vector2 perp = new Vector2(road.getDirY(), -road.getDirX());
//
//        //oben
//        renderer.line(road.getStartPosition().x + perp.x * road.getHeight() / 2, road.getStartPosition().y + perp.y * road.getHeight() / 2,
//                road.getEndPosition().x + perp.x * road.getHeight() / 2, road.getEndPosition().y + perp.y * road.getHeight() / 2);
//
//        //unten
//        renderer.line(road.getStartPosition().x - perp.x * road.getHeight() / 2, road.getStartPosition().y - perp.y * road.getHeight() / 2,
//                road.getEndPosition().x - perp.x * road.getHeight() / 2, road.getEndPosition().y - perp.y * road.getHeight() / 2);


    }
}
