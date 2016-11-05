package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Utils {

    private Utils() {}

    public static void breakRelative(Block block, int around) {
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

    public static Collection<? extends Entity> getCows() {
        List<Entity> cows = new ArrayList<Entity>();
        for (World world : Bukkit.getWorlds()) {
            cows.addAll(world.getEntities().stream().filter(entity -> entity.getMetadata(CowListener.meta) != null).collect(java.util.stream.Collectors.toList()));
        }
        return cows;
    }

    public static void broadcast(String message, Type type) {
        Bukkit.getConsoleSender().sendMessage(color(type.getMessage() + message));
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public enum Type {
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
