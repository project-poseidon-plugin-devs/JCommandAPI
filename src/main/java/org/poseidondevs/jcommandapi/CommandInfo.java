package org.poseidondevs.jcommandapi;

import java.util.Arrays;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandInfo {
    private final RegisteredCommand regCommand;
    private final CommandSender sender;
    private final String label;
    private final List<String> args;
    private final String command;
    private final List<String> aliases;
    private final String description;
    private final String usage;
    private final String permission;
    private final boolean playersOnly;
    private final int minArgs;
    private final int maxArgs;

    public CommandInfo(RegisteredCommand regCommand, CommandSender sender, String label, List<String> args, CommandHandler cmdHandler) {
        this.regCommand = regCommand;
        this.sender = sender;
        this.label = label;
        this.args = args;
        this.command = cmdHandler.command();
        this.aliases = Arrays.asList(cmdHandler.aliases());
        this.description = cmdHandler.description();
        this.usage = cmdHandler.usage();
        this.permission = cmdHandler.permission();
        this.playersOnly = cmdHandler.playersOnly();
        this.minArgs = cmdHandler.minArgs();
        this.maxArgs = cmdHandler.maxArgs();
    }

    public RegisteredCommand getRegisteredCommand() {
        return regCommand;
    }

    public CommandSender getSender() {
        return sender;
    }

    public String getLabel() {
        return label;
    }

    public List<String> getArgs() {
        return args;
    }

    public String getCommand() {
        return command;
    }

    public List<String> getAliases() {
        return aliases;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public String getPermission() {
        return permission;
    }

    public boolean isPlayersOnly() {
        return playersOnly;
    }

    public int getMinArgs() {
        return minArgs;
    }

    public int getMaxArgs() {
        return maxArgs;
    }

    public Player getPlayer() {
        return isPlayer() ? (Player) sender : null;
    }

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public boolean hasPermission() {
        return sender.isOp() || sender.hasPermission(permission) || permission.equals("");
    }

    public boolean hasMinArgs() {
        return args.size() >= minArgs;
    }

    public boolean hasMaxArgs() {
        return args.size() <= maxArgs || maxArgs < 0;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < args.size();
    }

    public String getIndex(int index, String defaultVal) {
        return isValidIndex(index) ? args.get(index) : defaultVal;
    }

    public int getInt(int index, int defaultVal) {
        if (isValidIndex(index)) {
            try {
                return Integer.parseInt(args.get(index));
            } catch (NumberFormatException ignored) {}
        }
        return defaultVal;
    }

    public double getDouble(int index, double defaultVal) {
        if (isValidIndex(index)) {
            try {
                return Double.parseDouble(args.get(index));
            } catch (NumberFormatException ignored) {}
        }
        return defaultVal;
    }

    public String joinArgs(int index) {
        return isValidIndex(index) ? String.join(" ", args.subList(index, args.size() - 1)) : null;
    }
}