package me.NickUltracraft.Tpa.Sistema;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

import me.NickUltracraft.Tpa.Main;
import me.NickUltracraft.Tpa.Listeners.MoveEvent;
import me.NickUltracraft.Tpa.Manager.Mensagens;

public class ComandoAceitar implements Listener { 
	
	public static ArrayList<String> lista1 = MoveEvent.lista;
	private int a = 0;

	@EventHandler(ignoreCancelled=true)
	public void onComando(PlayerCommandPreprocessEvent e) {
		String[] args = e.getMessage().toLowerCase().split(" ");
		String cmd = args[0];
		if(Main.m.getConfig().getStringList("Config.ComandosTpAccept").contains(cmd)) {
			e.setCancelled(true);
			Player p = e.getPlayer();
		    if (!p.hasPermission(Main.m.getConfig().getString("Permissoes.Aceitar"))) {
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
		    Player quemConvidou = Bukkit.getPlayer(args[1]);
		    if (quemConvidou != null) {
		    	HashMap<Player, Player> convites = ComandoTpa.convites;
		    	ArrayList<String> lista = MoveEvent.lista;
		    	if (!lista.contains(quemConvidou.getName())) {
		    		if(!Mensagens.erro3.equalsIgnoreCase("")) {
		    			quemConvidou.sendMessage(Mensagens.erro3);
		    		}
		    		return;
		    	}
		    	if (convites.containsKey(quemConvidou)) {
		    		if(!Mensagens.aceito.equalsIgnoreCase("")) {
		    			p.sendMessage(Mensagens.aceito);
		    		}
		    		if(!Mensagens.teletransporte1.equalsIgnoreCase("")) {
		    			quemConvidou.sendMessage(Mensagens.teletransporte1);
		    		}
		    		lista1.add(p.getName());
		    	} else if (lista1.contains(p.getName())) {
		    		if(!Mensagens.jaaceito.equalsIgnoreCase("")) {
		    			p.sendMessage(Mensagens.jaaceito);
		    		}
		    		return;
		    	}
		    	String title = Mensagens.title;
		    	String subtitle = Mensagens.subtitle;
		    	String subtitle2 = Mensagens.subtitle2;
		    	new BukkitRunnable() {
		    		@SuppressWarnings("deprecation")
					public void run() {
		    			a++;
		    			if(a == 1) {
		    				if(!title.equalsIgnoreCase("") && (!subtitle.equalsIgnoreCase(""))) {
			    				quemConvidou.sendTitle(title, subtitle.replace("%s%", "3"));
			    				quemConvidou.playSound(p.getLocation(), Sound.NOTE_PLING, 15, 1);
		    				}
		    				return;
		    			}
		    			if(a == 2) {
		    				if(!title.equalsIgnoreCase("") && (!subtitle.equalsIgnoreCase(""))) {
			    				quemConvidou.sendTitle(title, subtitle.replace("%s%", "2"));
			    				quemConvidou.playSound(p.getLocation(), Sound.NOTE_PLING, 15, 1);
		    				}
		    				return;
		    			}	
		    			if(a == 3) {
		    				if(!title.equalsIgnoreCase("") && (!subtitle.equalsIgnoreCase(""))) {
			    				quemConvidou.sendTitle(title, subtitle.replace("%s%", "1"));
			    				quemConvidou.playSound(p.getLocation(), Sound.NOTE_PLING, 15, 1);
		    				}
		    				return;
		    			}
		    			if(a == 4) {
		    				if(!title.equalsIgnoreCase("") && (!subtitle2.equalsIgnoreCase(""))) {
			    				quemConvidou.sendTitle(title, subtitle2);
			    				quemConvidou.playSound(p.getLocation(), Sound.LEVEL_UP, 15, 1);
		    				}
			    			quemConvidou.teleport(p);
			    			lista.remove(p.getName());
			    			if(!Mensagens.aceito.equalsIgnoreCase("")) {
			    				quemConvidou.sendMessage(Mensagens.aceito);
			    			}
			    			convites.remove(quemConvidou);
			    			a = 0;
			    			p.sendTitle("", "");
			    			if(lista1.contains(quemConvidou.getName())) {
		    					lista1.remove(quemConvidou.getName());
		    				}
			    			cancel();
			    			return;
		    			}
		    			return;
		    		}
		    	}.runTaskTimer(Main.m, 0, 20);
		    }
		    return;
		}
	}
}
