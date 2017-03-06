package io.github.phantamanta44.mobafort.game.hero.spell.missile;

import io.github.phantamanta44.mobafort.weaponize.projectile.SpellProjectile;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.UUID;

public abstract class HomingMissile extends SpellProjectile {

    protected double speed;

    public HomingMissile(Location pos, double speed, double radius, UUID source) {
        super(pos, new Vector(0, 0, 0), radius, source);
        this.speed = speed;
    }

    public HomingMissile(Location pos, double speed, double radius, UUID source, CollisionCriteria col) {
        super(pos, new Vector(0, 0, 0), radius, source, col);
        this.speed = speed;
    }

    @Override
    public void tick(long tick) {
        Vector target = getTarget();
        if (target.distanceSquared(getPosition()) <= speed) {
            setPosition(target);
            onReachTarget();
            kill();
        }
        else
            super.setVelocity(target.subtract(getPosition()).normalize().multiply(speed));
    }

    @Override
    public void setVelocity(Vector vel) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    protected abstract Vector getTarget();

    protected abstract void onReachTarget();

}
