package soulfoam.bitsiegemainserver.main.notify;

import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.logs.LogType;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMaker;
import soulfoam.bitsiegemainserver.main.party.PartyGroup;
import soulfoam.bitsiegemainserver.main.party.PartyInvite;

public class NotifyUserOfFriendRequestCommand extends Command{
	
	private ClientThread clientThread;
	private int friendID;
	
	public NotifyUserOfFriendRequestCommand(ClientThread clientThread, int friendID){
		this.clientThread = clientThread;
		this.friendID = friendID;
	}
	
	public boolean execute() {
		
		ClientThread ct = NetworkManager.getManager().getActiveAccountManager().getClientFromAccountID(friendID);
		if (ct != null){
			ct.getOutput().println(LobbyOPCode.OP_LIVEUPDATE_NEWFRIENDREQUEST);
			ct.getOutput().println(NetworkManager.getManager().getFriendManager().getFriendRequest(friendID, clientThread.getUserAccount().getID()));
		}
		
		
		return true;
	
	}


	public void undo() {
		
	}


}