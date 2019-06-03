package soulfoam.arenaserver.entities.abilities.illusionist;

import java.util.ArrayList;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistRemoteSpawnInfo;

public class IllusionistPassive {

	public Entity player;
	public Game gh;
	
	public ArrayList<IllusionistClone> cloneList = new ArrayList<IllusionistClone>();
	
	public int ultCharges = IllusionistRemoteSpawnInfo.CHARGES;
	
	public IllusionistPassive(Entity player){
		this.player = player;
		

	}
	
	public void update(int delta){
		IllusionistClone[] tempList = new IllusionistClone[cloneList.size()];
		cloneList.toArray(tempList);
		for (IllusionistClone ic: tempList){
			if (!Game.getGame().getAbilities().contains(ic)){
				cloneList.remove(ic);
			}
		}
	}
	
}
