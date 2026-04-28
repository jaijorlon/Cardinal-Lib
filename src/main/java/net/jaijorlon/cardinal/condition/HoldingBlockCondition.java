package net.jaijorlon.cardinal.condition;

import com.google.gson.JsonObject;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.threetag.palladium.condition.Condition;
import net.threetag.palladium.condition.ConditionSerializer;
import net.threetag.palladium.util.context.DataContext;
import net.threetag.palladium.util.property.PalladiumProperty;
import net.threetag.palladium.util.property.StringProperty;

public class HoldingBlockCondition extends Condition {

    private final String hand;

    public HoldingBlockCondition(String hand) {
        this.hand = hand;
    }

    @Override
    public boolean active(DataContext context) {
        var entity = context.getEntity();

        if (entity != null) {
            if (entity instanceof LivingEntity livingEntity) {
                switch (hand) {
                    case "main" -> {
                        return livingEntity.getMainHandItem().getItem() instanceof BlockItem;
                    }
                    case "off" -> {
                        return livingEntity.getOffhandItem().getItem() instanceof BlockItem;
                    }
                    case "both" -> {
                        return livingEntity.getMainHandItem().getItem() instanceof BlockItem || livingEntity.getOffhandItem().getItem() instanceof BlockItem;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public ConditionSerializer getSerializer() {
        return CardinalConditionSerializers.HOLDING_BLOCK.get();
    }

    public static class Serializer extends ConditionSerializer {

        public static final PalladiumProperty<String> HAND = new StringProperty("hand").configurable("What hand to check for block");

        public Serializer() {
            this.withProperty(HAND, "main");
        }

        @Override
        public Condition make(JsonObject json) {
            return new HoldingBlockCondition(getProperty(json, HAND));
        }

        @Override
        public String getDocumentationDescription() {
            return "Checks if the entity is holding a block in a certain hand.";
        }
    }
}
