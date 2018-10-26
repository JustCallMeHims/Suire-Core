package me.suiremc.core.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ChatUtil {

    public static void sendMsg(CommandSender commandSender, String msg) {
        commandSender.sendMessage(colorize(msg));
    }

    public static String colorize(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }


}
