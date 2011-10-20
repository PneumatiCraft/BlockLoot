package com.pneumaticraft.blockloot.commands;

import com.pneumaticraft.commandhandler.Command;
import com.pneumaticraft.blockloot.BlockLoot;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class BlockLootCommand extends Command {

    protected BlockLoot plugin;

    public BlockLootCommand(BlockLoot plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @Override
    public abstract void runCommand(CommandSender sender, List<String> args);

}
