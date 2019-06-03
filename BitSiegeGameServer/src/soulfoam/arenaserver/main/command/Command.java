package soulfoam.arenaserver.main.command;

import soulfoam.arenaserver.entities.Entity;

public abstract class Command {
	
	public static final int COMMAND_MOVE_ENTITY = 0;
	public static final int COMMAND_UPDATE_ENTITY = 1;
	public static final int COMMAND_REMOVE_ENTITY = 2;
	public static final int COMMAND_SPAWN_REQUEST = 3;
	public static final int COMMAND_CAST_ABILITY = 4;
	public static final int COMMAND_PING_UPDATE = 5;
	public static final int COMMAND_CHAT_MESSAGE = 6;
	public static final int COMMAND_SPAWN_ABILITY = 7;
	protected int commandID;
	
	protected Entity player;
	protected int storedDelta;
	
	protected int playerID;
	
	protected float delayTime;
	protected float delayTimer;

	public Command(int commandID){
		this.commandID = commandID;
	}

	public Command(int commandID, int playerID){
		this.commandID = commandID;
		this.playerID = playerID;
	}

	public Command(int commandID, int playerID, float delayTimer){
		this.commandID = commandID;
		this.playerID = playerID;
		this.delayTime = delayTimer;
		this.delayTimer = delayTimer;
	}
	
	public boolean timerCompleted(int delta){
		if (delayTimer > 0){
			delayTimer -= delta;
			return false;
		}
		if (delayTimer <= 0){
			return true;
		}
		
		return true;
	}
	public abstract boolean execute(int delta);
	public abstract void undo();

	public int getCommandID() {
		return commandID;
	}

	public Entity getPlayer() {
		return player;
	}

	public int getStoredDelta() {
		return storedDelta;
	}

	public int getPlayerID() {
		return playerID;
	}

	public float getDelayTime() {
		return delayTime;
	}

	public float getDelayTimer() {
		return delayTimer;
	}

	public void setCommandID(int commandID) {
		this.commandID = commandID;
	}

	public void setPlayer(Entity player) {
		this.player = player;
	}

	public void setStoredDelta(int storedDelta) {
		this.storedDelta = storedDelta;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public void setDelayTime(float delayTime) {
		this.delayTime = delayTime;
	}

	public void setDelayTimer(float delayTimer) {
		this.delayTimer = delayTimer;
	}

}
