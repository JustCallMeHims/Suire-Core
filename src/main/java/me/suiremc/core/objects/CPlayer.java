package me.suiremc.core.objects;

import me.suiremc.core.managers.CPlayerCache;
import me.suiremc.core.util.YamlFile;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.UUID;

public class CPlayer extends YamlFile {

    private UUID uid;
    private long joinTime = System.currentTimeMillis();
    private double tokens = 0.0;

    public CPlayer(Player player, JavaPlugin plugin){
        super(player.getUniqueId().toString(), plugin);
        this.uid = player.getUniqueId();
        createFile();
        loadFromFile();
    }

    public UUID getUUID() {
        return uid;
    }

    public long getJoinTime() {
        return joinTime;
    }

    public void loadFromFile(){
        if(getConfig().isSet("tokens"))
            tokens = getConfig().getDouble("tokens");
    }

    @Override
    public boolean write() {
        int changes = 0;

        if(getConfig().isSet("tokens")) {
            getConfig().createSection("tokens");
            changes++;
        }
        if(getConfig().getDouble("tokens") != getTokens()) {
            getConfig().set("tokens", tokens);
            changes++;
        }

        return changes != 0;
    }


    public double getTokens(){
        return tokens;
    }

    public void setTokens(double tokens){
        this.tokens = tokens;
    }

    public void update(){
        CPlayerCache.getInstance().add(this, true);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPlayer cPlayer = (CPlayer) o;
        return Objects.equals(uid, cPlayer.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid);
    }

}
