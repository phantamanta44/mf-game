package io.github.phantamanta44.mobafort.game.game;

import io.github.phantamanta44.mobafort.game.map.MobaMap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;
import java.util.stream.Collectors;

public class GameEngine {

	private Deque<UUID> queue;
	private Map<UUID, PlayerInfo> players;
	private MobaMap map;
	private long startTime;
	private boolean gameInProgress;

	public GameEngine() {
		queue = new LinkedList<>();
		players = new HashMap<>();
	}

	public void setMap(MobaMap map) {
		this.map = map;
	}

	public boolean queue(Player player) {
		return !queue.contains(player.getUniqueId()) && queue.offer(player.getUniqueId());
	}

	public boolean dequeue(Player player) {
		return queue.remove(player.getUniqueId());
	}

	public int queueSize() {
		return queue.size();
	}

	public void assignTeams() {
		Team team = Team.BLUE;
		for (int i = 0; i < 10; i++) {
			players.put(queue.pop(), new PlayerInfo(team));
			team = Team.values()[team.ordinal() ^ 1];
		}
	}

	public void beginGame(long tick) {
		map.reset();
		startTime = tick;
		gameInProgress = true;
		players.keySet().stream()
				.map(Bukkit.getServer()::getPlayer)
				.filter(p -> p != null)
				.forEach(GameEngine::initInventory);
	}

	public boolean isInGame() {
		return gameInProgress;
	}

	public void tick(long tick) {
		long gameTime = tick - startTime;

		if (gameTime == 600L)
			Announcer.global("Minions are now spawning.");
		else if (gameTime == 6000L) {
			Announcer.global("Towers are now vulnerable.");
			// TODO: Remove turret early-game armour
		} else if (gameTime == 24000L) {
			Announcer.global("<monster> has spawned.");
			// TODO: Spawn some ultimate objective monster
		}

		if (gameTime >= 600L) {
			if (gameTime % 600L == 0L)
				assert true; // TODO: Spawn minion waves
			if (gameTime % 1800L == 1799L)
				assert true; // TODO: Level up minions (and monsters?)
		}
	}

	public void endGame() {
		players.clear();
		gameInProgress = false;
	}

	public void dispose() {

	}

	public Set<Map.Entry<UUID, PlayerInfo>> getPlayers() {
		return players.entrySet();
	}

	public Set<Map.Entry<UUID, PlayerInfo>> getPlayers(Team team) {
		return getPlayers().stream()
				.filter(p -> p.getValue().getTeam() == team)
				.collect(Collectors.toSet());
	}

	public PlayerInfo getPlayer(Player player) {
		return players.get(player.getUniqueId());
	}

	public boolean isInGame(Player player) {
		return players.containsKey(player.getUniqueId());
	}

	private static void initInventory(Player player) {
		PlayerInventory inv = player.getInventory();
		inv.setItem(7, new ItemStack(Material.TIPPED_ARROW, 1));
		inv.setHeldItemSlot(7);
	}

	public static class PlayerInfo {

		private Team team;

		private PlayerInfo(Team team) {
			this.team = team;
		}

		public Team getTeam() {
			return team;
		}

	}

}
