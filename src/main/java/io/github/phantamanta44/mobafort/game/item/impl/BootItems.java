package io.github.phantamanta44.mobafort.game.item.impl;

import io.github.phantamanta44.mobafort.game.item.UniqueStatItem;
import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

import static io.github.phantamanta44.mobafort.mfrp.stat.StatTracker.SRC_ITEM;
import static io.github.phantamanta44.mobafort.weaponize.stat.Stats.*;

public class BootItems {

	public static class LeatherBoots extends UniqueStatItem {

		public static final ItemSig SIG = new ItemSig(Material.ARROW);
		public static final String ID = "bboots", NAME = "\u00a7rLeather Boots";
		private static final List<String> LORE = Arrays.asList(
				"\u00a75\u00a7lUNIQUE Passive - Swift:",
				"\u00a7b+25 Movement Speed"
		);
		private static final Object[] UNQ = new Object[] {
				"swift", new ProvidedStat<>(MOVE_SPEED, 25, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public LeatherBoots() {
			super(ID, SIG, NAME, LORE, new ProvidedStat[0], UNQ);
		}

	}

	public static class ArmourBoots extends UniqueStatItem {

		public static final ItemSig SIG = new ItemSig(Material.ARROW);
		public static final String ID = "barmourboots", NAME = "\u00a7rIronshod Treads";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+30 Armour", "",
				"\u00a75\u00a7lUNIQUE Passive - Swift:",
				"\u00a7b+45 Movement Speed"
		);
		private static final ProvidedStat<?>[] CMN = new ProvidedStat<?>[] {
				new ProvidedStat<>(ARM, 30, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};
		private static final Object[] UNQ = new Object[] {
				"swift", new ProvidedStat<>(MOVE_SPEED, 45, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public ArmourBoots() {
			super(ID, SIG, NAME, LORE, CMN, UNQ);
		}

	}

	public static class MagicResistBoots extends UniqueStatItem {

		public static final ItemSig SIG = new ItemSig(Material.ARROW);
		public static final String ID = "bmrboots", NAME = "\u00a7rNullifying Treads";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+25 Magic Resist", "",
				"\u00a75\u00a7lUNIQUE Passive - Swift:",
				"\u00a7b+45 Movement Speed"
		);
		private static final ProvidedStat<?>[] CMN = new ProvidedStat<?>[] {
				new ProvidedStat<>(MR, 25, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};
		private static final Object[] UNQ = new Object[] {
				"swift", new ProvidedStat<>(MOVE_SPEED, 45, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public MagicResistBoots() {
			super(ID, SIG, NAME, LORE, CMN, UNQ);
		}

	}

	public static class AttackSpeedBoots extends UniqueStatItem {

		public static final ItemSig SIG = new ItemSig(Material.ARROW);
		public static final String ID = "basboots", NAME = "\u00a7rStriker's Shoes";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+35% Attack Speed", "",
				"\u00a75\u00a7lUNIQUE Passive - Swift:",
				"\u00a7b+45 Movement Speed"
		);
		private static final ProvidedStat<?>[] CMN = new ProvidedStat<?>[] {
				new ProvidedStat<>(MR, 35, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};
		private static final Object[] UNQ = new Object[] {
				"swift", new ProvidedStat<>(MOVE_SPEED, 45, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public AttackSpeedBoots() {
			super(ID, SIG, NAME, LORE, CMN, UNQ);
		}

	}

	public static class CritBoots extends UniqueStatItem {

		public static final ItemSig SIG = new ItemSig(Material.ARROW);
		public static final String ID = "bcritboots", NAME = "\u00a7rAssassin's Treads";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+15% Critical Strike Chance", "",
				"\u00a75\u00a7lUNIQUE Passive - Swift:",
				"\u00a7b+50 Movement Speed"
		);
		private static final ProvidedStat<?>[] CMN = new ProvidedStat<?>[] {
				new ProvidedStat<>(CRIT_CHANCE, 0.15F, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};
		private static final Object[] UNQ = new Object[] {
				"swift", new ProvidedStat<>(MOVE_SPEED, 50, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public CritBoots() {
			super(ID, SIG, NAME, LORE, CMN, UNQ);
		}

	}

}
