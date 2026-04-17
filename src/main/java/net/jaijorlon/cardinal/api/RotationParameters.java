package net.jaijorlon.cardinal.api;

import net.jaijorlon.cardinal.config.CardinalConfig;

import net.jaijorlon.cardinal.config.CardinalConfigHandler;
import net.minecraft.nbt.CompoundTag;

public record RotationParameters(
    boolean rotateVelocity,
    boolean rotateView, // currently ignores this
    int rotationTimeMS
) {
    public static RotationParameters defaultParam = new RotationParameters(
        true, true, 500
    );
    
    public static void updateDefault() {
        defaultParam = new RotationParameters(
            !CardinalConfigHandler.worldVelocity,
            true,
            CardinalConfigHandler.rotationTime
        );
    }
    
    public static RotationParameters getDefault() {
        return defaultParam;
    }
    
    public RotationParameters withRotationTimeMs(int rotationTimeMS) {
        return new RotationParameters(
            rotateVelocity,
            rotateView,
            rotationTimeMS
        );
    }
    
    public CompoundTag toTag() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("RotateVelocity", rotateVelocity);
        tag.putBoolean("RotateView", rotateView);
        tag.putInt("RotationTimeMS", rotationTimeMS);
        return tag;
    }
    
    public static RotationParameters fromTag(CompoundTag tag) {
        return new RotationParameters(
            tag.getBoolean("RotateVelocity"),
            tag.getBoolean("RotateView"),
            tag.getInt("RotationTimeMS")
        );
    }
}
