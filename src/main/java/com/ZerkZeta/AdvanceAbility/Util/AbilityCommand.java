package com.ZerkZeta.AdvanceAbility.Util;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.ZerkZeta.AdvanceAbility.AdvanceAbilityMain;

public class AbilityCommand extends Command{
	 
    public AbilityCommand(String name, String description, String usageMessage, List<String> aliases)  {
        super(name,description,usageMessage,aliases);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel,String[] args) {
    	return AdvanceAbilityMain.getInstance().onCommand(sender, this, commandLabel,args);
    }
}
