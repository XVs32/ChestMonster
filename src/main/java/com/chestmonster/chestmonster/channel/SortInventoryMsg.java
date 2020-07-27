package com.chestmonster.chestmonster.channel;

import com.chestmonster.chestmonster.item.ItemHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;


public class SortInventoryMsg {

    public SortInventoryMsg() {}

    public static void encode(SortInventoryMsg msg, PacketBuffer buf) {}

    public static SortInventoryMsg decode(PacketBuffer buf) {
        return new SortInventoryMsg();
    }

    public static void handle(SortChestMsg msg, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {
            //ItemHandler.stack(Objects.requireNonNull(ctx.get().getSender()).inventory.mainInventory.subList(9,36));
            //ItemHandler.sort(Objects.requireNonNull(ctx.get().getSender()).inventory.mainInventory.subList(9,36));
            System.out.println("sort key press");
        });
        ctx.get().setPacketHandled(true);
    }

}
