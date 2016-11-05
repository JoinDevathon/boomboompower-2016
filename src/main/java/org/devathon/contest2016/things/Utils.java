package org.devathon.contest2016.things;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class Utils {

    // Might do static abuse later

    public Utils() {}

    public void breakRelative(Block block, int around) {
        for (int x = -around; x <= around; x++) {
            for (int y = -around; y <= around; y++) {
                for (int z = -around; z <= around; z++) {
                    if (block.getType() != Material.BEDROCK && block.getType() != Material.BARRIER) {
                        block.getRelative(x, y, z).breakNaturally();
                    }
                }
            }
        }
    }

    public void broadcast(String message, Type type) {
        Bukkit.getConsoleSender().sendMessage(color(type.getMessage() + message));
    }

    public String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private enum Type {
        NORMAL(""),
        ERROR("&c[Error] "),
        WARNING("&e[Warning] ");

        private String message;

        Type(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
