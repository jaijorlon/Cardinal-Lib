package net.jaijorlon.cardinal.util;

import com.mojang.brigadier.context.CommandContext;
import net.jaijorlon.cardinal.command.LocalDirection;
import net.minecraft.core.Direction;

public class GravityUtil {

    public static Direction getDirection(String direction) {
        String s = direction.toLowerCase();
        return switch (s) {
            case "up", "u" -> Direction.UP;
            case "down", "d" -> Direction.DOWN;
            case "north", "n" -> Direction.NORTH;
            case "south", "s" -> Direction.SOUTH;
            case "east", "e" -> Direction.EAST;
            case "west", "w" -> Direction.WEST;
            default -> null;
        };
    }

    public static LocalDirection getLocalDirection(String direction) {
        String s = direction.toLowerCase();
        return switch (s) {
            case "forward" -> LocalDirection.FORWARD;
            case "backward" -> LocalDirection.BACKWARD;
            case "left" -> LocalDirection.LEFT;
            case "right" -> LocalDirection.RIGHT;
            case "up" -> LocalDirection.UP;
            case "down" -> LocalDirection.DOWN;
            default -> null;
        };
    }

}
