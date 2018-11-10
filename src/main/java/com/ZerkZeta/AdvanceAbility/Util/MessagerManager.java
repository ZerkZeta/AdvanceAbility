package com.ZerkZeta.AdvanceAbility.Util;

import java.io.File;
import java.io.InputStreamReader;

import org.bukkit.configuration.file.YamlConfiguration;

import com.ZerkZeta.AdvanceAbility.AdvanceAbilityMain;
import com.google.common.base.Charsets;

public class MessagerManager {
	
	public YamlConfiguration LangFile;
	
	public MessagerManager(File file){
		String Lang = AdvanceAbilityMain.getInstance().getConfig().getString("Language", "EN").toUpperCase();
		Reload(file/*(new File(AdvanceAbilityMain.getInstance().getDataFolder() + File.separator + "Languages"))*/,Lang);
	}
	
	public void Reload(File files,String lang){
		//check file Exist
		if(!files.exists()){
			files.mkdirs();
		}
		File file = new File(files.getPath() + File.separator + lang + ".yml");
		if(!file.exists()){
			AdvanceAbilityMain.getlogger().Err("Language " + lang + " not found.");
			AdvanceAbilityMain.getlogger().info("Switching to EN language");
			lang = "EN";
			file = new File(files.getPath() + File.separator + lang + ".yml");
		};		
		if(!file.exists() && lang.equals("EN")){
			//EAM.getClass().getClassLoader().getResource("/Resources/EN.yml");
			//InputStream in = EAM.getClass().getClassLoader().getResourceAsStream("/Resources/EN.yml");
			try {
				YamlConfiguration.loadConfiguration(new InputStreamReader(AdvanceAbilityMain.getInstance().getClass().getClassLoader().getResourceAsStream("EN.yml"), Charsets.UTF_8)).save(file);
				AdvanceAbilityMain.getlogger().info("Created EN language file");
			} catch (Exception e) {
				AdvanceAbilityMain.getlogger().Err("Can't save " + file.getPath() + "\nError: ");
				e.printStackTrace();
			};
		};
		LangFile = YamlConfiguration.loadConfiguration(file);
	}
	
	public String getMessage(String Key){
		Key = Key.replace(" ", "_");
		String Message = LangFile.getString("Message." + Key, "Error can't get message at 'Message." + Key + "'");
		Message = Message.replace("&&", "\u00A7");
		return Message;
	}

}
