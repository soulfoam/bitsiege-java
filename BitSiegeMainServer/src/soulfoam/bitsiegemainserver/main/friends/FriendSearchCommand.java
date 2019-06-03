package soulfoam.bitsiegemainserver.main.friends;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyopcode.RequestType;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.db.Database;
import soulfoam.bitsiegemainserver.main.notify.NotifyUserOfFriendRequestCommand;
import soulfoam.bitsiegemainserver.main.notify.NotifyUsersOfPartyCommand;

public class FriendSearchCommand extends Command{
	
	private ClientThread clientThread;
	private String friendUsername = "";
	
	public FriendSearchCommand(ClientThread clientThread, String friendUsername){
		this.clientThread = clientThread;
		this.friendUsername = friendUsername;
	}
	
	public boolean execute() {
		
		int result = NetworkManager.getManager().getAccountManager().accountExists(friendUsername) ? LobbyReturnCode.FRIEND_SEARCH_SUCCESS : LobbyReturnCode.FRIEND_SEARCH_USERDOESNOTEXIST;
		clientThread.getOutput().println(LobbyOPCode.OP_FRIENDSEARCH);
		clientThread.getOutput().println(result);
		if (result == LobbyReturnCode.FRIEND_SEARCH_SUCCESS){
			clientThread.getOutput().println(NetworkManager.getManager().getFriendManager().getUserInfo(friendUsername));
		}
	
		return true;
	
	}

	public void undo() {
		
	}


}
