package soulfoam.arena.entities.abilities.archer;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.game.Game;

public class ArcherPassive {

	public Entity player;

	public final int EXPLOSION_COUNT = 4;
	public int explosionArrowCount;

	public ArcherPassive(Entity player) {
		this.player = player;
	}

}
