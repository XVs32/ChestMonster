package com.chestmonster.chestmonster.channel;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;
import static com.chestmonster.chestmonster.events.ChestSorter.lastInteractPosition;

import java.util.function.Supplier;

public class LastChestPos {

    private BlockPos Pos;


    public LastChestPos() {}
    public LastChestPos(BlockPos Pos) {
        this.Pos = Pos;
    }

    public static void encode(LastChestPos msg, PacketBuffer buf) {
        buf.writeInt(msg.Pos.getX());
        buf.writeInt(msg.Pos.getY());
        buf.writeInt(msg.Pos.getZ());
    }

    public static LastChestPos decode(PacketBuffer buf) {
        return new LastChestPos(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()));
    }

    public static void handle(LastChestPos msg, Supplier<NetworkEvent.Context> ctx) {

        ctx.get().enqueueWork(() -> {

            lastInteractPosition = msg.Pos;
            System.out.println("right click");

        });
        ctx.get().setPacketHandled(true);
    }


}

