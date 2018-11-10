package com.ZerkZeta.AdvanceAbility.AbilityPlayers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CancellationException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.ZerkZeta.AdvanceAbility.AdvanceAbilityMain;
import com.ZerkZeta.AdvanceAbility.Ability.Abilities;

public class AbilityPlayersManagers {

	//private HashSet<AbilityPlayer> AbilityPlayerList = new HashSet<AbilityPlayer>();
	private File PlayersDataFile;
	private ArrayList<AbilityPlayer> AbilityPlayerList = new ArrayList<AbilityPlayer>();
	private YamlConfiguration PlayerDataYML;
	
	public AbilityPlayersManagers(File PlayersDataFolder,String FileName){
		if(!PlayersDataFolder.exists()){
			PlayersDataFolder.mkdirs();
		}
		//{{initialze YAML file
		PlayersDataFile = new File(PlayersDataFolder.getPath() + File.separator + FileName);
		PlayerDataYML = new YamlConfiguration();
		if(!PlayersDataFile.exists()){
			try {
				PlayerDataYML.createSection("Player-Data");
				PlayerDataYML.save(PlayersDataFile);
			} catch (IOException e) {
				AdvanceAbilityMain.getlogger().Err("Can't Generate EmtyConFiguration for 'Player-DataFile', error details: ");
				e.printStackTrace();
			}
		}
		else{//Already have a file
			PlayerDataYML = YamlConfiguration.loadConfiguration(PlayersDataFile);
		}
		//}}
		Set<String> PlayersUUIDstr = PlayerDataYML.getConfigurationSection("Player-Data").getKeys(false);
		for(String PlayerUUIDstr : PlayersUUIDstr){
			ConfigurationSection PlayerDataSel = PlayerDataYML.getConfigurationSection("Player-Data." + PlayerUUIDstr);
			try {
				AbilityPlayerList.add(AbilityPlayer.Load(PlayerDataSel,PlayerUUIDstr));
			} catch (Exception e) {
				if(e instanceof IllegalArgumentException){
					AdvanceAbilityMain.getlogger().Err("Error can't load " + PlayerUUIDstr + " invalid player UUID");
					continue;
				}
				else if(!(e instanceof CancellationException)){
					AdvanceAbilityMain.getlogger().Err(e.getMessage());
				}
				//if continue = player is not online( offline = CancellationException)
				continue;
			}
		}
	}
		
	public AbilityPlayer AddPlayer(Player player, Abilities ability){
		AbilityPlayer ABPlayer = getFormUUID(player.getUniqueId());
		if(ABPlayer != null){
			return ABPlayer;
		}
		ABPlayer = new AbilityPlayer(player, ability);
		AbilityPlayerList.add(ABPlayer);
		return ABPlayer;		
	}
	
	public boolean isOfflineAbilityPlayer(Player player){
		if(player == null){
			return false;
		}
		return PlayerDataYML.contains("Player-Data." + player.getUniqueId());
	}
	
	public AbilityPlayer ContinueSession(Player player){
		if(player == null){
			AdvanceAbilityMain.getlogger().Err("Intenal error player is null");
		}
		String PlayerUUIDstr = player.getUniqueId().toString();
		try {
			AdvanceAbilityMain.getlogger().Debug("Continue session for " + player.getName());
			AbilityPlayer Abplayer = AbilityPlayer.Load(PlayerDataYML.getConfigurationSection("Player-Data." + PlayerUUIDstr) ,PlayerUUIDstr);
			AbilityPlayerList.add(Abplayer);
			AdvanceAbilityMain.getlogger().Debug("Session continued for " + player.getName());
			return Abplayer;
		} catch (Exception e) {
			if(e instanceof IllegalArgumentException){
				AdvanceAbilityMain.getlogger().Err("Error can't load " + PlayerUUIDstr + " invalid player UUID");
				return null;
			}
			else if(!(e instanceof CancellationException)){
				if(e.getMessage() != null){
					AdvanceAbilityMain.getlogger().Err(e.getMessage());
				}
				else{
					AdvanceAbilityMain.getlogger().Err("Something got error, detail: ");
					e.printStackTrace();
				}
				return null;
			}
		}
		return null;
	}
	
	public void EndSession(Player player){
		AbilityPlayer ABPlayer = getFormUUID(player.getUniqueId());
		AbilityPlayerList.remove(ABPlayer);
		PlayerDataYML = ABPlayer.saveToYml(PlayerDataYML);
		ABPlayer.DeactivateRepeatedlyAction();
		ABPlayer.DeactivateMovementDetectionAction();
		ABPlayer = null;
	}
	
	public void DeleteSession(Player player){
		AbilityPlayer ABPlayer = getFormUUID(player.getUniqueId());
		if(ABPlayer == null){
			return;
		}
		AbilityPlayerList.remove(ABPlayer);
		PlayerDataYML.set("Player-Data." + player.getUniqueId().toString(), null);
	}
	
	public void SaveAll(){
		for(AbilityPlayer ABPlayer : AbilityPlayerList){
			PlayerDataYML = ABPlayer.saveToYml(PlayerDataYML);
		}
		try {
			PlayerDataYML.save(PlayersDataFile);
		} catch (IOException e) {
			AdvanceAbilityMain.getlogger().Err("Can't save player data file, Error details: ");
			e.printStackTrace();
		}
	}

	public boolean isAbilityPlayer(Player player){
		for(AbilityPlayer ABPlayer : AbilityPlayerList){
			if(ABPlayer.getUUID() == player.getUniqueId()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * get AbilityPlayer from uuid, May return null if player not found
	 * @param playerID
	 * @return AbilityPlayer from a given uuid
	 */
	public AbilityPlayer getFormUUID(UUID playerID){
		for(AbilityPlayer ABPlayer : AbilityPlayerList){
			if(ABPlayer.getUUID() == playerID){
				return ABPlayer;
			}
		}
		return null;
	}
	
	public AbilityPlayer getPlayerOrCreatePlayer(Player player){
		for(AbilityPlayer ABPlayer : AbilityPlayerList){
			if(ABPlayer.getUUID() == player.getUniqueId()){
				return ABPlayer;
			}
		}
		if(isOfflineAbilityPlayer(player)){
			ContinueSession(player);
			return getFormUUID(player.getUniqueId());
		}
		else{
			return AddPlayer(player,null);
		}
	}
	
}
