package soulfoam.arenaserver.main.command.server;

import java.net.InetAddress;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.main.command.Command;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.opcode.OPCode;


public class BuyItemCommand extends Command{

	private InetAddress ipAddress = null;
	private int port;
	private int item;
	
	public BuyItemCommand(int playerID, int item){
		super(Command.COMMAND_REMOVE_ENTITY, playerID);
		this.item = item;
	}
	
	public BuyItemCommand(InetAddress ip, int port, int item){
		super(Command.COMMAND_REMOVE_ENTITY);
		this.ipAddress = ip;
		this.port = port;
		this.item = item;
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

		Game.getGame().getServerFunctions().serverBuyItem(player, item);
		
		return true;
	
	}


	public void undo() {
		
	}


}
