package me.NickUltracraft.Tpa.Sistema;

import java.util.HashMap;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.NickUltracraft.Tpa.Main;
import me.NickUltracraft.Tpa.Manager.Config;
import me.NickUltracraft.Tpa.Manager.Mensagens;

public class ComandoTpa implements Listener {
	
	public static HashMap<Player, Player> convites = new HashMap<>();
	
	@EventHandler(ignoreCancelled=true)
	public void onComando(PlayerCommandPreprocessEvent e) {
		String[] args = e.getMessage().toLowerCase().split(" ");
		String cmd = args[0];
		if(Main.m.getConfig().getStringList("Config.ComandosTpa").contains(cmd)) {
			e.setCancelled(true);
		    Player p = e.getPlayer();
		    if (!p.hasPermission(Main.m.getConfig().getString("Permissoes.Usar"))) {
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
		    if (alvo == null) {
		    	if(!Mensagens.offline.equalsIgnoreCase("")) {
		    		p.sendMessage(Mensagens.offline);
		    	}
		    	return;
		    }
		    if (alvo.equals(p)) {
		    	if(!Mensagens.erro1.equalsIgnoreCase("")) {
		    		p.sendMessage(Mensagens.erro1);
		    	}
		    	return;
		    }
		    if (alvo != null) {
		    	if (convites.containsKey(p)) {
		    		if(!Mensagens.erro2.equalsIgnoreCase("")) {
		    			p.sendMessage(Mensagens.erro2);
		    		}
		    		return;
		    	}
		    	convites.put(p, p);
		    	TextComponent texto = new TextComponent(Mensagens.pediu.replace("%jogador%", p.getName()));
		    	TextComponent texto4 = new TextComponent("\n§eClique ");
		    	texto.addExtra(texto4);
		    	TextComponent texto2 = new TextComponent("§a§lAQUI");
		    	texto.addExtra(texto2);
		    	TextComponent texto5 = new TextComponent(" §epara aceitar");
		    	texto.addExtra(texto5);
		    	TextComponent texto6 = new TextComponent(" §eou clique ");
		    	texto.addExtra(texto6);
		    	TextComponent texto3 = new TextComponent("§c§lAQUI");
		    	texto.addExtra(texto3);
		    	TextComponent texto8 = new TextComponent(" §epara negar.");
		    	texto.addExtra(texto8);
		    	TextComponent texto7 = new TextComponent("\n§eO pedido de teleporte expira em §e" + Config.tempodelay + " segundos§r§e.");
		    	texto.addExtra(texto7);
		    	texto2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd + " " + p.getName()));
		    	texto3.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd + " " + p.getName()));
		    	BaseComponent[] textos = new ComponentBuilder("§aClique para aceitar.").create();
		    	HoverEvent passarMouse = new HoverEvent(HoverEvent.Action.SHOW_TEXT, textos);
		    	texto2.setHoverEvent(passarMouse);
		    	BaseComponent[] textos1 = new ComponentBuilder("§cClique para negar.").create();
		    	HoverEvent passarMouse1 = new HoverEvent(HoverEvent.Action.SHOW_TEXT, textos1);
		    	texto3.setHoverEvent(passarMouse1);
		    	alvo.spigot().sendMessage(texto);
		    	if(!Mensagens.enviou.equalsIgnoreCase("")) {
		    		p.sendMessage(Mensagens.enviou.replace("%jogador%", alvo.getName()));
		    	}
			    new BukkitRunnable() {
		    		public void run() {
		    			if (ComandoTpa.convites.containsKey(p)) {
		    				ComandoTpa.convites.remove(p);
		    				if(!Mensagens.expirou.equalsIgnoreCase("")) {
		    					p.sendMessage(Mensagens.expirou);
		    				}
		    				cancel();
		    				return;
		    			} else {
		    				cancel();
		    				return;
		    			}
		    		}
			    }.runTaskLater(Main.m, 20 * Config.tempodelay);
			    return;
		    }
		}
	}
}
