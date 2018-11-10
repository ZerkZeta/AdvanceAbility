package com.ZerkZeta.AdvanceAbility.Ability.Action.Target;

import org.bukkit.Location;
import org.bukkit.block.Block;

import com.ZerkZeta.AdvanceAbility.AdvanceAbilityMain;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public class HisAimingLocationTarget implements Target{
	
	/**
	 * getTarget of sender
	 * @param sender player who use this ability
	 * @return Location - that sender aimming to OR null if out of bound.
	 */
	protected Location getTarget(AbilityPlayer sender){	
		@SuppressWarnings("deprecation")
		Block b = sender.getPlayer().getTargetBlock(AdvanceAbilityMain.getTransparentBlock(), 120);
		if(b != null){
			return b.getLocation();
		}
		else{
			return null;
		}
	}

	@Override
	public String getType() {
		return "AimmingTarget";
	}

}
