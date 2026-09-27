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

public class SurfaceMovementAbility extends Ability {
    @Override
    public void tick(LivingEntity entity, AbilityInstance entry, IPowerHolder holder, boolean enabled) {
        if (enabled) {
            if (!entity.level().isClientSide()) {
                boolean devMode = false;
                Direction Grav = null;
                BlockPos blockPos = entity.blockPosition();
                Direction baseGravityDirection = GravityChangerAPI.getBaseGravityDirection(entity);
                Direction prevGravityDirection = GravityChangerAPI.getPrevGravityDirection(entity);
                GravityCapabilityImpl comp = GravityChangerAPI.getGravityComponent(entity);
                String movingTowardsAxis = entity.getPersistentData().getString("Cardinal.movingTowardsAxis");

                boolean shouldAttach = true;
                boolean innerMovementNorth = false, innerMovementSouth = false, innerMovementEast = false, innerMovementWest = false;

                if (prevGravityDirection.equals(Direction.UP)) {
                    if (
                            !isCollisionShapeFullBlock(entity, blockPos.north().above())
                                    && !isCollisionShapeFullBlock(entity, blockPos.south().above())
                                    && !isCollisionShapeFullBlock(entity, blockPos.east().above())
                                    && !isCollisionShapeFullBlock(entity, blockPos.west().above())
                                    && !isCollisionShapeFullBlock(entity, blockPos.north().above().east())
                                    && !isCollisionShapeFullBlock(entity, blockPos.north().above().west())
                                    && !isCollisionShapeFullBlock(entity, blockPos.south().above().east())
                                    && !isCollisionShapeFullBlock(entity, blockPos.south().above().west())
                                    && !isCollisionShapeFullBlock(entity, blockPos.east().above().north())
                                    && !isCollisionShapeFullBlock(entity, blockPos.east().above().south())
                                    && !isCollisionShapeFullBlock(entity, blockPos.west().above().north())
                                    && !isCollisionShapeFullBlock(entity, blockPos.west().above().south())
                                    && !isCollisionShapeFullBlock(entity, blockPos.above(1).north())
                                    && !isCollisionShapeFullBlock(entity, blockPos.above(1).south())
                                    && !isCollisionShapeFullBlock(entity, blockPos.above(1).east())
                                    && !isCollisionShapeFullBlock(entity, blockPos.above(1).west())
                                    && !isCollisionShapeFullBlock(entity, blockPos.above(1).north().east())
                                    && !isCollisionShapeFullBlock(entity, blockPos.above(1).north().west())
                                    && !isCollisionShapeFullBlock(entity, blockPos.above(1).south().east())
                                    && !isCollisionShapeFullBlock(entity, blockPos.above(1).south().west())
                                    && !isCollisionShapeFullBlock(entity, blockPos.above(1).east().north())
                                    && !isCollisionShapeFullBlock(entity, blockPos.above(1).east().south())
                                    && !isCollisionShapeFullBlock(entity, blockPos.above(1).west().north())
                                    && !isCollisionShapeFullBlock(entity, blockPos.above(1).west().south())
                    ) {
                        if (!isCollisionShapeFullBlock(entity, blockPos.above(1))) {
                            Grav = Direction.DOWN;
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                        }
                    }
                } else if ((movingTowardsAxis.equals("z") || movingTowardsAxis.equals("-z")) ||
                        !isCollisionShapeFullBlock(entity, blockPos.north())
                        && !isCollisionShapeFullBlock(entity, blockPos.south())
                        && !isCollisionShapeFullBlock(entity, blockPos.east())
                        && !isCollisionShapeFullBlock(entity, blockPos.west())
                        && !isCollisionShapeFullBlock(entity, blockPos.north().east())
                        && !isCollisionShapeFullBlock(entity, blockPos.north().west())
                        && !isCollisionShapeFullBlock(entity, blockPos.south().east())
                        && !isCollisionShapeFullBlock(entity, blockPos.south().west())
                        && !isCollisionShapeFullBlock(entity, blockPos.east().north())
                        && !isCollisionShapeFullBlock(entity, blockPos.east().south())
                        && !isCollisionShapeFullBlock(entity, blockPos.west().north())
                        && !isCollisionShapeFullBlock(entity, blockPos.west().south())
                ) {
                    if (!baseGravityDirection.equals(Direction.DOWN) &&
                            (movingTowardsAxis.equals("z") || movingTowardsAxis.equals("-z")) &&
                            !isCollisionShapeFullBlock(entity, blockPos.north())
                            && !isCollisionShapeFullBlock(entity, blockPos.south())
                            && !isCollisionShapeFullBlock(entity, blockPos.east())
                            && !isCollisionShapeFullBlock(entity, blockPos.west())
                    ) {
                        Grav = Direction.DOWN;
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                    }
                    else if ((!movingTowardsAxis.equals("z") && !movingTowardsAxis.equals("-z")) && !baseGravityDirection.equals(Direction.UP) && !prevGravityDirection.equals(Direction.DOWN)) {
                        Grav = Direction.DOWN;
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                    }
                }

                if (!baseGravityDirection.equals(Direction.DOWN) && entity.getPersistentData().getBoolean("Cardinal.HasInputKeyCondition.jump")) {
                    Grav = Direction.DOWN;
                    GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                    comp.sendSyncPacketToOtherPlayers();
                }


                if (!baseGravityDirection.equals(Direction.UP)) {
                    BlockPos block = blockPos.above(2);
                    BlockPos blockSide = blockPos.north();
                    Grav = Direction.UP;
                    Direction onDirection = Direction.DOWN;

                    if (baseGravityDirection.equals(Direction.NORTH)) {
                        shouldAttach = movingTowardsAxis.equals("z");
                        if (isCollisionShapeFullBlock(entity, blockPos.above(1))) {
                            shouldAttach = movingTowardsAxis.equals("-z");
                        }
                        block = blockPos.above();
                        onDirection = Direction.NORTH;
                    }
                    if (baseGravityDirection.equals(Direction.SOUTH)) {
                        shouldAttach = movingTowardsAxis.equals("z");
                        if (isCollisionShapeFullBlock(entity, blockPos.above(1))) {
                            shouldAttach = movingTowardsAxis.equals("-z");
                        }
                        block = blockPos.above();
                        blockSide = blockPos.south();
                        onDirection = Direction.SOUTH;
                    } else if (baseGravityDirection.equals(Direction.EAST)) {
                        shouldAttach = movingTowardsAxis.equals("z");
                        if (isCollisionShapeFullBlock(entity, blockPos.above(1))) {
                            shouldAttach = movingTowardsAxis.equals("-z");
                        }
                        block = blockPos.above();
                        blockSide = blockPos.east();
                        onDirection = Direction.EAST;
                    } else if (baseGravityDirection.equals(Direction.WEST)) {
                        shouldAttach = movingTowardsAxis.equals("z");
                        if (isCollisionShapeFullBlock(entity, blockPos.above(1))) {
                            shouldAttach = movingTowardsAxis.equals("-z");
                        }
                        block = blockPos.above();
                        blockSide = blockPos.west();
                        onDirection = Direction.WEST;
                    }

                    if (shouldAttach && isCollisionShapeFullBlock(entity, block.relative(onDirection)) && !isCollisionShapeFullBlock(entity, blockPos.relative(onDirection))) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal("up"));
                        return;
                    }
                    else if (shouldAttach && isCollisionShapeFullBlock(entity, block) && baseGravityDirection.equals(Direction.DOWN)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal("up"));
                        return;
                    } else if (shouldAttach && entity.level().getBlockState(blockSide).isCollisionShapeFullBlock(entity.level(), blockSide) && isCollisionShapeFullBlock(entity, block)) {
                        GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                        comp.sendSyncPacketToOtherPlayers();
                        if (devMode) entity.sendSystemMessage(Component.literal("up inner"));
                        return;
                    }
                }

                if (!baseGravityDirection.equals(Direction.NORTH)) {
                    BlockPos block = blockPos.north();
                    Grav = Direction.NORTH;

                    if (!baseGravityDirection.equals(Direction.UP)) {
                        shouldAttach = movingTowardsAxis.equals("-z");

                        if (baseGravityDirection.equals(Direction.WEST)) {
                            shouldAttach = movingTowardsAxis.equals("-x");
                            if (isCollisionShapeFullBlock(entity, block) && entity.level().getBlockState(blockPos.west()).isCollisionShapeFullBlock(entity.level(), blockPos.west())) {
                                shouldAttach = movingTowardsAxis.equals("x");
                                innerMovementNorth = true;
                            }
                        }
                        if (baseGravityDirection.equals(Direction.EAST)) {
                            shouldAttach = movingTowardsAxis.equals("x");
                            if (isCollisionShapeFullBlock(entity, block) && entity.level().getBlockState(blockPos.east()).isCollisionShapeFullBlock(entity.level(), blockPos.east())) {
                                shouldAttach = movingTowardsAxis.equals("-x");
                                innerMovementNorth = true;
                            }
                        }

                        if (!innerMovementNorth && shouldAttach && isCollisionShapeFullBlock(entity, block) && baseGravityDirection.equals(Direction.DOWN)) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode) entity.sendSystemMessage(Component.literal("north"));
                            return;
                        } else if (!innerMovementNorth && shouldAttach && entity.level().getBlockState(block.east()).isCollisionShapeFullBlock(entity.level(), block.east()) && !isCollisionShapeFullBlock(entity, block) && !entity.level().getBlockState(blockPos.east()).isCollisionShapeFullBlock(entity.level(), blockPos.east()) && baseGravityDirection.equals(Direction.EAST)) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode) entity.sendSystemMessage(Component.literal("east to north"));
                            return;
                        } else if (!innerMovementNorth && shouldAttach && entity.level().getBlockState(block.west()).isCollisionShapeFullBlock(entity.level(), block.west()) && !isCollisionShapeFullBlock(entity, block) && !entity.level().getBlockState(blockPos.west()).isCollisionShapeFullBlock(entity.level(), blockPos.west()) && baseGravityDirection.equals(Direction.WEST)) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode) entity.sendSystemMessage(Component.literal("west to north"));
                            return;
                        } else if (innerMovementNorth && shouldAttach) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode)
                                entity.sendSystemMessage(Component.literal("west/east to north inner"));
                            return;
                        }
                    } else {
                        shouldAttach = movingTowardsAxis.equals("z");

                        if (isCollisionShapeFullBlock(entity, block) && entity.level().getBlockState(blockPos.above()).isCollisionShapeFullBlock(entity.level(), blockPos.above())) {
                            shouldAttach = movingTowardsAxis.equals("-z");
                        }

                        if (shouldAttach && entity.level().getBlockState(block.above()).isCollisionShapeFullBlock(entity.level(), block.above()) && !isCollisionShapeFullBlock(entity, block) && !entity.level().getBlockState(blockPos.above()).isCollisionShapeFullBlock(entity.level(), blockPos.above())) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode) entity.sendSystemMessage(Component.literal("up to north"));
                            return;
                        } else if (shouldAttach && isCollisionShapeFullBlock(entity, block)) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode)
                                entity.sendSystemMessage(Component.literal("up to north inner"));
                            return;
                        }
                    }
                }


                if (!baseGravityDirection.equals(Direction.SOUTH)) {
                    BlockPos block = blockPos.south();
                    Grav = Direction.SOUTH;

                    if (!baseGravityDirection.equals(Direction.UP)) {
                        shouldAttach = movingTowardsAxis.equals("z");

                        if (baseGravityDirection.equals(Direction.WEST)) {
                            shouldAttach = movingTowardsAxis.equals("x");
                            if (isCollisionShapeFullBlock(entity, block) && entity.level().getBlockState(blockPos.west()).isCollisionShapeFullBlock(entity.level(), blockPos.west())) {
                                shouldAttach = movingTowardsAxis.equals("-x");
                                innerMovementSouth = true;
                            }
                        }
                        if (baseGravityDirection.equals(Direction.EAST)) {
                            shouldAttach = movingTowardsAxis.equals("-x");
                            if (isCollisionShapeFullBlock(entity, block) && entity.level().getBlockState(blockPos.east()).isCollisionShapeFullBlock(entity.level(), blockPos.east())) {
                                shouldAttach = movingTowardsAxis.equals("x");
                                innerMovementSouth = true;
                            }
                        }

                        if (!innerMovementSouth && shouldAttach && isCollisionShapeFullBlock(entity, block) && baseGravityDirection.equals(Direction.DOWN)) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode) entity.sendSystemMessage(Component.literal("south"));
                            return;
                        } else if (!innerMovementSouth && shouldAttach && entity.level().getBlockState(block.east()).isCollisionShapeFullBlock(entity.level(), block.east()) && !isCollisionShapeFullBlock(entity, block) && !entity.level().getBlockState(blockPos.east()).isCollisionShapeFullBlock(entity.level(), blockPos.east()) && baseGravityDirection.equals(Direction.EAST)) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode) entity.sendSystemMessage(Component.literal("east to south"));
                            return;
                        } else if (!innerMovementSouth && shouldAttach && entity.level().getBlockState(block.west()).isCollisionShapeFullBlock(entity.level(), block.west()) && !isCollisionShapeFullBlock(entity, block) && !entity.level().getBlockState(blockPos.west()).isCollisionShapeFullBlock(entity.level(), blockPos.west()) && baseGravityDirection.equals(Direction.WEST)) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode) entity.sendSystemMessage(Component.literal("west to south"));
                            return;
                        } else if (innerMovementSouth && shouldAttach) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode)
                                entity.sendSystemMessage(Component.literal("west/east to south inner"));
                            return;
                        }
                    } else {
                        shouldAttach = movingTowardsAxis.equals("-z");

                        if (isCollisionShapeFullBlock(entity, block) && entity.level().getBlockState(blockPos.above()).isCollisionShapeFullBlock(entity.level(), blockPos.above())) {
                            shouldAttach = movingTowardsAxis.equals("z");
                        }

                        if (shouldAttach && entity.level().getBlockState(block.above()).isCollisionShapeFullBlock(entity.level(), block.above()) && !isCollisionShapeFullBlock(entity, block) && !entity.level().getBlockState(blockPos.above()).isCollisionShapeFullBlock(entity.level(), blockPos.above())) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode) entity.sendSystemMessage(Component.literal("up to south"));
                            return;
                        } else if (shouldAttach && isCollisionShapeFullBlock(entity, block)) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode)
                                entity.sendSystemMessage(Component.literal("up to south inner"));
                            return;
                        }
                    }
                }


                if (!baseGravityDirection.equals(Direction.EAST)) {
                    BlockPos block = blockPos.east();
                    Grav = Direction.EAST;

                    if (!baseGravityDirection.equals(Direction.UP)) {
                        shouldAttach = movingTowardsAxis.equals("x");

                        if (baseGravityDirection.equals(Direction.NORTH)) {
                            shouldAttach = movingTowardsAxis.equals("-x");
                            if (isCollisionShapeFullBlock(entity, block) && entity.level().getBlockState(blockPos.north()).isCollisionShapeFullBlock(entity.level(), blockPos.north())) {
                                shouldAttach = movingTowardsAxis.equals("x");
                                innerMovementEast = true;
                            }
                        }
                        if (baseGravityDirection.equals(Direction.SOUTH)) {
                            shouldAttach = movingTowardsAxis.equals("x");
                            if (isCollisionShapeFullBlock(entity, block) && entity.level().getBlockState(blockPos.south()).isCollisionShapeFullBlock(entity.level(), blockPos.south())) {
                                shouldAttach = movingTowardsAxis.equals("-x");
                                innerMovementEast = true;
                            }
                        }


                        if (!innerMovementEast && shouldAttach && isCollisionShapeFullBlock(entity, block) && baseGravityDirection.equals(Direction.DOWN)) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode) entity.sendSystemMessage(Component.literal("east"));
                            return;
                        } else if (!innerMovementEast && shouldAttach && entity.level().getBlockState(block.north()).isCollisionShapeFullBlock(entity.level(), block.north()) && !isCollisionShapeFullBlock(entity, block) && !entity.level().getBlockState(blockPos.north()).isCollisionShapeFullBlock(entity.level(), blockPos.north()) && baseGravityDirection.equals(Direction.NORTH)) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode) entity.sendSystemMessage(Component.literal("north to east"));
                            return;
                        } else if (!innerMovementEast && shouldAttach && entity.level().getBlockState(block.south()).isCollisionShapeFullBlock(entity.level(), block.south()) && !isCollisionShapeFullBlock(entity, block) && !entity.level().getBlockState(blockPos.south()).isCollisionShapeFullBlock(entity.level(), blockPos.south()) && baseGravityDirection.equals(Direction.SOUTH)) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode) entity.sendSystemMessage(Component.literal("south to east"));
                            return;
                        } else if (innerMovementEast && shouldAttach) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode)
                                entity.sendSystemMessage(Component.literal("north/south to east inner"));
                            return;
                        }
                    } else {
                        shouldAttach = movingTowardsAxis.equals("x");

                        if (isCollisionShapeFullBlock(entity, block) && entity.level().getBlockState(blockPos.above()).isCollisionShapeFullBlock(entity.level(), blockPos.above())) {
                            shouldAttach = movingTowardsAxis.equals("-x");
                        }

                        if (shouldAttach && entity.level().getBlockState(block.above()).isCollisionShapeFullBlock(entity.level(), block.above()) && !isCollisionShapeFullBlock(entity, block) && !entity.level().getBlockState(blockPos.above()).isCollisionShapeFullBlock(entity.level(), blockPos.above())) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode) entity.sendSystemMessage(Component.literal("up to east"));
                            return;
                        } else if (shouldAttach && isCollisionShapeFullBlock(entity, block)) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode)
                                entity.sendSystemMessage(Component.literal("up to east inner"));
                            return;
                        }
                    }
                }


                if (!baseGravityDirection.equals(Direction.WEST)) {
                    BlockPos block = blockPos.west();
                    Grav = Direction.WEST;

                    if (!baseGravityDirection.equals(Direction.UP)) {
                        shouldAttach = movingTowardsAxis.equals("-x");

                        if (baseGravityDirection.equals(Direction.NORTH)) {
                            shouldAttach = movingTowardsAxis.equals("x");
                            if (isCollisionShapeFullBlock(entity, block) && entity.level().getBlockState(blockPos.north()).isCollisionShapeFullBlock(entity.level(), blockPos.north())) {
                                shouldAttach = movingTowardsAxis.equals("-x");
                                innerMovementWest = true;
                            }
                        }
                        if (baseGravityDirection.equals(Direction.SOUTH)) {
                            shouldAttach = movingTowardsAxis.equals("-x");
                            if (isCollisionShapeFullBlock(entity, block) && entity.level().getBlockState(blockPos.south()).isCollisionShapeFullBlock(entity.level(), blockPos.south())) {
                                shouldAttach = movingTowardsAxis.equals("x");
                                innerMovementWest = true;
                            }
                        }

                        if (!innerMovementWest && shouldAttach && isCollisionShapeFullBlock(entity, block) && baseGravityDirection.equals(Direction.DOWN)) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode) entity.sendSystemMessage(Component.literal("west"));
                        } else if (!innerMovementWest && shouldAttach && entity.level().getBlockState(block.north()).isCollisionShapeFullBlock(entity.level(), block.north()) && !isCollisionShapeFullBlock(entity, block) && !entity.level().getBlockState(blockPos.north()).isCollisionShapeFullBlock(entity.level(), blockPos.north()) && baseGravityDirection.equals(Direction.NORTH)) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode) entity.sendSystemMessage(Component.literal("north to west"));
                        } else if (!innerMovementWest && shouldAttach && entity.level().getBlockState(block.south()).isCollisionShapeFullBlock(entity.level(), block.south()) && !isCollisionShapeFullBlock(entity, block) && !entity.level().getBlockState(blockPos.south()).isCollisionShapeFullBlock(entity.level(), blockPos.south()) && baseGravityDirection.equals(Direction.SOUTH)) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode) entity.sendSystemMessage(Component.literal("south to west"));
                        } else if (innerMovementWest && shouldAttach) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode)
                                entity.sendSystemMessage(Component.literal("north/south to west inner"));
                        }
                    } else {
                        shouldAttach = movingTowardsAxis.equals("-x");

                        if (isCollisionShapeFullBlock(entity, block) && entity.level().getBlockState(blockPos.above()).isCollisionShapeFullBlock(entity.level(), blockPos.above())) {
                            shouldAttach = movingTowardsAxis.equals("x");
                        }

                        if (shouldAttach && entity.level().getBlockState(block.above()).isCollisionShapeFullBlock(entity.level(), block.above()) && !isCollisionShapeFullBlock(entity, block) && !entity.level().getBlockState(blockPos.above()).isCollisionShapeFullBlock(entity.level(), blockPos.above())) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode) entity.sendSystemMessage(Component.literal("up to west"));
                        } else if (shouldAttach && isCollisionShapeFullBlock(entity, block)) {
                            GravityChangerAPI.setBaseGravityDirection(entity, Grav);
                            comp.sendSyncPacketToOtherPlayers();
                            if (devMode)
                                entity.sendSystemMessage(Component.literal("up to west inner"));
                        }
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

    public boolean isCollisionShapeFullBlock(LivingEntity entity, BlockPos blockPos) {
        return entity.level().getBlockState(blockPos).isCollisionShapeFullBlock(entity.level(), blockPos);
    }
}