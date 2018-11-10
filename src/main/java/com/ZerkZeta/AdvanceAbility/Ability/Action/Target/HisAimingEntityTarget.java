package com.ZerkZeta.AdvanceAbility.Ability.Action.Target;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.ZerkZeta.AdvanceAbility.AdvanceAbilityMain;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;
import com.google.common.collect.Lists;

public class HisAimingEntityTarget implements Target, EmtyAbleTarget{
	
	private String notFoundMessage;
	
	public HisAimingEntityTarget(String MessageIfNotFound){
		this.notFoundMessage  = MessageIfNotFound;
	}

	/**
	 * !!!!!!!!!!!!!!Don't forget to sendmessage if it null
	 * @param sender
	 * @return - Target OR null if out of range
	 */
	public LivingEntity getTarget(AbilityPlayer sender){
		
		Player bukkitPlayer = sender.getPlayer(); 
		@SuppressWarnings("deprecation")
		Block block = bukkitPlayer.getTargetBlock(AdvanceAbilityMain.getTransparentBlock(), 100);
		double lenght;
		if(block != null){
			lenght = bukkitPlayer.getLocation().distance(block.getLocation()) + 2;
		}
		else{
			lenght = 100;
		}
		List<Entity> nearbyEntity = Lists.reverse(bukkitPlayer.getNearbyEntities(lenght, lenght, lenght));
		HashMap<Double,LivingEntity> enMap = new HashMap<Double,LivingEntity>();
		for(Entity Temp_entity : nearbyEntity){
			if(!(Temp_entity instanceof LivingEntity)){
				continue;
			}
			LivingEntity entity = (LivingEntity)Temp_entity;
			Vector aim = entity.getLocation().toVector().subtract(bukkitPlayer.getEyeLocation().toVector());
			Vector sight = bukkitPlayer.getEyeLocation().getDirection().normalize();
			Vector compare = aim.normalize().subtract(sight);
			double CPX = Math.abs(compare.getX());
			double CPY = Math.abs(compare.getY());
			double CPZ = Math.abs(compare.getZ());
			double distanceofEntity = bukkitPlayer.getLocation().distance(entity.getLocation());
			//Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Distance: " + distanceofEntity);
			//Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "" + CPX + " " + CPY + " " + CPZ);
			if(distanceofEntity <= 3){
				//Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Distance <= 3");
				if(CPX <= 0.3D && CPY <= 0.39D && CPZ <= 0.3D){
					double ErrorPercent = CPX+CPY+CPZ+distanceofEntity;
					enMap.put(ErrorPercent, entity);
				}
				continue;
			}
			else if(distanceofEntity <= 4){
				//Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Distance <= 4");
				if(CPX <= 0.2D && CPY <= 0.2D && CPZ <= 0.2D){
					double ErrorPercent = CPX+CPY+CPZ+distanceofEntity;
					enMap.put(ErrorPercent, entity);
				}
				continue;
			}
			else if(distanceofEntity <= 5){
				//Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Distance <= 5");
				if(CPX <= 0.09D && CPY <= 0.19D && CPZ <= 0.09D){
					double ErrorPercent = CPX+CPY+CPZ+distanceofEntity;
					enMap.put(ErrorPercent, entity);
				}
				continue;
			}
			else if(distanceofEntity < 10){
				//Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Distance < 10");
				if(CPX <= 0.087D && CPY <= 0.15D && CPZ <= 0.087D){
					double ErrorPercent = CPX+CPY+CPZ+distanceofEntity;
					enMap.put(ErrorPercent, entity);
				}
				continue;
			}
			else if(distanceofEntity < 20){
				//Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Distance < 20");
				if(CPX <= 0.048D && CPY <= 0.06D && CPZ <= 0.048D){
					double ErrorPercent = CPX+CPY+CPZ+distanceofEntity;
					enMap.put(ErrorPercent, entity);
				}
				continue;
			}
			else if(distanceofEntity < 30){
				//Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Distance < 30");
				if(CPX <= 0.02D && CPY <= 0.044D && CPZ <= 0.02D){
					double ErrorPercent = CPX+CPY+CPZ+distanceofEntity;
					enMap.put(ErrorPercent, entity);
				}
				continue;
			}
			else if(distanceofEntity <= 102){
				if(CPX <= 0.03D && CPY <= 0.037D && CPZ <= 0.03D){
					double ErrorPercent = CPX+CPY+CPZ+distanceofEntity;
					enMap.put(ErrorPercent, entity);
				}
				continue;
			}
			continue;
		}
		if(!enMap.isEmpty()){
			double MinError = Collections.min(enMap.keySet());
			//Entity en = enMap.get(MinError);
			//Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "using Entity at " + en.getLocation().toVector().toString() + " type is " + en.getType().toString());
			//en.getLocation().getWorld().spawnEntity(en.getLocation(), EntityType.FIREWORK);
			return enMap.get(MinError);
		}
		return null;
	}
	
	@Override
	public String getType() {
		return "AimmingTarget";
	}

	@Override
	public String getEmtyMessage() {
		return this.notFoundMessage;
	}

	@Override
	public boolean willSendMessage() {
		return this.notFoundMessage.equalsIgnoreCase("") ? false : true;
	}

}
