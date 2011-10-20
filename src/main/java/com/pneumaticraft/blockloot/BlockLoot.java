package com.pneumaticraft.blockloot;

import com.pneumaticraft.blockloot.commands.HelpCommand;
import com.pneumaticraft.blockloot.listeners.BLBlockListener;
import com.pneumaticraft.commandhandler.CommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.config.Configuration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class BlockLoot extends JavaPlugin {
    private CommandHandler commandHandler;
    private BLPermissions permissions;
    private Configuration config;
    private boolean fix;
    private BLBlockListener blockListener;

    @Override
    public void onEnable() {
        this.permissions = new BLPermissions(this);
        this.commandHandler = new CommandHandler(this, permissions);

        this.registerCommands();
        this.registerEvents();
        this.getDataFolder().mkdirs();
        this.loadConfigs();

    }

    private void registerEvents() {
        this.blockListener = new BLBlockListener();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.BLOCK_BREAK, this.blockListener, Event.Priority.High, this);
    }

    private void registerCommands() {
        // Intro Commands
        this.commandHandler.registerCommand(new HelpCommand(this));

    }

    @Override
    public void onDisable() {
    }

    public void loadConfigs() {
        this.config = new Configuration(new File(getDataFolder(), "worlds.yml"));
    }

    public CommandHandler getCommandHandler() {
        return this.commandHandler;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (!this.isEnabled()) {
            sender.sendMessage("This plugin is Disabled!");
            return true;
        }
        ArrayList<String> allArgs = new ArrayList<String>(Arrays.asList(args));
        allArgs.add(0, command.getName());
        return this.commandHandler.locateAndRunCommand(sender, allArgs);
    }

    public Configuration getConfig() {
        return this.config;
    }
}
