package soulfoam.bitsiegemainserver.main.party;

import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.logs.LogType;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMaker;
import soulfoam.bitsiegemainserver.main.notify.NotifyUsersOfPartyCommand;

public class CreatePartyCommand extends Command{
	
	private ClientThread clientThread;
	
	public CreatePartyCommand(ClientThread clientThread){
		this.clientThread = clientThread;
	}
	
	public boolean execute() {
		
		int partyID = NetworkManager.getManager().getIDPool().getAvailablePartyID();
		PartyGroup newGroup = new PartyGroup(partyID, clientThread);
		NetworkManager.getManager().getPartyManager().getPartyGroups().add(newGroup);
		
		clientThread.setParty(newGroup);
		
		try {
			NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new NotifyUsersOfPartyCommand(newGroup));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return true;
	
	}


	public void undo() {
		
	}


}
