package io.github.phantamanta44.mobafort.game.item;

import io.github.phantamanta44.mobafort.lib.item.ItemSig;
import io.github.phantamanta44.mobafort.mfrp.item.IItem;

public abstract class AbstractItem implements IItem {

	private final String id;
	private final ItemSig type;

	public AbstractItem(String id, ItemSig sig) {
		this.id = id;
		this.type = sig;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public ItemSig getType() {
		return type;
	}

}
