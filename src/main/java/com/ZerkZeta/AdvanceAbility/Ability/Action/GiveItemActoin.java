package com.ZerkZeta.AdvanceAbility.Ability.Action;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.ZerkZeta.AdvanceAbility.AdvanceAbilityMain;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.Target;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.TargetManager;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public class GiveItemActoin extends BaseAction implements Action{
	
	private Long Delay;
	private Target target;
	private String ErrorMessage;
	private String Slot;
	private String ItemName;
	
	public GiveItemActoin(String[] Data)throws Exception{
		//GiveItem|Target|ItemName|Slot|ErrorMessage|Delay #Slot is "FirstEmty OR FirstToolBarEmty OR LastEmty OR LastToolBarEmty OR []"
		if(Data.length != 6){
			throw new Exception("GiveItemActoin action need more data");
		};
		this.target = TargetManager.newTarget(Data[1]);
		this.ItemName = Data[2];
		this.Slot = Data[3];
		try{
			Integer.parseInt(Data[3]);
		}
		catch(Exception e){
			if(!(Data[3].equalsIgnoreCase("FirstEmty") || Data[3].equalsIgnoreCase("FirstToolBarEmty") || Data[3].equalsIgnoreCase("LastEmty") || Data[3].equalsIgnoreCase("LastToolBarEmty"))){
				throw new Exception("Slot must be number OR FirstEmty OR FirstToolBarEmty OR LastEmty OR LastToolBarEmty OR []");
			}
		}
		this.ErrorMessage = Data[4].replace("&&", "\u00A7");
		try{
			this.Delay = Long.valueOf(Data[5]);
		}
		catch(Exception e){
			throw new Exception("Delay must be number");
		}
	}
	
	@Override
	public String getType() {
		return "GiveItem";
	}
	
	@Override
	public Long getDalayinOneTwentiethSecond() {
		return Delay;
	}

	@Override
	public void Activate(AbilityPlayer player) {
		ItemStack Item = player.getActiveAbility().getItem(ItemName);
		if(Item == null){
			player.getPlayer().sendMessage(AdvanceAbilityMain.getMessageManager().getMessage("Item was not found"));
			return;
		}
		List<LivingEntity> ENLIST = this.SendMessageAndgetTargetEntity(player);
		Player BukkitPlayer = player.getPlayer();
		for(LivingEntity EN : ENLIST){
			if(EN instanceof InventoryHolder){
				Integer slot = null;
				Inventory inventory = ((InventoryHolder) EN).getInventory();
				if(Slot.equalsIgnoreCase("FirstEmty")){
					slot = inventory.firstEmpty();
				};
				if(Slot.equalsIgnoreCase("FirstToolBarEmty")){
					slot = inventory.firstEmpty();
					if(slot > 8){
						BukkitPlayer.sendMessage(ErrorMessage);
						return;
					}
				};
				if(Slot.equalsIgnoreCase("LastEmty")){
					for(int i = inventory.getSize(); i >= 0; i--){
						if(inventory.getItem(i).getType().equals(Material.AIR)){
							slot = i;
							break;
						}
					}
				};
				if(Slot.equalsIgnoreCase("LastToolBarEmty")){
					for(int i = 8; i >= 0; i--){
						if(inventory.getItem(i).getType().equals(Material.AIR)){
							slot = i;
							break;
						}
					}
				};
				if(slot == null){
					BukkitPlayer.sendMessage(ErrorMessage);
					return;
				};
				((InventoryHolder) EN).getInventory().setItem(slot, Item);
			}
		};
	}

	@Override
	public Target getTarget() {
		return this.target;
	}

}
