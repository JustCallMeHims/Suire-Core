package me.suiremc.core.commands;

import org.bukkit.command.CommandSender;

import java.util.Arrays;

public abstract class SubCommand {

    private String name;
    private String permission;
    private String[] aliases;
    private String[] description;

    public SubCommand(String name, String permission, String... aliases){
        this.name = name;
        this.permission = permission;
        this.aliases = aliases;
    }

    public String getName() {
        return name;
    }

    public String getPermission(){
        return permission;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String[] getDescription() {
        return description;
    }

    public void setDescription(String... description) {
        this.description = description;
    }

    public boolean isSubCommand(String s){
        return name.equalsIgnoreCase(s) || aliases != null && Arrays.asList(aliases).contains(s);
    }

    public boolean processCommand(CommandSender sender, CommandBase command, String[] args){
        if(permission != null && !sender.hasPermission(permission)){
            // send message
            return true;
        }

        return onCommand(sender, command, args);
    }

    public abstract boolean onCommand(CommandSender sender, CommandBase command, String[] args);

}
