package com.chestmonster.chestmonster.events;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class CustomKeybind {

    public static KeyBinding keySort;

    public static boolean sorting = true;

    @OnlyIn(Dist.CLIENT)
    public static void init() throws Exception {
        keySort = new KeyBinding("Sort", 82 , "Chest Monster");
        ClientRegistry.registerKeyBinding(keySort);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onClientTick(InputEvent.KeyInputEvent event){


        if(event.getKey() != keySort.getKey().getKeyCode()){
            return;
        }

        if(event.getAction() == 1 && sorting){
            System.out.println("Sort is down");
            sorting = false;
        }

    }
}
