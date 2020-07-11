package com.technovision.chestmonster.events;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import java.lang.reflect.Field;
import java.util.Map;


public class KeyboardEventHandler {

    private static Field KEYBIND_ARRAY = null;
    private static Map<String, KeyBinding> binds;

    public static KeyBinding keyBindings;

    private static boolean keyflag = false;

    // declare an array of key bindings
    public static void init() throws Exception {
        // instantiate the key bindings
        keyBindings = new KeyBinding("R", 82 , "key.magicbeans.category");
        ClientRegistry.registerKeyBinding(keyBindings);

        KEYBIND_ARRAY = KeyBinding.class.getDeclaredField("KEYBIND_ARRAY");
        KEYBIND_ARRAY.setAccessible(true);
        binds = (Map<String, KeyBinding>) KEYBIND_ARRAY.get(null);
    }

    @SubscribeEvent (priority = EventPriority.NORMAL)
    public static void onClientTick(TickEvent.ClientTickEvent event){
        if(event.phase == TickEvent.Phase.END ){ //not sure what this does

            if(binds.get("R").isKeyDown() && keyflag == false){
                System.out.println("R is down");
                keyflag = true;
            }
            else if(binds.get("R").isKeyDown() == false && keyflag == true){
                System.out.println("R is up");
                keyflag = false;
            }

        }
    }


}
