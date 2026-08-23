package net.jaijorlon.cardinal.network.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SHasCollisionPacket {
    private final String key;
    private final boolean value;;

    public C2SHasCollisionPacket(String key, boolean value) {
        this.key = key;
        this.value = value;
    }

    public C2SHasCollisionPacket(FriendlyByteBuf buffer) {
        this(buffer.readUtf(), buffer.readBoolean());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeUtf(this.key);
        buffer.writeBoolean(this.value);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            ServerPlayer player = contextSupplier.get().getSender();

            if (player == null) return;


            player.getPersistentData().putBoolean("Cardinal.has"+this.key+"Collision", this.value);
        });
        contextSupplier.get().setPacketHandled(true);
    }
}
