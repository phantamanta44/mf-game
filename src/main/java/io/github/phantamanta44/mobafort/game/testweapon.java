package io.github.phantamanta44.mobafort.game;

import com.comphenix.protocol.wrappers.EnumWrappers;
import io.github.phantamanta44.mobafort.game.hero.spell.CooldownEngine;
import io.github.phantamanta44.mobafort.game.hero.spell.missile.HomingMissile;
import io.github.phantamanta44.mobafort.game.util.FreeItems;
import io.github.phantamanta44.mobafort.lib.effect.ParticleUtils;
import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import io.github.phantamanta44.mobafort.lib.math.RayTrace;
import io.github.phantamanta44.mobafort.mfrp.stat.StatTracker;
import io.github.phantamanta44.mobafort.weaponize.stat.Damage;
import io.github.phantamanta44.mobafort.weaponize.stat.Stats;
import io.github.phantamanta44.mobafort.weaponize.weapon.IWeapon;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class testweapon implements IWeapon {

    public static final testweapon INSTANCE = new testweapon();
    private static final ItemSig TYPE = new ItemSig(FreeItems.next());

    @Override
    public ItemSig getType() {
        return TYPE;
    }

    @Override
    public IWeaponInstance instantiate(Player player) {
        return new Instance(player);
    }

    private static class Instance implements IWeaponInstance {

        private static final List<String> LORE = Collections.singletonList("\u00a77Annihilate them all.");

        private final Player pl;
        private final CooldownEngine cd;

        private Instance(Player player) {
            this.pl = player;
            cd = new CooldownEngine(pl, false);
        }

        @Override
        public String getName() {
            return "\u00a79Penetrating Bullets";
        }

        @Override
        public List<String> getLore() {
            return LORE;
        }

        @Override
        public int getStackSize() {
            return 1;
        }

        @Override
        public String getHudInfo() {
            return cd.offCooldown() ? null : cd.getBarRepresentation();
        }

        @Override
        public IWeapon getTemplate() {
            return INSTANCE;
        }

        @Override
        public void onInteract(PlayerInteractEvent event) {
            if (cd.offCooldown()) {
                RayTrace trace = new RayTrace(pl, 6.1D, 7);
                LivingEntity target = null;
                for (Location loc : trace) {
                    Optional<Entity> ent = loc.getWorld().getNearbyEntities(loc, 1, 1, 1).stream()
                            .filter(e -> e instanceof LivingEntity && !e.equals(pl))
                            .findFirst();
                    if (ent.isPresent()) {
                        target = (LivingEntity)ent.get();
                        break;
                    }
                }
                if (target != null) {
                    new PenetratingBullet(target).dispatch();
                    cd.cooldown(24L);
                }
            }
        }

        @Override
        public void tick(long tick) {
            // NO-OP
        }

        @Override
        public void kill() {
            // NO-OP
        }

        private class PenetratingBullet extends HomingMissile {

            private final LivingEntity target;

            private PenetratingBullet(LivingEntity target) {
                super(pl.getLocation(), 0.4D, 0D, pl.getUniqueId(), CollisionCriteria.NONE);
                this.target = target;
            }

            @Override
            protected Vector getTarget() {
                return target.getLocation().toVector().add(new Vector(0D, 1D, 0D));
            }

            @Override
            protected void onReachTarget() {
                new Damage(0, Damage.DamageType.PHYSICAL)
                        .withDmg(Stats.AD, 1D)
                        .deal(pl, target);
            }

            @Override
            public void onHit(CollisionCriteria col, List<Entity> ents) {
                // NO-OP
            }

            @Override
            public void tick(long tick) {
                super.tick(tick);
                Location loc = getLocation();
                ParticleUtils.dispatchEffect(loc, EnumWrappers.Particle.CRIT_MAGIC, 5, 0.03F);
                ParticleUtils.dispatchEffect(loc, EnumWrappers.Particle.DRAGON_BREATH, 1, 0F);
            }

        }

    }

}
