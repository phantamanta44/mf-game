package io.github.phantamanta44.mobafort.game.event;

import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.lib.math.MathUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class InventoryManipulationHandler implements Listener {

	@EventHandler
	public void onHandSwap(PlayerSwapHandItemsEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent event) {
		event.setCancelled(true);
	}

	@EventHandler
	public void onInvInteract(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player))
			return;
		Player pl = (Player)event.getWhoClicked();
		if (GamePlugin.getEngine().isInGame(pl) && isAllowedSlot(event.getRawSlot()))
			return;
		event.setCancelled(true);
	}

	@EventHandler
	public void onSlotChange(PlayerItemHeldEvent event) {
		Player pl = event.getPlayer();
		if (!GamePlugin.getEngine().isInGame(pl))
			return;
		event.setCancelled(true);
	}

	private static boolean isAllowedSlot(int slotId) {
		return MathUtils.withinBounds(slotId, 4, 6) || MathUtils.withinBounds(slotId, 31, 33);
	}

}
