package io.github.phantamanta44.mobafort.game.item;

import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import io.github.phantamanta44.mobafort.mfrp.stat.ProvidedStat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class UniqueStatItem extends AbstractItem {

	private final List<ProvidedStat<?>> statsCommon;
	private final Map<String, ProvidedStat<?>> statsUnique;
	private final String name;
	private final List<String> lore;

	public UniqueStatItem(String id, ItemSig sig, String name, List<String> lore, ProvidedStat<?>[] common, Object[] unique) {
		super(id, sig);
		this.name = name;
		this.lore = lore;
		this.statsCommon = Arrays.asList(common);
		this.statsUnique = new HashMap<>();
		for (int i = 0; i < unique.length; i += 2)
			this.statsUnique.put((String)unique[i], (ProvidedStat<?>)unique[i + 1]);
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
		return statsCommon;
	}

	@Override
	public Map<String, ProvidedStat<?>> getUniqueStats(Player player, ItemStack itemStack) {
		return statsUnique;
	}

}
