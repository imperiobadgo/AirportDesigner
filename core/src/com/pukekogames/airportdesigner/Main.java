package com.pukekogames.airportdesigner;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.pukekogames.airportdesigner.Screens.GameScreen;

import java.util.ArrayList;
import java.util.Random;

public class Main extends Game {
	SpriteBatch batch;
	ArrayList<Texture> textures;
	private float rotation = 0f;
	private long startTime;
	private long totalTime;
	private int frameCount;
	GestureDetector gestureDetector;
	private Random random;

	public BitmapFont font;

	@Override
	public void create () {
		batch = new SpriteBatch();
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

		font = new BitmapFont();

		gestureDetector = new GestureDetector(new GestureHandler(this));
		Gdx.input.setInputProcessor(gestureDetector);

//		setScreen(new SplashScreen(this));
		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
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
	}
}
