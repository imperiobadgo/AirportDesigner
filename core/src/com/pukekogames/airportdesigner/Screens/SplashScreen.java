package com.pukekogames.airportdesigner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Marko Rapka on 08.06.2017.
 */
public class SplashScreen implements Screen {

    private SpriteBatch spriteBatch;
    private Texture splash;
    private com.pukekogames.airportdesigner.Main main;

    public SplashScreen(com.pukekogames.airportdesigner.Main main){
        this.main = main;
    }


    @Override
    public void show() {
        spriteBatch = new SpriteBatch();
        splash = new Texture(Gdx.files.internal("airplane_a380.png"));
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(splash, 0, 0);
        spriteBatch.end();

        if(Gdx.input.justTouched())
            main.setScreen(new MainMenuScreen(main));
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
        dispose();
    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        splash.dispose();
    }
}
