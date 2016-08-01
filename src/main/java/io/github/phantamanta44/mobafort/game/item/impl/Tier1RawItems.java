package io.github.phantamanta44.mobafort.game.item.impl;

import io.github.phantamanta44.mobafort.game.item.BasicStatItem;
import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat;
import io.github.phantamanta44.mobafort.mfrp.stat.StatTracker;
import io.github.phantamanta44.mobafort.weaponize.stat.Stats;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;

public class Tier1RawItems {

	public static class BerylShard extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.EMERALD);
		public static final String ID = "1beryl", NAME = "\u00a7rBeryl Shard";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+15 Ability Power");
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.AP, 15, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public BerylShard() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class BasinStone extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.CLAY_BRICK);
		public static final String ID = "1basin", NAME = "\u00a7rBasin Stone";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+250 Mana");
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.MANA_MAX, 250, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public BasinStone() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class LeatherGloves extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.LEATHER);
		public static final String ID = "1gloves", NAME = "\u00a7rLeather Gloves";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+15 Armour");
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.ARM, 15, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public LeatherGloves() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class ShortSword extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.IRON_INGOT);
		public static final String ID = "1shortsword", NAME = "\u00a7rShortsword";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+10 Attack Damage");
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.BONUS_AD, 10, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public ShortSword() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class BloodVial extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.GOLD_INGOT);
		public static final String ID = "1bloodvial", NAME = "\u00a7rSanguine Vial";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+150 Health");
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.HP_MAX, 150, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public BloodVial() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}
	
}
