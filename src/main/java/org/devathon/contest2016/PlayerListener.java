package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
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
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.devathon.contest2016.inventories.MainInventory;
import org.devathon.contest2016.utils.CustomLogging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PlayerListener implements Listener {

    private final HashMap<Player, Block> interacted = new HashMap<>();
    private final static List<Block> blocks = new ArrayList<>();
    private final static List<ArmorStand> stands = new ArrayList<>();
    private List<Player> taking = new ArrayList<>();
    private Inventory mainInventory;
    private ItemStack[] defaults = new ItemStack[] {
            new ItemStack(Material.BREWING_STAND_ITEM),
            new ItemStack(Material.FENCE)
    };

    public PlayerListener(DevathonPlugin plugin) {
        mainInventory = new MainInventory().getInventory();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (ArmorStand stand : stands) {
                for (int i = 0; i < 360; i += 5) {
                    Location particles = stand.getLocation();
                    Location particles2 = stand.getLocation();
                    Location particles3 = stand.getLocation();
                    particles.setX(particles.getX() + Math.cos(i) * 2);
                    particles.setY(stand.getLocation().getY() - 2);
                    particles.setZ(particles.getZ() + Math.sin(i) * 2);
                    particles2.setX(particles2.getX() + Math.cos(i) * 1.5);
                    particles2.setY(stand.getLocation().getY() - (new Random().nextInt(2) + new Random().nextDouble()));
                    particles2.setZ(particles2.getZ() + Math.sin(i) * 1.5);
                    particles3.setX(particles3.getX() + Math.cos(i) * 1);
                    particles3.setY(stand.getLocation().getY() - 2);
                    particles3.setZ(particles3.getZ() + Math.sin(i) * 1);

                    stand.getLocation().getWorld().spigot().playEffect(particles, Effect.COLOURED_DUST, 64, 0, 0.0F, 0.0F, 0.0F, 1.0F, 1, 5);
                    stand.getLocation().getWorld().spigot().playEffect(particles2, Effect.COLOURED_DUST, 64, 0, 0.0F, 0.0F, 0.0F, 1.0F, 1, 5);
                    stand.getLocation().getWorld().spigot().playEffect(particles3, Effect.COLOURED_DUST, 64, 0, 0.0F, 0.0F, 0.0F, 1.0F, 1, 5);
                }
            }
        }, 5L, 5L);
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
        player.setLevel(0);
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
            event.setCancelled(true);
            if (event.getClickedBlock().getType() == Material.BREWING_STAND && !player.isSneaking()) {
                player.openInventory(mainInventory);
                interacted.put(player, event.getClickedBlock());
            } else {
                CustomLogging.actionbar(event.getPlayer(), "&c&lYou cannot interact with this block.");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onInventoryClose(InventoryCloseEvent event) {
        interacted.remove(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onInventoryInteract(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().getTitle().equals(mainInventory.getTitle())) {
            if (event.getCurrentItem() != null && event.getCurrentItem().getItemMeta().getDisplayName() != null) {
                ItemStack item = event.getCurrentItem();
                if (item.equals(MainInventory.getItems()[1])) {
                    taking.add(player);
                    CustomLogging.send(player, "&cType your message in chat!");
                } else if (item.equals(MainInventory.getItems()[2])) {
                    CustomLogging.send(player, "&f\u2606 &bThis plugin was made by &3boomboompower");
                    CustomLogging.send(player, "&bIt used the Bukkit API and simple logical equations");
                    CustomLogging.send(player, "&bTo create some pretty awesome stuff.");
                    CustomLogging.send(player, "&bUse &3/remove&b to remove all turrets");
                } else if (item.equals(MainInventory.getItems()[3])) {
                    Block block = interacted.get(player);
                    CustomLogging.send(player, "&cRemoving these blocks from the turret");
                    CustomLogging.send(player, "&c(Particle effects shall remain)");
                    for (int i = 0; i <= 255; i++) {
                        block.getLocation().getWorld().spigot().playEffect(new Location(block.getWorld(), block.getX() + .5, i, block.getZ()), Effect.COLOURED_DUST, 64, 0, 0.0F, 0.0F, 0.0F, 1.0F, 1, 5);
                    }
                    blocks.remove(block.getLocation());
                    blocks.remove(new Location(block.getWorld(), block.getX(), block.getY() - 1, block.getZ()));
                }
            }
            event.setCancelled(true);
            player.closeInventory();
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        if (taking.contains(player)) {
            Bukkit.broadcastMessage(CustomLogging.color("&c[Turret] &f") + player.getName() + ": " + event.getMessage());
            event.setCancelled(true);
            taking.remove(player);
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
}
