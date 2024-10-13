package org.poseidondevs.jcommandapi;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

public class CommandManager {
    private final Plugin plugin;
    private Logger logger;
    private String prefix;
    private CommandMap commandMap;

    private Map<String, Command> baseCommands = new HashMap<>();
    private Map<Integer, List<Command>> queuedCommands = new HashMap<>();

    public CommandManager(Plugin plugin) {
        this.plugin = plugin;
        this.logger = Bukkit.getLogger();
        this.prefix = "[" + plugin.getDescription().getName() + "] ";

        try {
            Field field = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            commandMap = (SimpleCommandMap) field.get(Bukkit.getPluginManager());
            field.setAccessible(false);
            logger.log(Level.INFO, prefix + "Initialized CommandMap");
        } catch (Exception e) {
            logger.log(Level.SEVERE, prefix + "Error while initializing CommandMap");
            e.printStackTrace();
        }

    }

    public void registerCommands(Object... objects) {
        for (Object obj : objects) {
            Method[] methods = obj.getClass().getDeclaredMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(CommandHandler.class) && method.getParameterTypes()[0] == CommandInfo.class) {
                    CommandHandler cmdHandler = method.getAnnotation(CommandHandler.class);
                    if (!Modifier.isPublic(method.getModifiers())) {
                        logger.log(Level.WARNING, "Could not register command /" + cmdHandler.command() + ": method is not public");
                        continue;
                    }
                    if (cmdHandler.command().contains(".")) {
                        queueCommand(obj, method, cmdHandler);
                    } else {
                        registerBaseCommand(obj, method, cmdHandler);
                    }
                }
            }
            registerQueuedCommands();
        }

    }

    private void registerBaseCommand(Object obj, Method method, CommandHandler cmdHandler) {
        try {
            Class<PluginCommand> clazz = PluginCommand.class;
            Constructor<PluginCommand> constructor = clazz.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);
            Command command = new Command(obj, method);
            PluginCommand pluginCmd = constructor.newInstance(cmdHandler.command(), plugin);
            pluginCmd.setAliases(Arrays.asList(cmdHandler.aliases()));
            pluginCmd.setDescription(cmdHandler.description());
            pluginCmd.setUsage(cmdHandler.usage());
            pluginCmd.setExecutor(command);
            commandMap.register(plugin.getDescription().getName(), pluginCmd);
            baseCommands.put(cmdHandler.command(), command);
            logger.log(Level.INFO, prefix + "Registered command /" + cmdHandler.command());
        } catch (Exception e) {
            logger.log(Level.SEVERE, prefix + "Error while registering command /" + cmdHandler.command());
            e.printStackTrace();
        }
    }

    private void queueCommand(Object obj, Method method, CommandHandler cmdHandler) {
        int level = cmdHandler.command().split("\\.").length - 1;
        Command command = new Command(obj, method);
        List<Command> queue = queuedCommands.getOrDefault(level, new ArrayList<>());
        if (!queue.contains(command)) {
            queue.add(command);
        }
        queuedCommands.put(level, queue);
    }

    private void registerQueuedCommands() {
        if (queuedCommands.isEmpty()) return;
        int highestLevel = 0;
        for (int i : queuedCommands.keySet()) {
            if (i > highestLevel) highestLevel = i;
        }
        for (int i = 1; i <= highestLevel ; i++) {
            List<Command> queue = queuedCommands.get(i);
            if (queue == null || queue.isEmpty()) return;
            for (Command command : queue) {
                String[] list = command.getCommandHandler().command().split("\\.");
                Command parentCmd = getParentCommand(list, baseCommands.get(list[0]), 1);
                if (parentCmd == null) {
                    logger.log(Level.WARNING, prefix + "Could not register command /" + String.join(" ", list) + " due to nonexistent parent commands");
                    continue;
                }
                parentCmd.addChild(list[list.length - 1], command);
                logger.log(Level.INFO, prefix + "Registered subcommand /" + String.join(" ", list));
            }
        }
    }

    private Command getParentCommand(String[] list, Command command, int start) {
        if (command == null) return null;
        if (start > list.length - 2) {
            return command;
        }
        return command.hasChild(list[start]) ? getParentCommand(list, command.getChild(list[start]), ++start) : null;
    }
}