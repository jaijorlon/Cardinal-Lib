package net.jaijorlon.cardinal.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.jaijorlon.cardinal.api.GravityChangerAPI;
import net.jaijorlon.cardinal.util.RotationUtil;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {
    @Redirect(
        method = "Lnet/minecraft/client/renderer/entity/player/PlayerRenderer;setupRotations(Lnet/minecraft/client/player/AbstractClientPlayer;Lcom/mojang/blaze3d/vertex/PoseStack;FFF)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/AbstractClientPlayer;getViewVector(F)Lnet/minecraft/world/phys/Vec3;"
        )
    )
    private Vec3 modify_setupTransforms_Vec3d_0(AbstractClientPlayer instance, float partialTick) {
        Vec3 viewVector = instance.getViewVector(partialTick);
        
        Direction gravityDirection = GravityChangerAPI.getGravityDirection(instance);
        if (gravityDirection == Direction.DOWN) {
            return viewVector;
        }
        
        return RotationUtil.vecWorldToPlayer(viewVector, gravityDirection);
    }

    /**
     * @author Jaijorlon
     * @reason moves player crouching model the right direction
     */
    @Inject(method = "getRenderOffset(Lnet/minecraft/client/player/AbstractClientPlayer;F)Lnet/minecraft/world/phys/Vec3;", at = @At("HEAD"), cancellable = true)
    public void getRenderOffset(AbstractClientPlayer instance, float partialTick, CallbackInfoReturnable<Vec3> cir) {
        Direction gravityDirection = GravityChangerAPI.getGravityDirection(instance);
        if (gravityDirection != Direction.DOWN && instance.isCrouching()) {
            if (gravityDirection == Direction.UP) {
                cir.setReturnValue(new Vec3(0.0D, 0.125D, 0.0D));
            }

            if (gravityDirection == Direction.NORTH) {
                cir.setReturnValue(new Vec3(0.0D, 0.0D, -0.125D));
            }

            if (gravityDirection == Direction.SOUTH) {
                cir.setReturnValue(new Vec3(0.0D, 0.0D, 0.125D));
            }

            if (gravityDirection == Direction.EAST) {
                cir.setReturnValue(new Vec3(0.125D, 0.0D, 0.0D));
            }

            if (gravityDirection == Direction.WEST) {
                cir.setReturnValue(new Vec3(-0.125D, 0.0D, 0.0D));
            }
        }
    }
}
