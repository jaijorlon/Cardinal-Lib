package net.jaijorlon.cardinal.event;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import net.jaijorlon.cardinal.Cardinal;
import net.jaijorlon.cardinal.ability.CardinalAbilities;
import net.jaijorlon.cardinal.network.PacketHandler;
import net.jaijorlon.cardinal.network.packet.C2SHasInputKeyConditionPacket;
import net.jaijorlon.cardinal.network.packet.C2SMouseClickConditionPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = Cardinal.MOD_ID, value = Dist.CLIENT)
public class ClientForgeModEvents {

    static boolean reverseR = false;
    static boolean reverseG = false;
    static boolean reverseB = false;
    static float r = 1f, g = 0f, b = 0f;
    static float r1 = 0f, g1 = 0f, b1 = 0f;

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton event) {
        if (ModList.get().isLoaded("palladium")) {
            Minecraft mc = Minecraft.getInstance();

            if (mc.getConnection() != null) {
                if (event.getAction() == GLFW.GLFW_PRESS) {
                    if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                        PacketHandler.sendToServer(new C2SMouseClickConditionPacket("left", true));
                    }

                    if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                        PacketHandler.sendToServer(new C2SMouseClickConditionPacket("right", true));
                    }
                } else {
                    if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                        PacketHandler.sendToServer(new C2SMouseClickConditionPacket("left", false));
                    }

                    if (event.getButton() == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
                        PacketHandler.sendToServer(new C2SMouseClickConditionPacket("right", false));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (ModList.get().isLoaded("palladium")) {
            Minecraft mc = Minecraft.getInstance();

            if (mc.getConnection() != null) {
                if (mc.options.keyUp.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("up", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("up", false));
                }

                if (mc.options.keyDown.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("down", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("down", false));
                }

                if (mc.options.keyLeft.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("left", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("left", false));
                }

                if (mc.options.keyRight.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("right", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("right", false));
                }

                if (mc.options.keyJump.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("jump", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("jump", false));
                }

                if (mc.options.keySprint.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("sprint", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("sprint", false));
                }

                if (mc.options.keyUse.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("use", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("use", false));
                }

                if (mc.options.keyAttack.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("attack", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("attack", false));
                }

                if (mc.options.keyShift.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("shift", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("shift", false));
                }

                if (mc.options.keyAdvancements.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("advancements", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("advancements", false));
                }

                if (mc.options.keyChat.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("chat", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("chat", false));
                }

                if (mc.options.keyCommand.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("command", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("command", false));
                }

                if (mc.options.keyDrop.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("drop", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("drop", false));
                }

                if (mc.options.keyFullscreen.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("fullscreen", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("fullscreen", false));
                }

                if (mc.options.keyInventory.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("inventory", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("inventory", false));
                }

                if (mc.options.keyPickItem.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("pick_item", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("pick_item", false));
                }

                if (mc.options.keySwapOffhand.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("offhand", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("offhand", false));
                }

                if (mc.options.keyTogglePerspective.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("toggle_perspective", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("toggle_perspective", false));
                }

                if (mc.options.keySmoothCamera.isDown()) {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("smooth_camera", true));
                } else {
                    PacketHandler.sendToServer(new C2SHasInputKeyConditionPacket("smooth_camera", false));
                }
            }

            if (!net.threetag.palladium.power.ability.AbilityUtil.isTypeEnabled(mc.player, CardinalAbilities.PREVENT_MOVEMENT_INPUT.get()))
                return;

            if (mc.options.keyUp.isDown()) {
                mc.options.keyUp.setDown(false);
            }

            if (mc.options.keyDown.isDown()) {
                mc.options.keyDown.setDown(false);
            }

            if (mc.options.keyLeft.isDown()) {
                mc.options.keyLeft.setDown(false);
            }

            if (mc.options.keyRight.isDown()) {
                mc.options.keyRight.setDown(false);
            }

            if (mc.options.keyJump.isDown()) {
                mc.options.keyJump.setDown(false);
            }

            if (mc.options.keySprint.isDown()) {
                mc.options.keySprint.setDown(false);
            }
        }
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        /*
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) return;

        PoseStack poseStack = event.getPoseStack();

        Camera camera = mc.gameRenderer.getMainCamera();
        Vec3 camPos = camera.getPosition();

        List<Cow> list = player.level().getEntitiesOfClass(Cow.class, player.getBoundingBox().inflate(64.0D));

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        for (Cow cow : list) {

            double x = Mth.lerp(event.getPartialTick(), cow.xOld, cow.getX());
            double y = Mth.lerp(event.getPartialTick(), cow.yOld, cow.getY());
            double z = Mth.lerp(event.getPartialTick(), cow.zOld, cow.getZ());

            poseStack.pushPose();

            poseStack.translate(x - camPos.x, y - camPos.y, z - camPos.z);

            double d1 = player.distanceToSqr(cow.getX(), cow.getY(), cow.getZ());
            float f = (float)((1.0D - d1 / 256.0D) * 1.0);
            if (f > 0.0F) {
                renderShadow(poseStack, mc.renderBuffers().bufferSource(), cow, f-0.5F, 1.0F, (float) cow.getBoundingBox().getSize());
            }

            poseStack.popPose();
        }

        RenderSystem.enableCull();
        RenderSystem.disableBlend();

         */
    }

    private static void drawFilledCircle(PoseStack poseStack, float radius, int segments) {
        Tesselator tess = Tesselator.getInstance();
        BufferBuilder buffer = tess.getBuilder();

        buffer.begin(VertexFormat.Mode.TRIANGLES, DefaultVertexFormat.POSITION_COLOR);

        Matrix4f matrix = poseStack.last().pose();

        float r = 1f, g = 0f, b = 0f, a = 0.5f;

        for (int i = 0; i < segments; i++) {
            double angle1 = 2 * Math.PI * i / segments;
            double angle2 = 2 * Math.PI * (i + 1) / segments;

            float x1 = (float) (radius * Math.cos(angle1));
            float z1 = (float) (radius * Math.sin(angle1));
            float x2 = (float) (radius * Math.cos(angle2));
            float z2 = (float) (radius * Math.sin(angle2));

            buffer.vertex(matrix, 0, 0, 0).color(r, g, b, a).endVertex();
            buffer.vertex(matrix, x1, 0, z1).color(r, g, b, a).endVertex();
            buffer.vertex(matrix, x2, 0, z2).color(r, g, b, a).endVertex();
        }

        tess.end();
    }

    private static void renderShadow(PoseStack poseStack, MultiBufferSource buffer, Entity entity, float shadowStrength, float partialTick, float size) {
        float radius = size;
        if (entity instanceof Mob mob && mob.isBaby()) {
            radius *= 0.5F;
        }

        VertexConsumer vc = buffer.getBuffer(RenderType.entityShadow(Cardinal.id("textures/misc/shadow.png")));

        poseStack.pushPose();

        float time = entity.tickCount + partialTick;
        float angle = time * 4.0f;
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));

        PoseStack.Pose pose = poseStack.last();

        float half = radius / 2f;
        float yOffset = 0f;

        shadowVertex(pose, vc, shadowStrength, -half, yOffset, -half, 0, 0, entity.tickCount);
        shadowVertex(pose, vc, shadowStrength, -half, yOffset, half, 0, 1, entity.tickCount);
        shadowVertex(pose, vc, shadowStrength, half, yOffset, half, 1, 1, entity.tickCount);
        shadowVertex(pose, vc, shadowStrength, half, yOffset, -half, 1, 0, entity.tickCount);

        poseStack.popPose();
    }

    private static void shadowVertex(PoseStack.Pose pose, VertexConsumer vc, float alpha, float x, float y, float z, float u, float v, float time) {
        if (time % 3 == 0) {
            if (r > 0) {
                if (!reverseR) {
                    r1 += 0.01F;
                    if (r1 >= r) reverseR = true;
                } else {
                    r1 -= 0.01F;
                    if (r1 <= 0) reverseR = false;
                }
            } else {
                r1 = 0f;
            }

            if (g > 0) {
                if (!reverseG) {
                    g1 += 0.01F;
                    if (g1 >= g) reverseG = true;
                } else {
                    g1 -= 0.01F;
                    if (g1 <= 0) reverseG = false;
                }
            } else {
                g1 = 0f;
            }

            if (b > 0) {
                if (!reverseB) {
                    b1 += 0.01F;
                    if (b1 >= b) reverseB = true;
                } else {
                    b1 -= 0.01F;
                    if (b1 <= 0) reverseB = false;
                }
            } else {
                b1 = 0f;
            }
        }

        Vector3f vec = pose.pose().transformPosition(x, y, z, new Vector3f());
        vc.vertex(vec.x(), vec.y(), vec.z(), r1, g1, b1, alpha, u, v, OverlayTexture.NO_OVERLAY, 15728880, 0f, 1f, 0f);
    }
}
