package io.github.phantamanta44.mobafort.game.command;

import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.game.game.Announcer;
import io.github.phantamanta44.mobafort.mfrp.stat.StatTracker;
import io.github.phantamanta44.mobafort.weaponize.stat.Stats;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStats implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player pl = (Player)sender;
			if (GamePlugin.getEngine().isInGame(pl)) {
				Player target = pl;
				if (args.length != 0)
					target = Bukkit.getPlayer(args[0]);
				if (target == null)
					Announcer.player("No player by this name was found.", pl);
				else {
					pl.sendMessage(new String[] {
						String.format("\u00a73%s's Stats:", target.getName()),
						String.format("\u00a7cAttack Damage\u00a78: %d", StatTracker.getStat(target, Stats.AD).getValue()),
						String.format("\u00a7cArmour\u00a78: %d", StatTracker.getStat(target, Stats.ARM).getValue()),
						String.format("\u00a7bAbility Power\u00a78: %d", StatTracker.getStat(target, Stats.AP).getValue()),
						String.format("\u00a7bMagic Resist\u00a78: %d", StatTracker.getStat(target, Stats.MR).getValue()),
						String.format("\u00a77Attack Speed\u00a78: %.2f", StatTracker.getStat(target, Stats.AS).getValue()),
						String.format("\u00a77Movement Speed\u00a78: %d", StatTracker.getStat(target, Stats.MOVE_SPEED).getValue())
					});
				}
			}
			else
				Announcer.player("You can only use this command in a game.", pl);
			return true;
		}
		return false;
	}

}
