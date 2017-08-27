package com.pukekogames.airportdesigner.Drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Helper.Geometry.PointInt;
import com.pukekogames.airportdesigner.Objects.Buildings.Building;
import com.pukekogames.airportdesigner.Objects.ClickableGameObject;
import com.pukekogames.airportdesigner.Objects.GameObject;
import com.pukekogames.airportdesigner.Objects.RoadIntersection;
import com.pukekogames.airportdesigner.Objects.Roads.Road;
import com.pukekogames.airportdesigner.Objects.Vehicles.Airplane;
import com.pukekogames.airportdesigner.Objects.Vehicles.Bus;
import com.pukekogames.airportdesigner.Objects.Vehicles.Vehicle;
import com.pukekogames.airportdesigner.Screens.GameScreen;
import com.pukekogames.airportdesigner.Settings;
import com.pukekogames.airportdesigner.TextureLoader;
import com.pukekogames.airportdesigner.UiManager;

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
            DrawVehicle.draw(batch, vehicle);
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

    public static void drawAttention(PointInt centerPos, SpriteBatch batch, Color attentionColor, PointInt sourcePoint) {
        int disX = GameInstance.Settings().screenSize.x / 10;
        int disY = GameInstance.Settings().screenSize.y / 10;
        if (centerPos.x - disX < 0 || centerPos.x + disX > GameInstance.Settings().screenSize.x || centerPos.y - disY < 0 || centerPos.y + disY > GameInstance.Settings().screenSize.y) {
            drawArrow(centerPos, batch, attentionColor, sourcePoint);
        }
    }

    public static void drawArrow(PointInt centerPos, SpriteBatch batch, Color attentionColor, PointInt sourcePoint) {
        PointInt middlePos;

        if (sourcePoint == null){
            //CenterScreenPoint
            middlePos = new PointInt(GameInstance.Settings().screenSize.x / 2, GameInstance.Settings().screenSize.y / 2);
        }else{
            middlePos = sourcePoint;
        }

        double dirX = centerPos.x - middlePos.x;
        double dirY = centerPos.y - middlePos.y;
        double length = Math.sqrt(Math.pow(dirX, 2) + Math.pow(dirY, 2));
        dirX /= length;
        dirY /= length;
        double direction = Math.atan2(dirY, dirX);
        double angleSeparation = Math.toRadians(7);
        double leftDirection = (direction - angleSeparation) % 360;
        double rightDirection = (direction + angleSeparation) % 360;
        int innerRadius = 60;
        int outerRadius = 120;
        int sideRadius = 105;
        PointInt startPos = new PointInt((int) (middlePos.x + dirX * innerRadius), (int) (middlePos.y + dirY * innerRadius));
        PointInt endPos = new PointInt((int) (middlePos.x + dirX * outerRadius), (int) (middlePos.y + dirY * outerRadius));
        double directionX = Math.cos(leftDirection);
        double directionY = Math.sin(leftDirection);
        PointInt leftPos = new PointInt((int) (middlePos.x + directionX * sideRadius), (int) (middlePos.y + directionY * sideRadius));
        directionX = Math.cos(rightDirection);
        directionY = Math.sin(rightDirection);
        PointInt rightPos = new PointInt((int) (middlePos.x + directionX * sideRadius), (int) (middlePos.y + directionY * sideRadius));

        ShapeRenderer renderer = DrawManager.getShapeRenderer();


        int width = 4;
        renderer.setColor(attentionColor);
        renderer.rectLine(startPos.x, startPos.y, endPos.x, endPos.y, width);
        renderer.rectLine(leftPos.x, leftPos.y, endPos.x, endPos.y, width);
        renderer.rectLine(rightPos.x, rightPos.y, endPos.x, endPos.y, width);

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
