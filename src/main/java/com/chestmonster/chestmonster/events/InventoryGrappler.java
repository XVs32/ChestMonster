package com.chestmonster.chestmonster.events;

import com.chestmonster.chestmonster.channel.SortInventoryMsg;
import com.chestmonster.chestmonster.channel.ChannelSetup;
import com.chestmonster.chestmonster.channel.SortChestMsg;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.locks.ReentrantLock;

import static com.chestmonster.chestmonster.events.CustomKeybind.sorting;
import static com.chestmonster.chestmonster.events.RightClickChest.LastChestPos;

public class InventoryGrappler {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){

        if(!sorting){

            if(isInventory(Minecraft.getInstance().currentScreen)){
                ChannelSetup.SortInventory.sendToServer(new SortInventoryMsg());
            }
            else{
                ChannelSetup.SortChest.sendToServer(new SortChestMsg(LastChestPos));
            }

            sorting = true;

        }
    }



    private static boolean isInventory(Screen curScreen){

        String[] splitList = curScreen.toString().split("@|\\.");

        if(splitList[splitList.length - 2].compareTo("InventoryScreen") == 0 ||
                splitList[splitList.length - 2].compareTo("CreativeScreen") == 0){
            return true;
        }
        return false;

    }




}


