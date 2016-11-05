package org.devathon.contest2016.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.things.Utils;

public class CowListener implements Listener{

    private DevathonPlugin plugin;

    public CowListener(DevathonPlugin main) {
        plugin = main;
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getInventory().getItemInMainHand();
        if (stack.getType() == Material.MONSTER_EGG && stack.getItemMeta().hasDisplayName() && stack.getItemMeta().getDisplayName().equals(Utils.color("&4The Cow"))) {
            player.getWorld().spawnEntity(new Location(player.getWorld(), event.getClickedBlock().getX(), event.getClickedBlock().getY() + 1, event.getClickedBlock().getZ()), EntityType.COW);

        }
    }
}
