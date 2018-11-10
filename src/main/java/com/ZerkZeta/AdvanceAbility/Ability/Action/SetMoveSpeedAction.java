package com.ZerkZeta.AdvanceAbility.Ability.Action;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.Target;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.TargetManager;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public class SetMoveSpeedAction extends BaseAction implements Action{
	
	private Long Delay;
	private Target target;
	private float speed;
	
	public SetMoveSpeedAction(String[] Data)throws Exception{
		//SetMoveSpeed|Target|Value|Delay
		if(!(Data.length != 4)){
			throw new Exception("set move speed action need more data");
		};
		
		this.target = TargetManager.newTarget(Data[1]);
		
		try{
			this.speed = Float.parseFloat(Data[2]);
		}
		catch (Exception e){
			throw new Exception("Invalid speed");
		}
		
		try{
			this.Delay = Long.valueOf(Data[3]);
		}
		catch (Exception e){
			throw new Exception("Invalid dalay");
		}
	}
	
	@Override
	public void Activate(AbilityPlayer player) {
		List<LivingEntity> ENLIST = this.SendMessageAndgetTargetEntity(player);
		for(LivingEntity EN : ENLIST){
			if(EN instanceof Player){
				try{
					((Player) EN).setWalkSpeed(speed);
				}
				catch (Exception e){
					player.getPlayer().sendMessage("Invalid movement speed");
				}
			}
			
		};
	}

	@Override
	public Long getDalayinOneTwentiethSecond() {
		return this.Delay;
	}

	@Override
	public String getType() {
		return "SetMoveSpeed";
	}
	
	public float getSpeed(){
		return this.speed;
	}

	@Override
	public Target getTarget() {
		return this.target;
	}

}
