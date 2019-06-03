package soulfoam.arena.entities.abilities;

import java.util.ArrayList;

import soulfoam.arena.entities.Entity;

public class AbilityCollision {

	private ArrayList<Entity> playersHit = new ArrayList<Entity>();
	private ArrayList<Entity> playersCycled = new ArrayList<Entity>();

	public ArrayList<Entity> getPlayersCycled() {
		return playersCycled;
	}

	public void setPlayersCycled(ArrayList<Entity> playersCycled) {
		this.playersCycled = playersCycled;
	}

	public ArrayList<Entity> getPlayersHit() {
		return playersHit;
	}

	public void setPlayersHit(ArrayList<Entity> playersHit) {
		this.playersHit = playersHit;
	}

}
