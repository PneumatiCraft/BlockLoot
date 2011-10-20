package com.pneumaticraft.blockloot;

import com.pneumaticraft.commandhandler.PermissionsInterface;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.util.List;

public class BLPermissions implements PermissionsInterface {
    private BlockLoot plugin;

    public BLPermissions(BlockLoot plugin) {
        this.plugin = plugin;
    }
    @Override
    public boolean hasPermission(CommandSender sender, String node, boolean isOpRequired) {
        Permission perm = this.plugin.getServer().getPluginManager().getPermission(node);
        return (perm != null && sender.hasPermission(perm));
    }

    @Override
    public boolean hasAnyPermission(CommandSender sender, List<String> nodes, boolean isOpRequired) {
        for (String node : nodes) {
            if (this.hasPermission(sender, node, isOpRequired)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasAllPermission(CommandSender sender, List<String> nodes, boolean isOpRequired) {
        for (String node : nodes) {
            if (!this.hasPermission(sender, node, isOpRequired)) {
                return false;
            }
        }
        return true;
    }
}
