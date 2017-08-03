package com.pukekogames.airportdesigner.Screens.TimeTabelContent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.pukekogames.airportdesigner.Main;

/**
 * Created by Marko Rapka on 31.07.2017.
 */
public class TimeTableScreen implements Screen {

    private final Main main;
    private final Skin skin;
    private TimeRowWindow inventoryActor;

    public static Stage stage;

    public TimeTableScreen(Main main, Skin skin) {
        this.main = main;
        this.skin = skin;
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        DragAndDrop dragAndDrop = new DragAndDrop();
        inventoryActor = new TimeRowWindow(main,this, new TimeRow(), dragAndDrop, skin);
        stage.addActor(inventoryActor);
    }

    @Override
    public void resume() {
        main.assets.finishLoading();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY)) {
            inventoryActor.setVisible(true);
        }

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // NOOP
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
