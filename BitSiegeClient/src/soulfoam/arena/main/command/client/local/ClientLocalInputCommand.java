package soulfoam.arena.main.command.client.local;

import soulfoam.arena.main.command.Command;
import soulfoam.arena.main.game.Game;
import soulfoam.arenashared.main.opcode.OPCode;

public class ClientLocalInputCommand extends Command {

	private byte currentDirection;
	private boolean autoAttacking;
	private int mouseX;
	private int mouseY;
	private byte currentVelocity;
	
	public ClientLocalInputCommand(byte currentDirection, boolean autoAttacking, int mouseX, int mouseY, byte currentVelocity) {
		super(Command.COMMAND_LOCAL_UPDATE_ENTITY);

		this.currentDirection = currentDirection;
		this.autoAttacking = autoAttacking;

		this.mouseX = mouseX;
		this.mouseY = mouseY;
		
		this.currentVelocity = currentVelocity;
	}

	public boolean execute(int delta) {

		Game.getGame().getClient().sendData(OPCode.OP_PLAYERUPDATE + currentDirection + "," + autoAttacking + "," + mouseX + "," + mouseY + "," + currentVelocity);
		
		return true;

	}

	public void undo() {

	}

	public byte getCurrentDirection() {
		return currentDirection;
	}

	public boolean isAutoAttacking() {
		return autoAttacking;
	}

	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}
}