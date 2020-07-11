package com.technovision.chestmonster.events;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

import static com.technovision.chestmonster.events.KeyboardEventHandler.keystate;

public class InventoryGrappler {


    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){

        if(keystate == 1){
            List<ItemStack> item;
            item = event.player.inventory.mainInventory;

            System.out.println(item.size());

            for(int i=0;i<item.size();i++){
                System.out.println(item.get(i).getDisplayName());
            }

            keystate = 2;
        }

    }

}
