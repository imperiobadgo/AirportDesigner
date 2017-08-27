package com.pukekogames.airportdesigner.Drawing;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Shader;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
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
    private PointInt windDirectionCenter;
    private PointInt windDirectionTarget;
    private ShaderProgram passThroughShader;
    private ShaderProgram airplaneShader;
    private ShaderProgram vehicleShader;

    private DrawAirport() {
        renderObjects = new LinkedList<GameObject>();
        selectedRoadIntersections = new LinkedList<RoadIntersection>();
        planeInstruction = new ArrayList<Airplane>();
        windDirectionCenter = new PointInt(200, 200);
        windDirectionTarget = new PointInt();
        passThroughShader = new ShaderProgram(Gdx.files.internal("shaders/passthrough.vsh"), Gdx.files.internal("shaders/passthrough.fsh"));
        passThroughShader.pedantic = false;

        airplaneShader = new ShaderProgram(Gdx.files.internal("shaders/airplaneShader.vsh"), Gdx.files.internal("shaders/airplaneShader.fsh"));
        airplaneShader.pedantic = false;

        vehicleShader = new ShaderProgram(Gdx.files.internal("shaders/vehicleShader.vsh"), Gdx.files.internal("shaders/vehicleShader.fsh"));
        vehicleShader.pedantic = false;
        System.out.println("passthroughShader:" + (passThroughShader.isCompiled() ? " compiled" : passThroughShader.getLog()));
        System.out.println("airplaneShader:" + (airplaneShader.isCompiled() ? " compiled" : airplaneShader.getLog()));
        System.out.println("vehicleShader:" + (vehicleShader.isCompiled() ? " compiled" : vehicleShader.getLog()));

    }

    public void draw(SpriteBatch batch, Airport airport) {
        ShaderProgram lastShader = batch.getShader();
        selectedRoadIntersections.clear();
        batch.setShader(passThroughShader);
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

        if (GameInstance.Settings().DebugMode) {
            batch.setShader(vehicleShader);
        }
        for (int i = 0; i < airport.getVehicleCount(); i++) {
            Vehicle vehicle = airport.getVehicle(i);
            DrawManager.draw(batch, vehicle);
        }

        if (GameInstance.Settings().DebugMode) {
            batch.setShader(passThroughShader);
        }
        for (int i = 0; i < airport.getBuildingCount(); i++) {
            Building building = airport.getBuilding(i);
            DrawManager.draw(batch, building);
        }
        planeInstruction.clear();

        if (GameInstance.Settings().DebugMode) {
            batch.setShader(airplaneShader);
        }

        for (int i = 0; i < airport.getAirplaneCount(); i++) {
            Airplane airplane = airport.getAirplane(i);
            if (airplane.isWaitingForInstructions()) {
                planeInstruction.add(airplane);
            }
            DrawManager.draw(batch, airplane);
        }
        batch.end();

        if (GameInstance.Settings().DebugMode) {
            batch.setShader(passThroughShader);
        }

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
        float windDirection = (GameInstance.Airport().getWindDirection()) % 360;
        float radius = 100;
        double dirX = (float) Math.cos(Math.toRadians(windDirection)) * radius;
        double dirY = (float) Math.sin(Math.toRadians(windDirection)) * -radius;
        windDirectionTarget.set(windDirectionCenter.x + Math.round(dirX), windDirectionCenter.y + Math.round(dirY));
        DrawManager.drawArrow(windDirectionCenter, batch, Color.BLUE, windDirectionTarget);
        DrawManager.getShapeRenderer().setProjectionMatrix(Settings.Instance().uiManager.getGameScreen().getCamera().combined);
        DrawManager.getShapeRenderer().end();

        batch.setShader(lastShader);
    }

}
