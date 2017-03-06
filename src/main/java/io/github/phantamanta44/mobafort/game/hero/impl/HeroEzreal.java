package io.github.phantamanta44.mobafort.game.hero.impl;

import com.comphenix.protocol.wrappers.EnumWrappers;
import com.google.common.collect.Lists;
import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.game.game.Announcer;
import io.github.phantamanta44.mobafort.game.hero.HeroClass;
import io.github.phantamanta44.mobafort.game.hero.HeroKit;
import io.github.phantamanta44.mobafort.game.hero.IBaseStats;
import io.github.phantamanta44.mobafort.game.hero.IHero;
import io.github.phantamanta44.mobafort.game.hero.spell.ChannelEngine;
import io.github.phantamanta44.mobafort.game.hero.spell.CooldownEngine;
import io.github.phantamanta44.mobafort.game.hero.spell.ITieredSpell;
import io.github.phantamanta44.mobafort.game.hero.spell.missile.AutoAttackMissile;
import io.github.phantamanta44.mobafort.game.hero.spell.missile.HomingMissile;
import io.github.phantamanta44.mobafort.game.hero.spell.missile.IMissileDecorator;
import io.github.phantamanta44.mobafort.game.util.FreeItems;
import io.github.phantamanta44.mobafort.lib.collection.TimedExpiryMap;
import io.github.phantamanta44.mobafort.lib.effect.ParticleUtils;
import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import io.github.phantamanta44.mobafort.lib.math.RayTrace;
import io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat;
import io.github.phantamanta44.mobafort.mfrp.stat.StatTracker;
import io.github.phantamanta44.mobafort.mfrp.status.IStatStatus;
import io.github.phantamanta44.mobafort.mfrp.status.StatusTracker;
import io.github.phantamanta44.mobafort.weaponize.projectile.SpellProjectile;
import io.github.phantamanta44.mobafort.weaponize.stat.Damage;
import io.github.phantamanta44.mobafort.weaponize.stat.Stats;
import io.github.phantamanta44.mobafort.weaponize.weapon.IWeapon;
import io.github.phantamanta44.mobafort.weaponize.weapon.WeaponTracker;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat.ReduceType.ADD;
import static io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat.ReduceType.PERC;
import static io.github.phantamanta44.mobafort.mfrp.stat.StatTracker.SRC_BASE;

public class HeroEzreal implements IHero {

    private static final ItemSig ICON = new ItemSig(Material.STONE);
    private static final IMissileDecorator<AutoAttackMissile> AA_DECOR = (p, t) ->
            ParticleUtils.dispatchEffect(p.getLocation(), EnumWrappers.Particle.ENCHANTMENT_TABLE, 3, 0.05F);

    @Override
    public String getName() {
        return "Ezreal";
    }

    @Override
    public ItemSig getIcon() {
        return ICON;
    }

    @Override
    public HeroClass getHeroClass() {
        return HeroClass.MARKSMAN;
    }

    @Override
    public IBaseStats getStats() {
        return l -> Arrays.asList(
                new ProvidedStat<>(Stats.HP_MAX, 564 + 80 * l, SRC_BASE, ADD),
                new ProvidedStat<>(Stats.HP_REGEN, 6.97 + 0.55 * l, SRC_BASE, ADD),
                new ProvidedStat<>(Stats.MANA_MAX, 403 + 42 * l, SRC_BASE, ADD),
                new ProvidedStat<>(Stats.MANA_REGEN, 8.74 + 0.65 * l, SRC_BASE, ADD),
                new ProvidedStat<>(Stats.MOVE_SPEED, 325, SRC_BASE, ADD),
                new ProvidedStat<>(Stats.AD, 58 + 3 * l, SRC_BASE, ADD),
                new ProvidedStat<>(Stats.AS, 0.625, SRC_BASE, ADD),
                new ProvidedStat<>(Stats.AS, 0.028F, SRC_BASE, PERC),
                new ProvidedStat<>(Stats.ARM, 25 + 4 * l, SRC_BASE, ADD),
                new ProvidedStat<>(Stats.MR, 30, SRC_BASE, ADD)
        );
    }

    @Override
    public HeroKit getKit() {
        return new HeroKit(S1.INSTANCE, S2.INSTANCE, S3.INSTANCE, S4.INSTANCE, false, 4.4D, 0.55D, AA_DECOR);
    }

    private static class S1 implements ITieredSpell {

        private static final S1 INSTANCE = new S1();
        private static final ItemSig TYPE = new ItemSig(FreeItems.next());

        @Override
        public ItemSig getType() {
            return TYPE;
        }

        @Override
        public TieredSpellInstance instantiate(Player player) {
            return new Instance(player);
        }

        private static class Instance extends TieredSpellInstance {

            private final Player pl;
            public final CooldownEngine cd;

            public Instance(Player player) {
                this.pl = player;
                this.cd = new CooldownEngine(player);
            }

            @Override
            public String getName() {
                return format("\u00a7fMystic Shot ({})");
            }

            @Override
            public List<String> getLore() {
                return format(
                        "\u00a7f{28|31|34|37|40} Mana / {6.5|6|5.5|5|4.5}s Cooldown",
                        "\u00a76\u00a7lActive:",
                        "\u00a79Fires a bolt of energy in a line, dealing {35|55|75|95|115} \u00a76(+110% AD) \u00a7a(+40% AP)",
                        "\u00a79physical damage to the first enemy hit. If Mystic Shot",
                        "\u00a79successfully hits an enemy, all of Ezreal's ability",
                        "\u00a79cooldowns are refreshed by 1.5 seconds."
                );
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
                    if (Stats.expendMana(pl, 28 + level * 3)) {
                        new Missile(pl.getEyeLocation()).dispatch(); // TODO sfx
                        cd.cooldown(130 - 10 * level);
                    }
                    else
                        Announcer.player("You don't have enough mana.", pl);
                }
                else
                    Announcer.player("You cannot use this right now.", pl);
            }

            private class Missile extends SpellProjectile {

                private Missile(Location loc) {
                    super(loc, loc.getDirection().multiply(0.37D), 0.25D, pl.getUniqueId(), CollisionCriteria.ENTITY);
                }

                @Override
                public void onHit(CollisionCriteria col, List<Entity> ents) {
                    LivingEntity hit = ents.stream() // TODO Check if target is on own team
                            .filter(e -> e instanceof LivingEntity)
                            .map(e -> (LivingEntity)e)
                            .findAny().orElse(null);
                    if (hit != null) {
                        new Damage(35 + 20 * level, Damage.DamageType.PHYSICAL)
                                .withDmg(Stats.AD, 1.1D)
                                .withDmg(Stats.AP, 0.4D)
                                .deal(pl, hit);
                        WeaponTracker.get(getSource()).forEach(w -> {
                            try {
                                ((CooldownEngine)w.getClass().getDeclaredField("cd").get(w)).subtract(30L);
                            } catch (NoSuchFieldException | IllegalAccessException ignored) {
                                ignored.printStackTrace();
                            }
                        });
                        kill();
                    }
                }

                @Override
                public void tick(long tick) {
                    Location loc = getLocation();
                    ParticleUtils.dispatchEffect(loc, EnumWrappers.Particle.ENCHANTMENT_TABLE, 3, 0.07F);
                    ParticleUtils.dispatchEffect(loc, EnumWrappers.Particle.CRIT_MAGIC, 3, 0.07F);
                    if (getTimeAlive() > 15L)
                        kill();
                }

            }

        }

    }

    private static class S2 implements ITieredSpell {

        private static final S2 INSTANCE = new S2();
        private static final ItemSig TYPE = new ItemSig(FreeItems.next());
        private static final String STATUS_ID = "heroEzrealEssenceFlux";
        private static final TimedExpiryMap<UUID, ProvidedStat<?>> providedStats = new TimedExpiryMap<>(GamePlugin.INSTANCE, 100L);

        private S2() {
            StatusTracker.registerStatus(new IStatStatus() {
                @Override
                public Collection<ProvidedStat<?>> getProvidedStats(Player player, int stacks) {
                    return Collections.singletonList(providedStats.get(player.getUniqueId()));
                }

                @Override
                public String getId() {
                    return STATUS_ID;
                }

                @Override
                public String getName() {
                    return "Essence Flux";
                }

                @Override
                public String getDescription() {
                    return "Gain bonus attack speed.";
                }

                @Override
                public boolean isBuff() {
                    return true;
                }

                @Override
                public long getDuration() {
                    return 100L;
                }
            });
        }

        @Override
        public ItemSig getType() {
            return TYPE;
        }

        @Override
        public TieredSpellInstance instantiate(Player player) {
            return new Instance(player);
        }

        private static class Instance extends TieredSpellInstance {

            private final Player pl;
            public final CooldownEngine cd;

            public Instance(Player player) {
                this.pl = player;
                this.cd = new CooldownEngine(player);
            }

            @Override
            public String getName() {
                return format("\u00a7fEssence Flux ({})");
            }

            @Override
            public List<String> getLore() {
                return format(
                        "\u00a7f{50|60|70|80|90} Mana / 9s Cooldown",
                        "\u00a76\u00a7lActive:",
                        "\u00a79Fires a wave of energy in a line, dealing {70|115|160|205|250} \u00a7a(+80% AP)",
                        "\u00a79magic damage to all enemy heroes struck, and granting",
                        "\u00a79all allied heroes {20|25|30|35|40}% bonus attack speed",
                        "\u00a79for 5 seconds."
                );
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
                    if (Stats.expendMana(pl, 50 + level * 10)) {
                        new Missile(pl.getEyeLocation()).dispatch(); // TODO sfx
                        cd.cooldown(180);
                    }
                    else
                        Announcer.player("You don't have enough mana.", pl);
                }
                else
                    Announcer.player("You cannot use this right now.", pl);
            }

            private class Missile extends SpellProjectile {

                private Missile(Location loc) {
                    super(loc, loc.getDirection().multiply(0.32D), 0.4D, pl.getUniqueId(), CollisionCriteria.ENTITY);
                }

                @Override
                public void onHit(CollisionCriteria col, List<Entity> ents) {
                    Set<UUID> seen = new HashSet<>();
                    ents.stream()
                            .filter(e -> e instanceof Player && !seen.contains(e.getUniqueId()))
                            .map(e -> (Player)e)
                            .filter(p -> GamePlugin.getEngine().areFriendly(p, pl))
                            .peek(p -> seen.add(p.getUniqueId()))
                            .peek(p -> providedStats.put(p.getUniqueId(),
                                    new ProvidedStat<>(Stats.AS, 0.2F + 0.05F * level, StatTracker.SRC_STATUS, ProvidedStat.ReduceType.PERC)))
                            .forEach(p -> StatusTracker.inflict(p, STATUS_ID));
                    ents.stream()
                            .filter(e -> e instanceof LivingEntity && !seen.contains(e.getUniqueId())) // TODO Check if target is on own team
                            .peek(e -> seen.add(e.getUniqueId()))
                            .map(e -> (LivingEntity)e)
                            .forEach(e -> new Damage(70 + 45 * level, Damage.DamageType.MAGIC).withDmg(Stats.AP, 0.8D).deal(pl, e));
                }

                @Override
                public void tick(long tick) {
                    ParticleUtils.dispatchEffect(getLocation(), EnumWrappers.Particle.CRIT, 7, 0.09F);
                    if (getTimeAlive() > 15L)
                        kill();
                }

            }

        }

    }

    private static class S3 implements ITieredSpell {

        private static final S3 INSTANCE = new S3();
        private static final ItemSig TYPE = new ItemSig(FreeItems.next());

        @Override
        public ItemSig getType() {
            return TYPE;
        }

        @Override
        public TieredSpellInstance instantiate(Player player) {
            return new Instance(player);
        }

        private static class Instance extends TieredSpellInstance {

            private final Player pl;
            public final CooldownEngine cd;

            public Instance(Player player) {
                this.pl = player;
                this.cd = new CooldownEngine(player);
            }

            @Override
            public String getName() {
                return format("\u00a7fArcane Shift ({})");
            }

            @Override
            public List<String> getLore() {
                return format(
                        "\u00a7f90 Mana / {19|17.5|16|14.5|13}s Cooldown",
                        "\u00a76\u00a7lActive:",
                        "\u00a79Blinks to the target location, firing a homing bolt",
                        "\u00a79that deals {75|125|175|225|275} \u00a76(+50% Bonus AD) \u00a7a(+75% AP) \u00a79magic",
                        "\u00a79damage to the nearest enemy unit."
                );
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
                    if (Stats.expendMana(pl, 90)) {
                        RayTrace trace = new RayTrace(pl.getLocation(), pl.getLocation().getDirection().setY(0), 4D, 6);
                        List<Location> traceLocs = Lists.newArrayList(trace);
                        Location finalLoc = null;
                        for (int i = traceLocs.size() - 1; i >= 0; i--) {
                            if (!traceLocs.get(i).getBlock().getType().isSolid()) {
                                finalLoc = traceLocs.get(i);
                                break;
                            }
                        }
                        for (int i = 0; i < 8; i++)
                            ParticleUtils.colourEffect(pl.getLocation(), EnumWrappers.Particle.SPELL_MOB, 1F, 1F, 0F, 1F);
                        if (finalLoc != null)
                            pl.teleport(finalLoc.setDirection(pl.getLocation().getDirection()));
                        for (int i = 0; i < 8; i++)
                            ParticleUtils.colourEffect(pl.getLocation(), EnumWrappers.Particle.SPELL_MOB, 1F, 1F, 0F, 1F);
                        LivingEntity nearest = (LivingEntity)pl.getNearbyEntities(3.6D, 5D, 3.6D).stream() // TODO don't target teammates
                                .filter(e -> e instanceof LivingEntity)
                                .sequential()
                                .sorted((a, b) -> (int)Math.floor(a.getLocation().distanceSquared(b.getLocation())))
                                .findFirst().orElse(null);
                        if (nearest != null)
                            new Missile(nearest).dispatch(); // TODO sfx
                        cd.cooldown(380 - 30 * level);
                    }
                    else
                        Announcer.player("You don't have enough mana.", pl);
                }
                else
                    Announcer.player("You cannot use this right now.", pl);
            }

            private class Missile extends HomingMissile {

                private final LivingEntity target;

                private Missile(LivingEntity target) {
                    super(pl.getLocation(), 0.48D, 0D, pl.getUniqueId(), CollisionCriteria.NONE);
                    this.target = target;
                }

                @Override
                protected Vector getTarget() {
                    return target.getLocation().toVector();
                }

                @Override
                protected void onReachTarget() {
                    new Damage(75 + 50 * level, Damage.DamageType.MAGIC)
                            .withDmg(Stats.BONUS_AD, 0.5D)
                            .withDmg(Stats.AP, 0.75D)
                            .deal(pl, target);
                }

                @Override
                public void onHit(CollisionCriteria col, List<Entity> ents) {
                    // NO-OP
                }

                @Override
                public void tick(long tick) {
                    super.tick(tick);
                    ParticleUtils.dispatchEffect(getLocation(), EnumWrappers.Particle.CRIT, 3, 0.05F);
                }

            }

        }

    }

    private static class S4 implements ITieredSpell {

        private static final S4 INSTANCE = new S4();
        private static final ItemSig TYPE = new ItemSig(FreeItems.next());

        @Override
        public ItemSig getType() {
            return TYPE;
        }

        @Override
        public TieredSpellInstance instantiate(Player player) {
            return new Instance(player);
        }

        private static class Instance extends TieredSpellInstance {

            private final Player pl;
            public final CooldownEngine cd;
            private final ChannelEngine ch;

            public Instance(Player player) {
                this.pl = player;
                this.cd = new CooldownEngine(player);
                this.ch = new ChannelEngine(player);
            }

            @Override
            public String getName() {
                return format("\u00a7fTrueshot Barrage ({})");
            }

            @Override
            public List<String> getLore() {
                return format(
                        "\u00a7f100 Mana / 120s Cooldown",
                        "\u00a76\u00a7lActive:",
                        "\u00a79After gathering energy for 1 second, Ezreal fires an",
                        "\u00a79energy projectile in the target direction, dealing",
                        "\u00a79{350|500|650} \u00a76(+100% Bonus AD) \u00a7a(+90% AP) \u00a79magic damage to enemies",
                        "\u00a79it passes through. Each enemy hit reduces the projectile's",
                        "\u00a79damage by 10%, down to a minimum 30% damage."
                );
            }

            @Override
            public int getStackSize() {
                return 1;
            }

            @Override
            public String getHudInfo() {
                return ch.isChanneling() ? ch.getBarRepresentation() : (cd.offCooldown() ? null : cd.getBarRepresentation());
            }

            @Override
            public IWeapon getTemplate() {
                return INSTANCE;
            }

            @Override
            public void onInteract(PlayerInteractEvent event) {
                if (cd.offCooldown() && !ch.isChanneling()) {
                    if (Stats.expendMana(pl, 100)) {
                        ch.channel(20L).setInterruptible(false).onComplete(() -> new Missile(pl.getEyeLocation()).dispatch()); // TODO sfx
                        cd.cooldown(2400L);
                    }
                    else
                        Announcer.player("You don't have enough mana.", pl);
                }
                else
                    Announcer.player("You cannot use this right now.", pl);
            }

            private class Missile extends SpellProjectile {

                private final Set<UUID> seen;

                private Missile(Location loc) {
                    super(loc, loc.getDirection().setY(0).normalize().multiply(0.5D), 1.36D, pl.getUniqueId(), CollisionCriteria.ENTITY);
                    seen = new HashSet<>();
                }

                @Override
                public void onHit(CollisionCriteria col, List<Entity> ents) {
                    ents.stream()
                            .filter(e -> e instanceof LivingEntity && !seen.contains(e.getUniqueId())) // TODO Check if target is on own team
                            .map(e -> (LivingEntity)e)
                            .forEach(e -> {
                                double mult = Math.max(1D - 0.1D * seen.size(), 0.3D);
                                new Damage((mult * (350 + 150 * level)), Damage.DamageType.MAGIC)
                                        .withDmg(Stats.BONUS_AD, mult)
                                        .withDmg(Stats.AP, mult * 0.9D)
                                        .deal(pl, e);
                                seen.add(e.getUniqueId());
                            });
                }

                @Override
                public void tick(long tick) {
                    Vector perp = getVelocity().clone().setY(0).normalize();
                    double prevX = perp.getX();
                    perp.setX(perp.getZ()).setZ(-prevX);
                    for (int i = -1; i <= 1; i++)
                        ParticleUtils.dispatchEffect(getLocation().add(perp.multiply(i)), EnumWrappers.Particle.CRIT, 6, 0.5F);
                    ParticleUtils.dispatchEffect(getLocation(), EnumWrappers.Particle.ENCHANTMENT_TABLE, 8, 1.36F);
                    if (getTimeAlive() > 256L)
                        kill();
                }

            }

        }

    }

}
