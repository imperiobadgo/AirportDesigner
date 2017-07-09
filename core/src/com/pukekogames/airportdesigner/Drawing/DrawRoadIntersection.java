package com.pukekogames.airportdesigner.Drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.pukekogames.airportdesigner.Objects.Images;
import com.pukekogames.airportdesigner.Objects.RoadIntersection;
import com.pukekogames.airportdesigner.TextureLoader;

/**
 * Created by Marko Rapka on 01.07.2017.
 */
public class DrawRoadIntersection {

    public static void draw(SpriteBatch batch, RoadIntersection roadIntersection){
        int radius = (int) (TextureLoader.Instance().getTexture(Images.indexRunwayMiddle).getHeight() / 2f);

//        radius = 2;

//        DrawManager.getShapeRenderer().setColor(DrawRoads.TAXIWAYCOLOR);
        ShapeRenderer renderer = DrawManager.getShapeRenderer();
        renderer.setColor(DrawRoads.TAXIWAYCOLOR);
//        renderer.setColor(Color.GRAY);

        DrawManager.getShapeRenderer().circle(roadIntersection.getPosition().x, roadIntersection.getPosition().y, radius);
    }
}
