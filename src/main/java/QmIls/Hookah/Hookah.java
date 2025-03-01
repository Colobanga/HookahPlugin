package QmIls.Hookah;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.HashMap;
import java.util.Map;

public class Hookah extends JavaPlugin implements Listener {

    private final Map<String, Integer> puffs = new HashMap<>();
    private final int maxPuffs = 15;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);

        getLogger().info("====================================");
        getLogger().info("");
        getLogger().info("Кальян готов к дымку!");
        getLogger().info("");
        getLogger().info("====================================");

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_AIR) {
            int currentPuffs = puffs.getOrDefault(playerName, 0);

            if (currentPuffs > 0) {
                spawnParticles(player, currentPuffs);
                puffs.put(playerName, 0);
            }
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && player.isSneaking()) {
            if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.BREWING_STAND) {
                event.setCancelled(true);
                int currentPuffs = puffs.getOrDefault(playerName, 0);

                if (currentPuffs < maxPuffs) {
                    currentPuffs++;
                    puffs.put(playerName, currentPuffs);
                    if (currentPuffs == 1) {
                        sendActionBarMessage(player, ChatColor.GRAY + "[" + ChatColor.GREEN + "|" + ChatColor.GRAY + "||||||||||||||]");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_CLERIC, 0.3F, 0.1F);
                    } else if (currentPuffs == 2) {
                        sendActionBarMessage(player, ChatColor.GRAY + "[" + ChatColor.GREEN + "||" + ChatColor.GRAY + "|||||||||||||]");
                    } else if (currentPuffs == 3) {
                        sendActionBarMessage(player, ChatColor.GRAY + "[" + ChatColor.GREEN + "|||" + ChatColor.GRAY + "||||||||||||]");
                    } else if (currentPuffs == 4) {
                        sendActionBarMessage(player, ChatColor.GRAY + "[" + ChatColor.GREEN + "||||" + ChatColor.GRAY + "|||||||||||]");
                    } else if (currentPuffs == 5) {
                        sendActionBarMessage(player, ChatColor.GRAY + "[" + ChatColor.GREEN + "|||||" + ChatColor.GRAY + "||||||||||]");
                    } else if (currentPuffs == 6) {
                        sendActionBarMessage(player, ChatColor.GRAY + "[" + ChatColor.GREEN + "||||||" + ChatColor.GRAY + "|||||||||]");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_CLERIC, 0.3F, 0.1F);
                    } else if (currentPuffs == 7) {
                        sendActionBarMessage(player, ChatColor.GRAY + "[" + ChatColor.GREEN + "|||||||" + ChatColor.GRAY + "||||||||]");
                    } else if (currentPuffs == 8) {
                        sendActionBarMessage(player, ChatColor.GRAY + "[" + ChatColor.GREEN + "||||||||" + ChatColor.GRAY + "|||||||]");
                    } else if (currentPuffs == 9) {
                        sendActionBarMessage(player, ChatColor.GRAY + "[" + ChatColor.GREEN + "|||||||||" + ChatColor.GRAY + "||||||]");
                    } else if (currentPuffs == 10) {
                        sendActionBarMessage(player, ChatColor.GRAY + "[" + ChatColor.GREEN + "||||||||||" + ChatColor.GRAY + "|||||]");
                    } else if (currentPuffs == 11) {
                        sendActionBarMessage(player, ChatColor.GRAY + "[" + ChatColor.GREEN + "|||||||||||" + ChatColor.GRAY + "||||]");
                    } else if (currentPuffs == 12) {
                        sendActionBarMessage(player, ChatColor.GRAY + "[" + ChatColor.GREEN + "||||||||||||" + ChatColor.GRAY + "|||]");
                    } else if (currentPuffs == 13) {
                        sendActionBarMessage(player, ChatColor.GRAY + "[" + ChatColor.GREEN + "|||||||||||||" + ChatColor.GRAY + "||]");
                    } else if (currentPuffs == 14) {
                        sendActionBarMessage(player, ChatColor.GRAY + "[" + ChatColor.GREEN + "||||||||||||||" + ChatColor.GRAY + "|]");
                    } else if (currentPuffs == 15) {
                        sendActionBarMessage(player, ChatColor.GRAY + "[" + ChatColor.GREEN + "|||||||||||||||" + ChatColor.GRAY + "]");
                        player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_WORK_CLERIC, 0.3F, 0.1F);
                    }
                } else {
                    sendActionBarMessage(player, ChatColor.GRAY + "[" + ChatColor.RED + "|||||||||||||||" + ChatColor.GRAY + "]");
                }
            }
        }
    }

    private void spawnParticles(Player player, int count) {
        int particleCount;
        if (count <= 5) {
            particleCount = 15;
        } else if (count <= 14) {
            particleCount = 22;
        } else {
            particleCount = 30;
        }

        long delay = 2L;

        for (int i = 0; i < particleCount; i++) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Vector dir = player.getLocation().getDirection();
                    Location loca = player.getLocation().add(0, 1.5, 0);
                    loca.add(dir.multiply(0.25));
                    player.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, loca, 0, dir.getX(), dir.getY(), dir.getZ());
                }
            }.runTaskLater(this, delay * i);
        }
    }

    private void sendActionBarMessage(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }
}