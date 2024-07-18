package org.redthsgayclub.resourcedrop.config;

import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.core.OneKeyBind;
import org.redthsgayclub.resourcedrop.ResourceDrop;
import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;


/**
 * The main Config entrypoint that extends the Config type and inits the config options.
 * See <a href="https://docs.polyfrost.cc/oneconfig/config/adding-options">this link</a> for more config Options
 */
public class PolyConfig extends Config {
    @Slider(
            name = "Delay",
            description = "The delay between each drop.",
            min = 0,
            max = 10
    )
    public static int delay = 2;
    @Checkbox(
            name = "Iron",
            description = "Drop iron ingots when the keybind is pressed."
    )
    public static boolean ironCheckbox = true;
    @Checkbox(
            name = "Gold",
            description = "Drop gold ingots when the keybind is pressed."
    )
    public static boolean goldCheckbox = true;
    @Checkbox(
            name = "Diamond",
            description = "Drop diamonds when the keybind is pressed."
    )
    public static boolean diamondCheckbox = true;
    @Checkbox(
            name = "Emerald",
            description = "Drop emeralds when the keybind is pressed."
    )
    public static boolean emeraldCheckbox = true;


    @KeyBind(
            name = "Drop Key"
    )
    public static OneKeyBind dropKey = new OneKeyBind();

    public PolyConfig() {
        super(new Mod(ResourceDrop.NAME, ModType.UTIL_QOL), ResourceDrop.MODID + ".json");
        initialize();
        registerKeyBind(dropKey, () -> {
            ResourceDrop.dropAll();
        });
    }







}

