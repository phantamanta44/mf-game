package io.github.phantamanta44.mobafort.game.game;

import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.game.gui.impl.GuiClassSelect;
import io.github.phantamanta44.mobafort.game.hero.HeroClass;
import io.github.phantamanta44.mobafort.game.hero.IHero;
import io.github.phantamanta44.mobafort.lib.collection.CollectionUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClassSelectSession {

    private static final Scoreboard SB;
    private static final Objective SB_OBJ;
    private static final Score SB_OBJ_ENTRY;

    static {
        SB = Bukkit.getServer().getScoreboardManager().getNewScoreboard();
        SB_OBJ = SB.registerNewObjective("Class Selection", "dummy");
        SB_OBJ.setDisplaySlot(DisplaySlot.SIDEBAR);
        SB_OBJ_ENTRY = SB_OBJ.getScore("Time Remaining");
    }

    private final long creationTime;
    private Map<UUID, GuiClassSelect> guis;
    private boolean overrideReady;

    public ClassSelectSession(long tick) {
        this.creationTime = tick;
        this.guis = new HashMap<>();
        this.overrideReady = false;
        GamePlugin.getEngine().getPlayers().forEach(e -> {
            GuiClassSelect gui = new GuiClassSelect(e.getKey(), e.getValue());
            gui.player().setScoreboard(SB);
        });
    }

    public void tick(long tick) {
        long timeRemaining = creationTime + 1200 - tick;
        if (timeRemaining <= 0) {
            guis.values().stream()
                    .filter(g -> g.getGamePlayer().getHero() == null)
                    .forEach(g -> {
                        g.getGamePlayer().setHero(getRandomHero());
                        Announcer.player("You were randomly assigned "
                                + ChatColor.GRAY + g.getGamePlayer().getHero().getName()
                                + ChatColor.RESET + ".", g.player());
                    });
            overrideReady = true;
        } else {
            SB_OBJ_ENTRY.setScore((int)Math.ceil(timeRemaining / 20F));
        }
    }

    public void cleanUp() {
        guis.forEach((k, v) -> {
            v.player().closeInventory();
            v.player().setScoreboard(Bukkit.getServer().getScoreboardManager().getMainScoreboard());
        });
    }

    public boolean isAllReady() {
        return overrideReady || guis.values().stream().allMatch(GuiClassSelect::isReady);
    }

    public GuiClassSelect getForPlayer(GameEngine.PlayerInfo player) {
        return guis.values().stream()
                .filter(g -> g.getGamePlayer().equals(player))
                .findAny().orElse(null);
    }

    public void register(UUID id, GuiClassSelect gui) {
        guis.put(id, gui);
    }

    public boolean includes(Player player) {
        return guis.containsKey(player.getUniqueId());
    }

    private IHero getRandomHero() {
        return CollectionUtils.random(
                GamePlugin.getHeros().get(HeroClass.values()[(int)Math.floor(HeroClass.values().length * Math.random())]));
    }

}
