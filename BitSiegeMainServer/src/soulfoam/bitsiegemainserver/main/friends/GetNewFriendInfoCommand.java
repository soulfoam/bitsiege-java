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
import soulfoam.bitsiegemainserver.main.notify.NotifyUserOfFriendRequestCommand;
import soulfoam.bitsiegemainserver.main.notify.NotifyUsersOfPartyCommand;

public class GetNewFriendInfoCommand extends Command{
	
	private ClientThread clientThread;
	private int friendID;
	private int result = -1;
	
	public GetNewFriendInfoCommand(ClientThread clientThread, int friendID){
		this.clientThread = clientThread;
		this.friendID = friendID;
	}
	
	public boolean execute() {
		
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
     
		
		return true;
	
	}

	private void sendResult(){
		clientThread.getOutput().println(LobbyOPCode.OP_FRIENDADD);
		clientThread.getOutput().println(result);
	}

	public void undo() {
		
	}


}
