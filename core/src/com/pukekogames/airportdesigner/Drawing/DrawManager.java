package com.pukekogames.airportdesigner.Drawing;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pukekogames.airportdesigner.Objects.Buildings.Building;
import com.pukekogames.airportdesigner.Objects.ClickableGameObject;
import com.pukekogames.airportdesigner.Objects.GameObject;
import com.pukekogames.airportdesigner.Objects.RoadIntersection;
import com.pukekogames.airportdesigner.Objects.Roads.Road;
import com.pukekogames.airportdesigner.Objects.Vehicles.Airplane;
import com.pukekogames.airportdesigner.Objects.Vehicles.Bus;
import com.pukekogames.airportdesigner.Objects.Vehicles.Vehicle;
import com.pukekogames.airportdesigner.Screens.GameScreen;
import com.pukekogames.airportdesigner.TextureLoader;

/**
 * Created by Marko Rapka on 08.06.2017.
 */
public class DrawManager {

    private static ShapeRenderer shapeRenderer;

    public static ShapeRenderer getShapeRenderer(){
        if  (shapeRenderer == null){
            shapeRenderer = new ShapeRenderer();
        }
        return shapeRenderer;
    }

    public static void draw(SpriteBatch batch, GameObject object) {
        if (object instanceof Road) {
            Road road = (Road) object;
            DrawRoads.draw(batch, road);
        }else if (object instanceof RoadIntersection){
            RoadIntersection roadIntersection = (RoadIntersection) object;
            DrawRoadIntersection.draw(batch, roadIntersection);
        }else if (object instanceof Vehicle){
            Vehicle vehicle = (Vehicle) object;
            Texture texture = TextureLoader.Instance().getTexture(vehicle.getImageID());

            if (texture != null) {

                float scale = 1f;
                if (vehicle instanceof Airplane) scale = 2f;


                DrawManager.drawBatch(batch, texture, vehicle.getAlign_X(), vehicle.getAlign_Y(), vehicle.getHeading() - 90, scale);


            }

        }else if(object instanceof Building) {
            Building building = (Building) object;
            DrawBuildings.draw(batch, building);
        }else if (object instanceof ClickableGameObject) {
            ClickableGameObject clickableGameObject = (ClickableGameObject) object;
            Texture texture = TextureLoader.Instance().getTexture(clickableGameObject.getImageID());

            if (texture != null) {



                DrawManager.drawBatch(batch, texture, clickableGameObject.getX(), clickableGameObject.getY(), clickableGameObject.getHeading() - 90, 1f);


            }
        }
    }

    public static void drawPossibleSelection(SpriteBatch batch, ClickableGameObject object){
        if (object instanceof Airplane) {
            Airplane airplane = (Airplane) object;

        } else if (object instanceof Bus) {
            Bus bus = (Bus) object;

        } else if (object instanceof Road) {
            Road road = (Road) object;
            DrawRoads.drawPossibleSelection(batch, road);
        } else if (object instanceof RoadIntersection) {
            RoadIntersection roadIntersection = (RoadIntersection) object;
            DrawRoadIntersection.drawPossibleSelection(batch, roadIntersection, false);
        } else if (object instanceof Building) {
            Building building = (Building) object;
            DrawBuildings.drawPossibleSelection(batch, building);
        }
    }

    public static void drawBatch(SpriteBatch batch, Texture tex, float x, float y, float rotation, float scale) {
//        x /= 50;
//        y /= 50;
        int width = tex.getWidth();
        int height = tex.getHeight();
        batch.draw(tex, x - width /2, y - height / 2, width / 2, height / 2, width, height,
                scale, scale, rotation, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
    }

    public static void drawBatch(SpriteBatch batch, Texture tex, float x, float y,float rotXTexture, float rotYTexture, float rotation, float scale) {
        batch.draw(tex, x, y, rotXTexture, rotYTexture, GameScreen.WORLD_WIDTH_METERS, GameScreen.WORLD_HEIGHT_METERS,
                scale, scale, rotation, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
    }

    public static void drawBatch(SpriteBatch batch, Texture tex, float x, float y,float rotXTexture, float rotYTexture, float rotation, float scaleX, float scaleY) {
        int width = tex.getWidth();
        int height = tex.getHeight();
        batch.draw(tex, x - width / 2, y - height / 2, rotXTexture, rotYTexture, width, height,
                scaleX, scaleY, rotation, 0, 0, tex.getWidth(), tex.getHeight(), false, false);
    }
}
