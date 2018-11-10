package com.ZerkZeta.AdvanceAbility.Ability.Action;

import java.util.Iterator;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.ZerkZeta.AdvanceAbility.AdvanceAbilityMain;
import com.ZerkZeta.AdvanceAbility.Ability.Abilities;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.Target;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.TargetManager;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;
import com.ZerkZeta.AdvanceAbility.Util.ItemStackUtil;

public class RepairItemAction extends BaseAction implements Action{
	
	private Long Delay;
	private Target target;
	private String AbilityName;
	private String ItemName;
	
	public RepairItemAction(String[] Data)throws Exception{
		//RepairItemAction|Target|Ability|ItemName|Delay
		if(Data.length != 6){
			throw new Exception("GiveItemActoin action need more data");
		};
		this.target = TargetManager.newTarget(Data[1]);
		this.AbilityName = Data[2];
		this.ItemName = Data[3];
		try{
			this.Delay = Long.valueOf(Data[4]);
		}
		catch(Exception e){
			throw new Exception("Delay must be number");
		}
	}
	
	public String getAbilityName(){
		return this.AbilityName;
	}
	
	public String getItemName(){
		return this.ItemName;
	}
	
	@Override
	public void Activate(AbilityPlayer player) {
		Abilities ItemsAbility = AdvanceAbilityMain.getAbilitiesManager().getAbility(AbilityName);
		if(ItemsAbility == null){
			return;
		};
		ItemStack Item = ItemsAbility.getItem(ItemName);
		if(Item == null){
			return;
		};
		List<LivingEntity> ENLIST = this.SendMessageAndgetTargetEntity(player);
		for(LivingEntity EN : ENLIST){
			if(EN instanceof InventoryHolder){
				Inventory ENInv = ((InventoryHolder) EN).getInventory();				
				for(Iterator<ItemStack> CItemIterator = ENInv.iterator() ; CItemIterator.hasNext();){
					ItemStack CItem = CItemIterator.next();
					if(ItemStackUtil.isItemDeepEqual(Item, CItem)){
						CItem.setDurability(CItem.getType().getMaxDurability());
					}
				}
			}
		}
	}
	
	@Override
	public String getType() {
		return "RepairItem";
	}
	
	@Override
	public Long getDalayinOneTwentiethSecond() {
		return Delay;
	}
	
	@Override
	public Target getTarget() {
		return this.target;
	}

}
