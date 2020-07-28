package com.chestmonster.chestmonster.channel;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static com.chestmonster.chestmonster.events.ChestSorter.chestFlag;
import static com.chestmonster.chestmonster.events.ChestSorter.lastInteractPosition;

public class SortChestMsg {

    private BlockPos Pos;

    public SortChestMsg() {}
    public SortChestMsg(BlockPos Pos) {
        this.Pos = Pos;
    }

    public static void encode(SortChestMsg msg, PacketBuffer buf) {
        buf.writeInt(msg.Pos.getX());
        buf.writeInt(msg.Pos.getY());
        buf.writeInt(msg.Pos.getZ());
    }

    public static SortChestMsg decode(PacketBuffer buf) {
        return new SortChestMsg(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()));
    }

    public static void handle(SortChestMsg msg, Supplier<NetworkEvent.Context> ctx) {


        ctx.get().enqueueWork(() -> {
            lastInteractPosition.offer(msg.Pos);
        });
        ctx.get().setPacketHandled(true);
    }

}
