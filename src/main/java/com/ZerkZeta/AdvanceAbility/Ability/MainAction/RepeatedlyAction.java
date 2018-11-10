package com.ZerkZeta.AdvanceAbility.Ability.MainAction;

import org.bukkit.configuration.ConfigurationSection;

public class RepeatedlyAction extends MainAction{
	
	private Long Period;
	
	public RepeatedlyAction(ConfigurationSection CS) throws Exception {
		super(CS);
		this.Period = CS.getLong("Period",-1L);
		if(this.Period <= 0L){
			throw new Exception("Invalid period");
		}
	}
	
	public Long getPeriod(){
		return this.Period;
	}

}
