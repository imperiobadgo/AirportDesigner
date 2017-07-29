package com.pukekogames.airportdesigner.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.pukekogames.airportdesigner.UnitTest.DragDropTest;

/**
 * Created by Marko Rapka on 29.07.2017.
 */
public class TestDesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1920;
        config.height = 1080;
        new LwjglApplication(new DragDropTest(), config);
    }
}
