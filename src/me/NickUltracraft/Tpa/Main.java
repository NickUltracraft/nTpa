package me.NickUltracraft.Tpa;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.NickUltracraft.Tpa.Listeners.MoveEvent;
import me.NickUltracraft.Tpa.Sistema.ComandoAceitar;
import me.NickUltracraft.Tpa.Sistema.ComandoNegar;
import me.NickUltracraft.Tpa.Sistema.ComandoTpa;

public class Main extends JavaPlugin {
	
	public static Main m;
	
	public void onEnable() {
		m = this;
		try {
			config();
			eventos();
			comandos();
		} catch (Exception e) {
			console("§cErro durante inicialização do plugin. Desligando plugin...");
			getServer().getPluginManager().disablePlugin(this);
			e.printStackTrace();
		}
	}
	private void comandos() {
		registerEvent(new ComandoTpa());
		registerEvent(new ComandoAceitar());
	    registerEvent(new ComandoNegar());
	}
	private void eventos() {
		registerEvent(new MoveEvent());
	}
	private void config() {
		if(!(new File(getDataFolder(), "config.yml").exists())) {
			saveResource("config.yml", false);
			return;
		}
	}
	private void registerEvent(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}
	public static String getMensagens(String path) {
		return Main.m.getConfig().getString("Mensagens." + path).replace("&", "§");
	}
	public static void console(String mensagem) {
		Bukkit.getConsoleSender().sendMessage(mensagem.replace("&", "§"));
	}

}
