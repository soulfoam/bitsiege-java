package soulfoam.arena.net.lobby.managers;

import org.newdawn.slick.Color;

import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.resources.avatars.AvatarInfo;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.entityinfo.ChallengerInfo;
import soulfoam.arenashared.main.entityinfo.SkinInfo;
import soulfoam.arenashared.main.entityinfo.UnderglowInfo;
import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.store.BaseStoreItem;
import soulfoam.arenashared.main.store.Currency;
import soulfoam.arenashared.main.store.StoreItemSkin;
import soulfoam.arenashared.main.store.StorePrice;

public class StoreManager {

	private String storeText = getDefaultStoreText();
	private Color storeColor = Color.yellow;
	private int storeResult = -1;

	public void buyChallenger(int challengerID, int currency) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_STORE);
		LobbyManager.getManager().getLobbyClient().getOutput().println("C~" + challengerID + "~" + currency);
	}

	public void buySkin(int challengerID, int skinID) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_STORE);
		LobbyManager.getManager().getLobbyClient().getOutput().println("S~" + challengerID + "~" + skinID);
	}

	public void buyUnderglow(int underglowID) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_STORE);
		LobbyManager.getManager().getLobbyClient().getOutput().println("U~" + underglowID);
	}

	public void buyAvatar(int id, int slotID, int currency) {
		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_STORE);
		LobbyManager.getManager().getLobbyClient().getOutput().println("A~" + id + "~" + slotID + "~" + currency);
	}

	public void setStoreText(int result, BaseStoreItem item) {

		if (item != null) {
			if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_CHALLENGER) {
				storeText = item.getName() + " purchased successfully!";
				storeColor = Color.green;
			}
			if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_SKIN) {
				StoreItemSkin sis = (StoreItemSkin) item;
				storeText = item.getName() + " "
						+ StorePrice.getPrices().getChallengerStoreItem(sis.getChallengerID()).getName()
						+ " purchased successfully!";
				storeColor = Color.green;
			}
			if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_UNDERGLOW) {
				storeText = item.getName() + " Underglow purchased successfully!";
				storeColor = Color.green;
			}
			if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_BG) {
				storeText = item.getName() + " Background purchased successfully!";
				storeColor = Color.green;
			}
			if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_BORDER) {
				storeText = item.getName() + " Border purchased successfully!";
				storeColor = Color.green;
			}
			if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_ICON) {
				storeText = item.getName() + " Icon purchased successfully!";
				storeColor = Color.green;
			}
		}

		if (result == LobbyReturnCode.STORE_CHALLENGER_NOT_OWNED) {
			storeText = "Failure: You don't own this Challenger!";
			storeColor = new Color(255, 150, 0);
		}
		
		if (result == LobbyReturnCode.STORE_ALREADY_OWNED) {
			storeText = "Failure: You already own this item!";
			storeColor = new Color(255, 150, 0);
		}

		if (result == LobbyReturnCode.STORE_NOT_ENOUGH_BITS) {
			storeText = "Failure: You don't have enough Bits!";
			storeColor = new Color(255, 150, 0);
		}

		if (result == LobbyReturnCode.STORE_NOT_ENOUGH_SIEGEPOINTS) {
			storeText = "Failure: You don't have enough Siege Points!";
			storeColor = new Color(255, 150, 0);
		}
	}

	public void setStoreText(String requestText, Color requestColor) {
		storeText = requestText;
		storeColor = requestColor;
	}

	public void setStoreResult(int result) {
		storeResult = result;
		setStoreText(result, null);
	}

	public void setStoreResult(int result, BaseStoreItem item, int currency) {

		storeResult = result;
		setStoreText(result, item);

		if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_CHALLENGER) {
			if (currency == Currency.BITS) {
				LobbyManager.getManager().getUserAccount()
						.setBits(LobbyManager.getManager().getUserAccount().getBits() - item.getBitPrice());
			}
			if (currency == Currency.SIEGE_POINTS) {
				LobbyManager.getManager().getUserAccount().setSiegePoints(
						LobbyManager.getManager().getUserAccount().getSiegePoints() - item.getSiegePrice());
			}
			LobbyManager.getManager().getUserAccount().getUnlockedChallengers().add(new ChallengerInfo(item.getID()));
			LobbyManager.getManager().getUserAccount().getUnlockedSkins().add(new SkinInfo(item.getID(), 0));
			MainMenuManager.getMainMenu().getStoreUI().getChallengerTab().setJustPurchased(true);
			MainMenuManager.getMainMenu().getStoreUI().getChallengerTab().initPages();
			MainMenuManager.getMainMenu().getStoreUI().getSkinTab().initPages();
		}

		if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_SKIN) {
			if (currency == Currency.SIEGE_POINTS) {
				LobbyManager.getManager().getUserAccount().setSiegePoints(
						LobbyManager.getManager().getUserAccount().getSiegePoints() - item.getSiegePrice());
			}

			StoreItemSkin sis = (StoreItemSkin) item;
			LobbyManager.getManager().getUserAccount().getUnlockedSkins()
					.add(new SkinInfo(sis.getChallengerID(), sis.getID()));
			MainMenuManager.getMainMenu().getStoreUI().getSkinTab().initPages();
		}

		if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_UNDERGLOW) {

			LobbyManager.getManager().getUserAccount()
					.setSiegePoints(LobbyManager.getManager().getUserAccount().getSiegePoints() - item.getSiegePrice());

			LobbyManager.getManager().getUserAccount().getUnlockedUnderglows().add(new UnderglowInfo(item.getID()));
			MainMenuManager.getMainMenu().getStoreUI().getUnderglowTab().initPages();

		}
		if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_BG
				|| result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_BORDER
				|| result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_ICON) {

			if (currency == Currency.BITS) {
				LobbyManager.getManager().getUserAccount()
						.setBits(LobbyManager.getManager().getUserAccount().getBits() - item.getBitPrice());
			}
			if (currency == Currency.SIEGE_POINTS) {
				LobbyManager.getManager().getUserAccount().setSiegePoints(
						LobbyManager.getManager().getUserAccount().getSiegePoints() - item.getSiegePrice());
			}

			if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_BG) {
				LobbyManager.getManager().getUserAccount().getUnlockedAvatars()
						.add(new AvatarInfo(item.getID(), AvatarLibrary.SLOT_BACKGROUND));
			} else if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_BORDER) {
				LobbyManager.getManager().getUserAccount().getUnlockedAvatars()
						.add(new AvatarInfo(item.getID(), AvatarLibrary.SLOT_BORDER));
			} else if (result == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_ICON) {
				LobbyManager.getManager().getUserAccount().getUnlockedAvatars()
						.add(new AvatarInfo(item.getID(), AvatarLibrary.SLOT_ICON));
			}

			MainMenuManager.getMainMenu().getStoreUI().getAvatarTab().setOwnedAvatars(result);
		}
	}

	public int getStoreResult() {
		return storeResult;
	}

	public String getStoreText() {
		return storeText;
	}

	public Color getStoreColor() {
		return storeColor;
	}

	public String getDefaultStoreText() {
		return "";
	}
}
