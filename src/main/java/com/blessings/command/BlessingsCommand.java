package com.blessings.command;

import com.blessings.Blessings;
import com.blessings.manager.BlessingsManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BlessingsCommand implements CommandExecutor {
    private final Blessings plugin;
    private final BlessingsManager manager;

    public BlessingsCommand(Blessings plugin) {
        this.plugin = plugin;
        this.manager = plugin.getBlessingsManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        String cmdName = command.getName().toLowerCase();

        switch (cmdName) {
            case "blessings" -> {
                if (args.length == 2 && args[0].equalsIgnoreCase("setlimit")) {
                    try {
                        int limit = Integer.parseInt(args[1]);
                        manager.setLimit(limit);
                        sender.sendMessage("Blessing limit set to " + limit);
                    } catch (NumberFormatException e) {
                        sender.sendMessage("Invalid number");
                    }
                    return true;
                }
                
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        if (manager.blessPlayer(target.getUniqueId())) {
                            target.setGameMode(GameMode.CREATIVE);
                            sender.sendMessage("Blessed " + target.getName());
                        } else {
                            sender.sendMessage("Blessing limit reached");
                        }
                    } else {
                        sender.sendMessage("Player not found");
                    }
                    return true;
                }
            }
            case "unbless", "delete" -> {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        manager.unBlessPlayer(target.getUniqueId());
                        target.setGameMode(GameMode.SURVIVAL);
                        sender.sendMessage("Unblessed " + target.getName());
                    } else {
                        sender.sendMessage("Player not found");
                    }
                    return true;
                }
            }
        }

        return true;
    }
}