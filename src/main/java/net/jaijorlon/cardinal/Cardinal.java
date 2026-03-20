package net.jaijorlon.cardinal;

import net.jaijorlon.cardinal.capabilities.GravityCapabilities;
import net.jaijorlon.cardinal.command.GravityCommand;
import net.jaijorlon.cardinal.command.PalladiumPropertyCommand;
import net.jaijorlon.cardinal.config.CardinalConfig;
import net.jaijorlon.cardinal.init.CardinalBlocks;
import net.jaijorlon.cardinal.init.CardinalCreativeTabs;
import net.jaijorlon.cardinal.init.CardinalItems;
import net.jaijorlon.cardinal.mob_effect.CardinalMobEffects;
import net.jaijorlon.cardinal.network.GravityNetwork;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.threetag.palladium.util.property.PalladiumPropertyLookup;
import net.threetag.palladiumcore.event.CommandEvents;
import org.slf4j.Logger;

@Mod(Cardinal.MOD_ID)
public class Cardinal {

	public static final String MOD_ID = "cardinal";
    public static final Logger LOGGER = LogUtils.getLogger();
	
	public static void init() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext ctx = ModLoadingContext.get();
		
		CardinalItems.ITEMS.register(bus);
		CardinalBlocks.BLOCKS.register(bus);
		CardinalBlocks.BLOCK_ENTITIES.register(bus);
		CardinalMobEffects.EFFECTS.register(bus);
		CardinalMobEffects.POTIONS.register(bus);
		CardinalCreativeTabs.CREATIVE_MODE_TAB.register(bus);

        CommandEvents.REGISTER.register((dispatcher, selection) -> {
            GravityCommand.register(dispatcher);
            PalladiumPropertyCommand.register(dispatcher);
        });

		GravityNetwork.registerMessages();
		MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, GravityCapabilities::attachEntityCapability);
		ctx.registerConfig(Type.COMMON, CardinalConfig.CONFIG_SPEC, "gravity-api.toml");
	}

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
