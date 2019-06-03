package soulfoam.bitsiegemainserver.main.notify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.GameServerThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.db.Database;
import soulfoam.bitsiegemainserver.main.logs.LogType;
import soulfoam.bitsiegemainserver.main.matchmaking.MatchMaker;
import soulfoam.bitsiegemainserver.main.party.PartyGroup;
import soulfoam.bitsiegemainserver.main.party.PartyInvite;

public class NotifyFriendsOfConnectionChangeCommand extends Command{
	
	private ClientThread clientThread;
	private int connectionStatus;
	
	public NotifyFriendsOfConnectionChangeCommand(ClientThread clientThread){
		this.clientThread = clientThread;
		this.connectionStatus = clientThread.getUserAccount().getConnectionStatus();
	}
	
	public NotifyFriendsOfConnectionChangeCommand(ClientThread clientThread, int connectionStatus){
		this.clientThread = clientThread;
		this.connectionStatus = connectionStatus;
	}
	
	public boolean execute() {
		
     	try {
     		
			Connection conn = Database.getConnection();
			
			String query = "SELECT * FROM friends WHERE accountid = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, clientThread.getUserAccount().getID());
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()){

				ClientThread ct = NetworkManager.getManager().getActiveAccountManager().getClientFromAccountID(rs.getInt("friendid"));
				if (ct != null){
					ct.getOutput().println(LobbyOPCode.OP_LIVEUPDATE_FRIENDSTATUSCHANGE);
					ct.getOutput().println(clientThread.getUserAccount().getID() + "," + connectionStatus);
				}

			}


			rs.close();
			stmt.close();
			conn.close();
     	} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	
	}


	public void undo() {
		
	}


}