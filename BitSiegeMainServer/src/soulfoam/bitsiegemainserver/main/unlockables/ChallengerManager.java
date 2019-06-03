package soulfoam.bitsiegemainserver.main.unlockables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import soulfoam.arenashared.main.entityinfo.ChallengerInfo;
import soulfoam.bitsiegemainserver.main.db.Database;

public class ChallengerManager {

    public String getUnlockedChallengers(int accountID){

    	String returnString = "";
    	
    	try {
			Connection conn = Database.getConnection();
			
			String query = "SELECT * from unlockedchallengers WHERE accountid = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, accountID);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()){
				returnString += rs.getInt("challengerid") + ",";
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	return returnString;
    	
    }
    
    public ArrayList<ChallengerInfo> getUnlockedChallengersList(int accountID){

    	ArrayList<ChallengerInfo> returnList = new ArrayList<ChallengerInfo>();
    	
    	try {
			Connection conn = Database.getConnection();
			
			String query = "SELECT * from unlockedchallengers WHERE accountid = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, accountID);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()){
				returnList.add(new ChallengerInfo(rs.getInt("challengerid")));
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

    	return returnList;
    	
    }
    
	public void unlockChallenger(int challengerID, int accountID){
		
    	try {
    		
			Connection conn = Database.getConnection();
			String challengerquery = "INSERT into unlockedchallengers (accountid, challengerid)" 
			+ " values (?, ?)";
			
			PreparedStatement challengerstmt = conn.prepareStatement(challengerquery);
			challengerstmt.setInt(1, accountID);
			challengerstmt.setInt(2, challengerID);
			
			challengerstmt.execute();
			
			challengerstmt.close();
			conn.close();
		
    	} catch (SQLException e) {

			e.printStackTrace();
		}
    	
	}
	
}
