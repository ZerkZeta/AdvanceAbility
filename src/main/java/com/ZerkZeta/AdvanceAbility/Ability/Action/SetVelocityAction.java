package com.ZerkZeta.AdvanceAbility.Ability.Action;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import com.ZerkZeta.AdvanceAbility.AdvanceAbilityMain;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.Target;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.TargetManager;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public class SetVelocityAction extends BaseAction implements Action{
	
	private Long Delay;
	private Target TargetEntity;
	private Target TargetLocation;
	private float XSpeed;
	private float YSpeed;
	private float ZSpeed;
	
	public SetVelocityAction(String[] Data)throws Exception{
		if((Data.length != 7)){
			throw new Exception("play sound action need more data");
		};
		try{
			this.TargetEntity = TargetManager.newTarget(Data[1]);//new TargetOfAction(Data[1]);
		} catch(Exception e){
			throw new Exception("TargetEntity " + e.getMessage());
		}
		try{
			this.TargetLocation = TargetManager.newTarget(Data[2]);//new TargetOfAction(Data[2]);
		} catch(Exception e){
			throw new Exception("TargetLocation " + e.getMessage());
		}		
		try{
			this.XSpeed = Float.parseFloat(Data[3]);
			this.YSpeed = Float.parseFloat(Data[4]);
			this.ZSpeed = Float.parseFloat(Data[5]);
		} catch(Exception e){
			throw new Exception("invalid speed");
		}
		try{
			this.Delay = Long.parseLong(Data[6]);
		} catch(Exception e){
			throw new Exception("invalid delay");
		}
	}
	
	@Override
	public Long getDalayinOneTwentiethSecond() {
		return Delay;
	}

	@Override
	public String getType() {
		return "SetVelocity";
	}

	public float getXSpeed(){
		return this.XSpeed;
	}
	
	public float getYSpeed(){
		return this.YSpeed;
	}
	
	public float getZSpeed(){
		return this.ZSpeed;
	}
	
	@Override
	public void Activate(AbilityPlayer player) {
		/*List<LivingEntity> ENLIST = TargetManager.getTargetLivingEntity(this.TargetEntity, player);
		if(ENLIST == null){
			throw new IllegalStateException("Intenal eror while trying to get target, please report");
		};
		if(ENLIST.isEmpty()){
			player.getPlayer().sendMessage(AdvanceAbilityMain.getMessageManager().getMessage("Target is too far"));
			return;
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
			Vector StartPoint = EN.getLocation().toVector();
			Vector ToPoint = LOCLIST.get(0).toVector();
			Vector Result = ToPoint.subtract(StartPoint);
			Result.setX(Result.getX()*this.XSpeed);
			Result.setY(Result.getY()*this.YSpeed);
			Result.setZ(Result.getZ()*this.ZSpeed);			
			EN.setVelocity(Result);
		};
	}

	@Override
	public Target getTarget() {
		return this.TargetEntity;
	}
	
}
