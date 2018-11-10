package com.ZerkZeta.AdvanceAbility.Ability.Action;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Sound;

import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.Target;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.TargetManager;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public class PlaySoundAction implements Action{

	private Long Delay;
	private Target target;
	private Sound sound;
	private float volume;
	private float pitch;
	
	
	public PlaySoundAction(String[] Data)throws Exception{
		if((Data.length != 6)){
			throw new Exception("play sound action need more data");
		};
		this.target = TargetManager.newTarget(Data[2]);
		try{
			this.sound = Sound.valueOf(Data[1]);
		}
		catch (Exception e){
			throw new Exception("invalid sound");
		}
		try{
			this.volume = Float.parseFloat(Data[3]);
		}
		catch (Exception e){
			throw new Exception("invalid volume");
		}
		try{
			this.pitch = Float.parseFloat(Data[4]);
		}
		catch (Exception e){
			throw new Exception("invalid pitch");
		}
		try{
			this.Delay = Long.valueOf(Data[5]);
		}
		catch (Exception e){
			throw new Exception("invalid dalay");
		}
	}
	
	public Sound getSound(){
		return sound;
	}
	
	public float getVolume(){
		return volume;
	}
	
	public float getPitch(){
		return pitch;
	}
	
	@Override
	public Long getDalayinOneTwentiethSecond() {
		return Delay;
	}

	@Override
	public String getType() {
		return "PlaySound";
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
			loc.getWorld().playSound(loc, sound, volume, pitch);
		};
	}
	
	@Override
	public Target getTarget() {
		return this.target;
	}

}
