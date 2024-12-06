package me.mchiappinam.pdgheventos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

public class Comando implements CommandExecutor {
	private Mainn plugin;
	BukkitTask efrog;
	boolean first = true;

	public Comando(Mainn main) {
		plugin=main;
	}
	private Frog classeFrog;
	
	public Comando(Frog frog) {
		classeFrog=frog;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if(cmd.getName().equalsIgnoreCase("evento")) {
			if (args.length==0) {
	            Location loc1 = new Location(plugin.getServer().getWorld(plugin.getConfig().getString("worldDefault")), -25006.5, 73, 24993.5);
	            loc1.setPitch(0);
	            loc1.setYaw(180);
	            Location loc2 = new Location(plugin.getServer().getWorld(plugin.getConfig().getString("worldDefault")), -25006.5, 73, 25006.5);
	            loc2.setPitch(0);
	            loc2.setYaw(180);
	            Location loc3 = new Location(plugin.getServer().getWorld(plugin.getConfig().getString("worldDefault")), -24993.5, 73, 25006.5);
	            loc3.setPitch(0);
	            loc3.setYaw(180);
	            Location loc4 = new Location(plugin.getServer().getWorld(plugin.getConfig().getString("worldDefault")), -24993.5, 73, 24993.5);
	            loc4.setPitch(0);
	            loc4.setYaw(180);
		        Random randomgen=new Random();
		        int i=randomgen.nextInt(2) + 1;
		        if(i==1) {
					((Player)sender).teleport(loc1);
					sender.sendMessage(" ");
					sender.sendMessage("§d[Evento] &9Voce foi teleportado para o spawn 1 do lobby de eventos.");
					sender.sendMessage(" ");
		        }else if(i==2) {
					((Player)sender).teleport(loc2);
					sender.sendMessage(" ");
					sender.sendMessage("§d[Evento] &9Voce foi teleportado para o spawn 2 do lobby de eventos.");
					sender.sendMessage(" ");
		        }else if(i==3) {
					((Player)sender).teleport(loc3);
					sender.sendMessage(" ");
					sender.sendMessage("§d[Evento] &9Voce foi teleportado para o spawn 3 do lobby de eventos.");
					sender.sendMessage(" ");
		        }else if(i==4) {
					((Player)sender).teleport(loc4);
					sender.sendMessage(" ");
					sender.sendMessage("§d[Evento] &9Voce foi teleportado para o spawn 4 do lobby de eventos.");
					sender.sendMessage(" ");
		        }
				return true;
			}
			if(args[0].equalsIgnoreCase("lista")) {
				if((args.length<2) || (args.length>2)) {
					plugin.help((Player)sender);
					return true;
				}
			}else if(args[0].equalsIgnoreCase("frog")) {
				if(!sender.hasPermission("pdgh.admin")) {
					plugin.noPerm((Player)sender);
					return true;
				}
				if((args.length==1)||(args.length==2)) {
					if(sender==plugin.getServer().getConsoleSender()) {
						plugin.helpConsole(plugin.getServer().getConsoleSender());
						return true;
					}
					plugin.help((Player)sender);
					return true;
	        	}
				if(args[1].equalsIgnoreCase("iniciar")) {
					if(args.length>=3) {
						if(sender==plugin.getServer().getConsoleSender()) {
							plugin.helpConsole(plugin.getServer().getConsoleSender());
							return true;
						}
						plugin.help((Player)sender);
						return true;
		        	}
					if(plugin.evento!="Nenhum evento") {
						sender.sendMessage("§c"+plugin.evento+" ocorrendo no momento.");
						return true;
					}
					sender.sendMessage("§d[Evento Automático] §aCarregando posições...");
					if(!plugin.getConfig().contains("frog.posicoes")) {
						sender.sendMessage("§d[Evento Automático] §cSem posições salvas!");
						return true;
					}
					if(!plugin.getConfig().contains("frog.posicoes.pos1")) {
						sender.sendMessage("§d[Evento Automático] §cPosição 1 não encontrada!");
						return true;
					}else if(!plugin.getConfig().contains("frog.posicoes.pos2")) {
						sender.sendMessage("§d[Evento Automático] §cPosição 2 não encontrada!");
						return true;
					}else{
						String[] me = plugin.getConfig().getString("frog.posicoes.pos1").split(";");
						Location l = new Location(((Player)sender).getWorld(),Double.parseDouble(me[0]),Double.parseDouble(me[1]),Double.parseDouble(me[2]));
						Frog.pos1=l;
						me = plugin.getConfig().getString("frog.posicoes.pos2").split(";");
						l = new Location(((Player)sender).getWorld(),Double.parseDouble(me[0]),Double.parseDouble(me[1]),Double.parseDouble(me[2]));
						Frog.pos2=l;
						sender.sendMessage("§d[Evento Automático] §aPosições carregadas com sucesso!");
					}
					sender.sendMessage("§d[Evento Automático] §aColocando blocos no sistema...");
					if(!plugin.getConfig().contains("frogBlocos")) {
						sender.sendMessage("§d[Evento Automático] §cBlocos não encontrados!");
						return true;
					}
					for(String n : plugin.getConfig().getStringList("frogBlocos")) {
						String[] me = n.split(";");
						Location l = new Location(((Player)sender).getWorld(),Double.parseDouble(me[0]),Double.parseDouble(me[1]),Double.parseDouble(me[2]));
						((Player)sender).getWorld().getBlockAt(l).setType(Material.getMaterial(Integer.parseInt(me[3].split(":")[0])));
						((Player)sender).getWorld().getBlockAt(l).setData((byte)Integer.parseInt(me[3].split(":")[1]));
					}
					sender.sendMessage("§d[Evento Automático] §aBlocos colocados no sistema com sucesso!");
					sender.sendMessage("§d[Evento Automático] §aColocando blocos no mapa...");
					if(Frog.pos1==null) {
						sender.sendMessage("§d[Evento Automático] Posição 1 não marcada!");
						return true;
					}else if(Frog.pos2==null) {
						sender.sendMessage("§d[Evento Automático] Posição 2 não marcada!");
						return true;
					}else if(classeFrog.saida==null) {
						sender.sendMessage("§d[Evento Automático] Saida não marcada!");
						return true;
					}else if(Frog.pos1.getBlockY()!=Frog.pos2.getY()) {
						sender.sendMessage("§d[Evento Automático] O Y das posições deve ser igual!");
						return true;
					}else if(Frog.pos1==Frog.pos2) {
						sender.sendMessage("§d[Evento Automático] As posições sao iguais!");
						return true;
					}else if(classeFrog.etapa==1) {
						sender.sendMessage("§d[Evento Automático] O PDGHFrog já foi preparado!");
						return true;
					}else if(classeFrog.etapa==2) {
						sender.sendMessage("§d[Evento Automático] Um PDGHFrog está acontecendo!");
						return true;
					}else{
						classeFrog.material_sumiu.clear();
						final Player sen = (Player)sender;
						plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
							public void run() {
								List<FrogBlock2> lt = new ArrayList<FrogBlock2>();
								for(int x=Frog.getMinX();x<=Frog.getMaxX();x++)
									for(int z=Frog.getMinZ();z<=Frog.getMaxZ();z++) {
										Block B = sen.getWorld().getBlockAt(x, Frog.getY(), z);
										if(B.getType()!=Material.AIR&&B.getType()!=Material.SNOW_BLOCK) {
											classeFrog.material_loc.add(B.getLocation());
											if(!classeFrog.material_sumiu.containsKey(B.getTypeId()+":"+(int)B.getData())) {
												classeFrog.material_sumiu.put(B.getTypeId()+":"+(int)B.getData(),false);
												classeFrog.materiais.add(B.getTypeId()+":"+(int)B.getData());
											}
										}else{
											if(B.getType()!=Material.SNOW_BLOCK)
												lt.add(new FrogBlock2(B.getLocation(),Material.SNOW_BLOCK,(byte)0));
										}
									}
								FrogBlockQueue bq = new FrogBlockQueue(lt);
								bq.start();
							}
						}, 1L);
						classeFrog.etapa=1;
						sender.sendMessage("§d[Evento Automático] §aBlocos colocados no mapa com sucesso!");
					}
					sender.sendMessage("§d[Evento Automático] §aIniciando...");
			    	classeFrog.vencedor=null;
					plugin.getServer().broadcastMessage(" ");
			    	plugin.getServer().broadcastMessage("§d[Evento Automático] Evento §c§lFrog");
			    	plugin.getServer().broadcastMessage("§d[Evento Automático] Iniciando o Frog agora!");
			    	plugin.getServer().broadcastMessage(" ");
					classeFrog.etapa=2;
					final Player admin = (Player)sender;
					efrog = plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
						public void run() {
							try {
								if(first) {
									RemoveSnowsInicio(admin);
									first=false;
								}
								else {
									if(classeFrog.materiais.size()>1) {
										int idx = new Random().nextInt(classeFrog.materiais.size());
										final String sorteado = classeFrog.materiais.get(idx);
										List<FrogBlock2> lt = new ArrayList<FrogBlock2>();
										for(int x=Frog.getMinX();x<=Frog.getMaxX();x++)
											for(int z=Frog.getMinZ();z<=Frog.getMaxZ();z++) {
												Block B = admin.getWorld().getBlockAt(x, Frog.getY(), z);
												if(B.getType()==Material.getMaterial(Integer.parseInt(sorteado.split(":")[0]))&&B.getData()==(byte)Integer.parseInt(sorteado.split(":")[1]))
													lt.add(new FrogBlock2(B.getLocation(),Material.SNOW_BLOCK,(byte)0));
											}
										FrogBlockQueue bq = new FrogBlockQueue(lt);
										bq.start();
										RemoveSnows(admin);
										classeFrog.materiais.remove(idx);
									}
									else {
										List<FrogBlock2> lt = new ArrayList<FrogBlock2>();
										int certo = new Random().nextInt(classeFrog.material_loc.size());
										for(Location num : classeFrog.material_loc) {
											if(!num.equals(classeFrog.material_loc.get(certo))) {
												Block B = admin.getWorld().getBlockAt(num);
												lt.add(new FrogBlock2(B.getLocation(),Material.SNOW_BLOCK,(byte)0));
											}
										}
										lt.add(new FrogBlock2(classeFrog.material_loc.get(certo).clone(),Material.WOOL,(byte)14));
										classeFrog.vencedor=classeFrog.material_loc.get(certo).clone().add(0, 1, 0);
										FrogBlockQueue bq = new FrogBlockQueue(lt);
										bq.start();
										efrog.cancel();
									}
								}
							}
							catch(Exception e) {
							}
						}
					}, classeFrog.init, classeFrog.game);
					return true;
				}else if(args[1].equalsIgnoreCase("marcar")) {
					if(args.length>=4) {
						if(sender==plugin.getServer().getConsoleSender()) {
							plugin.helpConsole(plugin.getServer().getConsoleSender());
							return true;
						}
						plugin.help((Player)sender);
						return true;
		        	}
					if(args[2].equalsIgnoreCase("1")) {
						if(classeFrog.etapa==0) {
							Block targetblock = ((Player)sender).getTargetBlock(null, 50);
			                Location location = targetblock.getLocation();
							Frog.pos1=location;
							sender.sendMessage("§d[Evento Automático] §aPosição 1 marcada com sucesso!");
							checkSave((Player)sender);
							return true;
						}
					}else if(args[2].equalsIgnoreCase("2")) {
						if(classeFrog.etapa==0) {
							Block targetblock = ((Player)sender).getTargetBlock(null, 50);
			                Location location = targetblock.getLocation();
							Frog.pos2=location;
							sender.sendMessage("§d[Evento Automático] §aPosição 2 marcada com sucesso!");
							checkSave((Player)sender);
							return true;
						}
					}
					return true;
				}
				if(args.length<3) {
					plugin.help((Player)sender);
					return true;
				}
			}
			if(args.length<2) {
				plugin.help((Player)sender);
				return true;
			}
		}
		return true;
	}

	
	private void BackSnows(final Player admin) {
		plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
			public void run() {
				List<FrogBlock2> lt = new ArrayList<FrogBlock2>();
				for(Location i : classeFrog.material_loc) {
					Block B = admin.getWorld().getBlockAt(i);
					if(B.getType()==Material.AIR)
						lt.add(new FrogBlock2(B.getLocation(),Material.SNOW_BLOCK,(byte)0));
				}
				FrogBlockQueue bq = new FrogBlockQueue(lt);
				bq.start();
			}
		}, classeFrog.bksn);
	}
	
	public void checkSave(Player p) {
		if(Frog.pos1.getBlockY()!=Frog.pos2.getY()) {
			p.sendMessage("§d[Evento Automático] O Y das posições deve ser igual!");
			return;
		}
		
		if(Frog.pos1==null)
			return;
		else if(Frog.pos2==null)
			return;
		else {
			plugin.getConfig().set("frog.posicoes.pos1", Frog.pos1.getX()+";"+Frog.pos1.getY()+";"+Frog.pos1.getZ());
			plugin.getConfig().set("frog.posicoes.pos2", Frog.pos2.getX()+";"+Frog.pos2.getY()+";"+Frog.pos2.getZ());
			plugin.saveConfig();
			p.sendMessage("§d[Evento Automático] §aPosições salvas com sucesso!");
		}
		

		if(Frog.pos1==null)
			return;
		else if(Frog.pos2==null)
			return;
		else {
			List<String> lista = new ArrayList<String>();
			for(int x=Frog.getMinX();x<=Frog.getMaxX();x++)
				for(int z=Frog.getMinZ();z<=Frog.getMaxZ();z++) {
						Block B = p.getWorld().getBlockAt(x, Frog.getY(), z);
						if(B.getType()!=Material.SNOW_BLOCK&&B.getType()!=Material.AIR)
							lista.add(B.getX()+";"+B.getY()+";"+B.getZ()+";"+B.getTypeId()+":"+(int)B.getData());
				}
			plugin.getConfig().set("frogBlocos", lista);
			plugin.saveConfig();
			plugin.reloadConfig();
			lista.clear();
			p.sendMessage("§d[Evento Automático] §aBlocos salvos com sucesso!");
		}
	}
	
	private void RemoveSnows(final Player admin) {
		plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
			public void run() {
				List<FrogBlock2> lt = new ArrayList<FrogBlock2>();
				for(int x=Frog.getMinX();x<=Frog.getMaxX();x++)
					for(int z=Frog.getMinZ();z<=Frog.getMaxZ();z++) {
						Block b = admin.getWorld().getBlockAt(x, Frog.getY(), z);
						if(b.getType()==Material.SNOW_BLOCK)
							lt.add(new FrogBlock2(b.getLocation(),Material.AIR,(byte)0));
					}
				FrogBlockQueue bq = new FrogBlockQueue(lt);
				bq.start();
				BackSnows(admin);
			}
		},classeFrog.rmsn);
	}
	
	private void RemoveSnowsInicio(final Player admin) {
		plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
			public void run() {
				List<FrogBlock2> lt = new ArrayList<FrogBlock2>();
				for(int x=Frog.getMinX();x<=Frog.getMaxX();x++)
					for(int z=Frog.getMinZ();z<=Frog.getMaxZ();z++) {
						Block b = admin.getWorld().getBlockAt(x, Frog.getY(), z);
						if(b.getType()==Material.SNOW_BLOCK)
							lt.add(new FrogBlock2(b.getLocation(),Material.AIR,(byte)0));
					}
				FrogBlockQueue bq = new FrogBlockQueue(lt);
				bq.start();
			}
		},1L);
	}
	
	
	
	
}