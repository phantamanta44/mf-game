package io.github.phantamanta44.mobafort.game.item.impl;

import io.github.phantamanta44.mobafort.game.item.BasicStatItem;
import io.github.phantamanta44.mobafort.game.util.FreeItems;
import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.github.phantamanta44.mobafort.mfrp.stat.StatTracker.SRC_ITEM;
import static io.github.phantamanta44.mobafort.weaponize.stat.Stats.*;

public class Tier1RawItems {

	public static class ShortSword extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(FreeItems.next());
		public static final String ID = "1shortsword", NAME = "\u00a7rShortsword";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+12% Attack Speed");
		private static final ProvidedStat[] STATS = new ProvidedStat[] {
				new ProvidedStat<>(AS, 0.12F, SRC_ITEM, ProvidedStat.ReduceType.PERC)
		};

		public ShortSword() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class Sledgehammer extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.COAL, 0);
		public static final String ID = "1sledgehammer", NAME = "\u00a7r+10 Attack Damage";
		private static final List<String> LORE = Collections.singletonList("\u00a7a");
		private static final ProvidedStat[] STATS = new ProvidedStat[] {
				new ProvidedStat<>(BONUS_AD, 10, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public Sledgehammer() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class SteelOrb extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.GOLD_INGOT);
		public static final String ID = "1steelorb", NAME = "\u00a7rSteel Orb";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+12% Critical Strike Chance");
		private static final ProvidedStat[] STATS = new ProvidedStat[] {
				new ProvidedStat<>(CRIT_CHANCE, 0.12F, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public SteelOrb() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class SteelRod extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.GOLD_INGOT);
		public static final String ID = "1steelrod", NAME = "\u00a7rSteel Rod";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+24 Attack Damage");
		private static final ProvidedStat[] STATS = new ProvidedStat[] {
				new ProvidedStat<>(BONUS_AD, 24, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public SteelRod() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class BarbedWire extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.GOLD_INGOT);
		public static final String ID = "1barbedwire", NAME = "\u00a7rBarbed Wire";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+5% Critical Strike Damage");
		private static final ProvidedStat[] STATS = new ProvidedStat[] {
				new ProvidedStat<>(CRIT_DMG, 0.05F, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public BarbedWire() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class BerylShard extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.COAL, 1);
		public static final String ID = "1beryl", NAME = "\u00a7rBeryl Shard";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+15 Ability Power");
		private static final ProvidedStat[] STATS = new ProvidedStat[] {
				new ProvidedStat<>(AP, 15, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public BerylShard() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class Timepiece extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.CLAY_BRICK);
		public static final String ID = "1timepiece", NAME = "\u00a7rTimepiece";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+5% Cooldown Reduction");
		private static final ProvidedStat[] STATS = new ProvidedStat[] {
				new ProvidedStat<>(CDR, 0.05F, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public Timepiece() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class BasinStone extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.CLAY_BRICK);
		public static final String ID = "1basin", NAME = "\u00a7rBasin Stone";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+250 Mana");
		private static final ProvidedStat[] STATS = new ProvidedStat[] {
				new ProvidedStat<>(MANA_MAX, 250, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public BasinStone() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class LookingGlass extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.CLAY_BRICK);
		public static final String ID = "1manamirror", NAME = "\u00a7rLooking Glass";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+50% Mana Regeneration");
		private static final ProvidedStat[] STATS = new ProvidedStat[] {
				new ProvidedStat<>(MANA_REGEN, 0.5F, SRC_ITEM, ProvidedStat.ReduceType.PERC)
		};

		public LookingGlass() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class ElderStaff extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.CLAY_BRICK);
		public static final String ID = "1estaff", NAME = "\u00a7rElder Staff";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+32 Ability Power");
		private static final ProvidedStat[] STATS = new ProvidedStat[] {
				new ProvidedStat<>(AP, 32, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public ElderStaff() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class LeatherGloves extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.LEATHER);
		public static final String ID = "1gloves", NAME = "\u00a7rLeather Gloves";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+15 Armour");
		private static final ProvidedStat[] STATS = new ProvidedStat[] {
				new ProvidedStat<>(ARM, 15, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public LeatherGloves() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class Cloak extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.CLAY_BRICK);
		public static final String ID = "1cloak", NAME = "\u00a7rCloak";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+10 Magic Resist");
		private static final ProvidedStat[] STATS = new ProvidedStat[] {
				new ProvidedStat<>(MR, 10, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public Cloak() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class BloodVial extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.GOLD_INGOT);
		public static final String ID = "1bloodvial", NAME = "\u00a7rSanguine Vial";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+150 Health");
		private static final ProvidedStat[] STATS = new ProvidedStat[] {
				new ProvidedStat<>(HP_MAX, 150, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public BloodVial() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class LifeSlice extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.CLAY_BRICK);
		public static final String ID = "1lifeslice", NAME = "\u00a7rSlice of Life";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+36% Health Regeneration");
		private static final ProvidedStat[] STATS = new ProvidedStat[] {
				new ProvidedStat<>(HP_REGEN, 0.36F, SRC_ITEM, ProvidedStat.ReduceType.PERC)
		};

		public LifeSlice() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class Lodestone extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.CLAY_BRICK);
		public static final String ID = "1lodestone", NAME = "\u00a7rLodestone";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+25% Health Regeneration",
				"\u00a7a+25% Mana Regeneration"
		);
		private static final ProvidedStat[] STATS = new ProvidedStat[] {
				new ProvidedStat<>(HP_REGEN, 0.25F, SRC_ITEM, ProvidedStat.ReduceType.PERC),
				new ProvidedStat<>(MANA_REGEN, 0.25F, SRC_ITEM, ProvidedStat.ReduceType.PERC)
		};

		public Lodestone() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}
	
}
