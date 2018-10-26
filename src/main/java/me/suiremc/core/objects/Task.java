package me.suiremc.core.objects;

import me.suiremc.core.Core;
import org.bukkit.entity.Player;

import org.bukkit.scheduler.BukkitRunnable;

public abstract class Task extends BukkitRunnable {

    private Core plugin;
    private String name;
    private boolean requiresLogout = false;

    public Task(String name, Core plugin){
        this.name = name;
        this.plugin = plugin;
    }

    public Core getPlugin(){
        return plugin;
    }

    public abstract void run();

    public String getName() {
        return name;
    }

    public void setRequiresLogout(boolean requiresLogout) {
        this.requiresLogout = requiresLogout;
    }

    public boolean isRequiresLogout() {
        return requiresLogout;
    }

    public abstract void logout(Player player);

}
