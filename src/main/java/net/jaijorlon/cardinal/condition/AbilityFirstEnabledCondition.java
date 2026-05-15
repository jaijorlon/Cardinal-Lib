package net.jaijorlon.cardinal.condition;

import com.google.gson.JsonObject;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.threetag.palladium.condition.Condition;
import net.threetag.palladium.condition.ConditionSerializer;
import net.threetag.palladium.power.ability.AbilityInstance;
import net.threetag.palladium.power.ability.AbilityReference;
import net.threetag.palladium.util.context.DataContext;
import net.threetag.palladium.util.property.*;

import java.util.Arrays;

public class AbilityFirstEnabledCondition extends Condition {
    private final AbilityReference ability;
    private boolean wasEnabled;

    public AbilityFirstEnabledCondition(AbilityReference ability) {
        this.ability = ability;
    }

    @Override
    public boolean active(DataContext context) {
        var entity = context.getLivingEntity();
        var holder = context.getPowerHolder();

        if (entity == null) {
            return false;
        }

        AbilityInstance dependency = this.ability.getEntry(entity, holder);
        if (dependency != null) {
            if (dependency.isEnabled()) {
                if (!wasEnabled) {
                    wasEnabled = true;
                    return true;
                }
            }
            else if (wasEnabled) {
                wasEnabled = false;
            }
        }

        return false;
    }

    @Override
    public ConditionSerializer getSerializer() {
        return CardinalConditionSerializers.ABILITY_FIRST_ENABLED.get();
    }

    public static class Serializer extends ConditionSerializer {
        public static final PalladiumProperty<ResourceLocation> POWER = new ResourceLocationProperty("power").configurable("ID of the power where is the desired ability is located. Can be null IF used for abilities, then it will look into the current power");
        public static final PalladiumProperty<String> ABILITY = new StringProperty("ability").configurable("ID of the desired ability");

        @Override
        public String getDocumentationDescription() {
            return "Checks if the ability was enabled. If the power is not null, it will look for the ability in the specified power. If the power is null, it will look for the ability in the current power.";
        }

        public Serializer() {
            this.withProperty(POWER, null);
            this.withProperty(ABILITY, "ability_id");
        }

        @Override
        public Condition make(JsonObject json) {
            AbilityReference abilityReference = AbilityReference.fromString(this.getProperty(json, ABILITY));

            if (this.getProperty(json, POWER) != null) {
                abilityReference = new AbilityReference(this.getProperty(json, POWER), this.getProperty(json, ABILITY));
            }

            return new AbilityFirstEnabledCondition(abilityReference);
        }

    }
}
