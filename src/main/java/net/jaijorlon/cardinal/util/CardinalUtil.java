package net.jaijorlon.cardinal.util;

import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.threetag.palladium.util.EntityUtil;

import java.util.ArrayList;
import java.util.List;

public class CardinalUtil {
    public static void stopSound(LivingEntity entity, SoundSource soundSource, ResourceLocation name) {
        ClientboundStopSoundPacket clientboundstopsoundpacket = new ClientboundStopSoundPacket(name,soundSource);

        if (entity instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(clientboundstopsoundpacket);
        }
    }

    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static String capitalizeString(String input) {
        if (!input.contains("_")) {
            return capitalizeFirstLetter(input);
        }
        List<String> UppercaseStrings = new ArrayList<>();

        for (String lowercas_string : input.split("_")) {
            UppercaseStrings.add(capitalizeFirstLetter(lowercas_string));
        }

        return String.join(" ", UppercaseStrings);
    }

    public static EntityHitResult raytrace(Entity entity, float distance) {
        try {
            var start = entity.getEyePosition(1.0F);
            var end = start.add(EntityUtil.getLookVector(entity, 1.0F).scale(distance));
            HitResult endHit = EntityUtil.rayTraceWithEntities(entity, start, end, start.distanceTo(end), ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, en -> true);
            if (endHit instanceof EntityHitResult entityHitResult) {
                return entityHitResult;
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }
}
