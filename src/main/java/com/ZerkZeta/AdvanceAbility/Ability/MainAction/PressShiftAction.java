package com.ZerkZeta.AdvanceAbility.Ability.MainAction;

import org.bukkit.configuration.ConfigurationSection;

public class PressShiftAction extends MainAction{
	
	private boolean isHoldShiftAction = false;
	
	public PressShiftAction(ConfigurationSection CS)throws Exception{
		super(CS);
		this.isHoldShiftAction = (CS.getBoolean("isHolding-Shift", false));
	}
	
	public boolean isHoldShiftAction(){
		return this.isHoldShiftAction;
	}

}
