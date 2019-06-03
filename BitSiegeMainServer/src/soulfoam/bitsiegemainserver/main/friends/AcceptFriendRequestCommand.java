package soulfoam.bitsiegemainserver.main.friends;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import soulfoam.arenashared.main.lobbyopcode.ConnectionStatus;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyopcode.RequestType;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.db.Database;
import soulfoam.bitsiegemainserver.main.notify.NotifySenderUserAcceptedFriendRequestCommand;
import soulfoam.bitsiegemainserver.main.notify.NotifyUserOfFriendRequestCommand;
import soulfoam.bitsiegemainserver.main.notify.NotifyUsersOfPartyCommand;

public class AcceptFriendRequestCommand extends Command{
	
	private ClientThread clientThread;
	private int requestID;
	private int senderID;
	private int result = -1;
	
	public AcceptFriendRequestCommand(ClientThread clientThread, int requestID, int senderID){
		this.clientThread = clientThread;
		this.requestID = requestID;
		this.senderID = senderID;
	}
	
	public boolean execute() {
		
		result = NetworkManager.getManager().getFriendManager().acceptFriendRequest(requestID, clientThread.getUserAccount().getID());
		clientThread.getOutput().println(result);
		
		if (result == LobbyReturnCode.REQUEST_FRIEND_ACCEPT_SUCCESS){
			// send info about new friend
			clientThread.getOutput().println(requestID);
			clientThread.getOutput().println(NetworkManager.getManager().getFriendManager().getNewlyAddedFriendInfo(senderID));
			try {
				NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new NotifySenderUserAcceptedFriendRequestCommand(clientThread, senderID));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
     
		
		return true;
	
	}


	public void undo() {
		
	}


}
