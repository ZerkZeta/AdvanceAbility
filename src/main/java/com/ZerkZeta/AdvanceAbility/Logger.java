package com.ZerkZeta.AdvanceAbility;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Logger {

	ConsoleCommandSender console;
	private boolean isdebuging = false;
	private String prefix;
	
	public Logger(String prefixs,ConsoleCommandSender consoles,boolean isDebug){
		prefix = prefixs;
		isdebuging = isDebug;
		console = consoles;
	}
	
	public void info(String Msg){
		console.sendMessage(prefix + Msg); 
	}
	
	public void warn(String Msg){
		console.sendMessage(ChatColor.YELLOW + "[warning!] " + ChatColor.GOLD + " [Warning!]" + Msg);
	}
	
	public void Err(String Msg){
		console.sendMessage(ChatColor.YELLOW + "[Error!] " + ChatColor.RED + Msg);
	}
	
	public void Err(String Msg,Player sender){
		console.sendMessage(ChatColor.YELLOW + "[Error! From " + sender.getName() + "] " + ChatColor.RED + Msg);
		sender.sendMessage(ChatColor.RED + Msg);
	}
	
	public void Debug(String Msg){
		if(isdebuging){
			console.sendMessage(ChatColor.AQUA + "[Debug] " + Msg);
		}
		else{
			return;
		}
	}
	
}
