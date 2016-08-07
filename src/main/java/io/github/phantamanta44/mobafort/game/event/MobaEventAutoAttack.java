package io.github.phantamanta44.mobafort.game.event;

import io.github.phantamanta44.mobafort.weaponize.stat.Damage;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MobaEventAutoAttack extends Event {

	private static final HandlerList hl = new HandlerList();

	private final Player source;
	private final LivingEntity target;
	private final Damage damage;
	private boolean cancelled;

	public MobaEventAutoAttack(Player source, LivingEntity target, Damage damage) {
		this.source = source;
		this.target = target;
		this.damage = damage;
		this.cancelled = false;
	}

	public Player getSource() {
		return source;
	}

	public LivingEntity getTarget() {
		return target;
	}

	public Damage getDamage() {
		return damage;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public HandlerList getHandlers() {
		return hl;
	}

	public static MobaEventAutoAttack fire(Player source, LivingEntity target, Damage damage) {
		MobaEventAutoAttack event = new MobaEventAutoAttack(source, target, damage);
		Bukkit.getServer().getPluginManager().callEvent(event);
		return event;
	}

}
