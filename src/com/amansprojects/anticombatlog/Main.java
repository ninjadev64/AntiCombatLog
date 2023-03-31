package com.amansprojects.anticombatlog;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin implements Listener {
    HashMap<Entity, Long> times = new HashMap<Entity, Long>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("AntiCombatLog by ninjadev64 has been enabled!");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            times.put(event.getEntity(), System.currentTimeMillis());
            times.put(event.getDamager(), System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Long d = times.get(player);
        if (d == null) return;
        long difference = System.currentTimeMillis() - d;
        if (difference < 20000) {
            getLogger().warning(ChatColor.RED + player.getName() + " may have combat logged!");
            Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " may have combat logged!");
        }
        times.remove(player);
    }
}
