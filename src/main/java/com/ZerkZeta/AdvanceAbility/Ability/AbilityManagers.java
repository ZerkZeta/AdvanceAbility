package com.ZerkZeta.AdvanceAbility.Ability;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ZerkZeta.AdvanceAbility.AdvanceAbilityMain;
import com.ZerkZeta.AdvanceAbility.Util.AbilityCommand;

public class AbilityManagers {
	
	private static HashMap<String,Abilities> AbilitiesMap = new HashMap<String,Abilities>();
	//private HashMap<Abilities,List<AbilityPlayer>> AbilitiesMapWithPlayer = new HashMap<Abilities,List<AbilityPlayer>>();
	
	public AbilityManagers(){
		File file = new File(AdvanceAbilityMain.getInstance().getDataFolder() + File.separator + "Abilities");
		if(!file.exists()){
			file.mkdirs();
		}
		Enable(file);
	}
	
	public void Enable(File folder){
		File[] listOfFiles = folder.listFiles();
		for(File file : listOfFiles){
			if (!file.isFile()) {
	    		//not a file
	    		continue;
	    	}
			String AbilityName;
			if(file.getName().contains(".")){
				AbilityName = file.getName().toString().substring(0, file.getName().toString().lastIndexOf('.'));
			}
			else{
				AbilityName = file.getName();
			}
			YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
			if(!Abilities.isValidConfiguration(yaml)){
				AdvanceAbilityMain.getlogger().Err(AbilityName + " need more data, " + AbilityName + " won't be load");
				continue;
			}
			/*Abilities Ablitity = new Abilities(yaml);
			AbilitiesMapWithPlayer.put(new Abilities(yaml), Ablitity.getPlayers());*/
			try {
				AbilitiesMap.put(AbilityName, new Abilities(yaml,AbilityName) );
			} catch (Exception e) {
				if(e instanceof IllegalArgumentException){
					AdvanceAbilityMain.getlogger().Err(e.getMessage());
					AdvanceAbilityMain.getlogger().Err(AbilityName + " won't be load");
					continue;
				}
				AdvanceAbilityMain.getlogger().Err(AbilityName + " " + e.getMessage() + ", " + AbilityName + " won't be load");
				continue;
			}
			AdvanceAbilityMain.getlogger().info(AbilityName + " has been loaded");
		}
		List<Command> CommandList = new ArrayList<Command>();
		for(Abilities Ab : AbilitiesMap.values()){
			CommandList.add(new AbilityCommand(Ab.getCommandName(), Ab.getCommandDescription(), Ab.getCommandUsage(), Ab.getCommandAllies()));
		}
		AdvanceAbilityMain.getInstance().getCommandMap().registerAll("", CommandList);
	}
	
	public void Disable(){
		
	}
	
	public Abilities getAbility(String AbilityName){
		if(AbilitiesMap.containsKey(AbilityName)){
			return AbilitiesMap.get(AbilityName);
		}
		return null;
	}
	
	public Collection<Abilities> getAbilitiesList(){
		return AbilitiesMap.values();
	}
	
}
