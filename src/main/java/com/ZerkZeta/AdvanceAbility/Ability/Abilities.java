package com.ZerkZeta.AdvanceAbility.Ability;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import com.ZerkZeta.AdvanceAbility.AdvanceAbilityMain;
import com.ZerkZeta.AdvanceAbility.Util.AbilityCommand;
import com.ZerkZeta.AdvanceAbility.Ability.MainAction.*;

public class Abilities {
	
	private boolean[] ActionStatus = new boolean[]{false,false,false,false,false};
	private PressShiftAction pressshiftaction;
	private ReleaseShiftAction releaseshiftaction;
	private DropItemAction dropitemaction;
	private RepeatedlyAction repeatedlyaction;
	private HashMap<List<String>,CommandAction> commandsaction;
	private String AbilityName;
	//private String Command;
	private String CommandDescription;
	private String CommandUsage;
	private List<String> CommandAllies;
	private Long PeriodOfCheckDirectionTask;
	private HashMap<String,ItemStack> ItemMap;

	public Abilities(YamlConfiguration file, String ABName)throws Exception{
		//AdvanceAbilityMain.getlogger().Err("");
		AdvanceAbilityMain.getlogger().info("Loading " + ABName);
		this.AbilityName = ABName;
		this.CommandDescription = file.getString("Command.Description", "");
		this.CommandUsage = file.getString("Command.UsageMessage", "");
		this.CommandAllies = file.getStringList("Command.Allies");
		String Type = file.getString("Type");
		if(Type.equalsIgnoreCase("normal")){
			
		}
		else if(Type.equalsIgnoreCase("Suit")){
			
		}
		
		
		if(file.contains("Action.Shift-Press")){
			ActionStatus[0] = true;
		};
		if(file.contains("Action.Shift-Release")){
			ActionStatus[1] = true;
		};
		if(file.contains("Action.Drop-Item")){
			ActionStatus[2] = true;
		};
		if(file.contains("Action.Repeatedly")){
			ActionStatus[3] = true;
		};
		if(file.contains("Action.CommandAction")){
			ActionStatus[4] = true;
		};
		
		if(file.contains("Item")){
			ItemMap = new HashMap<String,ItemStack>();
			for(String ItemName : file.getConfigurationSection("Item").getKeys(false)){
				ItemStack item = file.getItemStack("Item." + ItemName);
				if(item == null){
					throw new IllegalArgumentException("ItemName " + ItemName + " was invalid.");
				}
				ItemMap.put(ItemName, item);
			}
		}
		
		if(isHasPressShiftAction()){
			try{
				this.pressshiftaction = new PressShiftAction(file.getConfigurationSection("Action.Shift-Press"));
			}
			catch (Exception e){
				throw new IllegalArgumentException("Shift-Press in " + this.getName() + " has " + e.getMessage().toLowerCase());
			}
		};
		if(isHasReleaseShiftAction()){
			try{
				releaseshiftaction = new ReleaseShiftAction(file.getConfigurationSection("Action.Shift-Release"));
			}
			catch (Exception e){
				throw new IllegalArgumentException("Shift-Release in " + this.getName() + " has " + e.getMessage().toLowerCase());				
			}
		};
		if(isHasDrop_ItemAction()){
			try{
				dropitemaction = new DropItemAction(file.getConfigurationSection("Action.Drop-Item"));
			}
			catch (Exception e){
				throw new IllegalArgumentException("Drop-Item in " + this.getName() + " has " + e.getMessage().toLowerCase());
			}
		};
		if(isHasRepeatedlyAction()){
			try{
				this.repeatedlyaction = new RepeatedlyAction(file.getConfigurationSection("Action.Repeatedly"));
			}
			catch (Exception e){
				throw new IllegalArgumentException("Repeatedly in " + this.getName() + " has " + e.getMessage().toLowerCase());
			}
		}
		if(isHasCommandAction()){
			commandsaction = new HashMap<List<String>,CommandAction>();
			List<Command> CommandList = new ArrayList<Command>();
			ConfigurationSection commandSection = file.getConfigurationSection("Action.CommandAction");
			for(String CommandName : commandSection.getKeys(false)){
				try{
					CommandAction CMDAC = new CommandAction(commandSection.getConfigurationSection(CommandName));
					CMDAC.setCommandActionName(CommandName);
					List<String> command = CMDAC.getCommandAllies();
					command.add(CommandName);
					commandsaction.put(command, CMDAC);
					CommandList.add(new AbilityCommand(CommandName, CMDAC.getCommandDescription(), CMDAC.getCommandUsage(), CMDAC.getCommandAllies()));
				}
				catch (Exception e){
					throw new IllegalArgumentException("Repeatedly in " + this.getName() + " has " + e.getMessage().toLowerCase());
				}
			}
			AdvanceAbilityMain.getInstance().getCommandMap().registerAll("", CommandList);
		}
		PeriodOfCheckDirectionTask = file.getLong("Direction-Tracker-Period" , 5L);
	}
	
	public static boolean isValidConfiguration(YamlConfiguration yaml){
		if(!yaml.contains("Type")){
			return false;
		}
		if(!yaml.contains("Action")){
			return false;
		}
		return true;
	}
	
	public boolean isHasPressShiftAction(){
		return this.ActionStatus[0];
	}
	
	public boolean isHasReleaseShiftAction(){
		return this.ActionStatus[1];
	}
	
	public boolean isHasDrop_ItemAction(){
		return this.ActionStatus[2];
	}
	
	public boolean isHasRepeatedlyAction(){
		return this.ActionStatus[3];
	}
	
	public boolean isHasCommandAction(){
		return this.ActionStatus[4];
	}
	
	public PressShiftAction getPressShiftAction(){
		return this.pressshiftaction;
	}
	
	public ReleaseShiftAction getReleaseShiftAction(){
		return this.releaseshiftaction;
	}
	
	public DropItemAction getDrop_ItemAction(){
		return this.dropitemaction;
	}
	
	public RepeatedlyAction getRepeatedlyAction(){
		return this.repeatedlyaction;
	}
	
	public HashMap<List<String>,CommandAction> getCommandActionMap(){
		return this.commandsaction;
	}
	
	public String getName(){
		return this.AbilityName;
	}
	
	public String getCommandName(){
		return this.AbilityName;
	}
	
	public String getCommandDescription(){
		return this.CommandDescription;
	}
	
	public String getCommandUsage(){
		return this.CommandUsage;
	}
	
	public boolean isUseMovingAction(){
		boolean[] args = new boolean[]{false,false,false,false};
		if(this.isHasPressShiftAction()){
			args[0] = this.getPressShiftAction().isMovingAction();
		};
		if(this.isHasReleaseShiftAction()){
			args[1] = this.getReleaseShiftAction().isMovingAction();
		};
		if(this.isHasDrop_ItemAction()){
			args[2] = this.getDrop_ItemAction().isMovingAction();
		};
		if(this.isHasRepeatedlyAction()){
			args[3] = this.getRepeatedlyAction().isMovingAction();
		};
		return (args[0] || args[1] || args[2] || args[3]);
	}
	
	public Long getDirectionTrackerPeriod(){
		return this.PeriodOfCheckDirectionTask;
	}
	
	/**
	 * 
	 * @return a new coppy of all commands for this ability
	 */
	public List<String> getCommandAllies(){
		return new ArrayList<String>(this.CommandAllies);
	}
	
	public ItemStack getItem(String Name){
		if(ItemMap.containsKey(Name)){
			return ItemMap.get(Name);
		}
		else{
			return null;
		}
	}
	
}
