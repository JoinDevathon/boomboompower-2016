package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Block block : blocks) {
                    for (Entity entity : block.getWorld().getEntities()) {
                        if (entity.getLocation().distance(block.getLocation()) <= 10D) {
                            BlockIterator blocksToAdd = new BlockIterator(entity.getLocation(), 0D, 0);
                            Location blockToAdd;
                            while(blocksToAdd.hasNext()) {
                                blockToAdd = blocksToAdd.next().getLocation();
                                block.getWorld().playEffect(blockToAdd, Effect.COLOURED_DUST, 0);
                            }
                        }
                    }
                }
            }
        }, 0L, 10L);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onInteract(PlayerInteractEvent event) {
        if (blocks.contains(event.getClickedBlock())) {
            event.setCancelled(true);
        }
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
