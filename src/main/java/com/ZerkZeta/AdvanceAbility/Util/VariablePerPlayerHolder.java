package com.ZerkZeta.AdvanceAbility.Util;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public class VariablePerPlayerHolder {
	
	private ConfigurationSection VarHolder;
	
	public VariablePerPlayerHolder(ConfigurationSection CS)throws Exception{
		VarHolder = new YamlConfiguration();
		/*for(String Key : CS.getValues(false).keySet()){
			String Type = CS.getString(Key + ".Type");
			Object Value = CS.getString(Key + ".Value");
			if(Type == null || Value == null){
				throw new Exception("invalid data from " + Key);
			};
			if(!(Type.equalsIgnoreCase("String") || Type.equalsIgnoreCase("Boolean") || Type.equalsIgnoreCase("Number"))){
				throw new Exception("invalid data from " + Key + " : unsupport type");
			};
			try{
				switch(Type){
				case"String":
					break;//DO nothing
				case"Boolean":
					Value = Boolean.valueOf((String)Value);
					break;
				case"Number":
					Value = Double.parseDouble((String)Value);
					break;
				default:
					throw new Exception();
				}
			}
			catch(Exception e){
				throw new Exception("invalid value from " + Key);
			}
			VarHolder.set(Type.toUpperCase() + "." + Key, Value);
		}*/
	}
	
	public void set(AbilityPlayer Abplayer, String VarName, Object Value, String Type)throws Exception{
		if(Abplayer == null || Value == null || VarName == null){
			throw new Exception("invalid data - parameter can't be null");
		};
		String Type = "";
		if(VarHolder.contains(Abplayer.getUUID() + ".String")){
			Type = "String";
		};
		//TODO Continue here
	}
	
	/**
	 * get Number
	 * @param VarName - VariableName
	 * @return Value OR null if doesn't exist
	 */
	@SuppressWarnings("null")
	public double getNumber(String VarName,AbilityPlayer Abplayer){
		if(!VarHolder.contains(Abplayer.getUUID().toString())){
			return (Double) null;
		}
		return VarHolder.getDouble("NUMBER." + VarName, (Double)null);
	}
	
	/**
	 * get String
	 * @param VarName - VariableName
	 * @return Value OR null if doesn't exist
	 */
	public String getString(String VarName,AbilityPlayer Abplayer){
		if(!VarHolder.contains(Abplayer.getUUID().toString())){
			return null;
		}
		return VarHolder.getString("STRING." + VarName, null);
	}
	
	/**
	 * get Boolean
	 * @param VarName - VariableName
	 * @return Value OR null if doesn't exist
	 */
	@SuppressWarnings("null")
	public boolean getBoolean(String VarName,AbilityPlayer Abplayer){
		if(!VarHolder.contains(Abplayer.getUUID().toString())){
			return (Boolean) null;
		}
		return VarHolder.getBoolean("BOOLEAN." + VarName, (Boolean)null);
	}

}
