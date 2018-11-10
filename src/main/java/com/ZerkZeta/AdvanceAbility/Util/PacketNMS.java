package com.ZerkZeta.AdvanceAbility.Util;

import java.util.List;

import net.minecraft.server.v1_7_R3.DedicatedPlayerList;
import net.minecraft.server.v1_7_R3.PacketPlayOutWorldParticles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R3.CraftServer;
import org.bukkit.craftbukkit.v1_7_R3.CraftWorld;
import org.bukkit.entity.Entity;

public class PacketNMS {
	
	public static void sendParticlesPacket(Location loc, ParticleEffects ParticleEffect, ParticleEffectProperties properties){
		
		float xOffset = properties.getOffsetX();
		float yOffset = properties.getOffsetY();
		float zOffset = properties.getOffsetZ();
		float effectSpeed = properties.getSpeed();
		int amount = properties.getAmount();
		
		DedicatedPlayerList Plist = ((CraftServer)Bukkit.getServer()).getHandle();
    	PacketPlayOutWorldParticles packet  = new PacketPlayOutWorldParticles(ParticleEffect.getName(),(float)loc.getX(),(float)loc.getY(),(float)loc.getZ(),xOffset,yOffset,zOffset,effectSpeed,amount);
    	Plist.sendPacketNearby(loc.getX(), loc.getY(), loc.getZ(), 64, ((CraftWorld) loc.getWorld()).getHandle().dimension, packet);
	}
	
	@SuppressWarnings("unchecked")
	public static void sendParticlesPacket(Object Target, ParticleEffects ParticleEffect, ParticleEffectProperties properties)
	{
		DedicatedPlayerList Plist = ((CraftServer)Bukkit.getServer()).getHandle();
		Location loc;
		
		float xOffset = properties.getOffsetX();
		float yOffset = properties.getOffsetY();
		float zOffset = properties.getOffsetZ();
		float effectSpeed = properties.getSpeed();
		int amount = properties.getAmount();
		
		if(Target instanceof List<?>){
			for(Entity en : (List<Entity>)Target){
				loc = en.getLocation();
				PacketPlayOutWorldParticles packet  = new PacketPlayOutWorldParticles(ParticleEffect.getName(),(float)loc.getX(),(float)loc.getY(),(float)loc.getZ(),xOffset,yOffset,zOffset,effectSpeed,amount);
		    	Plist.sendPacketNearby(loc.getX(), loc.getY(), loc.getZ(), 64, ((CraftWorld) loc.getWorld()).getHandle().dimension, packet);
			}
			return;
		}
		else if(Target instanceof Entity){
			loc = ((Entity)Target).getLocation();
		}
		else{
			loc = (Location)Target;
		}		
		
		PacketPlayOutWorldParticles packet  = new PacketPlayOutWorldParticles(ParticleEffect.getName(),(float)loc.getX(),(float)loc.getY(),(float)loc.getZ(),xOffset,yOffset,zOffset,effectSpeed,amount);
    	Plist.sendPacketNearby(loc.getX(), loc.getY(), loc.getZ(), 64, ((CraftWorld) loc.getWorld()).getHandle().dimension, packet);
	}

}
