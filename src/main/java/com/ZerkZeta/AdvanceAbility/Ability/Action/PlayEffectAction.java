package com.ZerkZeta.AdvanceAbility.Ability.Action;

import java.util.List;

import org.bukkit.Location;

import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.Target;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.TargetManager;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;
import com.ZerkZeta.AdvanceAbility.Util.PacketNMS;
import com.ZerkZeta.AdvanceAbility.Util.ParticleEffectProperties;
import com.ZerkZeta.AdvanceAbility.Util.ParticleEffects;

public class PlayEffectAction implements Action{	

	private Long Delay;
	private Target target;
	private ParticleEffects Effect;
	private ParticleEffectProperties proper;
	
	public PlayEffectAction(String[] Data)throws Exception{
		if(!(Data.length != 8)){
			throw new Exception("Play effect action need more data");
		};
		try{
			this.Effect = ParticleEffects.valueOf(Data[1]);
		}
		catch (Exception e){
			throw new Exception("Invalid Particle name");
		}
		
		this.target = TargetManager.newTarget(Data[2]);
		
		try{
			this.proper = new ParticleEffectProperties(Data[3],Data[4],Data[5],Data[6],Data[7]);
		}
		catch (Exception e){
			throw new Exception("Invalid Particle properties");
		}
		try{
			this.Delay = Long.valueOf(Data[8]);
		}
		catch (Exception e){
			throw new Exception("Invalid dalay");
		}
	}

	@Override
	public Long getDalayinOneTwentiethSecond() {
		return Delay;
	}

	@Override
	public String getType() {
		return "PlayEffect";
	}
	
	public ParticleEffects getEffect(){
		return Effect;
	}
	
	public ParticleEffectProperties getProperties(){
		return proper;
	}
	
	@Override
	public void Activate(AbilityPlayer player) {
		List<Location> LOCLIST = TargetManager.getTargetLocation(target, player);
		if(LOCLIST == null){
			throw new IllegalStateException("Intenal eror while trying to get target, please report.");
		};
		//getTargetLocation cant be EMTYLIST
		if(LOCLIST.isEmpty()){			
			throw new IllegalStateException("Intenal error target has nothing, please report.");
		};
		for(Location loc : LOCLIST){
			PacketNMS.sendParticlesPacket(loc, Effect, proper);
		};
	}
	
	@Override
	public Target getTarget() {
		return this.target;
	}

}
