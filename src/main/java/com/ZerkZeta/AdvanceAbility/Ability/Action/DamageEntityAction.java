package com.ZerkZeta.AdvanceAbility.Ability.Action;

import java.util.List;

import org.bukkit.entity.LivingEntity;

import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.Target;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.TargetManager;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public class DamageEntityAction extends BaseAction implements Action{
	
	private Long Delay;
	private Target target;
	private double DamageAmount;
	
	public DamageEntityAction(String[] Data)throws Exception{
		if(Data.length != 4){
			throw new Exception("DamageEntity action need more data");
		};
		this.DamageAmount = Double.parseDouble(Data[1]);
		this.target = TargetManager.newTarget(Data[2]);
		try{
			this.Delay = Long.valueOf(Data[3]);
		}
		catch(Exception e){
			throw new Exception("Delay must be number");
		}
	}
	
	@Override
	public String getType() {
		return "DamageEntity";
	}
	
	@Override
	public Long getDalayinOneTwentiethSecond() {
		return Delay;
	}

	@Override
	public void Activate(AbilityPlayer player) {
		List<LivingEntity> ENLIST = this.SendMessageAndgetTargetEntity(player);
		for(LivingEntity EN : ENLIST){
			EN.damage(DamageAmount, player.getPlayer());
		};
	}

	@Override
	public Target getTarget() {
		return this.target;
	}

}
