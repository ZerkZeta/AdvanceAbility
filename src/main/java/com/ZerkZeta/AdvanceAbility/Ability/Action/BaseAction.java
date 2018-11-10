package com.ZerkZeta.AdvanceAbility.Ability.Action;

import java.util.List;

import org.bukkit.entity.LivingEntity;

import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.EmtyAbleTarget;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.Target;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.TargetManager;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public abstract class BaseAction implements Action{
	
	protected List<LivingEntity> SendMessageAndgetTargetEntity(AbilityPlayer player){
		Target target = this.getTarget();
		List<LivingEntity> ENLIST = TargetManager.getTargetLivingEntity(target, player);
		if(ENLIST == null){
			throw new IllegalStateException("Intenal eror while trying to get target, please report");
		};
		if(ENLIST.isEmpty()){
			if(target instanceof EmtyAbleTarget){
				String Message = ((EmtyAbleTarget) target).getEmtyMessage();
				if(Message.equalsIgnoreCase("")){
					return ENLIST;
				}
				Message = Message.replace("&&", "\u00A7");
				player.getPlayer().sendMessage(Message);
			}
			//player.getPlayer().sendMessage(AdvanceAbilityMain.getMessageManager().getMessage("Target is too far"));			
		};
		return ENLIST;
	}
	
	/*protected List<Location> SendMessageAndgetTargetLocation(AbilityPlayer player){
		Target target = this.getTarget();
		List<Location> LOCLIST = TargetManager.getTargetLocation(target, player);
		if(LOCLIST == null){
			throw new IllegalStateException("Intenal eror while trying to get target, please report");
		};
		if(LOCLIST.isEmpty()){
			if(target instanceof EmtyAbleTarget){
				String Message = ((EmtyAbleTarget) target).getEmtyMessage();
				if(Message.equalsIgnoreCase("")){
					return LOCLIST;
				}
				Message = Message.replace("&&", "\u00A7");
				player.getPlayer().sendMessage(Message);
			}
		};
		return LOCLIST;
	}*/

}
