package com.technovision.chestmonster.events;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.InputEvent;
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

    public static boolean keyPress = false;
    public static boolean sorting = true;

    // declare an array of key bindings
    public static void init() throws Exception {
        // instantiate the key bindings
        /*keyBindings = new KeyBinding("R", 82 , "key.magicbeans.category");
        ClientRegistry.registerKeyBinding(keyBindings);

        KEYBIND_ARRAY = KeyBinding.class.getDeclaredField("KEYBIND_ARRAY");
        KEYBIND_ARRAY.setAccessible(true);
        binds = (Map<String, KeyBinding>) KEYBIND_ARRAY.get(null);*/
    }

    @SubscribeEvent (priority = EventPriority.NORMAL)
    public static void onClientTick(InputEvent.KeyInputEvent event){

        /*System.out.println(event.getKey());
        System.out.println(event.getAction());
        System.out.println(event.getScanCode());*/

        if(event.getKey() != 82){
            return;
        }

        if(event.getAction() == 1 && sorting){
            System.out.println("R is down");
            sorting = false;
        }
        /*else if(event.getAction() == 0 ){
            System.out.println("R is up");
            keystate = 0;
        }*/


    }

    /*@SubscribeEvent (priority = EventPriority.NORMAL)
    public static void openGUI(GuiScreenEvent.KeyboardKeyEvent event){

        System.out.println(event.getKeyCode());
        System.out.println(keyBindings.isKeyDown());
        System.out.println(keystate);
        if(keyBindings.isKeyDown() && keystate == 0){
            System.out.println("GUI R is down");

            keystate = 5;
        }
        else if(keyBindings.isKeyDown() == false && keystate == 2){
            System.out.println("GUI R is up");
            keystate = 0;
        }
    }*/


}
