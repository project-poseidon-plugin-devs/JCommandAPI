package org.poseidondevs.jcommandapi;

import java.lang.reflect.Method;
import java.util.*;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Command implements CommandExecutor {
    private final Object object;
    private final Method method;
    private final CommandHandler cmdHandler;
    private final Map<String, Command> childCommands = new HashMap<>();

    public Command(Object object, Method method) {
        this.object = object;
        this.method = method;
        this.cmdHandler = method.getAnnotation(CommandHandler.class);
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] strings) {
        List<String> args = Arrays.asList(strings);
        handleCommand(new CommandInfo(this, sender, s, args, cmdHandler));
        return true;
    }

    public void handleCommand(CommandInfo info) {
        List<String> args = info.getArgs();
        if (!args.isEmpty()) {
            Command child = getChild(args.get(0));
            if (child == null) {
                execute(info);
                return;
            }
            CommandInfo cmdInfo = new CommandInfo(child, info.getSender(), info.getLabel(),
                    args.size() == 1 ? Collections.emptyList() : args.subList(1, args.size()), child.cmdHandler);
            child.handleCommand(cmdInfo);
        } else {
            execute(info);
        }


    }

    public void execute(CommandInfo info) {
        if (!info.hasPermission()) {
            info.getSender().sendMessage("You don't have permission to do that.");
            return;
        } else if (info.isPlayersOnly() && !info.isPlayer()) {
            info.getSender().sendMessage("Only players can run this command.");
            return;
        } else if (!info.hasMinArgs()) {
            info.getSender().sendMessage("Not enough arguments.");
            info.getSender().sendMessage(info.getUsage());
            return;
        } else if (!info.hasMaxArgs()) {
            info.getSender().sendMessage("Too many arguments.");
            info.getSender().sendMessage(info.getUsage());
            return;
        }

        try {
            method.invoke(object, info);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CommandHandler getCommandHandler() {
        return cmdHandler;
    }

    public void addChild(String name, Command child) {
        childCommands.put(name.toLowerCase(), child);
    }

    public Command getChild(String name) {
        return childCommands.get(name.toLowerCase());
    }

    public boolean hasChild(String name) {
        return childCommands.containsKey(name.toLowerCase());
    }

    public Map<String, Command> getChildren() {
        return childCommands;
    }
}