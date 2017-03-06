package io.github.phantamanta44.mobafort.game.command;

import io.github.phantamanta44.mobafort.game.GamePlugin;
import io.github.phantamanta44.mobafort.game.game.Announcer;
import io.github.phantamanta44.mobafort.lib.format.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.BiConsumer;

public class CommandChatAugment implements CommandExecutor {

    private final String prefix;
    private final BiConsumer<Player, String> processor;

    public CommandChatAugment(String prefix, BiConsumer<Player, String> processor) {
        this.prefix = prefix;
        this.processor = processor;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player pl = (Player)sender;
            if (GamePlugin.getEngine().isInGame(pl))
                processor.accept(pl, Announcer.chat(pl, prefix + StringUtils.concat(args)));
            else
                Announcer.player("You can only use this command in a game.", pl);
            return true;
        }
        return false;
    }

}
