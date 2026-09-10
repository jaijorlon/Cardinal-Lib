package net.jaijorlon.cardinal.event;

import net.jaijorlon.cardinal.Cardinal;
import net.jaijorlon.cardinal.ability.CardinalAbilities;
import net.jaijorlon.cardinal.api.GravityChangerAPI;
import net.jaijorlon.cardinal.capabilities.GravityCapabilities;
import net.jaijorlon.cardinal.capabilities.GravityCapabilityImpl;
import net.jaijorlon.cardinal.command.GravityCommand;
import net.jaijorlon.cardinal.config.CardinalConfigHandler;
import net.jaijorlon.cardinal.network.PacketHandler;
import net.jaijorlon.cardinal.network.packet.C2SHasCollisionPacket;
import net.jaijorlon.cardinal.network.packet.C2SHasInputKeyConditionPacket;
import net.jaijorlon.cardinal.util.GCUtil;
import net.jaijorlon.cardinal.util.PalladiumPropertyUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.threetag.palladium.power.ability.AbilityUtil;

import java.util.Objects;

public class ModEvents {
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Cardinal.MOD_ID)
    public static class ForgeModEvents {
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
            Player player = event.player;

            if (ModList.get().isLoaded("palladium")) {
                if (player.level().isClientSide()) {
                    BlockPos blockPos = player.blockPosition();
                    Vec3 playerPos = player.position();

                    boolean northValid = false;
                    boolean southValid = false;
                    boolean eastValid = false;
                    boolean westValid = false;

                    boolean upValid = false;
                    boolean downValid = false;

                    if (player.level().getBlockState(blockPos.north()).isCollisionShapeFullBlock(player.level(), blockPos.north())) {
                        northValid = playerPos.distanceTo(blockPos.north().getCenter()) < 1;
                    }

                    if (player.level().getBlockState(blockPos.south()).isCollisionShapeFullBlock(player.level(), blockPos.south())) {
                        southValid = playerPos.distanceTo(blockPos.south().getCenter()) < 1;
                    }

                    if (player.level().getBlockState(blockPos.east()).isCollisionShapeFullBlock(player.level(), blockPos.east())) {
                        eastValid = playerPos.distanceTo(blockPos.east().getCenter()) < 1;
                    }

                    if (player.level().getBlockState(blockPos.west()).isCollisionShapeFullBlock(player.level(), blockPos.west())) {
                        westValid = playerPos.distanceTo(blockPos.west().getCenter()) < 1;
                    }

                    if (player.level().getBlockState(blockPos.above()).isCollisionShapeFullBlock(player.level(), blockPos.above())) {
                        upValid = playerPos.distanceTo(blockPos.above().getCenter()) < 1;
                    }

                    if (player.level().getBlockState(blockPos.below()).isCollisionShapeFullBlock(player.level(), blockPos.below())) {
                        downValid = playerPos.distanceTo(blockPos.below().getCenter()) < 1;
                    }

                    PacketHandler.sendToServer(new C2SHasCollisionPacket("Horizontal", northValid || southValid || eastValid || westValid));
                    PacketHandler.sendToServer(new C2SHasCollisionPacket("Vertical", upValid || downValid));
                }

                if (!AbilityUtil.isTypeEnabled(player, CardinalAbilities.SURFACE_MOVEMENT.get()) && !player.getPersistentData().getBoolean("cardinalGravityReset")) {
                    GravityChangerAPI.setBaseGravityDirection(player, Direction.DOWN);
                    player.getPersistentData().putBoolean("cardinalGravityReset", true);
                } else if (AbilityUtil.isTypeEnabled(player, CardinalAbilities.SURFACE_MOVEMENT.get()) && player.getPersistentData().getBoolean("cardinalGravityReset")) {
                    player.getPersistentData().putBoolean("cardinalGravityReset", false);
                }
            }
        }

        @SubscribeEvent
        public static void onRegisterCommands(RegisterCommandsEvent event) {
            GravityCommand.register(event.getDispatcher());
        }

        @SubscribeEvent
        public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
            Entity entity = event.getEntity();

            GCUtil.ENTITY_MAP.put(entity.getClass().hashCode(), entity);
            GCUtil.ENTITY_MAP2.put(entity.getClass().getSuperclass().hashCode(), entity);
        }

        @SubscribeEvent
        public static void onPlayerClone(PlayerEvent.Clone event) {
            Player player = event.getEntity();
            if (event.isWasDeath() && !CardinalConfigHandler.resetGravityOnRespawn) {
                Player original = event.getOriginal();
                original.revive();
                GravityChangerAPI.setBaseGravityDirection(player, GravityChangerAPI.getBaseGravityDirection(original));
            }
            for (Entity entity : Objects.requireNonNull(GCUtil.getAllEntities(player.level()))) {
                if (!entity.level().isClientSide) {
                    if (GravityChangerAPI.getBaseGravityDirection(entity) == Direction.DOWN) {
                        continue;
                    }
                    GravityCapabilityImpl cap = GravityChangerAPI.getGravityComponent(entity);
                    cap.initialized = false;
                    cap.deserializeNBT(cap.serializeNBT());
                }
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Cardinal.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModEventBusEvents {
        @SubscribeEvent
        public static void commonSetup(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                PacketHandler.register();
            });
        }
    }
}
