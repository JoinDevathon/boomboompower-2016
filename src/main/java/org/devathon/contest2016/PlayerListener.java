package org.devathon.contest2016;

import net.minecraft.server.v1_10_R1.EnumParticle;
import net.minecraft.server.v1_10_R1.PacketPlayOutWorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

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
            Location loc = block.getLocation();
            spawn(player.getLocation(), block.getLocation());
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

    @EventHandler(priority = EventPriority.HIGH)
    private void onTurretCreate(TurretCreateEvent event) {
        ArmorStand stand = event.getLocation().getWorld().spawn(event.getLocation(), ArmorStand.class);
        stand.setInvulnerable(true);
        stand.setHelmet(new ItemStack(Material.STAINED_GLASS));
        stand.setInvulnerable(true);
    }

    private void spawn(Location player, Location block) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.REDSTONE, true, (float) block.getX(), (float) block.getY(), (float) block.getZ(), (float) player.getX(), (float) player.getY(), (float) player.getZ(), (float) 0, 0, 0);
        for (Player all : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packet);
        }
    }
}
