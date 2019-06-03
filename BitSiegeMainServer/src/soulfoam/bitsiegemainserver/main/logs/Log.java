package soulfoam.bitsiegemainserver.main.logs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.bitsiegemainserver.main.db.Database;

public class Log {
	
	private int type;
	private String text;
	private String ipAddress;
	
	public Log(int type, String text, String ipAddress){
		this.type = type;
		this.text = text;
		this.ipAddress = ipAddress;
	}
	
	public Log(int type, String text){
		this.type = type;
		this.text = text;
		this.ipAddress = "localhost";
	}

	public int getType() {
		return type;
	}
	
	public String getText() {
		return text;
	}
	
	public void execute(){
      	try {
    			Connection conn = Database.getConnection();
    			
    			String logquery = "INSERT into logs (text, type, ip) values (?, ?, ?)";
    					
    			PreparedStatement logstmt = conn.prepareStatement(logquery);
    			logstmt.setString(1, getText());
    			logstmt.setInt(2, getType());
    			logstmt.setString(3, getIPAddress());
    			logstmt.execute();
    			
    			logstmt.close();
    			conn.close();
    			
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}

	}

	public String getIPAddress() {
		return ipAddress;
	}

}
