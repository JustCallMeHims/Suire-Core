package me.suiremc.core.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public abstract class YamlFile {

    private final String name;
    private final JavaPlugin plugin;
    private File file;
    private FileConfiguration config;

    public YamlFile(String name, JavaPlugin plugin){
        this.name = name;
        this.plugin = plugin;
    }

    /**
     * @return true or false, true if file already exists, false if creation failed.
     */
    public void createFile(){
        File fileDirectory = new File(plugin.getDataFolder()+"\\player-data\\");

        if(!fileDirectory.exists()){
            fileDirectory.mkdir();
        }

        file = new File(fileDirectory, name + ".yml");

        if(file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
    }


    public String getName() {
        return name;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public FileConfiguration getConfig(){
        return config;
    }

    public boolean save(boolean write){
        if(write && !write())
            return false;

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public abstract boolean write();

}
