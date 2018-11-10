package com.ZerkZeta.AdvanceAbility.Ability.Action;
import java.util.List;

import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.Target;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Target.TargetManager;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public class AddPotionEffectAction extends BaseAction implements Action {
	
	private PotionEffect potioneffect;
	private Long Delay;
	private Target target;
	
	public AddPotionEffectAction(String[] Data)throws Exception{
		PotionEffectType efftype;
		int Level;
		int Duration;
		boolean isAmbient;
		
		if(Data.length != 7){
			throw new Exception("Potion effect action need more data");
		};
		efftype = PotionEffectType.getByName(Data[1]);
		if(efftype == null){
			throw new Exception("Potion effect " + Data[1] + " was invalid");
		}
		//{{Level
		try{
			Level = Integer.parseInt(Data[2]);
		}
		catch (Exception e){
			throw new Exception("Level must be number");
		}
		//}}
		//{{Duration
		if(!Data[3].equalsIgnoreCase("MAX")){
			try{
				Duration = Integer.parseInt(Data[3]);
			}
			catch (Exception e){
				throw new Exception("Duration must be number or 'MAX'");
			}
		}
		else{
			throw new Exception("Duration must be number or 'MAX'");
		};
		Duration = Integer.MAX_VALUE;
		//}}
		//{{isAmbient
		isAmbient = Boolean.parseBoolean(Data[4]);
		//}}
		//{{Target
		this.target = TargetManager.newTarget(Data[5]);//new TargetOfAction(Data[5]);
		//}}
		//{{Delay
		try{
			Delay = Long.valueOf(Data[6]);
		}
		catch(Exception e){
			throw new Exception("Delay must be number");
		}
		//}}
		
		potioneffect = new PotionEffect(efftype,Duration,Level,isAmbient);
	}
	
	@Override
	public String getType() {
		return "AddPotionEffect";
	}
	
	public PotionEffect getPotionEffect(){
		return potioneffect;
	}
	
	@Override
	public Long getDalayinOneTwentiethSecond() {
		return Delay;
	}

	@Override
	public void Activate(AbilityPlayer player) {
		List<LivingEntity> ENLIST = this.SendMessageAndgetTargetEntity(player);
		for(LivingEntity EN : ENLIST){
			EN.addPotionEffect(potioneffect);
		};
	}

	@Override
	public Target getTarget() {
		return this.target;
	}

}
