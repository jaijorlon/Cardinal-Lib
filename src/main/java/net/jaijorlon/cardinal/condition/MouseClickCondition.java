package net.jaijorlon.cardinal.condition;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.threetag.palladium.condition.Condition;
import net.threetag.palladium.condition.ConditionEnvironment;
import net.threetag.palladium.condition.ConditionSerializer;
import net.threetag.palladium.util.context.DataContext;
import net.threetag.palladium.util.context.DataContextType;
import net.threetag.palladium.util.property.*;

import java.util.Objects;

import net.threetag.palladium.util.property.IntegerProperty;
import net.threetag.palladium.util.property.PalladiumProperty;

public class MouseClickCondition extends MouseCondition {
    private static boolean holdingDownLeft = false;
    private static boolean holdingDownRight = false;

    public MouseClickCondition(int cooldown, String mouseButton) {
        super(cooldown, mouseButton);
    }

    @Override
    public boolean active(DataContext context) {
        var entity = context.getLivingEntity();
        var entry = context.get(DataContextType.ABILITY);

        if (entity == null || entry == null) {
            return false;
        }

        if (entity.level().isClientSide()) {
            Minecraft minecraft = Minecraft.getInstance();

            if (minecraft.mouseHandler.isLeftPressed() && this.mouseButton.equals("left")) {
                if (entry.cooldown == 0 && !holdingDownLeft) {
                    entry.keyPressed = true;
                    holdingDownLeft = true;
                }
            }
            else {
                holdingDownLeft = false;
            }

            if (minecraft.mouseHandler.isRightPressed() && this.mouseButton.equals("right")) {
                if (entry.cooldown == 0 && !holdingDownRight) {
                    entry.keyPressed = true;
                    holdingDownRight = true;
                }
            }
            else {
                holdingDownRight = false;
            }
        }

        if (Objects.requireNonNull(entry).keyPressed) {
            entry.keyPressed = false;
            if (entry.getEnabledTicks() == 0 && this.cooldown != 0) {
                entry.startCooldown(entity, this.cooldown);
            }
            return true;
        }

        return false;
    }

    @Override
    public ConditionSerializer getSerializer() {
        return CardinalConditionSerializers.MOUSE_CLICK.get();
    }

    public static class Serializer extends ConditionSerializer {

        public static final PalladiumProperty<Integer> COOLDOWN = new IntegerProperty("cooldown").configurable("Amount of ticks the ability wont be useable for after using it");
        public static final PalladiumProperty<String> MOUSE_BUTTON = new StringProperty("mouse_button").configurable("What mouse button to check for the player using");

        public Serializer() {
            this.withProperty(COOLDOWN, 0);
            this.withProperty(MOUSE_BUTTON, "left");
        }

        @Override
        public Condition make(JsonObject json) {
            return new MouseClickCondition(this.getProperty(json, COOLDOWN), this.getProperty(json, MOUSE_BUTTON));
        }

        @Override
        public ConditionEnvironment getContextEnvironment() {
            return ConditionEnvironment.DATA;
        }

        @Override
        public String getDocumentationDescription() {
            return "This condition is used to activate the power when a mouse button is clicked.";
        }
    }
}
