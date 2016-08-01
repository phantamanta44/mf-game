package io.github.phantamanta44.mobafort.game;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.phantamanta44.mobafort.game.command.CommandItems;
import io.github.phantamanta44.mobafort.game.command.CommandQueue;
import io.github.phantamanta44.mobafort.game.command.CommandStats;
import io.github.phantamanta44.mobafort.game.command.CommandUnqueue;
import io.github.phantamanta44.mobafort.game.game.GameEngine;
import io.github.phantamanta44.mobafort.game.item.impl.Tier1RawItems;
import io.github.phantamanta44.mobafort.mfrp.item.IItem;
import io.github.phantamanta44.mobafort.mfrp.item.ItemRegistry;
import org.bukkit.plugin.java.JavaPlugin;

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
		registerCommands();
	}

	@Override
	public void onDisable() {
		engine.dispose();
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

	private void registerCommands() {
		getCommand("items").setExecutor(new CommandItems());
		getCommand("queue").setExecutor(new CommandQueue());
		getCommand("stats").setExecutor(new CommandStats());
		getCommand("unqueue").setExecutor(new CommandUnqueue());
	}

	public static GameEngine getEngine() {
		return INSTANCE.engine;
	}

	public static StateMachine getStater() {
		return INSTANCE.stater;
	}

}
