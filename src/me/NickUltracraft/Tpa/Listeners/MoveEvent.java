package me.NickUltracraft.Tpa.Listeners;

import java.util.ArrayList;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveEvent implements Listener {
	
	public static ArrayList<String> lista = new ArrayList<>();
	
	@EventHandler
	public void onAndar(PlayerMoveEvent e) {
		String n = e.getPlayer().getName();
		if(!lista.contains(n)) {
			lista.add(n);
		}
	}
}
