package com.chestmonster.chestmonster.channel;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static com.chestmonster.chestmonster.events.ChestSorter.chestFlag;

public class SortChestMsg {

    public SortChestMsg() {}

    public static void encode(SortChestMsg msg, PacketBuffer buf) {}

    public static SortChestMsg decode(PacketBuffer buf) {
        return new SortChestMsg();
    }

    public static void handle(SortChestMsg msg, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {

            chestFlag = true;

        });
        ctx.get().setPacketHandled(true);
    }

}
