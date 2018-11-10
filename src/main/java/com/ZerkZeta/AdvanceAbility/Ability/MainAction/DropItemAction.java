package com.ZerkZeta.AdvanceAbility.Ability.MainAction;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class DropItemAction extends MainAction{

	private ItemStack ItemToTrack;
	
	public DropItemAction(ConfigurationSection CS)throws Exception{
		super(CS);
		if(CS.getBoolean("Certain-Item",false)){
			ItemToTrack = CS.getItemStack("Certain-Item-Data");
			if(ItemToTrack == null){
				throw new Exception("Invalid item data");
			};
			ItemToTrack = ItemToTrack.clone();
		}
	}
	
	/**
	 * get Item to track
	 * @return Item to track, null if no item to track
	 */
	public ItemStack getItemStackToTrack(){
		return ItemToTrack;
	}

}
