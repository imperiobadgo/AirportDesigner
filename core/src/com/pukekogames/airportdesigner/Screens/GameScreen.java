package com.pukekogames.airportdesigner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.pukekogames.airportdesigner.Drawing.DrawManager;
import com.pukekogames.airportdesigner.Drawing.DrawRoads;
import com.pukekogames.airportdesigner.GameContent;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Main;
import com.pukekogames.airportdesigner.Objects.Roads.Road;
import com.pukekogames.airportdesigner.Objects.Vehicles.Airplane;
import com.pukekogames.airportdesigner.Objects.Vehicles.Vehicle;
import com.pukekogames.airportdesigner.TextureLoader;

/**
 * Created by Marko Rapka on 08.06.2017.
 */
public class GameScreen implements Screen {

    public static float PIXELS_PER_METER = 32;
    public static float WORLD_WIDTH_METERS = 100;
    public static float WORLD_HEIGHT_METERS = 100;

    private Main main;
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    FPSLogger logger;

    public GameScreen(Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        TextureLoader.Instance().loadTextures();
        GameInstance.Settings().gameType = 1;
        GameContent.setNewGame();
        logger = new FPSLogger();
        GameInstance.Settings().gameSpeed = 5;


        camera = new OrthographicCamera();
        camera.zoom = 1000;
        camera.update();
    }

    @Override
    public void render(float delta) {
        handleInput();
        GameInstance.Instance().tick();

        camera.update();

        spriteBatch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        logger.log();

//        Road road = GameInstance.Airport().getRoad(0);
//        road.setHeading((road.getHeading() + 0.8f) % 360);
//        DrawRoads.draw(spriteBatch, road);
        spriteBatch.disableBlending();
        for (int i = 0; i < GameInstance.Airport().getRoadCount(); i++) {
            Road road = GameInstance.Airport().getRoad(i);
            DrawManager.draw(spriteBatch, road);

        }
        spriteBatch.enableBlending();
        for (int i = 0; i < GameInstance.Airport().getVehicleCount(); i++) {
            Vehicle vehicle = GameInstance.Airport().getVehicle(i);
            DrawManager.draw(spriteBatch, vehicle);
        }

        for (int i = 0; i < GameInstance.Airport().getAirplaneCount(); i++) {
            Airplane airplane = GameInstance.Airport().getAirplane(i);
            DrawManager.draw(spriteBatch, airplane);
        }

//        System.out.println("Rendercalls: " + spriteBatch.renderCalls);

        spriteBatch.end();

    }

    private void handleInput() {
        float move = 100;
        float zoom = 4f;
        float x, y;
        if (Gdx.input.justTouched()){
            x = Gdx.input.getX();
            y = Gdx.input.getY();
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.zoom += zoom;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom -= zoom;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.translate(-move, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.translate(move, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, move);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, -move);
        }

//        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100/camera.viewportWidth);
//
//        float effectiveViewPortWidth = camera.viewportWidth * camera.zoom;
//        float effectiveViewPortHeight = camera.viewportHeight * camera.zoom;
//
//        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewPortWidth / 2f, 100 - effectiveViewPortWidth / 2f);
//        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewPortHeight / 2f, 100 - effectiveViewPortHeight / 2f);

    }

    @Override
    public void resize(int width, int height) {
//        camera.viewportWidth = PIXELS_PER_METER;
//        camera.viewportHeight = PIXELS_PER_METER * (height / width);

      /*
      This resize code will ensure that the window is filled with world. The camera position will be maintained during
      the resize so that whatever it was looking at isn't suddenly displaced or off screen altogether.
      Zoom is conveniently handled by the camera internals so doesn't need to be taken into account here.
       */
        Vector3 pos = new Vector3(camera.position);
        //enforce a fixed number of pixels per meter otherwise aspect ratio will skew in one dimension
        camera.setToOrtho(true, width / PIXELS_PER_METER, height / PIXELS_PER_METER);
        //move the camera back to where it was - zoom hasn't changed so this is ok to do in screen coords.
        camera.translate(pos.x - camera.position.x, pos.y - camera.position.y);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public OrthographicCamera getCamera(){
        return camera;
    }

}
