package com.ZerkZeta.AdvanceAbility.Util;

import org.bukkit.Sound;
import org.bukkit.potion.PotionEffectType;

public class Validating {
	
	/**
	 * Validate
	 * @param Type - type
	 * @param obj - Object to check
	 * @return "FINE" if everything ok.
	 */
	/*public String ValidTypeAndInstance(String Type, Object obj){
		
	}*/
	
	/**
	 * Check if Ability can use the given target.
	 * @param AbilityName
	 * @param Target
	 * @return "OK" OR ErrorMessage
	 */
	public static String ValidAbilityAndTarget(String[] Data){
		
		if(Data[0].equalsIgnoreCase("AddPotionEffect")){
			if(Data.length != 7){
				return "AddPotionEffect need more data";
			}
			if(PotionEffectType.getByName(Data[2]) == null){
				return "AddPotionEffect has invalid potion name";
			}
			if(Data[5].equalsIgnoreCase("HisAimigLocation") || Data[5].equalsIgnoreCase("HisAimigLocation") || Data[5].contains("AdvanceLocation")){
				return "AddPotionEffect target must be entity";
			}
			return "OK";
		}
		else if(Data[0].equalsIgnoreCase("PlayEffect")){
			if(Data.length != 9){
				return "PlayEffect need more data";
			}
			try{
				ParticleEffects.valueOf(Data[1]);
			}
			catch (Exception e){
				return "PlayEffect has invalid effect name";
			}
			return "OK";
		}
		else if(Data[0].equalsIgnoreCase("Teleport")){
			if(Data.length != 3){
				return "Teleport need more data";
			}
			return "OK";
		}
		else if(Data[0].equalsIgnoreCase("PlaySound")){
			if(Data.length != 6){
				return "PlaySound need more data";
			}
			try{
				Sound.valueOf(Data[1]);
			}
			catch (Exception e){
				return "PlaySound has invalid sound name";
			}
			return "OK";
		}
		else if(Data[0].equalsIgnoreCase("SetVelocity")){
			if(Data.length != 5){
				return "SetVelocity need more data";
			}
			return "OK";
		}
		else{
			return "Invalid action";
		}
		
	}

}
