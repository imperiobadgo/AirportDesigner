package com.pukekogames.airportdesigner;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.pukekogames.airportdesigner.Screens.GameScreen;

/**
 * Created by Marko Rapka on 26.05.2016.
 */
public class Settings {
    private static Settings ourInstance = new Settings();

    public static Settings Instance() {
        return ourInstance;
    }

    private Settings(){}

    public static void setSettings(Settings settings){
        ourInstance = settings;
    }

//    public static String getString(int id){
//        if (ourInstance.game == null) return "";
//        return ourInstance.game.getString(id);
//    }

    public float minZoom = 5f;
    public float maxZoom = 1000f;
    public int ButtonWidth = 70;
    public int ButtonHeight = 40;
    int ButtonCircleDiameter = 60;

//    public Game game;

    public boolean isShowingDialog = false;
    public UiManager uiManager;

    //Fonts
    public int normalFontSize = 15;
//    public Font buttonFont = new Font("serif", Font.BOLD, 15);
//    public Font normalFont = new Font("serif", Font.PLAIN, 15);


    //Colors
    public Color attentionColor = new Color(1f,0.588f,0.078f, 1f);
//    public int selectedColor = Color.BLUE;
//    public int possibleSelectionColor = Color.CYAN;
//    public int fontColor = Color.BLACK;
//    public int clearColor = Color.WHITE;
}
