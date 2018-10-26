package me.suiremc.core.commands;

import com.google.common.collect.Lists;
import me.suiremc.core.util.ChatUtil;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public abstract class CommandBase implements CommandExecutor {

    private String name;
    private String[] aliases;
    private String[] description;
    private String permission;

    private List<SubCommand> subCommands = Lists.newArrayList();

    public CommandBase(String name, String permission, String... aliases){
        this.name = name;
        this.permission = permission;
        this.aliases = aliases;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(permission != null && !sender.hasPermission(permission)){
            // send message
            return true;
        }

        if(args.length > 0){
            SubCommand subCommand = getSubCommand(args[0]);
            if(subCommand != null)
                return subCommand.processCommand(sender, this, args.length == 1 ?
                        new String[0] : (String[])ArrayUtils.remove(args, 0));
        }

        return onCommand(sender, this, args);
    }

    public SubCommand getSubCommand(String name){
        return subCommands.stream().filter(s->s.isSubCommand(name))
                .findFirst().orElse(null);
    }

    public void addSubCommand(SubCommand subCommand){
        subCommands.add(subCommand);
    }

    public static void sendHelpMenu(CommandSender target, CommandBase commandBase){
        //TODO: Move to config

        ChatUtil.sendMsg(target, "&3&l<~>&aHelp Menu&3&l<~>");
        StringBuilder stringBuilder = new StringBuilder();

        for(SubCommand subCommand: commandBase.getSubCommands()){
            if(subCommand.getPermission() != null && !target.hasPermission(subCommand.getPermission()))
                continue;

            stringBuilder.append("&6" + "/" + commandBase.getName() + " " + subCommand.getName() + " &8&l:: &6\n");
            if(subCommand.getDescription() != null)
                Arrays.stream(subCommand.getDescription()).forEach(s ->
                        stringBuilder.append("&e" + s + "\n"));
        }

        ChatUtil.sendMsg(target, stringBuilder.toString());
    }

    public abstract boolean onCommand(CommandSender sender, CommandBase command, String[] args);


    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public String[] getDescription() {
        return description;
    }

    public void setDescription(String[] description) {
        this.description = description;
    }

    public List<SubCommand> getSubCommands(){
        return subCommands;
    }
}
