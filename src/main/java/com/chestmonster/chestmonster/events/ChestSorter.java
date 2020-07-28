package com.chestmonster.chestmonster.events;

import com.chestmonster.chestmonster.item.ItemHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ChestSorter {

    public static boolean chestFlag = false;
    public static Queue<BlockPos> lastInteractPosition = new LinkedList<>();

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event){

        if(!lastInteractPosition.isEmpty()){
            BlockPos po = lastInteractPosition.poll();
            TileEntity chestContainer = event.world.getTileEntity(po);

            if(chestContainer != null){

                LazyOptional<IItemHandler> itemHandler = chestContainer.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
                itemHandler.ifPresent( h -> {

                    int slotsCount = h.getSlots();

                    List<ItemStack> itemList = new ArrayList<>();

                    for(int i=0;i<slotsCount;i++){
                        itemList.add(h.getStackInSlot(i).copy());
                    }

                    ItemHandler.stack(itemList);
                    ItemHandler.sort(itemList);

                    for(int i=0;i<slotsCount;i++){
                        h.extractItem(i,64,false);
                        h.insertItem(i, itemList.get(i).copy() , false);
                    }

                });

            }
        }
    }

}
