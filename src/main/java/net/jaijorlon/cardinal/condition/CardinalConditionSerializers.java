package net.jaijorlon.cardinal.condition;

import net.jaijorlon.cardinal.Cardinal;
import net.threetag.palladium.condition.ConditionSerializer;
import net.threetag.palladiumcore.registry.DeferredRegister;
import net.threetag.palladiumcore.registry.RegistrySupplier;

public class CardinalConditionSerializers {
    public static final DeferredRegister<ConditionSerializer> CONDITION_SERIALIZERS = DeferredRegister.create(Cardinal.MOD_ID, ConditionSerializer.REGISTRY);

    public static final RegistrySupplier<ConditionSerializer> FACING_DIRECTION = CONDITION_SERIALIZERS.register("facing_direction", FacingDirectionCondition.Serializer::new);
}
