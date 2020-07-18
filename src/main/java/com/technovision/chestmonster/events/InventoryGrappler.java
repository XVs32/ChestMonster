package com.technovision.chestmonster.events;

import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.common.primitives.Ints.max;
import static com.google.common.primitives.Ints.min;
import static com.technovision.chestmonster.events.KeyboardEventHandler.sorting;
import static java.lang.Float.valueOf;

public class InventoryGrappler {

    private static ReentrantLock mutex = new ReentrantLock();
    private static IWorld world;

    //@SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){


        if(sorting == false){
            sorting = true;
            BlockPos po = new BlockPos(0,80,0);
            System.out.println("before");
            TileEntity chestContainer = world.getTileEntity(po);
            System.out.println("getTileEntity");
            if(chestContainer != null){
                System.out.println("not null");

                LazyOptional<IItemHandler> itemHandler = chestContainer.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);

                event.player.
                ItemStack tem = event.player.inventory.mainInventory.get(0);
                System.out.println(tem.getDisplayName());

                itemHandler.ifPresent(h -> System.out.println(h.isItemValid(5,tem)));

                //itemHandler.ifPresent(h -> h.insertItem(5, new ItemStack(Items.IRON_INGOT) , false));
                itemHandler.ifPresent(h -> System.out.println(h.getStackInSlot(5).getDisplayName()));



                //itemHandler.insertItem(0,event.player.inventory.mainInventory.get(0),false);
                //System.out.println(itemHandler.getStackInSlot(0).getDisplayName());
            }



/*
            mutex.lock();

            printChest(event);

            switch (event.player.openContainer.getInventory().size()){
                case 46:
                    System.out.println("Inventory");
                    //event.player.inventory.mainInventory.get(0).setCount(5);
                    event.player.inventory.mainInventory.subList(9,36).sort(new inventoryComparator());
                    break;
                case 63:
                    System.out.println("small chest");
                    //event.player.openContainer.inventorySlots.get(0).getStack().setCount(5);

                    //event.player.openContainer.getInventory().get(0).setCount(5);
                    //event.player.inventory.mainInventory.subList(9,36).sort(new StringComparator());
                    event.player.openContainer.inventorySlots.subList(0,27).sort(new containerComparator());

                    break;
                case 90:
                    System.out.println("big chest");
                    //event.player.inventory.mainInventory.get(0).setCount(5);
                    //event.player.inventory.mainInventory.subList(9,36).sort(new StringComparator());
                    event.player.openContainer.getInventory().subList(0,54).sort(new containerComparator());
                    break;
                default:
                    System.out.println("mod? chest");
                    //event.player.openContainer.getInventory().subList(0, event.player.openContainer.getInventory().size() - 36 ).sort(new StringComparator());

            }


            mutex.unlock();



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

            //event.player.openContainer.getInventory()
            //printInventory(event);

*/
        }
    }

    @SubscribeEvent
    public static void onWorldLoad(TickEvent.WorldTickEvent event){
        if(sorting == false){
            sorting = true;

            System.out.println("onWorldLoad");
            world = event.world;

            BlockPos po = new BlockPos(0,80,0);
            System.out.println("before");
            TileEntity chestContainer = world.getTileEntity(po);

            System.out.println("getTileEntity" + chestContainer);

            if(chestContainer != null){
                System.out.println("not null");

                LazyOptional<IItemHandler> itemHandler = chestContainer.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);

                itemHandler.ifPresent(h -> System.out.println(h.isItemValid(5,new ItemStack(Items.IRON_INGOT))));

                itemHandler.ifPresent(h -> System.out.println(h.getStackInSlot(5).getDisplayName()));

                itemHandler.ifPresent(h -> h.insertItem(5, new ItemStack(Items.IRON_INGOT) , false));

            }

        }



    }


    private static boolean isSameItem(TickEvent.PlayerTickEvent event, int o1, int o2){
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

    private static void printChest(TickEvent.PlayerTickEvent event){

        System.out.println("size:" + event.player.openContainer.inventorySlots.size());
        for(int i=0;i<event.player.openContainer.inventorySlots.size();i++){
            System.out.println(String.valueOf(i) + " " + event.player.openContainer.inventorySlots.get(i).getStack().getDisplayName() + " " + event.player.openContainer.inventorySlots.get(i).getStack().isStackable() + " " + event.player.openContainer.inventorySlots.get(i).getStack().getCount() + " " + event.player.openContainer.inventorySlots.get(i).getStack().getMaxStackSize() + " "  + event.player.openContainer.inventorySlots.get(i).getStack().isEmpty() );
        }
    }

    private static void printInventory(TickEvent.PlayerTickEvent event){
        for(int i=0;i<36;i++){
            System.out.println(event.player.inventory.mainInventory.get(i).getDisplayName() + " " + event.player.inventory.mainInventory.get(i).isStackable() + " " + event.player.inventory.mainInventory.get(i).getCount() + " " + event.player.inventory.mainInventory.get(i).getMaxStackSize() + " "  + event.player.inventory.mainInventory.get(i).isEmpty() );
        }
    }


    static class containerComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            String[] name1 = ((String) ((Slot) o1).getStack().getTranslationKey()).split("_|\\.");
            String[] name2 = ((String) ((Slot) o2).getStack().getTranslationKey()).split("_|\\.");

            int i = name1.length - 1;
            int j = name2.length - 1;

            while (true) {
                int result = name1[i].compareTo(name2[j]);
                if (result == 0) {
                    if (i == 0 && j == 0) {
                        return 0;
                    } else if (i == 0) {
                        return -1;
                    } else if (j == 0) {
                        return 1;
                    } else {
                        i--;
                        j--;
                    }
                } else {
                    return -result;
                }
            }
        }
    }

    static class inventoryComparator implements Comparator {

        @Override
        public int compare(Object o1, Object o2) {
            String[] name1 = ((String) ((ItemStack) o1).getTranslationKey()).split("_|\\.");
            String[] name2 = ((String) ((ItemStack) o2).getTranslationKey()).split("_|\\.");

            int i = name1.length - 1;
            int j = name2.length - 1;

            while (true) {
                int result = name1[i].compareTo(name2[j]);
                if (result == 0) {
                    if (i == 0 && j == 0) {
                        return 0;
                    } else if (i == 0) {
                        return -1;
                    } else if (j == 0) {
                        return 1;
                    } else {
                        i--;
                        j--;
                    }
                } else {
                    return -result;
                }
            }
        }
    }

}



