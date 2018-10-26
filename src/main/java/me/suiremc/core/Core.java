package me.suiremc.core;

import me.suiremc.core.commands.basecommands.CMDSell;
import me.suiremc.core.listener.CPlayerInitialization;
import me.suiremc.core.listener.ItemValuer;
import me.suiremc.core.managers.CPlayerCache;
import me.suiremc.core.managers.TaskManager;
import me.suiremc.core.objects.CPlayer;
import me.suiremc.core.tasks.PlayTimeRewardTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Core extends JavaPlugin {

    private static Core instance;
    private TaskManager taskManager = null;

    @Override
    public void onEnable(){
        instance = this;
        saveDefaultConfig();
        setup();
        reloadSupport();
        taskManager = new TaskManager(this);

        taskManager.addTaskAndRun(new PlayTimeRewardTask(this), 0, 10);
    }

    private void reloadSupport(){
        for (Player online : Bukkit.getOnlinePlayers()) {
            CPlayer cPlayer = new CPlayer(online, this);
            CPlayerCache.getInstance().add(cPlayer, false);
        }
    }

    @Override
    public void onDisable(){
        CPlayerCache.getInstance().getCache().forEach(cP -> cP.save(true));
    }

    private void setup(){
        getCommand("verkoop").setExecutor(new CMDSell());
        registerListeners(new CPlayerInitialization(this), new ItemValuer());
    }

    public void registerListeners(Listener... listeners){
        for(Listener listener: listeners){
            getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    public TaskManager getTaskManager(){
        return taskManager;
    }

    public static String getDisplay(String path){
        return instance.getConfig().getString("display."+path);
    }
}
