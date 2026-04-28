package net.jaijorlon.cardinal.condition;

import net.threetag.palladium.condition.Condition;

public abstract class MouseCondition extends Condition {
    public final int cooldown;
    public final String mouseButton;

    public MouseCondition(int cooldown, String mouseButton) {
        this.cooldown = cooldown;
        this.mouseButton = mouseButton;
    }

    @Override
    public boolean handlesCooldown() {
        return this.cooldown > 0;
    }
}
