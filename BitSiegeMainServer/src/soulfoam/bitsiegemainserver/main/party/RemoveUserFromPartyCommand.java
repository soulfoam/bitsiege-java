package soulfoam.bitsiegemainserver.main.party;

import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.logs.LogType;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMaker;
import soulfoam.bitsiegemainserver.main.notify.NotifyUsersOfPartyCommand;

public class RemoveUserFromPartyCommand extends Command{
	
	private ClientThread clientThread;
	private PartyGroup party;
	
	public RemoveUserFromPartyCommand(ClientThread clientThread){
		this.clientThread = clientThread;
	}

	public RemoveUserFromPartyCommand(ClientThread clientThread, PartyGroup party){
		this.clientThread = clientThread;
		this.party = party;
	}
	
	public boolean execute() {
		
		if (clientThread == null){
			return false;
		}
		
		clientThread.getParty().removePartyMember(clientThread);
		NetworkManager.getManager().getPartyManager().removeAllInvitesFromParty(clientThread.getParty().getID(), clientThread.getUserAccount().getID());
		
		if (party != null){
			clientThread.setParty(party);
			party.addPartyMember(clientThread);
		}
		
		if (clientThread.getParty().getPartyMembers().size() <= 0){
			NetworkManager.getManager().getIDPool().addPartyIDToPool(clientThread.getParty().getID());
			NetworkManager.getManager().getPartyManager().getPartyGroups().remove(clientThread.getParty());
			return true;
		}
		
		try {
			NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new NotifyUsersOfPartyCommand(clientThread.getParty()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return true;
	
	}


	public void undo() {
		
	}


}
