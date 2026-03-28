package net.jaijorlon.cardinal.ability;

import net.jaijorlon.cardinal.Cardinal;
import net.threetag.palladium.power.ability.Ability;
import net.threetag.palladiumcore.registry.DeferredRegister;
import net.threetag.palladiumcore.registry.RegistrySupplier;

public class CardinalAbilities {
    public static final DeferredRegister<Ability> ABILITIES;
    public static final RegistrySupplier<Ability> HIDE_SHADOW;

    static {
        ABILITIES = DeferredRegister.create(Cardinal.MOD_ID, Ability.REGISTRY);
        HIDE_SHADOW = ABILITIES.register("hide_shadow", HideShadowAbility::new);
    }
}