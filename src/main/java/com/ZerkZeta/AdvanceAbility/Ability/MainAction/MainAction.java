package com.ZerkZeta.AdvanceAbility.Ability.MainAction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;

import com.ZerkZeta.AdvanceAbility.Ability.Action.*;

public class MainAction {
	
	private Long CoolDownTime;
	private String CoolDownMessage;
	//private List<Action> Actions;
	private HashMap<Long,List<Action>> DelayAndAction;
	private HashMap<Long,List<Action>> DelayAndMovingLeftAction;
	private HashMap<Long,List<Action>> DelayAndMovingRightAction;
	private HashMap<Long,List<Action>> DelayAndMovingForwardAction;
	private HashMap<Long,List<Action>> DelayAndMovingBackAction;
	private HashMap<Long,List<Action>> DelayAndStandingAction;	
	
	public MainAction(ConfigurationSection CS)throws Exception{
		if(CS == null){
			throw new Exception("Internal error please report");
		}
		this.CoolDownTime = CS.getLong("Cooldown.Duration",0);
		this.CoolDownMessage = CS.getString("Cooldown.Message","");
		List<String> tempList = CS.getStringList("Action");
		if(!CS.contains("Action")){
			if(!(CS.contains("MovingLeft-Action") && CS.contains("MovingRight-Action")  && CS.contains("MovingFront-Action")  
					&& CS.contains("MovingBack-Action") && CS.contains("Standing-Action"))){
				throw new Exception("request more data: 'actions is emty'");
			}
			
			//{{Moving Left
			try{
				this.DelayAndMovingLeftAction = new HashMap<Long,List<Action>>();
				tempList = CS.getStringList("MovingLeft-Action");
				for(String TempData : tempList){
					String Data[] = TempData.split("\\|").clone();
					Action AC = ActionManagers.newAction(Data);
					if(this.DelayAndMovingLeftAction.containsKey(AC.getDalayinOneTwentiethSecond())){
						this.DelayAndMovingLeftAction.get(AC.getDalayinOneTwentiethSecond()).add(AC);
					}
					else{
						List<Action> AClist = new ArrayList<Action>();
						AClist.add(AC);
						this.DelayAndMovingLeftAction.put(AC.getDalayinOneTwentiethSecond(), AClist);
					}
				}
			}
			catch(Exception e){
				this.DelayAndMovingLeftAction = null;
				throw new Exception(e.getMessage().toLowerCase() + " in moving left action\n");
			}
			//}}
			
			//{{Moving Right
			try{
				this.DelayAndMovingRightAction = new HashMap<Long,List<Action>>();
				tempList = CS.getStringList("MovingRight-Action");
				for(String TempData : tempList){
					String Data[] = TempData.split("\\|").clone();
					Action AC = ActionManagers.newAction(Data);
					if(this.DelayAndMovingRightAction.containsKey(AC.getDalayinOneTwentiethSecond())){
						this.DelayAndMovingRightAction.get(AC.getDalayinOneTwentiethSecond()).add(AC);
					}
					else{
						List<Action> AClist = new ArrayList<Action>();
						AClist.add(AC);
						this.DelayAndMovingRightAction.put(AC.getDalayinOneTwentiethSecond(), AClist);
					}
				}
			}
			catch(Exception e){
				this.DelayAndMovingRightAction = null;
				throw new Exception(e.getMessage().toLowerCase() + " in moving right action\n");
			}
			//}}
			
			//{{Moving Back
			try{
				this.DelayAndMovingBackAction = new HashMap<Long,List<Action>>();
				tempList = CS.getStringList("MovingBack-Action");
				for(String TempData : tempList){
					String Data[] = TempData.split("\\|").clone();
					Action AC = ActionManagers.newAction(Data);
					if(this.DelayAndMovingBackAction.containsKey(AC.getDalayinOneTwentiethSecond())){
						this.DelayAndMovingBackAction.get(AC.getDalayinOneTwentiethSecond()).add(AC);
					}
					else{
						List<Action> AClist = new ArrayList<Action>();
						AClist.add(AC);
						this.DelayAndMovingBackAction.put(AC.getDalayinOneTwentiethSecond(), AClist);
					}
				}
			}
			catch(Exception e){
				this.DelayAndMovingBackAction = null;
				throw new Exception(e.getMessage().toLowerCase() + " in moving back action\n");
			}
			//}}
			
			//{{Moving Forward
			try{
				this.DelayAndMovingForwardAction = new HashMap<Long,List<Action>>();
				tempList = CS.getStringList("MovingFront-Action");
				for(String TempData : tempList){
					String Data[] = TempData.split("\\|").clone();
					Action AC = ActionManagers.newAction(Data);
					if(this.DelayAndMovingForwardAction.containsKey(AC.getDalayinOneTwentiethSecond())){
						this.DelayAndMovingForwardAction.get(AC.getDalayinOneTwentiethSecond()).add(AC);
					}
					else{
						List<Action> AClist = new ArrayList<Action>();
						AClist.add(AC);
						this.DelayAndMovingForwardAction.put(AC.getDalayinOneTwentiethSecond(), AClist);
					}
				}
			}
			catch(Exception e){
				this.DelayAndMovingForwardAction = null;
				throw new Exception(e.getMessage().toLowerCase() + " in moving front action\n");
			}
			//}}
			
			//{{Standing
			try{
				this.DelayAndStandingAction = new HashMap<Long,List<Action>>();
				tempList = CS.getStringList("Standing-Action");
				for(String TempData : tempList){
					String Data[] = TempData.split("\\|").clone();
					Action AC = ActionManagers.newAction(Data);
					if(this.DelayAndStandingAction.containsKey(AC.getDalayinOneTwentiethSecond())){
						this.DelayAndStandingAction.get(AC.getDalayinOneTwentiethSecond()).add(AC);
					}
					else{
						List<Action> AClist = new ArrayList<Action>();
						AClist.add(AC);
						this.DelayAndStandingAction.put(AC.getDalayinOneTwentiethSecond(), AClist);
					}
				}
			}
			catch(Exception e){
				this.DelayAndStandingAction = null;
				throw new Exception(e.getMessage().toLowerCase() + " in standing action\n");
			}
			//}}
			
			return;
		}
		else{
			try{
				this.DelayAndAction = new HashMap<Long,List<Action>>();
				for(String TempData : tempList){
					String Data[] = TempData.split("\\|").clone();
					
					Action AC = ActionManagers.newAction(Data);
					
					if(DelayAndAction.containsKey(AC.getDalayinOneTwentiethSecond())){
						DelayAndAction.get(AC.getDalayinOneTwentiethSecond()).add(AC);
					}
					else{
						List<Action> AClist = new ArrayList<Action>();
						AClist.add(AC);
						DelayAndAction.put(AC.getDalayinOneTwentiethSecond(), AClist);
					}
				}
			}
			catch(Exception e){
				this.DelayAndAction = null;
				throw new Exception(e.getMessage().toLowerCase() + " \n");
			}
		}
	}
	
	/**
	 * Don't forget to use method "isMovingAction()" to check if rather using MovingAction
	 * @return Map you've requested OR null if non action in this
	 */
	public HashMap<Long,List<Action>> getDelayAndActionMap(){
		return this.DelayAndAction;
	}
	
	public boolean isMovingAction(){
		return (((this.DelayAndMovingLeftAction != null) && (this.DelayAndMovingRightAction != null) && (this.DelayAndMovingBackAction != null) 
				&& (this.DelayAndMovingForwardAction != null)  && (this.DelayAndStandingAction != null)) ? true : false);
	}
	
	public HashMap<Long,List<Action>> getDelayAndMovingLeftActionMap(){
		return this.DelayAndMovingLeftAction;
	}
	
	public HashMap<Long,List<Action>> getDelayAndMovingRightActionMap(){
		return this.DelayAndMovingRightAction;
	}
	
	public HashMap<Long,List<Action>> getDelayAndMovingForwardActionMap(){
		return this.DelayAndMovingForwardAction;
	}
	
	public HashMap<Long,List<Action>> getDelayAndMovingBackwardActionMap(){
		return this.DelayAndMovingBackAction;
	}
	
	public HashMap<Long,List<Action>> getDelayAndStandingActionMap(){
		return this.DelayAndStandingAction;
	}

	/**
	 * get cool down time in millisec
	 * @return Long cool down time
	 */
	public Long getCoolDownTime() {
		return this.CoolDownTime;
	}
	
	public String getCoolDownMessage(){
		return this.CoolDownMessage;
	}
	
	public String getType(){
		return this.getClass().getSimpleName();
	}

}
