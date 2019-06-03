package soulfoam.arenaserver.entities.abilities.misc;

import soulfoam.arenaserver.entities.Entity;

public class StackableAbility {
	
	public int stackID;
	public Entity stackOwner;
	public Entity stackGiver;
	public float removeTimer;
	public boolean needsRemoved;
	
	public boolean consumeOnRemove;
	public boolean removeOnConsume;
	
	private boolean removeWithTime;
	
	public StackableAbility(int stackID, Entity stackOwner, Entity stackGiver, float removeTimer){
		this.stackID = stackID;
		this.stackOwner = stackOwner;
		this.stackGiver = stackGiver;
		this.removeTimer = removeTimer * 1000;
		removeWithTime = true;
	}
	
	public StackableAbility(int stackID, Entity stackOwner, Entity stackGiver, int removeTimer){
		this.stackID = stackID;
		this.stackOwner = stackOwner;
		this.stackGiver = stackGiver;
		this.removeTimer = removeTimer;
		removeWithTime = true;
	}
	
	public StackableAbility(int stackID, Entity stackOwner, Entity stackGiver){
		this.stackID = stackID;
		this.stackOwner = stackOwner;
		this.stackGiver = stackGiver;
	}

	public void update(int delta){
		if (removeWithTime){
			removeTimer -= delta;
			if (removeTimer <= 0){
				needsRemoved = true;
			}
		}
	}
	
	public void applyStack(){
		
	}
	
	public void consumeStack(){

	}
	
}
