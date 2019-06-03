package soulfoam.bitsiegemainserver.main.matchmaking;

import soulfoam.bitsiegemainserver.main.ClientThread;

public class GameLobbyClient {
	
	private ClientThread ct;
	private byte team;
	private int challengerPick = -1;
	private int skinPick;
	private int underglowPick;
	private boolean isReady;
	
	public GameLobbyClient(ClientThread ct){
		this.ct = ct;
	}

	public ClientThread getClientThread() {
		return ct;
	}

	public void setClientThread(ClientThread ct) {
		this.ct = ct;
	}

	public byte getTeam() {
		return team;
	}

	public void setTeamPick(byte teamPick) {
		this.team = teamPick;
	}

	public int getChallengerPick() {
		return challengerPick;
	}

	public void setChallengerPick(int challengerPick) {
		this.challengerPick = challengerPick;
	}

	public int getSkinPick() {
		return skinPick;
	}

	public void setSkinPick(int skinPick) {
		this.skinPick = skinPick;
	}

	public int getUnderglowPick() {
		return underglowPick;
	}

	public void setUnderglowPick(int underglowPick) {
		this.underglowPick = underglowPick;
	}

	public boolean isReady() {
		return isReady;
	}

	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}
}
