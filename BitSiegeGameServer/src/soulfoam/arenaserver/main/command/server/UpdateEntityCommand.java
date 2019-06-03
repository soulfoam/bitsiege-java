package soulfoam.arenaserver.main.command.server;

import java.net.InetAddress;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.main.command.Command;
import soulfoam.arenaserver.main.game.Game;


public class UpdateEntityCommand extends Command{

	private byte oldCurrentDirection;
	private boolean oldIsAutoAttacking;
	
	private byte currentDirection;
	private boolean isAutoAttacking;
	
	private int mouseX;
	private int mouseY;
	
	private byte currentVelocity;
	
	private InetAddress ipAddress;
	private int port;
	
	public UpdateEntityCommand(int playerID, byte currentDirection, boolean isAutoAttacking, int mouseX, int mouseY, byte currentVelocity){
		super(Command.COMMAND_UPDATE_ENTITY, playerID);
		
		this.currentDirection = currentDirection;
		this.isAutoAttacking = isAutoAttacking;
		
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.currentVelocity = currentVelocity;
	}
	
	public UpdateEntityCommand(InetAddress ip, int port, byte currentDirection, boolean isAutoAttacking, int mouseX, int mouseY, byte currentVelocity){
		super(Command.COMMAND_UPDATE_ENTITY);
		
		this.ipAddress = ip;
		this.port = port;
		
		this.currentDirection = currentDirection;
		this.isAutoAttacking = isAutoAttacking;
		
		this.mouseX = mouseX;
		this.mouseY = mouseY;
		this.currentVelocity = currentVelocity;
	}

	public boolean execute(int delta) {
		Entity player = null;
		
		if (ipAddress == null){
			player = Game.getGame().getPlayerObject(playerID);
		}
		else{
			player = Game.getGame().getPlayerObject(ipAddress, port);
		}
		
		if (player == null){
			return true;
		}
		
		this.storedDelta = delta;

		if (!player.isConfused()){
			player.setCurrentDirection(currentDirection);
		}
		player.setAutoAttacking(isAutoAttacking);
		player.setMouseX(mouseX);
		player.setMouseY(mouseY);
		player.setCurrentVelocity(currentVelocity);
		
		
		return true;
	
	}
	

	public void undo() {
		player.setCurrentDirection(oldCurrentDirection);
		player.setAutoAttacking(oldIsAutoAttacking);
	}


}
