package net.jaijorlon.cardinal.ability;

import net.jaijorlon.cardinal.api.GravityChangerAPI;
import net.jaijorlon.cardinal.capabilities.GravityCapabilityImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.threetag.palladium.power.IPowerHolder;
import net.threetag.palladium.power.ability.Ability;
import net.threetag.palladium.power.ability.AbilityInstance;

import java.util.ArrayList;

public class SurfaceMovementAbility extends Ability {
    @Override
    public void tick(LivingEntity entity, AbilityInstance entry, IPowerHolder holder, boolean enabled) {
        if (enabled) {
            if (!entity.level().isClientSide()) {
                boolean devMode = false;
                Direction Grav = null;
                BlockPos blockPos = entity.blockPosition();
                Direction baseGravityDirection = GravityChangerAPI.getBaseGravityDirection(entity);
                GravityCapabilityImpl comp = GravityChangerAPI.getGravityComponent(entity);
                String movingTowardsAxis = entity.getPersistentData().getString("Cardinal.movingTowardsAxis");

                boolean shouldAttach = true;
                boolean innerMovementNorth = false, innerMovementSouth = false, innerMovementEast = false, innerMovementWest = false;

                if (!entity.level().getBlockState(blockPos.offset(0, 2, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(0, 2, 0))) && !entity.level().getBlockState(blockPos.offset(1, 0, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(1, 0, 0))) && !entity.level().getBlockState(blockPos.offset(-1, 0, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(-1, 0, 0))) && !entity.level().getBlockState(blockPos.offset(0, 0, 1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(0, 0, 1))) && !entity.level().getBlockState(blockPos.offset(0, 0, -1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(0, 0, -1))) && !entity.level().getBlockState(blockPos.offset(1, 1, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(1, 1, 0))) && !entity.level().getBlockState(blockPos.offset(-1, 1, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(-1, 1, 0))) && !entity.level().getBlockState(blockPos.offset(1, 1, 1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(1, 1, 1))) && !entity.level().getBlockState(blockPos.offset(-1, 1, 1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(-1, 1, 1))) && !entity.level().getBlockState(blockPos.offset(1, 1, -1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(1, 1, -1))) && !entity.level().getBlockState(blockPos.offset(-1, 1, -1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(-1, 1, 1)))) {
                    Grav = Direction.DOWN;
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                }

                if (entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.jump")) {
                    Grav = Direction.DOWN;
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                }


                if (!baseGravityDirection.equals(Direction.UP)) {
                    BlockPos block = blockPos.above(2);
                    BlockPos blockSide = blockPos.north();
                    Grav = Direction.UP;
                    shouldAttach = entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.jump");

                    if (baseGravityDirection.equals(Direction.NORTH)) {
                        shouldAttach = movingTowardsAxis.equals("-z");
                        block = blockPos.above();
                    }
                    if (baseGravityDirection.equals(Direction.SOUTH)) {
                        shouldAttach = movingTowardsAxis.equals("-z");
                        block = blockPos.above();
                        blockSide = blockPos.south();
                    }
                    else if (baseGravityDirection.equals(Direction.EAST)) {
                        shouldAttach = movingTowardsAxis.equals("-z");
                        block = blockPos.above();
                        blockSide = blockPos.east();
                    }
                    else if (baseGravityDirection.equals(Direction.WEST)) {
                        shouldAttach = movingTowardsAxis.equals("-z");
                        block = blockPos.above();
                        blockSide = blockPos.west();
                    }

                    if (shouldAttach && entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && baseGravityDirection.equals(Direction.DOWN)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("up")));
                        return;
                    }
                    else if (shouldAttach && entity.level().getBlockState(blockSide).isCollisionShapeFullBlock(entity.level(), blockSide) && entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("up inner")));
                        return;
                    }
                }


                if (!baseGravityDirection.equals(Direction.NORTH)) {
                    BlockPos block = blockPos.north();
                    Grav = Direction.NORTH;

                    shouldAttach = movingTowardsAxis.equals("-z");

                    if (baseGravityDirection.equals(Direction.UP)) {
                        shouldAttach = movingTowardsAxis.equals("-z");
                    }
                    if (baseGravityDirection.equals(Direction.WEST)) {
                        shouldAttach = movingTowardsAxis.equals("-x");
                        if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && entity.level().getBlockState(blockPos.west()).isCollisionShapeFullBlock(entity.level(), blockPos.west())) {
                            shouldAttach = movingTowardsAxis.equals("x");
                            innerMovementNorth = true;
                        }
                    }
                    if (baseGravityDirection.equals(Direction.EAST)) {
                        shouldAttach = movingTowardsAxis.equals("x");
                        if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && entity.level().getBlockState(blockPos.east()).isCollisionShapeFullBlock(entity.level(), blockPos.east())) {
                            shouldAttach = movingTowardsAxis.equals("-x");
                            innerMovementNorth = true;
                        }
                    }

                    if (!innerMovementNorth && shouldAttach && entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && baseGravityDirection.equals(Direction.DOWN)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("north")));
                        return;
                    }
                    else if (!innerMovementNorth && shouldAttach && entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && baseGravityDirection.equals(Direction.UP)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("up to north inner")));
                        return;
                    }
                    else if (!innerMovementNorth && shouldAttach && entity.level().getBlockState(block.east()).isCollisionShapeFullBlock(entity.level(), block.east()) && !entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && !entity.level().getBlockState(blockPos.east()).isCollisionShapeFullBlock(entity.level(), blockPos.east()) && baseGravityDirection.equals(Direction.EAST)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("east to north")));
                        return;
                    }
                    else if (!innerMovementNorth && shouldAttach && entity.level().getBlockState(block.west()).isCollisionShapeFullBlock(entity.level(), block.west()) && !entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && !entity.level().getBlockState(blockPos.west()).isCollisionShapeFullBlock(entity.level(), blockPos.west()) && baseGravityDirection.equals(Direction.WEST)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("west to north")));
                        return;
                    }
                    else if (innerMovementNorth && shouldAttach) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("west/east to north inner")));
                        return;
                    }
                }


                if (!baseGravityDirection.equals(Direction.SOUTH)) {
                    BlockPos block = blockPos.south();
                    Grav = Direction.SOUTH;

                    shouldAttach = movingTowardsAxis.equals("z");

                    if (baseGravityDirection.equals(Direction.WEST)) {
                        shouldAttach = movingTowardsAxis.equals("x");
                        if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && entity.level().getBlockState(blockPos.west()).isCollisionShapeFullBlock(entity.level(), blockPos.west())) {
                            shouldAttach = movingTowardsAxis.equals("-x");
                            innerMovementSouth = true;
                        }
                    }
                    if (baseGravityDirection.equals(Direction.EAST)) {
                        shouldAttach = movingTowardsAxis.equals("-x");
                        if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && entity.level().getBlockState(blockPos.east()).isCollisionShapeFullBlock(entity.level(), blockPos.east())) {
                            shouldAttach = movingTowardsAxis.equals("x");
                            innerMovementSouth = true;
                        }
                    }

                    if (!innerMovementSouth && shouldAttach && entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && baseGravityDirection.equals(Direction.DOWN)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("south")));
                        return;
                    }
                    else if (!innerMovementSouth && shouldAttach && entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && baseGravityDirection.equals(Direction.UP)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("up to south inner")));
                        return;
                    }
                    else if (!innerMovementSouth && shouldAttach && entity.level().getBlockState(block.east()).isCollisionShapeFullBlock(entity.level(), block.east()) && !entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && !entity.level().getBlockState(blockPos.east()).isCollisionShapeFullBlock(entity.level(), blockPos.east()) && baseGravityDirection.equals(Direction.EAST)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("east to south")));
                        return;
                    }
                    else if (!innerMovementSouth && shouldAttach && entity.level().getBlockState(block.west()).isCollisionShapeFullBlock(entity.level(), block.west()) && !entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && !entity.level().getBlockState(blockPos.west()).isCollisionShapeFullBlock(entity.level(), blockPos.west()) && baseGravityDirection.equals(Direction.WEST)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("west to south")));
                        return;
                    }
                    else if (innerMovementSouth && shouldAttach) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("west/east to south inner")));
                        return;
                    }
                }


                if (!baseGravityDirection.equals(Direction.EAST)) {
                    BlockPos block = blockPos.east();
                    Grav = Direction.EAST;

                    shouldAttach = movingTowardsAxis.equals("x");

                    if (baseGravityDirection.equals(Direction.UP)) {
                        shouldAttach = movingTowardsAxis.equals("-x");
                    }
                    if (baseGravityDirection.equals(Direction.NORTH)) {
                        shouldAttach = movingTowardsAxis.equals("-x");
                        if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && entity.level().getBlockState(blockPos.north()).isCollisionShapeFullBlock(entity.level(), blockPos.north())) {
                            shouldAttach = movingTowardsAxis.equals("x");
                            innerMovementEast = true;
                        }
                    }
                    if (baseGravityDirection.equals(Direction.SOUTH)) {
                        shouldAttach = movingTowardsAxis.equals("x");
                        if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && entity.level().getBlockState(blockPos.south()).isCollisionShapeFullBlock(entity.level(), blockPos.south())) {
                            shouldAttach = movingTowardsAxis.equals("-x");
                            innerMovementEast = true;
                        }
                    }


                    if (!innerMovementEast && shouldAttach && entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && baseGravityDirection.equals(Direction.DOWN)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("east")));
                        return;
                    }
                    else if (!innerMovementEast && shouldAttach && entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && baseGravityDirection.equals(Direction.UP)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("up to east inner")));
                        return;
                    }
                    else if (!innerMovementEast && shouldAttach && entity.level().getBlockState(block.north()).isCollisionShapeFullBlock(entity.level(), block.north()) && !entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && !entity.level().getBlockState(blockPos.north()).isCollisionShapeFullBlock(entity.level(), blockPos.north()) && baseGravityDirection.equals(Direction.NORTH)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("north to east")));
                        return;
                    }
                    else if (!innerMovementEast && shouldAttach && entity.level().getBlockState(block.south()).isCollisionShapeFullBlock(entity.level(), block.south()) && !entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && !entity.level().getBlockState(blockPos.south()).isCollisionShapeFullBlock(entity.level(), blockPos.south()) && baseGravityDirection.equals(Direction.SOUTH)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("south to east")));
                        return;
                    }
                    else if (innerMovementEast && shouldAttach) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("north/south to east inner")));
                        return;
                    }
                }


                if (!baseGravityDirection.equals(Direction.WEST)) {
                    BlockPos block = blockPos.west();
                    Grav = Direction.WEST;

                    shouldAttach = movingTowardsAxis.equals("-x");

                    if (baseGravityDirection.equals(Direction.UP)) {
                        shouldAttach = movingTowardsAxis.equals("x");
                    }
                    if (baseGravityDirection.equals(Direction.NORTH)) {
                        shouldAttach = movingTowardsAxis.equals("x");
                        if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && entity.level().getBlockState(blockPos.north()).isCollisionShapeFullBlock(entity.level(), blockPos.north())) {
                            shouldAttach = movingTowardsAxis.equals("-x");
                            innerMovementWest = true;
                        }
                    }
                    if (baseGravityDirection.equals(Direction.SOUTH)) {
                        shouldAttach = movingTowardsAxis.equals("-x");
                        if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && entity.level().getBlockState(blockPos.south()).isCollisionShapeFullBlock(entity.level(), blockPos.south())) {
                            shouldAttach = movingTowardsAxis.equals("x");
                            innerMovementWest = true;
                        }
                    }

                    if (!innerMovementWest && shouldAttach && entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && baseGravityDirection.equals(Direction.DOWN)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("west")));
                        return;
                    }
                    else if (!innerMovementWest && shouldAttach && entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && baseGravityDirection.equals(Direction.UP)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("up to west")));
                        return;
                    }
                    else if (!innerMovementWest && shouldAttach && entity.level().getBlockState(block.north()).isCollisionShapeFullBlock(entity.level(), block.north()) && !entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && !entity.level().getBlockState(blockPos.north()).isCollisionShapeFullBlock(entity.level(), blockPos.north()) && baseGravityDirection.equals(Direction.NORTH)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("north to west")));
                        return;
                    }
                    else if (!innerMovementWest && shouldAttach && entity.level().getBlockState(block.south()).isCollisionShapeFullBlock(entity.level(), block.south()) && !entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && !entity.level().getBlockState(blockPos.south()).isCollisionShapeFullBlock(entity.level(), blockPos.south()) && baseGravityDirection.equals(Direction.SOUTH)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("south to west")));
                        return;
                    }
                    else if (innerMovementWest && shouldAttach) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal(String.valueOf("north/south to west inner")));
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void lastTick(LivingEntity entity, AbilityInstance entry, IPowerHolder holder, boolean enabled) {
        if (enabled) {
            GravityCapabilityImpl comp = GravityChangerAPI.getGravityComponent(entity);
            GravityChangerAPI.setBaseGravityDirection(entity, Direction.DOWN);
            comp.sendSyncPacketToOtherPlayers();
        }
    }

    @Override
    public String getDocumentationDescription() {
        return "Allows the user to move on any surface.";
    }

    public boolean isNearCenterBlock(LivingEntity entity, BlockPos blockPos) {
        return entity.distanceToSqr(blockPos.getCenter()) < 1.0;
    }
}