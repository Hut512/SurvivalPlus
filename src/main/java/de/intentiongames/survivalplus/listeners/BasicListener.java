package de.intentiongames.survivalplus.listeners;

import de.intentiongames.survivalplus.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

public class BasicListener implements Listener {
    public BasicListener() {
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }
}
