package org.poseidondevs.jcommandapi;

import org.bukkit.plugin.java.JavaPlugin;

public class JCommandAPI extends JavaPlugin {

    @Override
    public void onEnable() {
        CommandManager manager = new CommandManager(this);
        manager.registerCommands(this);
    }

    @Override
    public void onDisable() {

    }
}
