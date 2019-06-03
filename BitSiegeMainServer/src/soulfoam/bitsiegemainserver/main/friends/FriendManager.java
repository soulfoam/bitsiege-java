package soulfoam.bitsiegemainserver.main.friends;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import soulfoam.arenashared.main.lobbyopcode.ConnectionStatus;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyopcode.RequestType;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.db.Database;

public class FriendManager {
	
	public String getUserInfo(String username){
		
		String returnString = "";

     	try {
     		
			Connection conn = Database.getConnection();
			
			String query = "SELECT * FROM accounts WHERE username = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				returnString = rs.getString("username") + "," + rs.getInt("backgroundid") + "," + rs.getInt("borderid") + "," + rs.getInt("iconid") + "," + rs.getTimestamp("creationdate").getTime()
				+ "," + rs.getTimestamp("lastlogindate").getTime() + "," + rs.getString("namecolor");
			}


			rs.close();
			stmt.close();
			conn.close();
			
     	} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return returnString;
		
	}

	public String getNewlyAddedFriendInfo(int friendID){
		
		String returnString = "";
		
     	try {
     		
			Connection conn = Database.getConnection();
	
			String accountQuery = "SELECT * FROM accounts WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(accountQuery);
			stmt.setInt(1, friendID);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				
				int connectionStatus = ConnectionStatus.OFFLINE;
				ClientThread ct = NetworkManager.getManager().getActiveAccountManager().getClientFromAccountID(rs.getInt("id"));
				if (ct != null){
					connectionStatus = ct.getUserAccount().getDisplayConnectionStatus();
				}
				
				String[] nameColor = rs.getString("namecolor").split(",");
				returnString += rs.getInt("id") + "," + rs.getString("username") 
				+ "," + rs.getInt("backgroundid") + "," + rs.getInt("borderid") + "," + rs.getInt("iconid") 
				+ "," + nameColor[0] + "," + nameColor[1] + "," + nameColor[2]
				+ "," + connectionStatus
				+ "]";
			}
	

			rs.close();
			stmt.close();
			conn.close();
     	} catch (SQLException e) {
			e.printStackTrace();
		}
     	
		
		return returnString;
	}
	
	public boolean isFriends(int accountID, int friendID){
		
		boolean friends = false;
		
    	try {
			Connection conn = Database.getConnection();
			
			String query = "SELECT count(*) from friends WHERE accountid = ? AND friendid = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, accountID);
			stmt.setInt(2, friendID);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				int count = rs.getInt(1);
				if (count >= 1){
					friends = true;
				}
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
		return friends;	
	}
	
	public boolean hasExisitingRequest(int senderID, int receiverID){
		boolean exists = false;
		
    	try {
			Connection conn = Database.getConnection();
			
			String query = "SELECT count(*) from requests WHERE senderid = ? AND receiverid = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, senderID);
			stmt.setInt(2, receiverID);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				int count = rs.getInt(1);
				if (count >= 1){
					exists = true;
				}
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
		return exists;	
	}
	
	public String getFriends(int accountID){
		String returnString = "";
		
     	try {
     		
			Connection conn = Database.getConnection();
			
			String query = "SELECT * FROM friends WHERE accountid = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, accountID);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()){
    			String accountQuery = "SELECT * FROM accounts WHERE id = ?";
    			
    			PreparedStatement accountstmt = conn.prepareStatement(accountQuery);
    			accountstmt.setInt(1, rs.getInt("friendid"));
    			
    			ResultSet accountrs = accountstmt.executeQuery();
    			
    			if (accountrs.next()){
    				int connectionStatus = ConnectionStatus.OFFLINE;
    				ClientThread ct = NetworkManager.getManager().getActiveAccountManager().getClientFromAccountID(accountrs.getInt("id"));
    				if (ct != null){
    					connectionStatus = ct.getUserAccount().getDisplayConnectionStatus();
    				}
    				String[] nameColor = accountrs.getString("namecolor").split(",");
    				returnString += accountrs.getInt("id") + "," + accountrs.getString("username") 
    				+ "," + accountrs.getInt("backgroundid") + "," + accountrs.getInt("borderid") + "," + accountrs.getInt("iconid") 
    				+ "," + nameColor[0] + "," + nameColor[1] + "," + nameColor[2]
    				+ "," + connectionStatus
    				+ "]";
    			}
				
    			accountrs.close();
    			accountstmt.close();
			}


			rs.close();
			stmt.close();
			conn.close();
     	} catch (SQLException e) {
			e.printStackTrace();
		}
     	
		
		return returnString;
	}
	
	public String getFriendRequest(int senderID, int friendID){
		
		String returnString = "";

     	try {
     		
			Connection conn = Database.getConnection();
				
			String senderQuery = "SELECT * FROM accounts WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(senderQuery);
			stmt.setInt(1, friendID);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				String[] nameColor = rs.getString("namecolor").split(",");
				returnString += getFriendRequestIDFromUserPair(friendID, senderID) + "," + rs.getString("username") 
				+ "," + rs.getInt("backgroundid") + "," + rs.getInt("borderid") + "," + rs.getInt("iconid") 
				+ "," + nameColor[0] + "," + nameColor[1] + "," + nameColor[2]
				+ "]";
			}
			
			rs.close();
			stmt.close();
			conn.close();
     	} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return returnString;
	}
	
	public String getFriendRequests(int accountID){
		
		String returnString = "";

     	try {
     		
			Connection conn = Database.getConnection();
			
			String query = "SELECT * FROM requests WHERE receiverid = ? AND type = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, accountID);
			stmt.setInt(2, RequestType.FRIEND_REQUEST);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()){
				
    			String senderQuery = "SELECT * FROM accounts WHERE id = ?";
    			
    			PreparedStatement senderstmt = conn.prepareStatement(senderQuery);
    			senderstmt.setInt(1, rs.getInt("senderid"));
    			
    			ResultSet senderrs = senderstmt.executeQuery();
    			
    			if (senderrs.next()){
    				String[] nameColor = senderrs.getString("namecolor").split(",");
    				returnString += rs.getInt("id") + "," + senderrs.getString("username") 
    				+ "," + senderrs.getInt("backgroundid") + "," + senderrs.getInt("borderid") + "," + senderrs.getInt("iconid") 
    				+ "," + nameColor[0] + "," + nameColor[1] + "," + nameColor[2]
    				+ "]";
    			}
				
    			senderrs.close();
    			senderstmt.close();
			}


			rs.close();
			stmt.close();
			conn.close();
     	} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
     	return returnString;
	}
	
	public int acceptFriendRequest(int requestID, int receiverID){
		
     	try {
     		
     		int senderID = getSenderIDFromRequestID(requestID);
     		
     		if (senderID == -1){
     			return LobbyReturnCode.REQUEST_FRIEND_REQUESTDOESNTEXIST;
     		}
     		
			Connection conn = Database.getConnection();
			
			String query = "INSERT into friends (accountid, friendid)" 
					+ " values (?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, receiverID);
			stmt.setInt(2, senderID);
			stmt.execute();
			stmt.close();
			
			PreparedStatement stmt2 = conn.prepareStatement(query);
			stmt2.setInt(1, senderID);
			stmt2.setInt(2, receiverID);
			stmt2.execute();
			stmt2.close();
			
			conn.close();
			
			deleteFriendRequest(requestID, receiverID);
			
			return LobbyReturnCode.REQUEST_FRIEND_ACCEPT_SUCCESS;
     		
     		
     	}catch (SQLException e) {
			e.printStackTrace();
		}
     	
     	return LobbyReturnCode.REQUEST_FRIEND_REQUESTDOESNTEXIST;
     	
	}

	public int deleteFriendRequest(int requestID, int receiverID){	
     	try {
     		
			Connection conn = Database.getConnection();
			
			String query = "DELETE FROM requests WHERE id = ? AND receiverid = ? AND type = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);

			stmt.setInt(1, requestID);
			stmt.setInt(2, receiverID);
			stmt.setInt(3, RequestType.FRIEND_REQUEST);
			stmt.execute();
			
			stmt.close();
			conn.close();
     	} catch (SQLException e) {
			e.printStackTrace();
		}
     	
     	return LobbyReturnCode.REQUEST_FRIEND_DECLINE_SUCCESS;
	}
	
	public int getSenderIDFromRequestID(int requestID){
		
		int returnID = -1;
		
		try{
	
			Connection conn = Database.getConnection();
			
			String query = "SELECT * FROM requests WHERE id = ? AND type = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, requestID);
			stmt.setInt(2, RequestType.FRIEND_REQUEST);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				returnID = rs.getInt("senderid");
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnID;
	}
	
	public int getFriendRequestIDFromUserPair(int senderID, int friendID){
		
		int returnID = -1;
		
		try{
	
			Connection conn = Database.getConnection();
			
			String query = "SELECT * FROM requests WHERE senderid = ? AND receiverid = ? AND type = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, senderID);
			stmt.setInt(2, friendID);
			stmt.setInt(3, RequestType.FRIEND_REQUEST);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				returnID = rs.getInt("id");
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnID;
		
	}

}
