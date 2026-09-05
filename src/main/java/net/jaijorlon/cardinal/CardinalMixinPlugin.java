package net.jaijorlon.cardinal;

import com.bawnorton.mixinsquared.MixinSquaredBootstrap;
import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.service.MixinService;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class CardinalMixinPlugin implements IMixinConfigPlugin {

    private static final boolean HAS_AC;
    private static final boolean HAS_PALLADIUM;
    private static final String[] PalladiumNeedingMixins = new String[] {
            "net.jaijorlon.cardinal.mixin.compat.PalladiumEntityRenderDispatcherMixin",
            "net.jaijorlon.cardinal.mixin.compat.PalladiumPropertyMixin",
            "net.jaijorlon.cardinal.mixin.compat.PalladiumPropertyLookupMixin"
    };

    static {
        HAS_AC = hasClass("com.github.alexmodguy.alexscaves.AlexsCaves");
        HAS_PALLADIUM = hasClass("net.threetag.palladium.Palladium");
    }

    @Override
    public void onLoad(String mixinPackage) {
        MixinExtrasBootstrap.init();
        MixinSquaredBootstrap.init();
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.equalsIgnoreCase("net.jaijorlon.cardinal.mixin.compat.ACEntityMixin")) {
            return HAS_AC;
        }
        for (String mixin : PalladiumNeedingMixins) {
            if (mixinClassName.equalsIgnoreCase(mixin)) {
                if (!HAS_PALLADIUM) {
                    Cardinal.LOGGER.warn("Palladium mixin {} has been removed", mixin);
                }
                return HAS_PALLADIUM;
            }
        }

        return true;
    }

    private static boolean hasClass(String name) {
        try {
            // This does *not* load the class!
            MixinService.getService().getBytecodeProvider().getClassNode(name);
            return true;
        } catch (ClassNotFoundException | IOException e) {
            Cardinal.LOGGER.error("Failed to load class " + name, e);
            return false;
        }
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
