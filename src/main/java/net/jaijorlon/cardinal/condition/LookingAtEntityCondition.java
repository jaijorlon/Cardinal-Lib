package net.jaijorlon.cardinal.condition;

import com.google.gson.JsonObject;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.threetag.palladium.condition.Condition;
import net.threetag.palladium.condition.ConditionSerializer;
import net.threetag.palladium.util.EntityUtil;
import net.threetag.palladium.util.context.DataContext;
import net.threetag.palladium.util.property.FloatProperty;
import net.threetag.palladium.util.property.PalladiumProperty;
import net.threetag.palladium.util.property.StringProperty;

public class LookingAtEntityCondition extends Condition {

    private final float distance;

    public LookingAtEntityCondition(float distance) {
        this.distance = distance;
    }

    @Override
    public boolean active(DataContext context) {
        var entity = context.getEntity();

        if (entity != null) {
            if (entity instanceof LivingEntity livingEntity) {

                try {
                    var start = livingEntity.getEyePosition(1F);
                    var end = start.add(EntityUtil.getLookVector(livingEntity, 1F).scale(this.distance));
                    HitResult endHit = EntityUtil.rayTraceWithEntities(livingEntity, start, end, start.distanceTo(end), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, en -> true);
                    if (endHit instanceof EntityHitResult) {
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        }

        return false;
    }

    @Override
    public ConditionSerializer getSerializer() {
        return CardinalConditionSerializers.LOOKING_AT_ENTITY.get();
    }

    public static class Serializer extends ConditionSerializer {

        public static final PalladiumProperty<Float> DISTANCE = new FloatProperty("distance").configurable("The amount of distance that you want to check for");

        public Serializer() {
            this.withProperty(DISTANCE, 10.0F);
        }

        @Override
        public Condition make(JsonObject json) {
            return new LookingAtEntityCondition(getProperty(json, DISTANCE));
        }

        @Override
        public String getDocumentationDescription() {
            return "Checks if the entity is looking at another entity.";
        }
    }
}
