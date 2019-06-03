package soulfoam.arenaserver.main.command.server;

import java.net.InetAddress;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.main.command.Command;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.opcode.OPCode;


public class RemoveEntityCommand extends Command{

	private InetAddress ipAddress = null;
	private int port;
	
	public RemoveEntityCommand(int playerID){
		super(Command.COMMAND_REMOVE_ENTITY, playerID);
	}
	
	public RemoveEntityCommand(InetAddress ip, int port){
		super(Command.COMMAND_REMOVE_ENTITY);
		this.ipAddress = ip;
		this.port = port;
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

		Game.getGame().getServerFunctions().removeEntityAbilities(player);
		Game.getGame().getIdPool().addPlayerIDToPool(player.getPlayerID());
		Game.getGame().getServer().sendDataToEveryone(OPCode.OP_REMOVEPLAYER + player.getPlayerID());
		Game.getGame().getPlayers().remove(player);

		Game.getGame().getServerFunctions().serverBroadcastPlayers();
		return true;
	
	}


	public void undo() {
		
	}


}
