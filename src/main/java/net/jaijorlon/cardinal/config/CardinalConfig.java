package net.jaijorlon.cardinal.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import org.apache.commons.lang3.tuple.Pair;

public class CardinalConfig {
    public static final CardinalConfig CONFIG;
    public static final ForgeConfigSpec CONFIG_SPEC;

    public static ConfigValue<Integer> rotationTime;
    public static ForgeConfigSpec.BooleanValue worldVelocity;

    public static ForgeConfigSpec.DoubleValue gravityStrengthMultiplier;

    public static ForgeConfigSpec.BooleanValue resetGravityOnRespawn;
    public static ForgeConfigSpec.BooleanValue voidDamageAboveWorld;
    public static ForgeConfigSpec.BooleanValue voidDamageOnHorizontalFallTooFar;
    public static ForgeConfigSpec.BooleanValue autoJumpOnGravityPlateInnerCorner;
    public static ForgeConfigSpec.BooleanValue adjustPositionAfterChangingGravity;

    static {
        Pair<CardinalConfig, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(CardinalConfig::new);
        CONFIG = pair.getLeft();
        CONFIG_SPEC = pair.getRight();
    }

    public CardinalConfig(ForgeConfigSpec.Builder config) {
        config.push("Gravity Settings");
        CardinalConfig.rotationTime = config.comment("animation rotation time").defineInRange("rotationTime", 500, 0, Integer.MAX_VALUE);
        CardinalConfig.gravityStrengthMultiplier = config.comment("gravity strength multiplier").defineInRange("gravityStrengthMultiplier", 1.0F, 0.0F, Float.MAX_VALUE);
        CardinalConfig.worldVelocity = config.comment("world velocity").define("worldVelocity", false);
        CardinalConfig.resetGravityOnRespawn = config.comment("reset gravity on respawn").define("resetGravityOnRespawn", true);
        CardinalConfig.voidDamageAboveWorld = config.comment("void damage when above world").define("voidDamageAboveWorld", true);
        CardinalConfig.voidDamageOnHorizontalFallTooFar = config.comment("void damage when horizontally fall too far").define("voidDamageOnHorizontalFallTooFar", true);
        CardinalConfig.autoJumpOnGravityPlateInnerCorner = config.comment("auto jump on gravity plate inner corner").define("autoJumpOnGravityPlateInnerCorner", true);
        CardinalConfig.adjustPositionAfterChangingGravity = config.comment("adjust position after gravity change").define("adjustPositionAfterChangingGravity", true);
        config.pop();
    }
}
