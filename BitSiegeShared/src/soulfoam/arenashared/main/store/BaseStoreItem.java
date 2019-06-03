package soulfoam.arenashared.main.store;

public class BaseStoreItem {
	
	private String name;
	private int id;
	
	private boolean canBuyWithBits;
	private boolean canBuyWithSiegePoints;
	
	private int bitPrice;
	private int siegePrice;
	
	public BaseStoreItem(String name, int id, int bitPrice, int siegePrice, boolean canBuyWithBits, boolean canBuyWithSiegePoints){
		this.name = name;
		this.id = id;
		this.bitPrice = bitPrice;
		this.siegePrice = siegePrice;
		this.canBuyWithBits = canBuyWithBits;
		this.canBuyWithSiegePoints = canBuyWithSiegePoints;
	}

	public boolean canBuyWithBits() {
		return canBuyWithBits;
	}

	public boolean canBuyWithSiegePoints() {
		return canBuyWithSiegePoints;
	}

	public int getBitPrice() {
		return bitPrice;
	}

	public int getSiegePrice() {
		return siegePrice;
	}

	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

}
