package io.github.phantamanta44.mobafort.game.item;

import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import io.github.phantamanta44.mobafort.mfrp.item.IItem;
import org.bukkit.Material;

public abstract class AbstractItem implements IItem {

	private final ItemSig type;

	public AbstractItem(Material mat, int meta) {
		this.type = new ItemSig(mat, meta);
	}

	@Override
	public ItemSig getType() {
		return type;
	}

}
