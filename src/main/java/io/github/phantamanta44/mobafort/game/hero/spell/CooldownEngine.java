package io.github.phantamanta44.mobafort.game.hero.spell;

import io.github.phantamanta44.mobafort.mfrp.stat.StatTracker;
import io.github.phantamanta44.mobafort.weaponize.Weaponize;
import io.github.phantamanta44.mobafort.weaponize.stat.Stats;
import org.bukkit.entity.Player;

public class CooldownEngine {

	private final Player player;
	private final boolean reduce;

	private long cdStart = -1L;
	private long length = 0L;

	public CooldownEngine(Player player) {
		this(player, true);
	}

	public CooldownEngine(Player player, boolean reduce) {
		this.player = player;
		this.reduce = reduce;
	}

	public boolean offCooldown() {
		return Weaponize.INSTANCE.getTick() - cdStart > length;
	}

	public void cooldown(long len) {
		cdStart = Weaponize.INSTANCE.getTick();
		if (reduce)
			length = (long)((float)len * (1F - StatTracker.getStat(player, Stats.CDR).getValue()));
		else
			length = len;
	}

	public void subtract(long amt) {
		length -= amt;
	}

	public void multiply(float mult) {
		length = (long)((float)length * mult);
	}

	public long getRemaining() {
		return length - (Weaponize.INSTANCE.getTick() - cdStart);
	}

}
