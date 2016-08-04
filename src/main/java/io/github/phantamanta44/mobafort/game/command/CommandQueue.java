package io.github.phantamanta44.mobafort.game.command;

import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.game.game.Announcer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandQueue implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player pl = (Player)sender;
			if (GamePlugin.getEngine().queue(pl))
				Announcer.player(String.format("You are in queue position #%d.", GamePlugin.getEngine().queueSize()), pl);
			else
				Announcer.player("You are already in queue.", pl);
			return true;
		}
		return false;
	}

}
