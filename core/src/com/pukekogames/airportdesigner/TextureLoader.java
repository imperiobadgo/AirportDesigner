package com.pukekogames.airportdesigner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.pukekogames.airportdesigner.Objects.Images;

import java.util.ArrayList;

/**
 * Created by Marko Rapka on 08.06.2017.
 */
public class TextureLoader {

    private static TextureLoader ourInstance = new TextureLoader();

    public static TextureLoader Instance() {
        return ourInstance;
    }

    Texture[] textures = new Texture[100];

    public static int indexCircleButtonBackground = 70;
    public static int indexCircleButtonBackgroundClicked = 71;
    public static int indexOptionButton = 72;
    public static int indexCircleButtonGoto = 73;
    public static int indexCircleButtonHold = 74;
    public static int indexCircleButtonInfo = 75;
    public static int indexCircleButtonTakeOff = 76;
    public static int indexButtonBuild = 80;
    public static int indexButtonBuildRoad = 81;
    public static int indexButtonBuildDepot = 82;
    public static int indexButtonDelete = 83;
    public static int indexButtonConstruct = 84;
    public static int indexButtonAccept = 85;
    public static int indexButtonCancel = 86;


    public void loadTextures() {
        textures[Images.indexAirplaneSmall] = new Texture(Gdx.files.internal("airplane_small.png"));
        textures[Images.indexAirplaneCessna] = new Texture(Gdx.files.internal("airplane_cessna.png"));
        textures[Images.indexAirplaneA320] = new Texture(Gdx.files.internal("airplane_a320.png"));
        textures[Images.indexAirplaneA320Czech] = new Texture(Gdx.files.internal("airplane_a320_czech.png"));
        textures[Images.indexAirplaneB737Alaska] = new Texture(Gdx.files.internal("airplane_b737_alaska.png"));
        textures[Images.indexAirplane777] = new Texture(Gdx.files.internal("airplane_777.png"));
        textures[Images.indexAirplane747] = new Texture(Gdx.files.internal("airplane_b747.png"));
        textures[Images.indexAirplane380] = new Texture(Gdx.files.internal("airplane_a380.png"));

        textures[Images.indexBus] = new Texture(Gdx.files.internal("bus.png"));
        textures[Images.indexCateringTruck] = new Texture(Gdx.files.internal("cateringtruck.png"));
        textures[Images.indexCrewBus] = new Texture(Gdx.files.internal("crewbus.png"));
        textures[Images.indexTankTruck] = new Texture(Gdx.files.internal("tanktruck.png"));
        textures[Images.indexBaggageTruck] = new Texture(Gdx.files.internal("baggagetruck.png"));

        textures[Images.indexRunwayEnd] = new Texture(Gdx.files.internal("runway_end.jpg"));
        textures[Images.indexRunwayMiddle] = new Texture(Gdx.files.internal("runway_middle.jpg"));
        textures[Images.indexTaxiway] = new Texture(Gdx.files.internal("taxiway.jpg"));
        textures[Images.indexStreet] = new Texture(Gdx.files.internal("street.jpg"));
        textures[Images.indexParkGate] = new Texture(Gdx.files.internal("parkgate.jpg"));

        textures[Images.indexBusDepot] = new Texture(Gdx.files.internal("depot_bus.png"));
        textures[Images.indexCateringDepot] = new Texture(Gdx.files.internal("depot_catering.png"));
        textures[Images.indexCrewBusDepot] = new Texture(Gdx.files.internal("depot_crewbus.png"));
        textures[Images.indexTankDepot] = new Texture(Gdx.files.internal("depot_tank.png"));
        textures[Images.indexBaggageDepot] = new Texture(Gdx.files.internal("depot_baggage.png"));
        textures[Images.indexTower] = new Texture(Gdx.files.internal("tower.png"));
        textures[Images.indexTerminal] = new Texture(Gdx.files.internal("terminal.png"));

        textures[indexCircleButtonBackground] = new Texture(Gdx.files.internal("button_circlebackground.png"));
        textures[indexCircleButtonBackgroundClicked] = new Texture(Gdx.files.internal("button_clickedcirclebackground.png"));

        textures[indexOptionButton] = new Texture(Gdx.files.internal("button_options.png"));
        textures[indexCircleButtonGoto] = new Texture(Gdx.files.internal("button_goto.png"));
        textures[indexCircleButtonHold] = new Texture(Gdx.files.internal("button_hold.png"));
        textures[indexCircleButtonInfo] = new Texture(Gdx.files.internal("button_info.png"));
        textures[indexCircleButtonTakeOff] = new Texture(Gdx.files.internal("button_takeoff.png"));

        textures[indexButtonBuild] = new Texture(Gdx.files.internal("button_build.png"));
        textures[indexButtonBuildRoad] = new Texture(Gdx.files.internal("button_buildroad.png"));
        textures[indexButtonBuildDepot] = new Texture(Gdx.files.internal("button_builddepot.png"));
        textures[indexButtonConstruct] = new Texture(Gdx.files.internal("button_construct.png"));
        textures[indexButtonDelete] = new Texture(Gdx.files.internal("button_delete.png"));
        textures[indexButtonAccept] = new Texture(Gdx.files.internal("button_accept.png"));
        textures[indexButtonCancel] = new Texture(Gdx.files.internal("button_cancel.png"));
    }

    public Texture getTexture(int textureIndex) {
        if (textureIndex > 0 && textureIndex < textures.length) return textures[textureIndex];
        return null;
    }

    public static Texture createTextureWithText(String content, TextureRegion background, BitmapFont font, int width, int height, int textPositionX, int textPositionY) {
        FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
        OrthographicCamera cam = new OrthographicCamera();
        cam.setToOrtho(true, width, height);
        SpriteBatch batch = new SpriteBatch();
        //make the FBO the current buffer
        fbo.begin();

//... clear the FBO color with transparent black ...
//        Gdx.gl.glClearColor(0f, 0f, 0f, 0f); //transparent black
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f); //white background
        Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT); //clear the color buffer
        Gdx.gl.glBlendFuncSeparate(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA, Gdx.gl.GL_ONE, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);
//since the FBO may not be the same size as the display,
//we need to give the SpriteBatch our new screen dimensions

        batch.setProjectionMatrix(cam.combined);
//render some sprites
        batch.begin();

        batch.draw(background, 0, height / 4, width - width / 4, height - height / 4);
        font.setColor(Color.BLACK);
        font.draw(batch, content, textPositionX, textPositionY);

        batch.end(); //flushes data to GL

//now we can unbind the FBO, returning rendering back to the default back buffer (the Display)
        fbo.end();

        Texture tex = fbo.getColorBufferTexture();
        return tex;
    }
}
