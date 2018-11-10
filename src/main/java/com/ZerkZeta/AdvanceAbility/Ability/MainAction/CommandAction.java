package com.ZerkZeta.AdvanceAbility.Ability.MainAction;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;

import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public class CommandAction extends MainAction{
	
	private String CommandDescription;
	private String CommandUsage;
	private List<String> CommandAllies;
	private String CommandName;
	private HashMap<UUID,Long> coolDownStartTimeMap = new HashMap<UUID,Long>();

	public CommandAction(ConfigurationSection CS) throws Exception {
		super(CS);
		this.CommandAllies = CS.getStringList("Allies");
		this.CommandDescription = CS.getString("Description", "");
		this.CommandUsage = CS.getString("UsageMessage", "");
	}
	
	public String getCommandDescription(){
		return this.CommandDescription;
	}
	
	public String setCommandActionName(String str){
		this.CommandName = str;
		return this.CommandName;
	}
	
	public String getCommandActionName(){
		return this.CommandName;
	}
	
	public String getCommandUsage(){
		return this.CommandUsage;
	}
	
	public List<String> getCommandAllies(){
		return this.CommandAllies;
	}
	
	public boolean isCoolingDown(AbilityPlayer player){
		UUID playerUUID = player.getUUID();
		if(this.coolDownStartTimeMap.containsKey(playerUUID)){
			Long startTime = coolDownStartTimeMap.get(playerUUID);
			Long timeLeft = this.getCoolDownTime() - (System.currentTimeMillis() - startTime);
			if(this.getCoolDownTime() - timeLeft > 1L){
				return true;
			};
			/*else{
				return false;
			}*/
		}
		return false;
	}
	
	public void setCoolDownStartTime(AbilityPlayer player){
		setCoolDownStartTime(player,System.currentTimeMillis());
	}
	
	public void setCoolDownStartTime(AbilityPlayer player, Long StratTime){
		this.coolDownStartTimeMap.put(player.getUUID(), StratTime);
	}
	
	/**
	 * !!!!!!!Will return null!!!! if player is not coolingdown yet
	 * @param player
	 * @return
	 */
	public Long getCoolDownStartTime(AbilityPlayer player){
		if(this.coolDownStartTimeMap.containsKey(player.getUUID())){
			return this.coolDownStartTimeMap.get(player.getUUID());
		}
		else{
			return null;
		}
	}

}
