package net.jaijorlon.cardinal;

import net.jaijorlon.cardinal.util.PalladiumPropertyUtil;
import net.minecraft.world.entity.projectile.Projectile;
import net.threetag.palladium.entity.CustomProjectile;
import net.threetag.palladium.event.PalladiumEvents;

public class CardinalPalladiumProperties {
    public static void init() {
        PalladiumEvents.REGISTER_PROPERTY.register(handler -> {
            if (!(handler.getEntity() instanceof CustomProjectile) && !(handler.getEntity() instanceof Projectile)) {
                PalladiumPropertyUtil.registerProperty(handler, "gravityDir", "string", "");
            }
        });
    }
}
