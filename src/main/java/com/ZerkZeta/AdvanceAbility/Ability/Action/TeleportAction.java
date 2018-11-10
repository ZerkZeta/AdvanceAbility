package com.ZerkZeta.AdvanceAbility.Ability.Action;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import com.ZerkZeta.AdvanceAbility.AdvanceAbilityMain;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.Target;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.TargetManager;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public class TeleportAction extends BaseAction implements Action{

	private Long Delay;
	private Target TargetEntity;
	private Target TargetLocation;
	
	public TeleportAction(String[] Data)throws Exception{
		if((Data.length != 4)){
			throw new Exception("teleport action need more data");
		};
		this.TargetEntity = TargetManager.newTarget(Data[1]);
		this.TargetLocation = TargetManager.newTarget(Data[2]);
		try{
			this.Delay = Long.valueOf(Data[3]);
		}
		catch (Exception e){
			throw new Exception("invalid dalay");
		}
	}
	
	@Override
	public Long getDalayinOneTwentiethSecond() {
		return Delay;
	}

	@Override
	public String getType() {
		return "Teleport";
	}

	/*@Override
	public TargetOfAction getTargetOfAction() {
		return Target;
	}*/
	
	@Override
	public void Activate(AbilityPlayer player) {
		/*List<Location> LOCLIST = TargetManager.getTargetLocation(target, player);
		if(LOCLIST == null){
			throw new IllegalStateException("Intenal eror while trying to get target, please report.");
		};
		//getTargetLocation cant be EMTYLIST
		if(LOCLIST.isEmpty()){			
			throw new IllegalStateException("Intenal error target has nothing, please report.");
		};
		for(Location loc : LOCLIST){
			player.getPlayer().teleport(loc, TeleportCause.PLUGIN);
		};*/
		
		List<Location> LOCLIST = TargetManager.getTargetLocation(this.TargetLocation, player);
		if(LOCLIST == null){
			throw new IllegalStateException("Intenal eror while trying to get target, please report");
		};
		if(LOCLIST.isEmpty()){
			player.getPlayer().sendMessage(AdvanceAbilityMain.getMessageManager().getMessage("Target is too far"));
			return;
		};
		if(LOCLIST.size() != 1){
			player.getPlayer().sendMessage("TargetLocation of setvelocity was invalid");
		}
		
		List<LivingEntity> ENLIST = this.SendMessageAndgetTargetEntity(player);
		
		for(LivingEntity EN : ENLIST){
			EN.teleport(LOCLIST.get(0), TeleportCause.PLUGIN);
		}
		
	}
	

	/**
	 * Return Target entity
	 */
	@Override
	public Target getTarget() {
		return this.TargetEntity;
	}
	
	public Target getTargetLocation(){
		return this.TargetLocation;
	}

}
