package me.mchiappinam.pdgheventos;

import java.util.ArrayList;
import java.util.List;

import me.mchiappinam.pdgheventos.FrogBlock2;
import me.mchiappinam.pdgheventos.FrogBlockQueue;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class FrogListeners implements Listener {
	private Mainn plugin;
	private Frog classeFrog;
	
	public FrogListeners(Mainn main) {
		plugin=main;
	}
	
	public FrogListeners(Frog frog) {
		classeFrog=frog;
	}

    
    @EventHandler
    private void onMove(PlayerMoveEvent e) {
		   	if(classeFrog.vencedor!=null)
		    	if(e.getPlayer().getLocation().getBlockX()==classeFrog.vencedor.getBlockX()&&e.getPlayer().getLocation().getBlockZ()==classeFrog.vencedor.getBlockZ()&&e.getPlayer().getLocation().getBlockY()==classeFrog.vencedor.getBlockY()) {
		    		plugin.getServer().broadcastMessage(" ");
		    		plugin.getServer().broadcastMessage("§d[Evento Automatico] Evento §c§lFrog");
		    		plugin.getServer().broadcastMessage("§d[Evento Automatico] "+e.getPlayer().getName()+" venceu!");
		    		plugin.getServer().broadcastMessage(" ");
		    		if(classeFrog.premio!=0) {
		    			e.getPlayer().sendMessage("§d[Evento Automatico] §f§lVoce recebeu §6"+classeFrog.premio+" coins §f§lpor ganhar o evento!");
		    			Mainn.econ.depositPlayer(e.getPlayer().getName(), classeFrog.premio);
		    		}
		    		classeFrog.vencedor=null;
		    		classeFrog.etapa=0;
		    		classeFrog.materiais.clear();
					classeFrog.material_sumiu.clear();
					classeFrog.material_loc.clear();
					Frog.pos1 = null;
					Frog.pos2 = null;
					classeFrog.saida = null;
					plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
						public void run() {
							List<FrogBlock2> lt = new ArrayList<FrogBlock2>();
							for(int x=Frog.getMinX();x<=Frog.getMaxX();x++)
								for(int z=Frog.getMinZ();z<=Frog.getMaxZ();z++) {
									Block b = Frog.pos1.getWorld().getBlockAt(x, Frog.getY(), z);
									lt.add(new FrogBlock2(b.getLocation(),Material.AIR,(byte)0));
								}
							FrogBlockQueue bq = new FrogBlockQueue(lt);
							bq.start();
							Frog.pos1=null;
							Frog.pos2=null;
							classeFrog.saida=null;
							plugin.getServer().broadcastMessage(" ");
							plugin.getServer().broadcastMessage("§d[Evento Automatico] Evento §c§lFrog");
							plugin.getServer().broadcastMessage("§d[Evento Automatico] Fim do evento!");
							plugin.getServer().broadcastMessage(" ");
						}
					},601L); //201L - 200L = 10s
		    	}
	    }
    
    @EventHandler
    private void onBFade(BlockFadeEvent e) {
	    if(classeFrog.etapa!=0)
	    	if(Frog.isInsideEvent(e.getBlock().getLocation()))
	    		e.setCancelled(true);
    }
	
	
	
	
	
	
	
	
}