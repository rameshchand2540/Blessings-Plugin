package com.blessings.listener;

import com.blessings.Blessings;
import com.blessings.manager.BlessingsManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    private final BlessingsManager manager;

    public PlayerJoinListener(Blessings plugin) {
        this.manager = plugin.getBlessingsManager();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        if (manager.isBlessed(player.getUniqueId())) {
            player.setGameMode(GameMode.CREATIVE);
        }
    }
}