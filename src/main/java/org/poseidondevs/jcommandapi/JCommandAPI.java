package org.poseidondevs.jcommandapi;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

public class JCommandAPI /*extends JavaPlugin*/ {

    /*@Override
    public void onEnable() {
        CommandManager manager = new CommandManager(this);
        manager.registerCommands(this);
    }

    @Override
    public void onDisable() {

    }

    @CommandHandler(command = "help", aliases = {"info", "?"}, permission = "jcommandapi.help", minArgs = 1, maxArgs = 1, description = "Shows information for commands.", usage = "/help <command>")
    public void onHelp(CommandInfo info) {
        Command command = Bukkit.getPluginCommand(info.getIndex(0, ""));
        if (command == null) {
            info.getSender().sendMessage("This command does not exist!");
            return;
        }
        info.getSender().sendMessage(command.getDescription());
        info.getSender().sendMessage(command.getUsage());
    }

    @CommandHandler(command = "test", usage = "/test", maxArgs = 0)
    public void onTest(CommandInfo info) {
        info.getSender().sendMessage(info.getUsage());
    }

    @CommandHandler(command = "test.1", usage = "/test 1", maxArgs = 0)
    public void onTest_1(CommandInfo info) {
        info.getSender().sendMessage(info.getUsage());
    }

    @CommandHandler(command = "test.2", usage = "/test 2", playersOnly = true, maxArgs = 0)
    public void onTest_2(CommandInfo info) {
        info.getSender().sendMessage(info.getUsage());
    }

    @CommandHandler(command = "test.1.1", usage = "/test 1 1", maxArgs = 0)
    public void onTest_1_1(CommandInfo info) {
        info.getSender().sendMessage(info.getUsage());
    }

    @CommandHandler(command = "test.1.2", usage = "/test 1 2", maxArgs = 0)
    public void onTest_1_2(CommandInfo info) {
        info.getSender().sendMessage(info.getUsage());
    }*/
}
