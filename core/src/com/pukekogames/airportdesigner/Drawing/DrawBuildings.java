package com.pukekogames.airportdesigner.Drawing;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pukekogames.airportdesigner.Helper.Geometry.PointFloat;
import com.pukekogames.airportdesigner.Objects.Buildings.Building;
import com.pukekogames.airportdesigner.TextureLoader;

/**
 * Created by Marko Rapka on 08.07.2017.
 */
public class DrawBuildings {

    public static void draw(SpriteBatch batch, Building building) {
        Texture texture = TextureLoader.Instance().getTexture(building.getImageID());

        PointFloat renderCenter = building.getRoad().getCenterPosition();
        float scale = 2;

        DrawManager.drawBatch(batch, texture, renderCenter.x, renderCenter.y,
                texture.getWidth() / 2, texture.getHeight() / 2,
                (building.getHeading() + 90) % 360, scale, scale);
    }
}
