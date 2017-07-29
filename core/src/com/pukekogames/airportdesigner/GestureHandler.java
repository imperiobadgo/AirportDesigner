package com.pukekogames.airportdesigner;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.pukekogames.airportdesigner.Screens.GameScreen;

/**
 * Created by Marko Rapka on 26.06.2017.
 */
public class GestureHandler implements GestureDetector.GestureListener {

    private final Main main;
    private float lastX, lastY;
    Vector3 touchPos;
    float zoomStep = 10f;
    private boolean zooming;
    private float scale = 10f;
    private float initialZoom = 1f;

    public GestureHandler(Main main) {
        this.main = main;
        lastX = 0;
        lastY = 0;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        lastX = x;
        lastY = y;
        GameScreen screen = getGameScreen();
        if (screen != null) {
            initialZoom = screen.getCamera().zoom;
        }
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
            Vector3 worldPos = screen.unproject(x, y);

            if (screen.getHandler().isDraggingBuildIntersection(worldPos.x,worldPos.y)) {
                screen.getHandler().touchMoved(worldPos.x,worldPos.y);
            } else {
                OrthographicCamera camera = screen.getCamera();
                float magicPanFactor = camera.zoom / 30;// just happen to be 30(than the mouse sticks to the ground when moving)

                camera.translate(-deltaX * magicPanFactor, -deltaY * magicPanFactor);
            }
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
            float factor = initialDistance / distance;
            screen.zoomCamera(initialZoom * factor, factor);
//            screen.getCamera().zoom = initialZoom * factor;

        }
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
//        GameScreen screen = getGameScreen();
//        if (screen != null) {
//            OrthographicCamera camera = screen.getCamera();
//            //grab all the positions
//
//            touchPos.set(initialPointer1.x, initialPointer1.y, 0);
//            camera.unproject(touchPos);
//            float x1n = touchPos.x;
//            float y1n = touchPos.y;
//            touchPos.set(initialPointer2.x, initialPointer2.y, 0);
//            camera.unproject(touchPos);
//            float x2n = touchPos.x;
//            float y2n = touchPos.y;
//            touchPos.set(pointer1.x, pointer1.y, 0);
//            camera.unproject(touchPos);
//            float x1p = touchPos.x;
//            float y1p = touchPos.y;
//            touchPos.set(pointer2.x, pointer2.y, 0);
//            camera.unproject(touchPos);
//            float x2p = touchPos.x;
//            float y2p = touchPos.y;
//
//            float dx1 = x1n - x2n;
//            float dy1 = y1n - y2n;
//            float initialDistance = (float) Math.sqrt(dx1 * dx1 + dy1 * dy1);
//            float dx2 = x1p - x2p;
//            float dy2 = y1p - y2p;
//            float distance = (float) Math.sqrt(dx2 * dx2 + dy2 * dy2);
//
//            float cx;
//            float cy;
//
//                float nextZoom = (initialDistance / distance) * scale;
//			/* do some ifs here to check if nextZoom is too zoomed in or out*/
//                camera.zoom = nextZoom;
//                camera.update();
//
//                Vector3 pos = new Vector3((pointer1.x + pointer2.x) / 2, (pointer1.y + pointer2.y) / 2, 0f);
//                camera.unproject(pos);
//                float dx = cx - pos.x;
//                float dy = cy - pos.y;
//			/* do some ifs here to check if we are in bounds*/
//                camera.translate(dx, dy);
//                camera.update();
//            }
//        }
        return false;

    }

    @Override
    public void pinchStop() {

    }

    private GameScreen getGameScreen() {
        Screen screen = main.getScreen();
        if (screen instanceof GameScreen) {
            GameScreen gs = (GameScreen) screen;
            return gs;
        }
        return null;
    }


}
