package com.pukekogames.airportdesigner.Objects.Airlines;

import com.badlogic.gdx.graphics.*;
import com.pukekogames.airportdesigner.TextureLoader;

/**
 * Created by Marko Rapka on 19.03.2017.
 */
public class AirlineList {

    public static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVW";

    public static float baseRed = 255 / 255f;
    public static float logoRed = 240 / 255f;

    //first color= baseColor, second Color = logoColor

    private static Object[][] airlines = {
            {"Air Berlin", "AB", "airberlin", new Color(1f, 1f, 1f, 1f), new Color(1f, 1f, 1f, 1f)},
            {"Lufthansa", "LH", "lufthansa", new Color(0f, 1f, 1f, 1f), new Color(1f, 1f, 1f, 1f)},
            {"Emirates", "EK", "emirates", new Color(1f, 1f, 1f, 1f), new Color(1f, 1f, 1f, 1f)},
            {"United", "UD", "united", new Color(1f, 1f, 1f, 1f), new Color(1f, 1f, 1f, 1f)},
            {"North Air", "NA", "northcentral", new Color(1f, 1f, 1f, 1f), new Color(1f, 1f, 1f, 1f)},
            {"Jet Airlines", "JE", "jetairways", new Color(1f, 1f, 1f, 1f), new Color(1f, 1f, 1f, 1f)},
            {"American Airlines", "AA", "americanairlines", new Color(1f, 1f, 1f, 1f), new Color(1f, 1f, 1f, 1f)},
            {"Eastern Air", "EA", "chinaeastern", new Color(1f, 1f, 1f, 1f), new Color(1f, 1f, 1f, 1f)}
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
        if (id < 0 || id > airlines.length) return new Color();
        return (Color) airlines[id][3];
    }

    public static Color getAirlineLogoColor(int id) {
        if (id < 0 || id > airlines.length) return new Color();
        return (Color) airlines[id][4];
    }

    public static int maxIndex() {
        return airlines.length;
    }

    public static Texture setbaseColor(Texture baseTexture, Airline airline) {

//        int r = ...
//        int g = ...
//        int b = ...
        float[] hsv = new float[3];


        TextureData textureData = baseTexture.getTextureData();
        if (!textureData.isPrepared()) {
            textureData.prepare();
        }
        Pixmap pixmap = textureData.consumePixmap();
        Color baseColor = getAirlineBaseColor(airline.getId());
        Color fontColor = getAirlineLogoColor(airline.getId());
        for (int x = 0; x < pixmap.getWidth(); x++) {
            for (int y = 0; y < pixmap.getHeight(); y++) {
                Color baseCompareColor = new Color();
                Color.rgba8888ToColor(baseCompareColor, pixmap.getPixel(x, y));

                if (ColorEqualsBaseColor(baseRed, baseCompareColor, 0.001f)) {

                    //get hue, saturation and brightness of the original color
                    TextureLoader.RGBtoHSB( (int)(baseCompareColor.r * 255),(int) (baseCompareColor.g * 255),(int) (baseCompareColor.b * 255), hsv);
                    float saturation = hsv[1];
                    //change the baseColor to desired saturation
                    TextureLoader.RGBtoHSB( (int)(baseColor.r * 255),(int) (baseColor.g * 255),(int) (baseColor.b * 255), hsv);
                    //convert back to rgb color space
                    int[] convertedColor = TextureLoader.HSBtoRGB(hsv[0], saturation, hsv[2]);

                    //create new LibGDX color
                    Color hsvBaseColor = new Color(convertedColor[0] / 255f, convertedColor[1] / 255f,convertedColor[2] / 255f, baseCompareColor.a);

                    pixmap.setColor(hsvBaseColor);
                    pixmap.fillRectangle(x, y, 1, 1);

                }
                Color fontCompareColor = new Color();
                Color.rgba8888ToColor(fontCompareColor, pixmap.getPixel(x, y));
                if (ColorEqualsBaseColor(logoRed, fontCompareColor, 0.001f)) {

                    pixmap.setColor(fontColor);
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
        if (Math.abs(colorFloat - c2.r) > epsilon) return false;
        return true;
    }

}
