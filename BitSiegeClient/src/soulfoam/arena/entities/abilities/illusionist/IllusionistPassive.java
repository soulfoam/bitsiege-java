package soulfoam.arena.entities.abilities.illusionist;

import java.util.ArrayList;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.game.Game;

public class IllusionistPassive {

	private Entity player;

	private ArrayList<IllusionistClone> cloneList = new ArrayList<IllusionistClone>();

	public IllusionistPassive(Entity player) {
		this.player = player;
	}

	public void update(int delta) {
		IllusionistClone[] tempList = new IllusionistClone[cloneList.size()];
		cloneList.toArray(tempList);
		for (IllusionistClone ic : tempList) {
			if (ic != null) {
				if (!Game.getGame().getAbilities().contains(ic)) {
					cloneList.remove(ic);
				}
			}
		}
	}

	public Entity getPlayer() {
		return player;
	}

	public ArrayList<IllusionistClone> getCloneList() {
		return cloneList;
	}
}
