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

public class AddFriendCommand extends Command{
	
	private ClientThread clientThread;
	private String friendUsername = "";
	private int result = -1;
	
	public AddFriendCommand(ClientThread clientThread, String friendUsername){
		this.clientThread = clientThread;
		this.friendUsername = friendUsername;
	}
	
	public boolean execute() {
		
		int receiverID = NetworkManager.getManager().getAccountManager().getAccountIDFromUsername(friendUsername);
		int senderID = clientThread.getUserAccount().getID();
		
		if (!NetworkManager.getManager().getAccountManager().accountExists(friendUsername)){
			result = LobbyReturnCode.FRIEND_ADD_USERDOESNOTEXIST;
			sendResult();
			return false;
		}
		
		if (NetworkManager.getManager().getFriendManager().hasExisitingRequest(senderID, receiverID) || NetworkManager.getManager().getFriendManager().hasExisitingRequest(receiverID, senderID)){
			result = LobbyReturnCode.FRIEND_ADD_REQUESTALREADYSENT;
			sendResult();
			return false;
		}
		
		if (NetworkManager.getManager().getFriendManager().isFriends(senderID, receiverID)){
			result = LobbyReturnCode.FRIEND_ADD_ALREADYFRIENDS;
			sendResult();
			return false;
		}
		
		if (senderID == receiverID){
			result = LobbyReturnCode.FRIEND_ADD_USERISYOU;
			sendResult();
			return false;
		}
		
		try {
    
			Connection conn = Database.getConnection();
			
			String query = "INSERT into requests (type, senderid, receiverid)" 
			+ " values (?, ?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, RequestType.FRIEND_REQUEST);
			stmt.setInt(2, senderID);
			stmt.setInt(3, receiverID);
			stmt.execute();
			
			stmt.close();
			conn.close();

			
		} catch (SQLException e) {

			e.printStackTrace();
		}

		result = LobbyReturnCode.FRIEND_ADD_SUCCESS;

		if (result == LobbyReturnCode.FRIEND_ADD_SUCCESS){
			int friendAccountID = NetworkManager.getManager().getAccountManager().getAccountIDFromUsername(friendUsername);
			try {
				NetworkManager.getManager().getCommandHandler().getCommandQueue().put(new NotifyUserOfFriendRequestCommand(clientThread, friendAccountID));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		sendResult();
		return true;
	
	}

	private void sendResult(){
		clientThread.getOutput().println(LobbyOPCode.OP_FRIENDADD);
		clientThread.getOutput().println(result);
	}

	public void undo() {
		
	}


}
