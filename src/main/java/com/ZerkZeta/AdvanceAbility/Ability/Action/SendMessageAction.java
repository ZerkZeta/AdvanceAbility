package com.ZerkZeta.AdvanceAbility.Ability.Action;

import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.Target;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.TargetManager;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public class SendMessageAction extends BaseAction implements Action{
	
	private Long Delay;
	private Target target;
	private String Message;
	
	public SendMessageAction(String[] Data)throws Exception{
		//SendMessage|Target|Message|Delay
		if(!(Data.length != 4)){
			throw new Exception("send message action need more data");
		};
		
		this.target = TargetManager.newTarget(Data[1]);
		
		this.Message = Data[2].replace("&&", "\u00A7");
		
		try{
			this.Delay = Long.valueOf(Data[3]);
		}
		catch (Exception e){
			throw new Exception("Invalid dalay");
		}
	}
	
	public String getMessage(){
		return this.Message;
	}

	@Override
	public void Activate(AbilityPlayer player) {
		List<LivingEntity> ENLIST = this.SendMessageAndgetTargetEntity(player);
		for(LivingEntity EN : ENLIST){
			if(EN instanceof Player){
				((Player) EN).sendMessage(this.Message);
			}
		};
	}

	@Override
	public Long getDalayinOneTwentiethSecond() {
		return this.Delay;
	}

	@Override
	public String getType() {
		return "SendMessage";
	}

	@Override
	public Target getTarget() {
		return this.target;
	}

}
