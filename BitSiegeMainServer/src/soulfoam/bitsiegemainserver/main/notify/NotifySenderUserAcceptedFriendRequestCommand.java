package soulfoam.bitsiegemainserver.main.notify;

import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.logs.LogType;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMaker;
import soulfoam.bitsiegemainserver.main.party.PartyGroup;
import soulfoam.bitsiegemainserver.main.party.PartyInvite;

public class NotifySenderUserAcceptedFriendRequestCommand extends Command{
	
	private ClientThread clientThread;
	private int senderID;
	
	public NotifySenderUserAcceptedFriendRequestCommand(ClientThread clientThread, int senderID){
		this.clientThread = clientThread;
		this.senderID = senderID;
	}
	
	public boolean execute() {
		
		ClientThread ct = NetworkManager.getManager().getActiveAccountManager().getClientFromAccountID(senderID);
		if (ct != null){
			ct.getOutput().println(LobbyOPCode.OP_LIVEUPDATE_NEWFRIEND);
			ct.getOutput().println(NetworkManager.getManager().getFriendManager().getNewlyAddedFriendInfo(clientThread.getUserAccount().getID()));
		}
		
		return true;
	
	}


	public void undo() {
		
	}


}