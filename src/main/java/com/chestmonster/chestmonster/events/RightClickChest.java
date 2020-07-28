package com.chestmonster.chestmonster.events;


import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.locks.ReentrantLock;

public class RightClickChest {

    public static BlockPos LastChestPos;

    private static ReentrantLock poMutex = new ReentrantLock();

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event){
        LastChestPos = event.getPos();
    }

}