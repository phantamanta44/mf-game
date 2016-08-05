package io.github.phantamanta44.mobafort.game.hero.spell;

import io.github.phantamanta44.mobafort.weaponize.Weaponize;
import org.bukkit.entity.Player;

public class SpellTimeEngine {

	private final Player player;
	private final boolean cdr;

	private long start = -1L;
	private int len = -1;

	public SpellTimeEngine(Player player) {
		this(player, false);
	}

	public SpellTimeEngine(Player player, boolean cdr) {
		this.player = player;
		this.cdr = cdr;
	}

	public boolean attempt(int length) {
		long now = Weaponize.INSTANCE.getTick();
		if (now - start <= len)
			return false;
	}

}
