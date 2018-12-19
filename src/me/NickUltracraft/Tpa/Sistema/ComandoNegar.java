package me.NickUltracraft.Tpa.Sistema;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.NickUltracraft.Tpa.Main;
import me.NickUltracraft.Tpa.Manager.Mensagens;

public class ComandoNegar implements Listener {
	
	@EventHandler(ignoreCancelled=true)
	public void onComando(PlayerCommandPreprocessEvent e) {
		String[] args = e.getMessage().toLowerCase().split(" ");
		String cmd = args[0];
		if(Main.m.getConfig().getStringList("Config.ComandosTpDeny").contains(cmd)) {
			e.setCancelled(true);
		    Player p = e.getPlayer();
		    if (!p.hasPermission(Main.m.getConfig().getString("Permissoes.Negar"))) {
		    	if(!Mensagens.permissao.equalsIgnoreCase("")) {
		    		p.sendMessage(Mensagens.permissao);
		    	}
		    	return;
		    }
		    if (args.length == 1 || (args.length == 0)) {
		    	if(!Mensagens.usocmd.equalsIgnoreCase("")) {
		    		p.sendMessage(Mensagens.usocmd.replace("%comando%", cmd.toLowerCase()));
		    	}
		    	return;
		    }
		    Player alvo = Bukkit.getPlayer(args[1]);
		    if (alvo != null) {
		    	HashMap<Player, Player> convites = ComandoTpa.convites;
		    	if (convites.containsKey(alvo))	{
		    		convites.remove(alvo);
		    		if(!Mensagens.cancelado.equalsIgnoreCase("")) {
			    		p.sendMessage(Mensagens.cancelado);
			    		alvo.sendMessage(Mensagens.cancelado);
		    		}
		    		return;
		    	}
		    }
		    return;
		}
	}
}
