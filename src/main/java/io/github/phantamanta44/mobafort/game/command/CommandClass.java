package io.github.phantamanta44.mobafort.game.command;

import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.game.GameState;
import io.github.phantamanta44.mobafort.game.game.Announcer;
import io.github.phantamanta44.mobafort.game.game.ClassSelectSession;
import io.github.phantamanta44.mobafort.game.gui.impl.GuiClassSelect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandClass implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player pl = (Player)sender;
            if (GamePlugin.getStater().getState() == GameState.DRAFTING) {
                ClassSelectSession sess = GamePlugin.getStater().getClassSelect();
                if (sess.includes(pl))
                    new GuiClassSelect(pl.getUniqueId(), GamePlugin.getEngine().getPlayer(pl));
                else
                    Announcer.player("You're not in this game!", pl);
            } else {
                Announcer.player("Can't select a class right now!", pl);
            }
            return true;
        }
        return false;
    }

}
