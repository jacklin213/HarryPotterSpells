package com.hpspells.core.spell;

import com.hpspells.core.HPS;
import com.hpspells.core.spell.Spell.SpellInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

@SpellInfo(
        name = "Sonorus",
        description = "descSonorus",
        range = 0,
        goThroughWalls = false,
        cooldown = 15
)
public class Sonorus extends Spell implements Listener {
    private static List<String> players = new ArrayList<String>();

    public Sonorus(HPS instance) {
        super(instance);
    }

    public boolean cast(final Player p) {
        Sonorus.players.add(p.getName());
        Location loc = new Location(p.getWorld(), p.getLocation().getBlockX(), p.getLocation().getBlockY() + 1, p.getLocation().getBlockZ());
        p.getWorld().createExplosion(loc, 0F);
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(HPS, new Runnable() {

            @Override
            public void run() {
                if (Sonorus.players.contains(p.getName())) {
                    Sonorus.players.remove(p.getName());
                }
            }

        }, 400L);
        return true;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (Sonorus.players.contains(e.getPlayer().getName())) {
            e.setCancelled(true);
            Bukkit.getServer().broadcastMessage(e.getPlayer().getDisplayName() + ChatColor.WHITE + ": " + e.getMessage());
            Sonorus.players.remove(e.getPlayer().getName());
        }
    }

}
