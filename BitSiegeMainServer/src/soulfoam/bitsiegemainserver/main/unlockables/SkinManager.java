package soulfoam.bitsiegemainserver.main.unlockables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import soulfoam.arenashared.main.entityinfo.SkinInfo;
import soulfoam.bitsiegemainserver.main.db.Database;

public class SkinManager {

    public String getUnlockedSkins(int accountID){
    	
    	String returnString = "";
    	
    	try {
			Connection conn = Database.getConnection();
			
			String query = "SELECT * from unlockedskins WHERE accountid = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, accountID);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()){
				returnString += rs.getInt("challengerid") + "," + rs.getInt("skinid") + "]";
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	return returnString;
    	
    }
    
    public ArrayList<SkinInfo> getUnlockedSkinsList(int accountID){
    	
    	ArrayList<SkinInfo> returnList = new ArrayList<SkinInfo>();
    	
    	try {
			Connection conn = Database.getConnection();
			
			String query = "SELECT * from unlockedskins WHERE accountid = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, accountID);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()){
				returnList.add(new SkinInfo(rs.getInt("challengerid"), rs.getInt("skinid")));
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	return returnList;
    	
    }
	
	public void unlockSkin(int skinID, int challengerID, int accountID){
		
    	try {
    		
			Connection conn = Database.getConnection();
			String skinquery = "INSERT into unlockedskins (accountid, skinid, challengerid)" 
			+ " values (?, ?, ?)";
			
			PreparedStatement skinstmt = conn.prepareStatement(skinquery);
			skinstmt.setInt(1, accountID);
			skinstmt.setInt(2, skinID);
			skinstmt.setInt(3, challengerID);
			
			skinstmt.execute();
			
			skinstmt.close();
			conn.close();
		
    	} catch (SQLException e) {

			e.printStackTrace();
		}
    	
	}
	
}
