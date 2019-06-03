package soulfoam.arena.entities.abilities.misc;

import soulfoam.arena.entities.Entity;

public class StackableAbility {

	private int stackID;
	protected Entity stackOwner;
	protected float removeTimer;
	protected boolean needsRemoved;

	protected boolean removeWithTime;

	public StackableAbility(int stackID, Entity stackOwner, float removeTimer) {
		setStackID(stackID);
		this.stackOwner = stackOwner;
		this.removeTimer = removeTimer * 1000;
		removeWithTime = true;
	}

	public StackableAbility(int stackID, Entity stackOwner) {
		setStackID(stackID);
		this.stackOwner = stackOwner;
	}

	public void update(int delta) {
		if (removeWithTime) {
			removeTimer -= delta;
			if (removeTimer <= 0) {
				needsRemoved = true;
			}
		}
	}

	public void applyStack() {

	}

	public void consumeStack() {
		needsRemoved = true;
	}

	public int getStackID() {
		return stackID;
	}

	public void setStackID(int stackID) {
		this.stackID = stackID;
	}
}
