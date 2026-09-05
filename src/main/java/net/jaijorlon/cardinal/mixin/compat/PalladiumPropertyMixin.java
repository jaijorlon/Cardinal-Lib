package net.jaijorlon.cardinal.mixin.compat;

import net.jaijorlon.cardinal.util.property.IntegerArrayProperty;
import net.threetag.palladium.util.property.PalladiumProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PalladiumProperty.class)
public class PalladiumPropertyMixin {
    /**
     * @author Jaijorlon
     * @reason stuff for integer array property to return
     */
    @Inject(method = "fixValues(Lnet/threetag/palladium/util/property/PalladiumProperty;Ljava/lang/Object;)Ljava/lang/Object;", at = @At("TAIL"), cancellable = true, remap = false)
    private static void fixValues(PalladiumProperty<?> property, Object value, CallbackInfoReturnable<Object> cir) {
        if (property instanceof IntegerArrayProperty && value instanceof List<?> list) {
            Integer[] array = new Integer[list.size()];
            for (int i = 0; i < list.size(); i++) {
                array[i] = Integer.valueOf(list.get(i).toString());
            }
            cir.setReturnValue(array);
        }
    }

}
