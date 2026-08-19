package net.jaijorlon.cardinal.condition;

import com.google.gson.JsonObject;
import net.threetag.palladium.condition.Condition;
import net.threetag.palladium.condition.ConditionSerializer;
import net.threetag.palladium.util.context.DataContext;

public class IsPassengerCondition extends Condition {
    @Override
    public boolean active(DataContext context) {
        var entity = context.getEntity();

        if (entity != null) {
            return entity.isPassenger();
        }

        return false;
    }

    @Override
    public ConditionSerializer getSerializer() {
        return CardinalConditionSerializers.IS_PASSENGER.get();
    }

    public static class Serializer extends ConditionSerializer {
        @Override
        public Condition make(JsonObject json) {
            return new IsPassengerCondition();
        }

        @Override
        public String getDocumentationDescription() {
            return "Checks if the entity is a passenger.";
        }
    }
}
