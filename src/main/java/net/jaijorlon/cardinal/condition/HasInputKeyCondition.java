package net.jaijorlon.cardinal.condition;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
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
            if (entity.level.isClientSide) {
                Minecraft minecraft = Minecraft.getInstance();

                if (minecraft.options.keyUp.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("forward") || s.equalsIgnoreCase("up"))) {
                        return true;
                    }
                }
                if (minecraft.options.keyDown.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("down") || s.equalsIgnoreCase("backward"))) {
                        return true;
                    }
                }
                if (minecraft.options.keyLeft.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("left"))) {
                        return true;
                    }
                }
                if (minecraft.options.keyRight.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("right"))) {
                        return true;
                    }
                }
                if (minecraft.options.keyJump.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("jump"))) {
                        return true;
                    }
                }
                if (minecraft.options.keySprint.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("sprint"))) {
                        return true;
                    }
                }
                if (minecraft.options.keyUse.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("use"))) {
                        return true;
                    }
                }
                if (minecraft.options.keyAttack.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("attack"))) {
                        return true;
                    }
                }
                if (minecraft.options.keyShift.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("shift"))) {
                        return true;
                    }
                }
                if (minecraft.options.keyAdvancements.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("advancements"))) {
                        return true;
                    }
                }
                if (minecraft.options.keyChat.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("chat"))) {
                        return true;
                    }
                }
                if (minecraft.options.keyCommand.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("command"))) {
                        return true;
                    }
                }
                if (minecraft.options.keyDrop.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("drop"))) {
                        return true;
                    }
                }
                if (minecraft.options.keyFullscreen.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("fullscreen"))) {
                        return true;
                    }
                }
                if (minecraft.options.keyInventory.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("inventory"))) {
                        return true;
                    }
                }
                if (minecraft.options.keyPickItem.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("pick_item"))) {
                        return true;
                    }
                }
                if (minecraft.options.keySwapOffhand.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("swap_offhand"))) {
                        return true;
                    }
                }
                if (minecraft.options.keyTogglePerspective.isDown()) {
                    if (Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("toggle_perspective"))) {
                        return true;
                    }
                }
                if (minecraft.options.keySmoothCamera.isDown()) {
                    return Arrays.stream(this.inputKeys).anyMatch(s -> s.equalsIgnoreCase("smooth_camera"));
                }
            }
        }

        return false;
    }

    @Override
    public ConditionSerializer getSerializer() {
        return CardinalConditionSerializers.HAS_INPUT_KEY.get();
    }

    public static class Serializer extends ConditionSerializer {

        public static final PalladiumProperty<String[]> INPUT_KEYS = new StringArrayProperty("inputKeys").configurable("Keys to test for input");

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
