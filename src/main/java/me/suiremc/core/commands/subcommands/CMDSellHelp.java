package me.suiremc.core.commands.subcommands;

import me.suiremc.core.commands.CommandBase;
import me.suiremc.core.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class CMDSellHelp extends SubCommand {


    public CMDSellHelp() {
        super("Help", "core.sell.help", null);
        setDescription("Just a help menu really...");
    }

    @Override
    public boolean onCommand(CommandSender sender, CommandBase command, String[] args) {
        CommandBase.sendHelpMenu(sender, command);
        return true;
    }
}
