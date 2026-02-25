package com.blessings.manager;

import com.blessings.Blessings;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BlessingsManager {
    private final Blessings plugin;
    private final Set<UUID> blessedPlayers;
    @Getter
    private int blessingLimit;

    public BlessingsManager(Blessings plugin) {
        this.plugin = plugin;
        this.blessedPlayers = new HashSet<>();
        loadBlessedPlayers();
    }

    public void loadBlessedPlayers() {
        FileConfiguration config = plugin.getConfig();
        this.blessingLimit = config.getInt("blessing-limit", 5);
        
        List<String> uuidStrings = config.getStringList("blessed-players");
        blessedPlayers.clear();
        
        for (String uuidString : uuidStrings) {
            try {
                blessedPlayers.add(UUID.fromString(uuidString));
            } catch (IllegalArgumentException ignored) {
            }
        }
    }

    public void saveBlessedPlayers() {
        FileConfiguration config = plugin.getConfig();
        
        List<String> uuidStrings = new ArrayList<>();
        for (UUID uuid : blessedPlayers) {
            uuidStrings.add(uuid.toString());
        }
        
        config.set("blessed-players", uuidStrings);
        config.set("blessing-limit", blessingLimit);
        plugin.saveConfig();
    }

    public boolean canBless() {
        return blessedPlayers.size() < blessingLimit;
    }

    public boolean blessPlayer(UUID uuid) {
        if (!canBless()) {
            return false;
        }
        
        boolean added = blessedPlayers.add(uuid);
        if (added) {
            saveBlessedPlayers();
        }
        return added;
    }

    public boolean unBlessPlayer(UUID uuid) {
        boolean removed = blessedPlayers.remove(uuid);
        if (removed) {
            saveBlessedPlayers();
        }
        return removed;
    }

    public boolean isBlessed(UUID uuid) {
        return blessedPlayers.contains(uuid);
    }

    public void setLimit(int limit) {
        this.blessingLimit = Math.max(0, limit);
        saveBlessedPlayers();
    }

    public int getBlessedCount() {
        return blessedPlayers.size();
    }
}