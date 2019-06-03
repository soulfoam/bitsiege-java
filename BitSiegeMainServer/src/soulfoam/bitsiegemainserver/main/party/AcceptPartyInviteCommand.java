package soulfoam.bitsiegemainserver.main.party;

import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.notify.NotifyUsersOfPartyCommand;

public class AcceptPartyInviteCommand extends Command{
	
	private ClientThread clientThread;
	private int partyID;
	private int senderID;
	private int result = -1;
	
	public AcceptPartyInviteCommand(int partyID, ClientThread clientThread, int senderID){
		this.partyID = partyID;
		this.clientThread = clientThread;
		this.senderID = senderID;
	}
	
	public boolean execute() {
		
		int accountID = clientThread.getUserAccount().getID();
		
		if (NetworkManager.getManager().getPartyManager().getInviteFromSenderIDAndReceiverID(senderID, accountID) == null){
			result = LobbyReturnCode.REQUEST_PARTY_REQUESTDOESNTEXIST;
			sendResult();
			return false;
		}
		
		PartyGroup party = NetworkManager.getManager().getPartyManager().getPartyGroupByID(partyID);

		if (party != null){
			
			if (!party.isUserInParty(senderID)){
				result = LobbyReturnCode.REQUEST_PARTY_REQUESTDOESNTEXIST;
				sendResult();
				return false;
			}
			
			if (party.getPartyMembers().size() >= 4){
				result = LobbyReturnCode.REQUEST_PARTY_FULL;
				sendResult();
				return false;
			}
			
			if (party.isUserInParty(accountID)){
				result = LobbyReturnCode.REQUEST_PARTY_ALREADYINPARTY;
				sendResult();
				return false;
			}
			
			if (party.getPartyLeader().getUserAccount().getID() != senderID && !party.canAnyoneInvite()){
				result = LobbyReturnCode.REQUEST_PARTY_PRIVATEPARTY;
				sendResult();
				return false;
			}

			try {
				NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new RemoveUserFromPartyCommand(clientThread, party));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
		else{
			result = LobbyReturnCode.REQUEST_PARTY_REQUESTDOESNTEXIST;
			sendResult();
			return false;
		}
		
		try {
			NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new DeletePartyInviteCommand(partyID, clientThread, senderID, false));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		result = LobbyReturnCode.REQUEST_PARTY_ACCEPT_SUCCESS; 
		sendResult();
			
		
		if (result == LobbyReturnCode.REQUEST_PARTY_ACCEPT_SUCCESS){
			try {
				NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new NotifyUsersOfPartyCommand(NetworkManager.getManager().getPartyManager().getPartyGroupByID(partyID)));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	
	}

	private void sendResult(){
		clientThread.getOutput().println(LobbyOPCode.OP_ACCEPTREQUEST);
		clientThread.getOutput().println(result);
		clientThread.getOutput().println(partyID);
	}

	public void undo() {
		
	}


}
