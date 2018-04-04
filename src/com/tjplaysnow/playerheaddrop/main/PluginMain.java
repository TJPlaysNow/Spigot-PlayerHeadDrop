package com.tjplaysnow.playerheaddrop.main;

import com.tjplaysnow.playerheaddrop.api.config.ItemManager;
import com.tjplaysnow.playerheaddrop.api.events.Events;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class PluginMain extends JavaPlugin {
	
	HashMap<UUID, ItemStack> playerHead;
	Events<PlayerJoinEvent> playerJoinEvents;
	Events<PlayerDeathEvent> playerDeathEvents;
	
	@Override
	public void onEnable() {
		playerHead = new HashMap<>();
		playerJoinEvents = new Events<>(this, PlayerJoinEvent.class);
		playerDeathEvents = new Events<>(this, PlayerDeathEvent.class);
		
		playerJoinEvents.onEvent((event) -> {
			if (playerHead.get(event.getPlayer().getUniqueId()) == null) {
				playerHead.put(event.getPlayer().getUniqueId(), ItemManager.getSkull(event.getPlayer().getUniqueId()));
			}
		});
		playerDeathEvents.onEvent((event) -> {
			if (event.getEntity().getKiller() != null) {
				event.getDrops().add(playerHead.get(event.getEntity().getUniqueId()));
			}
		});
	}
}