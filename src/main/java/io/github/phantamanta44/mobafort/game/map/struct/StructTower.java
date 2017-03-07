package io.github.phantamanta44.mobafort.game.map.struct;

import com.comphenix.protocol.wrappers.EnumWrappers;
import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.game.game.Announcer;
import io.github.phantamanta44.mobafort.game.game.Team;
import io.github.phantamanta44.mobafort.game.hero.spell.missile.HomingMissile;
import io.github.phantamanta44.mobafort.game.util.StattedDamageDummy;
import io.github.phantamanta44.mobafort.lib.effect.ParticleUtils;
import io.github.phantamanta44.mobafort.lib.math.CylBounds;
import io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat;
import io.github.phantamanta44.mobafort.weaponize.stat.Damage;
import io.github.phantamanta44.mobafort.weaponize.stat.Stats;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat.ReduceType.ADD;
import static io.github.phantamanta44.mobafort.mfrp.stat.StatTracker.SRC_BASE;

public class StructTower extends Structure {

    public static final double RANGE = 6.1D;

    private final TowerType type;
    private final Team team;
    private final BlockBuild[] builds;
    private final Vector bulletSrc;
    private StattedDamageDummy hp;
    private long lastShot = -1L;
    private boolean fort = true, reinf = false, targetable = false;
    private float dmgMult = 1F;

    public StructTower(Vector pos, TowerType type, Team team, BlockBuild a, BlockBuild b, BlockBuild c, boolean fort) {
        super(new CylBounds(pos, 2.5F, 5F));
        this.type = type;
        this.team = team;
        this.builds = new BlockBuild[] {a, b, c};
        this.fort = fort;
        this.bulletSrc = pos.clone().setY(pos.getY() + 4.5F);
        reset();
    }

    @Override
    public BlockBuild getBlocks() {
        double hpPerc = hp.getHealth() / hp.getMaxHealth();
        if (hpPerc > 0.5)
            return builds[0];
        else if (hpPerc > 0)
            return builds[1];
        else
            return builds[2];
    }

    @Override
    public void tick(long gameTick) {
        if (hp.getHealth() > 0) {
            if (gameTick % 20L == 0L) {
                hp.setHealth(Math.min(hp.getHealth() + type.hpGen, hp.getMaxHealth()));
                if (gameTick == 6000L)
                    fort = false;
            }
            if (gameTick % 1200L == 1199L) {
                hp.clearStats(true);
                hp.addStats(type.statProvider.apply((int)((gameTick + 1L) / 1200L)));
            }
            World world = GamePlugin.getEngine().getMap().getWorld();
            if (gameTick - lastShot >= 24L) {
                LivingEntity target = world.getNearbyEntities(getBounds().getBasePos().toLocation(world), RANGE, RANGE, RANGE).stream()
                        .filter(e -> e instanceof LivingEntity) // TODO Attack prioritization
                        .map(e -> (LivingEntity) e) // TODO Don't attack own team
                        .findAny().orElse(null);
                if (target != null) {
                    new PenetratingBullet(this, target).dispatch(); // TODO Bullet sfx
                    lastShot = gameTick;
                }
            }
        }
    }

    @Override
    public boolean isTargetable() {
        return targetable;
    }

    @Override
    public LivingEntity getDamageBuffer() {
        return hp;
    }

    public void setTargetable(boolean targetable) {
        this.targetable = targetable;
    }

    public void reset() {
        Collection<ProvidedStat<?>> stats = type.statProvider.apply(0);
        hp = new StattedDamageDummy(type.name, type.hp, type.hp, stats.toArray(new ProvidedStat<?>[stats.size()])) {
            @Override
            public void damage(double dmg) {
                if (reinf)
                    dmg /= 3D;
                super.damage(fort ? dmg / 2D : dmg);
                if (getHealth() < 1)
                    Announcer.game(team.tag + " turret has been destroyed!"); // TODO Destruction fx
            }
        };
    }

    private static class PenetratingBullet extends HomingMissile {

        private final StructTower src;
        private final LivingEntity target;

        private PenetratingBullet(StructTower src, LivingEntity target) {
            super(src.bulletSrc.toLocation(target.getWorld()), 0.4D, 0D, src.hp.getUniqueId());
            this.src = src;
            this.target = target;
        }

        @Override
        protected Vector getTarget() {
            return target.getLocation().toVector();
        }

        @Override
        protected void onReachTarget() {
            new Damage(0, Damage.DamageType.PHYSICAL)
                    .withDmg(Stats.AD, src.dmgMult)
                    .deal(src.hp, target);
            src.dmgMult = Math.min(src.dmgMult + 0.4F, 2.2F);
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

    public enum TowerType {

        OUTER("Outer Turret", 3500, 0, n -> Arrays.asList(
                    new ProvidedStat<>(Stats.AD, Math.min(152 + 4 * n, 180), SRC_BASE, ADD),
                    new ProvidedStat<>(Stats.ARM, 40 + Math.min(2 * n, 30), SRC_BASE, ADD),
                    new ProvidedStat<>(Stats.MR, 40 + Math.min(2 * n, 30), SRC_BASE, ADD),
                    new ProvidedStat<>(Stats.ARM_PEN, 11 + (int)Math.min(4.7F * n, 91), SRC_BASE, ADD)
        )),
        INNER("Inner Turret", 3300, 0, n -> {
            int n2 = Math.max(n - 8, 0), n3 = Math.max(n - 16, 0);
            return Arrays.asList(
                    new ProvidedStat<>(Stats.AD, Math.min(170 + 4 * n2, 250), SRC_BASE, ADD),
                    new ProvidedStat<>(Stats.ARM, 40 + Math.min(2 * n3, 30), SRC_BASE, ADD),
                    new ProvidedStat<>(Stats.MR, 40 + Math.min(2 * n3, 30), SRC_BASE, ADD),
                    new ProvidedStat<>(Stats.ARM_PEN, 11 + (int)Math.min(4.7F * n, 91), SRC_BASE, ADD)
            );
        }),
        INHIB("Inhibitor Turret", 3300, 5, n -> {
            int n2 = Math.max(n - 8, 0), n3 = Math.max(n - 31, 0);
            return Arrays.asList(
                    new ProvidedStat<>(Stats.AD, Math.min(170 + 4 * n2, 290), SRC_BASE, ADD),
                    new ProvidedStat<>(Stats.ARM, 40 + Math.min(2 * n3, 30), SRC_BASE, ADD),
                    new ProvidedStat<>(Stats.MR, 40 + Math.min(2 * n3, 30), SRC_BASE, ADD),
                    new ProvidedStat<>(Stats.ARM_PEN, 26 + (int)Math.min(11.6F * n, 128), SRC_BASE, ADD)
            );
        }),
        NEXUS("Nexus Turret", 3300, 5, n -> {
            int n2 = Math.max(n - 8, 0), n3 = Math.max(n - 30, 0);
            return Arrays.asList(
                    new ProvidedStat<>(Stats.AD, Math.min(150 + 4 * n2, 270), SRC_BASE, ADD),
                    new ProvidedStat<>(Stats.ARM, 40 + Math.min(2 * n3, 30), SRC_BASE, ADD),
                    new ProvidedStat<>(Stats.MR, 40 + Math.min(2 * n3, 30), SRC_BASE, ADD),
                    new ProvidedStat<>(Stats.ARM_PEN, 26 + (int)Math.min(11.6F * n, 182), SRC_BASE, ADD)
            );
        });

        public final String name;
        public final int hp, hpGen;
        public final Function<Integer, Collection<ProvidedStat<?>>> statProvider;

        TowerType(String name, int hp, int hpGen, Function<Integer, Collection<ProvidedStat<?>>> statProvider) {
            this.name = name;
            this.hp = hp;
            this.hpGen = hpGen;
            this.statProvider = statProvider;
        }

    }

}
