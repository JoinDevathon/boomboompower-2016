package org.devathon.contest2016;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.utils.CustomLogging;

import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {

    private final static List<Block> blocks = new ArrayList<>();
    private final static List<ArmorStand> stands = new ArrayList<>();
    private ItemStack[] defaults = new ItemStack[] {
            new ItemStack(Material.BREWING_STAND_ITEM),
            new ItemStack(Material.FENCE)
    };

    public PlayerListener(DevathonPlugin plugin) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (ArmorStand stand : stands) {
                    ArrayList<Location> c = cirle(5, stand.getLocation());
                    for (Location loc : c) {
                        stand.getWorld().spigot().playEffect(loc, Effect.HEART, 64, 0, 0.0F, 0.0F, 0.0F, 1.0F, 1, 5);
                    }
                }
            }
        }, 1L, 2L);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onBreak(BlockBreakEvent event) {
        if (blocks.contains(event.getBlock())) {
            CustomLogging.actionbar(event.getPlayer(), "&c&lYou cannot break this block.");
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onHunger(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }


    @EventHandler(priority = EventPriority.HIGH)
    private void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        CustomLogging.actionbar(player, "&eWelcome to the world &6" + player.getName() + "&e !");
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(16);
        player.setExp(0);
        player.setHealth(player.getMaxHealth());
        player.getInventory().clear();
        player.getActivePotionEffects().clear();

        player.getInventory().setItem(0, defaults[0]);
        player.getInventory().setItem(1, defaults[1]);
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (blocks.contains(event.getClickedBlock())) {
            if (event.getClickedBlock().getType() == Material.BREWING_STAND && !player.isSneaking()) {
            } else {
                CustomLogging.actionbar(event.getPlayer(), "&c&lYou cannot interact with this block.");
                event.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Block special = new Location(block.getWorld(), block.getX(), block.getY() - 1, block.getZ()).getBlock();
        if (special.getType() == Material.FENCE && block.getType() == Material.BREWING_STAND) {
            blocks.add(special);
            blocks.add(block);
            // Armorstand
            ArmorStand armorStand = block.getWorld().spawn(new Location(block.getWorld(), block.getX() + .5, block.getY() + 1, block.getZ() + .5), ArmorStand.class);
            armorStand.setCustomName(CustomLogging.color("&e&l" + player.getName()));
            armorStand.setCustomNameVisible(true);
            armorStand.setBasePlate(false);
            armorStand.setVisible(false);
            armorStand.setMarker(true);
            armorStand.setGravity(false);
            stands.add(armorStand);
            // End
            CustomLogging.actionbar(event.getPlayer(), "&a&lA turret was created!");
        }
    }

    public static List<Block> getBlocks() {
        return blocks;
    }

    public static void removeStands() {
        stands.forEach(Entity::remove);
        stands.clear();
    }

    private ArrayList<Location> cirle(double radius, Location block) {
        World world = block.getWorld();
        ArrayList<Location> loc = new ArrayList<>();
        double math = (Math.PI * 2) / 5;
        for (int i = 0; i < 5; i++) {
            loc.add(new Location(world, (block.getX() + .5) + (radius * Math.cos(i * math)), block.getY(), (block.getZ() + .5) + (radius * Math.cos(i * math))));
        }
        return loc;
    }
}
