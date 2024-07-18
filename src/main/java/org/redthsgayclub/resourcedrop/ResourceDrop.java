package org.redthsgayclub.resourcedrop;

import cc.polyfrost.oneconfig.events.EventManager;
import cc.polyfrost.oneconfig.events.event.Stage;
import cc.polyfrost.oneconfig.events.event.TickEvent;
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C16PacketClientStatus;
import org.redthsgayclub.resourcedrop.config.PolyConfig;
import cc.polyfrost.oneconfig.events.event.InitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.util.ArrayList;
import java.util.List;

import static org.redthsgayclub.resourcedrop.config.PolyConfig.*;

/**
 * The entrypoint of the Example Mod that initializes it.
 *
 * @see Mod
 * @see InitializationEvent
 */
@Mod(modid = ResourceDrop.MODID, name = ResourceDrop.NAME, version = ResourceDrop.VERSION)
public class ResourceDrop {

    // Sets the variables from `gradle.properties`. See the `blossom` config in `build.gradle.kts`.
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    @Mod.Instance(MODID)
    public static ResourceDrop INSTANCE; // Adds the instance of the mod, so we can access other variables.
    public static PolyConfig config;
    public static Minecraft mc = Minecraft.getMinecraft();

    static List<Integer> dropThese = new ArrayList<Integer>();
    private static GuiInventory guiScreenIn;
    private static boolean isDropping = false;
    private static int delayVar = PolyConfig.delay;


    // Register the config and commands.
    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        config = new PolyConfig();
        EventManager.INSTANCE.register(this);

    }

    @Subscribe
    public void onTick(TickEvent event) {

        if (event.stage == Stage.START) return;

        if (delayVar > 0) {
            delayVar--;
            return;
        }
        if (dropThese.isEmpty() && isDropping) {
            mc.displayGuiScreen(null);
            isDropping = false;
            return;
        } else if (dropThese.isEmpty()) return;

        if (!isDropping) {
            guiScreenIn = new GuiInventory(mc.thePlayer);
            mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
            mc.displayGuiScreen(guiScreenIn);
            isDropping = true;
        }

        dropItem(guiScreenIn, dropThese.get(0));
        delayVar = PolyConfig.delay;
        dropThese.remove(0);

    }

    protected void handleMouseClick(GuiInventory windowID, int slotId, int clickedButton, int clickType) {
        this.mc.playerController.windowClick(windowID.inventorySlots.windowId, slotId, clickedButton, clickType, this.mc.thePlayer);
    }

    private void dropItem(GuiInventory guiScreenIn, int slot) {
        this.handleMouseClick(guiScreenIn, slot, 1, 4);
    }

    public static void dropAll() {

        for (int i = 0; i < 9; i++) {
            ItemStack stack = mc.thePlayer.inventory.mainInventory[i];
            if (stack != null) {
                Item item = stack.getItem();
                if (ironCheckbox && item == Items.iron_ingot || goldCheckbox && item == Items.gold_ingot || diamondCheckbox && item == Items.diamond || emeraldCheckbox && item == Items.emerald) {
                    dropThese.add(i+36);
                }
            }

        }
        for (int i = 9; i < 36; i++) {
            ItemStack stack = mc.thePlayer.inventory.mainInventory[i];
            if (stack != null) {
                Item item = stack.getItem();
                if (ironCheckbox && item == Items.iron_ingot || goldCheckbox && item == Items.gold_ingot || diamondCheckbox && item == Items.diamond || emeraldCheckbox && item == Items.emerald) {
                    dropThese.add(i);
                }
            }

        }



    }


}
