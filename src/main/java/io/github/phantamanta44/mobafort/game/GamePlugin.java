package io.github.phantamanta44.mobafort.game;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.phantamanta44.mobafort.game.command.*;
import io.github.phantamanta44.mobafort.game.event.InventoryManipulationHandler;
import io.github.phantamanta44.mobafort.game.game.Announcer;
import io.github.phantamanta44.mobafort.game.game.GameEngine;
import io.github.phantamanta44.mobafort.game.hero.IHero;
import io.github.phantamanta44.mobafort.game.hero.spell.AutoAttackSpell;
import io.github.phantamanta44.mobafort.game.hero.spell.ChannelEngine;
import io.github.phantamanta44.mobafort.game.hero.spell.ITieredSpell;
import io.github.phantamanta44.mobafort.game.item.impl.Tier1RawItems;
import io.github.phantamanta44.mobafort.game.map.MapLoader;
import io.github.phantamanta44.mobafort.mfrp.item.IItem;
import io.github.phantamanta44.mobafort.mfrp.item.ItemRegistry;
import io.github.phantamanta44.mobafort.weaponize.weapon.WeaponRegistry;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class GamePlugin extends JavaPlugin {

	public static GamePlugin INSTANCE;

	private StateMachine stater;
	private GameEngine engine;

	@Override
	public void onEnable() {
		INSTANCE = this;
		engine = new GameEngine();
		stater = new StateMachine(engine);
		registerItemImplementations();
		registerHeroImplementations();
		registerCommands();
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryManipulationHandler(), this);
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
						} catch (Exception ignored) { }
					}
				}).scan();
		WeaponRegistry.register(AutoAttackSpell.INSTANCE);
		// TODO Keep a hero registry somewhere
	}

	private void registerCommands() {
		getCommand("queue").setExecutor(new CommandQueue());
		getCommand("unqueue").setExecutor(new CommandUnqueue());
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

}
