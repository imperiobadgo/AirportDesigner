package com.pukekogames.airportdesigner.Drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pukekogames.airportdesigner.Helper.Geometry.PointFloat;
import com.pukekogames.airportdesigner.Helper.Geometry.PointInt;
import com.pukekogames.airportdesigner.Objects.Buildings.Building;
import com.pukekogames.airportdesigner.Objects.Buildings.Depot;
import com.pukekogames.airportdesigner.Objects.Roads.Road;
import com.pukekogames.airportdesigner.TextureLoader;

/**
 * Created by Marko Rapka on 08.07.2017.
 */
public class DrawBuildings {

    private static PointInt startPos = null;
    private static PointInt endPos = null;

    public static void draw(SpriteBatch batch, Building building) {
        Texture texture = TextureLoader.Instance().getTexture(building.getImageID());

        PointFloat renderCenter = building.getRoad().getCenterPosition();
        float scale = 2;

        DrawManager.drawBatch(batch, texture, renderCenter.x, renderCenter.y,
                texture.getWidth() / 2, texture.getHeight() / 2,
                (building.getHeading() + 90) % 360, scale, scale);
    }

    public static void drawPossibleSelection(SpriteBatch batch, Building building) {
        ShapeRenderer renderer = DrawManager.getShapeRenderer();

        boolean selected = building.isSelected();
        Road road = building.getRoad();
        int radius = 200;
        if (selected) {
            renderer.setColor(Color.BLUE);
//            paint.setColor(Settings.Instance().selectedColor);
        } else {
            renderer.setColor(Color.CYAN);
//            paint.setColor(Settings.Instance().possibleSelectionColor);
        }
//        renderer.setColor(Color.GOLD);
        renderer.circle(road.getCenterPosition().x, road.getCenterPosition().y, radius);
    }

    static void drawBuildingStatus(SpriteBatch batch, Building building) {
        if (building.isUserWantsDemolition()) {

            int radius = 500;
            float heading = building.getHeading();
            PointFloat centerPos = building.getRoad().getCenterPosition();

            if (startPos == null) {
                startPos = new PointInt();
            }
            if (endPos == null) {
                endPos = new PointInt();
            }

            startPos.set((float) (Math.cos(Math.toRadians(heading)) * radius + centerPos.x), (float) (Math.sin(Math.toRadians(heading)) * radius + centerPos.y));

            endPos.set((float) (-Math.cos(Math.toRadians(heading)) * radius + centerPos.x), (float) (-Math.sin(Math.toRadians(heading)) * radius + centerPos.y));

            ShapeRenderer renderer = DrawManager.getShapeRenderer();
            renderer.setColor(Color.RED);

            int width = 30;
            renderer.rectLine(startPos.x, startPos.y, endPos.x, endPos.y, width);
        }
    }
}
