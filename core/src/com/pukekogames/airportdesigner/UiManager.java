package com.pukekogames.airportdesigner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.*;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Objects.Buildings.Depot;
import com.pukekogames.airportdesigner.Objects.ButtonCircle;
import com.pukekogames.airportdesigner.Objects.GameObject;
import com.pukekogames.airportdesigner.Objects.Vehicles.Airplane;
import com.pukekogames.airportdesigner.Objects.Vehicles.StreetVehicle;
import com.pukekogames.airportdesigner.Screens.ExitDialog;
import com.pukekogames.airportdesigner.Screens.GameScreen;
import com.pukekogames.airportdesigner.Screens.SaveDialog;

/**
 * Created by Marko Rapka on 13.07.2017.
 */
public class UiManager {

    public static int STD_WIDTH = 640;
    public static int STD_HEIGHT = 480;

    private Main main;
    private GameScreen gameScreen;

    public static Vector3 screenPos;
    public static Vector3 worldPos;
    ButtonCircle buttonCircle;
    ImageButton.ImageButtonStyle standardStyle;

    private Skin skin;
    private Stage screenStage;
    private Table table;
    private Table constructionTable;

    private Stage gameStage;

    public UiManager(Main main, GameScreen gameScreen) {
        this.main = main;
        this.gameScreen = gameScreen;

        STD_WIDTH = Gdx.graphics.getWidth();
        STD_HEIGHT = Gdx.graphics.getHeight();

        screenPos = new Vector3();
        worldPos = new Vector3();
        buttonCircle = new ButtonCircle(this);
    }

    public void setup() {
        skin = new Skin(Gdx.files.internal("ui\\uiskin.json"));

        final float buttonWidth = GameInstance.Settings().ButtonWidth * Gdx.graphics.getPpcX() / 25;
        final float buttonHeight = GameInstance.Settings().ButtonHeight * Gdx.graphics.getPpcY() / 25;
        int circleButtonDiameter = (int) (GameInstance.Settings().circleButtonWidth * Gdx.graphics.getPpcX() / 100f);
        if (GameInstance.Settings().isStartedOnMobile){
            circleButtonDiameter *= 2f;
        }
//        skin = new Skin();

//        skin.addRegions(main.assets.get("ui\\uiskin.atlas", TextureAtlas.class));
        skin.add("default-font", main.font);
//        skin.load(Gdx.files.internal("ui\\uiskin.json"));


        screenStage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        gameStage = new Stage(new ScreenViewport());
        table = new Table();
        table.setFillParent(true);
//        table.align(Align.center | Align.top);
        table.align(Align.right | Align.top);
//        table.setPosition(0, Gdx.graphics.getHeight());
//        table.setDebug(true);
        constructionTable = new Table();



        Drawable background = new SpriteDrawable(new Sprite(TextureLoader.Instance().getTexture(TextureLoader.indexCircleButtonBackground)));
        Drawable backgroundDown = new SpriteDrawable(new Sprite(TextureLoader.Instance().getTexture(TextureLoader.indexCircleButtonBackgroundClicked)));
//        Drawable image = new SpriteDrawable(new Sprite(TextureLoader.Instance().getTexture(TextureLoader.indexButtonBuild)));
        standardStyle = new ImageButton.ImageButtonStyle();
        standardStyle.up = background;
        standardStyle.down = backgroundDown;
        standardStyle.checked = background;
//        standardStyle.imageUp      = image;
//        standardStyle.imageDown    = image;
//        standardStyle.imageChecked = image;

//        button = new ImageButton(standardStyle);
//        button.setPosition(0,0);
//        button.setWidth(30);
//        button.setHeight(30);

//        ImageButton.ImageButtonStyle buildStyle = new ImageButton.ImageButtonStyle(standardStyle);
//        Drawable buildImage = new SpriteDrawable(new Sprite(TextureLoader.Instance().getTexture(TextureLoader.indexButtonBuild)));
//
//        buildStyle.imageUp = buildImage;
//        buildStyle.imageDown = buildImage;
//        buildStyle.imageChecked = buildImage;
//
//        ImageButton buildButton = new ImageButton(buildStyle);

        ImageButton buildButton = getButton(TextureLoader.indexButtonBuild);

        buildButton.setHeight(circleButtonDiameter);
        buildButton.setWidth(circleButtonDiameter);

        buildButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        ImageButton optionButton = getButton(TextureLoader.indexOptionButton);

        optionButton.setHeight(circleButtonDiameter);
        optionButton.setWidth(circleButtonDiameter);

        final SaveDialog saveDialog = new SaveDialog(main,screenStage, "", skin);

        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameInstance.Airport().setPauseSimulation(true);
                saveDialog.show(screenStage);
            }
        });

        ImageButton constructionButton = getButton(TextureLoader.indexButtonConstruct);

        constructionButton.setHeight(circleButtonDiameter);
        constructionButton.setWidth(circleButtonDiameter);

        constructionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean wasVisible = constructionTable.isVisible();
                constructionTable.setVisible(!wasVisible);
                Touchable t;
                if (constructionTable.isVisible()){
                    t = Touchable.enabled;
                }else{
                    t = Touchable.disabled;
                }
                constructionTable.setTouchable(t);
            }
        });

        ImageButton changeModeButton = getButton(TextureLoader.indexButtonBuildRoad);

        changeModeButton.setHeight(circleButtonDiameter);
        changeModeButton.setWidth(circleButtonDiameter);

        changeModeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });


        ImageButton buildBuildingButton = getButton(TextureLoader.indexButtonBuildDepot);

        buildBuildingButton.setHeight(circleButtonDiameter);
        buildBuildingButton.setWidth(circleButtonDiameter);

        buildBuildingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        ImageButton deleteRoadbutton = getButton(TextureLoader.indexButtonDelete);

        deleteRoadbutton.setHeight(circleButtonDiameter);
        deleteRoadbutton.setWidth(circleButtonDiameter);

        deleteRoadbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

            }
        });

        float padding = circleButtonDiameter / 2f;
        if (GameInstance.Settings().isStartedOnMobile){
            padding = circleButtonDiameter / 4f;
        }
        table.defaults().padRight(padding);
        table.add(constructionTable);

        constructionTable.add(changeModeButton).padRight(padding);
        constructionTable.add(buildBuildingButton).padRight(padding);
        constructionTable.add(deleteRoadbutton);

        table.add(optionButton).padBottom(padding);
        table.row();
        table.add();
        table.add(constructionButton);


//        TextButton startButton = new TextButton("Start Game", skin);
//        TextButton quitButton = new TextButton("Quit Game", skin);
//
//        table.padTop(buttonHeight / 5);
//        table.add(startButton).width(buttonWidth).height(buttonHeight).padBottom(buttonHeight / 5);
//
//        table.add(quitButton).width(buttonWidth).height(buttonHeight);
//
//        final ExitDialog dialog = new ExitDialog("", skin);
//
//
//        quitButton.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//			    GameInstance.Airport().setPauseSimulation(true);
//                dialog.show(screenStage);
//
//			}
//		});


//        gameStage.addActor(button);
        screenStage.addActor(table);
    }

    private ImageButton getButton(int textureId) {
        ImageButton.ImageButtonStyle buildStyle = new ImageButton.ImageButtonStyle(standardStyle);
        Drawable buildImage = new SpriteDrawable(new Sprite(TextureLoader.Instance().getTexture(textureId)));

        buildStyle.imageUp = buildImage;
        buildStyle.imageDown = buildImage;
        buildStyle.imageChecked = buildImage;

        return new ImageButton(buildStyle);
    }

    public void tick(float delta) {
        screenStage.act(delta);
        gameStage.act(delta);

        int width = gameStage.getViewport().getScreenWidth();
        int height = gameStage.getViewport().getScreenHeight();

        float diffWidth = (STD_WIDTH - width) / 2f;
        float diffHeight = (STD_HEIGHT - height) / 2f;


        buttonCircle.setDiffs(diffWidth, diffHeight);
        buttonCircle.tick();
        if (buttonCircle.shouldRemove()) {
            removeCircleButtons();
        }

//        Airplane airplane = GameInstance.Airport().getAirplane(0);
//        if (airplane != null) {
//            worldPos.set(airplane.getX(), airplane.getY(), 0);
////
//            screenPos.set(worldPos);
//            gameScreen.getCamera().project(screenPos);
//
//            float x = gameStage.getViewport().getCamera().position.x;
//            float y = gameStage.getViewport().getCamera().position.y;
//
//
//            button.setPosition(screenPos.x + diffWidth - button.getWidth() / 2, screenPos.y + diffHeight - button.getHeight() / 2);
//        }
    }

    void removeCircleButtons() {
//        buttons.removeAll(buttonCircle.getButtons());
        for (ImageButton button :
                buttonCircle.getButtons()) {
            button.remove();
        }
        buttonCircle.clearButtons();
    }

    public void projectVector(Vector3 vector) {
        gameScreen.getCamera().project(vector);
    }

    void createCircleButtons(GameObject object, int mx, int my) {

        int circleButtonDiameter = (int) (GameInstance.Settings().circleButtonWidth * Gdx.graphics.getPpcX() / 70f);

        buttonCircle.setRadius((int) (circleButtonDiameter * 1.2f));

        if (object instanceof StreetVehicle) {
            final StreetVehicle vehicle = (StreetVehicle) object;

//            if (GameInstance.Settings().CollisionDetection && GameInstance.Settings().DebugMode) {

            removeCircleButtons();
            buttonCircle.setObject(vehicle);

            ImageButton.ImageButtonStyle deleteStyle = new ImageButton.ImageButtonStyle(standardStyle);
            Drawable deleteImage = new SpriteDrawable(new Sprite(TextureLoader.Instance().getTexture(TextureLoader.indexButtonDelete)));

            deleteStyle.imageUp = deleteImage;
            deleteStyle.imageDown = deleteImage;
            deleteStyle.imageChecked = deleteImage;

            ImageButton deleteButton = new ImageButton(deleteStyle);

            deleteButton.setHeight(circleButtonDiameter);
            deleteButton.setWidth(circleButtonDiameter);

            deleteButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameInstance.Airport().RemoveVehicle(vehicle);
                    removeCircleButtons();
                }
            });

            buttonCircle.addButton(deleteButton);
            gameStage.addActor(deleteButton);

            ImageButton.ImageButtonStyle ignoreCollisionStyle = new ImageButton.ImageButtonStyle(standardStyle);
            Drawable ignoreCollisionImage = new SpriteDrawable(new Sprite(TextureLoader.Instance().getTexture(TextureLoader.indexOptionButton)));

            ignoreCollisionStyle.imageUp = ignoreCollisionImage;
            ignoreCollisionStyle.imageDown = ignoreCollisionImage;
            ignoreCollisionStyle.imageChecked = ignoreCollisionImage;

            ImageButton ignoreCollisionButton = new ImageButton(ignoreCollisionStyle);

            ignoreCollisionButton.setHeight(circleButtonDiameter);
            ignoreCollisionButton.setWidth(circleButtonDiameter);

            ignoreCollisionButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    vehicle.IgnoreCollision();
                }
            });

            buttonCircle.addButton(ignoreCollisionButton);
            gameStage.addActor(ignoreCollisionButton);

//            }

        }
        if (object instanceof Depot) {

            final Depot depot = (Depot) object;

            removeCircleButtons();
            buttonCircle.setObject(depot);

            ImageButton.ImageButtonStyle infoStyle = new ImageButton.ImageButtonStyle(standardStyle);
            Drawable infoImage = new SpriteDrawable(new Sprite(TextureLoader.Instance().getTexture(TextureLoader.indexCircleButtonInfo)));

            infoStyle.imageUp = infoImage;
            infoStyle.imageDown = infoImage;
            infoStyle.imageChecked = infoImage;

            ImageButton infoButton = new ImageButton(infoStyle);

            infoButton.setHeight(circleButtonDiameter);
            infoButton.setWidth(circleButtonDiameter);

            infoButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
//                    depot.IgnoreCollision();
                    System.out.println("info");
                }
            });

            buttonCircle.addButton(infoButton);
            gameStage.addActor(infoButton);


//            Button showInfo = new Button(Alignment.Table, 0, 0, Settings.Instance().ButtonCircleDiameter, Settings.Instance().ButtonCircleDiameter, new Command() {
//                @Override
//                public void execute(Object object) {
//                    game.setDepotScreen();
//                }
//            });
////                    showInfo.setContent(game.getString(R.string.ButtonCircle_InfoButton_Text));
//            showInfo.setImageID(BitmapLoader.indexCircleButtonInfo);
//            buttonCircle.addButton(showInfo);
//
//            objects.addAll(buttonCircle.getButtons());
        }

        if (object instanceof Airplane) {
            final Airplane plane = (Airplane) object;

        }

    }

    public void render(SpriteBatch batch) {

//        batch.begin();
//        button.draw(batch, 0);
//        batch.end();

        gameStage.draw();

        screenStage.draw();

    }

    public void resize(int width, int height) {
        screenStage.getViewport().update(width, height);
        gameStage.getViewport().update(width, height);
    }

    public void dispose() {
        gameStage.dispose();
        screenStage.dispose();
    }

    public Stage getScreenStage() {
        return screenStage;
    }

    public Stage getGameStage() {
        return gameStage;
    }
}
