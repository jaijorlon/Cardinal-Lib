package net.jaijorlon.cardinal.condition;

import com.google.gson.JsonObject;
import net.threetag.palladium.condition.Condition;
import net.threetag.palladium.condition.ConditionSerializer;
import net.threetag.palladium.util.context.DataContext;
import net.threetag.palladium.util.property.PalladiumProperty;
import net.threetag.palladium.util.property.StringArrayProperty;

import java.util.Arrays;

public class HasInputKeyCondition extends Condition {

    private final String[] inputKeys;

    public HasInputKeyCondition(String[] inputKeys) {
        this.inputKeys = inputKeys;
    }

    @Override
    public boolean active(DataContext context) {
        var entity = context.getEntity();

        if (entity != null) {
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.up")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("forward") || s.equalsIgnoreCase("up"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.down")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("down") || s.equalsIgnoreCase("backward"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.left")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("left"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.right")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("right"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.jump")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("jump"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.sprint")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("sprint"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.use")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("use"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.attack")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("attack"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.shift")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("shift"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.advancements")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("advancements"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.chat")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("chat"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.command")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("command"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.advancements")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("drop"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.fullscreen")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("fullscreen"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.inventory")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("inventory"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.pick_item")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("pick_item"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.swap_offhand")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("swap_offhand"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.toggle_perspective")) {
                if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("toggle_perspective"))) {
                    return true;
                }
            }
            if (entity.getPersistentData().getBoolean("Untapped.MouseClickCondition.smooth_camera")) {
                return Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("smooth_camera"));
            }
        }

        return false;
    }

    @Override
    public ConditionSerializer getSerializer() {
        return CardinalConditionSerializers.HAS_INPUT_KEY.get();
    }

    public static class Serializer extends ConditionSerializer {

        public static final PalladiumProperty<String[]> INPUT_KEYS = new StringArrayProperty("inputKeys").configurable("Keys to test for player input");

        public Serializer() {
            this.withProperty(INPUT_KEYS, new String[]{"jump", "forward"});
        }

        @Override
        public Condition make(JsonObject json) {
            return new HasInputKeyCondition(getProperty(json, INPUT_KEYS));
        }

        @Override
        public String getDocumentationDescription() {
            return "Checks what keys are being pressed by the player.";
        }
    }
}
