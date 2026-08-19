package net.jaijorlon.cardinal.ability;

import net.jaijorlon.cardinal.api.GravityChangerAPI;
import net.jaijorlon.cardinal.capabilities.GravityCapabilityImpl;
import net.jaijorlon.cardinal.plating.GravityPlatingBlock;
import net.jaijorlon.cardinal.util.PalladiumPropertyUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.threetag.palladium.power.IPowerHolder;
import net.threetag.palladium.power.ability.Ability;
import net.threetag.palladium.power.ability.AbilityInstance;

import java.util.ArrayList;
import java.util.Objects;

public class SurfaceMovementAbility extends Ability {
    @Override
    public void tick(LivingEntity entity, AbilityInstance entry, IPowerHolder holder, boolean enabled) {
            if (enabled) {
                Direction Grav = null;
                BlockPos blockPos = entity.blockPosition();

                if (!entity.level().getBlockState(blockPos.offset(1, 0, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(1, 0, 0))) && !entity.level().getBlockState(blockPos.offset(-1, 0, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(-1, 0, 0))) && !entity.level().getBlockState(blockPos.offset(0, 0, 1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(0, 0, 1))) && !entity.level().getBlockState(blockPos.offset(0, 0, -1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(0, 0, -1))) && !entity.level().getBlockState(blockPos.offset(1, 1, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(1, 1, 0))) && !entity.level().getBlockState(blockPos.offset(-1, 1, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(-1, 1, 0))) && !entity.level().getBlockState(blockPos.offset(1, 1, 1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(1, 1, 1))) && !entity.level().getBlockState(blockPos.offset(-1, 1, 1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(-1, 1, 1))) && !entity.level().getBlockState(blockPos.offset(1, 1, -1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(1, 1, -1))) && !entity.level().getBlockState(blockPos.offset(-1, 1, -1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(-1, 1, 1)))) {
                    Grav = Direction.DOWN;
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                }

                if (entity.getPersistentData().getInt("gravityCooldown") > 0) {
                    entity.getPersistentData().putInt("gravityCooldown", entity.getPersistentData().getInt("gravityCooldown") - 1);
                }

                if (entity.getPersistentData().getInt("gravityCooldown") > 0) return;

                if (!Objects.equals(PalladiumPropertyUtil.getString(entity, "gravityDir"), "down") && !Objects.equals(PalladiumPropertyUtil.getString(entity, "gravityDir"), "up") && !Objects.equals(PalladiumPropertyUtil.getString(entity, "gravityDir"), "west") && !Objects.equals(PalladiumPropertyUtil.getString(entity, "gravityDir"), "east")) {
                    BlockPos block = blockPos.north();
                    if (!entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block)) {
                        if (canMoveTo(block, entity) != null) {
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
                        }
                        entity.getPersistentData().putInt("gravityCooldown", 5);
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    }

                    block = blockPos.south();
                    if (!entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block)) {
                        if (canMoveTo(block, entity) != null) {
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
                        }
                        entity.getPersistentData().putInt("gravityCooldown", 5);
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    }
                } else if (!Objects.equals(PalladiumPropertyUtil.getString(entity, "gravityDir"), "down") && !Objects.equals(PalladiumPropertyUtil.getString(entity, "gravityDir"), "up") && !Objects.equals(PalladiumPropertyUtil.getString(entity, "gravityDir"), "north") && !Objects.equals(PalladiumPropertyUtil.getString(entity, "gravityDir"), "south")) {
                    BlockPos block = blockPos.west();
                    if (!entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block)) {
                        if (canMoveTo(block, entity) != null) {
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
                        }
                        entity.getPersistentData().putInt("gravityCooldown", 5);
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    }

                    block = blockPos.east();
                    if (!entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block)) {
                        if (canMoveTo(block, entity) != null) {
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
                        }
                        entity.getPersistentData().putInt("gravityCooldown", 5);
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    }
                } else {
                    BlockPos block = blockPos.below();

                    if (!entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block)) {
                        if (canMoveTo(block, entity) != null) {
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
                        }
                        entity.getPersistentData().putInt("gravityCooldown", 5);
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    }

                    block = blockPos.above();
                    if (!entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block)) {
                        if (canMoveTo(block, entity) != null) {
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
            GravityCapabilityImpl comp = GravityChangerAPI.getGravityComponent(entity);
            GravityChangerAPI.setBaseGravityDirection(entity, Direction.DOWN);
        }
    }

    @Override
    public String getDocumentationDescription() {
        return "Allows the user to move on any surface.";
    }

    public ArrayList<String> canMoveTo(BlockPos block, LivingEntity entity) {
        ArrayList<String> check = new ArrayList<>();
        if (!entity.level().isClientSide()) {
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
        return null;
    }

    // when approaching an inward corner, do auto-jump to make it smoothly go forward
    private static void tryToDoCornerAutoJump(BlockState blockState, BlockPos blockPos, Entity entity, GravityCapabilityImpl comp) {
        if (!entity.onGround()) {
            return;
        }

        // apply levitation when the entity is close to corner
        Direction entityGravityDir = comp.getCurrGravityDirection();

        for (Direction plateDir : Direction.values()) {
            if (GravityPlatingBlock.hasDir(blockState, plateDir)) {
                boolean orthogonal = entityGravityDir.getAxis() != plateDir.getAxis();
                if (!orthogonal) {
                    continue;
                }

                Vec3 plateDirVec = Vec3.atLowerCornerOf(plateDir.getNormal());

                Vec3 effectCenter = Vec3.atCenterOf(blockPos).add(plateDirVec.scale(0.5));
                Vec3 offset = effectCenter.subtract(entity.position());
                if (offset.dot(Vec3.atLowerCornerOf(entityGravityDir.getNormal())) > 0) {
                    // that plate is lower than entity
                    continue;
                }

                Vec3 worldVelocity = GravityChangerAPI.getWorldVelocity(entity);
                if (worldVelocity.dot(plateDirVec) < 0.01) {
                    continue;
                }

                double distanceToPlate = Math.abs(entity.position().subtract(effectCenter).dot(plateDirVec));
                if (distanceToPlate < 0.8) {
                    double strengthSqrt = Math.sqrt(comp.getCurrGravityStrength());

                    Vec3 entityGravityVec = Vec3.atLowerCornerOf(entityGravityDir.getNormal());

                    Vec3 deltaWorldVelocity =
                            entityGravityVec.scale(-strengthSqrt * 0.4)
                                    .add(plateDirVec.scale(0.08));

                    GravityChangerAPI.setWorldVelocity(
                            entity,
                            GravityChangerAPI.getWorldVelocity(entity).add(deltaWorldVelocity)
                    );

                    if (entity.level().isClientSide()) {
                        //LOGGER.info("Client entity auto-jump on gravity plate corner {}", entity);
                    }
                    return;
                }
            }
        }
    }

}