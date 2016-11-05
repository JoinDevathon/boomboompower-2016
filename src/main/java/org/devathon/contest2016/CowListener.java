package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class CowListener implements Listener{

    public static String meta = "why";
    private DevathonPlugin plugin;

    public CowListener(DevathonPlugin main) {
        plugin = main;

        Bukkit.getPluginManager().registerEvents(this, main);

        Bukkit.getScheduler().scheduleSyncRepeatingTask(main, () -> {
            for (Entity entity : Utils.getCows()) {
                entity.getWorld().playEffect(new Location(entity.getWorld(), entity.getLocation().getX(), entity.getLocation().getY(), entity.getLocation().getZ()), Effect.FLAME, 0, 10);
            }
        },0, 10);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack stack = player.getInventory().getItemInMainHand();
        if (stack.getType() == Material.MONSTER_EGG && stack.getItemMeta().hasDisplayName() && stack.getItemMeta().getDisplayName().equals(Utils.color("&4The Cow"))) {
            Cow cow = player.getWorld().spawn(new Location(player.getWorld(), event.getClickedBlock().getX(), event.getClickedBlock().getY() + 1, event.getClickedBlock().getZ()), Cow.class);

            cow.setMetadata(meta, new FixedMetadataValue(plugin, player));
        }
    }


}
