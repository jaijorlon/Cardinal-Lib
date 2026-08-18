package net.jaijorlon.cardinal.network;

import net.jaijorlon.cardinal.Cardinal;
import net.jaijorlon.cardinal.network.packet.C2SHasInputKeyConditionPacket;
import net.jaijorlon.cardinal.network.packet.C2SMouseClickConditionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    public static SimpleChannel INSTANCE;

    public static void register() {
        INSTANCE = NetworkRegistry.newSimpleChannel(Cardinal.id("main"), () -> "1", (s) -> true, (s) -> true);
        int packetId = 0;
        INSTANCE.messageBuilder(C2SMouseClickConditionPacket.class, packetId++, NetworkDirection.PLAY_TO_SERVER).decoder(C2SMouseClickConditionPacket::new).encoder(C2SMouseClickConditionPacket::encode).consumerMainThread(C2SMouseClickConditionPacket::handle).add();
        INSTANCE.messageBuilder(C2SHasInputKeyConditionPacket.class, packetId++, NetworkDirection.PLAY_TO_SERVER).decoder(C2SHasInputKeyConditionPacket::new).encoder(C2SHasInputKeyConditionPacket::encode).consumerMainThread(C2SHasInputKeyConditionPacket::handle).add();
        Cardinal.LOGGER.info("finished registering packets up to id-"+packetId);
    }

    public static void sendToServer(Object packet) {
        INSTANCE.sendToServer(packet);
    }

    public static void sendToPlayer(Object packet, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    public static void sendToAllClients(Object packet) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
    }
}