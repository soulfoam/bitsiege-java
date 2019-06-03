package soulfoam.bitsiegemainserver.main.gameserver;

import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.accounts.ActiveAccountChangeCommand;
import soulfoam.bitsiegemainserver.main.command.Command;

public class GameServerChangeCommand extends Command{
	
	private GameServerThread serverThread;
	private byte action;
	
	public GameServerChangeCommand(GameServerThread serverThread, byte action){
		this.serverThread = serverThread;
		this.action = action;
	}
	
	public boolean execute() {
		
		if (action == ActiveAccountChangeCommand.ADD){
			NetworkManager.getManager().getActiveGameServerManager().getServers().add(serverThread);
		}
		
		if (action == ActiveAccountChangeCommand.REMOVE){
			NetworkManager.getManager().getActiveGameServerManager().getServers().remove(serverThread);
		}
		
		return true;
	
	}

	public void undo() {
		
	}


}
