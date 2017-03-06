package io.github.phantamanta44.mobafort.game.hero.spell.missile;

import io.github.phantamanta44.mobafort.weaponize.projectile.SpellProjectile;

@FunctionalInterface
public interface IMissileDecorator<T extends SpellProjectile> {

    void apply(T proj, long tick);

}
