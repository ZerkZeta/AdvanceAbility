package com.ZerkZeta.AdvanceAbility.Util;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackUtil {
	
	public static boolean isItemDeepEqual(ItemStack COMPARE1,ItemStack COMPARE2){
		if(COMPARE1.getType() != COMPARE2.getType()){
			return false;
		};
		if(COMPARE1.hasItemMeta()){
			if(!COMPARE2.hasItemMeta()){
				return false;
			}
			ItemMeta COMPARE1Meta = COMPARE1.getItemMeta();
			ItemMeta COMPARE2Meta = COMPARE2.getItemMeta();
			if(!COMPARE1Meta.getDisplayName().equals(COMPARE2Meta.getDisplayName())){
				return false;
			}
			if(!COMPARE1Meta.getLore().equals(COMPARE2Meta.getLore())){
				return false;
			}
			if(COMPARE1Meta.hasEnchants()){
				if(!COMPARE2Meta.hasEnchants()){
					return false;
				};
				if(!COMPARE1Meta.getEnchants().equals(COMPARE2Meta.getEnchants())){
					return false;
				};
			};
		}
		if(!COMPARE1.getData().equals(COMPARE2.getData())){
			return false;
		};
		return true;
	}

}
