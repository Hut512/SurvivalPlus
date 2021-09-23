package de.intentiongames.survivalplus.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;

public abstract class BasicCommand extends BukkitCommand {
    public BasicCommand(String name, String permission, String... aliases) {
        super(name);
        this.setPermission(permission);
        this.setAliases(Arrays.asList(aliases));
        registerCommand(this);
    }

    @Override
    public abstract boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args);



    private static  <T extends BukkitCommand> void registerCommand(T command) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(command.getName(), command);
        }catch (NoSuchFieldException | IllegalAccessException exception) {
            throw new SecurityException("Exception registering command: " + command.getName(), exception);
        }
    }
}
