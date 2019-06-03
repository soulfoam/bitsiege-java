package soulfoam.arenaserver.net.server;

import java.util.ArrayList;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.abilities.Ability;
import soulfoam.arenaserver.entities.objectives.Objective;
import soulfoam.arenaserver.main.game.Game;

public class IDPool {
	
	public ArrayList<Integer> playerIDPool = new ArrayList<Integer>();
	public ArrayList<Integer> abilityIDPool = new ArrayList<Integer>();
	public ArrayList<Integer> objectiveIDPool = new ArrayList<Integer>();
	public ArrayList<Integer> notificationIDPool = new ArrayList<Integer>();

	
	public IDPool (){
		
		initPools();
	}
	
	public void initPools(){
		initPlayerIDPool();
		initAbilityIDPool();
		initObjectiveIDPool();
		initNotificationIDPool();
	}

	public void findInUseAbilityIDs(){
		for (Ability a: Game.getGame().getAbilities()){
			if (abilityIDPool.contains(a.getGameID())){
				abilityIDPool.remove((Integer)a.getGameID());
			}
		}
	}
	
	public void findInUsePlayerIDs(){
		for (Entity e: Game.getGame().getPlayers()){
			if (playerIDPool.contains(e.getPlayerID())){
				playerIDPool.remove((Integer)e.getPlayerID());
			}
		}
	}
	
	public void findInUseObjectives(){
		for (Objective o: Game.getGame().getObjectives()){
			if (objectiveIDPool.contains(o.getObjectiveGameID())){
				objectiveIDPool.remove((Integer)o.getObjectiveGameID());
			}
		}
	}
	
	public void initPlayerIDPool(){
		playerIDPool.clear();
		for (int i = 0; i < 1000; i++){
			playerIDPool.add(i);
		}
		findInUsePlayerIDs();
	}
	
	public void initAbilityIDPool(){
		abilityIDPool.clear();
		for (int i = 0; i < 9999; i++){
			abilityIDPool.add(i);
		}
		findInUseAbilityIDs();
	}
	
	public void initObjectiveIDPool(){
		objectiveIDPool.clear();
		for (int i = 0; i < 250; i++){
			objectiveIDPool.add(i);
		}
		findInUseObjectives();
	}
	
	public void initNotificationIDPool(){
		notificationIDPool.clear();
		for (int i = 0; i < 250; i++){
			notificationIDPool.add(i);
		}
	}
	
	public int getAvailablePlayerID(){

		if (playerIDPool.isEmpty()){
			initPlayerIDPool();
		}
		return playerIDPool.remove(0);
	}

	public int getAvailableAbilityID(){
		if (abilityIDPool.isEmpty()){
			initAbilityIDPool();
		}
		return abilityIDPool.remove(0);
	}
	
	public int getAvailableObjectiveID(){
		if (objectiveIDPool.isEmpty()){
			initObjectiveIDPool();
		}
		return objectiveIDPool.remove(0);
	}
	
	public int getAvailableNotificationID(){
		if (notificationIDPool.isEmpty()){
			initNotificationIDPool();
		}
		return notificationIDPool.remove(0);
	}
	
	public void addPlayerIDToPool(int id){
		playerIDPool.add(id);
	}
	
	public void addAbilityIDToPool(int id){
		abilityIDPool.add(id);
	}
	
	public void addObjectiveIDToPool(int id){
		objectiveIDPool.add(id);
	}
}
