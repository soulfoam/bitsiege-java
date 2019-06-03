package soulfoam.bitsiegemainserver.main.party;

import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.logs.LogType;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMaker;
import soulfoam.bitsiegemainserver.main.notify.NotifyUserOfPartyRequestCommand;

public class PartyInviteCommand extends Command{
	
	private ClientThread clientThread;
	private int friendID;
	private int result = -1;
	
	public PartyInviteCommand(ClientThread clientThread, int friendID){
		this.clientThread = clientThread;
		this.friendID = friendID;
	}
	
	public boolean execute() {

		if (NetworkManager.getManager().getPartyManager().getInviteFromSenderIDAndReceiverID(clientThread.getUserAccount().getID(), friendID) != null){
			result = LobbyReturnCode.PARTY_INVITE_INVITEALREADYEXISTS;
			sendResult();
			return false;
		}
		
		if (!NetworkManager.getManager().getFriendManager().isFriends(clientThread.getUserAccount().getID(), friendID)){
			result = LobbyReturnCode.PARTY_INVITE_YOUARENOTFRIENDS;
			sendResult();
			return false;
		}
		
		ClientThread fct = NetworkManager.getManager().getActiveAccountManager().getClientFromAccountID(friendID);
		if (fct == null || fct != null && !fct.getUserAccount().isLoggedIn()){
			result = LobbyReturnCode.PARTY_INVITE_USEROFFLINE;
			sendResult();
			return false;
		}

		if (clientThread.getParty().getPartyLeader().getUserAccount().getID() != clientThread.getUserAccount().getID() && !clientThread.getParty().canAnyoneInvite()){
			result = LobbyReturnCode.PARTY_INVITE_NOPERMISSION;
			sendResult();
			return false;
		}

		if (clientThread.getParty().isUserInParty(friendID)){
			result = LobbyReturnCode.PARTY_INVITE_USERALREADYINPARTY;
			sendResult();
			return false;
		}
		
		NetworkManager.getManager().getPartyManager().getPartyInvites().add(new PartyInvite(clientThread.getParty().getID(), clientThread.getUserAccount().getID(), friendID));
		
		result = LobbyReturnCode.PARTY_INVITE_SUCCESS;
		sendResult();

		if (result == LobbyReturnCode.PARTY_INVITE_SUCCESS){
			try {
				NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new NotifyUserOfPartyRequestCommand(NetworkManager.getManager().getPartyManager().getInviteFromSenderIDAndReceiverID(clientThread.getUserAccount().getID(), friendID)));
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
		
		return true;
	
	}

	private void sendResult(){
		clientThread.getOutput().println(LobbyOPCode.OP_PARTYINVITE);
		clientThread.getOutput().println(result);
	}

	public void undo() {
		
	}


}
