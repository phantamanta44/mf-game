package io.github.phantamanta44.mobafort.game.hero.spell;

import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.lib.collection.OneToManyMap;
import io.github.phantamanta44.mobafort.lib.format.StringUtils;
import io.github.phantamanta44.mobafort.weaponize.Weaponize;
import io.github.phantamanta44.mobafort.weaponize.event.EventSpellCast;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChannelEngine {

	private static OneToManyMap<UUID, ChannelFuture, List<ChannelFuture>> channels;

	public static void init() {
		channels = new OneToManyMap<>(CopyOnWriteArrayList::new);
		Bukkit.getServer().getPluginManager().registerEvents(new CastInterceptor(), GamePlugin.INSTANCE);
		Weaponize.INSTANCE.registerTickHandler(t -> channels.forEach((p, l) -> l.removeIf(c -> {
			if (c.destTime == t) {
				c.complete();
				return true;
			}
			c.tick();
			return false;
		})));
	}

	public static void interrupt(Player player) {
		channels.get(player.getUniqueId()).stream()
				.filter(ChannelFuture::isInterruptible)
				.forEach(ChannelFuture::cancel);
	}

	private static class CastInterceptor implements Listener {

		@EventHandler
		public void onCast(EventSpellCast event) {
			if (channels.contains(event.getPlayer().getUniqueId()))
				event.setCancelled(true);
		}

	}

	private final UUID playerId;
	private ChannelFuture current;

	public ChannelEngine(Player player) {
		this.playerId = player.getUniqueId();
	}

	public ChannelFuture channel(long duration) {
		ChannelFuture future = new ChannelFuture(duration, playerId, this);
		channels.put(playerId, future);
		current = future;
		return future;
	}

	public ChannelFuture getCurrentChannel() {
		return current;
	}

	public boolean isChanneling() {
		return current != null && !current.isDone();
	}

	public String getBarRepresentation() {
		return getCurrentChannel().getBarRepresentation();
	}

	public static class ChannelFuture {

		private final long length, destTime;
		private final UUID srcId;
		private final ChannelEngine engine;
		private final Collection<Runnable> tick = new LinkedList<>();
		private final Collection<Runnable> compl = new LinkedList<>();
		private final Collection<Runnable> cancel = new LinkedList<>();
		private boolean interruptible, done;

		private ChannelFuture(long length, UUID srcId, ChannelEngine engine) {
			this.length = length;
			this.destTime = Weaponize.INSTANCE.getTick() + length;
			this.srcId = srcId;
			this.engine = engine;
			this.interruptible = true;
			this.done = false;
		}

		public ChannelFuture onTick(Runnable cb) {
			tick.add(cb);
			return this;
		}

		public ChannelFuture onComplete(Runnable cb) {
			compl.add(cb);
			return this;
		}

		public ChannelFuture onCancel(Runnable cb) {
			cancel.add(cb);
			return this;
		}

		public ChannelFuture setInterruptible(boolean canInterrupt) {
			this.interruptible = canInterrupt;
			return this;
		}

		public long getRemaining() {
			return Math.max(destTime - Weaponize.INSTANCE.getTick(), 0);
		}

		public boolean isInterruptible() {
			return interruptible;
		}

		public boolean isDone() {
			return done;
		}

		public String getBarRepresentation() {
			return StringUtils.genTimeBar((int)getRemaining(), (int)length);
		}

		private void tick() {
			tick.forEach(Runnable::run);
		}

		private void complete() {
			engine.current = null;
			done = true;
			compl.forEach(Runnable::run);
		}

		private void cancel() {
			engine.current = null;
			channels.remove(srcId, this);
			done = true;
			cancel.forEach(Runnable::run);
		}

	}

}
