package com.technovision.chestmonster.events;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.technovision.chestmonster.events.KeyboardEventHandler.keystate;

public class InventoryGrappler {


    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){

        if(keystate == 1){
            List<ItemStack> item;
            item = event.player.inventory.mainInventory;


            System.out.println(item.size());

            /*for(int i=0;i<item.size();i++){
                //System.out.println(item.get(i).getDisplayName());
                System.out.println("LOLL" + item.get(i).getItem().getTranslationKey());

            }*/

            //event.player.inventory.mainInventory.set(2,item.get(0));
            event.player.inventory.mainInventory.sort(new StringComparator());

            System.out.println("LOLL" + item.get(0).getTranslationKey());
            System.out.println("LOLL" + item.get(0).getCount());
            System.out.println("LOLL" + item.get(0).getMaxStackSize());

            keystate = 2;
        }

    }

    static class StringComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            return ((ItemStack)o1).getTranslationKey().compareTo(((ItemStack)o2).getTranslationKey());
        }
    }

}



