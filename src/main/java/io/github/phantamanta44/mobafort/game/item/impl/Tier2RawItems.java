package io.github.phantamanta44.mobafort.game.item.impl;

import io.github.phantamanta44.mobafort.game.item.BasicStatItem;
import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat;
import io.github.phantamanta44.mobafort.mfrp.stat.StatTracker;
import io.github.phantamanta44.mobafort.weaponize.stat.Stats;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tier2RawItems {

	public static class SadisticSabre extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.DIAMOND);
		public static final String ID = "2vampiresword", NAME = "\u00a7rSadistic Sabre";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+18 Attack Damage",
				"\u00a7a+8% Life Steal"
		);
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.AD, 18, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(Stats.LIFE_STEAL, 0.08F, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public SadisticSabre() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class WindBite extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.STICK);
		public static final String ID = "2windbite", NAME = "\u00a7rWind's Bite";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+12 Attack Damage",
				"\u00a7a+15 Ability Power",
				"\u00a7a+12% Critical Strike Chance"
		);
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.AD, 12, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(Stats.AP, 15, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(Stats.CRIT_CHANCE, 0.12F, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public WindBite() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class DarkCapacitor extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.NETHER_BRICK_ITEM);
		public static final String ID = "2darkcapacitor", NAME = "\u00a7rDark Capacitor";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+25 Ability Power",
				"\u00a7a+120 Mana"
		);
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.AP, 25, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(Stats.MANA_MAX, 120, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public DarkCapacitor() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class CrystalMech extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.BLAZE_POWDER);
		public static final String ID = "2crystalmech", NAME = "\u00a7rCrystalline Mechanism";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+350 Mana",
				"\u00a7a+75% Mana Regeneration"
		);
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.MANA_MAX, 350, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(Stats.MANA_REGEN, 50F, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.PERC)
		};

		public CrystalMech() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class Helioscope extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.BLAZE_ROD);
		public static final String ID = "2helioscope", NAME = "\u00a7rHelioscope";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+36 Ability Power",
				"\u00a7a+8 Magic Penetration"
		);
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.AP, 36, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(Stats.MAG_PEN, 8, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public Helioscope() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class SpectralSyringe extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.GHAST_TEAR);
		public static final String ID = "2spectralsyringe", NAME = "\u00a7rSpectral Syringe";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+280 Health",
				"\u00a7a+300 Mana"
		);
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.HP_MAX, 350, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(Stats.MANA_MAX, 350, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public SpectralSyringe() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class PrismSphere extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.BONE);
		public static final String ID = "2prismsphere", NAME = "\u00a7rPrismatic Sphere";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+24 Ability Power",
				"\u00a7a+50% Mana Regeneration"
		);
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.AP, 24, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(Stats.MANA_MAX, 0.5F, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.PERC)
		};

		public PrismSphere() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class MassiveGauntlet extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.INK_SACK);
		public static final String ID = "2massivegauntlet", NAME = "\u00a7rMassive Gauntlet";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+340 Health",
				"\u00a7a+20 Armour"
		);
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.HP_MAX, 340, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(Stats.ARM, 20, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public MassiveGauntlet() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class EnergyVoid extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.FEATHER);
		public static final String ID = "2energyvoid", NAME = "\u00a7rEnergetic Void";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+18 Ability Power",
				"\u00a7a+12 Magic Resist"
		);
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.AP, 18, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(Stats.MR, 12, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public EnergyVoid() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class LifeFlask extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.CLAY_BALL);
		public static final String ID = "2lifeflask", NAME = "\u00a7rVictuum Flask";
		private static final List<String> LORE = Collections.singletonList("\u00a7a+420 Health");
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.HP_MAX, 420, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public LifeFlask() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class CritCape extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.PAPER);
		public static final String ID = "2critcape", NAME = "\u00a7rMarauder's Cape";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+18 Armour",
				"\u00a7a+12% Critical Strike Chance"
		);
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.ARM, 18, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(Stats.CRIT_CHANCE, 0.12F, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public CritCape() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class TalismanChain extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(Material.BOWL);
		public static final String ID = "2talismanchain", NAME = "\u00a7rTalisman Chain";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+36 Ability Power",
				"\u00a7a+12 Armour"
		);
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(Stats.AP, 36, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(Stats.ARM, 12, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public TalismanChain() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}
	
}
