package soulfoam.arenashared.main.entityinfo;

public class SkinInfo {

	private int challengerID;
	private int id;

	public SkinInfo(int challengerID, int id){
		this.challengerID = challengerID;
		this.id = id;
	}
	
	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getChallengerID() {
		return challengerID;
	}

	public void setChallengerID(int challengerID) {
		this.challengerID = challengerID;
	}
	
}
