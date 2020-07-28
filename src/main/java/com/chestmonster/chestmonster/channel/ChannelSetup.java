package com.chestmonster.chestmonster.channel;

import com.chestmonster.chestmonster.ChestMonster;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ChannelSetup {
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel SortChest = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ChestMonster.MOD_ID, "sort_chest"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static final SimpleChannel SortInventory = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ChestMonster.MOD_ID, "sort_inventory"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int id = 0;

    public static void register(){

        SortChest.registerMessage(
                id++,
                SortChestMsg.class,
                SortChestMsg::encode,
                SortChestMsg::decode,
                SortChestMsg::handle);

        SortInventory.registerMessage(
                id++,
                SortInventoryMsg.class,
                SortInventoryMsg::encode,
                SortInventoryMsg::decode,
                SortInventoryMsg::handle);

    }

}
