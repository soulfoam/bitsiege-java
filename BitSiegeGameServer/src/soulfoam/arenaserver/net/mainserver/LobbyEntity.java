package soulfoam.arenaserver.net.mainserver;

public class LobbyEntity {
	
	private int accountID;
	private int gameID;
	private String playerName;
	private int playerChallenger;
	private byte playerTeam;
	private int playerSkin;
	private int playerUnderglow;
	private String usernameColor;
	private boolean initialized;
	
	public LobbyEntity(int accountID, String playerName, String color, byte playerTeam, int playerChallenger, int playerSkin, int playerUnderglow){
		this.accountID = accountID;
		this.playerName = playerName;
		this.usernameColor = color;
		this.playerTeam = playerTeam;
		this.playerChallenger = playerChallenger;
		this.playerSkin = playerSkin;
		this.playerUnderglow = playerUnderglow;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getAccountID() {
		return accountID;
	}

	public void setID(int id) {
		this.accountID = id;
	}

	public byte getPlayerTeam() {
		return playerTeam;
	}

	public int getPlayerChallenger() {
		return playerChallenger;
	}

	public int getPlayerUnderglow() {
		return playerUnderglow;
	}

	public String getUsernameColor() {
		return usernameColor;
	}

	public int getPlayerSkin() {
		return playerSkin;
	}

	public boolean isInitialized() {
		return initialized;
	}

	public void setInitialized(boolean initialized) {
		this.initialized = initialized;
	}

	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

}