package com.chestmonster.chestmonster.events;

import com.chestmonster.chestmonster.item.ItemHandler;
import com.chestmonster.chestmonster.channel.ChannelSetup;
import com.chestmonster.chestmonster.channel.SortKeyPress;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.concurrent.locks.ReentrantLock;

import static com.chestmonster.chestmonster.events.CustomKeybind.sorting;

public class InventoryGrappler {

    private static ReentrantLock mutex = new ReentrantLock();



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

                    ItemHandler.stack(event.player.inventory.mainInventory.subList(9,36));
                    ItemHandler.sort(event.player.inventory.mainInventory.subList(9,36));

                    System.out.println("Inventory finish");
                    break;
                case 63:
                    System.out.println("small chest");
                    ChannelSetup.SortKey.sendToServer(new SortKeyPress());

                    break;
                case 90:
                    System.out.println("big chest");
                    ChannelSetup.SortKey.sendToServer(new SortKeyPress());

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








}


