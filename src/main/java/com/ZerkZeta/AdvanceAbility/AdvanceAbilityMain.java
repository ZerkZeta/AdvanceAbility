package com.ZerkZeta.AdvanceAbility;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R3.CraftServer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.ZerkZeta.AdvanceAbility.Ability.Abilities;
import com.ZerkZeta.AdvanceAbility.Ability.AbilityManagers;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Action;
import com.ZerkZeta.AdvanceAbility.Ability.MainAction.*;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayersManagers;
import com.ZerkZeta.AdvanceAbility.Listeners.AbilityPlayerListener;
import com.ZerkZeta.AdvanceAbility.Util.MessagerManager;

public class AdvanceAbilityMain extends JavaPlugin{
	
	public static Logger logger;
	private static AdvanceAbilityMain AdvanceAbilityMainInstance;
	private static MessagerManager MessageManage;
	private static AbilityPlayersManagers AbilityPlayersManager;
	private static AbilityPlayerListener ABPlayerListener;
	private static AbilityManagers ABManager;
	private boolean isDebuging = true;
	private static HashSet<Byte> transparentBlock = new HashSet<Byte>(Arrays.asList((byte)0, (byte)31, (byte)30 , (byte)50, (byte)175 , (byte)37 , (byte)38 ));
	
//{{ Overriding
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		if(!this.getDataFolder().exists())
        {
			this.getDataFolder().mkdir();
        }
		Initialize();
		logger.info(this.getDescription().getName() + " Version " + this.getDescription().getVersion() + " Has Been Enabled!");
	}
		
	@Override
	public void onDisable() {
		AbilityPlayersManager.SaveAll();
		logger.info(this.getDescription().getName() + " Version " + this.getDescription().getVersion() + " Has Been Disabled!");
	}
		
//}}

//{{ Commands
	
	public boolean onCommand(final CommandSender sender, Command command, String CommandLable, String[] args){
		if(CommandLable.equalsIgnoreCase("AdvanceAbility") || CommandLable.equalsIgnoreCase("AA") 
				|| CommandLable.equalsIgnoreCase("AdAbility") || CommandLable.equalsIgnoreCase("AAB")
				|| CommandLable.equalsIgnoreCase("Ability") || CommandLable.equalsIgnoreCase("SpecialAbility") ){
			if(args.length < 1){
				sender.sendMessage(MessageManage.getMessage("Too few arguments"));
				return true;
			}
			Abilities Ab = ABManager.getAbility(args[0]);
			if(Ab == null){
				AdvanceAbilityMain.getlogger().Err("AbilityName: " + args[0] + " was not found");
				return true;
			}
			//AdvanceAbilityMain.getlogger().info("Data for: ");
			AdvanceAbilityMain.getlogger().info("/nName: " + Ab.getName());
			AdvanceAbilityMain.getlogger().info("Is has press shift action: " + Ab.isHasPressShiftAction());
			AdvanceAbilityMain.getlogger().info("Is has release shift action: " + Ab.isHasReleaseShiftAction());
			AdvanceAbilityMain.getlogger().info("Is has drop Item action: " + Ab.isHasDrop_ItemAction());
			//AbilityPlayer Abplayer = AbilityPlayersManager.getFormUUID(((Player)sender).getUniqueId());
			if(Ab.isHasPressShiftAction()){
				PressShiftAction PAction = Ab.getPressShiftAction();
				AdvanceAbilityMain.getlogger().Debug("\nPressShiftAction: ");
				for(Long Delay : PAction.getDelayAndActionMap().keySet()){
					for(Action ac : PAction.getDelayAndActionMap().get(Delay)){
						logger.info("Delay = " + Delay + " \tAction = " + ac.getClass().getSimpleName());
					}
				}
			}
			if(Ab.isHasReleaseShiftAction()){
				ReleaseShiftAction RAction = Ab.getReleaseShiftAction();
				AdvanceAbilityMain.getlogger().Debug("\nReleaseShiftAction: ");
				for(Long Delay : RAction.getDelayAndActionMap().keySet()){
					for(Action ac : RAction.getDelayAndActionMap().get(Delay)){
						logger.info("Delay = " + Delay + " \tAction = " + ac.getClass().getSimpleName());
					}
				}
			}
			if(Ab.isHasDrop_ItemAction()){
				DropItemAction DIAction = Ab.getDrop_ItemAction();
				AdvanceAbilityMain.getlogger().Debug("\nDropItemAction: ");
				logger.info("Tarcking item = " + DIAction.getItemStackToTrack().toString());
				for(Long Delay : DIAction.getDelayAndActionMap().keySet()){
					for(Action ac : DIAction.getDelayAndActionMap().get(Delay)){
						logger.info("Delay = " + Delay + " \tAction = " + ac.getClass().getSimpleName());
					}
				}
			}
			return true;
		}
		for(Abilities Ab : ABManager.getAbilitiesList()){
			if(Ab.getCommandName().equalsIgnoreCase(CommandLable)){
				return ActivateAbilityFromCommand(sender,Ab);
			}
			else{
				for(String CommandAlly : Ab.getCommandAllies()){
					if(CommandAlly.equalsIgnoreCase(CommandLable)){
						return ActivateAbilityFromCommand(sender,Ab);
					}
				}
			}
			continue;
		};
		for(Abilities Ab : ABManager.getAbilitiesList()){
			if(!Ab.isHasCommandAction()){
				continue;
			}
			for(List<String> commandlist : Ab.getCommandActionMap().keySet()){
				for(String commandname : commandlist){
					if(CommandLable.equalsIgnoreCase(commandname)){
						if(!(sender instanceof Player)){
							sender.sendMessage(MessageManage.getMessage("This command only can be run by a player"));
							return true;
						}
						Player player = (Player)sender;
						AbilityPlayer Abplayer = AbilityPlayersManager.getFormUUID(player.getUniqueId());
						if(Abplayer == null){
							return false;
						}
						if(!Abplayer.isAbilityActivate()){
							return false;
						}
						Abplayer.ActivateAction(Ab.getCommandActionMap().get(commandlist));
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean ActivateAbilityFromCommand(CommandSender sender, Abilities Ab){
		if(!(sender instanceof Player)){
			sender.sendMessage(MessageManage.getMessage("This command only can be run by a player"));
			return true;
		}
		Player player = (Player)sender;
		AbilityPlayer Abplayer = AbilityPlayersManager.getPlayerOrCreatePlayer(player);
		if(Abplayer.isAbilityActivate()){
			if(Abplayer.getActiveAbility().getName() != Ab.getName()){
				sender.sendMessage(MessageManage.getMessage("You only can activate one ability a time"));
				return true;
			}
			else{
				Abplayer.setActiveAbility(null);
				sender.sendMessage(Ab.getName() + " " + MessageManage.getMessage("Deactivated"));
				return true;
			}
		}
		else{
			Abplayer.setActiveAbility(Ab);
			sender.sendMessage(Ab.getName() + " " + MessageManage.getMessage("Activated"));
			return true;
		}
	}
		
//}}
	
//{{Method	
	
	public static com.ZerkZeta.AdvanceAbility.Logger getlogger(){
		return logger;
	}
	
	private void Initialize(){
		AdvanceAbilityMainInstance = this;
		logger = new Logger(ChatColor.AQUA + "[AdvanceAbility] " + ChatColor.RESET,Bukkit.getServer().getConsoleSender(),isDebuging);
		MessageManage = new MessagerManager(new File(this.getDataFolder() + File.separator + "Translation"));
		ABManager = new AbilityManagers();
		AbilityPlayersManager = new AbilityPlayersManagers(this.getDataFolder(),"Players-DataFile.yml");
		ABPlayerListener = new AbilityPlayerListener();
		this.getServer().getPluginManager().registerEvents(ABPlayerListener, this);
	}
	
	public static AdvanceAbilityMain getInstance(){
		return AdvanceAbilityMainInstance;
	}
	
	public static AbilityManagers getAbilitiesManager(){
		return ABManager;
	}
	
	public static AbilityPlayersManagers getPlayerManager(){
		return AbilityPlayersManager;
	}
	
	public static HashSet<Byte> getTransparentBlock(){
		return transparentBlock;
	}
	
	public static AbilityPlayerListener getPlayerListener(){
		return ABPlayerListener;
	}
	
	public static MessagerManager getMessageManager(){
		return MessageManage;
	}
	
	public CommandMap getCommandMap(){
		return ((CraftServer)this.getServer()).getCommandMap();
	}
	
//}}

}