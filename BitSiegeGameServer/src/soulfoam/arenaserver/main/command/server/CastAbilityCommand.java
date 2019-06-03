package soulfoam.arenaserver.main.command.server;

import java.net.InetAddress;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.main.command.Command;
import soulfoam.arenaserver.main.game.Game;

import soulfoam.arenashared.main.opcode.OPCode;


public class CastAbilityCommand extends Command{

	private InetAddress ipAddress = null;
	private int port;
	
	private float x, y;
	private byte slotID;
	
	public CastAbilityCommand(int playerID, float x, float y, byte slotID){
		super(Command.COMMAND_CAST_ABILITY, playerID);
		this.x = x;
		this.y = y;
		this.slotID = slotID;
	}
	
	public CastAbilityCommand(InetAddress ip, int port, float x, float y, byte slotID){
		super(Command.COMMAND_CAST_ABILITY);
		this.ipAddress = ip;
		this.port = port;
		this.x = x;
		this.y = y;
		this.slotID = slotID;
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


		player.castAbilityAction(slotID, x, y);
		
		
		return true;
	
	}


	public void undo() {
		
	}


}
