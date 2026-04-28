package net.jaijorlon.cardinal.condition;

import com.google.gson.JsonObject;
import net.threetag.palladium.condition.Condition;
import net.threetag.palladium.condition.ConditionSerializer;
import net.threetag.palladium.util.context.DataContext;

public class HasPassengerCondition extends Condition {
    @Override
    public boolean active(DataContext context) {
        var entity = context.getEntity();

        if (entity != null) {
            return !entity.getPassengers().isEmpty();
        }

        return false;
    }

    @Override
    public ConditionSerializer getSerializer() {
        return CardinalConditionSerializers.HAS_PASSENGER.get();
    }

    public static class Serializer extends ConditionSerializer {
        @Override
        public Condition make(JsonObject json) {
            return new HasPassengerCondition();
        }

        @Override
        public String getDocumentationDescription() {
            return "Checks if the entity has passengers.";
        }
    }
}
