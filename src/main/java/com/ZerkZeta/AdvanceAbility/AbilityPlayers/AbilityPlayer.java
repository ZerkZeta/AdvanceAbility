package com.ZerkZeta.AdvanceAbility.AbilityPlayers;

import com.ZerkZeta.AdvanceAbility.Ability.Abilities;
import com.ZerkZeta.AdvanceAbility.Ability.Action.Action;
import com.ZerkZeta.AdvanceAbility.Ability.MainAction.*;
import com.ZerkZeta.AdvanceAbility.AdvanceAbilityMain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CancellationException;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class AbilityPlayer
{
	private UUID PlayerUUID;
	private Abilities ActiveAbility;
	private Long ShiftPressCooldownStartTime;
	private Long ShiftReleaseCooldownStartTime;
	private Long Drop_ItemCooldownStartTime;
	private BukkitRunnable RepeatedlyTask;
	private Movement MoveDirection;
	private enum Movement{
		LEFT, RIGHT, BACKWARD, FORWARD, STANDING;
	}
	
	protected AbilityPlayer(OfflinePlayer player, Abilities Ability){
		this.PlayerUUID = player.getUniqueId();
		this.setActiveAbility(Ability);
	}
	
	protected AbilityPlayer(OfflinePlayer player, Abilities Ability, Long ShiftPressCooldownStartTime, Long ShiftReleaseCooldownStartTime, Long Drop_ItemCooldownStartTime){
		this.PlayerUUID = player.getUniqueId();
		this.setActiveAbility(Ability);//this.ActiveAbility = Ability;
		this.ShiftPressCooldownStartTime = ShiftPressCooldownStartTime;
		this.ShiftReleaseCooldownStartTime = ShiftReleaseCooldownStartTime;
		this.Drop_ItemCooldownStartTime = Drop_ItemCooldownStartTime;
	}
	
	protected static AbilityPlayer Load(ConfigurationSection section, String playerUUIDstr)throws Exception{
		
		AdvanceAbilityMain.getlogger().Debug("Loading for " + playerUUIDstr);
		UUID playerUUID = UUID.fromString(playerUUIDstr);
		OfflinePlayer BukkitPlayer = Bukkit.getOfflinePlayer(playerUUID);
		if (BukkitPlayer == null) {
			throw new IllegalArgumentException("Player which has " + playerUUIDstr + " was not found");
		}
		if (BukkitPlayer.isOnline())
		{
			String ABName = section.getString("Ability", "not set");
			Long ShiftPressCooldownStartTime = (section.getLong("ShiftPressCoolDownTime", Long.parseLong("0")));
			Long ShiftReleaseCooldownStartTime = (section.getLong("ShiftReleseCoolDownTime", Long.parseLong("0")));
			Long Drop_ItemCooldownStartTime = (section.getLong("DropItemCoolDownTime", Long.parseLong("0")));
			if (ABName.equalsIgnoreCase("not set"))
			{
				AdvanceAbilityMain.getlogger().Debug("Player is loaded with this ability " + ABName);
				return InitializefromConfigurationsectionAndLoadPlayer(section, playerUUIDstr, BukkitPlayer, null, ShiftPressCooldownStartTime, ShiftReleaseCooldownStartTime, Drop_ItemCooldownStartTime);
			}
			Abilities ability = AdvanceAbilityMain.getAbilitiesManager().getAbility(ABName);
			if (ability == null) {
				throw new Exception("Invalid ability name for " + BukkitPlayer.getName() + " (" + playerUUIDstr + ")");
			}
			AdvanceAbilityMain.getlogger().Debug("Player is loaded with this ability " + ABName);
			return InitializefromConfigurationsectionAndLoadPlayer(section, playerUUIDstr, BukkitPlayer, ability, ShiftPressCooldownStartTime, ShiftReleaseCooldownStartTime, Drop_ItemCooldownStartTime);
		}
		AdvanceAbilityMain.getlogger().Debug("Player is not online");
		throw new CancellationException();
	}
	
	private static AbilityPlayer InitializefromConfigurationsectionAndLoadPlayer(ConfigurationSection section, String playerUUIDstr, OfflinePlayer Offplayer, Abilities Ability, Long ShiftPressCooldownStartTime, Long ShiftReleaseCooldownStartTime, Long Drop_ItemCooldownStartTime){
		AbilityPlayer player = new AbilityPlayer(Offplayer, Ability, ShiftPressCooldownStartTime, ShiftReleaseCooldownStartTime, Drop_ItemCooldownStartTime);
		if(player.getActiveAbility().isHasCommandAction()){
			for(CommandAction CMAC: player.getActiveAbility().getCommandActionMap().values()){
				if(section.contains("Player-Data." + playerUUIDstr + ".CoodownTime" + ".Command." + CMAC.getCommandActionName())){
					CMAC.setCoolDownStartTime(player,section.getLong("Player-Data." + playerUUIDstr + ".CoodownTime" + ".Command." + CMAC.getCommandActionName()));
				}
			}
		}
		return player;
	}
	
	public UUID getUUID(){
		return this.PlayerUUID;
	}
	
	public boolean isAbilityActivate(){
		return getActiveAbility() != null;
	}
	
	public YamlConfiguration saveToYml(YamlConfiguration yml){
		String AbilityName;
		if (this.ActiveAbility != null) {
			AbilityName = this.ActiveAbility.getName();
		} else {
			AbilityName = "not set";
		}
		//Clean up first
		yml.set("Player-Data." + this.PlayerUUID , null);
		yml.set("Player-Data." + this.PlayerUUID + ".Name", getPlayer().getName());
		yml.set("Player-Data." + this.PlayerUUID + ".CoodownTime" + ".Ability", AbilityName);
		yml.set("Player-Data." + this.PlayerUUID + ".CoodownTime" + ".ShiftPressCoolDownTime", this.ShiftPressCooldownStartTime);
		yml.set("Player-Data." + this.PlayerUUID + ".CoodownTime" + ".ShiftReleseCoolDownTime", this.ShiftReleaseCooldownStartTime);
		yml.set("Player-Data." + this.PlayerUUID + ".CoodownTime" + ".DropItemCoolDownTime", this.Drop_ItemCooldownStartTime);
		
		for(List<String> commandlist : this.getActiveAbility().getCommandActionMap().keySet()){
			for(String commandName : commandlist){
				Long CoolDownStartTime = this.getActiveAbility().getCommandActionMap().get(commandlist).getCoolDownStartTime(this);
				yml.set("Player-Data." + this.PlayerUUID + ".CoodownTime" + ".Command." + commandName, CoolDownStartTime);
			}
		}
		
		return yml;
	}
	
	public Player getPlayer(){
		return Bukkit.getPlayer(this.PlayerUUID);
	}
	
	public Abilities getActiveAbility(){
		return this.ActiveAbility;
	}
	
	public void setActiveAbility(Abilities ab){
		this.DeactivateRepeatedlyAction();
		this.ActiveAbility = ab;
		if(this.ActiveAbility != null){
			if(this.ActiveAbility.isHasRepeatedlyAction()){
				this.ActivateRepeatedlyAction();
			};
			if(this.ActiveAbility.isUseMovingAction()){
				this.ActivateMovementDetectionAction();
			}
			else{
				this.DeactivateMovementDetectionAction();
			}
		}
		else{
			this.DeactivateMovementDetectionAction();
		}
	}
	
	//{{PressShiftAction
	public boolean isCoolingDownShiftPressAction(){
		if (this.ShiftPressCooldownStartTime == null) {
			return false;
		}
		return getPressShiftCoolDownTimeLeft() > 1L;
	}
	
	public Long getPressShiftCoolDownTimeLeft(){
		if (this.ShiftPressCooldownStartTime == null) {
			return (Long.parseLong("0"));
		}
		Long CoolDowntime = this.ActiveAbility.getPressShiftAction().getCoolDownTime();
		return (CoolDowntime - (System.currentTimeMillis() - this.ShiftPressCooldownStartTime));
	}
	//}}
	
	//{{ReleaseShiftAction
	public boolean isCoolingDownShiftReleaseAction(){
		if (this.ShiftReleaseCooldownStartTime == null) {
			return false;
		}
		return getReleaseShiftCoolDownTimeLeft() > 1L;
	}
	
	public Long getReleaseShiftCoolDownTimeLeft(){
		if (this.ShiftReleaseCooldownStartTime == null) {
			return (Long.parseLong("0"));
		}
		Long CoolDowntime = this.ActiveAbility.getReleaseShiftAction().getCoolDownTime();
		return (CoolDowntime - (System.currentTimeMillis() - this.ShiftReleaseCooldownStartTime));
	}
	//}}
	
	//{{DropItemAction
	public boolean isCoolingDownDrop_ItemAction(){
		if (this.Drop_ItemCooldownStartTime == null) {
			return false;
		}
		return getDrop_ItemCoolDownTimeLeft() > 1L;
	}
	
	public Long getDrop_ItemCoolDownTimeLeft(){
		if (this.Drop_ItemCooldownStartTime == null) {
			return (Long.parseLong("0"));
		}
		Long CoolDowntime = this.ActiveAbility.getDrop_ItemAction().getCoolDownTime();
		return (CoolDowntime - (System.currentTimeMillis() - this.Drop_ItemCooldownStartTime));
	}
	//}}
	
	public void ActivateAction(MainAction MainACParm){
		MainAction MainAC = MainACParm;
		PressShiftAction PRESSMianAC = null;
		if(MainAC instanceof PressShiftAction){
			PRESSMianAC = (PressShiftAction) MainAC;
			if (this.isCoolingDownShiftPressAction())
			{
				String Message = MainAC.getCoolDownMessage();
				if (!Message.equalsIgnoreCase(""))
				{
					if (Message.contains("&COOLDOWNTIME")) {
						Long TimeLeft = getPressShiftCoolDownTimeLeft()/1000;
						if(TimeLeft < 0){
							TimeLeft = 1L;
						}
						Message = Message.replace("&COOLDOWNTIME", TimeLeft.toString());
					}
					getPlayer().sendMessage(Message);
				}
				return;
			}
			else{
				this.ShiftPressCooldownStartTime = System.currentTimeMillis();;
			}
		}
		else if(MainAC instanceof ReleaseShiftAction){
			if (this.isCoolingDownShiftReleaseAction())
			{
				String Message = MainAC.getCoolDownMessage();
				if (!Message.equalsIgnoreCase(""))
				{
					if (Message.contains("&COOLDOWNTIME")) {
						Long TimeLeft = getReleaseShiftCoolDownTimeLeft()/1000;
						if(TimeLeft < 0){
							TimeLeft = 1L;
						}
						Message = Message.replace("&COOLDOWNTIME", TimeLeft.toString());
					}
					getPlayer().sendMessage(Message);
				}
				return;
			}
			else{
				this.ShiftReleaseCooldownStartTime = System.currentTimeMillis();
			}
		}
		else if(MainAC instanceof DropItemAction){
			if (this.isCoolingDownDrop_ItemAction())
			{
				String Message = MainAC.getCoolDownMessage();
				if (!Message.equalsIgnoreCase(""))
				{
					if (Message.contains("&COOLDOWNTIME")) {
						Long TimeLeft = getDrop_ItemCoolDownTimeLeft()/1000;
						if(TimeLeft < 0){
							TimeLeft = 1L;
						}
						Message = Message.replace("&COOLDOWNTIME", TimeLeft.toString());
					}
					getPlayer().sendMessage(Message);
				}
				return;
			}
			else{
				this.Drop_ItemCooldownStartTime = System.currentTimeMillis();
			}
		}
		else if(MainAC instanceof CommandAction){
			if(((CommandAction) MainAC).isCoolingDown(this)){
				String Message = MainAC.getCoolDownMessage();
				if (!Message.equalsIgnoreCase(""))
				{
					if (Message.contains("&COOLDOWNTIME")) {
						Long TimeLeft = getDrop_ItemCoolDownTimeLeft()/1000;
						if(TimeLeft < 0){
							TimeLeft = 1L;
						}
						Message = Message.replace("&COOLDOWNTIME", TimeLeft.toString());
					}
					getPlayer().sendMessage(Message);
				}
				return;
			}
			else{
				((CommandAction) MainAC).setCoolDownStartTime(this);
			}
		};
		
		HashMap<Long,List<Action>> DelayAndActionTemp;
		
		if(MainAC.isMovingAction()){
			AdvanceAbilityMain.getlogger().Debug("isMovingAction");
			AdvanceAbilityMain.getlogger().Debug("Moving " + MoveDirection.toString());			
			switch(MoveDirection){
			case BACKWARD:
				DelayAndActionTemp = MainAC.getDelayAndMovingBackwardActionMap();
				AdvanceAbilityMain.getlogger().Debug("Case BACKWARD");
				break;
			case FORWARD:
				DelayAndActionTemp = MainAC.getDelayAndMovingForwardActionMap();
				AdvanceAbilityMain.getlogger().Debug("Case FORWARD");
				break;
			case LEFT:
				DelayAndActionTemp = MainAC.getDelayAndMovingLeftActionMap();
				AdvanceAbilityMain.getlogger().Debug("Case LEFT");
				break;
			case RIGHT:
				DelayAndActionTemp = MainAC.getDelayAndMovingRightActionMap();
				AdvanceAbilityMain.getlogger().Debug("Case RIGHT");
				break;
			case STANDING:
				DelayAndActionTemp = MainAC.getDelayAndStandingActionMap();
				AdvanceAbilityMain.getlogger().Debug("Case STANDING");
				break;
			default:
				DelayAndActionTemp = MainAC.getDelayAndActionMap();
				AdvanceAbilityMain.getlogger().Debug("Case default");
				break;
			}
		}
		else{
			DelayAndActionTemp = MainAC.getDelayAndActionMap();
		}
		final HashMap<Long,List<Action>> DelayAndAction = DelayAndActionTemp;
		
		if(DelayAndAction.isEmpty()){
			return;
		}
		boolean isHoldShiftTemp = false;
		if(PRESSMianAC != null){
			if(PRESSMianAC.isHoldShiftAction()){
				isHoldShiftTemp = true;
			}
		}
		final boolean isHoldShiftAction = isHoldShiftTemp;
		final Long StartedTime = System.currentTimeMillis();
		final List<Long> DelayKeys =  new ArrayList<Long>(DelayAndAction.keySet());
		final Player BukkitPlayer = this.getPlayer();
		Collections.sort(DelayKeys);
		
		new BukkitRunnable(){
			
			Long Duration = Collections.min(DelayKeys);
			Long DelayKey = 0L;
			
			@Override
			public void run() {
				for(Long CheckingDelay : DelayKeys){
					if(CheckingDelay >= Duration){
						DelayKey = CheckingDelay;
						if(ActiveAction(DelayAndAction.get(DelayKey),StartedTime, BukkitPlayer, isHoldShiftAction)){
							this.cancel();//will get in this if holdshiftaction and player is releaseshift
						}
						break;
					}
				}
				if(Duration >= Collections.max(DelayKeys)){
					if(isHoldShiftAction){
						Duration = Collections.min(DelayKeys);
						return;
					}
					this.cancel();
				}
								
				Duration++;
			}
		}.runTaskTimer(AdvanceAbilityMain.getInstance(), Collections.min(DelayKeys), 1L);
	}

	public void ActivateRepeatedlyAction(){
		final RepeatedlyAction MainAC = this.ActiveAbility.getRepeatedlyAction();
		final HashMap<Long,List<Action>> DelayAndAction = MainAC.getDelayAndActionMap();
		final Long StartedTime = System.currentTimeMillis();
		final List<Long> DelayKeys =  new ArrayList<Long>(DelayAndAction.keySet());
		Collections.sort(DelayKeys);
		
		RepeatedlyTask = new BukkitRunnable(){
			
			@Override
			public void run() {
				new BukkitRunnable(){
					
					Long Duration = Collections.min(DelayKeys);
					Long DelayKey = 0L;

					@Override
					public void run() {
						for(Long CheckingDelay : DelayKeys){
							if(CheckingDelay >= Duration){
								DelayKey = CheckingDelay;
								ActiveAction(DelayAndAction.get(DelayKey),StartedTime, null, false);
								break;
							}
						}
						if(Duration >= Collections.max(DelayKeys)){
							this.cancel();
							return;
						}
										
						Duration++;
					}					
				}.runTaskTimer(AdvanceAbilityMain.getInstance(), Collections.min(DelayKeys), 1L);
			}
			
			@Override
			public void cancel() {
				RepeatedlyTask = null;
				super.cancel();
			}
			
		};
		
		RepeatedlyTask.runTaskTimer(AdvanceAbilityMain.getInstance(), 0L, MainAC.getPeriod());
	}
	
	protected void DeactivateRepeatedlyAction(){
		if(RepeatedlyTask != null){
			RepeatedlyTask.cancel();
			RepeatedlyTask = null;
		}
	}
	
	protected void ActivateMovementDetectionAction(){
		MovementRunnable = CreateMoveDirectionTracker();
		MovementRunnable.runTaskTimer(AdvanceAbilityMain.getInstance(), 0L, this.getActiveAbility().getDirectionTrackerPeriod());
	}
	
	protected void DeactivateMovementDetectionAction(){
		if(MovementRunnable != null){
			MovementRunnable.cancel();
		}
		MovementRunnable = null;
	}
	
	private BukkitRunnable CreateMoveDirectionTracker(){
		return new BukkitRunnable(){
			
			Vector oldLocation = getPlayer().getLocation().toVector();

			@Override
			public void run() {
				Player bukkitPlayer = getPlayer();	
				
				Vector CurrenceLocation = bukkitPlayer.getLocation().toVector();
				Vector CurrentToOldDirection = oldLocation.subtract(CurrenceLocation).normalize(); // Vector CurrenceLocation to oldLocation
							
				Vector Front = CurrenceLocation.clone();
				double Yaw = Math.toRadians(bukkitPlayer.getLocation().getYaw());
				double plusX = (-2) * Math.sin(Yaw); //USE -1 for get to player front location
				double plusZ = (-2) * (Math.cos(Yaw)); //USE -1 for get to player front location
				Front.setX(Front.getX() + plusX);
				Front.setZ(Front.getZ() - plusZ);
				
				Vector FrontVector = Front.subtract(CurrenceLocation).normalize();
				FrontVector.setY(0);
				CurrentToOldDirection.setY(0);
				double dot = CurrentToOldDirection.dot(FrontVector);
				double det = ((CurrentToOldDirection.getX()*FrontVector.getZ()) - (CurrentToOldDirection.getZ()*FrontVector.getX())); //x1*y2 - y1*x2
				double angle = Math.toDegrees(Math.atan2(det, dot));
				if(Double.isNaN(angle)){
					MoveDirection = Movement.STANDING;
				}
				if(angle < -135 || angle >= 135){
					MoveDirection = Movement.FORWARD;
				};
				if(angle < 135 && angle >= 45){
					MoveDirection = Movement.RIGHT;
				};
				if(angle < 45 && angle >= -45){
					MoveDirection = Movement.BACKWARD;
				};
				if(angle < -45 && angle >= -135){
					MoveDirection = Movement.LEFT;
				};
				//AdvanceAbilityMain.getlogger().Debug("Moving " + MoveDirection.toString());	
				oldLocation = bukkitPlayer.getLocation().toVector();
			}
			
			@Override
			public void cancel(){
				MovementRunnable = null;
				super.cancel();
			}
		};
	}

	private BukkitRunnable MovementRunnable;
	
	/**
	 * return boolean true if have to cancel task
	 * @param ACList
	 * @param StartTime
	 * @param BukkitPlayer
	 * @param isHoldingAction
	 * @return
	 */
	private boolean ActiveAction(List<Action> ACList, Long StartTime, Player BukkitPlayer, boolean isHoldingAction){
		boolean tempboo = false;
		for (Action AC : ACList) {
			if(tempboo){
				return true;
			}
			if(isHoldingAction){
				if(BukkitPlayer.isSneaking()){
					AC.Activate(this);
					//tempboo = false;
				}
				else{
					tempboo = true;
				}
			}
			else{
				AC.Activate(this);
				//tempboo = false;
			}
		}
		return false;
	}
}
