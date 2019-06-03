package soulfoam.arenaserver.main.command.server;

import java.net.InetAddress;
import java.sql.Timestamp;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.main.command.Command;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenashared.main.opcode.OPCode;


public class PingUpdateCommand extends Command{

	private InetAddress ipAddress = null;
	private int port;
	private Timestamp ts;
	public PingUpdateCommand(int playerID){
		super(Command.COMMAND_PING_UPDATE, playerID);
		this.ts = new Timestamp(System.currentTimeMillis());
	}
	
	public PingUpdateCommand(InetAddress ip, int port){
		super(Command.COMMAND_PING_UPDATE);
		this.ipAddress = ip;
		this.port = port;
		this.ts = new Timestamp(System.currentTimeMillis());
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
		
		if (player.getPingRequestTime() != null){
			player.addPingToHistory((int)(ts.getTime() - player.getPingRequestTime().getTime()));
			player.setResponding(true);
		}
		
		return true;
	
	}


	public void undo() {
		
	}


}
