package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {

    private List<Block> blocks;
    private DevathonPlugin plugin;

    public PlayerListener(DevathonPlugin plugin) {
        this.plugin = plugin;
        this.blocks = new ArrayList<>();

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onInteract(PlayerInteractEvent event) {
        if (blocks.contains(event.getClickedBlock())) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        blocks.stream().filter(block -> player.getLocation().distance(block.getLocation()) <= 6D).forEach(block -> {
            BlockIterator iterator = new BlockIterator(player.getLocation());
            Location location;
            while (iterator.hasNext()) {
                location = iterator.next().getLocation();
                block.getWorld().playEffect(location, Effect.COLOURED_DUST, 0);
            }
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Location special = new Location(block.getWorld(), block.getX(), block.getY() - 1, block.getZ());
        if (special.getBlock().getType() == Material.FENCE && block.getType() == Material.BREWING_STAND) {
            TurretCreateEvent turretCreateEvent = new TurretCreateEvent(special);
            Bukkit.getPluginManager().callEvent(turretCreateEvent);

            blocks.add(block);
        }
    }
}
