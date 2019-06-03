package soulfoam.bitsiegemainserver.main.accounts;

import java.sql.Timestamp;
import java.util.ArrayList;

import soulfoam.arenashared.main.entityinfo.ChallengerInfo;
import soulfoam.arenashared.main.entityinfo.SkinInfo;
import soulfoam.arenashared.main.entityinfo.UnderglowInfo;
import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.ConnectionStatus;
import soulfoam.bitsiegemainserver.main.avatars.AvatarPair;

public class MultiplayerAccount {
	
	private int id = -1;
	private String username = "";
	private String password = "";
	private String email = "";
	private Timestamp creationDate;
	private String lastLoginIP = "";
	private Timestamp lastLoginDate;
	private int siegePoints;
	private int bits;
	private int level;
	private int exp;
	private boolean loggedIn;
	private int connectionStatus;
	private String nameColor;
	private int[] avatarIDs = new int[3];
	private String bannedExpireDate;
	private ArrayList<ChallengerInfo> unlockedChallengers = new ArrayList<ChallengerInfo>();
	private ArrayList<SkinInfo> unlockedSkins = new ArrayList<SkinInfo>();
	private ArrayList<UnderglowInfo> unlockedUnderglows = new ArrayList<UnderglowInfo>();
	private ArrayList<AvatarPair> unlockedAvatars = new ArrayList<AvatarPair>();
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	public Timestamp getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public int getSiegePoints() {
		return siegePoints;
	}

	public void setSiegePoints(int siegePoints) {
		this.siegePoints = siegePoints;
	}

	public int getBits() {
		return bits;
	}

	public void setBits(int bits) {
		this.bits = bits;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getEXP() {
		return exp;
	}

	public void setEXP(int exp) {
		this.exp = exp;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getNameColor() {
		return nameColor;
	}

	public void setNameColor(String nameColor) {
		this.nameColor = nameColor;
	}

	public int[] getAvatarIDs() {
		return avatarIDs;
	}

	public void setAvatarIDs(int bgID, int borderID, int iconID) {
		avatarIDs[AvatarLibrary.SLOT_BACKGROUND] = bgID;
		avatarIDs[AvatarLibrary.SLOT_BORDER] = borderID;
		avatarIDs[AvatarLibrary.SLOT_ICON] = iconID;
	}


	public String getBannedExpireDate() {
		return bannedExpireDate;
	}

	public void setBannedExpireDate(String bannedExpireDate) {
		this.bannedExpireDate = bannedExpireDate;
	}

	public int getDisplayConnectionStatus() {
		if (connectionStatus == ConnectionStatus.INVISIBLE){
			connectionStatus = ConnectionStatus.OFFLINE;
		}
		return connectionStatus;
	}

	public int getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(int connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.loggedIn = isLoggedIn;
	}

	public ArrayList<ChallengerInfo> getUnlockedChallengers() {
		return unlockedChallengers;
	}

	public ArrayList<SkinInfo> getUnlockedSkins() {
		return unlockedSkins;
	}

	public ArrayList<UnderglowInfo> getUnlockedUnderglows() {
		return unlockedUnderglows;
	}
	
	public ArrayList<AvatarPair> getUnlockedAvatars() {
		return unlockedAvatars;
	}


	public void setUnlockedChallengers(ArrayList<ChallengerInfo> unlockedChallengers) {
		this.unlockedChallengers = unlockedChallengers;
	}

	public void setUnlockedSkins(ArrayList<SkinInfo> unlockedSkins) {
		this.unlockedSkins = unlockedSkins;
	}

	public void setUnlockedUnderglows(ArrayList<UnderglowInfo> unlockedUnderglows) {
		this.unlockedUnderglows = unlockedUnderglows;
	}
	
	public void setUnlockedAvatars(ArrayList<AvatarPair> unlockedAvatars) {
		this.unlockedAvatars = unlockedAvatars;
	}
	
	public boolean hasChallengerUnlocked(int challengerID){
		for (ChallengerInfo ci : unlockedChallengers){
			if (ci.getID() == challengerID){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasSkinUnlocked(int challengerID, int skinID){
		for (SkinInfo si : unlockedSkins){
			if (si.getChallengerID() == challengerID && si.getID() == skinID){
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasUnderglowUnlocked(int underglowID){
		for (UnderglowInfo ui : unlockedUnderglows){
			if (ui.getID() == underglowID){
				return true;
			}
		}
		
		return false;
	}

	public boolean hasAvatarUnlocked(int id, int slotID){
		for (AvatarPair ap : unlockedAvatars){
			if (ap.getID() == id && ap.getSlot() == slotID){
				return true;
			}
		}
		
		return false;
	}

}
