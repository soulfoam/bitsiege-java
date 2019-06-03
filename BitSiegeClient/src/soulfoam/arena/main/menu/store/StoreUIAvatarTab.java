package soulfoam.arena.main.menu.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.main.menu.BaseTab;
import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;
import soulfoam.arenashared.main.store.StorePrice;

public class StoreUIAvatarTab {

	private MenuButton previousButton;
	private MenuButton nextButton;

	private CopyOnWriteArrayList<StoreUIAvatarPage> avatarPages = new CopyOnWriteArrayList<StoreUIAvatarPage>();
	private StoreUIAvatarObject selectedAvatarObject;
	private int pageIndex = 0;

	private Rectangle backgroundTabButton;
	private Rectangle borderTabButton;
	private Rectangle iconTabButton;

	private float x, y, width;
	private boolean isActive;

	public StoreUIAvatarTab(float x, float y) {
		this.x = x;
		this.y = y;
		width = 190;
		previousButton = new MenuButton("<", x + 3, y + 195, 12, 9);
		nextButton = new MenuButton(">", x + 18, y + 195, 12, 9);

		backgroundTabButton = new Rectangle(x + 1, y + 8, 77, 9);
		borderTabButton = new Rectangle(x + 80, y + 8, 77, 9);
		iconTabButton = new Rectangle(x + 159, y + 8, 77, 9);

		initBackgroundPages();

	}

	private void initBackgroundPages() {

		avatarPages.clear();
		pageIndex = 0;

		ArrayList<StoreUIAvatarObject> storeAvatarObjects = new ArrayList<StoreUIAvatarObject>();
		ArrayList<StoreUIAvatarPage> storeAvatarPages = new ArrayList<StoreUIAvatarPage>();

		storeAvatarObjects.add(new StoreUIAvatarObject(StorePrice.getPrices()
				.getAvatarStoreItem(AvatarLibrary.BACKGROUND_BLACK, AvatarLibrary.SLOT_BACKGROUND), x, y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.BACKGROUND_RED, AvatarLibrary.SLOT_BACKGROUND),
				x, y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.BACKGROUND_BLUE, AvatarLibrary.SLOT_BACKGROUND),
				x, y));
		storeAvatarObjects.add(new StoreUIAvatarObject(StorePrice.getPrices()
				.getAvatarStoreItem(AvatarLibrary.BACKGROUND_GREEN, AvatarLibrary.SLOT_BACKGROUND), x, y));
		storeAvatarObjects.add(new StoreUIAvatarObject(StorePrice.getPrices()
				.getAvatarStoreItem(AvatarLibrary.BACKGROUND_PURPLE, AvatarLibrary.SLOT_BACKGROUND), x, y));
		storeAvatarObjects.add(new StoreUIAvatarObject(StorePrice.getPrices()
				.getAvatarStoreItem(AvatarLibrary.BACKGROUND_DARK_GREEN, AvatarLibrary.SLOT_BACKGROUND), x, y));

		for (StoreUIAvatarObject sao : storeAvatarObjects) {
			if (LobbyManager.getManager().getUserAccount().hasAvatarUnlocked(sao.getAvatarInfo().getID(),
					sao.getAvatarInfo().getSlotID())) {
				sao.setOwned(true);
			} else {
				sao.setOwned(false);
			}
		}

		Collections.sort(storeAvatarObjects, new Comparator<StoreUIAvatarObject>() {

			public int compare(StoreUIAvatarObject o1, StoreUIAvatarObject o2) {

				Boolean owned1 = o1.isOwned();
				Boolean owned2 = o2.isOwned();

				int sComp = owned1.compareTo(owned2);

				if (sComp != 0) {
					return sComp;
				} else {
					String name1 = o1.getAvatarInfo().getName();
					String name2 = o2.getAvatarInfo().getName();
					return name1.compareTo(name2);
				}

			}
		});

		storeAvatarPages.add(new StoreUIAvatarPage(x, y + 3));

		int pageIndexSAP = 0;
		int objectIndex = 0;
		for (int i = 0; i < storeAvatarObjects.size(); i++) {
			
			if (objectIndex == 12) {
				pageIndexSAP++;
				storeAvatarPages.add(new StoreUIAvatarPage(x, y + 3));
				objectIndex = 0;
			}
			
			StoreUIAvatarObject avatarObject = storeAvatarObjects.get(i);
			storeAvatarPages.get(pageIndexSAP).getAvatarItems().add(avatarObject);
			objectIndex++;
		}

		for (StoreUIAvatarPage sap : storeAvatarPages) {
			avatarPages.add(sap);
		}

		selectFirstItemOnPage();
	}

	private void initBorderPages() {

		avatarPages.clear();
		pageIndex = 0;

		ArrayList<StoreUIAvatarObject> storeAvatarObjects = new ArrayList<StoreUIAvatarObject>();
		ArrayList<StoreUIAvatarPage> storeAvatarPages = new ArrayList<StoreUIAvatarPage>();

		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.BORDER_GOLD_0, AvatarLibrary.SLOT_BORDER), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.BORDER_GOLD_1, AvatarLibrary.SLOT_BORDER), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.BORDER_GOLD_2, AvatarLibrary.SLOT_BORDER), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.BORDER_GOLD_3, AvatarLibrary.SLOT_BORDER), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.BORDER_GOLD_4, AvatarLibrary.SLOT_BORDER), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.BORDER_GOLD_5, AvatarLibrary.SLOT_BORDER), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.BORDER_SILVER_0, AvatarLibrary.SLOT_BORDER), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.BORDER_SILVER_1, AvatarLibrary.SLOT_BORDER), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.BORDER_SILVER_2, AvatarLibrary.SLOT_BORDER), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.BORDER_SILVER_3, AvatarLibrary.SLOT_BORDER), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.BORDER_SILVER_4, AvatarLibrary.SLOT_BORDER), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.BORDER_SILVER_5, AvatarLibrary.SLOT_BORDER), x,
				y));

		for (StoreUIAvatarObject sao : storeAvatarObjects) {
			if (LobbyManager.getManager().getUserAccount().hasAvatarUnlocked(sao.getAvatarInfo().getID(),
					sao.getAvatarInfo().getSlotID())) {
				sao.setOwned(true);
			} else {
				sao.setOwned(false);
			}
		}

		Collections.sort(storeAvatarObjects, new Comparator<StoreUIAvatarObject>() {

			public int compare(StoreUIAvatarObject o1, StoreUIAvatarObject o2) {

				Boolean owned1 = o1.isOwned();
				Boolean owned2 = o2.isOwned();

				int sComp = owned1.compareTo(owned2);

				if (sComp != 0) {
					return sComp;
				} else {
					String name1 = o1.getAvatarInfo().getName();
					String name2 = o2.getAvatarInfo().getName();
					return name1.compareTo(name2);
				}

			}
		});

		storeAvatarPages.add(new StoreUIAvatarPage(x, y + 3));

		int pageIndexSAP = 0;
		int objectIndex = 0;
		for (int i = 0; i < storeAvatarObjects.size(); i++) {
			
			if (objectIndex == 12) {
				pageIndexSAP++;
				storeAvatarPages.add(new StoreUIAvatarPage(x, y + 3));
				objectIndex = 0;
			}
			
			StoreUIAvatarObject avatarObject = storeAvatarObjects.get(i);
			storeAvatarPages.get(pageIndexSAP).getAvatarItems().add(avatarObject);
			objectIndex++;
		}

		for (StoreUIAvatarPage sap : storeAvatarPages) {
			avatarPages.add(sap);
		}

		selectFirstItemOnPage();
	}

	private void initIconPages() {

		avatarPages.clear();
		pageIndex = 0;

		ArrayList<StoreUIAvatarObject> storeAvatarObjects = new ArrayList<StoreUIAvatarObject>();
		ArrayList<StoreUIAvatarPage> storeAvatarPages = new ArrayList<StoreUIAvatarPage>();

		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.ICON_GOLD_LION, AvatarLibrary.SLOT_ICON), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.ICON_GOLD_WOLF, AvatarLibrary.SLOT_ICON), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.ICON_GOLD_HORSE, AvatarLibrary.SLOT_ICON), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.ICON_GOLD_TOWER, AvatarLibrary.SLOT_ICON), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.ICON_GOLD_SHIELD, AvatarLibrary.SLOT_ICON), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.ICON_GOLD_SWORD, AvatarLibrary.SLOT_ICON), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.ICON_SILVER_LION, AvatarLibrary.SLOT_ICON), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.ICON_SILVER_WOLF, AvatarLibrary.SLOT_ICON), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.ICON_SILVER_HORSE, AvatarLibrary.SLOT_ICON), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.ICON_SILVER_TOWER, AvatarLibrary.SLOT_ICON), x,
				y));

		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.ICON_SILVER_SHIELD, AvatarLibrary.SLOT_ICON), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.ICON_SILVER_SWORD, AvatarLibrary.SLOT_ICON), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.ICON_GOLD_COIN, AvatarLibrary.SLOT_ICON), x,
				y));
		storeAvatarObjects.add(new StoreUIAvatarObject(
				StorePrice.getPrices().getAvatarStoreItem(AvatarLibrary.ICON_GOLD_COCK_BIRD, AvatarLibrary.SLOT_ICON),
				x, y));

		for (StoreUIAvatarObject sao : storeAvatarObjects) {
			if (LobbyManager.getManager().getUserAccount().hasAvatarUnlocked(sao.getAvatarInfo().getID(),
					sao.getAvatarInfo().getSlotID())) {
				sao.setOwned(true);
			} else {
				sao.setOwned(false);
			}
		}

		Collections.sort(storeAvatarObjects, new Comparator<StoreUIAvatarObject>() {

			public int compare(StoreUIAvatarObject o1, StoreUIAvatarObject o2) {

				Boolean owned1 = o1.isOwned();
				Boolean owned2 = o2.isOwned();

				int sComp = owned1.compareTo(owned2);

				if (sComp != 0) {
					return sComp;
				} else {
					String name1 = o1.getAvatarInfo().getName();
					String name2 = o2.getAvatarInfo().getName();
					return name1.compareTo(name2);
				}

			}
		});

		storeAvatarPages.add(new StoreUIAvatarPage(x, y + 3));

		int pageIndexSAP = 0;
		int objectIndex = 0;
		for (int i = 0; i < storeAvatarObjects.size(); i++) {
			
			if (objectIndex == 12) {
				pageIndexSAP++;
				storeAvatarPages.add(new StoreUIAvatarPage(x, y + 3));
				objectIndex = 0;
			}
			
			StoreUIAvatarObject avatarObject = storeAvatarObjects.get(i);
			storeAvatarPages.get(pageIndexSAP).getAvatarItems().add(avatarObject);
			objectIndex++;
		}

		for (StoreUIAvatarPage sap : storeAvatarPages) {
			avatarPages.add(sap);
		}

		selectFirstItemOnPage();
	}

	public void setOwnedAvatars(int id) {
		if (id == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_BG) {
			initBackgroundPages();
		} else if (id == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_BORDER) {
			initBorderPages();
		} else if (id == LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_ICON) {
			initIconPages();
		}
	}

	public void selectAvatarItem(StoreUIAvatarObject selectedAvatarItem) {
		for (StoreUIAvatarPage page : avatarPages) {
			for (StoreUIAvatarObject item : page.getAvatarItems()) {
				item.deselect();
			}
		}
		selectedAvatarObject = selectedAvatarItem;
		selectedAvatarItem.select();
	}

	public void selectFirstItemOnPage() {
		if (!avatarPages.get(pageIndex).getAvatarItems().isEmpty()){
			selectAvatarItem(avatarPages.get(pageIndex).getAvatarItems().get(0));
		}
	}

	public boolean anyHoverOver() {
		for (StoreUIAvatarPage page : avatarPages) {
			for (StoreUIAvatarObject item : page.getAvatarItems()) {
				if (item.isHoverOver()) {
					return true;
				}
			}
		}

		return false;
	}

	public void clearHoverOver() {
		for (StoreUIAvatarPage page : avatarPages) {
			for (StoreUIAvatarObject item : page.getAvatarItems()) {
				item.setHoverOver(false);
			}
		}
	}

	public void update(GameContainer gc, int delta) {

		previousButton.update(gc);
		nextButton.update(gc);

		if (previousButton.isClicked()) {
			setPageIndex(false);
		}

		if (nextButton.isClicked()) {
			setPageIndex(true);
		}
		
		if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if (backgroundTabButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				MainMenuManager.getMainMenu().getStoreUI().setBackgroundImage(Res.UI_RESOURCE.MAINMENU_STORE_UI_AVATARS_BACKGROUNDS);
				initBackgroundPages();
			}	 
			if (borderTabButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				MainMenuManager.getMainMenu().getStoreUI().setBackgroundImage(Res.UI_RESOURCE.MAINMENU_STORE_UI_AVATARS_BORDERS);
				initBorderPages();
			}	 
			if (iconTabButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				MainMenuManager.getMainMenu().getStoreUI().setBackgroundImage(Res.UI_RESOURCE.MAINMENU_STORE_UI_AVATARS_ICONS);
				initIconPages();
			}	 
		}

		if (!avatarPages.isEmpty()) {
			avatarPages.get(pageIndex).update(gc, delta);
		}

	}

	public void render(GameContainer gc, Graphics g) {
		
		if (backgroundTabButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			Res.UI_RESOURCE.MAINMENU_STORE_UI_TAB_SELECT.draw(backgroundTabButton.getX(), backgroundTabButton.getY());
		}
		if (borderTabButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			Res.UI_RESOURCE.MAINMENU_STORE_UI_TAB_SELECT.draw(borderTabButton.getX(), borderTabButton.getY());
		}
		if (iconTabButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			Res.UI_RESOURCE.MAINMENU_STORE_UI_TAB_SELECT.draw(iconTabButton.getX(), iconTabButton.getY());
		}
		
		String indexText = "Page: " + (pageIndex + 1) + "/" + avatarPages.size();
		Res.bitFont.drawString(x + 36, y + 197, indexText, Color.white);

		if (!avatarPages.isEmpty()) {
			avatarPages.get(pageIndex).render(gc, g);
		}

		previousButton.render(g, 1, gc);
		nextButton.render(g, 1, gc);


	}

	public void mouseWheelMoved(int m) {
		if (m > 0) { // downward wheel
			setPageIndex(true);
		} else if (m < 0) { // upward wheel
			setPageIndex(false);
		}
	}
	
	public void mousePressed(int button, int x, int y) {
		selectedAvatarObject.mousePressed(button, x, y);
	}

	public void setPageIndex(boolean add) {
		if (add) {
			pageIndex++;
			if (pageIndex >= avatarPages.size() - 1) {
				pageIndex = avatarPages.size() - 1;
			}
		} else {
			pageIndex--;
			if (pageIndex <= 0) {
				pageIndex = 0;
			}
		}

		selectFirstItemOnPage();
		clearHoverOver();
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
		initBackgroundPages();
		selectAvatarItem(avatarPages.get(pageIndex).getAvatarItems().get(0));
		if (isActive){
			MainMenuManager.getMainMenu().getStoreUI().setBackgroundImage(Res.UI_RESOURCE.MAINMENU_STORE_UI_AVATARS_BACKGROUNDS);
		}
	}
}
