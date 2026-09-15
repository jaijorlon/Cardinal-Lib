package net.jaijorlon.cardinal.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SMovingTowardsAxisPacket {
    private final String value;

    public C2SMovingTowardsAxisPacket(String value) {
        this.value = value;
    }

    public C2SMovingTowardsAxisPacket(FriendlyByteBuf buffer) {
        this(buffer.readUtf());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.value);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ServerPlayer player = contextSupplier.get().getSender();

            if (player == null) return;

            player.getPersistentData().putString("Cardinal.movingTowardsAxis", this.value);
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
