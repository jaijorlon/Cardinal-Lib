package net.jaijorlon.cardinal.util;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec2;
import net.threetag.palladium.util.EntityUtil;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

public class CardinalUtil {
    /// returns a string that letters are added one at a time based on animation progress (0.0 to 1.0)
    public static String textBuilder(String string, float animation) {
        return string.substring(0, Mth.lerpInt(animation, 0, string.length()));
    }

    /// stops a sound from playing to an entity
    public static void stopSound(LivingEntity entity, SoundSource soundSource, ResourceLocation name) {
        ClientboundStopSoundPacket clientboundstopsoundpacket = new ClientboundStopSoundPacket(name, soundSource);

        if (entity instanceof ServerPlayer serverPlayer) {
            serverPlayer.connection.send(clientboundstopsoundpacket);
        }
    }

    /// capitalizes the first letter in a string
    public static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    /// capitalizes the whole string with words being anything seperated with the _ symbol, also replaces that symbol with a space
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

    /// return EntityHitResult from entity with adjustable distance limit
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

    // Math

    /// gets what percentage the value is of the total as a percent so 10 out of 20 would return 50%(I name formally getPercentageOf in kubejs)
    public static float percentageOf(float value, float total) {
        return ((100 * value) / total);
    }

    /// get a percentage of a number so 10% of 10 would return 1(I name formally percentage in kubejs)
    public static float getPercentageOf(float percent, float total) {
        return ((percent / 100) * total);
    }

    /// gets what the yaw value of point entity would be if they were looking at target entity
    public static float getYawBetweenEntities(Entity point, Entity target) {
        double dX = target.getX() - point.getX();
        double dZ = target.getZ() - point.getZ();

        double radians = Math.atan2(dZ, dX);
        float yaw = (float) (radians * (180.0 / Math.PI)) - 90.0F;

        return Mth.wrapDegrees(yaw);
    }

    /// gets a random integer value between 2 numbers (inclusive)
    public static int getRandomIntRange(int min, int max) {
        return (int) (Math.floor(Math.random() * (max - min + 1)) + min);
    }

    // Client only

    /// gets how much the screen size on an axis has increased or decrease compared to orginal value
    public static float ScreenScale(Minecraft mc, int guiWidth, int guiHeight, float guiScale, String axis) {
        // adjust these numbers to match your own gui scale width and gui height numbers you use
        Vec2 mapperGuiScale = new Vec2(guiWidth, guiHeight);

        float compareScalePercent_x = percentageOf(mc.getWindow().getGuiScaledWidth(), mapperGuiScale.x);
        float compareScalePercent_y = percentageOf(mc.getWindow().getGuiScaledHeight(), mapperGuiScale.y);
        if (axis.equals("x")) {
            // returns the inputed number as the equivalent number for another persons screen
            return getPercentageOf(compareScalePercent_x, guiScale);
        }
        if (axis.equals("y")) {
            // returns the inputed number as the equivalent number for another persons screen
            return getPercentageOf(compareScalePercent_y, guiScale);
        }
        return guiScale;
    }

    /// draws a centered RGBA box
    public static void renderCenteredBox(PoseStack matrixStack, float x, float y, float width, float height, float r, float g, float b, float opacity) {
        Tesselator tes = Tesselator.getInstance();
        BufferBuilder bb = tes.getBuilder();

        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        bb.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bb.vertex(matrixStack.last().pose(), x + width, y, 0).color(-r, -g, -b, opacity).endVertex();
        bb.vertex(matrixStack.last().pose(), x, y, 0).color(-r, -g, -b, opacity).endVertex();
        bb.vertex(matrixStack.last().pose(), x, y + height, 0).color(-r, -g, -b, opacity).endVertex();
        bb.vertex(matrixStack.last().pose(), x + width, y + height, 0).color(-r, -g, -b, opacity).endVertex();
        tes.end();

        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();
    }

    /// draws an RGBA box
    public static void renderBox(PoseStack matrixStack, float x, float y, float width, float height, float r, float g, float b, float opacity) {
        Tesselator tes = Tesselator.getInstance();
        BufferBuilder bb = tes.getBuilder();

        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        bb.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bb.vertex(matrixStack.last().pose(), x + width, y, 0).color(-r, -g, -b, opacity).endVertex();
        bb.vertex(matrixStack.last().pose(), x, y, 0).color(-r, -g, -b, opacity).endVertex();
        bb.vertex(matrixStack.last().pose(), x, y + height, 0).color(-r, -g, -b, opacity).endVertex();
        bb.vertex(matrixStack.last().pose(), x + width, y + height, 0).color(-r, -g, -b, opacity).endVertex();
        tes.end();

        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();
    }

    /// fills area with a box that has gradient between 2 different hex numbers and can change opacity
    public static void renderGradientBox(PoseStack matrixStack, float x, float y, float width, float height, int hex, int hex2, float opacity) {
        Tesselator tes = Tesselator.getInstance();
        BufferBuilder bb = tes.getBuilder();
        float f1 = (float) FastColor.ARGB32.red(hex) / 255.0F;
        float f2 = (float) FastColor.ARGB32.green(hex) / 255.0F;
        float f3 = (float) FastColor.ARGB32.blue(hex) / 255.0F;
        float f5 = (float) FastColor.ARGB32.red(hex2) / 255.0F;
        float f6 = (float) FastColor.ARGB32.green(hex2) / 255.0F;
        float f7 = (float) FastColor.ARGB32.blue(hex2) / 255.0F;

        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        bb.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        bb.vertex(matrixStack.last().pose(), (float) x + width, (float) y, 0.0f).color(f1, f2, f3, opacity).endVertex();
        bb.vertex(matrixStack.last().pose(), (float) x, (float) y, 0.0f).color(f5, f6, f7, opacity).endVertex();
        bb.vertex(matrixStack.last().pose(), (float) x, (float) y + height, 0.0f).color(f5, f6, f7, opacity).endVertex();
        bb.vertex(matrixStack.last().pose(), (float) x + width, (float) y + height, 0.0f).color(f1, f2, f3, opacity).endVertex();
        tes.end();

        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();
    }

    /// same as regular drawCenteredString but you can toggle text shadow
    public static void drawCenteredString(GuiGraphics gui, Font font, Component text, int x, int y, int color, boolean hasShadow) {
        int width = font.width(text);
        gui.drawString(font, text, x - (width / 2), y, color, hasShadow);
    }

    /// draws a string that grows backwards instead of forwards
    public static void drawReversedString(GuiGraphics gui, Font font, Component text, int x, int y, int color, boolean hasShadow) {
        int width = font.width(text);
        gui.drawString(font, text, x - width, y, color, hasShadow);
    }

    /// fills area with a box that has gradient between 2 different hex numbers
    public static void fillGradient(PoseStack pPoseStack, int x1, int y1, int x2, int y2, int colorFrom, int colorTo) {
        RenderSystem.enableBlend();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tesselator.getBuilder();

        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        fillGradient(pPoseStack.last().pose(), bufferbuilder, x1, y1, x2, y2, colorFrom, colorTo);

        tesselator.end();
        RenderSystem.disableBlend();
    }

    private static void fillGradient(Matrix4f matrix4f, BufferBuilder builder, int x1, int y1, int x2, int y2, int colorA, int colorB) {
        int alphaA = colorA >> 24 & 0xFF;
        int redA = colorA >> 16 & 0xFF;
        int greenA = colorA >> 8 & 0xFF;
        int blueA = colorA & 0xFF;
        int alphaB = colorB >> 24 & 0xFF;
        int redB = colorB >> 16 & 0xFF;
        int greenB = colorB >> 8 & 0xFF;
        int blueB = colorB & 0xFF;
        builder.vertex(matrix4f, (float) x2, (float) y1, (float) 0).color(redB, greenB, blueB, alphaB).endVertex();
        builder.vertex(matrix4f, (float) x1, (float) y1, (float) 0).color(redA, greenA, blueA, alphaA).endVertex();
        builder.vertex(matrix4f, (float) x1, (float) y2, (float) 0).color(redA, greenA, blueA, alphaA).endVertex();
        builder.vertex(matrix4f, (float) x2, (float) y2, (float) 0).color(redB, greenB, blueB, alphaB).endVertex();
    }

    /// draws texture next to entity that scales with entity hitbox size
    public static void drawTextureQuad(PoseStack poseStack, VertexConsumer vertexConsumer, Entity entity, int light, float alpha) {
        float radius = (float) entity.getBoundingBox().getSize();

        PoseStack.Pose pose = poseStack.last();

        float half = radius / 2f;

        vertex(pose, vertexConsumer, alpha, -half, -half, 0.0F, 1.0F, 1.0F, light);
        vertex(pose, vertexConsumer, alpha, -half, half, 0.0F, 1.0F, 0.0F, light);
        vertex(pose, vertexConsumer, alpha, half, half, 0.0F, 0.0F, 0.0F, light);
        vertex(pose, vertexConsumer, alpha, half, -half, 0.0F, 0.0F, 1.0F, light);
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer vc, float alpha, float x, float y, float z, float u, float v, int light) {
        vc.vertex(pose.pose(), x, y, z)
                .color(255, 255, 255, (int) (alpha * 255))
                .uv(u, v)
                .overlayCoords(OverlayTexture.NO_OVERLAY)
                .uv2(light)
                .normal(pose.normal(), 0.0F, 0.0F, -1.0F)
                .endVertex();
    }
}
