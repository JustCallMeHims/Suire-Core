package me.suiremc.core.listener;

import com.google.common.collect.ImmutableList;
import me.suiremc.core.objects.ItemValue;
import me.suiremc.core.util.NMSTag;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.InventoryHolder;

/**
 * TODO:
 * When a block with the material specified in ITEM_VALUES is broken, FORTUNE enchantment is not effective.
 * A: Remove Fortune
 * B: Support fortune
 *
 * A:
 *   +This way it's harder to obtain tokens
 *   -Players need to miss out on the fortune enchantment
 *
 * B:
 *   +Everybody can use fortune
 *   -Tokens are easily obtainable.
 */
public class ItemValuer implements Listener {


    //TODO: Move to config
    private static final ImmutableList<ItemValue> ITEM_VALUES = ImmutableList.of(
            ItemValue.of(Material.STONE, 0.025),
            ItemValue.of(Material.COBBLESTONE, 0.020),

            ItemValue.of(Material.DIAMOND, 1.0),
            ItemValue.of(Material.DIAMOND_ORE, 1.0),

            ItemValue.of(Material.GOLD_INGOT, 0.5),
            ItemValue.of(Material.GOLD_ORE, 0.5),

            ItemValue.of(Material.IRON_INGOT, 0.25),
            ItemValue.of(Material.IRON_ORE, 0.25),

            ItemValue.of(Material.COAL, 0.15),
            ItemValue.of(Material.COAL_ORE, 0.15),

            ItemValue.of(Material.INK_SACK, 0.20, (short)4),
            ItemValue.of(Material.LAPIS_ORE, 1),

            ItemValue.of(Material.REDSTONE, 0.20),
            ItemValue.of(Material.REDSTONE_ORE, 1.0),

            ItemValue.of(Material.RAW_FISH, 1.5),

            ItemValue.of(Material.OBSIDIAN, 2)
    );

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event){
        if(event.getBlock().getState().getData() instanceof InventoryHolder || event.getPlayer().getGameMode()
        == GameMode.CREATIVE)
            return;

        if(!isValuableItem(event.getBlock().getType(), null))
            return;
       // Set<ItemStack> toUpdate = new HashSet<>(event.getBlock().getDrops());


        event.getBlock().getDrops().forEach(item -> event.getBlock().getWorld()
                .dropItem(event.getPlayer().getLocation(), NMSTag.setTag(item, "natural", true)));

        event.getBlock().setType(Material.AIR);
    }

    @EventHandler
    public void onFish(PlayerFishEvent event){
        // Checking the instance of getCaught() because I'm unsure of what it can return.
        if(event.getCaught() == null || !(event.getCaught() instanceof Item))
            return;

        Item item = (Item)event.getCaught();
        item.setItemStack(NMSTag.setTag(item.getItemStack(), "natural",  true));
    }

    @EventHandler
    public void onSmelt(FurnaceSmeltEvent event){
        if(!isValuableItem(event.getSource().getType(), null)
                || !NMSTag.getBoolean(event.getSource(), "natural"))
            return;

        event.setResult(NMSTag.setTag(event.getResult(), "natural", true));
    }

    public static ImmutableList<ItemValue> getItemValues() {
        return ITEM_VALUES;
    }

    public static ItemValue getItemValue(Material material){
        return getItemValues().stream().filter(i -> i.getMaterial().equals(material)).findFirst().orElse(null);
    }

    public static boolean isValuableItem(Material material, Short data){
        ItemValue itemValue = getItemValue(material);

        if(itemValue == null)
            return false;

        if(data != null){
            return itemValue.getData() == data;
        }

        return true;
    }
}
