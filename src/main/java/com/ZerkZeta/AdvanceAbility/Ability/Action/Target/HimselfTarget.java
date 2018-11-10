package com.ZerkZeta.AdvanceAbility.Ability.Action.Target;

import org.bukkit.entity.Player;

import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public class HimselfTarget implements Target{

	protected Player getTarget(AbilityPlayer sender){
		return sender.getPlayer();
	}

	@Override
	public String getType() {
		return "Himself";
	}

}
