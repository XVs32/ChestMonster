package com.chestmonster.chestmonster.channel;

import com.chestmonster.chestmonster.ChestMonster;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class ChannelSetup {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel ChestPos = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ChestMonster.MOD_ID, "chestpos"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static final SimpleChannel SortKey = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ChestMonster.MOD_ID, "sortkeypress"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int id = 0;

    public static void register(){

        ChestPos.registerMessage(
                id++,
                LastChestPos.class,
                LastChestPos::encode,
                LastChestPos::decode,
                LastChestPos::handle);

        SortKey.registerMessage(
                id++,
                SortKeyPress.class,
                SortKeyPress::encode,
                SortKeyPress::decode,
                SortKeyPress::handle);

    }

}
