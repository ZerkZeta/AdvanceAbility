package com.ZerkZeta.AdvanceAbility.Ability.Action;

import java.util.Arrays;

public class ActionManagers {
		
	public static Action newAction(String[] Data)throws Exception{
		if(Data[0].equalsIgnoreCase("AddPotionEffect")){
			return new AddPotionEffectAction(Data);
		}
		else if(Data[0].equalsIgnoreCase("PlayEffect")){
			return new PlayEffectAction(Data);
		}
		else if(Data[0].equalsIgnoreCase("Teleport")){
			return new TeleportAction(Data);
		}
		else if(Data[0].equalsIgnoreCase("PlaySound")){
			return new PlaySoundAction(Data);
		}
		else if(Data[0].equalsIgnoreCase("SetVelocity")){
			return new SetVelocityAction(Data);
		}
		else if(Data[0].equalsIgnoreCase("DamageEntity")){
			return new DamageEntityAction(Data);
		}
		else if(Data[0].equalsIgnoreCase("SetDirection")){
			return new SetDirectionAction(Data);
		}
		else if(Data[0].equalsIgnoreCase("SetDirection")){
			return new GiveItemActoin(Data);
		}
		else if(Data[0].equalsIgnoreCase("SendMessage")){
			return new SendMessageAction(Data);
		}
		else if(Data[0].equalsIgnoreCase("PerformCommand")){
			return new PerformCommandAction(Data);
		}
		else{
			throw new Exception("invalid Action(" + Arrays.toString(Data) + ")");
		}
	}

}
