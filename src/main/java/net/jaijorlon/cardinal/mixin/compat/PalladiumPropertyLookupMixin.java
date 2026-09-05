package net.jaijorlon.cardinal.mixin.compat;

import net.jaijorlon.cardinal.util.property.IntegerArrayProperty;
import net.threetag.palladium.util.property.PalladiumPropertyLookup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PalladiumPropertyLookup.class)
public class PalladiumPropertyLookupMixin {
    /**
     * @author Jaijorlon
     * @reason registers integer array property
     */
    @Inject(method = "<clinit>", at = @At("TAIL"), remap = false)
    private static void registerIntegerArrayProperty(CallbackInfo ci) {
        PalladiumPropertyLookup.register("integer_array", IntegerArrayProperty::new);
    }

}
