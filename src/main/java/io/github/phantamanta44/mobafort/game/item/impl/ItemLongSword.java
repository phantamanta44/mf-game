package io.github.phantamanta44.mobafort.game.item.impl;

import io.github.phantamanta44.mobafort.game.item.RawMaterialItem;
import io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat;
import io.github.phantamanta44.mobafort.mfrp.stat.StatTracker;
import io.github.phantamanta44.mobafort.weaponize.stat.Stats;
import org.bukkit.Material;

import java.util.Collections;
import java.util.List;

public class ItemLongSword extends RawMaterialItem {

	private static final ProvidedStat[] STATS = new ProvidedStat[] {
		new ProvidedStat<>(Stats.AD, 10, StatTracker.SRC_ITEM, ProvidedStat.ReduceType.ADD)
	};
	private static final List<String> LORE = Collections.singletonList("\u00a7a+10 Attack Damage");

	public ItemLongSword() {
		super(Material.IRON_SWORD, 0, STATS);
	}

	@Override
	protected String getName() {
		return "\u00a7rLongsword";
	}

	@Override
	protected List<String> getLore() {
		return LORE;
	}

}
