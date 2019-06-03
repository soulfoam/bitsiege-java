package soulfoam.arena.main.command;

import soulfoam.arena.entities.Entity;

public abstract class Command {

	public static final int COMMAND_LOCAL_MOVE_ENTITY = 0;
	public static final int COMMAND_PACKET_MOVE_ENTITY = 1;
	public static final int COMMAND_PACKET_UPDATE_ENTITY = 2;
	public static final int COMMAND_LOCAL_CAST_ABILITY = 3;
	public static final int COMMAND_LOCAL_LOBBY_INFO = 4;
	public static final int COMMAND_LOCAL_UPDATE_ENTITY = 5;
	public static final int COMMAND_PACKET_REMOVE_ENTITY = 6;
	public static final int COMMAND_PACKET_ADD_ENTITY = 7;
	public static final int COMMAND_PACKET_ADD_ABILITY = 8;
	
	private int commandID;

	protected Entity player;
	protected int playerID;
	protected int storedDelta;

	protected float delayTime;
	protected float delayTimer;

	public Command(int commandID) {
		this.commandID = commandID;
	}

	public Command(int commandID, int playerID) {
		this.commandID = commandID;
		this.playerID = playerID;
	}

	public Command(int commandID, Entity entity, float delayTimer) {
		this.commandID = commandID;
		player = entity;
		delayTime = delayTimer;
		this.delayTimer = delayTimer;
	}

	public boolean timerCompleted(int delta) {
		if (delayTimer > 0) {
			delayTimer -= delta;
			return false;
		}
		if (delayTimer <= 0) {
			return true;
		}

		return true;
	}

	public abstract boolean execute(int delta);

	public abstract void undo();

	public int getCommandID() {
		return commandID;
	}

	public void setCommandID(int commandID) {
		this.commandID = commandID;
	}

}
