package com.pukekogames.airportdesigner.Drawing;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Helper.Geometry.PointInt;
import com.pukekogames.airportdesigner.Objects.ClickableGameObject;
import com.pukekogames.airportdesigner.Objects.Roads.Road;
import com.pukekogames.airportdesigner.Objects.Vehicles.Airplane;
import com.pukekogames.airportdesigner.Objects.Vehicles.Vehicle;
import com.pukekogames.airportdesigner.Objects.Vehicles.VehicleData.AirplaneState;
import com.pukekogames.airportdesigner.Settings;
import com.pukekogames.airportdesigner.TextureLoader;
import com.pukekogames.airportdesigner.UiManager;

import java.util.ArrayList;

/**
 * Created by Marko Rapka on 28.07.2017.
 */
public class DrawVehicle {

    public static void draw(SpriteBatch batch, Vehicle vehicle) {
//        Texture texture = TextureLoader.Instance().getTexture(vehicle.getImageID());
        Texture texture = vehicle.getTexture();
        if (texture == null) {
            texture = TextureLoader.Instance().getTexture(vehicle.getImageID());
        }
        float scale = 1f;
        if (vehicle instanceof Airplane) {
            Airplane airplane = (Airplane) vehicle;

            if (airplane.getCategory() > 2) {
                scale = 3f;
            } else if (airplane.getCategory() > 3) {
                scale = 4f;
            } else {
                scale = 2f;
            }
            if (airplane.getAltitude() > 0) {
                scale += 0.001f * airplane.getAltitude();
            }
        }


        UiManager.worldPos.set(vehicle.getAlign_X(), vehicle.getAlign_Y(), 0);
        UiManager.screenPos.set(UiManager.worldPos);

        Settings.Instance().uiManager.projectVector(UiManager.screenPos);
        vehicle.setCenterPos(UiManager.screenPos.x, UiManager.screenPos.y);

        vehicle.setDimension(texture.getWidth() * scale, texture.getHeight() * scale);

        DrawManager.drawBatch(batch, texture, vehicle.getAlign_X(), vehicle.getAlign_Y(), vehicle.getHeading() - 90, scale);


    }

    private static PointInt posStart = null;
    private static PointInt posEnd = null;

    static void drawPath(Vehicle vehicle, ClickableGameObject selected, ArrayList<Road> nextRoads) {

        if (posStart == null) {
            posStart = new PointInt();
        }
        if (posEnd == null) {
            posEnd = new PointInt();
        }
        ShapeRenderer renderer = DrawManager.getShapeRenderer();
        boolean show = false;
        if (vehicle.isSelected()) {
            renderer.setColor(Color.BLUE);
            show = true;
        } else {
            renderer.setColor(Color.GREEN);
            show = !(selected instanceof Vehicle);
        }

        if (show) {
            for (Road road : nextRoads) {

                posStart.x = (int) road.getStartPosition().x;
                posStart.y = (int) road.getStartPosition().y;
//            posStart = getPositionForRender(posStart.x, posStart.y);

                posEnd.x = (int) road.getEndPosition().x;
                posEnd.y = (int) road.getEndPosition().y;
//            posEnd = getPositionForRender(posEnd.x, posEnd.y);
                renderer.line(posStart.x, posStart.y, posEnd.x, posEnd.y);
//            canvas.drawLine(posStart.x, posStart.y, posEnd.x, posEnd.y, paint);
//            canvas.drawLine(posStart.x - 1, posStart.y - 1, posEnd.x + 1, posEnd.y + 1, paint);
            }
        }

    }

    static void drawHeading(Vehicle vehicle) {
        if (posStart == null) {
            posStart = new PointInt();
        }
        if (posEnd == null) {
            posEnd = new PointInt();
        }
        ShapeRenderer renderer = DrawManager.getShapeRenderer();

        renderer.setColor(Color.BLUE);

        posStart.x = (int) vehicle.getAlign_X();
        posStart.y = (int) vehicle.getAlign_Y();
//            posStart = getPositionForRender(posStart.x, posStart.y);

        posEnd.x = (int) vehicle.getHeadingPoint().x;
        posEnd.y = (int) vehicle.getHeadingPoint().y;
//            posEnd = getPositionForRender(posEnd.x, posEnd.y);
        renderer.line(posStart.x, posStart.y, posEnd.x, posEnd.y);

    }

    static void drawVehicleStatus(SpriteBatch batch, Vehicle vehicle) {
        ShapeRenderer renderer = DrawManager.getShapeRenderer();
        boolean selected = vehicle.isSelected();
        if (vehicle instanceof Airplane) {
            Airplane airplane = (Airplane) vehicle;
            boolean holdPosition = airplane.isHoldPosition();
            AirplaneState state = airplane.getState();

            boolean showCollisionBox = selected || holdPosition;

            switch (state) {
                case Init:
                    break;
                case Waiting:
                    renderer.setColor(Settings.Instance().attentionColor);
                    showCollisionBox = true;
                    break;
                case Boarding:
                    renderer.setColor(Color.CYAN);
                    break;
                case ReadyForPushback:
                    renderer.setColor(Settings.Instance().attentionColor);
                    showCollisionBox = true;
                    break;
                case TaxiToGate:
                    renderer.setColor(Color.GREEN);
                    break;
                case Pushback:
                    break;
                case TaxiToRunway:
                    renderer.setColor(Color.GREEN);
                    break;
                case Landing:
                    renderer.setColor(Color.GRAY);
                    break;
                case WaitingForGate:
                    renderer.setColor(Settings.Instance().attentionColor);
                    showCollisionBox = true;
                    break;
                case ReadyForDeparture:
                    renderer.setColor(Settings.Instance().attentionColor);
                    showCollisionBox = true;
                    break;
                case ClearedForDeparture:
                    break;
                case Takeoff:
                    break;
                case Arrival:
                    break;
                case Departure:
                    break;
            }

            if (showCollisionBox) {
                if (selected) {//|| selectedTime > 0) {
                    renderer.setColor(Color.BLUE);
                }
                if (holdPosition) {
                    renderer.setColor(Settings.Instance().attentionColor);
                }
                int radius = (int) (Math.min(vehicle.getHeight(), vehicle.getWidth()) * 0.5);

                renderer.circle(vehicle.getAlign_X(), vehicle.getAlign_Y(), radius - 1);
                renderer.circle(vehicle.getAlign_X(), vehicle.getAlign_Y(), radius);
//                paint.setAlpha((int) (ratio * 255));
//                int radius = (int) (Math.min(scaledHeight, scaledWidth) * 0.5);
//                paint.setStyle(Paint.Style.STROKE);
//                paint.setStrokeWidth(2);
//                canvas.drawCircle(centerPos.x, centerPos.y, radius, paint);


            }
        }
    }
}
