package com.hpspells.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import com.hpspells.core.util.MiscUtilities;

/**
 * This class manages the wand
 */
public class Wand {
    private HPS HPS;

    public static final String[] WOOD_TYPES = new String[]{"Elder", "Walnut", "Blackthorn", "Ash", "Hawthorn", "Rose", "Hornbeam", "Holly", "Vine", "Mahogany", "Willow", "Elm", "Oak", "Fir", "Cherry", "Chestnut", "Alder", "Yew"};
    public static final String[] CORES = new String[]{"Thestral tail hair", "Dragon heartstring", "Troll whisker", "Unicorn hair", "Veela hair", "Phoenix feather"};

    /**
     * Constructs a new {@link Wand}
     *
     * @param instance an instance of {@link HPS}
     */
    public Wand(HPS instance) {
        this.HPS = instance;
    }

    /**
     * Checks if a given {@link ItemStack} is useable as a wand
     *
     * @param i the itemstack
     * @return {@code true} if the ItemStack is useable as a wand
     */
    public boolean isWand(ItemStack i) {
    	if (i == null)
    		return false;
    	if (i.getTypeId() != (Integer) getConfig("id", 280)) // Item id check
    		return false;
    	
    	if ((Boolean) getConfig("lore.enabled", true) && !i.getItemMeta().getDisplayName().contains("Wand")) // Lore name check
    		return false;

    	return true;
        //return new NBTContainerItem(i).getTag(TAG_NAME) != null;
    }

    /**
     * Gets the wand
     *
     * @param owner a {@link Nullable} parameter that specifies the owner of the wand
     *
     * @return an {@link ItemStack} that has been specified as a wand in the config
     */
    public ItemStack getWand(@Nullable Player owner) {
        ItemStack wand = new ItemStack((Integer) getConfig("id", 280));
        //NBTTagCompound comp = new NBTTagCompound(TAG_NAME);

        if ((Boolean) getConfig("lore.enabled", true)) {
        	Random random = new Random();
            ItemMeta meta = wand.getItemMeta();
            List<String> lore = new ArrayList<String>(Arrays.asList(
        			ChatColor.GOLD + "Current-Spell: " + ChatColor.YELLOW + (owner == null ? "None" : HPS.SpellManager.getCurrentSpell(owner).getName()),
        			WOOD_TYPES[random.nextInt(WOOD_TYPES.length)] + " wood",
        			CORES[random.nextInt(CORES.length)] + " core",
        			random.nextInt(20) + " inches long"
        	));

            meta.setDisplayName(ChatColor.RESET + "" + ChatColor.AQUA + (String) getConfig("lore.name", "Wand"));
            meta.setLore(lore);

            wand.setItemMeta(meta);
        }

        if ((Boolean) getConfig("enchantment-effect", true)) {
            try {
                wand = MiscUtilities.makeGlow(wand);
            } catch (Exception e) {
                HPS.PM.debug(HPS.Localisation.getTranslation("errEnchantmentEffect"));
                HPS.PM.debug(e);
            }
        }

        /*if(owner != null) {
            NBTTagString tag = new NBTTagString();
            tag.setName("Owner");
            tag.set(owner.getName());

            comp.set("Owner", tag);
        }

        NBTContainerItem item = new NBTContainerItem(wand);
        item.setTag(comp);*/
        return wand;
    }
    
    /**
     * Attempts to get a wand from a players inventory.
     * Calls the {@link #updateCurrentSpellLore(player, wand)} method.
     * This method also updates the wand in the players inventory
     * 
     * @param player The player to update current spell with
     * @param inv The players inventory
     * @return the wand's ItemStack
     */
    public ItemStack getWand(Player player, PlayerInventory inv) {
    	ItemStack wand = null;
    	for (ItemStack is : inv.getContents()) {
    		if (isWand(is)) {
    			wand = is;
    			inv.remove(is);
    			if (wand != null) {
    	    		wand = updateCurrentSpellLore(player, wand);
    	    		inv.setItem(inv.firstEmpty(), wand);
    	    	}
    		}
    	}
    	return wand;
    }
    
    /**
     * Update's the current spell listed on the wand. This does not update
     * the actual item in the players inventory. To do that call
     * {@link #getWand(Player, PlayerInventory)}
     * 
     * If the wand does not have a lore it will create a lore
     * containing the current spell
     * 
     * @param player The player
     * @param wand The wand to update
     * @return the updated wand ItemStack
     */
    public ItemStack updateCurrentSpellLore(Player player, ItemStack wand) {
    	ItemMeta meta = wand.getItemMeta();
    	if (!meta.hasLore()) {
    		meta.setLore(Arrays.asList(ChatColor.GOLD + "Current-Spell: " + ChatColor.YELLOW + (player == null ? "None" : HPS.SpellManager.getCurrentSpell(player).getName())));
    	} else {
    		List<String> lore = meta.getLore();
    		if (lore.get(0).contains("Current-Spell")) {
    			lore.set(0, ChatColor.GOLD + "Current-Spell: " + ChatColor.YELLOW + (player == null ? "None" : HPS.SpellManager.getCurrentSpell(player).getName()));
    		} else {
    			lore.add(0, ChatColor.GOLD + "Current-Spell: " + ChatColor.YELLOW + (player == null ? "None" : HPS.SpellManager.getCurrentSpell(player).getName()));
    		}
    		meta.setLore(lore);
    	}
		wand.setItemMeta(meta);
		return wand;
    }

    /**
     * Gets a wand without lore.
     * Used for crafting so that people can't determine wand lore whilst crafting.
     *
     * @return an {@link ItemStack} containing a wand
     */
    public ItemStack getLorelessWand() {
        ItemStack wand = new ItemStack((Integer) getConfig("id", 280));

        if ((Boolean) getConfig("lore.enabled", true)) {
            ItemMeta meta = wand.getItemMeta();
            meta.setDisplayName((String) getConfig("lore.name", "Wand"));
            wand.setItemMeta(meta);
        }

        if ((Boolean) getConfig("enchantment-effect", true))
            try {
                wand = MiscUtilities.makeGlow(wand);
            } catch (Exception e) {
                HPS.PM.debug(HPS.Localisation.getTranslation("errEnchantmentEffect"));
                HPS.PM.debug(e);
            }

        return wand;
    }
    
    private Object getConfig(String string, Object defaultt) {
        return HPS.getConfig().get("wand." + string, defaultt);
    }

}
