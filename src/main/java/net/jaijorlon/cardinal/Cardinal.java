package net.jaijorlon.cardinal;

import net.jaijorlon.cardinal.ability.CardinalAbilities;
import net.jaijorlon.cardinal.capabilities.GravityCapabilities;
import net.jaijorlon.cardinal.command.GravityCommand;
import net.jaijorlon.cardinal.command.PalladiumPropertyCommand;
import net.jaijorlon.cardinal.command.DirectionArgumentType;
import net.jaijorlon.cardinal.command.LocalDirectionArgumentType;
import net.jaijorlon.cardinal.command.OperationArgumentType;
import net.jaijorlon.cardinal.condition.CardinalConditionSerializers;
import net.jaijorlon.cardinal.init.CardinalBlocks;
import net.jaijorlon.cardinal.init.CardinalCreativeTabs;
import net.jaijorlon.cardinal.init.CardinalItems;
import net.jaijorlon.cardinal.mob_effect.CardinalMobEffects;
import net.jaijorlon.cardinal.network.GravityNetwork;
import net.jaijorlon.cardinal.config.CardinalConfig;

import com.mojang.logging.LogUtils;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.threetag.palladiumcore.event.CommandEvents;
import org.slf4j.Logger;

@Mod(Cardinal.MOD_ID)
public class Cardinal {

	public static final String MOD_ID = "cardinal";
    public static final Logger LOGGER = LogUtils.getLogger();

    public Cardinal() {
        ModLoadingContext ctx = ModLoadingContext.get();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ctx.registerConfig(Type.COMMON, CardinalConfig.SPEC, "cardinal.toml");

        CardinalItems.ITEMS.register(bus);
        CardinalBlocks.BLOCKS.register(bus);
        CardinalBlocks.BLOCK_ENTITIES.register(bus);
        CardinalMobEffects.EFFECTS.register(bus);
        CardinalMobEffects.POTIONS.register(bus);
        CardinalCreativeTabs.CREATIVE_MODE_TAB.register(bus);

        ArgumentTypeInfos.registerByClass(OperationArgumentType.class, SingletonArgumentInfo.contextFree(OperationArgumentType::new));
        ArgumentTypeInfos.registerByClass(DirectionArgumentType.class, SingletonArgumentInfo.contextFree(DirectionArgumentType::new));
        ArgumentTypeInfos.registerByClass(LocalDirectionArgumentType.class, SingletonArgumentInfo.contextFree(LocalDirectionArgumentType::new));

        CommandEvents.REGISTER.register((dispatcher, selection) -> {
            GravityCommand.register(dispatcher);
            PalladiumPropertyCommand.register(dispatcher);
        });

        GravityNetwork.registerMessages();
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, GravityCapabilities::attachEntityCapability);
    }

    public static void init() {
        CardinalAbilities.ABILITIES.register();
        CardinalConditionSerializers.CONDITION_SERIALIZERS.register();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
