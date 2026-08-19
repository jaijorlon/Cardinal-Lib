package net.jaijorlon.cardinal.event;

import net.jaijorlon.cardinal.Cardinal;
import net.jaijorlon.cardinal.ability.CardinalAbilities;
import net.jaijorlon.cardinal.api.GravityChangerAPI;
import net.jaijorlon.cardinal.capabilities.GravityCapabilities;
import net.jaijorlon.cardinal.capabilities.GravityCapabilityImpl;
import net.jaijorlon.cardinal.command.GravityCommand;
import net.jaijorlon.cardinal.config.CardinalConfigHandler;
import net.jaijorlon.cardinal.network.PacketHandler;
import net.jaijorlon.cardinal.util.GCUtil;
import net.jaijorlon.cardinal.util.PalladiumPropertyUtil;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
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

            PalladiumPropertyUtil.setValue(player, "gravityDir", GravityChangerAPI.getGravityDirection(player).getName().toLowerCase());

            if (!AbilityUtil.isTypeEnabled(player, CardinalAbilities.SURFACE_MOVEMENT.get()) && !player.getPersistentData().getBoolean("cardinalGravityReset")) {
                GravityChangerAPI.setBaseGravityDirection(player, Direction.DOWN);
                player.getPersistentData().putBoolean("cardinalGravityReset", true);
            }
            else if (AbilityUtil.isTypeEnabled(player, CardinalAbilities.SURFACE_MOVEMENT.get()) && player.getPersistentData().getBoolean("cardinalGravityReset")) {
                player.getPersistentData().putBoolean("cardinalGravityReset", false);
            }
        }

        @SubscribeEvent
        public static void onRegisterCommands(RegisterCommandsEvent event) {
            GravityCommand.register(event.getDispatcher());
        }

        @SubscribeEvent
        public static void onEntityJoinLevel(EntityJoinLevelEvent event) {
            Entity entity = event.getEntity();
            if (ModList.get().isLoaded("palladium")) {
                net.threetag.palladium.event.PalladiumEvents.REGISTER_PROPERTY.register(handler -> {
                    PalladiumPropertyUtil.registerProperty(handler, "forDoTestProperty", "boolean", false);
                });
            }

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
