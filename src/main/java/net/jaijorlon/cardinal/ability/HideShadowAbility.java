package net.jaijorlon.cardinal.ability;

import net.minecraft.world.entity.LivingEntity;
import net.threetag.palladium.power.IPowerHolder;
import net.threetag.palladium.power.ability.Ability;
import net.threetag.palladium.power.ability.AbilityInstance;

public class HideShadowAbility extends Ability {
    @Override
    public void tick(LivingEntity entity, AbilityInstance entry, IPowerHolder holder, boolean enabled) {
        if (enabled) {

        }
    }

    @Override
    public String getDocumentationDescription() {
        return "move through blocks like a mole.";
    }
}