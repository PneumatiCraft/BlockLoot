package com.pneumaticraft.blockloot;

import com.pneumaticraft.blockloot.commands.HelpCommand;
import com.pneumaticraft.blockloot.listeners.BLBlockListener;
import com.pneumaticraft.blockloot.listeners.BLEntityListener;
import com.pneumaticraft.commandhandler.CommandHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BlockLoot extends JavaPlugin {
    private CommandHandler commandHandler;
    private BLPermissions permissions;
    private FileConfiguration config;
    private boolean fix;
    private BLBlockListener blockListener;
    public static int GlobalDebug;
    private static final String tag = "[DropLoot]";
    private static final Logger log = Logger.getLogger("Minecraft");

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
        BLEntityListener entityListener = new BLEntityListener();
        PluginManager pm = getServer().getPluginManager();
        //pm.registerEvent(Event.Type.BLOCK_BREAK, this.blockListener, Event.Priority.High, this);
        pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Event.Priority.High, this);
    }

    private void registerCommands() {
        // Intro Commands
        this.commandHandler.registerCommand(new HelpCommand(this));

    }

    @Override
    public void onDisable() {
    }

    public void loadConfigs() {
        this.config = YamlConfiguration.loadConfiguration(new File(this.getDataFolder(), "drops.yml"));
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

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void saveBlockLootConfig() {
        try {
            this.config.save("drops.yml");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void log(Level level, String msg) {
        staticLog(level, msg);
    }

    public static void staticLog(Level level, String msg) {
        if (level == Level.FINE && GlobalDebug >= 1) {
            staticDebugLog(Level.INFO, msg);
            return;
        } else if (level == Level.FINER && GlobalDebug >= 2) {
            staticDebugLog(Level.INFO, msg);
            return;
        } else if (level == Level.FINEST && GlobalDebug >= 3) {
            staticDebugLog(Level.INFO, msg);
            return;
        } else if (level != Level.FINE && level != Level.FINER && level != Level.FINEST) {
            log.log(level, tag + " " + msg);
        }
    }

    public static void staticDebugLog(Level level, String msg) {
        log.log(level, "[MVCore-Debug] " + msg);
    }
}
