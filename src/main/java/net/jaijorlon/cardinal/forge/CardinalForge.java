package net.jaijorlon.cardinal.forge;

import net.jaijorlon.cardinal.Cardinal;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.threetag.palladiumcore.forge.PalladiumCoreForge;

@Mod(Cardinal.MOD_ID)
@Mod.EventBusSubscriber(modid = Cardinal.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CardinalForge {

    public CardinalForge() {
        PalladiumCoreForge.registerModEventBus(Cardinal.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Cardinal.init();
    }
}
