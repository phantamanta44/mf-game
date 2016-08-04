package io.github.phantamanta44.mobafort.game.game;

import io.github.phantamanta44.mobafort.game.GamePlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Announcer {

	public static final String PREF_GLOBAL = "\u00a7a[ALL] \u00a7r";
	public static final String PREF_TEAM_RED = "\u00a7c[TEAM] \u00a7r";
	public static final String PREF_TEAM_BLUE = "\u00a79[TEAM] \u00a7r";
	public static final String PREF_PLAYER = "\u00a78[\u00a74!\u00a78] \uaa07r";

	public static void global(String msg) {
		Bukkit.getServer().broadcastMessage(PREF_GLOBAL + msg);
	}

	public static void game(String msg) {
		String withPref = PREF_GLOBAL + msg;
		GamePlugin.getEngine().getPlayers().forEach(e -> Bukkit.getServer().getPlayer(e.getKey()).sendMessage(withPref));
	}

	public static void team(String msg, Team team) {
		String withPref = (team == Team.RED ? PREF_TEAM_RED : PREF_TEAM_BLUE) + msg;
		GamePlugin.getEngine().getPlayers(team).forEach(e -> Bukkit.getServer().getPlayer(e.getKey()).sendMessage(withPref));
	}

	public static void player(String msg, UUID id) {
		player(msg, Bukkit.getServer().getPlayer(id));
	}

	public static void player(String msg, Player player) {
		player.sendMessage(PREF_PLAYER + msg);
	}

	public static String chat(Player pl, String msg) {
		return pl.getName() + ": " + msg;
	}

}
