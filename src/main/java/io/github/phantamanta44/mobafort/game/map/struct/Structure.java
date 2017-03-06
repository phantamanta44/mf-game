package io.github.phantamanta44.mobafort.game.map.struct;

import io.github.phantamanta44.mobafort.lib.math.CylBounds;
import org.bukkit.entity.LivingEntity;

public abstract class Structure {

    private final CylBounds bounds;

    public Structure(CylBounds bounds) {
        this.bounds = bounds;
    }

    public abstract BlockBuild getBlocks();

    public abstract void tick(long tick);

    public CylBounds getBounds() {
        return bounds;
    }

    public boolean isTargetable() {
        return false;
    }

    public LivingEntity getDamageBuffer() {
        throw new UnsupportedOperationException();
    }

}
