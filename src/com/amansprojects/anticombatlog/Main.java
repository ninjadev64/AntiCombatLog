package com.amansprojects.anticombatlog;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Main extends JavaPlugin implements Listener {
    HashMap<Player, Date> times = new HashMap<Player, Date>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("AntiCombatLog by ninjadev64 has been enabled!");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Date date = new Date();
            times.put((Player) event.getEntity(), date);
            times.put((Player) event.getDamager(), date);
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        long difference = TimeUnit.SECONDS.convert((times.get(player).getTime() - new Date().getTime()), TimeUnit.MILLISECONDS);
        if (difference <= 20) {
            getLogger().warning(ChatColor.RED + player.getName() + " may have combat logged!");
            Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " may have combat logged!");
        }
        times.remove(player);
    }
}
