package net.jaijorlon.cardinal.event;

import net.jaijorlon.cardinal.Cardinal;
import net.jaijorlon.cardinal.api.GravityChangerAPI;
import net.jaijorlon.cardinal.capabilities.GravityCapabilityImpl;
import net.jaijorlon.cardinal.config.CardinalConfig;
import net.jaijorlon.cardinal.util.GCUtil;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Cardinal.MOD_ID)
public class CommonEvents
{
    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event)
    {
    	//GravityCommand.register(event.getDispatcher());
    }
    
	@SubscribeEvent
	public static void onEntityJoinLevel(EntityJoinLevelEvent event)
	{
		Entity entity = event.getEntity();
		GCUtil.ENTITY_MAP.put(entity.getClass().hashCode(), entity);
		GCUtil.ENTITY_MAP2.put(entity.getClass().getSuperclass().hashCode(), entity);
	}
	
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event)
    {
    	Player player = event.getEntity();
    	if(event.isWasDeath() && !CardinalConfig.resetGravityOnRespawn.get())
    	{
        	Player original = event.getOriginal();
        	original.revive();
        	GravityChangerAPI.setBaseGravityDirection(player, GravityChangerAPI.getBaseGravityDirection(original));
    	}
		for(Entity entity : GCUtil.getAllEntities(player.level()))
		{
			if(!entity.level().isClientSide)
			{
    			if(GravityChangerAPI.getBaseGravityDirection(entity) == Direction.DOWN)
    			{
    				continue;
    			}
    			GravityCapabilityImpl cap = GravityChangerAPI.getGravityComponent(entity);
    			cap.initialized = false;
				cap.deserializeNBT(cap.serializeNBT());
			}
		}
    }
}
