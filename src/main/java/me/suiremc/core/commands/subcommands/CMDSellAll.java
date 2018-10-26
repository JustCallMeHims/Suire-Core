package me.suiremc.core.commands.subcommands;

import me.suiremc.core.commands.CommandBase;
import me.suiremc.core.commands.SubCommand;
import me.suiremc.core.commands.basecommands.CMDSell;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Really, this class is unnecessary, but it enhances readability.
 * Using CommandBase and SubCommand we can easily list the plugin its commands.
 * By doing this we can easily create a help menu.
 */
public class CMDSellAll extends SubCommand {

    public CMDSellAll() {
        super("all", "core.sell.all", "inv",
                "inventory", "alles");
        setDescription("Sell your entire inventory!");
    }

    @Override
    public boolean onCommand(CommandSender sender, CommandBase command, String[] args) {
        Player player = (Player) sender;
        CMDSell cmdSell = (CMDSell) command;
        cmdSell.sell(player, true);
        return true;
    }
}
