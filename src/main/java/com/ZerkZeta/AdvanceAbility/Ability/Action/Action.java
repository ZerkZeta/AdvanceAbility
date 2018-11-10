package com.ZerkZeta.AdvanceAbility.Ability.Action;

import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.Target;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public interface Action {
	
	//public abstract Action(ConfigurationSection cs){};
	
	public Long getDalayinOneTwentiethSecond();
	
	public String getType();
		
	//public TargetOfAction getTargetOfAction();
	
	public void Activate(AbilityPlayer player);
	
	public Target getTarget();
	
	//public abstract void Active(AbilityPlayer User);

}
