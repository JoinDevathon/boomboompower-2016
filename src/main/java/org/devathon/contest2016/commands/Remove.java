package org.devathon.contest2016.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.devathon.contest2016.PlayerListener;
import org.devathon.contest2016.utils.CustomLogging;

public class Remove implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player && !commandSender.hasPermission("blocks.remove")) {
            CustomLogging.send((Player) commandSender, "&cYou do not have permission to do this!");
        } else {
            PlayerListener.getBlocks().clear();
            PlayerListener.removeStands();
            commandSender.sendMessage(CustomLogging.color("&cAll turrets were removed!"));
        }
        return true;
    }
}
