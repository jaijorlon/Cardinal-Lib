package net.jaijorlon.cardinal.condition;

import com.google.gson.JsonObject;
import net.threetag.palladium.condition.Condition;
import net.threetag.palladium.condition.ConditionSerializer;
import net.threetag.palladium.util.context.DataContext;
import net.threetag.palladium.util.property.PalladiumProperty;
import net.threetag.palladium.util.property.StringProperty;

public class MovingTowardsAxisCondition extends Condition {

    private final String axis;

    public MovingTowardsAxisCondition(String axis) {
        this.axis = axis;
    }

    @Override
    public boolean active(DataContext context) {
        var entity = context.getEntity();

        if (entity != null) {
            return entity.getPersistentData().getString("Cardinal.movingTowardsAxis").equals(axis);
        }

        return false;
    }

    @Override
    public ConditionSerializer getSerializer() {
        return CardinalConditionSerializers.MOVING_TOWARDS_AXIS.get();
    }

    public static class Serializer extends ConditionSerializer {

        public static final PalladiumProperty<String> AXIS = new StringProperty("axis").configurable("What axis you want to test going towards");

        public Serializer() {
            this.withProperty(AXIS, "x");
        }

        @Override
        public Condition make(JsonObject json) {
            return new MovingTowardsAxisCondition(getProperty(json, AXIS));
        }

        @Override
        public String getDocumentationDescription() {
            return "Checks if player inputs move them towards an axis.";
        }
    }
}
