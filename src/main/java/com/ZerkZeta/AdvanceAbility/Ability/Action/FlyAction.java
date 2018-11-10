package com.ZerkZeta.AdvanceAbility.Ability.Action;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.Target;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.TargetManager;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public class FlyAction extends BaseAction implements Action{
	
	private Long Delay;
	private Target target;
	private boolean fly;
	private float FlySpeed;
	
	public FlyAction(String[] Data)throws Exception{
		//Fly|Target|Value|Flyspped|Delay
		if(!(Data.length != 5)){
			throw new Exception("fly action need more data");
		};
		
		this.target = TargetManager.newTarget(Data[1]);
		
		try{
			this.fly = Boolean.parseBoolean(Data[2]);
		}
		catch (Exception e){
			throw new Exception("Invalid value");
		}
		
		try{
			this.FlySpeed = Float.parseFloat(Data[3]);
		}
		catch (Exception e){
			throw new Exception("Invalid fly speed");
		}
		try{
			this.Delay = Long.valueOf(Data[4]);
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
					((Player) EN).setAllowFlight(fly);
					((Player) EN).setFlying(fly);
					((Player) EN).setFlySpeed(FlySpeed);
				}
				catch (Exception e){
					player.getPlayer().sendMessage("Invalid arguments");
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
		return "Fly";
	}
	
	public boolean getFlyValue(){
		return this.fly;
	}
	
	public float getFlySpeed(){
		return this.FlySpeed;
	}

	@Override
	public Target getTarget() {
		return this.target;
	}

}
