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

public class DeleteFriendRequestCommand extends Command{
	
	private ClientThread clientThread;
	private int requestID;
	private int result = -1;
	
	public DeleteFriendRequestCommand(ClientThread clientThread, int requestID){
		this.clientThread = clientThread;
		this.requestID = requestID;
	}
	
	public boolean execute() {
		
		result = NetworkManager.getManager().getFriendManager().deleteFriendRequest(requestID, clientThread.getUserAccount().getID());
		clientThread.getOutput().println(result);
		if (result == LobbyReturnCode.REQUEST_FRIEND_DECLINE_SUCCESS){
			clientThread.getOutput().println(requestID);
		}
		
		return true;
	
	}

	public void undo() {
		
	}


}
