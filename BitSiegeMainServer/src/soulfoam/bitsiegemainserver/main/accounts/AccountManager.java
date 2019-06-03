package soulfoam.bitsiegemainserver.main.accounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.mysql.jdbc.Statement;

import soulfoam.arenashared.main.account.AccountValues;
import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.ConnectionStatus;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.lobbyutil.LobbyUtil;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.db.Database;
import soulfoam.bitsiegemainserver.main.logs.LogType;

public class AccountManager {
	
    public int createAccount(String username, String password, String confirmedPassword, String email, String ipAddress){
    	
    	username = username.trim();
    	password = password.trim();
    	confirmedPassword = confirmedPassword.trim();
    	email = email.trim();
    	ipAddress = ipAddress.trim();
    	
    	if (accountExists(username)){
    		return LobbyReturnCode.ACCOUNT_CREATE_USEREXISTS;
    	}
    	
    	if (!password.equals(confirmedPassword)){
    		return LobbyReturnCode.ACCOUNT_CREATE_PASSWORDSDONTMATCH;
    	}
    	
    	if (username.length() > AccountValues.MAX_USERNAME_LENGTH){
    		return LobbyReturnCode.ACCOUNT_CREATE_USERNAMETOOLONG;
    	}
    	
    	if (username.isEmpty()) {
    		return LobbyReturnCode.ACCOUNT_CREATE_USERNAMEEMPTY;
    	}
    	
    	if (!username.matches("[a-zA-Z0-9]*")) {
    		return LobbyReturnCode.ACCOUNT_CREATE_USERNAMECONTAINSSPECIALCHARACTERS;
    	}
    	
    	if (password.length() < 6){
    		return LobbyReturnCode.ACCOUNT_CREATE_PASSWORDTOOSHORT;
    	}
    	
    	if (!AccountValues.isValidEmail(email)){
    		return LobbyReturnCode.ACCOUNT_CREATE_INVALIDEMAIL;
    	}
    	
    	if (!isIPBanned(ipAddress).isEmpty()){
    		return LobbyReturnCode.ACCOUNT_CREATE_BANNED;
    	}
    	
    	try {
    		
    		password = LobbyUtil.sha256(password);
    		
			Connection conn = Database.getConnection();
			
			Date date = new Date();
			Timestamp timestamp = new Timestamp(date.getTime());
			
			String query = "INSERT into accounts (username, password, email, creationdate, lastlogindate, lastloginip, siegepoints, bits, level, exp, targetexp, namecolor, backgroundid, borderid, iconid, invisible)" 
			+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.setString(3, email);
			stmt.setObject(4, timestamp);
			stmt.setObject(5, timestamp);
			stmt.setString(6, ipAddress);
			stmt.setInt(7, 0);
			stmt.setInt(8, 0);
			stmt.setInt(9, 1);
			stmt.setInt(10, 0);
			stmt.setInt(11, 1000);
			stmt.setString(12, "255,255,255");
			stmt.setInt(13, 0);
			stmt.setInt(14, 0);
			stmt.setInt(15, 0);
			stmt.setInt(16, 0);
			stmt.execute();
			
			int newAccountID = -1;
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			newAccountID = rs.getInt(1);
			   
			stmt.close();
			conn.close();
			
			NetworkManager.getManager().getAvatarManager().unlockAvatar(AvatarLibrary.BACKGROUND_BLACK, AvatarLibrary.SLOT_BACKGROUND, newAccountID);
			NetworkManager.getManager().getAvatarManager().unlockAvatar(AvatarLibrary.BORDER_SILVER_3, AvatarLibrary.SLOT_BORDER, newAccountID);
			NetworkManager.getManager().getAvatarManager().unlockAvatar(AvatarLibrary.ICON_SILVER_SHIELD, AvatarLibrary.SLOT_ICON, newAccountID);
			
			NetworkManager.getManager().getAvatarManager().changeAvatar(AvatarLibrary.BACKGROUND_BLACK, AvatarLibrary.SLOT_BACKGROUND, newAccountID);
			NetworkManager.getManager().getAvatarManager().changeAvatar(AvatarLibrary.BORDER_SILVER_3, AvatarLibrary.SLOT_BORDER, newAccountID);
			NetworkManager.getManager().getAvatarManager().changeAvatar(AvatarLibrary.ICON_SILVER_SHIELD, AvatarLibrary.SLOT_ICON, newAccountID);
			
			NetworkManager.getManager().getChallengerManager().unlockChallenger(0, newAccountID);
			NetworkManager.getManager().getChallengerManager().unlockChallenger(1, newAccountID);
			NetworkManager.getManager().getChallengerManager().unlockChallenger(2, newAccountID);
			NetworkManager.getManager().getChallengerManager().unlockChallenger(3, newAccountID);
			
			NetworkManager.getManager().getSkinManager().unlockSkin(0, 0, newAccountID);
			NetworkManager.getManager().getSkinManager().unlockSkin(0, 1, newAccountID);
			NetworkManager.getManager().getSkinManager().unlockSkin(0, 2, newAccountID);
			NetworkManager.getManager().getSkinManager().unlockSkin(0, 3, newAccountID);
			
			NetworkManager.getManager().getUnderglowManager().unlockUnderglow(0, newAccountID);
			
		} catch (SQLException e) {

			e.printStackTrace();
		}
    	
    	
    	NetworkManager.getManager().getLogManager().addLog(LogType.REGISTRATION_SUCCESSFUL, username + " registered successfully.", ipAddress);
    	
    	return LobbyReturnCode.ACCOUNT_CREATE_SUCCESS;
    }
	    
    public int loadAccount(String username, String password, String ipAddress, MultiplayerAccount account){

    	username = username.trim();
    	password = password.trim();
    	ipAddress = ipAddress.trim();
    	
    	password = LobbyUtil.sha256(password);
    	
    	
    	if (!accountExists(username)){
	    	return LobbyReturnCode.ACCOUNT_LOGIN_USERDOESNTEXIST;
    	}

    	if (!isPasswordValid(password, getAccountIDFromUsername(username))){
			NetworkManager.getManager().getLogManager().addLog(LogType.LOGIN_WRONGPASSWORD, username + " attempted to login with the wrong password.", ipAddress);
    		return LobbyReturnCode.ACCOUNT_LOGIN_INVALIDPASSWORD;
    	}
    	
    	String ipBanned = isIPBanned(ipAddress);
    	if (!ipBanned.isEmpty()){
			account.setBannedExpireDate(ipBanned);
    		return LobbyReturnCode.ACCOUNT_LOGIN_BANNED_IP;
    	}
    		
    	String banned = isAccountBanned(getAccountIDFromUsername(username));
    	if (!banned.isEmpty()){
			account.setBannedExpireDate(banned);
			return LobbyReturnCode.ACCOUNT_LOGIN_BANNED;
    	}
    	
    	if (NetworkManager.getManager().getActiveAccountManager().isLoggedInFromUsername(username)){
			NetworkManager.getManager().getLogManager().addLog(LogType.LOGIN_WRONGPASSWORD, username + " attempted to login but is already logged in.", ipAddress);
			return LobbyReturnCode.ACCOUNT_LOGIN_ALREADYLOGGEDIN;
    	}
		
      	try {
      		
      			Connection conn = Database.getConnection();
    		
    			String query = "SELECT * FROM accounts WHERE username = ? AND password = ?";
    			
    			PreparedStatement stmt = conn.prepareStatement(query);
    			stmt.setString(1, username);
    			stmt.setString(2, password);
    			
    			ResultSet rs = stmt.executeQuery();
    			
    			if (rs.next()){
    				
    				account.setID(rs.getInt("id"));
    	    		account.setUsername(rs.getString("username"));
    	    		account.setPassword(rs.getString("password"));
    	    		account.setEmail(rs.getString("email"));
    	    		account.setCreationDate(rs.getTimestamp("creationdate"));
    	    		account.setLastLoginDate(rs.getTimestamp("lastlogindate"));
    	    		account.setLastLoginIP(rs.getString("lastloginip"));
    	    		account.setSiegePoints(rs.getInt("siegepoints"));
    	    		account.setBits(rs.getInt("bits"));
    	    		account.setLevel(rs.getInt("level"));
    	    		account.setEXP(rs.getInt("exp"));
    	    		account.setLoggedIn(true);
    	    		if (rs.getBoolean("invisible")){
    	    			account.setConnectionStatus(ConnectionStatus.INVISIBLE);
    	    		}
    	    		else{
    	    			account.setConnectionStatus(ConnectionStatus.ONLINE);
    	    		}
    	    		account.setNameColor(rs.getString("namecolor"));
    				account.setAvatarIDs(rs.getInt("backgroundid"), rs.getInt("borderid"), rs.getInt("iconid"));
    			}
    
    			
    			Date date = new Date();
    			Timestamp timestamp = new Timestamp(date.getTime());
    			
    			String updatequery = "UPDATE accounts SET lastlogindate = ?, lastloginip = ? WHERE id = ?";
    			
    			PreparedStatement updatestmt = conn.prepareStatement(updatequery);
    			updatestmt.setObject(1, timestamp);
    			updatestmt.setString(2, ipAddress);
    			updatestmt.setInt(3, account.getID());
    			
    			updatestmt.execute();
    			
   
    			rs.close();
    			updatestmt.close();
    			stmt.close();
    			conn.close();
    			
    			account.setUnlockedChallengers(NetworkManager.getManager().getChallengerManager().getUnlockedChallengersList(account.getID()));
    			account.setUnlockedSkins(NetworkManager.getManager().getSkinManager().getUnlockedSkinsList(account.getID()));
    			account.setUnlockedUnderglows(NetworkManager.getManager().getUnderglowManager().getUnlockedUnderglowsList(account.getID()));
    			account.setUnlockedAvatars(NetworkManager.getManager().getAvatarManager().getUnlockedAvatarsList(account.getID()));
    			
    			NetworkManager.getManager().getLogManager().addLog(LogType.LOGIN_SUCCESSFUL, account.getUsername() + " logged in successfully.", ipAddress);
    			
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
		
    	return LobbyReturnCode.ACCOUNT_LOGIN_SUCCESS;
    }
    
	public boolean accountExists(String username){
		
		username = username.trim();
		
		if (getAccountIDFromUsername(username) != -1){
			return true;
		}
		
		return false;
		
	}

	public boolean isPasswordValid(String password, int accountID){
		
		boolean correct = false;
		password = password.trim();
		
    	try {
			Connection conn = Database.getConnection();
			
			String query = "SELECT count(*) from accounts WHERE id = ? AND password = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, accountID);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				int count = rs.getInt(1);
				if (count >= 1){
					correct = true;
				}
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
		return correct;
	}
	
	public int changeAccountPassword(String currentPassword, String newPassword, String newConfirmedPassword, int accountID, String ipAddress){
		
		currentPassword = currentPassword.trim();
		newPassword = newPassword.trim();
		newConfirmedPassword.trim();
		ipAddress = ipAddress.trim();
		
    	if (!newPassword.equals(newConfirmedPassword)){
    		return LobbyReturnCode.ACCOUNT_CHANGEPASSWORD_PASSWORDSDONTMATCH;
    	}
    	
    	if (newPassword.length() < 6){
    		return LobbyReturnCode.ACCOUNT_CHANGEPASSWORD_PASSWORDTOOSHORT;
    	}
    	
    	if (!isPasswordValid(LobbyUtil.sha256(currentPassword), accountID)){
    		return LobbyReturnCode.ACCOUNT_CHANGEPASSWORD_CURRENTPASSWORDISWRONG;
    	}
    	
    	
		try{
			
			Connection conn = Database.getConnection();
			
			String updatequery = "UPDATE accounts SET password = ? WHERE id = ?";
			
			PreparedStatement updatestmt = conn.prepareStatement(updatequery);
			updatestmt.setString(1, LobbyUtil.sha256(newPassword));
			updatestmt.setInt(2, accountID);
			
			updatestmt.execute();
			
			updatestmt.close();
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	
    	NetworkManager.getManager().getLogManager().addLog(LogType.PASSWORD_CHANGE, "Account ID: " + accountID + " changed password from: " + LobbyUtil.sha256(currentPassword) + " to: " + LobbyUtil.sha256(newPassword), ipAddress);
    	
    	return LobbyReturnCode.ACCOUNT_CHANGEPASSWORD_SUCCESS;
    	
	}
	
	public int changeAccountEmail(String currentPassword, String newEmail, String newConfirmedEmail, int accountID, String ipAddress){
		
    	if (!newEmail.equalsIgnoreCase(newConfirmedEmail)){
    		return LobbyReturnCode.ACCOUNT_CHANGEEMAIL_EMAILSDONTMATCH;
    	}
    	
    	if (!isPasswordValid(LobbyUtil.sha256(currentPassword), accountID)){
    		return LobbyReturnCode.ACCOUNT_CHANGEEMAIL_CURRENTPASSWORDISWRONG;
    	}
    	
    	String currentEmail = getEmailFromAccountID(accountID);
    	
		try{
			
			Connection conn = Database.getConnection();
			
			String updatequery = "UPDATE accounts SET email = ? WHERE id = ?";
			
			PreparedStatement updatestmt = conn.prepareStatement(updatequery);
			updatestmt.setString(1, newEmail);
			updatestmt.setInt(2, accountID);
			
			updatestmt.execute();
			
			updatestmt.close();
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
		NetworkManager.getManager().getLogManager().addLog(LogType.EMAIL_CHANGE, "Account ID: " + accountID + " changed email from: " + currentEmail + " to: " + newEmail, ipAddress);
		
    	return LobbyReturnCode.ACCOUNT_CHANGEEMAIL_SUCCESS;
	}
	
	public void changeInvisibleStatus(int accountID, boolean value){
		try{
			
			Connection conn = Database.getConnection();
			
			String updatequery = "UPDATE accounts SET invisible = ? WHERE id = ?";
			
			PreparedStatement updatestmt = conn.prepareStatement(updatequery);
			updatestmt.setBoolean(1, value);
			updatestmt.setInt(2, accountID);
			
			updatestmt.execute();
			
			updatestmt.close();
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public String isAccountBanned(int accountID){
		
		String banned = "";
		
    	try {
			Connection conn = Database.getConnection();
			
			String query = "SELECT * FROM bannedaccounts WHERE accountid = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, accountID);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				banned = rs.getString("expiredate");
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
		return banned;
	}
	
	public String isIPBanned(String ip){
		
		String banned = "";
		ip = ip.trim();
		
    	try {
			Connection conn = Database.getConnection();
			
			String query = "SELECT * FROM bannedips WHERE ip = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, ip);
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				banned = rs.getString("expiredate");
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
		return banned.trim();
	}
	
	public String getEmailFromAccountID(int accountID){
		String email = "";
		try{
			
			Connection conn = Database.getConnection();
			
			String query = "SELECT * FROM accounts WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, accountID);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				email = rs.getString("email");
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return email.trim();
	}
	
	public int getAccountIDFromUsername(String username){
		
		int returnID = -1;
		username = username.trim();
		
		try{
	
			Connection conn = Database.getConnection();
			
			String query = "SELECT * FROM accounts WHERE username = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setString(1, username);
			
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
	
	public String getUsernameFromAccountID(int accountID){
		
		String username = "";
		
		try{
			
			Connection conn = Database.getConnection();
			
			String query = "SELECT * FROM accounts WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, accountID);
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				username = rs.getString("username");
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return username.trim();
	}
}
