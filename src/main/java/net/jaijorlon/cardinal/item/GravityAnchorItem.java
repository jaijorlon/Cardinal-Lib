package net.jaijorlon.cardinal.item;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

// based on AmethystGravity
public class GravityAnchorItem extends Item {
    public final Direction direction;
    
    public GravityAnchorItem(Properties settings, Direction _direction) {
        super(settings);
        direction = _direction;
    }
    
    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, Level world, List<Component> tooltip, @NotNull TooltipFlag tooltipContext) {
        tooltip.add(
            Component.translatable("gravity_changer.gravity_anchor.tooltip.0")
                .withStyle(ChatFormatting.GRAY)
        );
        
        tooltip.add(
            Component.translatable("gravity_changer.gravity_anchor.tooltip.1")
                .withStyle(ChatFormatting.GRAY)
        );
    }
}
