package io.github.phantamanta44.mobafort.game.game;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.game.hero.IHero;
import io.github.phantamanta44.mobafort.game.hero.spell.AutoAttackSpell;
import io.github.phantamanta44.mobafort.game.hero.spell.ITieredSpell;
import io.github.phantamanta44.mobafort.game.map.IGameMap;
import io.github.phantamanta44.mobafort.mfrp.stat.StatTracker;
import io.github.phantamanta44.mobafort.weaponize.event.ItemCheckHandler;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameEngine {

    private Deque<UUID> queue;
    private BiMap<UUID, PlayerInfo> players;
    private IGameMap map;
    private DeathManager deathManager;
    private long startTime;
    private boolean gameInProgress;

    public GameEngine() {
        this.queue = new LinkedList<>();
        this.players = HashBiMap.create();
        this.map = null;
        this.deathManager = new DeathManager();
    }

    public void setMap(IGameMap map) {
        this.map = map;
    }

    public IGameMap getMap() {
        return map;
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
        for (int i = 0; i < map.getProvider().getTeamSize(); i++) {
            players.put(queue.pop(), new PlayerInfo(Team.BLUE));
            players.put(queue.pop(), new PlayerInfo(Team.RED));
        }
    }

    public void beginGame(long tick) {
        map.reset();
        startTime = tick;
        gameInProgress = true;
        players().forEach(p -> {
            initInventory(p);
            StatTracker.updateBaseStats(p, getPlayer(p).getHero().getStats().getStats(0));
            p.teleport(map.getSpawn(p));
            getPlayer(p).engageScoreboard();
        });
    }

    public boolean isInGame() {
        return gameInProgress;
    }

    public void tick(long tick) {
        long gameTime = tick - startTime; // TODO display level somewhere
        map.tick(gameTime); // TODO handle gold
        deathManager.tick(gameTime);
        Team winner = map.getWinner();
        if (winner != null)
            endGame(winner);
    }

    public void endGame(Team winner) {
        gameInProgress = false;
        players().forEach(p -> p.getInventory().clear());
        Announcer.system(winner.tag + " wins!"); // TODO make this not lame
        deathManager.release();
    }

    public void cleanUp() {
        getPlayers().forEach(e -> e.getValue().setHero(null));
        players().forEach(p -> p.setScoreboard(
                Bukkit.getServer().getScoreboardManager().getMainScoreboard()));
        GamePlugin.getLobbies().teleportToMain(players().collect(Collectors.toList()));
        players.clear();
        map.reset();
        map.cleanUp();
    }

    public void dispose() {
        // NO-OP
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

    private UUID getPlayer(PlayerInfo playerInfo) {
        return players.inverse().get(playerInfo);
    }

    public Stream<Player> players() {
        return players.keySet().stream()
                .map(Bukkit.getServer()::getPlayer)
                .filter(Objects::nonNull);
    }

    public boolean isInGame(Player player) {
        return players.containsKey(player.getUniqueId());
    }

    public boolean areFriendly(Player p, Player pl) {
        return getPlayer(p).team == getPlayer(pl).team;
    }

    private void initInventory(Player player) {
        PlayerInventory inv = player.getInventory();
        ITieredSpell[] spells = getPlayer(player).getHero().getKit().getSpells();
        for (int i = 0; i <= 3; i++)
            inv.setItem(i, spells[i].getType().construct(1));
        inv.setItem(7, new ItemStack(Material.TIPPED_ARROW, 1)); // TODO recall item
        inv.setItem(8, AutoAttackSpell.TYPE.construct(1));
        inv.setHeldItemSlot(8);
        updateWeapons(player);
    }

    public static void updateWeapons(Player player) {
        new ItemCheckHandler.CheckTask(player.getInventory(), player.getUniqueId()).run();
    }

    public DeathManager getDeathManager() {
        return deathManager;
    }

    public static class PlayerInfo {

        private final Scoreboard scoreboard;
        private final Objective sbObj;
        private final Score sbKills, sbDeaths, sbAssists, sbLevel, sbExp, sbGold;

        private Team team;
        private IHero hero;
        private int kdaK, kdaD, kdaA;
        private int gold;
        private int xp;
        private int level;

        private PlayerInfo(Team team) { // TODO leveling abilities
            this.team = team;
            this.scoreboard = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
            this.sbObj = scoreboard.registerNewObjective("In-Game", "dummy");
            this.sbObj.setDisplaySlot(DisplaySlot.SIDEBAR);
            this.sbKills = sbObj.getScore("Kills");
            this.sbDeaths = sbObj.getScore("Deaths");
            this.sbAssists = sbObj.getScore("Assists");
            this.sbLevel = sbObj.getScore("Level");
            this.sbExp = sbObj.getScore("Experience");
            this.sbGold = sbObj.getScore("Gold");
            this.kdaK = this.kdaD = this.kdaA = 0;
            this.gold = this.xp = this.level = 0;
        }

        public void engageScoreboard() {
            Bukkit.getPlayer(GamePlugin.getEngine().getPlayer(this)).setScoreboard(scoreboard);
        }

        public Team getTeam() {
            return team;
        }

        public void setHero(IHero hero) {
            this.hero = hero;
        }

        public IHero getHero() {
            return hero;
        }

        public void offsetGold(int qty) {
            gold += qty;
            sbGold.setScore(qty);
        }

        public int getGold() {
            return gold;
        }

        public void offsetXp(int qty) {
            xp += qty;
            int xpForLevel = getXpForLevel(level);
            if (xpForLevel > 0 && xp >= xpForLevel) {
                xp %= xpForLevel;
                StatTracker.updateBaseStats(
                        Bukkit.getServer().getPlayer(GamePlugin.getEngine().getPlayer(this)),
                        hero.getStats().getStats(level));
                if (getXpForLevel(++level) == -1)
                    xp = 0;
                sbLevel.setScore(level);
            }
            sbExp.setScore(xp);
        }

        public int getXp() {
            return xp;
        }

        public int getLevel() {
            return level;
        }

        public static int getXpForLevel(int level) {
            return level >= 17 ? -1 : 280 + level * 100;
        }

    }

}
