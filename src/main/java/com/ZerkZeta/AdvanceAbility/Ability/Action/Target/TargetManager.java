package com.ZerkZeta.AdvanceAbility.Ability.Action.Target;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

import com.ZerkZeta.AdvanceAbility.AdvanceAbilityMain;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;

public class TargetManager {
	
	public static Target newTarget(String Data)throws Exception{
		if(Data.equalsIgnoreCase("Himself")){			
			return new HimselfTarget();
		};
		if(Data.equalsIgnoreCase("HisAimigLocation")){			
			return new HisAimingLocationTarget();
		};
		if(Data.contains("HisAimigEntity")){
			//HisAimigEntity[1]
			/*int firstAppearence = Data.indexOf("[");
			if(firstAppearence == -1){
				throw new Exception("require HisAimigEntity[] if you don't want to send any message to user");
			}*/
			String Message = Data.split("\\[")[1].replace("]", "");
			AdvanceAbilityMain.getlogger().Debug("Creating HisAimigEntity(" + Message +")");
			return new HisAimingEntityTarget(Message);
			//return new HisAimingEntityTarget();
		};
		if(Data.contains("HisNearByEntity")){
			//HisNearByEntity(100,100,100)[too far]
			int firstAppearence = Data.indexOf("[");
			if(firstAppearence == -1){
				throw new Exception("require HisNearByEntity[] if you don't want to send any message to user");
			}
			String Message = Data.split("\\[")[1].replace("]", "");
			Data = Data.substring(0, firstAppearence-1);
			String[] args = Data.split("\\(")[1].split(",");
			int x;
			int y;
			int z;
			try{
				x = Integer.parseInt(args[0]);
				y = Integer.parseInt(args[1]);
				z = Integer.parseInt(args[2]);
			} catch (Exception e){
				throw new Exception("HisNearByEntity size was invalid.");
			}
			//AdvanceAbilityMain.getlogger().Debug("Creating HisNearByLivingEntityTarget(" + x + ", " + y + ", " + z + ", " + Message + ")");
			return new HisNearByLivingEntityTarget(x, y, z,Message);
		};
		if(Data.contains("AdvanceLocation+")){	
			//AdvanceLocation+(0,5,1)
			Data = Data.replace("(", "").replace(")", "");
			String[] args = Data.split("\\+")[1].split(",");
			double Left_Right;
			double Back_Front;
			double Under_Over;
			try{
				Left_Right = Double.parseDouble(args[0]);
				Back_Front = Double.parseDouble(args[1]);
				Under_Over = Double.parseDouble(args[2]);
			} catch (Exception e){
				throw new Exception("AdvanceLocation+ size was invalid.");
			}	
			return new AdvanceLocationTarget(Left_Right, Back_Front, Under_Over);
		};
		if(Data.contains("AdvanceLocationStickGround+")){
			//AdvanceLocationStickGround+(0,5,0)
			Data = Data.replace("(", "").replace(")", "");
			String[] args = Data.split("\\+")[1].split(",");
			double Left_Right;
			double Back_Front;
			double Under_Over;
			try{
				Left_Right = Double.parseDouble(args[0]);
				Back_Front = Double.parseDouble(args[1]);
				Under_Over = Double.parseDouble(args[2]);
			} catch (Exception e){
				throw new Exception("AdvanceLocationStickGround+ size was invalid.");
			}	
			return new AdvanceLocationStickGroundTarget(Left_Right, Back_Front, Under_Over);
		};
		throw new IllegalStateException("invalid Target(" + Data + ")");
	}
	
	/**
	 * Don't forget to Check if list.isEmty() if out of bound OR if null if invalidData
	 * @param target
	 * @param sender
	 * @return (EMTYLIST if OUT OF BOUND) OR (Null if invalid data)
	 */
	public static List<LivingEntity> getTargetLivingEntity(Target target, AbilityPlayer sender){
		List<LivingEntity> ENLIST = new ArrayList<LivingEntity>();
		if(target instanceof HimselfTarget){
			ENLIST.add(((HimselfTarget) target).getTarget(sender));
			return ENLIST;
		};
		if(target instanceof HisAimingEntityTarget){
			LivingEntity TempEn = ((HisAimingEntityTarget) target).getTarget(sender);
			if(TempEn != null){
				ENLIST.add(TempEn);
			}
			return ENLIST;
		};
		if(target instanceof HisNearByLivingEntityTarget){
			ENLIST = ((HisNearByLivingEntityTarget) target).getTarget(sender);
			return ENLIST;
		};
		return null;
	}
	
	/**
	 * Don't forget to Check if list.isEmty() if out of bound OR if null if invalidData
	 * @param target
	 * @param sender
	 * @return (EMTYLIST if OUT OF BOUND) OR (Null if invalid data)
	 */
	public static List<Location> getTargetLocation(Target target, AbilityPlayer sender){
		List<Location> LOCLIST = new ArrayList<Location>();
		if(target instanceof HimselfTarget){
			LOCLIST.add(((HimselfTarget) target).getTarget(sender).getLocation());
			return LOCLIST;
		};
		if(target instanceof HisAimingLocationTarget){
			Location temloc = ((HisAimingLocationTarget) target).getTarget(sender);
			if(temloc != null){
				LOCLIST.add(temloc);
				return LOCLIST;
			};
			return LOCLIST;
		};
		if(target instanceof HisAimingEntityTarget){
			LivingEntity tempEN = ((HisAimingEntityTarget) target).getTarget(sender);
			if(tempEN != null){
				LOCLIST.add(tempEN.getLocation());
				return LOCLIST;
			};
			return LOCLIST;
		};
		if(target instanceof HisNearByLivingEntityTarget){
			List<LivingEntity> ENLIST = ((HisNearByLivingEntityTarget) target).getTarget(sender);
			if(ENLIST.isEmpty()){
				return LOCLIST; // Return emtylist
			};
			for(LivingEntity EN : ENLIST){
				LOCLIST.add(EN.getLocation());
			}
			return LOCLIST;
		};
		if(target instanceof AdvanceLocationTarget){
			LOCLIST.add(((AdvanceLocationTarget) target).getTarget(sender));
			return LOCLIST;
		};
		if(target instanceof AdvanceLocationStickGroundTarget){
			LOCLIST.add(((AdvanceLocationStickGroundTarget) target).getTarget(sender));
			return LOCLIST;
		};
		return null;
	}

}
