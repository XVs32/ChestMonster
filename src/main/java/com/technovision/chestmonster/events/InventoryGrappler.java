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
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

import static com.google.common.primitives.Ints.max;
import static com.google.common.primitives.Ints.min;
import static com.technovision.chestmonster.events.KeyboardEventHandler.sorting;
import static java.lang.Float.valueOf;

public class InventoryGrappler {

    private static ReentrantLock mutex = new ReentrantLock();
    private static ReentrantLock poMutex = new ReentrantLock();
    private static BlockPos lastInteractPosition;
    private static boolean chestFlag = false;

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event){

        if(sorting == false){
            sorting = true;

            mutex.lock();

            //printChest(event);

            switch (event.player.openContainer.getInventory().size()){
                case 46:
                case 47:
                    System.out.println("Inventory");

                    for(int i=9;i<36;i++){

                        if( !event.player.inventory.mainInventory.get(i).isEmpty() && event.player.inventory.mainInventory.get(i).isStackable() ){
                            for(int j=36-1;j>i;j--){
                                if( isSameItemInPlayerTickEvent(event,i,j) ){
                                    inventoryItemTransfer(event, i, j, 64);
                                }
                            }
                        }
                    }

                    event.player.inventory.mainInventory.subList(9,36).sort(new ItemStackComparator());
                    System.out.println("Inventory finish");
                    break;
                case 63:
                    System.out.println("small chest");
                    chestFlag = true;

                    break;
                case 90:
                    System.out.println("big chest");
                    chestFlag = true;

                    break;
                default:
                    System.out.println("mod? chest");
                    //event.player.openContainer.getInventory().subList(0, event.player.openContainer.getInventory().size() - 36 ).sort(new StringComparator());

            }


            mutex.unlock();


            //event.player.openContainer.getInventory()
            //printInventory(event);


        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event){

        if(chestFlag == true){
            chestFlag = false;
            BlockPos po = lastInteractPosition;
            TileEntity chestContainer = event.world.getTileEntity(po);
            System.out.println("getTileEntity");
            if(chestContainer != null){
                System.out.println("not null");

                LazyOptional<IItemHandler> itemHandler = chestContainer.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);

                itemHandler.ifPresent( h -> {

                    int slotsCount = h.getSlots();

                    List<ItemStack> itemList = new ArrayList<>();

                    for(int i=0;i<slotsCount;i++){
                        itemList.add(h.getStackInSlot(i).copy());
                    }

                    for(int i=0;i<slotsCount;i++){
                        if( !itemList.get(i).isEmpty() && itemList.get(i).isStackable() ){
                            for(int j = slotsCount - 1;j>i;j--){
                                if( isSameItemInWorldTickEvent(h,i,j) ){
                                    chestItemTransfer(itemList, i, j, 64);
                                }
                            }
                        }
                    }

                    itemList.sort(new ItemStackComparator());

                    for(int i=0;i<slotsCount;i++){
                        h.extractItem(i,64,false);
                        h.insertItem(i, itemList.get(i).copy() , false);
                    }

                });

            }
        }
    }

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickBlock event){
        poMutex.lock();
        lastInteractPosition = event.getPos();
        poMutex.unlock();
    }


    private static boolean isSameItemInPlayerTickEvent(TickEvent.PlayerTickEvent event, int o1, int o2){
        return isSameItem(event.player.inventory.mainInventory.get(o1), event.player.inventory.mainInventory.get(o2));
    }

    private static boolean isSameItemInWorldTickEvent(IItemHandler h, int o1, int o2){
        return isSameItem(h.getStackInSlot(o1), h.getStackInSlot(o2));
    }

    private static boolean isSameItem(ItemStack o1, ItemStack o2){
        return o1.getTranslationKey().equals(o2.getTranslationKey());
    }



    private static void inventoryItemTransfer(TickEvent.PlayerTickEvent event, int o1, int o2, int amount){
        itemStacker(event.player.inventory.mainInventory.get(o1), event.player.inventory.mainInventory.get(o2),amount);
    }

    private static void chestItemTransfer(List<ItemStack> itemList, int o1, int o2, int amount){
        itemStacker(itemList.get(o1), itemList.get(o2),amount);
    }

    private static void itemStacker(ItemStack destination, ItemStack source, int amount){

        amount = Math.min(amount , destination.getMaxStackSize() - destination.getCount());
        amount = Math.min(amount , source.getCount());

        destination.setCount(destination.getCount() + amount);
        source.setCount(source.getCount() - amount);

    }



    private static void printChest(TickEvent.PlayerTickEvent event){

        System.out.println("size:" + event.player.openContainer.inventorySlots.size());
        for(int i=0;i<event.player.openContainer.inventorySlots.size();i++){
            System.out.println(i + " " + event.player.openContainer.inventorySlots.get(i).getStack().getDisplayName() + " " + event.player.openContainer.inventorySlots.get(i).getStack().isStackable() + " " + event.player.openContainer.inventorySlots.get(i).getStack().getCount() + " " + event.player.openContainer.inventorySlots.get(i).getStack().getMaxStackSize() + " "  + event.player.openContainer.inventorySlots.get(i).getStack().isEmpty() );
        }
    }

    private static void printInventory(TickEvent.PlayerTickEvent event){
        for(int i=0;i<36;i++){
            System.out.println(event.player.inventory.mainInventory.get(i).getDisplayName() + " " + event.player.inventory.mainInventory.get(i).isStackable() + " " + event.player.inventory.mainInventory.get(i).getCount() + " " + event.player.inventory.mainInventory.get(i).getMaxStackSize() + " "  + event.player.inventory.mainInventory.get(i).isEmpty() );
        }
    }


    static class ItemStackComparator implements Comparator {

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



