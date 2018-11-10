package com.ZerkZeta.AdvanceAbility.Ability.Action.Target;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public class HisNearByLivingEntityTarget implements Target, EmtyAbleTarget{
	
	private int x;
	private int y;
	private int z;
	private String NotFoundMessage;
	
	public HisNearByLivingEntityTarget(int x, int y, int z,String NotFoundMessage){
		this.x = x;
		this.y = y;
		this.z = z;
		this.NotFoundMessage = NotFoundMessage;
	}
	
	protected List<LivingEntity> getTarget(AbilityPlayer sender){
		List<LivingEntity> ENLIST = new ArrayList<LivingEntity>();
		for(Entity en : sender.getPlayer().getNearbyEntities(x, y, z)){
			if(en instanceof LivingEntity){
				ENLIST.add((LivingEntity) en);
			}
		}
		return ENLIST;
	}

	@Override
	public String getType() {
		return "NearByLivingEntity";
	}

	@Override
	public String getEmtyMessage() {
		return this.NotFoundMessage;
	}

	@Override
	public boolean willSendMessage() {
		return this.NotFoundMessage.equalsIgnoreCase("") ? false : true;
	}

}
