package me.suiremc.core.commands.basecommands;

import me.suiremc.core.Core;
import me.suiremc.core.commands.CommandBase;
import me.suiremc.core.commands.subcommands.CMDSellAll;
import me.suiremc.core.commands.subcommands.CMDSellHelp;
import me.suiremc.core.listener.ItemValuer;
import me.suiremc.core.managers.CPlayerCache;
import me.suiremc.core.objects.CPlayer;
import me.suiremc.core.util.ChatUtil;
import me.suiremc.core.util.NMSTag;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CMDSell extends CommandBase {


    public CMDSell() {
        super("Sell", "core.sell", "ruil", "verkoop","trade");
        addSubCommand(new CMDSellAll());
        addSubCommand(new CMDSellHelp());
    }

    @Override
    public boolean onCommand(CommandSender sender, CommandBase command, String[] args) {
        Player player = (Player)sender;
        sell(player, false);
        return true;
    }


    public void sell(Player player, boolean all){
        double earned = 0.0;

        if(all)
            earned = sellAll(player);
        else
            earned = sell(player);

        CPlayer cPlayer = CPlayerCache.getInstance().get(player);

        if(earned > 0.0)
            cPlayer.setTokens(cPlayer.getTokens()+earned);

        String output = earned == 0.0 ? Core.getDisplay("sell.no-interest") :
                Core.getDisplay("sell.success");


        DecimalFormat f = new DecimalFormat("##.00");

        // Lovely spaghetti
        ChatUtil.sendMsg(player, Core.getDisplay("prefix") +
                output.replace("{tokens}", ""+f.format(earned)).replace("{token1}",
                        Core.getDisplay("low-token-name")).replace("{token}",
                        Core.getDisplay("normal-token-name")).replace("{total}",
                        ""+f.format(cPlayer.getTokens())) +
                Core.getDisplay("suffix"));
    }

    private double sell(Player player){
        ItemStack inHand = player.getItemInHand();
        if(inHand == null || !ItemValuer.isValuableItem(inHand.getType(), getData(inHand)))
            return 0.0;

        if(!NMSTag.getBoolean(inHand, "natural"))
            return 0.0;

        player.setItemInHand(null); // This does not work in 1.10+ I believe
        return inHand.getAmount()*ItemValuer.getItemValue(inHand.getType()).getValue();
    }

    private double sellAll(Player player){
        List<ItemStack> useAble = new ArrayList<>(Arrays.asList(player.getInventory().getContents()).stream()
                .filter(
                        item -> item != null && ItemValuer.isValuableItem(item.getType(),getData(item)))
                .collect(Collectors.toList()));

        double earned = 0.0;

        for(ItemStack item: useAble){
            if(!NMSTag.getBoolean(item, "natural"))
                continue;

            player.getInventory().removeItem(item);
            earned+=item.getAmount() * ItemValuer.getItemValue(item.getType()).getValue();
        }

        return earned;
    }

    private Short getData(ItemStack itemStack){
        if(itemStack.getType() != Material.INK_SACK || itemStack.getData() == null)
            return null;

        return (short)itemStack.getData().getData();
    }
}
