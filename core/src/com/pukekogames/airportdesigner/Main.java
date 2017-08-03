package com.pukekogames.airportdesigner;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pukekogames.airportdesigner.GameInstance.GameInstance;
import com.pukekogames.airportdesigner.Screens.GameScreen;
import com.pukekogames.airportdesigner.Screens.MainMenuScreen;
import com.pukekogames.airportdesigner.Screens.TimeTabelContent.TimeTableScreen;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Game {
	SpriteBatch batch;
	ArrayList<Texture> textures;
	private float rotation = 0f;
	private long startTime;
	private long totalTime;
	private int frameCount;
	private Random random;

	public AssetManager assets;

	private Stage stage;
	private Skin skin;
	private Table table;
	private TextButton startButton;
	private TextButton quitButton;

	private Stage gameStage;

	public BitmapFont font;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assets = new AssetManager();
		assets.load("airlines/icons.atlas", TextureAtlas.class);

//		textures = new ArrayList<Texture>();
//		textures.add(new Texture("airplane_a320.png"));
//		textures.add(new Texture("airplane_777.png"));
//		textures.add(new Texture("airplane_cessna.png"));
//		textures.add(new Texture("airplane_a380.png"));
//
//		textures.add(new Texture("runway_end.jpg"));
//		textures.add(new Texture("taxiway.jpg"));
//		textures.add(new Texture("parkgate.jpg"));
//		textures.add(new Texture("tower.png"));

		random = new Random();

		font = new BitmapFont(Gdx.files.internal("ArialBasic.fnt"), Gdx.files.internal("ArialBasic.png"), false);

		skin = new Skin(Gdx.files.internal("ui\\uiskin.json"));
		stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));
		gameStage = new Stage(new StretchViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight()));


		table = new Table();
		table.setWidth(stage.getWidth());
		table.align(Align.center|Align.top);
		table.setPosition(0, Gdx.graphics.getHeight());

//		final Dialog startDialog = new Dialog("Start Message", skin);
//		final Dialog quitDialog = new Dialog("Quit Message", skin);
//
//		startDialog.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				startDialog.hide();
//			}
//		});
//		quitDialog.addListener(new ClickListener() {
//			@Override
//			public void clicked(InputEvent event, float x, float y) {
//				quitDialog.hide();
//			}
//		});
//
//		startButton = new TextButton("Start Game", skin);
//		quitButton = new TextButton("Quit Game", skin);
//
//
//
//		startButton.addListener(new ClickListener(){
//			@Override
//			public void clicked(InputEvent event, float x, float y){
//				startDialog.show(stage);
//				Timer.schedule(new Timer.Task() {
//					@Override
//					public void run() {
//						startDialog.hide();
//					}
//				},5);
//			}
//		});
//
//		quitButton.addListener(new ClickListener(){
//			@Override
//			public void clicked(InputEvent event, float x, float y){
//				quitDialog.show(stage);
//				Timer.schedule(new Timer.Task() {
//					@Override
//					public void run() {
//						quitDialog.hide();
//					}
//				},5);
//			}
//		});


//		table.padTop(30);
//		table.add(startButton).padBottom(30);
//		table.row();
//		table.add(quitButton);
//
//		stage.addActor(table);

        Label progressLabel = new Label("", skin);

		final TextButton button = new TextButton("Click Me", skin, "default");
		button.setWidth(200);
		button.setHeight(50);





//		stage.addActor(button);

//		Gdx.input.setInputProcessor(stage);

//		InputMultiplexer im = new InputMultiplexer();
//
//		GestureDetector gestureDetector = new GestureDetector(new GestureHandler(this));
//		InputHandler inputHandler = new InputHandler(this, );
//		im.addProcessor(stage);
//		im.addProcessor(gestureDetector);
//		im.addProcessor(inputHandler);
//
//		Gdx.input.setInputProcessor(im);

//		setScreen(new SplashScreen(this));

//		try {
//			logger.setLevel(Logger.DEBUG);
//			Gdx.app.setLogLevel(Application.LOG_DEBUG);

			Texture.setAssetManager(assets);
//
//			spriteBatch = new SpriteBatch();
//			modelBatch = new ModelBatch();

//			setScreen(new TimeTableScreen(this, skin));
//		} catch (Exception e) {
////			logger.error(e.getMessage(), e);
//			System.out.println(e.getMessage());
//			Gdx.app.exit();
//		}


//		setScreen(new MainMenuScreen(this));
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		stage.getViewport().update(width, height);
		gameStage.getViewport().update(width, height);
	}

	private boolean screenSet = false;

	@Override
	public void render () {
		super.render();
		float delta = Gdx.graphics.getDeltaTime();

		if (assets.update()){
            if (!screenSet) {
                setScreen(new TimeTableScreen(this, skin));
                screenSet = true;
            }
        }

		stage.act(delta);
		stage.draw();

		gameStage.act(delta);
		gameStage.draw();

//		startTime = System.nanoTime();
//
//		Gdx.gl.glClearColor(1, 0, 0, 1);
//		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		batch.begin();
//
//		for (Texture tex : textures) {
//			for (int i = 0; i < 200; i++) {
//				batch.draw(tex, random.nextInt(1800), random.nextInt(800),tex.getWidth() / 2, tex.getHeight() / 2, tex.getWidth(), tex.getHeight(),
//						1,1,rotation + random.nextFloat() * 180, 0,0,tex.getWidth(),tex.getHeight(),false, false);
//			}
//		}
//
////		for (int i = 0; i < 500; i++) {
////			batch.draw(img, random.nextInt(1800), random.nextInt(800),img.getWidth() / 2, img.getHeight() / 2, img.getWidth(), img.getHeight(),
////					1,1,rotation + random.nextFloat() * 180, 0,0,img.getWidth(),img.getHeight(),false, false);
////		}
////
////		for (int i = 0; i < 500; i++) {
////			batch.draw(img2, random.nextInt(1800), random.nextInt(800), img.getWidth() / 2, img.getHeight() / 2, img.getWidth(), img.getHeight(),
////					1, 1, rotation + random.nextFloat() * 180, 0, 0, img.getWidth(), img.getHeight(), false, false);
////		}
//		batch.end();
//		rotation += 1f;
//
//
//		totalTime += System.nanoTime() - startTime;
//		frameCount++;
//		if (frameCount == 30 && totalTime > 0) {
//			double averageFPS = 1000 / ((totalTime / frameCount) / 1000000);
//			Gdx.app.log("MyTag", "FPS: " + Math.round(averageFPS));
////			cV.message = "FPS: " + Math.round(averageFPS);
//			frameCount = 0;
//			totalTime = 0;
////                Log.d(Game.TAG, "FPS: " + averageFPS);
//		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
//		for (Texture tex : textures) {
//		tex.dispose();
//		}
		font.dispose();
		assets.dispose();
	}

	public Stage getGameStage(){
		return gameStage;
	}
}
