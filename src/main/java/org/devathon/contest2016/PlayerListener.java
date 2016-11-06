package org.devathon.contest2016;

import net.minecraft.server.v1_10_R1.EnumParticle;
import net.minecraft.server.v1_10_R1.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;

public class PlayerListener implements Listener {

    private HashMap<Location, Block> blocks;
    private DevathonPlugin plugin;

    public PlayerListener(DevathonPlugin plugin) {
        this.plugin = plugin;
        this.blocks = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (blocks.containsKey(block)) {
            if (event.getPlayer().getGameMode() != GameMode.CREATIVE) {
                event.setCancelled(true);
            } else {
                blocks.remove(block);
                if (block.getType() == Material.FENCE) {
                    Block above = new Location(block.getWorld(), block.getX(), block.getY() + 1, block.getZ()).getBlock();
                    above.setType(Material.AIR);
                    blocks.remove(above);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        blocks.keySet().stream().filter(block -> player.getLocation().distance(block.getBlock().getLocation()) <= 6D).forEach(block -> {
            Location loc = block.getBlock().getLocation();
            loc.getWorld().spigot().playEffect(loc, Effect.FIREWORKS_SPARK, 64, 0, 0.0F, 0.0F, 0.0F, 1.0F, 1, 5);
            spawn(player.getLocation(), loc);
        });
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Location special = new Location(block.getWorld(), block.getX(), block.getY() - 1, block.getZ());
        if (special.getBlock().getType() == Material.FENCE && block.getType() == Material.BREWING_STAND) {
            TurretCreateEvent turretCreateEvent = new TurretCreateEvent(special, block.getLocation());
            Bukkit.getPluginManager().callEvent(turretCreateEvent);

            blocks.put(block.getLocation(), block);
            blocks.put(special, special.getBlock());
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onTurretCreate(TurretCreateEvent event) {
        ArmorStand stand = new Location(event.getTopBlock().getWorld(), event.getTopBlock().getX() + 0.5, event.getTopBlock().getY() + 0.5, event.getTopBlock().getZ() + 0.5).getWorld().spawn(event.getTopBlock(), ArmorStand.class);
        stand.setHelmet(new ItemStack(Material.STAINED_GLASS));
        stand.setInvulnerable(true);
        stand.setBasePlate(false);
        stand.setVisible(false);
        stand.setMetadata("block1", new FixedMetadataValue(plugin, event.getTopBlock()));
        stand.setMetadata("block2", new FixedMetadataValue(plugin, event.getBottomBlock()));
    }

    private void spawn(Location player, Location block) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.HEART, true, (float) block.getX(), (float) block.getY(), (float) block.getZ(), (float) player.getX(), (float) player.getY(), (float) player.getZ(), (float) 0, 10);
        for (Player all : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
