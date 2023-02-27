package com.minikloon.togglemute;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundCategory;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@Mod(modid = ToggleMuteMod.MODID, version = ToggleMuteMod.VERSION)
public class ToggleMuteMod {
    public static final String MODID = "kloonmute";
    public static final String VERSION = "1.0";

    public static final KeyBinding MUTE = new KeyBinding("Toggle Mute", Keyboard.KEY_M, "KloonMute");

    private float recordedSoundLevel;

    @EventHandler
    public void init(FMLInitializationEvent e) {
        ClientRegistry.registerKeyBinding(MUTE);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onKey(InputEvent.KeyInputEvent e) {
        if (!MUTE.isKeyDown()) {
            return;
        }

        GameSettings settings = Minecraft.getMinecraft().gameSettings;

        float soundLevel = settings.getSoundLevel(SoundCategory.MASTER);
        if (soundLevel == 0) {
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Un-muted!").setChatStyle(new ChatStyle().setColor(EnumChatFormatting.DARK_GRAY)));
            settings.setSoundLevel(SoundCategory.MASTER, recordedSoundLevel);
        } else {
            this.recordedSoundLevel = soundLevel;
            settings.setSoundLevel(SoundCategory.MASTER, 0);

            String message = recordedSoundLevel == 0
                    ? "Game already muted!"
                    : "Muted the game!";
            Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(message).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE)));
        }
    }
}
