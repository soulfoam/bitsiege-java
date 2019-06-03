package soulfoam.arenashared.main.store;

public class StoreItemSkin extends BaseStoreItem {

	private int challengerID;
	
	public StoreItemSkin(String name, int challengerID, int id, int siegePrice) {
		super(name, id, 0, siegePrice, false, true);
		this.challengerID = challengerID;
	}

	public int getChallengerID() {
		return challengerID;
	}

}
