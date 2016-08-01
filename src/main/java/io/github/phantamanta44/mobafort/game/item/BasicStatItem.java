package io.github.phantamanta44.mobafort.game.item;

import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class BasicStatItem extends AbstractItem {

	private final List<ProvidedStat<?>> stats;
	private final String name;
	private final List<String> lore;

	public BasicStatItem(String id, ItemSig sig, String name, List<String> lore, ProvidedStat<?>... stats) {
		super(id, sig);
		this.name = name;
		this.lore = lore;
		this.stats = Arrays.asList(stats);
	}

	@Override
	public void initialize(Player player, ItemStack stack) {
		ItemMeta meta = Bukkit.getServer().getItemFactory().getItemMeta(stack.getType());
		meta.setDisplayName(name);
		meta.setLore(lore);
		stack.setItemMeta(meta);
	}

	@Override
	public void update(long l, Player player, ItemStack stack) {
		// NO-OP
	}

	@Override
	public List<ProvidedStat<?>> getCommonStats(Player player, ItemStack stack) {
		return stats;
	}

	@Override
	public List<ProvidedStat<?>> getUniqueStats(Player player, ItemStack itemStack) {
		return Collections.emptyList();
	}

}
