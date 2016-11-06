package org.devathon.contest2016.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {

    private Items() { }

    public static ItemStack withName(Material item, int data, String name) {
        ItemStack stack = new ItemStack(item, 1, (short) data);
        ItemMeta meta = stack.getItemMeta();
        meta.setDisplayName(CustomLogging.color(name));
        stack.setItemMeta(meta);
        return stack;
    }
}
