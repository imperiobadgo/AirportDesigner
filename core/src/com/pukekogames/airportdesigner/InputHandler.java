package com.pukekogames.airportdesigner;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.pukekogames.airportdesigner.Screens.GameScreen;

/**
 * Created by Marko Rapka on 30.06.2017.
 */
public class InputHandler implements InputProcessor {

    private final Main main;

    public InputHandler(Main main){
        this.main = main;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        GameScreen screen = getGameScreen();
        if (screen != null) {
            OrthographicCamera camera = screen.getCamera();
            float factor = amount * camera.zoom / 10;
            screen.zoomCamera(camera.zoom + factor, factor);
//            camera.zoom += amount * camera.zoom / 10;
        }
        return false;
    }

    public GameScreen getGameScreen(){
        Screen screen = main.getScreen();
        if (screen instanceof GameScreen){
            GameScreen gs = (GameScreen) screen;
            return gs;
        }
        return null;
    }
}
