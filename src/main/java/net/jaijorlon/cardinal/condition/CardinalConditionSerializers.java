package net.jaijorlon.cardinal.condition;

import net.jaijorlon.cardinal.Cardinal;
import net.threetag.palladium.condition.ConditionSerializer;
import net.threetag.palladiumcore.registry.DeferredRegister;
import net.threetag.palladiumcore.registry.RegistrySupplier;

public class CardinalConditionSerializers {
    public static final DeferredRegister<ConditionSerializer> CONDITION_SERIALIZERS = DeferredRegister.create(Cardinal.MOD_ID, ConditionSerializer.REGISTRY);

    public static final RegistrySupplier<ConditionSerializer> FACING_DIRECTION = CONDITION_SERIALIZERS.register("facing_direction", FacingDirectionCondition.Serializer::new);
    public static final RegistrySupplier<ConditionSerializer> HAS_PASSENGER = CONDITION_SERIALIZERS.register("has_passenger", HasPassengerCondition.Serializer::new);
    public static final RegistrySupplier<ConditionSerializer> HAS_INPUT_KEY = CONDITION_SERIALIZERS.register("has_input_key", HasInputKeyCondition.Serializer::new);
    public static final RegistrySupplier<ConditionSerializer> HOLDING_BLOCK = CONDITION_SERIALIZERS.register("holding_block", HoldingBlockCondition.Serializer::new);
    public static final RegistrySupplier<ConditionSerializer> MOUSE_CLICK = CONDITION_SERIALIZERS.register("mouse_click", MouseClickCondition.Serializer::new);
}
