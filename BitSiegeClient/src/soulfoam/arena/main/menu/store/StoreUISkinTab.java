package soulfoam.arena.main.menu.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.store.StorePrice;

public class StoreUISkinTab {

	private MenuButton previousButton;
	private MenuButton nextButton;

	private CopyOnWriteArrayList<StoreUISkinPage> skinPages = new CopyOnWriteArrayList<StoreUISkinPage>();
	private StoreUISkinObject selectedSkinObject;
	private int pageIndex = 0;

	private float x, y;
	private boolean isActive;

	public StoreUISkinTab(float x, float y) {
		this.x = x;
		this.y = y;
		previousButton = new MenuButton("<", x + 3, y + 195, 12, 9);
		nextButton = new MenuButton(">", x + 18, y + 195, 12, 9);

		initPages();

	}

	public void initPages() {

		skinPages.clear();
		pageIndex = 0;

		ArrayList<StoreUISkinObject> storeSkinObjects = new ArrayList<StoreUISkinObject>();
		ArrayList<StoreUISkinPage> storeSkinPages = new ArrayList<StoreUISkinPage>();

		storeSkinObjects.add(new StoreUISkinObject(
				StorePrice.getPrices().getSkinStoreItem(EntityInfo.KNIGHTCHALLENGER, CosmeticLibrary.KNIGHT_SKIN_BLOOD),
				x, y));
		storeSkinObjects.add(new StoreUISkinObject(StorePrice.getPrices().getSkinStoreItem(EntityInfo.WARLOCKCHALLENGER,
				CosmeticLibrary.WARLOCK_SKIN_FROST), x, y));
		storeSkinObjects.add(new StoreUISkinObject(StorePrice.getPrices().getSkinStoreItem(EntityInfo.ARCHERCHALLENGER,
				CosmeticLibrary.ARCHER_SKIN_EXPLOSIVE), x, y));
		storeSkinObjects.add(new StoreUISkinObject(StorePrice.getPrices().getSkinStoreItem(EntityInfo.CLERICCHALLENGER,
				CosmeticLibrary.CLERIC_SKIN_AMETHYST), x, y));
		storeSkinObjects.add(new StoreUISkinObject(StorePrice.getPrices()
				.getSkinStoreItem(EntityInfo.ILLUSIONISTCHALLENGER, CosmeticLibrary.ILLUSIONIST_SKIN_PYRO), x, y));
		storeSkinObjects.add(new StoreUISkinObject(StorePrice.getPrices()
				.getSkinStoreItem(EntityInfo.VOIDLORDCHALLENGER, CosmeticLibrary.VOIDLORD_SKIN_BLOOD), x, y));
		storeSkinObjects.add(new StoreUISkinObject(StorePrice.getPrices()
				.getSkinStoreItem(EntityInfo.WATERQUEENCHALLENGER, CosmeticLibrary.WATERQUEEN_SKIN_SWAMP), x, y));
		storeSkinObjects.add(new StoreUISkinObject(
				StorePrice.getPrices().getSkinStoreItem(EntityInfo.SHAMANCHALLENGER, CosmeticLibrary.SHAMAN_SKIN_VOID),
				x, y));

		for (StoreUISkinObject sso : storeSkinObjects) {
			if (LobbyManager.getManager().getUserAccount().hasSkinUnlocked(sso.getSkinInfo().getChallengerID(),
					sso.getSkinInfo().getID())) {
				sso.setOwned(true);
			} else {
				sso.setOwned(false);
			}

			if (LobbyManager.getManager().getUserAccount().hasChallengerUnlocked(sso.getSkinInfo().getChallengerID())) {
				sso.setChallengerUnlocked(true);
			} else {
				sso.setChallengerUnlocked(false);
			}
		}

		Collections.sort(storeSkinObjects, new Comparator<StoreUISkinObject>() {

			public int compare(StoreUISkinObject o1, StoreUISkinObject o2) {

				Boolean owned1 = o1.isOwned();
				Boolean owned2 = o2.isOwned();
				int value1 = owned1.compareTo(owned2);

				if (value1 == 0) {
					Boolean challengerUnlocked1 = o1.isChallengerUnlocked();
					Boolean challengerUnlocked2 = o2.isChallengerUnlocked();
					int value2 = challengerUnlocked2.compareTo(challengerUnlocked1);
					if (value2 == 0) {
						String name1 = o1.getSkinInfo().getName();
						String name2 = o2.getSkinInfo().getName();
						return name1.compareTo(name2);
					} else {
						return value2;
					}
				}
				return value1;

			}
		});

		storeSkinPages.add(new StoreUISkinPage(x, y + 3));

		int pageIndexSAP = 0;
		int objectIndex = 0;
		for (int i = 0; i < storeSkinObjects.size(); i++) {
			
			if (objectIndex == 12) {
				pageIndexSAP++;
				storeSkinPages.add(new StoreUISkinPage(x, y + 3));
				objectIndex = 0;
			}
			
			StoreUISkinObject skinObject = storeSkinObjects.get(i);
			storeSkinPages.get(pageIndexSAP).getSkinItems().add(skinObject);
			objectIndex++;
		}

		for (StoreUISkinPage ssp : storeSkinPages) {
			skinPages.add(ssp);
		}

		selectFirstItemOnPage();

	}

	public void selectSkin(StoreUISkinObject selectedSkin) {
		for (StoreUISkinPage page : skinPages) {
			for (StoreUISkinObject item : page.getSkinItems()) {
				item.deselect();
			}
		}
		selectedSkinObject = selectedSkin;
		selectedSkin.select();
	}

	public void selectFirstItemOnPage() {
		selectSkin(skinPages.get(pageIndex).getSkinItems().get(0));
	}

	public boolean anyHoverOver() {
		for (StoreUISkinPage page : skinPages) {
			for (StoreUISkinObject item : page.getSkinItems()) {
				if (item.isHoverOver()) {
					return true;
				}
			}
		}

		return false;
	}

	public void clearHoverOver() {
		for (StoreUISkinPage page : skinPages) {
			for (StoreUISkinObject item : page.getSkinItems()) {
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

		skinPages.get(pageIndex).update(gc, delta);

	}

	public void render(GameContainer gc, Graphics g) {
		g.setColor(Color.red);
		// g.fillRect(x, y, width, height);

		String indexText = "Page: " + (pageIndex + 1) + "/" + skinPages.size();
		Res.bitFont.drawString(x + 36, y + 197, indexText, Color.white);

		skinPages.get(pageIndex).render(gc, g);

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
		selectedSkinObject.mousePressed(button, x, y);
	}

	public void setPageIndex(boolean add) {
		if (add) {
			pageIndex++;
			if (pageIndex >= skinPages.size() - 1) {
				pageIndex = skinPages.size() - 1;
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
		selectSkin(skinPages.get(0).getSkinItems().get(0));
		if (isActive){
			MainMenuManager.getMainMenu().getStoreUI().setBackgroundImage(Res.UI_RESOURCE.MAINMENU_STORE_UI_SKINS);
		}
	}

}
