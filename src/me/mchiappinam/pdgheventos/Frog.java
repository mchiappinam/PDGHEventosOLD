package me.mchiappinam.pdgheventos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Frog {
	protected List<String> materiais = new ArrayList<String>();
	protected HashMap<String, Boolean> material_sumiu = new HashMap<String, Boolean>();
	protected List<Location> material_loc = new ArrayList<Location>();

	public int timefim;
	public int iniciar;
	public static Location pos1 = null;
	public static Location pos2 = null;
	public Location saida = null;
	public Location vencedor = null;
	
	public int etapa = 0;
	
	public double premio = 1.0;
	
	public int bpt = 50;
	public int init = 100;
	public int game = 240;
	public int rmsn = 100;
	public int bksn = 25;
	//private Main plugin;
	
	//public Frog(Mainn main) {
		//plugin=main;
	//}
    
    protected static boolean isInsideEvent(Location p) {
    	if(p.getBlockX()>=getMinX())
    		if(p.getBlockX()<=getMaxX())
    			if(p.getBlockZ()>=getMinZ())
    				if(p.getBlockZ()<=getMaxZ())
    					return true;
    	return false;
    }
    
    protected boolean lostEvent(Player p) {
    	if(p.getLocation().getBlockX()>=getMinX())
    		if(p.getLocation().getBlockX()<=getMaxX())
    			if(p.getLocation().getBlockZ()>=getMinZ())
    				if(p.getLocation().getBlockZ()<=getMaxZ())
    					if(p.getLocation().getBlockY()<getY())
    						return true;
    	return false;
    }
    
    protected static int getMinX() {
   		if(pos1.getBlockX()<pos2.getBlockX())
   			return pos1.getBlockX();
   		else
   			return pos2.getBlockX();
    }
    protected static int getMinZ() {
    	if(pos1.getBlockZ()<pos2.getBlockZ())
    		return pos1.getBlockZ();
    	else
    		return pos2.getBlockZ();
    }
    protected static int getMaxX() {
    	if(pos1.getBlockX()>pos2.getBlockX())
    		return pos1.getBlockX();
    	else
    		return pos2.getBlockX();
    }
    protected static int getMaxZ() {
    	if(pos1.getBlockZ()>pos2.getBlockZ())
    		return pos1.getBlockZ();
    	else
    		return pos2.getBlockZ();
    }
    protected static int getY() {
    	return pos1.getBlockY();
    }
    		
    
}
