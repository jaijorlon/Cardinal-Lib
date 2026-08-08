package net.jaijorlon.cardinal.mixin.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.jaijorlon.cardinal.EntityTags;
import net.jaijorlon.cardinal.RotationAnimation;
import net.jaijorlon.cardinal.ability.CardinalAbilities;
import net.jaijorlon.cardinal.api.GravityChangerAPI;
import net.jaijorlon.cardinal.util.RotationUtil;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Quaternionf;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public abstract class PalladiumEntityRenderDispatcherMixin {
    /**
     * @author Jaijorlon
     * @reason stop shadow from rendering when ability is enabled
     */
    @Inject(method = "renderShadow", at = @At("HEAD"), cancellable = true)
    private static void renderShadow(PoseStack p_114458_, MultiBufferSource p_114459_, Entity p_114460_, float p_114461_, float p_114462_, LevelReader p_114463_, float p_114464_, CallbackInfo ci) {
        if (p_114460_ instanceof LivingEntity livingEntity) {
                if (net.threetag.palladium.power.ability.AbilityUtil.isTypeEnabled(livingEntity, CardinalAbilities.HIDE_SHADOW.get()))
                    ci.cancel();
        }
    }
}
