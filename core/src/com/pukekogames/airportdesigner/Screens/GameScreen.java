package com.pukekogames.airportdesigner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pukekogames.airportdesigner.Drawing.DrawManager;
import com.pukekogames.airportdesigner.GameContent;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Main;
import com.pukekogames.airportdesigner.Objects.Roads.Road;
import com.pukekogames.airportdesigner.TextureLoader;

/**
 * Created by Marko Rapka on 08.06.2017.
 */
public class GameScreen implements Screen {

    private Main main;
    private SpriteBatch spriteBatch;
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
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        logger.log();
        for (int i = 0; i < GameInstance.Airport().getRoadCount(); i++) {
            Road road = GameInstance.Airport().getRoad(i);
            DrawManager.draw(spriteBatch, road);
        }

        spriteBatch.end();

    }

    @Override
    public void resize(int width, int height) {

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
}
