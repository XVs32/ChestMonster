package com.chestmonster.chestmonster.channel;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static com.chestmonster.chestmonster.events.ChestSorter.chestFlag;

public class SortKeyPress {

    public SortKeyPress() {}

    public static void encode(SortKeyPress msg, PacketBuffer buf) {}

    public static SortKeyPress decode(PacketBuffer buf) {
        return new SortKeyPress();
    }

    public static void handle(SortKeyPress msg, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {

            chestFlag = true;
            System.out.println("sort key press");

        });
        ctx.get().setPacketHandled(true);
    }

}
