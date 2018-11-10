package com.ZerkZeta.AdvanceAbility.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;

import com.ZerkZeta.AdvanceAbility.AdvanceAbilityMain;
import com.ZerkZeta.AdvanceAbility.AbilityPlayers.AbilityPlayer;
import com.ZerkZeta.AdvanceAbility.Util.ItemStackUtil;

public class AbilityPlayerListener implements Listener{
		
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerJoin(PlayerJoinEvent e){
		if(AdvanceAbilityMain.getPlayerManager().isOfflineAbilityPlayer(e.getPlayer())){
			AdvanceAbilityMain.getPlayerManager().ContinueSession(e.getPlayer());
		};
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerQuit(PlayerQuitEvent e){
		if(AdvanceAbilityMain.getPlayerManager().isAbilityPlayer(e.getPlayer())){
			AdvanceAbilityMain.getPlayerManager().EndSession(e.getPlayer());
		};
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerToggleSneak(PlayerToggleSneakEvent e){
		Player player = e.getPlayer();
		AbilityPlayer AbPlayer = AdvanceAbilityMain.getPlayerManager().getFormUUID(player.getUniqueId());
		if(AbPlayer == null){
			return;
		}
		if(AbPlayer.isAbilityActivate() == false){
			AdvanceAbilityMain.getlogger().Debug(player.getName() + " have no ability activated");
			return;
		}
		if(player.isSneaking()){
			//ReleaseShift
			if(!AbPlayer.getActiveAbility().isHasReleaseShiftAction()){
				return;
			}
			//AbPlayer.ActivateReleaseShiftAction();
			AbPlayer.ActivateAction(AbPlayer.getActiveAbility().getReleaseShiftAction());
		}
		else{
			//PressShift
			if(!AbPlayer.getActiveAbility().isHasPressShiftAction()){
				return;
			}
			//AbPlayer.ActivatePressShiftAction();
			AbPlayer.ActivateAction(AbPlayer.getActiveAbility().getPressShiftAction());
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = false)
	public void onPlayerDropItem(PlayerDropItemEvent e){
		Player player = e.getPlayer();
		AbilityPlayer AbPlayer = AdvanceAbilityMain.getPlayerManager().getFormUUID(player.getUniqueId());
		if(AbPlayer == null){
			return;
		}
		if(AbPlayer.isAbilityActivate() == false){
			AdvanceAbilityMain.getlogger().Debug(player.getName() + " have no ability activated");
			return;
		}
		ItemStack dropedItem = e.getItemDrop().getItemStack();
		ItemStack TrackItem = AbPlayer.getActiveAbility().getDrop_ItemAction().getItemStackToTrack();
		if(!ItemStackUtil.isItemDeepEqual(TrackItem, dropedItem)){
			return;
		};
		AbPlayer.ActivateAction(AbPlayer.getActiveAbility().getDrop_ItemAction());
	}
	
}
