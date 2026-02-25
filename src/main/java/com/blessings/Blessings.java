package com.blessings;

import com.blessings.command.BlessingsCommand;
import com.blessings.listener.PlayerJoinListener;
import com.blessings.manager.BlessingsManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class Blessings extends JavaPlugin {
    private BlessingsManager blessingsManager;
    private boolean geyserEnabled;
    private boolean floodgateEnabled;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        
        geyserEnabled = getServer().getPluginManager().getPlugin("Geyser-Spigot") != null;
        floodgateEnabled = getServer().getPluginManager().getPlugin("floodgate") != null;
        
        this.blessingsManager = new BlessingsManager(this);
        
        BlessingsCommand commandExecutor = new BlessingsCommand(this);
        getCommand("blessings").setExecutor(commandExecutor);
        getCommand("unbless").setExecutor(commandExecutor);
        getCommand("delete").setExecutor(commandExecutor);
        
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        
        getLogger().info("Blessings enabled");
        if (geyserEnabled || floodgateEnabled) {
            getLogger().info("Bedrock player support detected (GeyserMC/Floodgate)");
        }
    }

    @Override
    public void onDisable() {
        if (blessingsManager != null) {
            blessingsManager.saveBlessedPlayers();
        }
    }
}