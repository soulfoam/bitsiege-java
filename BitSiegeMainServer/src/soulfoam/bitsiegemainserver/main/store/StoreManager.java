package soulfoam.bitsiegemainserver.main.store;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import soulfoam.arenashared.main.entityinfo.ChallengerInfo;
import soulfoam.arenashared.main.entityinfo.SkinInfo;
import soulfoam.arenashared.main.entityinfo.UnderglowInfo;
import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.store.BaseStoreItem;
import soulfoam.arenashared.main.store.Currency;
import soulfoam.arenashared.main.store.StoreItemAvatar;
import soulfoam.arenashared.main.store.StoreItemChallenger;
import soulfoam.arenashared.main.store.StoreItemSkin;
import soulfoam.arenashared.main.store.StoreItemUnderglow;
import soulfoam.arenashared.main.store.StorePrice;
import soulfoam.bitsiegemainserver.main.ClientThread;
import soulfoam.bitsiegemainserver.main.NetworkManager;
import soulfoam.bitsiegemainserver.main.avatars.AvatarPair;
import soulfoam.bitsiegemainserver.main.db.Database;
import soulfoam.bitsiegemainserver.main.logs.LogType;

public class StoreManager {
	
	private boolean canBuy(int currencyAmount, int itemCost){
		if (itemCost > currencyAmount){
			return false;
		}
		return true;
	}

	public int buyChallenger(ClientThread ct, int challengerID, int currency){
		if (ct != null){
			StoreItemChallenger challengerItem = StorePrice.getPrices().getChallengerStoreItem(challengerID);
			if (ct.getUserAccount().hasChallengerUnlocked(challengerID)){
				return LobbyReturnCode.STORE_ALREADY_OWNED;
			}
			if (currency == Currency.BITS){
				if (!canBuy(ct.getUserAccount().getBits(), challengerItem.getBitPrice())){
					return LobbyReturnCode.STORE_NOT_ENOUGH_BITS;
				}
			}
			if (currency == Currency.SIEGE_POINTS){
				if (!canBuy(ct.getUserAccount().getSiegePoints(), challengerItem.getSiegePrice())){
					return LobbyReturnCode.STORE_NOT_ENOUGH_SIEGEPOINTS;
				}
				
			}
			
			ct.getUserAccount().getUnlockedChallengers().add(new ChallengerInfo(challengerID));
			NetworkManager.getManager().getChallengerManager().unlockChallenger(challengerID, ct.getUserAccount().getID());
			NetworkManager.getManager().getSkinManager().unlockSkin(0, challengerID, ct.getUserAccount().getID());
			
			buyItem(ct, challengerItem, currency);
			
			return LobbyReturnCode.STORE_BOUGHT_SUCCESS_CHALLENGER;
		}
		
		return LobbyReturnCode.STORE_ERROR;
	}
	
	public int buySkin(ClientThread ct, int challengerID, int skinID){
		if (ct != null){
			StoreItemSkin skinItem = StorePrice.getPrices().getSkinStoreItem(challengerID, skinID);
			
			if (ct.getUserAccount().hasSkinUnlocked(challengerID, skinID)){
				return LobbyReturnCode.STORE_ALREADY_OWNED;
			}

			if (!canBuy(ct.getUserAccount().getSiegePoints(), skinItem.getSiegePrice())){
				return LobbyReturnCode.STORE_NOT_ENOUGH_SIEGEPOINTS;
			}
			
			if (!ct.getUserAccount().hasChallengerUnlocked(challengerID)){
				return LobbyReturnCode.STORE_CHALLENGER_NOT_OWNED;
			}
			
			ct.getUserAccount().getUnlockedSkins().add(new SkinInfo(challengerID, skinID));
			NetworkManager.getManager().getSkinManager().unlockSkin(skinID, challengerID, ct.getUserAccount().getID());
			
			buyItem(ct, skinItem, Currency.SIEGE_POINTS);
			
			return LobbyReturnCode.STORE_BOUGHT_SUCCESS_SKIN;
		}
		
		return LobbyReturnCode.STORE_ERROR;
	}
	
	public int buyUnderglow(ClientThread ct, int underglowID){
		if (ct != null){
			StoreItemUnderglow underglowItem = StorePrice.getPrices().getUnderglowStoreItem(underglowID);
			if (ct.getUserAccount().hasUnderglowUnlocked(underglowID)){
				return LobbyReturnCode.STORE_ALREADY_OWNED;
			}

			if (!canBuy(ct.getUserAccount().getSiegePoints(), underglowItem.getSiegePrice())){
				return LobbyReturnCode.STORE_NOT_ENOUGH_SIEGEPOINTS;
			}
			
			ct.getUserAccount().getUnlockedUnderglows().add(new UnderglowInfo(underglowID));
			NetworkManager.getManager().getUnderglowManager().unlockUnderglow(underglowID, ct.getUserAccount().getID());

			buyItem(ct, underglowItem, Currency.SIEGE_POINTS);
			
			return LobbyReturnCode.STORE_BOUGHT_SUCCESS_UNDERGLOW;
		}
		
		return LobbyReturnCode.STORE_ERROR;
	}
	
	public int buyAvatar(ClientThread ct, int id, int slotID, int currency){
		if (ct != null){
			StoreItemAvatar avatarItem = StorePrice.getPrices().getAvatarStoreItem(id, slotID);
			
			if (ct.getUserAccount().hasAvatarUnlocked(id, slotID)){
				return LobbyReturnCode.STORE_ALREADY_OWNED;
			}

			if (currency == Currency.BITS){
				if (!canBuy(ct.getUserAccount().getBits(), avatarItem.getBitPrice())){
					return LobbyReturnCode.STORE_NOT_ENOUGH_BITS;
				}
			}
			if (currency == Currency.SIEGE_POINTS){
				if (!canBuy(ct.getUserAccount().getSiegePoints(), avatarItem.getSiegePrice())){
					return LobbyReturnCode.STORE_NOT_ENOUGH_SIEGEPOINTS;
				}
			}
			
			ct.getUserAccount().getUnlockedAvatars().add(new AvatarPair(id, slotID));
			NetworkManager.getManager().getAvatarManager().unlockAvatar(id, slotID, ct.getUserAccount().getID());
			
			int returnCode = LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_BG;
			
			if (slotID == AvatarLibrary.SLOT_BORDER){
				returnCode = LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_BORDER;
			}
			else if (slotID == AvatarLibrary.SLOT_ICON){
				returnCode = LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_ICON;
			}
			
			buyItem(ct, avatarItem, currency);
			
			return returnCode;
		}
		
		return LobbyReturnCode.STORE_ERROR;
	}
	
	public void buyItem(ClientThread ct, BaseStoreItem item, int currency){
		
      	try {
      		
      		int currentSiegePoints = -1;
      		int currentBits = -1;
      		int newCurrencyAmount = 0;
      		
      		if (ct != null){
    			NetworkManager.getManager().getLogManager().addLog(LogType.BOUGHT_ITEM, ct.getUserAccount().getUsername() + " bought " + item.getName(), ct.getSocket().getInetAddress().getHostAddress());
      		}
      		else{
      			return;
      		}
      		
  			Connection conn = Database.getConnection();
  			
			String query = "SELECT * FROM accounts WHERE id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(query);
			stmt.setInt(1, ct.getUserAccount().getID());
			
			ResultSet rs = stmt.executeQuery();
			
			if (rs.next()){
				currentSiegePoints = rs.getInt("siegepoints");
				currentBits = rs.getInt("bits");
			}
			
			if (currentSiegePoints == -1 || currentBits == -1){
				return;
			}
			
			String updateQuery = "";
			
			if (currency == Currency.BITS){
				newCurrencyAmount = currentBits - item.getBitPrice();
				updateQuery = "UPDATE accounts SET bits = ? WHERE id = ?";
				ct.getUserAccount().setBits(newCurrencyAmount);
			}
			if (currency == Currency.SIEGE_POINTS){
				newCurrencyAmount = currentSiegePoints - item.getSiegePrice();
				updateQuery = "UPDATE accounts SET siegepoints = ? WHERE id = ?";
				ct.getUserAccount().setSiegePoints(newCurrencyAmount);
			}
			
			PreparedStatement updatestmt = conn.prepareStatement(updateQuery);
			updatestmt.setInt(1, newCurrencyAmount);
			updatestmt.setInt(2, ct.getUserAccount().getID());
			
			updatestmt.execute();
			
			rs.close();
			stmt.close();
			updatestmt.close();
			conn.close();
			
    			
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
      	
	}
	
}
