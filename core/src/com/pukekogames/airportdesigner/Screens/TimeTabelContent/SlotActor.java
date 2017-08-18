package com.pukekogames.airportdesigner.Screens.TimeTabelContent;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.pukekogames.airportdesigner.Main;
import com.pukekogames.airportdesigner.Objects.Airlines.Airline;
import com.pukekogames.airportdesigner.Objects.Airlines.AirlineList;
import com.pukekogames.airportdesigner.TextureLoader;

/**
 * Created by Marko Rapka on 31.07.2017.
 */
public class SlotActor extends ImageButton implements SlotListener {

    private final Main main;
    private TimeSlot slot;

    private Skin skin;

    public SlotActor(Main main, Stage stage, Skin skin, TimeSlot slot) {
        super(createStyle(main, skin, slot));
        this.main = main;
        this.slot = slot;
        this.skin = skin;

        slot.addListener(this);

        SlotTooltip tooltip = new SlotTooltip(slot, skin);
        tooltip.setTouchable(Touchable.disabled); // allows for mouse to hit tooltips in the top-right corner of the screen without flashing
        stage.addActor(tooltip);
//        slot.addListener(tooltip);
        addListener(new TooltipListener(tooltip, true));
    }

    private static ImageButtonStyle createStyle(Main main, Skin skin, TimeSlot slot) {
        TextureAtlas icons = main.assets.get("airlines/airlines.atlas", TextureAtlas.class);
        TextureRegion image;
        String content = "";
        if (slot.getItem() != null) {
            Airline airline = slot.getItem().getAirline();
            image = icons.findRegion(AirlineList.getAirlineFileName(airline.getId()));
            content = slot.getItem().getCallSign();
        } else {
            image = icons.findRegion("airline");
        }
        Texture tex = TextureLoader.createTextureWithText(content, image, main.font,128,128,5,30);
        Drawable imageDrawable = new SpriteDrawable(new Sprite(tex));
        ImageButtonStyle style = new ImageButtonStyle(skin.get(ButtonStyle.class));
        style.imageUp = imageDrawable;
        style.imageDown = imageDrawable;
//        style.imageUp = new TextureRegionDrawable(image);
//        style.imageDown = new TextureRegionDrawable(image);

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
