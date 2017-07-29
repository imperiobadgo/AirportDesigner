package com.pukekogames.airportdesigner.Drawing;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pukekogames.airportdesigner.GameInstance.Airport;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Helper.Geometry.PointInt;
import com.pukekogames.airportdesigner.Objects.Buildings.Building;
import com.pukekogames.airportdesigner.Objects.GameObject;
import com.pukekogames.airportdesigner.Objects.RoadIntersection;
import com.pukekogames.airportdesigner.Objects.Roads.Road;
import com.pukekogames.airportdesigner.Objects.Roads.Runway;
import com.pukekogames.airportdesigner.Objects.Vehicles.Airplane;
import com.pukekogames.airportdesigner.Objects.Vehicles.Vehicle;
import com.pukekogames.airportdesigner.Settings;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Marko Rapka on 28.07.2017.
 */
public class DrawAirport {

    private static DrawAirport ourInstance = new DrawAirport();

    public static DrawAirport Instance() {
        return ourInstance;
    }


    private LinkedList<GameObject> renderObjects;
    private LinkedList<RoadIntersection> selectedRoadIntersections;
    private ArrayList<Airplane> planeInstruction;

    private DrawAirport() {
        renderObjects = new LinkedList<GameObject>();
        selectedRoadIntersections = new LinkedList<RoadIntersection>();
        planeInstruction = new ArrayList<Airplane>();
    }

    public void draw(SpriteBatch batch, Airport airport) {

        selectedRoadIntersections.clear();

        DrawManager.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < airport.getRoadIntersectionCount(); i++) {
            RoadIntersection roadIntersection = GameInstance.Airport().getRoadIntersection(i);
            if (roadIntersection.isSelected()) selectedRoadIntersections.add(roadIntersection);
            DrawManager.draw(batch, roadIntersection);
        }
        DrawManager.getShapeRenderer().end();

        batch.begin();
        batch.disableBlending();
        for (int i = 0; i < airport.getRoadCount(); i++) {
            Road road = airport.getRoad(i);
            if (road instanceof Runway) continue;
            DrawManager.draw(batch, road);

        }
        for (int i = 0; i < airport.getRunwayCount(); i++) {
            Runway runway = airport.getRunway(i);
            DrawManager.draw(batch, runway);
        }

        batch.enableBlending();
        batch.end();


        DrawManager.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < airport.getRoadCount(); i++) {
            Road road = airport.getRoad(i);
            DrawRoads.drawLines(batch, road);

        }
//        DrawRoads.drawLines(spriteBatch, GameInstance.Airport().getRoad(0));
        DrawManager.getShapeRenderer().end();

        batch.begin();
        for (int i = 0; i < airport.getVehicleCount(); i++) {
            Vehicle vehicle = airport.getVehicle(i);
            DrawManager.draw(batch, vehicle);
        }

        for (int i = 0; i < airport.getBuildingCount(); i++) {
            Building building = airport.getBuilding(i);
            DrawManager.draw(batch, building);
        }
        planeInstruction.clear();

        for (int i = 0; i < airport.getAirplaneCount(); i++) {
            Airplane airplane = airport.getAirplane(i);
            if (airplane.isWaitingForInstructions()) {
                planeInstruction.add(airplane);
            }
            DrawManager.draw(batch, airplane);
        }
        batch.end();

        DrawManager.getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
        for (RoadIntersection selectedIntersection : selectedRoadIntersections) {
            DrawManager.drawPossibleSelection(batch, selectedIntersection);
        }
        for (int i = 0; i < airport.getVehicleCount(); i++) {
            Vehicle vehicle = airport.getVehicle(i);
            DrawVehicle.drawVehicleStatus(batch, vehicle);
        }
        DrawManager.getShapeRenderer().end();

        DrawManager.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
        for (int i = 0; i < airport.getBuildingCount(); i++) {
            Building building = airport.getBuilding(i);
            DrawBuildings.drawBuildingStatus(batch, building);
        }
        DrawManager.getShapeRenderer().setProjectionMatrix(Settings.Instance().uiManager.getScreenStage().getCamera().combined);
        for (Airplane plane : planeInstruction) {
            DrawManager.drawAttention(plane.getCenterPos(), batch, Settings.Instance().attentionColor, null);

        }
        DrawManager.getShapeRenderer().setProjectionMatrix(Settings.Instance().uiManager.getGameScreen().getCamera().combined);
        DrawManager.getShapeRenderer().end();

    }

}
