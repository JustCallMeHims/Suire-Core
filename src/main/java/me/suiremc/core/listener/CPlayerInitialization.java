package me.suiremc.core.listener;

import me.suiremc.core.objects.CPlayer;
import me.suiremc.core.Core;
import me.suiremc.core.objects.Task;
import me.suiremc.core.managers.CPlayerCache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CPlayerInitialization implements Listener {

    private Core plugin;
    private CPlayerCache cPlayerCache = CPlayerCache.getInstance();

    public CPlayerInitialization(Core plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onJoin(PlayerJoinEvent event){
        CPlayer cPlayer = new CPlayer(event.getPlayer(), plugin);
        cPlayerCache.add(cPlayer, false);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        CPlayer cPlayer = cPlayerCache.get(event.getPlayer());
        cPlayer.save(true);

        plugin.getTaskManager().get(Task::isRequiresLogout).forEach(task -> task.logout(event.getPlayer()));
        cPlayerCache.del(cPlayer);
    }


}
