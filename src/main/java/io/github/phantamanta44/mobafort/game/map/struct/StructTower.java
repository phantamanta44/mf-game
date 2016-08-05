package io.github.phantamanta44.mobafort.game.map.struct;

import io.github.phantamanta44.mobafort.game.game.Team;
import io.github.phantamanta44.mobafort.game.util.StattedDamageDummy;
import io.github.phantamanta44.mobafort.lib.math.CylBounds;
import io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat;
import io.github.phantamanta44.mobafort.mfrp.stat.StatTracker;
import io.github.phantamanta44.mobafort.weaponize.stat.Stats;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

public class StructTower extends Structure {

	public static final float RANGE = 6F;

	private final TowerType type;
	private final Team team;
	private final BlockBuild[] builds;
	private StattedDamageDummy hp;
	private long lastShot = -1L;
	private boolean fort = true, reinf = false, targetable = false;

	public StructTower(Vector pos, TowerType type, Team team, BlockBuild a, BlockBuild b, BlockBuild c) {
		super(new CylBounds(pos, 2.5F, 5F));
		this.type = type;
		this.team = team;
		this.builds = new BlockBuild[] {a, b, c};
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
		if (gameTick % 20L == 0L) {
			hp.setHealth(Math.max(hp.getHealth() + type.hpGen, hp.getMaxHealth()));
			if (gameTick == 6000L)
				fort = false;
		}
		if (gameTick % 1200L == 1199L) {
			hp.clearStats(true);
			hp.addStats(type.statProvider.apply((int)((gameTick + 1L) / 1200L)));
		}

		if (!type.beam) {
			if (gameTick - lastShot >= 24L) {
				assert true; // TODO Turret attack
				lastShot = gameTick;
			}
		} else {
			if (gameTick - lastShot >= 5L) {
				assert true; // TODO Turret attack
				lastShot = gameTick;
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
			}
		};
	}

	public enum TowerType {

		OUTER("Outer Turret", 3500, 0, false, n -> Arrays.asList(
				new ProvidedStat<>(Stats.AD, 152 + Math.min(4 * n, 180), StatTracker.SRC_BASE, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(Stats.ARM, 40 + Math.min(2 * n, 30), StatTracker.SRC_BASE, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(Stats.MR, 40 + Math.min(2 * n, 30), StatTracker.SRC_BASE, ProvidedStat.ReduceType.ADD)
		)),
		INNER("Inner Turret", 3300, 0, false, n -> {
			int n2 = Math.max(n - 8, 0), n3 = Math.max(n - 16, 0);
			return Arrays.asList(
				new ProvidedStat<>(Stats.AD, 170 + Math.min(4 * n2, 250), StatTracker.SRC_BASE, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(Stats.ARM, 40 + Math.min(2 * n3, 30), StatTracker.SRC_BASE, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(Stats.MR, 40 + Math.min(2 * n3, 30), StatTracker.SRC_BASE, ProvidedStat.ReduceType.ADD)
			);
		}),
		INHIB("Inhibitor Turret", 3300, 5, true, n -> {
			int n2 = Math.max(n - 8, 0), n3 = Math.max(n - 31, 0);
			return Arrays.asList(
					new ProvidedStat<>(Stats.AD, 170 + Math.min(4 * n2, 290), StatTracker.SRC_BASE, ProvidedStat.ReduceType.ADD),
					new ProvidedStat<>(Stats.ARM, 40 + Math.min(2 * n3, 30), StatTracker.SRC_BASE, ProvidedStat.ReduceType.ADD),
					new ProvidedStat<>(Stats.MR, 40 + Math.min(2 * n3, 30), StatTracker.SRC_BASE, ProvidedStat.ReduceType.ADD)
			);
		}),
		NEXUS("Nexus Turret", 3300, 5, true, n -> {
			int n2 = Math.max(n - 8, 0), n3 = Math.max(n - 30, 0);
			return Arrays.asList(
					new ProvidedStat<>(Stats.AD, 150 + Math.min(4 * n2, 270), StatTracker.SRC_BASE, ProvidedStat.ReduceType.ADD),
					new ProvidedStat<>(Stats.ARM, 40 + Math.min(2 * n3, 30), StatTracker.SRC_BASE, ProvidedStat.ReduceType.ADD),
					new ProvidedStat<>(Stats.MR, 40 + Math.min(2 * n3, 30), StatTracker.SRC_BASE, ProvidedStat.ReduceType.ADD)
			);
		});

		public final String name;
		public final int hp, hpGen;
		public final boolean beam;
		public final Function<Integer, Collection<ProvidedStat<?>>> statProvider;

		TowerType(String name, int hp, int hpGen, boolean beam, Function<Integer, Collection<ProvidedStat<?>>> statProvider) {
			this.name = name;
			this.hp = hp;
			this.hpGen = hpGen;
			this.beam = beam;
			this.statProvider = statProvider;
		}

	}

}
