package com.technovision.chestmonster.events;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.google.common.primitives.Ints.max;
import static com.google.common.primitives.Ints.min;
import static com.technovision.chestmonster.events.KeyboardEventHandler.keystate;

public class InventoryGrappler {


    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){

        if(keystate == 1){

            for(int i=9;i<36;i++){

                int j=9;
                while( !event.player.inventory.mainInventory.get(i).isEmpty() && event.player.inventory.mainInventory.get(i).isStackable() ){

                    for(;j<i;j++){
                        if( isSameItem(event,i,j) ){
                            inventoryItemTransfer(event, i, j, 64);
                            break;
                        }
                    }

                    if(j == i){
                        break;
                    }

                }
            }

            event.player.inventory.mainInventory.subList(9,36).sort(new StringComparator());
            //printInventory(event);

            keystate = 2;
        }

    }

    private static boolean isSameItem(TickEvent.PlayerTickEvent event,int o1, int o2){
        return event.player.inventory.mainInventory.get(o1).getTranslationKey().
                equals(event.player.inventory.mainInventory.get(o2).getTranslationKey());
    }

    private static void inventoryItemTransfer(TickEvent.PlayerTickEvent event, int o1, int o2, int amount){
        amount = Math.min(amount , event.player.inventory.mainInventory.get(o1).getMaxStackSize()
                - event.player.inventory.mainInventory.get(o1).getCount());

        amount = Math.min(amount , event.player.inventory.mainInventory.get(o2).getCount());

        event.player.inventory.mainInventory.get(o1)
                .setCount(event.player.inventory.mainInventory.get(o1).getCount() + amount);

        event.player.inventory.mainInventory.get(o2)
                .setCount(event.player.inventory.mainInventory.get(o2).getCount() - amount);
    }

    private static void printInventory(TickEvent.PlayerTickEvent event){
       for(int i=0;i<36;i++){
            System.out.println(event.player.inventory.mainInventory.get(i).getDisplayName() + " " + event.player.inventory.mainInventory.get(i).isStackable() + " " + event.player.inventory.mainInventory.get(i).getCount() + " " + event.player.inventory.mainInventory.get(i).getMaxStackSize() + " "  + event.player.inventory.mainInventory.get(i).isEmpty() );
        }
    }


    static class StringComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            return -((ItemStack)o1).getTranslationKey().compareTo(((ItemStack)o2).getTranslationKey());
        }
    }

}



