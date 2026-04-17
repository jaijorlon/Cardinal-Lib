package net.jaijorlon.cardinal.config;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = "cardinal", bus = Mod.EventBusSubscriber.Bus.MOD)
public class CardinalConfigHandler {
    public static int rotationTime;

    public static double gravityStrengthMultiplier;

    public static boolean worldVelocity;
    public static boolean resetGravityOnRespawn;
    public static boolean voidDamageAboveWorld;
    public static boolean voidDamageOnHorizontalFallTooFar;
    public static boolean autoJumpOnGravityPlateInnerCorner;
    public static boolean adjustPositionAfterChangingGravity;

    @SubscribeEvent
    public static void onConfigLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == CardinalConfig.SPEC) {
            rotationTime = CardinalConfig.rotationTime.get();
            gravityStrengthMultiplier = CardinalConfig.gravityStrengthMultiplier.get();
            worldVelocity = CardinalConfig.worldVelocity.get();
            resetGravityOnRespawn = CardinalConfig.resetGravityOnRespawn.get();
            voidDamageAboveWorld = CardinalConfig.voidDamageAboveWorld.get();
            voidDamageOnHorizontalFallTooFar = CardinalConfig.voidDamageOnHorizontalFallTooFar.get();
            autoJumpOnGravityPlateInnerCorner = CardinalConfig.autoJumpOnGravityPlateInnerCorner.get();
            adjustPositionAfterChangingGravity = CardinalConfig.adjustPositionAfterChangingGravity.get();
        }
    }

    @SubscribeEvent
    public static void onConfigReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == CardinalConfig.SPEC) {
            rotationTime = CardinalConfig.rotationTime.get();
            gravityStrengthMultiplier = CardinalConfig.gravityStrengthMultiplier.get();
            worldVelocity = CardinalConfig.worldVelocity.get();
            resetGravityOnRespawn = CardinalConfig.resetGravityOnRespawn.get();
            voidDamageAboveWorld = CardinalConfig.voidDamageAboveWorld.get();
            voidDamageOnHorizontalFallTooFar = CardinalConfig.voidDamageOnHorizontalFallTooFar.get();
            autoJumpOnGravityPlateInnerCorner = CardinalConfig.autoJumpOnGravityPlateInnerCorner.get();
            adjustPositionAfterChangingGravity = CardinalConfig.adjustPositionAfterChangingGravity.get();
        }
    }
}
