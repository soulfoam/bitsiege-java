package soulfoam.bitsiegemainserver.main.avatars;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.db.Database;
import soulfoam.bitsiegemainserver.main.logs.LogType;

public class AvatarManager {

	public void unlockAvatar(int id, int slot, int accountID){
		
    	try {
    		
		Connection conn = Database.getConnection();
		String avatarquery = "INSERT into unlockedavatars (accountid, avatarid, slot)" 
		+ " values (?, ?, ?)";
		
		PreparedStatement avatarstmt = conn.prepareStatement(avatarquery);
		avatarstmt.setInt(1, accountID);
		avatarstmt.setInt(2, id);
		avatarstmt.setInt(3, slot);
		
		avatarstmt.execute();
		
		avatarstmt.close();
		conn.close();
		
    	} catch (SQLException e) {

			e.printStackTrace();
		}
    	
	}
	
	public int changeAvatar(int id, int slot, int accountID){
		
		if (!hasAvatarUnlocked(id, slot, accountID)){
			NetworkManager.getManager().getLogManager().addLog(LogType.AVATAR_CHANGE_NOTUNLOCKED, "Account ID: " + accountID + " tried changing to: " + id + " on slot: " + slot + " but doesn't have it unlocked.");
			return LobbyReturnCode.AVATAR_CHANGE_NOTUNLOCKED;
		}
		
    	try {
    		
			Connection conn = Database.getConnection();
			
			String updatequery = "";
			
			if (slot == AvatarLibrary.SLOT_BACKGROUND){
				updatequery = "UPDATE accounts SET backgroundid = ? WHERE id = ?";
			}
			if (slot == AvatarLibrary.SLOT_BORDER){
				updatequery = "UPDATE accounts SET borderid = ? WHERE id = ?";
			}
			if (slot == AvatarLibrary.SLOT_ICON){
				updatequery = "UPDATE accounts SET iconid = ? WHERE id = ?";
			}
			
			PreparedStatement updatestmt = conn.prepareStatement(updatequery);
			updatestmt.setInt(1, id);
			updatestmt.setInt(2, accountID);
			
			updatestmt.execute();
			
			updatestmt.close();
			conn.close();
			
    	}catch (SQLException e) {

			e.printStackTrace();
		}
    	
		return LobbyReturnCode.AVATAR_CHANGE_SUCCESS;
	}
	
	public boolean hasAvatarUnlocked(int id, int slot, int accountID){
		
		boolean exists = false;
		
    	try {
			Connection conn = Database.getConnection();
			
			String query = "SELECT count(*) from unlockedavatars WHERE accountid = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, accountID);
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
	
	public String getUnlockedAvatars(int accountID){
		
		String returnString = "";
		String bgUnlocked = "";
		String borderUnlocked = "";
		String iconUnlocked = "";
		
		ArrayList<AvatarPair> avatarList = new ArrayList<AvatarPair>();
		
		try{
			
			Connection conn = Database.getConnection();
			
			String query = "SELECT * FROM unlockedavatars WHERE accountid = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, accountID);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()){
				avatarList.add(new AvatarPair(rs.getInt("avatarid"), rs.getInt("slot")));
			}
			
			for (AvatarPair av : avatarList){
				if (av.getSlot() == AvatarLibrary.SLOT_BACKGROUND){
					bgUnlocked += av.getID() + ",";
				}
				if (av.getSlot() == AvatarLibrary.SLOT_BORDER){
					borderUnlocked += av.getID() + ",";
				}
				if (av.getSlot() == AvatarLibrary.SLOT_ICON){
					iconUnlocked += av.getID() + ",";
				}
			}
			
			returnString = bgUnlocked + "]" + borderUnlocked + "]" + iconUnlocked;
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return returnString;
	}
	
	public ArrayList<AvatarPair> getUnlockedAvatarsList(int accountID){
		
		ArrayList<AvatarPair> avatarList = new ArrayList<AvatarPair>();
		
		try{
			
			Connection conn = Database.getConnection();
			
			String query = "SELECT * FROM unlockedavatars WHERE accountid = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, accountID);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()){
				avatarList.add(new AvatarPair(rs.getInt("avatarid"), rs.getInt("slot")));
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return avatarList;
	}
	
}
