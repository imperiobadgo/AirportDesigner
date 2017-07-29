package com.pukekogames.airportdesigner.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pukekogames.airportdesigner.Helper.GameStart;

/**
 * Created by Marko Rapka on 08.06.2017.
 */
public class MainMenuScreen implements Screen {

    private static final float BUTTON_WIDTH = 300f;
    private static final float BUTTON_HEIGHT = 60f;
    private static final float BUTTON_SPACING = 30f;

    private com.pukekogames.airportdesigner.Main main;

    private Stage stage;
    private Table table;
    private TextButton.TextButtonStyle skin;
    private Skin normalSkin;

    public MainMenuScreen(com.pukekogames.airportdesigner.Main main) {
        this.main = main;
    }

    @Override
    public void show() {
        skin = new TextButton.TextButtonStyle();
        normalSkin = new Skin(Gdx.files.internal("ui\\uiskin.json"));
        normalSkin.add("default-font", main.font);
        skin.font = main.font;

        stage = new Stage(new StretchViewport(1080,720));
        Gdx.input.setInputProcessor(stage);


        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        table.setDebug(false); // This is optional, but enables debug lines for tables.

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
        buttonBackgroundColor.setColor(new Color(0,0.1f,0.5f,1));
        buttonBackgroundColor.fill();

        Pixmap buttonBackgroundColorPressed = new Pixmap((int) BUTTON_WIDTH, (int) BUTTON_HEIGHT, Pixmap.Format.RGBA8888);
        buttonBackgroundColorPressed.setColor(new Color(0,0.1f,0.7f,1));
        buttonBackgroundColorPressed.fill();

        skin.up = new Image(new Texture(buttonBackgroundColor)).getDrawable();
        skin.down = new Image(new Texture(buttonBackgroundColorPressed)).getDrawable();



        // button "load airport"
        TextButton loadAirportButton = new TextButton("Load Airport", normalSkin);
        loadAirportButton.setX(buttonX);
        loadAirportButton.setY(currentY);
        loadAirportButton.setWidth(BUTTON_WIDTH);
        loadAirportButton.setHeight(BUTTON_HEIGHT);
        loadAirportButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen( new GameScreen(main, GameStart.Load) );
            }
        } );
        stage.addActor(loadAirportButton);

        // button "start new airport"
        TextButton startNewAirportButton = new TextButton("New Airport", normalSkin);
        startNewAirportButton.setX(buttonX);
        startNewAirportButton.setY(currentY -= BUTTON_HEIGHT + BUTTON_SPACING);
        startNewAirportButton.setWidth(BUTTON_WIDTH);
        startNewAirportButton.setHeight(BUTTON_HEIGHT);
        startNewAirportButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen( new GameScreen(main, GameStart.New) );
            }
        } );
        stage.addActor(startNewAirportButton);

        // button "debug airport"
        TextButton debugAirportButton = new TextButton("Debug Airport", normalSkin);
        debugAirportButton.setX(buttonX);
        debugAirportButton.setY(currentY -= BUTTON_HEIGHT + BUTTON_SPACING);
        debugAirportButton.setWidth(BUTTON_WIDTH);
        debugAirportButton.setHeight(BUTTON_HEIGHT);
        debugAirportButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                main.setScreen( new GameScreen(main, GameStart.Debug) );
            }
        } );
        stage.addActor(debugAirportButton);

        TextButton exitButton = new TextButton("Exit", normalSkin);
        exitButton.setX(buttonX);
        exitButton.setY(currentY -= BUTTON_HEIGHT + BUTTON_SPACING);
        exitButton.setWidth(BUTTON_WIDTH);
        exitButton.setHeight(BUTTON_HEIGHT);
        exitButton.addListener( new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        } );
        stage.addActor(exitButton);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
