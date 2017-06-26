package com.pukekogames.airportdesigner;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.pukekogames.airportdesigner.Screens.GameScreen;

/**
 * Created by Marko Rapka on 26.06.2017.
 */
public class GestureHandler implements GestureDetector.GestureListener{

    Main main;
    float zoomStep = 10f;

    public GestureHandler(Main main) {
        this.main = main;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        GameScreen screen = getGameScreen();
        if (screen != null) {
            screen.getCamera().translate(-deltaX * 50, -deltaY * 50);
        }

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {

        GameScreen screen = getGameScreen();
        if (screen != null) {
            if (initialDistance >= distance) {
                screen.getCamera().zoom += zoomStep;
            } else {
                screen.getCamera().zoom -= zoomStep;
            }
        }
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }

    private GameScreen getGameScreen(){
        Screen screen = main.getScreen();
        if (screen instanceof GameScreen){
            GameScreen gs = (GameScreen) screen;
            return gs;
        }
        return null;
    }


}
