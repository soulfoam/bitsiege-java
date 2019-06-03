package soulfoam.arenaserver.entities.abilities.archer;


import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.main.game.Game;

public class ArcherPassive {
	
	public Entity player;
	public Game gh;
	
	public final int EXPLOSION_COUNT = 4;
	public int explosionArrowCount;
	
	public ArcherPassive(Entity player){
		this.player = player;
		
	}


}
