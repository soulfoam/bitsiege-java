package soulfoam.arena.main.command.client;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.command.Command;
import soulfoam.arena.main.game.Game;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class MoveEntityCommand extends Command {

	private float oldX, oldY;
	private byte oldCurrentDirection;
	private byte oldCurrentAction;

	private float remoteX;
	private float remoteY;
	private byte velocity;
	private byte direction;

	private boolean localPlayer;

	public MoveEntityCommand(int playerID, float remoteX, float remoteY, byte velocity, byte direction, byte action) {
		super(Command.COMMAND_PACKET_MOVE_ENTITY, playerID);
		

		this.remoteX = remoteX;
		this.remoteY = remoteY;
		this.velocity = velocity;
		this.direction = direction;

	}

	public MoveEntityCommand(int playerID, float remoteX, float remoteY) {
		super(Command.COMMAND_PACKET_MOVE_ENTITY, playerID);

		this.remoteX = remoteX;
		this.remoteY = remoteY;

		localPlayer = true;

	}

	public boolean execute(int delta) {

		player = Game.getGame().getPlayerObject(playerID);
		
		if (player == null){
			Game.getGame().getClientFunctions().syncGame();
			return true;
		}
		
		setOldValues();
		
		if (!localPlayer) {
			if (player.getRemoteX() == remoteX && player.getRemoteY() == remoteY) {
				player.setCurrentAction(EntityInfo.IDLE);
			} else {
				player.setCurrentAction(EntityInfo.WALKING);
			}
			player.setCurrentVelocity(velocity);
			player.setCurrentDirection(direction);
			player.setRemoteX(remoteX);
			player.setRemoteY(remoteY);
		} else {
			if (player.getRemoteX() == remoteX && player.getRemoteY() == remoteY) {
				player.setCurrentAction(EntityInfo.IDLE);
			} else {
				player.setCurrentAction(EntityInfo.WALKING);
			}
			player.setRemoteX(remoteX);
			player.setRemoteY(remoteY);
		}
		

		return true;

	}
	
	private void setOldValues(){
		oldX = player.getX();
		oldY = player.getY();
		oldCurrentDirection = player.getCurrentDirection();
		oldCurrentAction = player.getCurrentAction();
	}


	public void undo() {
		if (player != null) {
			player.setX(oldX);
			player.setY(oldY);
			player.setCurrentDirection(oldCurrentDirection);
			player.setCurrentAction(oldCurrentAction);
		}
	}

}
