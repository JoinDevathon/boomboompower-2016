package org.devathon.contest2016.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.utils.CustomLogging;
import org.devathon.contest2016.utils.Items;

public class MainInventory {

    private Inventory inventory = Bukkit.createInventory(null, 45, CustomLogging.color("&4Turret"));
    private ItemStack[] items = new ItemStack[] {
            Items.withName(Material.STAINED_CLAY, 5, "&aLoot"),
            Items.withName(Material.STAINED_CLAY, 15, "&2Chat"),
            Items.withName(Material.STAINED_CLAY, 4, "&6Information"),
            Items.withName(Material.STAINED_CLAY, 6, "&cDestroy"),
            Items.withName(Material.STAINED_CLAY, 14, "&4Nuke")
    };

    public MainInventory() {
        inventory.setItem(11, items[0]);
        inventory.setItem(28, items[1]);
        inventory.setItem(22, items[2]);
        inventory.setItem(15, items[3]);
        inventory.setItem(33, items[4]);
    }

    public Inventory getInventory() {
        return inventory;
    }
}
