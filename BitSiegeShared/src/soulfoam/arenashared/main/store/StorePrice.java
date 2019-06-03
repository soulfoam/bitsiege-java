package soulfoam.arenashared.main.store;

import java.util.ArrayList;

import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.ids.AvatarLibrary;

public class StorePrice {

	private static StorePrice sp = null;
	
	public static StorePrice getPrices(){
		if (sp == null){
			sp = new StorePrice();
		}
		
		return sp;
	}
	
	private ArrayList<StoreItemChallenger> challengerItems = new ArrayList<StoreItemChallenger>();
	private ArrayList<StoreItemSkin> skinItems = new ArrayList<StoreItemSkin>();
	private ArrayList<StoreItemUnderglow> underglowItems = new ArrayList<StoreItemUnderglow>();
	private ArrayList<StoreItemAvatar> avatarItems = new ArrayList<StoreItemAvatar>();
	
	public StorePrice(){

		//String name, int id, int bitPrice, int siegePrice, boolean canBuyWithBits, boolean canBuyWithSiegePoints
		challengerItems.add(new StoreItemChallenger("Knight", EntityInfo.KNIGHTCHALLENGER, 100, 100, true, true));
		challengerItems.add(new StoreItemChallenger("Warlock", EntityInfo.WARLOCKCHALLENGER, 100, 100, true, true));
		challengerItems.add(new StoreItemChallenger("Archer", EntityInfo.ARCHERCHALLENGER, 100, 100, true, true));
		challengerItems.add(new StoreItemChallenger("Cleric", EntityInfo.CLERICCHALLENGER, 100, 100, true, true));
		challengerItems.add(new StoreItemChallenger("Illusionist", EntityInfo.ILLUSIONISTCHALLENGER, 100, 100, true, true));
		challengerItems.add(new StoreItemChallenger("Void Lord", EntityInfo.VOIDLORDCHALLENGER, 100, 100, true, true));
		challengerItems.add(new StoreItemChallenger("Water Queen", EntityInfo.WATERQUEENCHALLENGER, 100, 100, true, true));
		challengerItems.add(new StoreItemChallenger("Shaman", EntityInfo.SHAMANCHALLENGER, 100, 100, true, true));
		
		// int challengerID, int id, int siegePrice
		skinItems.add(new StoreItemSkin("Blood", EntityInfo.KNIGHTCHALLENGER, CosmeticLibrary.KNIGHT_SKIN_BLOOD, 600));
		skinItems.add(new StoreItemSkin("Frost", EntityInfo.WARLOCKCHALLENGER, CosmeticLibrary.WARLOCK_SKIN_FROST, 600));
		skinItems.add(new StoreItemSkin("Explosive", EntityInfo.ARCHERCHALLENGER, CosmeticLibrary.ARCHER_SKIN_EXPLOSIVE, 600));
		skinItems.add(new StoreItemSkin("Amethyst", EntityInfo.CLERICCHALLENGER, CosmeticLibrary.CLERIC_SKIN_AMETHYST, 600));
		skinItems.add(new StoreItemSkin("Pyro", EntityInfo.ILLUSIONISTCHALLENGER, CosmeticLibrary.ILLUSIONIST_SKIN_PYRO, 600));
		skinItems.add(new StoreItemSkin("Blood", EntityInfo.VOIDLORDCHALLENGER, CosmeticLibrary.VOIDLORD_SKIN_BLOOD, 600));
		skinItems.add(new StoreItemSkin("Swamp", EntityInfo.WATERQUEENCHALLENGER, CosmeticLibrary.WATERQUEEN_SKIN_SWAMP, 600));
		skinItems.add(new StoreItemSkin("Void", EntityInfo.SHAMANCHALLENGER, CosmeticLibrary.SHAMAN_SKIN_VOID, 600));
		
		//int id, int siegePricez
		underglowItems.add(new StoreItemUnderglow("Orange", CosmeticLibrary.UNDERGLOW_ORANGE, 200));
		underglowItems.add(new StoreItemUnderglow("Blue", CosmeticLibrary.UNDERGLOW_BLUE, 200));
		underglowItems.add(new StoreItemUnderglow("White", CosmeticLibrary.UNDERGLOW_WHITE, 200));
		underglowItems.add(new StoreItemUnderglow("Purple", CosmeticLibrary.UNDERGLOW_PURPLE, 200));
		underglowItems.add(new StoreItemUnderglow("Green", CosmeticLibrary.UNDERGLOW_GREEN, 200));
		underglowItems.add(new StoreItemUnderglow("Red", CosmeticLibrary.UNDERGLOW_RED, 200));
		underglowItems.add(new StoreItemUnderglow("Rainbow", CosmeticLibrary.UNDERGLOW_RAINBOW, 200));
		underglowItems.add(new StoreItemUnderglow("Yellow", CosmeticLibrary.UNDERGLOW_YELLOW, 200));
		underglowItems.add(new StoreItemUnderglow("Blood Spin", CosmeticLibrary.UNDERGLOW_BLOOD_SPIN, 400));
		
		// int id, int slotID, int bitPrice, int siegePrice, boolean canBuyWithBits, boolean canBuyWithSiegePoints
		avatarItems.add(new StoreItemAvatar("Black", AvatarLibrary.BACKGROUND_BLACK, AvatarLibrary.SLOT_BACKGROUND, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Red", AvatarLibrary.BACKGROUND_RED, AvatarLibrary.SLOT_BACKGROUND, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Blue", AvatarLibrary.BACKGROUND_BLUE, AvatarLibrary.SLOT_BACKGROUND, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Green", AvatarLibrary.BACKGROUND_GREEN, AvatarLibrary.SLOT_BACKGROUND, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Purple", AvatarLibrary.BACKGROUND_PURPLE, AvatarLibrary.SLOT_BACKGROUND, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Dark Green", AvatarLibrary.BACKGROUND_DARK_GREEN, AvatarLibrary.SLOT_BACKGROUND, 500, 200, true, true));
		
		avatarItems.add(new StoreItemAvatar("Gold 0", AvatarLibrary.BORDER_GOLD_0, AvatarLibrary.SLOT_BORDER, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Gold 1", AvatarLibrary.BORDER_GOLD_1, AvatarLibrary.SLOT_BORDER, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Gold 2", AvatarLibrary.BORDER_GOLD_2, AvatarLibrary.SLOT_BORDER, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Gold 3", AvatarLibrary.BORDER_GOLD_3, AvatarLibrary.SLOT_BORDER, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Gold 4", AvatarLibrary.BORDER_GOLD_4, AvatarLibrary.SLOT_BORDER, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Gold 5", AvatarLibrary.BORDER_GOLD_5, AvatarLibrary.SLOT_BORDER, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Silver 0", AvatarLibrary.BORDER_SILVER_0, AvatarLibrary.SLOT_BORDER, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Silver 1", AvatarLibrary.BORDER_SILVER_1, AvatarLibrary.SLOT_BORDER, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Silver 2", AvatarLibrary.BORDER_SILVER_2, AvatarLibrary.SLOT_BORDER, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Silver 3", AvatarLibrary.BORDER_SILVER_3, AvatarLibrary.SLOT_BORDER, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Silver 4", AvatarLibrary.BORDER_SILVER_4, AvatarLibrary.SLOT_BORDER, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Silver 5", AvatarLibrary.BORDER_SILVER_5, AvatarLibrary.SLOT_BORDER, 500, 200, true, true));
		
		avatarItems.add(new StoreItemAvatar("Gold Lion", AvatarLibrary.ICON_GOLD_LION, AvatarLibrary.SLOT_ICON, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Gold Wolf", AvatarLibrary.ICON_GOLD_WOLF, AvatarLibrary.SLOT_ICON, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Gold Horse", AvatarLibrary.ICON_GOLD_HORSE, AvatarLibrary.SLOT_ICON, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Gold Tower", AvatarLibrary.ICON_GOLD_TOWER, AvatarLibrary.SLOT_ICON, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Gold Shield", AvatarLibrary.ICON_GOLD_SHIELD, AvatarLibrary.SLOT_ICON, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Gold Sword", AvatarLibrary.ICON_GOLD_SWORD, AvatarLibrary.SLOT_ICON, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Silver Lion", AvatarLibrary.ICON_SILVER_LION, AvatarLibrary.SLOT_ICON, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Silver Wolf", AvatarLibrary.ICON_SILVER_WOLF, AvatarLibrary.SLOT_ICON, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Silver Horse", AvatarLibrary.ICON_SILVER_HORSE, AvatarLibrary.SLOT_ICON, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Silver Tower", AvatarLibrary.ICON_SILVER_TOWER, AvatarLibrary.SLOT_ICON, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Silver Shield", AvatarLibrary.ICON_SILVER_SHIELD, AvatarLibrary.SLOT_ICON, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Silver Sword", AvatarLibrary.ICON_SILVER_SWORD, AvatarLibrary.SLOT_ICON, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Gold Coin", AvatarLibrary.ICON_GOLD_COIN, AvatarLibrary.SLOT_ICON, 500, 200, true, true));
		avatarItems.add(new StoreItemAvatar("Gold Cock", AvatarLibrary.ICON_GOLD_COCK_BIRD, AvatarLibrary.SLOT_ICON, 500, 200, true, true));
		
	}
	
	public StoreItemChallenger getChallengerStoreItem(int challengerID){
		for (StoreItemChallenger sic : challengerItems){
			if (sic.getID() == challengerID){
				return sic;
			}
		}
		
		return null;
	}
	
	public StoreItemSkin getSkinStoreItem(int challengerID, int skinID){
		for (StoreItemSkin sis : skinItems){
			if (sis.getID() == skinID && sis.getChallengerID() == challengerID){
				return sis;
			}
		}
		
		return null;
	}

	public StoreItemUnderglow getUnderglowStoreItem(int underglowID){
		for (StoreItemUnderglow siu : underglowItems){
			if (siu.getID() == underglowID){
				return siu;
			}
		}
		
		return null;
	}
	
	public StoreItemAvatar getAvatarStoreItem(int id, int slotID){
		for (StoreItemAvatar sia : avatarItems){
			if (sia.getID() == id && sia.getSlotID() == slotID){
				return sia;
			}
		}
		
		return null;
	}

}
