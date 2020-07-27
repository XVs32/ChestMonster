package com.chestmonster.chestmonster.events;

import com.chestmonster.chestmonster.channel.ChannelSetup;
import com.chestmonster.chestmonster.channel.LastChestPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.locks.ReentrantLock;

public class RightClickChest {

    private static ReentrantLock poMutex = new ReentrantLock();

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event){
        poMutex.lock();

        ChannelSetup.ChestPos.sendToServer(new LastChestPos(event.getPos()));

        poMutex.unlock();
    }

}