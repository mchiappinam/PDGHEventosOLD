package me.mchiappinam.pdgheventos;

import java.io.File;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Mainn extends JavaPlugin {

	private Frog classeFrog;
	
	public Mainn(Frog frog) {
		classeFrog=frog;
	}
	
	protected static Economy econ=null;
	public String evento="Nenhum evento"; // evento+" ocorrendo no momento."
	
	public void onEnable() {
		File file=new File(getDataFolder(),"config.yml");
		if(!file.exists()) {
			try {
				saveResource("config_template.yml",false);
				File file2=new File(getDataFolder(),"config_template.yml");
				file2.renameTo(new File(getDataFolder(),"config.yml"));
			}catch(Exception e) {}
		}
		getServer().getPluginCommand("evento").setExecutor(new Comando(this));
		getServer().getPluginManager().registerEvents(new FrogListeners(this), this);
		if(!setupEconomy()) {
			getLogger().warning("ERRO: Vault (Economia) nao encontrado!");
			getLogger().warning("Desativando o ..");
			getServer().getPluginManager().disablePlugin(this);
        }

		try {classeFrog.premio = getConfig().getDouble("frog.premio");} catch(Exception e) {classeFrog.premio=1.0;}
		try {classeFrog.bpt = getConfig().getInt("frog.bpt");} catch(Exception e) {classeFrog.bpt=50;}
		try {classeFrog.init = getConfig().getInt("frog.timer.iniciando");} catch(Exception e) {classeFrog.init=100;}
		try {classeFrog.game = getConfig().getInt("frog.timer.rodada");} catch(Exception e) {classeFrog.game=240;}
		try {classeFrog.rmsn = getConfig().getInt("frog.timer.tirarNeve");} catch(Exception e) {classeFrog.rmsn=100;}
		try {classeFrog.bksn = getConfig().getInt("frog.timer.voltarNeve");} catch(Exception e) {classeFrog.bksn=25;}
		
		getServer().getConsoleSender().sendMessage("§3[PDGHEventos] §2ativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHEventos] §2Acesse: http://pdgh.com.br/");
	}

	public void onDisable() {
		getServer().getConsoleSender().sendMessage("§3[PDGHEventos] §2desativando...");
		
		getServer().getConsoleSender().sendMessage("§3[PDGHEventos] §2desativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHEventos] §2Acesse: http://pdgh.com.br/");
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp=getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ=rsp.getProvider();
        return econ != null;
	}
	
	public void helpConsole(ConsoleCommandSender c) {
		c.sendMessage("Ajuda: /evento frog iniciar");
	}
	
	public void help(Player p) {
		p.sendMessage("Ajuda: /evento frog iniciar");
	}
	
	public void noPerm(Player p) {
		p.sendMessage("§cSem permissões");
	}
    
}