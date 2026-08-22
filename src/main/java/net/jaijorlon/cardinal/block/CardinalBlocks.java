package net.jaijorlon.cardinal.block;

import net.jaijorlon.cardinal.Cardinal;
import net.jaijorlon.cardinal.block.custom.GravityPlatingBlock;
import net.jaijorlon.cardinal.block.entity.GravityPlatingBlockEntity;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CardinalBlocks
{
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Cardinal.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Cardinal.MOD_ID);
    
    public static final RegistryObject<Block> GRAVITY_PLATING = BLOCKS.register("plating", () -> new GravityPlatingBlock());
    
    public static final RegistryObject<BlockEntityType<GravityPlatingBlockEntity>> GRAVITY_PLATING_BLOCK_ENTITY = BLOCK_ENTITIES.register("plating", () -> BlockEntityType.Builder.of(GravityPlatingBlockEntity::new, CardinalBlocks.GRAVITY_PLATING.get()).build(null));
}
