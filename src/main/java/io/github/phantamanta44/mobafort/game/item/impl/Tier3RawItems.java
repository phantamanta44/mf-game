package io.github.phantamanta44.mobafort.game.item.impl;

import io.github.phantamanta44.mobafort.game.item.BasicStatItem;
import io.github.phantamanta44.mobafort.game.util.FreeItems;
import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;

import static io.github.phantamanta44.mobafort.mfrp.stat.StatTracker.SRC_ITEM;
import static io.github.phantamanta44.mobafort.weaponize.stat.Stats.*;

public class Tier3RawItems {

	public static class Bloodthirst extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(FreeItems.next());
		public static final String ID = "3bloodthirst", NAME = "\u00a7rInhumanity Manifest";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+60 Attack Damage",
				"\u00a7a+18% Life Steal"
		);
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(BONUS_AD, 60, SRC_ITEM, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(LIFE_STEAL, 0.18F, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public Bloodthirst() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class Tempest extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(FreeItems.next());
		public static final String ID = "3tempest", NAME = "\u00a7rTempest";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+50% Attack Speed",
				"\u00a7a+65 Ability Power",
				"\u00a7a+8% Movement Speed"
		);
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(AS, 0.5F, SRC_ITEM, ProvidedStat.ReduceType.PERC),
				new ProvidedStat<>(AP, 65, SRC_ITEM, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(MOVE_SPEED, 0.08F, SRC_ITEM, ProvidedStat.ReduceType.PERC)
		};

		public Tempest() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

	public static class FullAuto extends BasicStatItem {

		public static final ItemSig SIG = new ItemSig(FreeItems.next());
		public static final String ID = "3fullauto", NAME = "\u00a7rFull-Auto";
		private static final List<String> LORE = Arrays.asList(
				"\u00a7a+40% Attack Speed",
				"\u00a7a+40 Attack Damage",
				"\u00a7a+20 Critical Strike Chance",
				"\u00a7a+8% Critical Strike Damage"
		);
		private static final ProvidedStat[] STATS = new ProvidedStat[]{
				new ProvidedStat<>(BONUS_AD, 40, SRC_ITEM, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(AS, 0.4F, SRC_ITEM, ProvidedStat.ReduceType.PERC),
				new ProvidedStat<>(CRIT_CHANCE, 0.2F, SRC_ITEM, ProvidedStat.ReduceType.ADD),
				new ProvidedStat<>(CRIT_DMG, 0.08F, SRC_ITEM, ProvidedStat.ReduceType.ADD)
		};

		public FullAuto() {
			super(ID, SIG, NAME, LORE, STATS);
		}

	}

}
