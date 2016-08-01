package io.github.phantamanta44.mobafort.game.game;

import io.github.phantamanta44.mobafort.game.map.MobaMap;
import org.bukkit.entity.Player;

import java.util.*;

public class GameEngine {

	private Deque<UUID> queue;
	private Map<UUID, PlayerInfo> players;
	private MobaMap map;
	private long startTime;

	public GameEngine() {
		queue = new LinkedList<>();
		players = new HashMap<>();
	}

	public void setMap(MobaMap map) {
		this.map = map;
	}

	public void queue(Player player) {
		queue.offer(player.getUniqueId());
	}

	public void beginGame(long tick) {
		map.reset();
		Team team = Team.BLUE;
		for (int i = 0; i < 10; i++) {
			players.put(queue.pop(), new PlayerInfo(team));
			team = Team.values()[team.ordinal() ^ 1];
		}
		startTime = tick;
	}

	public void tick(long tick) {
		long gameTime = tick - startTime;

		if (gameTime == 600L) {
			Announcer.global("Minions are now spawning.");
		} else if (gameTime == 6000L) {
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
	}

	public void dispose() {

	}

	public Set<Map.Entry<UUID, PlayerInfo>> getPlayers() {
		return players.entrySet();
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
