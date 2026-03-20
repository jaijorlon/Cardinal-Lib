package net.jaijorlon.cardinal.event;

import com.google.common.eventbus.Subscribe;
import net.jaijorlon.cardinal.Cardinal;
import net.jaijorlon.cardinal.command.DirectionArgumentType;
import net.jaijorlon.cardinal.command.LocalDirectionArgumentType;

import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = Cardinal.MOD_ID)
public class RegisterArgumentTypes
{
	@Subscribe
	public static void onFMLCommonSetup(FMLCommonSetupEvent event)
	{
		ArgumentTypeInfos.registerByClass(DirectionArgumentType.class, SingletonArgumentInfo.contextFree(DirectionArgumentType::new));
		ArgumentTypeInfos.registerByClass(LocalDirectionArgumentType.class, SingletonArgumentInfo.contextFree(LocalDirectionArgumentType::new));
	}
}
