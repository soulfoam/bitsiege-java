package soulfoam.bitsiegemainserver.main.accounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.command.Command;
import soulfoam.bitsiegemainserver.main.db.Database;

public class BanCommand extends Command{
	
	private ClientThread clientThread;
	private int hours;
	private String reason = "";
	private String ip = "";
	private int accountID = -1;
	
	public BanCommand(ClientThread clientThread, int hours, String reason){
		this.clientThread = clientThread;
		this.accountID = clientThread.getUserAccount().getID();
		this.hours = hours;
		this.reason = reason;
	}
	
	public BanCommand(int accountID, int hours, String reason){
		this.accountID = accountID;
		this.hours = hours;
		this.reason = reason;
	}
	
	public BanCommand(String ip, int hours, String reason){
		this.hours = hours;
		this.reason = reason;
		this.ip = ip;
	}
	
	public BanCommand(ClientThread clientThread, int hours, String reason, boolean ipBan){
		this.clientThread = clientThread;
		this.accountID = clientThread.getUserAccount().getID();
		this.hours = hours;
		this.reason = reason;
		this.ip = clientThread.getSocket().getInetAddress().getHostAddress();
	}
	
	public boolean execute() {
		
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(new Date());
	    calendar.add(Calendar.HOUR, hours);
	    Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
	    
		if (ip.isEmpty()){
	    	try {
	    	    
				Connection conn = Database.getConnection();
				String banquery = "INSERT into bannedaccounts (accountid, expiredate, reason)" 
				+ " values (?, ?, ?)";
				
				PreparedStatement banstmt = conn.prepareStatement(banquery);
				banstmt.setInt(1, accountID);
				banstmt.setTimestamp(2, timestamp);
				banstmt.setString(3, reason);
				
				banstmt.execute();
				
				banstmt.close();
				conn.close();
			
	    	} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		else{
	    	try {
	    	    
				Connection conn = Database.getConnection();
				String banquery = "INSERT into bannedips (ip, expiredate, reason)" 
				+ " values (?, ?, ?)";
				
				PreparedStatement banstmt = conn.prepareStatement(banquery);
				banstmt.setString(1, ip);
				banstmt.setTimestamp(2, timestamp);
				banstmt.setString(3, reason);
				
				banstmt.execute();
				
				banstmt.close();
				conn.close();
			
	    	} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		
		if (clientThread != null){
			clientThread.getOutput().println(LobbyOPCode.OP_BANNED);
			clientThread.logOutAccount();
		}
		
		return true;
	}

	public void undo() {
		
	}


}
