package soulfoam.bitsiegemainserver.main.unlockables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import soulfoam.arenashared.main.entityinfo.UnderglowInfo;
import soulfoam.bitsiegemainserver.main.db.Database;

public class UnderglowManager {

    public String getUnlockedUnderglows(int accountID){
    	
    	String returnString = "";
    	
    	try {
			Connection conn = Database.getConnection();
			
			String query = "SELECT * from unlockedunderglows WHERE accountid = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, accountID);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()){
				returnString += rs.getInt("underglowid") + ",";
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	return returnString;
    	
    }
    
    public ArrayList<UnderglowInfo> getUnlockedUnderglowsList(int accountID){
    	
    	ArrayList<UnderglowInfo> returnList = new ArrayList<UnderglowInfo>();
    	
    	try {
			Connection conn = Database.getConnection();
			
			String query = "SELECT * from unlockedunderglows WHERE accountid = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, accountID);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()){
				returnList.add(new UnderglowInfo(rs.getInt("underglowid")));
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	return returnList;
    	
    }
    
	public void unlockUnderglow(int underglowID, int accountID){
		
    	try {
    		
			Connection conn = Database.getConnection();
			String underglowquery = "INSERT into unlockedunderglows (accountid, underglowid)" 
			+ " values (?, ?)";
			
			PreparedStatement underglowstmt = conn.prepareStatement(underglowquery);
			underglowstmt.setInt(1, accountID);
			underglowstmt.setInt(2, underglowID);
			
			underglowstmt.execute();
			
			underglowstmt.close();
			conn.close();
		
    	} catch (SQLException e) {

			e.printStackTrace();
		}
    	
	}
	
}
