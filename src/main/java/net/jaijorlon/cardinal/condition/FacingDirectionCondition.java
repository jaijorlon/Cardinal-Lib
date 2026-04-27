package net.jaijorlon.cardinal.condition;

import com.google.gson.JsonObject;
import com.sun.jna.StringArray;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.threetag.palladium.condition.Condition;
import net.threetag.palladium.condition.ConditionSerializer;
import net.threetag.palladium.util.context.DataContext;
import net.threetag.palladium.util.property.*;

import java.util.Arrays;

public class FacingDirectionCondition extends Condition {

    private final String[] directions;
    private final boolean ignoreVertical;

    public FacingDirectionCondition(String[] directions, boolean ignoreVertical) {
        this.directions = directions;
        this.ignoreVertical = ignoreVertical;
    }

    @Override
    public boolean active(DataContext context) {
        var entity = context.getEntity();

        if (entity != null) {
            if (this.ignoreVertical) {
                return Arrays.stream(this.directions).anyMatch(s -> s.equalsIgnoreCase(entity.getDirection().getName()));
            }
            return Arrays.stream(this.directions).anyMatch(s -> s.equalsIgnoreCase(getFacing(entity).getName()));
        }

        return false;
    }

    @Override
    public ConditionSerializer getSerializer() {
        return CardinalConditionSerializers.FACING_DIRECTION.get();
    }

    public static class Serializer extends ConditionSerializer {

        public static final PalladiumProperty<String[]> DIRECTIONS = new StringArrayProperty("directions").configurable("What directions you want to look for");
        public static final PalladiumProperty<Boolean> IGNOREVERTICAL = new BooleanProperty("ignoreVertical").configurable("What directions you want to look for");

        public Serializer() {
            this.withProperty(DIRECTIONS, new String[]{"up", "down"});
            this.withProperty(IGNOREVERTICAL, false);
        }

        @Override
        public Condition make(JsonObject json) {
            return new FacingDirectionCondition(getProperty(json, DIRECTIONS), getProperty(json, IGNOREVERTICAL));
        }

        @Override
        public String getDocumentationDescription() {
            return "Checks what direction the entity is looking at.";
        }
    }

    private Direction getFacing(Entity entity) {
        if (entity.getXRot() > 45F) {
            return Direction.DOWN;
        } else if (entity.getXRot() < -45F) {
            return Direction.UP;
        }

        return entity.getDirection();
    }
}
