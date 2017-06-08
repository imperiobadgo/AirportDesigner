package com.pukekogames.airportdesigner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

/**
 * Created by Marko Rapka on 08.06.2017.
 */
public class MainMenuScreen implements Screen {

    private static final float BUTTON_WIDTH = 300f;
    private static final float BUTTON_HEIGHT = 60f;
    private static final float BUTTON_SPACING = 10f;

    private com.pukekogames.airportdesigner.Main main;

    private Stage stage;
    private Table table;
    private TextButton.TextButtonStyle skin;

    public MainMenuScreen(com.pukekogames.airportdesigner.Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        skin = new TextButton.TextButtonStyle();
        skin.font = main.font;

        stage = new Stage(new StretchViewport(1080,720));
        Gdx.input.setInputProcessor(stage);


        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setDebug(true); // This is optional, but enables debug lines for tables.

        // Add widgets to the table here.

        stage.clear();

        float width = stage.getWidth();

        final float buttonX = (width - BUTTON_WIDTH) / 2;
        float currentY = (stage.getHeight() / 3) * 2;

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = main.font;

        // label "welcome"
        Label welcomeLabel = new Label("Airport Designer", labelStyle);
        welcomeLabel.setX((width - welcomeLabel.getWidth()) / 2);
        welcomeLabel.setY(currentY + 100);
        stage.addActor(welcomeLabel);

        Pixmap buttonBackgroundColor = new Pixmap((int) BUTTON_WIDTH, (int) BUTTON_HEIGHT, Pixmap.Format.RGBA8888);
        buttonBackgroundColor.setColor(Color.GRAY);
        buttonBackgroundColor.fill();

        Pixmap buttonBackgroundColorPressed = new Pixmap((int) BUTTON_WIDTH, (int) BUTTON_HEIGHT, Pixmap.Format.RGBA8888);
        buttonBackgroundColorPressed.setColor(Color.DARK_GRAY);
        buttonBackgroundColorPressed.fill();

        skin.up = new Image(new Texture(buttonBackgroundColor)).getDrawable();
        skin.down = new Image(new Texture(buttonBackgroundColorPressed)).getDrawable();

        // button "load airport"
        TextButton loadAirportButton = new TextButton("Load Airport", skin);
        loadAirportButton.setX(buttonX);
        loadAirportButton.setY(currentY);
        loadAirportButton.setWidth(BUTTON_WIDTH);
        loadAirportButton.setHeight(BUTTON_HEIGHT);

        stage.addActor(loadAirportButton);

        // button "start new airport"
        TextButton startNewAirportButton = new TextButton("New Airport", skin);
        startNewAirportButton.setX(buttonX);
        startNewAirportButton.setY(currentY -= BUTTON_HEIGHT + BUTTON_SPACING);
        startNewAirportButton.setWidth(BUTTON_WIDTH);
        startNewAirportButton.setHeight(BUTTON_HEIGHT);
        startNewAirportButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen( new GameScreen(main) );
            }
        } );
        stage.addActor(startNewAirportButton);

        // button "debug airport"
        TextButton debugAirportButton = new TextButton("Debug Airport", skin);
        debugAirportButton.setX(buttonX);
        debugAirportButton.setY(currentY -= BUTTON_HEIGHT + BUTTON_SPACING);
        debugAirportButton.setWidth(BUTTON_WIDTH);
        debugAirportButton.setHeight(BUTTON_HEIGHT);
        stage.addActor(debugAirportButton);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}
