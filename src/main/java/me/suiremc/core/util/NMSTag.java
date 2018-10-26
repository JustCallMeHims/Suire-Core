package me.suiremc.core.util;

import com.google.common.collect.Lists;
import me.suiremc.core.Core;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class NMSTag {

    public static ItemStack setTag(ItemStack itemStack, String key, boolean value){
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);

        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();

        compound.setBoolean(key, value);
        nmsStack.setTag(compound);

        ItemStack is = CraftItemStack.asBukkitCopy(nmsStack);
        ItemMeta meta = is.getItemMeta();

        List<String> lore = meta.hasLore() ? meta.getLore() : Lists.newArrayList();
        lore.add(ChatColor.translateAlternateColorCodes('&', Core.getDisplay("sell.item-lore")));
        meta.setLore(lore);
        is.setItemMeta(meta);

        return is;
    }

    public static boolean getBoolean(ItemStack itemStack, String key){
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();

        if(!compound.hasKey(key))
            return false;

        return compound.getBoolean(key);
    }

}
