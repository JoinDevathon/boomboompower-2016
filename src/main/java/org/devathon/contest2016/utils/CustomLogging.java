package org.devathon.contest2016.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CustomLogging {

    private CustomLogging() {}

    public static void console(String message) {
        Bukkit.getConsoleSender().sendMessage(color(message));
    }

    public static void send(Player player, String message) {
        player.sendMessage(color(message));
    }

    public static void actionbar(Player player, String bar) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(color(bar)));
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
