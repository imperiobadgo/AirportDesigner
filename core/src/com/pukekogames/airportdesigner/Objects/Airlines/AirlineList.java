package com.pukekogames.airportdesigner.Objects.Airlines;

import com.badlogic.gdx.graphics.*;
import com.pukekogames.airportdesigner.Objects.Vehicles.Airplane;
import com.pukekogames.airportdesigner.TextureLoader;

import java.util.ArrayList;

/**
 * Created by Marko Rapka on 19.03.2017.
 */
public class AirlineList {

    public static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVW";

    public static float baseRed = 255 / 255f;
    public static float logoRed = 240 / 255f;

    //first color= baseColor, second Color = logoColor

    private static Object[][] airlines = {
            {"Air Berlin", "AB", "airberlin", new Color(1f, 0f, 0f, 1f), new Color(1f, 0f, 0f, 1f)},
            {"Lufthansa", "LH", "lufthansa", new Color(1f, 1f, 0.9f, 1f), new Color(1f, 0.8f, 0f, 1f)},
            {"Emirates", "EK", "emirates", new Color(0.9f, 0.9f, 0.9f, 1f), new Color(1f, 0.5f, 0f, 1f)},
            {"United", "UD", "united", new Color(0f, 0f, 1f, 1f), new Color(0f, 0.5f, 1f, 1f)},
            {"North Air", "NA", "northcentral", new Color(0f, 0.5f, 0.5f, 1f), new Color(0f, 1f, 0.5f, 1f)},
            {"Jet Airlines", "JE", "jetairways", new Color(0.5f, 0.8f, 1f, 1f), new Color(1f, 0.5f, 0.5f, 1f)},
            {"American Airlines", "AA", "americanairlines", new Color(1f, 0.2f, 0f, 1f), new Color(1f, 0.8f, 0.8f, 1f)},
            {"Eastern Air", "EA", "chinaeastern", new Color(1f, 0.3f, 0.2f, 1f), new Color(0.8f, 0.8f, 1f, 1f)}
    };

    public static String getAirlineName(int id) {
        if (id < 0 || id > airlines.length) return "Airline";
        return (String) airlines[id][0];
    }

    public static String getAirlineCode(int id) {
        if (id < 0 || id > airlines.length) return "AIR";
        return (String) airlines[id][1];
    }

    public static String getAirlineFileName(int id) {
        if (id < 0 || id > airlines.length) return "airline";
        return (String) airlines[id][2];
    }

    public static Color getAirlineBaseColor(int id) {
        if (id < 0 || id > airlines.length) return Color.WHITE;
        return (Color) airlines[id][3];
    }

    public static Color getAirlineLogoColor(int id) {
        if (id < 0 || id > airlines.length) return Color.WHITE;
        return (Color) airlines[id][4];
    }

    public static int maxIndex() {
        return airlines.length;
    }

    public static Texture setbaseColor(Texture texture, Texture baseTexture, Texture logoTexture, Airline airline) {
        if (texture == null || baseTexture == null || logoTexture == null) return null;
        if (!(texture.getHeight() == baseTexture.getHeight() && texture.getWidth() == baseTexture.getWidth() &&
                texture.getHeight() == logoTexture.getHeight() && texture.getWidth() == logoTexture.getWidth())) return null;
//        int r = ...
//        int g = ...
//        int b = ...
        float[] hsv = new float[3];


        TextureData textureData = texture.getTextureData();
        TextureData baseTextureData = baseTexture.getTextureData();
        TextureData logoTextureData = logoTexture.getTextureData();

        if (!textureData.isPrepared()) {
            textureData.prepare();
        }
        if (!baseTextureData.isPrepared()) {
            baseTextureData.prepare();
        }
        if (!logoTextureData.isPrepared()) {
            logoTextureData.prepare();
        }
        Pixmap pixmap = textureData.consumePixmap();
        Pixmap basePixmap = baseTextureData.consumePixmap();
        Pixmap logoPixmap = logoTextureData.consumePixmap();
        Color baseColor = getAirlineBaseColor(airline.getId());
        Color fontColor = getAirlineLogoColor(airline.getId());
        for (int x = 0; x < pixmap.getWidth(); x++) {
            for (int y = 0; y < pixmap.getHeight(); y++) {
                Color baseCompareColor = new Color();
                Color.rgba8888ToColor(baseCompareColor, basePixmap.getPixel(x, y));

                if (ColorEqualsBaseColor(baseRed, baseCompareColor, 0.001f)) {


                    Color hsvBaseColor = changeColor(baseCompareColor, baseColor, hsv);

                    pixmap.setColor(hsvBaseColor);
                    pixmap.fillRectangle(x, y, 1, 1);

                }
                Color fontCompareColor = new Color();
                Color.rgba8888ToColor(fontCompareColor, logoPixmap.getPixel(x, y));
                if (ColorEqualsBaseColor(logoRed, fontCompareColor, 0.001f)) {

                    Color hsvFontColor = changeColor(fontCompareColor, fontColor, hsv);

                    pixmap.setColor(hsvFontColor);
                    pixmap.fillRectangle(x, y, 1, 1);


                }
//                int colorInt[] = getColorFromHex(color);
            }
        }
        Texture tex = new Texture(pixmap);
        return tex;
    }

    public static boolean ColorEqualsBaseColor(float colorFloat, Color c2, float epsilon) {
        if (c2 == null) return false;
        if (c2.a < 0.1) return false;
        return true;
    }

    private static Color changeColor(Color baseCompareColor, Color baseColor, float[] hsv) {
        //get hue, saturation and brightness of the original color
        TextureLoader.RGBtoHSB((int) (baseCompareColor.r * 255), (int) (baseCompareColor.g * 255), (int) (baseCompareColor.b * 255), hsv);
        float saturation = hsv[1];
        //change the baseColor to desired saturation
        TextureLoader.RGBtoHSB((int) (baseColor.r * 255), (int) (baseColor.g * 255), (int) (baseColor.b * 255), hsv);
        //convert back to rgb color space
        int[] convertedColor = TextureLoader.HSBtoRGB(hsv[0], saturation, hsv[2]);

        //create new LibGDX color
        return new Color(convertedColor[0] / 255f, convertedColor[1] / 255f, convertedColor[2] / 255f, baseCompareColor.a);
    }
}
