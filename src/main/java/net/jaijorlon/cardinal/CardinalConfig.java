package net.jaijorlon.cardinal;

import net.minecraftforge.common.ForgeConfigSpec;

public class CardinalConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public final static ForgeConfigSpec.IntValue rotationTime;

    public final static ForgeConfigSpec.DoubleValue gravityStrengthMultiplier;

    public final static ForgeConfigSpec.BooleanValue worldVelocity;
    public final static ForgeConfigSpec.BooleanValue resetGravityOnRespawn;
    public final static ForgeConfigSpec.BooleanValue voidDamageAboveWorld;
    public final static ForgeConfigSpec.BooleanValue voidDamageOnHorizontalFallTooFar;
    public final static ForgeConfigSpec.BooleanValue autoJumpOnGravityPlateInnerCorner;
    public final static ForgeConfigSpec.BooleanValue adjustPositionAfterChangingGravity;

    static {
        BUILDER.push("Gravity Settings");
        rotationTime = BUILDER.comment("animation rotation time").defineInRange("rotationTime", 500, 0, Integer.MAX_VALUE);
        gravityStrengthMultiplier = BUILDER.comment("gravity strength multiplier").defineInRange("gravityStrengthMultiplier", 1.0F, 0.0F, Float.MAX_VALUE);
        worldVelocity = BUILDER.comment("world velocity").define("worldVelocity", false);
        resetGravityOnRespawn = BUILDER.comment("reset gravity on respawn").define("resetGravityOnRespawn", true);
        voidDamageAboveWorld = BUILDER.comment("void damage when above world").define("voidDamageAboveWorld", true);
        voidDamageOnHorizontalFallTooFar = BUILDER.comment("void damage when horizontally fall too far").define("voidDamageOnHorizontalFallTooFar", true);
        autoJumpOnGravityPlateInnerCorner = BUILDER.comment("auto jump on gravity plate inner corner").define("autoJumpOnGravityPlateInnerCorner", true);
        adjustPositionAfterChangingGravity = BUILDER.comment("adjust position after gravity change").define("adjustPositionAfterChangingGravity", true);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
