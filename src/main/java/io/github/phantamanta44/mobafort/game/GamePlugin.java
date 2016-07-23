package io.github.phantamanta44.mobafort.game;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import io.github.phantamanta44.mobafort.game.item.impl.ItemLongSword;
import io.github.phantamanta44.mobafort.mfrp.item.IItem;
import io.github.phantamanta44.mobafort.mfrp.item.ItemRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public class GamePlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		registerItemImplementations();
	}

	@Override
	public void onDisable() {

	}

	private void registerItemImplementations() {
		if (ItemRegistry.get(new ItemLongSword().getType()) == null) {
			new FastClasspathScanner("io.github.phantamanta44.mobafort.game.item.impl").matchAllClasses(c -> {
				try {
					ItemRegistry.register((IItem)c.newInstance());
				} catch (Exception ignored) { }
			}).scan();
		}
	}

}
