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
            if (entity.level().isClientSide()) return;

            Direction Grav = null;
            BlockPos blockPos = entity.blockPosition();
            Direction baseGravityDirection = GravityChangerAPI.getBaseGravityDirection(entity);
            GravityCapabilityImpl comp = GravityChangerAPI.getGravityComponent(entity);
            boolean shouldAttach = true;
            String movementDirectionHorizontal = "";
            String movementDirectionVertical = "";
            boolean flipHorizontal = (entity.getYRot() > -90 && entity.getYRot() < 90) && !baseGravityDirection.equals(Direction.DOWN);
            boolean flipVertical = (entity.getYRot() > -180 && entity.getYRot() < 0) && !baseGravityDirection.equals(Direction.DOWN);
            boolean innerMovementNorth = false;
            boolean innerMovementSouth = false;
            boolean innerMovementEast = false;
            boolean innerMovementWest = false;

            if (!entity.level().getBlockState(blockPos.offset(1, 0, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(1, 0, 0))) && !entity.level().getBlockState(blockPos.offset(-1, 0, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(-1, 0, 0))) && !entity.level().getBlockState(blockPos.offset(0, 0, 1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(0, 0, 1))) && !entity.level().getBlockState(blockPos.offset(0, 0, -1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(0, 0, -1))) && !entity.level().getBlockState(blockPos.offset(1, 1, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(1, 1, 0))) && !entity.level().getBlockState(blockPos.offset(-1, 1, 0)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(-1, 1, 0))) && !entity.level().getBlockState(blockPos.offset(1, 1, 1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(1, 1, 1))) && !entity.level().getBlockState(blockPos.offset(-1, 1, 1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(-1, 1, 1))) && !entity.level().getBlockState(blockPos.offset(1, 1, -1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(1, 1, -1))) && !entity.level().getBlockState(blockPos.offset(-1, 1, -1)).isCollisionShapeFullBlock(entity.level(), new BlockPos(blockPos.offset(-1, 1, 1)))) {
                Grav = Direction.DOWN;
                GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                comp.sendSyncPacketToOtherPlayers();
            }

            String[] directions = new String[]{"north", "east", "south", "west", "up", "down"};

            for (String direction : directions) {
                if (entity.getPersistentData().getInt(direction + "GravityCooldown") > 0) {
                    entity.getPersistentData().putInt(direction + "GravityCooldown", entity.getPersistentData().getInt(direction + "GravityCooldown") - 1);
                }
            }

            if (!baseGravityDirection.equals(Direction.NORTH)) {
                BlockPos block = blockPos.north();
                Grav = Direction.NORTH;
                BlockPos blockDirectionPos = block.west();

                if (baseGravityDirection.equals(Direction.DOWN) && entity.getDirection().equals(Grav)) {
                    shouldAttach = entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.up");
                }
                else if (!baseGravityDirection.equals(Direction.DOWN)) {
                    shouldAttach = !(entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.left") || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.right") || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.up") || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.down"));
                }

                if (baseGravityDirection.equals(Direction.WEST)) {
                    if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && entity.level().getBlockState(blockPos.west()).isCollisionShapeFullBlock(entity.level(), blockPos.west())) {
                        innerMovementNorth = true;
                    }

                    movementDirectionHorizontal = flipHorizontal ? "right" : "left";
                    movementDirectionVertical = flipVertical ? "down" : "up";

                    if (innerMovementNorth) {
                        if (movementDirectionHorizontal.equals("right")) {
                            movementDirectionHorizontal = "left";
                        }
                        else {
                            movementDirectionHorizontal = "right";
                        }

                        if (movementDirectionVertical.equals("up")) {
                            movementDirectionVertical = "down";
                        }
                        else {
                            movementDirectionVertical = "up";
                        }
                    }
                }
                if (baseGravityDirection.equals( Direction.EAST)) {
                    if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && entity.level().getBlockState(blockPos.east()).isCollisionShapeFullBlock(entity.level(), blockPos.east())) {
                        innerMovementNorth = true;
                    }

                    movementDirectionHorizontal = flipHorizontal ? "left" : "right";
                    movementDirectionVertical = flipVertical ? "up" : "down";
                    blockDirectionPos = block.east();

                    if (innerMovementNorth) {
                        if (movementDirectionHorizontal.equals("right")) {
                            movementDirectionHorizontal = "left";
                        }
                        else {
                            movementDirectionHorizontal = "right";
                        }

                        if (movementDirectionVertical.equals("up")) {
                            movementDirectionVertical = "down";
                        }
                        else {
                            movementDirectionVertical = "up";
                        }
                    }
                }

                if (!entity.level().getBlockState(blockPos.west()).isCollisionShapeFullBlock(entity.level(), blockPos.west()) && !entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && !entity.level().getBlockState(blockDirectionPos.south()).isCollisionShapeFullBlock(entity.level(), blockDirectionPos.south()) && entity.level().getBlockState(blockDirectionPos).isCollisionShapeFullBlock(entity.level(), blockDirectionPos) && (entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionHorizontal) || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionVertical))) {
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                    entity.getPersistentData().putInt("northGravityCooldown", 20);
                    entity.sendSystemMessage(Component.literal(String.valueOf("north")));
                    return;
                }
                else if (innerMovementNorth && isNearCenterBlock(entity, block) && baseGravityDirection.equals(Direction.WEST) && (entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionHorizontal) || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionVertical))) {
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                    entity.sendSystemMessage(Component.literal(String.valueOf("west to north")));
                    return;
                }
                else if (innerMovementNorth && isNearCenterBlock(entity, block) && baseGravityDirection.equals(Direction.EAST) && (entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionHorizontal) || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionVertical))) {
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                    entity.sendSystemMessage(Component.literal(String.valueOf("east to north")));
                    return;
                }
                else if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && shouldAttach && baseGravityDirection.equals(Direction.DOWN)) {
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                    entity.sendSystemMessage(Component.literal(String.valueOf("north2")));
                    return;
                }
            }



            if (!baseGravityDirection.equals(Direction.SOUTH)) {
                BlockPos block = blockPos.south();
                Grav = Direction.SOUTH;
                BlockPos blockDirectionPos = block.east();

                if (baseGravityDirection.equals(Direction.DOWN) && entity.getDirection().equals(Grav)) {
                    shouldAttach = entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.up");
                }
                else if (!baseGravityDirection.equals(Direction.DOWN)) {
                    shouldAttach = !(entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.left") || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.right") || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.up") || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.down"));
                }

                if (baseGravityDirection.equals(Direction.WEST)) {
                    if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && entity.level().getBlockState(blockPos.west()).isCollisionShapeFullBlock(entity.level(), blockPos.west())) {
                        innerMovementSouth = true;
                    }

                    movementDirectionHorizontal = flipHorizontal ? "left" : "right";
                    movementDirectionVertical = flipVertical ? "up" : "down";
                    blockDirectionPos = block.west();

                    if (innerMovementSouth) {
                        if (movementDirectionHorizontal.equals("right")) {
                            movementDirectionHorizontal = "left";
                        }
                        else {
                            movementDirectionHorizontal = "right";
                        }

                        if (movementDirectionVertical.equals("up")) {
                            movementDirectionVertical = "down";
                        }
                        else {
                            movementDirectionVertical = "up";
                        }
                    }
                }
                if (baseGravityDirection.equals(Direction.EAST)) {
                    if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && entity.level().getBlockState(blockPos.east()).isCollisionShapeFullBlock(entity.level(), blockPos.east())) {
                        innerMovementSouth = true;
                    }

                    movementDirectionHorizontal = flipHorizontal ? "right" : "left";
                    movementDirectionVertical = flipVertical ? "down" : "up";

                    if (innerMovementSouth) {
                        if (movementDirectionHorizontal.equals("right")) {
                            movementDirectionHorizontal = "left";
                        }
                        else {
                            movementDirectionHorizontal = "right";
                        }

                        if (movementDirectionVertical.equals("up")) {
                            movementDirectionVertical = "down";
                        }
                        else {
                            movementDirectionVertical = "up";
                        }
                    }
                }

                if (!entity.level().getBlockState(blockPos.east()).isCollisionShapeFullBlock(entity.level(), blockPos.east()) && !entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && !entity.level().getBlockState(blockDirectionPos.north()).isCollisionShapeFullBlock(entity.level(), blockDirectionPos.north()) && entity.level().getBlockState(blockDirectionPos).isCollisionShapeFullBlock(entity.level(), blockDirectionPos) && (entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionHorizontal) || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionVertical))) {
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                    entity.getPersistentData().putInt("southGravityCooldown", 20);
                    entity.sendSystemMessage(Component.literal(String.valueOf("south")));
                    return;
                }
                else if (innerMovementSouth && isNearCenterBlock(entity, block) && baseGravityDirection.equals(Direction.WEST) && (entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionHorizontal) || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionVertical))) {
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                    entity.sendSystemMessage(Component.literal(String.valueOf("west to south")));
                    return;
                }
                else if (innerMovementSouth && isNearCenterBlock(entity, block) && baseGravityDirection.equals(Direction.EAST) && (entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionHorizontal) || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionVertical))) {
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                    entity.sendSystemMessage(Component.literal(String.valueOf("east to south")));
                    return;
                }
                else if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && shouldAttach) {
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                    entity.sendSystemMessage(Component.literal(String.valueOf("south2")));
                    return;
                }
            }



            if (!baseGravityDirection.equals(Direction.EAST)) {
                BlockPos block = blockPos.east();
                Grav = Direction.EAST;
                BlockPos blockDirectionPos = block.north();

                if (baseGravityDirection.equals(Direction.DOWN) && entity.getDirection().equals(Grav)) {
                    shouldAttach = entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.up");
                }
                else if (!baseGravityDirection.equals(Direction.DOWN)) {
                    shouldAttach = !(entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.left") || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.right") || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.up") || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.down"));
                }

                if (baseGravityDirection.equals(Direction.NORTH)) {
                    if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && entity.level().getBlockState(blockPos.north()).isCollisionShapeFullBlock(entity.level(), blockPos.north())) {
                        innerMovementEast = true;
                    }

                    movementDirectionHorizontal = flipHorizontal ? "right" : "left";
                    movementDirectionVertical = flipVertical ? "down" : "up";

                    if (innerMovementEast) {
                        if (movementDirectionHorizontal.equals("right")) {
                            movementDirectionHorizontal = "left";
                        }
                        else {
                            movementDirectionHorizontal = "right";
                        }

                        if (movementDirectionVertical.equals("up")) {
                            movementDirectionVertical = "down";
                        }
                        else {
                            movementDirectionVertical = "up";
                        }
                    }
                }
                if (baseGravityDirection.equals(Direction.SOUTH)) {
                    if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && entity.level().getBlockState(blockPos.south()).isCollisionShapeFullBlock(entity.level(), blockPos.south())) {
                        innerMovementEast = true;
                    }

                    movementDirectionHorizontal = flipHorizontal ? "left" : "right";
                    movementDirectionVertical = flipVertical ? "up" : "down";
                    blockDirectionPos = block.south();

                    if (innerMovementEast) {
                        if (movementDirectionHorizontal.equals("right")) {
                            movementDirectionHorizontal = "left";
                        }
                        else {
                            movementDirectionHorizontal = "right";
                        }

                        if (movementDirectionVertical.equals("up")) {
                            movementDirectionVertical = "down";
                        }
                        else {
                            movementDirectionVertical = "up";
                        }
                    }
                }

                if (!entity.level().getBlockState(blockPos.north()).isCollisionShapeFullBlock(entity.level(), blockPos.north()) && !entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && !entity.level().getBlockState(blockDirectionPos.west()).isCollisionShapeFullBlock(entity.level(), blockDirectionPos.west()) && entity.level().getBlockState(blockDirectionPos).isCollisionShapeFullBlock(entity.level(), blockDirectionPos) && (entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionHorizontal) || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionVertical))) {
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                    entity.getPersistentData().putInt("eastGravityCooldown", 20);
                    entity.sendSystemMessage(Component.literal(String.valueOf("east")));
                    return;
                }
                else if (innerMovementEast && isNearCenterBlock(entity, block) && baseGravityDirection.equals(Direction.NORTH) && (entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionHorizontal) || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionVertical))) {
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                    entity.sendSystemMessage(Component.literal(String.valueOf("north to east")));
                    return;
                }
                else if (innerMovementEast && isNearCenterBlock(entity, block) && baseGravityDirection.equals(Direction.SOUTH) && (entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionHorizontal) || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionVertical))) {
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                    entity.sendSystemMessage(Component.literal(String.valueOf("south to east")));
                    return;
                }
                else if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && shouldAttach) {
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                    entity.sendSystemMessage(Component.literal(String.valueOf("east2")));
                    return;
                }
            }



            if (!baseGravityDirection.equals(Direction.WEST)) {
                BlockPos block = blockPos.west();
                Grav = Direction.WEST;
                BlockPos blockDirectionPos = block.south();

                if (baseGravityDirection.equals(Direction.DOWN) && entity.getDirection().equals(Grav)) {
                    shouldAttach = entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.up");
                }
                else if (!baseGravityDirection.equals(Direction.DOWN)) {
                    shouldAttach = !(entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.left") || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.right") || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.up") || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.down"));
                }

                if (baseGravityDirection.equals(Direction.NORTH)) {
                    if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && entity.level().getBlockState(blockPos.north()).isCollisionShapeFullBlock(entity.level(), blockPos.north())) {
                        innerMovementWest = true;
                    }

                    movementDirectionHorizontal = flipHorizontal ? "left" : "right";
                    movementDirectionVertical = flipVertical ? "up" : "down";
                    blockDirectionPos = block.north();

                    if (innerMovementWest) {
                        if (movementDirectionHorizontal.equals("right")) {
                            movementDirectionHorizontal = "left";
                        }
                        else {
                            movementDirectionHorizontal = "right";
                        }

                        if (movementDirectionVertical.equals("up")) {
                            movementDirectionVertical = "down";
                        }
                        else {
                            movementDirectionVertical = "up";
                        }
                    }
                }
                if (baseGravityDirection.equals(Direction.SOUTH)) {
                    if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && entity.level().getBlockState(blockPos.south()).isCollisionShapeFullBlock(entity.level(), blockPos.south())) {
                        innerMovementWest = true;
                    }

                    movementDirectionHorizontal = flipHorizontal ? "right" : "left";
                    movementDirectionVertical = flipVertical ? "down" : "up";

                    if (innerMovementWest) {
                        if (movementDirectionHorizontal.equals("right")) {
                            movementDirectionHorizontal = "left";
                        }
                        else {
                            movementDirectionHorizontal = "right";
                        }

                        if (movementDirectionVertical.equals("up")) {
                            movementDirectionVertical = "down";
                        }
                        else {
                            movementDirectionVertical = "up";
                        }
                    }
                }

                if (!entity.level().getBlockState(blockPos.south()).isCollisionShapeFullBlock(entity.level(), blockPos.south()) && !entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && !entity.level().getBlockState(blockDirectionPos.east()).isCollisionShapeFullBlock(entity.level(), blockDirectionPos.east()) && entity.level().getBlockState(blockDirectionPos).isCollisionShapeFullBlock(entity.level(), blockDirectionPos) && (entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionHorizontal) || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionVertical))) {
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                    entity.getPersistentData().putInt("westGravityCooldown", 20);
                    entity.sendSystemMessage(Component.literal(String.valueOf("west")));
                }
                else if (innerMovementWest && isNearCenterBlock(entity, block) && baseGravityDirection.equals(Direction.NORTH) && (entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionHorizontal) || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionVertical))) {
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                    entity.sendSystemMessage(Component.literal(String.valueOf("north to west")));
                }
                else if (innerMovementWest && isNearCenterBlock(entity, block) && baseGravityDirection.equals(Direction.SOUTH) && (entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionHorizontal) || entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition."+movementDirectionVertical))) {
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                    entity.sendSystemMessage(Component.literal(String.valueOf("north to west")));
                }
                else if (entity.level().getBlockState(block).isCollisionShapeFullBlock(entity.level(), block) && shouldAttach) {
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                    entity.sendSystemMessage(Component.literal(String.valueOf("west2")));
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

}