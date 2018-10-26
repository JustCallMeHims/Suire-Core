package me.suiremc.core.managers;

import me.suiremc.core.objects.CPlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CPlayerCache {

    private static CPlayerCache instance;
    private Set<CPlayer> cache = new HashSet<>();

    private CPlayerCache(){

    }

    public CPlayer get(UUID uid){
        return cache.stream().filter(p -> p.getUUID().equals(uid)).findFirst().orElse(null);
    }

    public CPlayer get(Player player){
        return cache.stream().filter(p -> p.getUUID().equals(player.getUniqueId())).findFirst().orElse(null);
    }

    public static CPlayerCache getInstance(){
        if(instance == null)
            instance = new CPlayerCache();

        return instance;
    }

    public boolean add(CPlayer cPlayer, boolean update){
        if(update)
            cache.removeIf(cPlayer::equals);

        return cache.add(cPlayer);
    }

    public boolean del(CPlayer cPlayer){
        return cache.remove(cPlayer);
    }

    public Set<CPlayer> getCache(){
        return cache;
    }
}
