package com.pukekogames.airportdesigner.Screens.TimeTabelContent;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.pukekogames.airportdesigner.Main;

/**
 * Created by Marko Rapka on 31.07.2017.
 */
public class SlotActor extends ImageButton implements SlotListener {

    private final Main main;
    private TimeSlot slot;
    private TimeTableScreen screen;

    private Skin skin;

    public SlotActor(Main main, TimeTableScreen screen, Skin skin, TimeSlot slot) {
        super(createStyle(main, skin, slot));
        this.main = main;
        this.slot = slot;
        this.skin = skin;
        this.screen = screen;

        slot.addListener(this);

        SlotTooltip tooltip = new SlotTooltip(slot, skin);
        tooltip.setTouchable(Touchable.disabled); // allows for mouse to hit tooltips in the top-right corner of the screen without flashing
        screen.stage.addActor(tooltip);
        addListener(new TooltipListener(tooltip, true));
    }

    private static ImageButtonStyle createStyle(Main main, Skin skin, TimeSlot slot) {
        TextureAtlas icons = main.assets.get("airlines/icons.atlas", TextureAtlas.class);
        TextureRegion image;
        if (slot.getItem() != null) {
            image = icons.findRegion("batterybase");
        } else {
            image = icons.findRegion("nothing");
        }

        ImageButtonStyle style = new ImageButtonStyle(skin.get(ButtonStyle.class));
        style.imageUp = new TextureRegionDrawable(image);
        style.imageDown = new TextureRegionDrawable(image);

//        Drawable image = new SpriteDrawable(new Sprite(new Texture("badlogic.jpg")));
//        ImageButtonStyle style = new ImageButtonStyle(skin.get(ButtonStyle.class));
//        style.imageUp = image;
//        style.imageDown = image;

        return style;
    }

    public TimeSlot getSlot() {
        return slot;
    }

    @Override
    public void hasChanged(TimeSlot slot) {
        setStyle(createStyle(main, skin, slot));
    }

}
