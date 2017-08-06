package io.github.phantamanta44.mobafort.game;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.phantamanta44.mobafort.game.command.*;
import io.github.phantamanta44.mobafort.game.event.InventoryManipulationHandler;
import io.github.phantamanta44.mobafort.game.event.MechanicSuppressor;
import io.github.phantamanta44.mobafort.game.event.SpawnHandler;
import io.github.phantamanta44.mobafort.game.game.Announcer;
import io.github.phantamanta44.mobafort.game.game.GameEngine;
import io.github.phantamanta44.mobafort.game.game.Team;
import io.github.phantamanta44.mobafort.game.gui.GuiHandler;
import io.github.phantamanta44.mobafort.game.hero.HeroClass;
import io.github.phantamanta44.mobafort.game.hero.IHero;
import io.github.phantamanta44.mobafort.game.hero.spell.AutoAttackSpell;
import io.github.phantamanta44.mobafort.game.hero.spell.ChannelEngine;
import io.github.phantamanta44.mobafort.game.hero.spell.ITieredSpell;
import io.github.phantamanta44.mobafort.game.item.impl.Tier1RawItems;
import io.github.phantamanta44.mobafort.game.map.MapLoader;
import io.github.phantamanta44.mobafort.lib.collection.OneToManyMap;
import io.github.phantamanta44.mobafort.mfrp.item.IItem;
import io.github.phantamanta44.mobafort.mfrp.item.ItemRegistry;
import io.github.phantamanta44.mobafort.weaponize.weapon.WeaponRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GamePlugin extends JavaPlugin {

    public static GamePlugin INSTANCE;

    private StateMachine stater;
    private GameEngine engine;
    private GuiHandler guiHandler;
    private LobbyManager lobbies;
    private OneToManyMap<HeroClass, IHero, Set<IHero>> heroRegistry;

    @Override
    public void onEnable() {
        INSTANCE = this;
        engine = new GameEngine();
        stater = new StateMachine(engine);
        guiHandler = new GuiHandler();
        saveDefaultConfig();
        lobbies = new LobbyManager(getConfig().getConfigurationSection("lobby"));
        heroRegistry = new OneToManyMap<>(HashSet::new);
        registerItemImplementations();
        registerHeroImplementations();
        registerCommands();
        Bukkit.getServer().getPluginManager().registerEvents(new SpawnHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new MechanicSuppressor(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new InventoryManipulationHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(guiHandler, this);
        ChannelEngine.init();
        MapLoader.load(new File(getDataFolder(), "maps.json"));
    }

    @Override
    public void onDisable() {
        engine.dispose();
        HandlerList.unregisterAll(this);
    }

    private void registerItemImplementations() {
        if (ItemRegistry.get(Tier1RawItems.BasinStone.ID) == null) {
            new FastClasspathScanner("io.github.phantamanta44.mobafort.game.item.impl")
                    .matchAllClasses(c -> {
                        if (IItem.class.isAssignableFrom(c)) {
                            try {
                                ItemRegistry.register((IItem)c.newInstance());
                            } catch (Exception ignored) { }
                        }
                    }).scan();
        }
    }

    private void registerHeroImplementations() {
        new FastClasspathScanner("io.github.phantamanta44.mobafort.game.hero.impl")
                .matchAllClasses(c -> {
                    if (IHero.class.isAssignableFrom(c)) {
                        try {
                            IHero h = (IHero)c.newInstance();
                            for (ITieredSpell spell : h.getKit().getSpells())
                                WeaponRegistry.register(spell);
                            heroRegistry.put(h.getHeroClass(), h);
                        } catch (Exception ignored) { }
                    }
                }).scan();
        WeaponRegistry.register(AutoAttackSpell.INSTANCE);
    }

    private void registerCommands() {
        getCommand("queue").setExecutor(new CommandQueue());
        getCommand("unqueue").setExecutor(new CommandUnqueue());
        getCommand("class").setExecutor(new CommandClass());
        getCommand("items").setExecutor(new CommandItems());
        getCommand("stats").setExecutor(new CommandStats());
        getCommand("mia").setExecutor(new CommandChatAugment("\u00a7e\u00a7l/mia \u00a7e",
                (p, m) -> Announcer.team(m, engine.getPlayer(p).getTeam())));
        getCommand("omw").setExecutor(new CommandChatAugment("\u00a7b\u00a7l/omw \u00a7b",
                (p, m) -> Announcer.team(m, engine.getPlayer(p).getTeam())));
        getCommand("caution").setExecutor(new CommandChatAugment("\u00a74\u00a7l/caution \u00a74",
                (p, m) -> Announcer.team(m, engine.getPlayer(p).getTeam())));
        getCommand("helpme").setExecutor(new CommandChatAugment("\u00a7d\u00a7l/helpme \u00a7d",
                (p, m) -> Announcer.team(m, engine.getPlayer(p).getTeam())));
        getCommand("all").setExecutor(new CommandChatAugment("", (p, m) -> Announcer.game(m)));
    }

    public static GameEngine getEngine() {
        return INSTANCE.engine;
    }

    public static StateMachine getStater() {
        return INSTANCE.stater;
    }

    public static GuiHandler getGuiHandler() {
        return INSTANCE.guiHandler;
    }

    public static LobbyManager getLobbies() {
        return INSTANCE.lobbies;
    }

    public static OneToManyMap<HeroClass, IHero, Set<IHero>> getHeros() {
        return INSTANCE.heroRegistry;
    }

    public static class LobbyManager {

        private final Location mainLobby;
        private final Location gameLobbyRed;
        private final Location gameLobbyBlue;

        public LobbyManager(ConfigurationSection config) {
            mainLobby = getLocation(config.getConfigurationSection("main"));
            gameLobbyRed = getLocation(config.getConfigurationSection("red"));
            gameLobbyBlue = getLocation(config.getConfigurationSection("blue"));
        }

        public Location getMainLobby() {
            return mainLobby;
        }

        public Location getTeamLobby(Team team) {
            switch (team) {
                case RED:
                    return gameLobbyRed;
                case BLUE:
                    return gameLobbyBlue;
            }
            return mainLobby;
        }

        public void teleportToMain(Collection<Player> targets) {
            targets.forEach(p -> p.teleport(mainLobby));
        }

        public void teleportToDraft() {
            getEngine().getPlayers().forEach(e ->
                    Bukkit.getServer().getPlayer(e.getKey()).teleport(getTeamLobby(e.getValue().getTeam())));
        }

        public void teleportToDraft(Player player) {
            player.teleport(getTeamLobby(getEngine().getPlayer(player).getTeam()));
        }

        private static Location getLocation(ConfigurationSection sub) {
            List<Integer> coords = sub.getIntegerList("coords");
            return new Location(Bukkit.getServer().getWorld(sub.getString("world")),
                    coords.get(0), coords.get(1), coords.get(2));
        }

    }
}
