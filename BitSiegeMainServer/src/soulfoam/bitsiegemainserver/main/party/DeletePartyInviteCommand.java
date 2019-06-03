package soulfoam.bitsiegemainserver.main.party;

import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.logs.LogType;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMaker;

public class DeletePartyInviteCommand extends Command{
	
	private int partyID;
	private ClientThread clientThread;
	private int senderID;
	private int result = -1;
	private boolean broadcast;
	
	public DeletePartyInviteCommand(int partyID, ClientThread clientThread, int senderID, boolean broadcast){
		this.partyID = partyID;
		this.clientThread = clientThread;
		this.senderID = senderID;
		this.broadcast = broadcast;
	}
	
	public boolean execute() {
		
		NetworkManager.getManager().getPartyManager().getPartyInvites().remove(NetworkManager.getManager().getPartyManager().getInviteFromAllInfo(partyID, senderID, clientThread.getUserAccount().getID()));
		
		if (broadcast){
			result = LobbyReturnCode.REQUEST_PARTY_DECLINE_SUCCESS; 
			
			clientThread.getOutput().println(LobbyOPCode.OP_DECLINEREQUEST);
			clientThread.getOutput().println(result);
			if (result == LobbyReturnCode.REQUEST_PARTY_DECLINE_SUCCESS){
				clientThread.getOutput().println(partyID);
			}
		}
		
		return true;
	
	}


	public void undo() {
		
	}

}
