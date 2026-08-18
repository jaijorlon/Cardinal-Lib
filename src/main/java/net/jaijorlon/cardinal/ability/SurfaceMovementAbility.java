package net.jaijorlon.cardinal.ability;

import net.jaijorlon.cardinal.api.GravityChangerAPI;
import net.jaijorlon.cardinal.util.PalladiumPropertyUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.threetag.palladium.power.IPowerHolder;
import net.threetag.palladium.power.ability.Ability;
import net.threetag.palladium.power.ability.AbilityInstance;

import java.util.ArrayList;
import java.util.Objects;

public class SurfaceMovementAbility extends Ability {
    @Override
    public void tick(LivingEntity entity, AbilityInstance entry, IPowerHolder holder, boolean enabled) {
        if (enabled) {
            Direction Grav = Direction.DOWN;
            if (!entity.level().getBlockState(entity.blockPosition().offset(1, 0, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(entity.blockPosition().offset(1, 0, 0))) && !entity.level().getBlockState(entity.blockPosition().offset(-1, 0, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(entity.blockPosition().offset(-1, 0, 0))) && !entity.level().getBlockState(entity.blockPosition().offset(0, 0, 1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(entity.blockPosition().offset(0, 0, 1))) && !entity.level().getBlockState(entity.blockPosition().offset(0, 0, -1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(entity.blockPosition().offset(0, 0, -1))) && !entity.level().getBlockState(entity.blockPosition().offset(1, 1, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(entity.blockPosition().offset(1, 1, 0))) && !entity.level().getBlockState(entity.blockPosition().offset(-1, 1, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(entity.blockPosition().offset(-1, 1, 0))) && !entity.level().getBlockState(entity.blockPosition().offset(1, 1, 1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(entity.blockPosition().offset(1, 1, 1))) && !entity.level().getBlockState(entity.blockPosition().offset(-1, 1, 1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(entity.blockPosition().offset(-1, 1, 1))) && !entity.level().getBlockState(entity.blockPosition().offset(1, 1, -1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(entity.blockPosition().offset(1, 1, -1))) && !entity.level().getBlockState(entity.blockPosition().offset(-1, 1, -1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(entity.blockPosition().offset(-1, 1, 1)))) {
                GravityChangerAPI.setBaseGravityDirection(entity, Grav);
            }

            entity.getPersistentData().putInt("gravityCooldown", entity.getPersistentData().getInt("gravityCooldown")-1);
            if (entity.getPersistentData().getInt("gravityCooldown") > 0) return;
            if (!Objects.equals(PalladiumPropertyUtil.getString(entity, "gravityDir"), "down") && !Objects.equals(PalladiumPropertyUtil.getString(entity, "gravityDir"), "up") && !Objects.equals(PalladiumPropertyUtil.getString(entity, "gravityDir"), "west") && !Objects.equals(PalladiumPropertyUtil.getString(entity, "gravityDir"), "east")) {
                    BlockPos block = entity.blockPosition().north();
                    if (!entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), new BlockPos(block))) {
                        if (canMoveTo(block, entity).contains("up")) {
                            Grav = Direction.UP;
                        }
                        if (canMoveTo(block, entity).contains("down")) {
                            Grav = Direction.DOWN;
                        }
                        if (canMoveTo(block, entity).contains("west")) {
                            Grav = Direction.WEST;
                        }
                        if (canMoveTo(block, entity).contains("east")) {
                            Grav = Direction.EAST;
                        }
                        entity.getPersistentData().putInt("gravityCooldown", 5);
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    }

                    block = entity.blockPosition().south();
                    if (!entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), new BlockPos(block))) {
                        if (canMoveTo(block, entity).contains("up")) {
                            Grav = Direction.UP;
                        }
                        if (canMoveTo(block, entity).contains("down")) {
                            Grav = Direction.DOWN;
                        }
                        if (canMoveTo(block, entity).contains("west")) {
                            Grav = Direction.WEST;
                        }
                        if (canMoveTo(block, entity).contains("east")) {
                            Grav = Direction.EAST;
                        }
                        entity.getPersistentData().putInt("gravityCooldown", 5);
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    }
            }
            else if (!Objects.equals(PalladiumPropertyUtil.getString(entity, "gravityDir"), "down") && !Objects.equals(PalladiumPropertyUtil.getString(entity, "gravityDir"), "up") && !Objects.equals(PalladiumPropertyUtil.getString(entity, "gravityDir"), "north") && !Objects.equals(PalladiumPropertyUtil.getString(entity, "gravityDir"), "south")) {
                    BlockPos block = entity.blockPosition().west();
                    if (!entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), new BlockPos(block))) {
                        if (canMoveTo(block, entity).contains("up")) {
                            Grav = Direction.UP;
                        }
                        if (canMoveTo(block, entity).contains("down")) {
                            Grav = Direction.DOWN;
                        }
                        if (canMoveTo(block, entity).contains("north")) {
                            Grav = Direction.NORTH;
                        }
                        if (canMoveTo(block, entity).contains("south")) {
                            Grav = Direction.SOUTH;
                        }
                        entity.getPersistentData().putInt("gravityCooldown", 5);
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    }

                    block = entity.blockPosition().east();
                    if (!entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), new BlockPos(block))) {
                        if (canMoveTo(block, entity).contains("up")) {
                            Grav = Direction.UP;
                        }
                        if (canMoveTo(block, entity).contains("down")) {
                            Grav = Direction.DOWN;
                        }
                        if (canMoveTo(block, entity).contains("north")) {
                            Grav = Direction.NORTH;
                        }
                        if (canMoveTo(block, entity).contains("south")) {
                            Grav = Direction.SOUTH;
                        }
                        entity.getPersistentData().putInt("gravityCooldown", 5);
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    }
            }
            else {
                    BlockPos block = entity.blockPosition().below();

                    if (!entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), new BlockPos(block))) {
                        if (canMoveTo(block, entity).contains("west")) {
                            Grav = Direction.WEST;
                        }
                        if (canMoveTo(block, entity).contains("east")) {
                            Grav = Direction.EAST;
                        }
                        if (canMoveTo(block, entity).contains("south")) {
                            Grav = Direction.SOUTH;
                        }
                        if (canMoveTo(block, entity).contains("north")) {
                            Grav = Direction.NORTH;
                        }
                        entity.getPersistentData().putInt("gravityCooldown", 5);
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    }

                    block = entity.blockPosition().above();
                    if (!entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), new BlockPos(block))) {
                        if (canMoveTo(block, entity).contains("up")) {
                            Grav = Direction.UP;
                        }
                        if (canMoveTo(block, entity).contains("west")) {
                            Grav = Direction.WEST;
                        }
                        if (canMoveTo(block, entity).contains("east")) {
                            Grav = Direction.EAST;
                        }
                        if (canMoveTo(block, entity).contains("south")) {
                            Grav = Direction.SOUTH;
                        }
                        if (canMoveTo(block, entity).contains("north")) {
                            Grav = Direction.NORTH;
                        }
                        entity.getPersistentData().putInt("gravityCooldown", 5);
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    }
            }
        }
    }

    @Override
    public void lastTick(LivingEntity entity, AbilityInstance entry, IPowerHolder holder, boolean enabled) {
        if (enabled) {
            GravityChangerAPI.setBaseGravityDirection(entity, Direction.DOWN);
        }
    }

    @Override
    public String getDocumentationDescription() {
        return "Allows the user to move on any surface.";
    }

    public ArrayList<String> canMoveTo(BlockPos block, LivingEntity entity) {
        ArrayList<String> check = new ArrayList<>();

        if (entity.level().getBlockState(block.offset(1, 0, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(block.offset(1, 0, 0)))) {
            check.add("east");
        }
        if (entity.level().getBlockState(block.offset(-1, 0, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(block.offset(-1, 0, 0)))) {
            check.add("west");
        }
        if (entity.level().getBlockState(block.offset(0, 1, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(block.offset(0, 1, 0)))) {
            check.add("up");
        }
        if (entity.level().getBlockState(block.offset(0, -1, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(block.offset(0, -1, 0)))) {
            check.add("down");
        }
        if (entity.level().getBlockState(block.offset(0, 0, 1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(block.offset(0, 0, 1)))) {
            check.add("south");
        }
        if (entity.level().getBlockState(block.offset(0, 0, -1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(block.offset(0, 0, -1)))) {
            check.add("north");
        }

        return check;
    }
}