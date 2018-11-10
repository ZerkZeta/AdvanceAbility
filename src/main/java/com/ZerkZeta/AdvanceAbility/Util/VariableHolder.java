package com.ZerkZeta.AdvanceAbility.Util;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public class VariableHolder {
	
	private ConfigurationSection VarHolder;
	
	public VariableHolder(ConfigurationSection CS)throws Exception{
		VarHolder = new YamlConfiguration();
		for(String Key : CS.getValues(false).keySet()){
			String Type = CS.getString(Key + ".Type");
			Object Value = CS.getString(Key + ".Value");
			if(Type == null || Value == null){
				throw new Exception("Invalid data from " + Key);
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
				throw new Exception("invalid value from " + Key + ".Value");
			}
			VarHolder.set(Type.toUpperCase() + "." + Key, Value);
		}
	}
	
	/**
	 * get Number
	 * @param VarName - VariableName
	 * @return Value OR null if doesn't exist
	 */
	@SuppressWarnings("null")
	public double getNumber(String VarName){
		return VarHolder.getDouble("NUMBER." + VarName, (Double)null);
	}
	
	/**
	 * get String
	 * @param VarName - VariableName
	 * @return Value OR null if doesn't exist
	 */
	public String getString(String VarName){
		return VarHolder.getString("STRING." + VarName, null);
	}
	
	/**
	 * get Boolean
	 * @param VarName - VariableName
	 * @return Value OR null if doesn't exist
	 */
	@SuppressWarnings("null")
	public boolean getBoolean(String VarName){
		return VarHolder.getBoolean("BOOLEAN." + VarName, (Boolean)null);
	}

}
