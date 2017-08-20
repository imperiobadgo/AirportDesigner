package com.pukekogames.airportdesigner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.pukekogames.airportdesigner.*;
import com.pukekogames.airportdesigner.Drawing.DrawManager;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Helper.CommonMethods;
import com.pukekogames.airportdesigner.Helper.GameSave;
import com.pukekogames.airportdesigner.Helper.GameStart;
import com.pukekogames.airportdesigner.Objects.Airlines.Airline;
import com.pukekogames.airportdesigner.Objects.Airlines.AirlineList;
import com.pukekogames.airportdesigner.Objects.Vehicles.Airplane;
import com.pukekogames.airportdesigner.Objects.Vehicles.VehicleData.AirplaneDataB747;
import com.pukekogames.airportdesigner.Objects.Vehicles.VehicleData.AirplaneDataB777;

import java.io.*;

/**
 * Created by Marko Rapka on 08.06.2017.
 */
public class GameScreen implements Screen {

    public static float PIXELS_PER_METER = 32;
    public static float WORLD_WIDTH_METERS = 100;
    public static float WORLD_HEIGHT_METERS = 100;

    private static final String SAVE_EXTENSION = ".ADSave";
    private static final String SAVE_BACKUP_EXTENSION = ".ADBackupSave";

    public float screenWidth, screenHeight;

    private Airplane airplane;
    private Main main;
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    FPSLogger logger;

    Vector3 screenPos;
    Vector3 worldPos;

    //    private Skin skin;
//    private Stage gameStage;
//    private Table table;
    private UiManager uiManager;
    private Handler handler;


    public GameScreen(Main main, GameStart gameStart) {
        this.main = main;
        spriteBatch = new SpriteBatch();
        TextureLoader.Instance().loadTextures();
        logger = new FPSLogger();


        camera = new OrthographicCamera();
        uiManager = new UiManager(main, this);
        handler = new Handler(main, uiManager);
        uiManager.setHandler(handler);
        screenPos = new Vector3();
        worldPos = new Vector3();

        airplane = new Airplane(new AirplaneDataB777(), new Airline(1));

        Texture texture = TextureLoader.Instance().getTexture(airplane.getImageID());
        Texture t = AirlineList.setbaseColor(texture, airplane.getAirline());
        airplane.setTexture(t);


        switch (gameStart) {

            case New:
                NewGame();
                break;
            case Load:
                LoadGame();
                break;
            case Debug:
                DebugGame();
                break;
            default:
                NewGame();
                break;
        }
    }

    private void LoadGame() {
        loadSaves();


        int currentSlot = GameSave.Instance().currentSlot;
        if (GameSave.Instance().gameInstances[currentSlot] == null) {
            return;
        }
        GameInstance.setGameInstance(GameSave.Instance().gameInstances[currentSlot]);

        CommonMethods.loadAllObjectReferences(GameInstance.Airport());
        GameInstance.Settings().gameType = 2; //important to show, that this airport was loaded!
    }

    private void NewGame() {
        GameInstance.Settings().gameType = 0;
        loadSaves();
    }

    private void DebugGame() {
        GameInstance.Settings().gameType = 1;
        loadSaves();
    }

    @Override
    public void show() {
        uiManager.setup();

//        skin = new Skin(Gdx.files.internal("ui\\uiskin.json"));
////        skin = new Skin();
//
////        skin.addRegions(main.assets.get("ui\\uiskin.atlas", TextureAtlas.class));
//        skin.add("default-font", main.font);
////        skin.load(Gdx.files.internal("ui\\uiskin.json"));
//
//        gameStage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));

//        table = new Table();
//        table.setWidth(gameStage.getWidth());
//        table.align(Align.center|Align.top);
//        table.setPosition(0, Gdx.graphics.getHeight());
//
//
//        TextButton startButton = new TextButton("Start Game", skin);
//        TextButton quitButton = new TextButton("Quit Game", skin);
//
//        table.padTop(30);
//        table.add(startButton).padBottom(30);
//        table.row();
//        table.add(quitButton);
//
//        gameStage.addActor(table);


        InputMultiplexer im = new InputMultiplexer();

        GestureDetector gestureDetector = new GestureDetector(new GestureHandler(main));
        InputHandler inputHandler = new InputHandler(main, this);
        im.addProcessor(uiManager.getScreenStage());
        im.addProcessor(uiManager.getGameStage());
        im.addProcessor(gestureDetector);
        im.addProcessor(inputHandler);

        Gdx.input.setInputProcessor(im);

        GameContent.setNewGame();

        camera.zoom = 1000;
        camera.update();
        camera.viewportWidth = 640;
        camera.viewportWidth = 480;
    }

    @Override
    public void render(float delta) {
        handleInput();
//        handler.tick();

        camera.update();

        spriteBatch.setProjectionMatrix(camera.combined);
        DrawManager.getShapeRenderer().setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        logger.log();

//        spriteBatch.begin();
//        Road road = GameInstance.Airport().getRoad(19);
////        road.setHeading((road.getHeading() + 0.8f) % 360);
//        road.calculateNewDirection(road.getHeading() + 0.1f, road.getLength());
//        DrawRoads.draw(spriteBatch, road);
//        spriteBatch.end();
//
//        DrawManager.getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
//        DrawRoads.drawLines(spriteBatch, road);
//        DrawManager.getShapeRenderer().end();


//        handler.draw(spriteBatch);

        spriteBatch.begin();
        DrawManager.draw(spriteBatch, airplane);
        spriteBatch.end();

//        DrawManager.getShapeRenderer().begin(ShapeRenderer.ShapeType.Filled);
//        for (int i = 0; i < GameInstance.Airport().getRoadIntersectionCount(); i++) {
//            RoadIntersection roadIntersection = GameInstance.Airport().getRoadIntersection(i);
//            DrawManager.draw(spriteBatch, roadIntersection);
//        }
//        DrawManager.getShapeRenderer().end();
//
//        spriteBatch.begin();
//        spriteBatch.disableBlending();
//        for (int i = 0; i < GameInstance.Airport().getRoadCount(); i++) {
//            Road road = GameInstance.Airport().getRoad(i);
//            DrawManager.draw(spriteBatch, road);
//
//        }
//        spriteBatch.enableBlending();
//        spriteBatch.end();
//
//
//        DrawManager.getShapeRenderer().begin(ShapeRenderer.ShapeType.Line);
//        for (int i = 0; i < GameInstance.Airport().getRoadCount(); i++) {
//            Road road = GameInstance.Airport().getRoad(i);
//            DrawRoads.drawLines(spriteBatch, road);
//
//        }
////        DrawRoads.drawLines(spriteBatch, GameInstance.Airport().getRoad(0));
//        DrawManager.getShapeRenderer().end();
//
//        spriteBatch.begin();
//        for (int i = 0; i < GameInstance.Airport().getVehicleCount(); i++) {
//            Vehicle vehicle = GameInstance.Airport().getVehicle(i);
//            DrawManager.draw(spriteBatch, vehicle);
//        }
//
//        for (int i = 0; i < GameInstance.Airport().getBuildingCount(); i++) {
//            Building building = GameInstance.Airport().getBuilding(i);
//            DrawManager.draw(spriteBatch, building);
//        }
//
//        for (int i = 0; i < GameInstance.Airport().getAirplaneCount(); i++) {
//            Airplane airplane = GameInstance.Airport().getAirplane(i);
//            DrawManager.draw(spriteBatch, airplane);
//        }
        spriteBatch.begin();
        main.font.setColor(Color.BLACK);
        main.font.draw(spriteBatch, "Test Text", worldPos.x, worldPos.y);

        spriteBatch.end();

        uiManager.tick(delta);
        uiManager.render(spriteBatch);

//        spriteBatch.end();

//        System.out.println("Rendercalls: " + spriteBatch.renderCalls);


    }

    private void handleInput() {
        float move = 100;
        float zoom = 4f;
        if (Gdx.input.justTouched()) {

//            screenPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//
//            worldPos.set(camera.unproject(screenPos));

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

    public void setTouched(float screenX, float screenY) {
        screenPos.set(screenX, screenY, 0);
        worldPos.set(camera.unproject(screenPos));
        handler.OnTouch(worldPos.x, worldPos.y);
    }

    public Vector3 unproject(float screenX, float screenY) {
        screenPos.set(screenX, screenY, 0);
        return worldPos.set(camera.unproject(screenPos));
    }

    public void zoomCamera(float zoom, float factor) {
        zoom = Math.max(Settings.Instance().minZoom, Math.min(zoom, Settings.Instance().maxZoom));
        camera.zoom = zoom;


        if (zoom > Settings.Instance().maxZoom - (Settings.Instance().maxZoom - Settings.Instance().minZoom) / 5 && factor > 0) {//just on the "last" bit
            float deltaX = camera.position.x - screenWidth / 2;
            float deltaY = camera.position.y - screenHeight / 2;
            float translateFactor = 0.2f;

            float translateX = -deltaX * translateFactor;
            float translateY = -deltaY * translateFactor;
            if (Math.abs(translateX) > 10 && Math.abs(translateY) > 10)
                camera.translate(translateX, translateY);

        }
    }

    @Override
    public void resize(int width, int height) {
//        camera.viewportWidth = PIXELS_PER_METER;
//        camera.viewportHeight = PIXELS_PER_METER * (height / width);

        screenWidth = width;
        screenHeight = height;


        uiManager.resize(width, height);

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
        GameInstance.Airport().setPauseSimulation(true);
        save();
    }

    @Override
    public void resume() {
        loadSaves();
        //load
        GameInstance.Airport().setPauseSimulation(false);
    }

    @Override
    public void hide() {
        GameInstance.Airport().setPauseSimulation(true);
        save();
        //save
    }

    void loadSaves() {

        boolean loadSuccessful = false;

        try {

            loadWithExtension(SAVE_EXTENSION);

            loadSuccessful = true;
            System.out.println("Loading completed.");
        } catch (Exception e) {
            loadSuccessful = false;
            System.out.println("error: " + e.getMessage());
        }

        if (!loadSuccessful) {
            System.out.println("Loading failed. Trying to load backup...");
            try {
                loadWithExtension(SAVE_BACKUP_EXTENSION);

                FileHandle directoryHandle = Gdx.files.local("bin/");

                FileHandle[] files = directoryHandle.list();
                for (FileHandle file : files) {
                    String fileName = file.name();
                    String[] splittedFileName = fileName.split("\\.");//escape dot as wildcard
                    if (splittedFileName.length != 2) {
                        continue;
                    }
                    if (SAVE_EXTENSION.equals("." + splittedFileName[1])) {
                        file.delete();//delete corrupted files
                    }

                }
            } catch (Exception e) {
                System.out.println("error: " + e.getMessage());
            }
        }
    }

    private void loadWithExtension(String extension) throws IOException, ClassNotFoundException {
        //first load just gamesave
//        FileInputStream fis = openFileInput("game" + extension);
        FileHandle handle = Gdx.files.local("bin/save" + extension);
        ObjectInputStream is = new ObjectInputStream(handle.read());
        GameSave save = (GameSave) is.readObject();
        GameSave.setGameSave(save);
        GameSave.Instance().gameInstances = new GameInstance[GameSave.SAVECOUNT];
        is.close();
//        fis.close();

        for (int i = 0; i < GameSave.SAVECOUNT; i++) {
            //than load every airport

//            fis = openFileInput("airport" + i + extension);
            handle = Gdx.files.local("bin/airport" + i + extension);
            is = new ObjectInputStream(handle.read());
            System.out.println("Loading instance number " + i + " with " + extension);
            GameInstance instance = (GameInstance) is.readObject();
            if (instance != null) {
                GameSave.Instance().gameInstances[i] = instance;
            } else {
                GameSave.Instance().gameInstances[i] = null;
            }

            is.close();
//            fis.close();

        }
    }

    public void save() {

//        Json json = new Json();
//
//        System.out.println(json.toJson(GameInstance.Instance()));

        //make last save the backupsave
//        File directory = this.getFilesDir();
        FileHandle directoryHandle = Gdx.files.local("bin/");

        FileHandle[] files = directoryHandle.list();
        for (FileHandle file : files) {
            String fileName = file.name();
            String[] splittedFileName = fileName.split("\\.");//escape dot as wildcard
            if (splittedFileName.length != 2) {
                continue;
            }
            if (SAVE_EXTENSION.equals("." + splittedFileName[1])) {
//                FileHandle newFile = Gdx.files.local(directory + splittedFileName[0] + SAVE_BACKUP_EXTENSION);

                FileHandle newFile = Gdx.files.local(directoryHandle + "/" + splittedFileName[0] + SAVE_BACKUP_EXTENSION);
                file.copyTo(newFile);
                System.out.println("renamed " + file.name() + " to " + newFile.name());
            }

        }


        try {

            //first save just GameSave
//            FileOutputStream fos = openFileOutput("game" + SAVE_EXTENSION, Context.MODE_PRIVATE);
            FileHandle handle = Gdx.files.local("bin/save" + SAVE_EXTENSION);
            ObjectOutputStream os = new ObjectOutputStream(handle.write(false));
            os.writeObject(GameSave.Instance());
            os.close();

            for (int i = 0; i < GameSave.SAVECOUNT; i++) {
                //than save every Airport
                handle = Gdx.files.local("bin/airport" + i + SAVE_EXTENSION);
//                fos = openFileOutput("airport" + i + SAVE_EXTENSION, Context.MODE_PRIVATE);
                os = new ObjectOutputStream(handle.write(false));
                System.out.println("Saving instance number " + i);
                os.writeObject(GameSave.Instance().gameInstances[i]);
                os.close();
//                fos.close();
            }

            System.out.println("Saving completed.");
        } catch (Exception e) {
//            Toast toast = Toast.makeText(Game.this, "error: " + e.getMessage(), Toast.LENGTH_SHORT);
//            toast.show();
            System.out.println("error: " + e.getMessage());
        }
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        uiManager.dispose();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Handler getHandler() {
        return handler;
    }

    public UiManager getUiManager() {
        return uiManager;
    }
}
