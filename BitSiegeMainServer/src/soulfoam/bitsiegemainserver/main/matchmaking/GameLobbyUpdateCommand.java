package soulfoam.bitsiegemainserver.main.matchmaking;

import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.party.PartyGroup;

public class GameLobbyUpdateCommand extends Command{
	
	private ClientThread clientThread;
	private String lobbyString = "";
	private String[] lobbyInfo;
	
	public GameLobbyUpdateCommand(ClientThread clientThread, String lobbyString, String[] lobbyInfo){
		this.clientThread = clientThread;
		this.lobbyString = lobbyString;
		this.lobbyInfo = lobbyInfo;
	}
	
	public boolean execute() {
		
		PartyGroup party = clientThread.getParty();
		
		if (party.getCurrentGameLobby() == null){
			return false;
		}
		
		if (lobbyInfo[0].equalsIgnoreCase("S")){ // skin pick change
			party.getCurrentGameLobby().pickSkin(LobbyUtil.parseInt(lobbyInfo[1]), clientThread.getUserAccount().getID());
		}
		if (lobbyInfo[0].equalsIgnoreCase("U")){ //underglow pick change
			party.getCurrentGameLobby().pickUnderglow(LobbyUtil.parseInt(lobbyInfo[1]), clientThread.getUserAccount().getID());
		}
		if (lobbyInfo[0].equalsIgnoreCase("L")){ //class lock in
			party.getCurrentGameLobby().lockInChallenger(LobbyUtil.parseInt(lobbyInfo[1]), clientThread.getUserAccount().getID());
		}
		if (lobbyString.substring(0, 2).equalsIgnoreCase("MA")){ //chat message all
			party.getCurrentGameLobby().submitChatMessage(clientThread.getUserAccount().getID(), lobbyString.substring(2).trim(), true);
		}
		if (lobbyString.substring(0, 2).equalsIgnoreCase("MT")){ //chat message team
			party.getCurrentGameLobby().submitChatMessage(clientThread.getUserAccount().getID(), lobbyString.substring(2).trim(), false);
		}
		
		return true;
	
	}

	public void undo() {
		
	}


}
