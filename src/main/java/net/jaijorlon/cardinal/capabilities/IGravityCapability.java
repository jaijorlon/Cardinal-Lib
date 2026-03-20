package net.jaijorlon.cardinal.capabilities;

import net.jaijorlon.cardinal.Cardinal;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.util.INBTSerializable;

@AutoRegisterCapability
public interface IGravityCapability extends INBTSerializable<CompoundTag>
{
	ResourceLocation ID = Cardinal.id("gravity");

	void setEntity(Entity entity);
	
	void tick();
	
	void applyGravityChange();
	
	void sync(boolean noAnimation, Direction baseGravityDirection, Direction currentGravityDirection, double baseGravityStrength, double currentGravityStrength);
}
