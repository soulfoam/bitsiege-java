package soulfoam.arenaserver.entities.abilities;

import java.util.ArrayList;

import soulfoam.arenaserver.entities.Entity;

public class AbilityCollision {
	
	private ArrayList<Entity> playersHit = new ArrayList<Entity>();
	private ArrayList<Entity> playersCycled = new ArrayList<Entity>();
	
	public ArrayList<Entity> getPlayersHit() {
		return playersHit;
	}

	public ArrayList<Entity> getPlayersCycled() {
		return playersCycled;
	}

}
