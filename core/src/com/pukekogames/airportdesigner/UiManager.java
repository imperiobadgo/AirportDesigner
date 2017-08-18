package com.pukekogames.airportdesigner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.pukekogames.airportdesigner.Helper.ClassTranslation.BuildingType;
import com.pukekogames.airportdesigner.Helper.ClassTranslation.RoadType;
import com.pukekogames.airportdesigner.Helper.CommonMethods;
import com.pukekogames.airportdesigner.Objects.Buildings.Depot;
import com.pukekogames.airportdesigner.Objects.ButtonCircle;
import com.pukekogames.airportdesigner.Objects.GameObject;
import com.pukekogames.airportdesigner.Objects.RoadIntersection;
import com.pukekogames.airportdesigner.Objects.Roads.*;
import com.pukekogames.airportdesigner.Objects.Vehicles.Airplane;
import com.pukekogames.airportdesigner.Objects.Vehicles.StreetVehicle;
import com.pukekogames.airportdesigner.Screens.*;
import com.pukekogames.airportdesigner.Screens.TimeTabelContent.TimeRowWindow;
import com.pukekogames.airportdesigner.Screens.TimeTabelContent.TimeTableScreen;

import java.util.ArrayList;

/**
 * Created by Marko Rapka on 13.07.2017.
 */
public class UiManager {

    public static int STD_WIDTH = 640;
    public static int STD_HEIGHT = 480;

    private Main main;
    private GameScreen gameScreen;
    private Handler handler;

    public static Vector3 screenPos;
    public static Vector3 worldPos;
    ButtonCircle buttonCircle;
    ImageButton.ImageButtonStyle standardStyle;

    private Skin skin;
    private Stage screenStage;
    private Table table;
    private Table constructionTable;
    private Table infoTable;
    private Label moneyLabel;
    private Label levelLabel;
    private Label modeLabel;
    private Label gameSpeedLabel;
    private Label timeLabel;


    private Stage gameStage;

    private ImageButton removeSelectionButton;
    private ImageButton buildRoadButton;
    private TextButton gameSpeedButton;

    public UiManager(Main main, GameScreen gameScreen) {
        this.main = main;
        this.gameScreen = gameScreen;

        STD_WIDTH = Gdx.graphics.getWidth();
        STD_HEIGHT = Gdx.graphics.getHeight();

        screenPos = new Vector3();
        worldPos = new Vector3();
        buttonCircle = new ButtonCircle(this);
        Settings.Instance().uiManager = this;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setup() {
        skin = new Skin(Gdx.files.internal("ui\\uiskin.json"));

        final float buttonWidth = GameInstance.Settings().ButtonWidth * Gdx.graphics.getPpcX() / 25;
        final float buttonHeight = GameInstance.Settings().ButtonHeight * Gdx.graphics.getPpcY() / 25;
        int circleButtonDiameter = (int) (GameInstance.Settings().circleButtonWidth * Gdx.graphics.getPpcX() / 100f);
        if (Main.IS_STARTED_ON_MOBILE) {
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
//        table.align(Align.right | Align.top);
//        table.setPosition(0, Gdx.graphics.getHeight());
//        table.setDebug(true);
        constructionTable = new Table();
        infoTable = new Table();


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

        ImageButton optionButton = getButton(TextureLoader.indexOptionButton);

        optionButton.setHeight(circleButtonDiameter);
        optionButton.setWidth(circleButtonDiameter);

        final SaveDialog saveDialog = new SaveDialog(main, screenStage, "", skin);

        optionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!GameInstance.Airport().isPauseSimulation()) {
                    GameInstance.Airport().setPauseSimulation(true);

                    saveDialog.show(screenStage);
                }
            }
        });

        ImageButton constructionButton = getButton(TextureLoader.indexButtonBuild);

        constructionButton.setHeight(circleButtonDiameter);
        constructionButton.setWidth(circleButtonDiameter);

        constructionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean wasVisible = constructionTable.isVisible();
                constructionTable.setVisible(!wasVisible);
                Touchable t;
                switchBuild(0);
                if (constructionTable.isVisible()) {
                    t = Touchable.enabled;
                    GameInstance.Airport().setPauseSimulation(true);
                } else {
                    t = Touchable.disabled;
                }
                constructionTable.setTouchable(t);
            }
        });
        constructionTable.setVisible(false);
        constructionTable.setTouchable(Touchable.disabled);
        final ImageButton changeModeButton = getButton(TextureLoader.indexButtonBuildRoad);

        changeModeButton.setHeight(circleButtonDiameter);
        changeModeButton.setWidth(circleButtonDiameter);

        ArrayList<String> classNamesList = new ArrayList<>();

        classNamesList.add("Taxiway");
        classNamesList.add("Runway");
        classNamesList.add("Street");
        classNamesList.add("ParkGate");

        final RoadListDialog roadListDialog = new RoadListDialog(main, classNamesList, "", skin);

        changeModeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                GameInstance.Airport().setPauseSimulation(true);
                roadListDialog.show(screenStage);
                switchBuild(1);
            }
        });


        ImageButton buildBuildingButton = getButton(TextureLoader.indexButtonBuildDepot);

        buildBuildingButton.setHeight(circleButtonDiameter);
        buildBuildingButton.setWidth(circleButtonDiameter);

        ArrayList<String> buildingClassList = new ArrayList<>();

        buildingClassList.add("CrewBusDepot");
        buildingClassList.add("BusDepot");
        buildingClassList.add("BaggageDepot");
        buildingClassList.add("TankDepot");
        buildingClassList.add("CateringDepot");

        if (GameInstance.Settings().level >= 3) {
            buildingClassList.add("Terminal");
        }
        if (GameInstance.Settings().level > 4 && GameInstance.Airport().getTower() == null) {
            buildingClassList.add("Tower");
        }

        final DepotListDialog depotListDialog = new DepotListDialog(main, buildingClassList, "", skin);

        buildBuildingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {


                depotListDialog.show(screenStage);

                switchBuild(3);
//                changeModeButton.setTouchable(Touchable.disabled);
//                changeModeButton.setVisible(false);
            }
        });

        ImageButton deleteRoadbutton = getButton(TextureLoader.indexButtonDelete);

        deleteRoadbutton.setHeight(circleButtonDiameter);
        deleteRoadbutton.setWidth(circleButtonDiameter);

        deleteRoadbutton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchBuild(2);
            }
        });

        removeSelectionButton = getButton(TextureLoader.indexButtonCancel);
        removeSelectionButton.setHeight(circleButtonDiameter);
        removeSelectionButton.setWidth(circleButtonDiameter);
        removeSelectionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                clearSelectableObjects();
            }
        });


        buildRoadButton = getButton(TextureLoader.indexButtonConstruct);
        buildRoadButton.setHeight(circleButtonDiameter);
        buildRoadButton.setWidth(circleButtonDiameter);

        setupBuildRoadButton();

        final ImageButton timeTableButton = getButton(TextureLoader.indexCircleButtonTakeOff);

        timeTableButton.setHeight(circleButtonDiameter);
        timeTableButton.setWidth(circleButtonDiameter);

//        final DepotListDialog depotListDialog = new DepotListDialog(main, buildingClassList, "", skin);

        final TimeRowWindow timeRowWindow = new TimeRowWindow(main,screenStage, skin);

        timeTableButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameInstance.Airport().setPauseSimulation(true);
                timeRowWindow.setupScreen();
                timeRowWindow.show(screenStage);
            }
        });


        BitmapFont boldFont = new BitmapFont(Gdx.files.internal("ArialBasic.fnt"), Gdx.files.internal("ArialBasic.png"), false);

        Label.LabelStyle boldStyle = new Label.LabelStyle(boldFont, Color.BLACK);
        boldStyle.font.getData().setScale(1.5f);

        Label.LabelStyle style = new Label.LabelStyle(main.font, Color.BLACK);

        moneyLabel = new Label("Test", boldStyle);
        levelLabel = new Label("Test", style);
        modeLabel = new Label("Test", style);
        gameSpeedLabel = new Label("Test", style);
        timeLabel = new Label("Test", style);

        Drawable drawable = skin.getDrawable("default-rect-pad");


        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle(drawable, drawable, drawable, main.font);
        gameSpeedButton = new TextButton("test", textButtonStyle);
        gameSpeedButton.setHeight(30f);
        gameSpeedButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (GameInstance.Settings().gameSpeed == 1) {
                    GameInstance.Settings().gameSpeed = 2;
                } else if (GameInstance.Settings().gameSpeed == 2) {
                    GameInstance.Settings().gameSpeed = 5;
                } else if (GameInstance.Settings().gameSpeed == 5) {
                    GameInstance.Settings().gameSpeed = 10;
                } else {
                    GameInstance.Settings().gameSpeed = 1;
                }
            }
        });

        TextButton debugButton = new TextButton("Debug", textButtonStyle);
        debugButton.setHeight(30f);
        debugButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameInstance.Settings().DebugMode = !GameInstance.Settings().DebugMode;
            }
        });

        float padding = circleButtonDiameter / 2f;
        if (Main.IS_STARTED_ON_MOBILE) {
            padding = circleButtonDiameter / 4f;
        }
        table.defaults().padRight(padding);
        table.add(infoTable).left().top();
        table.add(constructionTable).right().top().expandX();

        infoTable.add(moneyLabel).left().padRight(30f);
        infoTable.add(timeLabel).row();
        infoTable.add(levelLabel).left().row();
        infoTable.add(modeLabel).left().row();
        infoTable.add(gameSpeedButton).left().row();
        infoTable.add(debugButton).left().row();

        constructionTable.add(changeModeButton).padRight(padding);
        constructionTable.add(buildBuildingButton).padRight(padding);
        constructionTable.add(deleteRoadbutton);

        table.add(optionButton).padBottom(padding).right().align(Align.top | Align.right);
        table.row();
        table.add();
        table.add();
        table.add(constructionButton).align(Align.top | Align.right);
        table.row().expandY();

        table.add(removeSelectionButton).align(Align.left | Align.bottom);
        table.add();
        table.add(timeTableButton).align(Align.top | Align.right);

        table.row();
        table.add(buildRoadButton).align(Align.left | Align.bottom).padBottom(100f);
        table.add();
        table.add();

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
        switchBuild(0);
    }

    private void setupBuildRoadButton() {
        buildRoadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (handler.buildRoad != null) {
                    if (handler.intersectPoints.size() > 0 || handler.buildCost == 0) {
//                        Toast toast = Toast.makeText(game, R.string.Building_Collision_Toast, Toast.LENGTH_SHORT);
//                        toast.show();
                        return;
                    }

                    if (handler.buildRoad instanceof Runway) {
                        if (handler.buildRoad.getLength() < 4000) {
//                            Toast toast = Toast.makeText(game, R.string.Building_RunwayToShort_Toast, Toast.LENGTH_SHORT);
//                            toast.show();
                            return;
                        }
                    }
//                    if (buildRoad instanceof ParkGate) {
//                        if (buildRoad.getLength() < 1200) {
//                            Toast toast = Toast.makeText(game, R.string.Building_ParkGateToShort_Toast, Toast.LENGTH_SHORT);
//                            toast.show();
//                            return;
//                        }
//                    }

                    //money check
                    if (GameInstance.Instance().removeMoney(handler.buildCost)) {
                        RoadIntersection intersection = null;
                        boolean shouldAddIntersection = true;
                        for (int i = 0; i < GameInstance.Airport().getRoadIntersectionCount(); i++) {
                            RoadIntersection tempIntersection = GameInstance.Airport().getRoadIntersection(i);
                            double distance = CommonMethods.getDistance(tempIntersection.getPosition(), handler.buildIntersection.getPosition());
                            if (distance < 0.1) {
                                intersection = tempIntersection;
                                shouldAddIntersection = false;
                                break;
                            }
                        }

                        if (shouldAddIntersection) {

                            intersection = new RoadIntersection(handler.buildIntersection.getPosition());

                            GameInstance.Airport().AddRoadIntersection(intersection);//to prevent referencing to the changeable buildingRoadIntersection
                        }
                        handler.buildRoad.setNext(intersection);
                        GameInstance.Airport().AddRoad(handler.buildRoad);

                        handler.buildRoad = null;
                        handler.firstRoadIntersection = null;
                        setSelectableRoadIntersections(null);
                    } else {
//                        Toast toast = Toast.makeText(game, R.string.Building_NotEnoughMoney_Toast, Toast.LENGTH_SHORT);
//                        toast.show();
                    }
                }
            }
        });
    }

    private ImageButton getButton(int textureId) {
        ImageButton.ImageButtonStyle buildStyle = new ImageButton.ImageButtonStyle(standardStyle);
        Drawable buildImage = new SpriteDrawable(new Sprite(TextureLoader.Instance().getTexture(textureId)));

        buildStyle.imageUp = buildImage;
        buildStyle.imageDown = buildImage;
        buildStyle.imageChecked = buildImage;

        return new ImageButton(buildStyle);
    }

    public void clearSelectableObjects() {
        handler.clearSelectableObjects();
        removeSelectionButton.setVisible(false);
        removeSelectionButton.setTouchable(Touchable.disabled);
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

        long money = GameInstance.Instance().getMoney();
        moneyLabel.setText(String.format("%1$d Euro", money));

        int hour = GameInstance.Instance().getHour();
        int minute = GameInstance.Instance().getMinute();
        timeLabel.setText(String.format("%02d", hour) + ":" + String.format("%02d", minute));
        levelLabel.setText(String.format("Level %1$d", GameInstance.Settings().level));
        modeLabel.setText(String.format("Mode %1$d", GameInstance.Settings().buildMode));
        gameSpeedLabel.setText(String.format("GS %1$d", GameInstance.Settings().gameSpeed));
        gameSpeedButton.setText(String.format("GS %1$d", GameInstance.Settings().gameSpeed));
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

            if (GameInstance.Settings().CollisionDetection && GameInstance.Settings().DebugMode) {

                removeCircleButtons();
                buttonCircle.setObject(vehicle);

                ImageButton deleteButton = getButton(TextureLoader.indexButtonDelete);

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

                ImageButton ignoreCollisionButton = getButton(TextureLoader.indexOptionButton);

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

            }

        }
        if (object instanceof Depot) {

            final Depot depot = (Depot) object;

            removeCircleButtons();
            buttonCircle.setObject(depot);

            ImageButton infoButton = getButton(TextureLoader.indexCircleButtonInfo);

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

            removeCircleButtons();
            buttonCircle.setObject(plane);
            if (GameInstance.Settings().DebugMode) {
                ImageButton deleteButton = getButton(TextureLoader.indexButtonDelete);

                deleteButton.setHeight(circleButtonDiameter);
                deleteButton.setWidth(circleButtonDiameter);

                deleteButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        GameInstance.Airport().RemoveVehicle(plane);
                        removeCircleButtons();
                    }
                });

                buttonCircle.addButton(deleteButton);
                gameStage.addActor(deleteButton);
            }

            if (GameInstance.Settings().CollisionDetection && GameInstance.Settings().DebugMode) {

                ImageButton ignoreCollisionButton = getButton(TextureLoader.indexOptionButton);

                ignoreCollisionButton.setHeight(circleButtonDiameter);
                ignoreCollisionButton.setWidth(circleButtonDiameter);

                ignoreCollisionButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        plane.IgnoreCollision();
                    }
                });

                buttonCircle.addButton(ignoreCollisionButton);
                gameStage.addActor(ignoreCollisionButton);
            }

            switch (plane.getState()) {

                case Init:
                    break;
                case Waiting:
                    break;
                case Arrival:
                    break;
                case Landing:
                    break;
                case WaitingForGate:

                case ReadyForPushback:

                    ImageButton goToTarget = getButton(TextureLoader.indexCircleButtonGoto);
                    goToTarget.setHeight(circleButtonDiameter);
                    goToTarget.setWidth(circleButtonDiameter);

                    goToTarget.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            clearSelectableObjects();
                            handler.selectableGameObjects.addAll(plane.getPossibleTargets());
                            handler.choiceForThis = plane;
                            if (handler.selectableGameObjects.size() > 0) {
                                removeSelectionButton.setTouchable(Touchable.enabled);
                                removeSelectionButton.setVisible(true);
                            }
                            removeCircleButtons();
                        }
                    });

                    buttonCircle.addButton(goToTarget);
                    screenStage.addActor(goToTarget);
                    break;

                case ArrivedAtGate:
                case Boarding:
                    if (plane.isServiceNotPossible()) {
                        goToTarget = getButton(TextureLoader.indexCircleButtonGoto);
                        goToTarget.setHeight(circleButtonDiameter);
                        goToTarget.setWidth(circleButtonDiameter);

                        goToTarget.addListener(new ClickListener() {
                            @Override
                            public void clicked(InputEvent event, float x, float y) {
                                clearSelectableObjects();
                                handler.selectableGameObjects.addAll(plane.getPossibleTargets());
                                handler.choiceForThis = plane;
                                if (handler.selectableGameObjects.size() > 0) {
                                    removeSelectionButton.setTouchable(Touchable.enabled);
                                    removeSelectionButton.setVisible(true);
                                }
                                removeCircleButtons();
                            }
                        });

                        buttonCircle.addButton(goToTarget);
                        screenStage.addActor(goToTarget);
                    }
                    break;

                case Pushback:
                    break;
                case TaxiToGate:
                case TaxiToRunway:
                    ImageButton toggleHoldPosition = getButton(TextureLoader.indexCircleButtonHold);
                    toggleHoldPosition.setHeight(circleButtonDiameter);
                    toggleHoldPosition.setWidth(circleButtonDiameter);

                    toggleHoldPosition.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            plane.setHoldPosition(!plane.isHoldPosition());
                        }
                    });

                    buttonCircle.addButton(toggleHoldPosition);
                    screenStage.addActor(toggleHoldPosition);
                    break;

                case ReadyForDeparture:
                    ImageButton clearedForDepartureButton = getButton(TextureLoader.indexCircleButtonTakeOff);
                    clearedForDepartureButton.setHeight(circleButtonDiameter);
                    clearedForDepartureButton.setWidth(circleButtonDiameter);

                    clearedForDepartureButton.addListener(new ClickListener() {
                        @Override
                        public void clicked(InputEvent event, float x, float y) {
                            for (RoadIntersection intersection : plane.getPossibleTargets()) {
                                plane.searchRoute(intersection);
                                break;
                            }
                            plane.setHoldPosition(false);
                            buttonCircle.setCooldown();
                        }
                    });

                    buttonCircle.addButton(clearedForDepartureButton);
                    screenStage.addActor(clearedForDepartureButton);
                    break;
                case ClearedForDeparture:
                    break;
                case Takeoff:
                    break;
                case Departure:
                    break;
            }

        }

    }

    public void setSelectableRoadIntersections(RoadIntersection lastIntersection) {
        handler.selectableGameObjects.clear();
        for (int i = 0; i < GameInstance.Airport().getRoadIntersectionCount(); i++) {
            RoadIntersection intersection = GameInstance.Airport().getRoadIntersection(i);

            if (intersection.equals(lastIntersection)) continue;//dont add selected intersection

            Road[] roadArray = intersection.getRoadArray();

            if (GameInstance.Settings().buildRoad == null) {
                GameInstance.Settings().buildRoad = RoadType.None;
            }

            switch (GameInstance.Settings().buildRoad) {

                case None:
                    break;

                case taxiway:
                    boolean noTaxiwayOrRunway = true;
                    for (int j = 0; j < roadArray.length; j++) {
                        Road connectedRoad = roadArray[j];
                        if (connectedRoad instanceof Taxiway || connectedRoad instanceof Runway) {
                            noTaxiwayOrRunway = false;
                            break;
                        }
                    }
                    if (noTaxiwayOrRunway) {
                        continue;
                    }
                    break;

                case runway:
                    boolean noTaxiway = true;
                    for (int j = 0; j < roadArray.length; j++) {
                        Road connectedRoad = roadArray[j];
                        if (connectedRoad instanceof Runway) {
                            noTaxiway = true;
                            break;
                        }
                        if (connectedRoad instanceof Taxiway) {
                            noTaxiway = false;
                        }
                    }
                    if (noTaxiway) {
                        continue;
                    }
                    break;

                case street:
                    boolean noStreet = true;
                    for (int j = 0; j < roadArray.length; j++) {
                        Road connectedRoad = roadArray[j];
                        if (connectedRoad instanceof Street) {
                            noStreet = false;
                            break;
                        }
                        if (connectedRoad instanceof ParkGate && connectedRoad.getNext().equals(intersection)) {
                            noStreet = false;
                            break;
                        }
                    }
                    if (noStreet) {
                        continue;
                    }
                    break;

                case parkGate:

                    boolean noTaxiwayForGate = true;
                    for (int j = 0; j < roadArray.length; j++) {
                        Road connectedRoad = roadArray[j];
                        if (connectedRoad instanceof ParkGate || connectedRoad instanceof Runway) {
                            noTaxiwayForGate = true;
                            break;
                        }
                        if (handler.buildRoad == null && connectedRoad instanceof Taxiway) {
                            noTaxiwayForGate = false;
                        }
                        if (handler.buildRoad != null && connectedRoad instanceof Street) {
                            noTaxiwayForGate = false;
                        }
                    }
                    if (noTaxiwayForGate) {
                        continue;
                    }
                    break;
                default:

            }

            handler.selectableGameObjects.add(intersection);
        }
    }

    void switchBuild(int newBuild) {
        clearSelectableObjects();
        removeCircleButtons();
//
//        //reset buttons
        buildRoadButton.setVisible(false);
        buildRoadButton.setTouchable(Touchable.disabled);
//        showNextAirplanesButton.setEnabled(false);
//        showNextAirplanesButton.setNoVisual(true);
        switch (newBuild) {
            case 1:
                GameInstance.Airport().setPauseSimulation(true);
                GameInstance.Settings().buildMode = 1;
                buildRoadButton.setVisible(true);
                buildRoadButton.setTouchable(Touchable.enabled);

                setSelectableRoadIntersections(null);
                break;
            case 2:
                GameInstance.Settings().buildMode = 2;
                GameInstance.Airport().setPauseSimulation(true);

                gameScreen.getHandler().setAllDeletableObjects();

                break;
            case 3:
                GameInstance.Settings().buildMode = 3;
                GameInstance.Airport().setPauseSimulation(true);
                handler.selectableGameObjects.clear();

                gameScreen.getHandler().setSelectableObjectsForBuildBuilding();

                for (int i = 0; i < GameInstance.Airport().getRoadCount(); i++) {
                    Road road = GameInstance.Airport().getRoad(i);
                    if (road.getLength() > GameInstance.Settings().buildMinRadius * 1.2 && road instanceof Street && road.getBuilding() == null)
                        handler.selectableGameObjects.add(road);
                }
                break;
            default:
//                showNextAirplanesButton.setEnabled(true);
//                showNextAirplanesButton.setNoVisual(false);

                GameInstance.Settings().buildMode = newBuild;
                GameInstance.Settings().buildRoad = RoadType.None;
                GameInstance.Settings().buildDepot = BuildingType.None;
                GameInstance.Settings().selectionCompleted = false;
                GameInstance.Settings().buildPrice = 0L;
                gameScreen.getHandler().selectableGameObjects.clear();
                gameScreen.getHandler().firstRoadIntersection = null;
                gameScreen.getHandler().buildRoad = null;
                GameInstance.Airport().setPauseSimulation(false);
                GameInstance.Airport().CheckGateServicePossibility();
                break;
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
        GameInstance.Settings().screenSize.set(width,height);
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

    public GameScreen getGameScreen(){
        return gameScreen;
    }
}
