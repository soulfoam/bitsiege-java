package soulfoam.arenashared.main.store;

public class StoreItemAvatar extends BaseStoreItem {

	private int slotID;
	
	public StoreItemAvatar(String name, int id, int slotID, int bitPrice, int siegePrice, boolean canBuyWithBits, boolean canBuyWithSiegePoints) {
		super(name, id, bitPrice, siegePrice, canBuyWithBits, canBuyWithSiegePoints);
		this.slotID = slotID;
	}

	public int getSlotID() {
		return slotID;
	}

}
