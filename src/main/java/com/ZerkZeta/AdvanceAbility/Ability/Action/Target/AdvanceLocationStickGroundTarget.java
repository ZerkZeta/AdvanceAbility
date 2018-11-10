package com.ZerkZeta.AdvanceAbility.Ability.Action.Target;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public class AdvanceLocationStickGroundTarget implements Target{
	
	double Left_Right;
	double Back_Front;
	double Under_Orver;

	public AdvanceLocationStickGroundTarget(double Left_Right, double Back_Front, double Under_Orver) {
		this.Left_Right = Left_Right;
		this.Back_Front = (Back_Front*-1);
		this.Under_Orver = Under_Orver;
	}
	
	protected Location getTarget(AbilityPlayer sender){
		Player bukkitPlayer = sender.getPlayer();
		double Yaw = Math.toRadians(bukkitPlayer.getLocation().getYaw());
		double plusX = Back_Front * Math.sin(Yaw);
		double plusZ = Back_Front * (Math.cos(Yaw));
		Location loc = bukkitPlayer.getLocation();
		loc.setX(loc.getX() + plusX);
		loc.setZ(loc.getZ() - plusZ);
		
		Yaw = Math.toRadians(bukkitPlayer.getLocation().getYaw()-90);
		plusX = Left_Right * Math.sin(Yaw);
		plusZ = Left_Right * (Math.cos(Yaw));
		loc.setX(loc.getX() + plusX);
		loc.setZ(loc.getZ() - plusZ);
		
		if(loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()).getType().equals(Material.AIR)){
			for(int Y = loc.getBlockY(); Y > 0; Y--){
				if(!loc.getWorld().getBlockAt(loc.getBlockX(), Y, loc.getBlockZ()).getType().equals(Material.AIR)){
					loc.setY(Y+1);
					break;
				}
			}
		}
		else{
			for(int Y = loc.getBlockY(); Y > 0; Y++){
				if(loc.getWorld().getBlockAt(loc.getBlockX(), Y, loc.getBlockZ()).getType().equals(Material.AIR)){
					loc.setY(Y+1);
					break;
				}
			}
		}
		
		loc.setY(loc.getY() + Under_Orver);
				
		return loc;
	}

	@Override
	public String getType() {
		return "AdvanceLocationStickGround";
	}

}
