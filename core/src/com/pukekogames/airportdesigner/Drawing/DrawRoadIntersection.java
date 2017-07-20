package com.pukekogames.airportdesigner.Drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
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

    public static void drawPossibleSelection(SpriteBatch batch, RoadIntersection roadIntersection, boolean error) {
        ShapeRenderer renderer = DrawManager.getShapeRenderer();

        boolean selected = roadIntersection.isSelected();
        int radius = 200;
        if (selected) {
            renderer.setColor(Color.BLUE);
//            paint.setColor(Settings.Instance().selectedColor);
        } else {
            renderer.setColor(Color.CYAN);
//            paint.setColor(Settings.Instance().possibleSelectionColor);
        }
        if (error){
            renderer.setColor(Color.RED);
        }

//        renderer.setColor(Color.GOLD);
        renderer.circle(roadIntersection.getPosition().x, roadIntersection.getPosition().y, radius);
    }
}
