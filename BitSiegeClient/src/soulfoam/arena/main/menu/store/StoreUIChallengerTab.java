package soulfoam.arena.main.menu.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.entities.challengers.tooltips.ChallengerToolTip;
import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.store.StorePrice;

public class StoreUIChallengerTab {

	private MenuButton previousButton;
	private MenuButton nextButton;

	private CopyOnWriteArrayList<StoreUIChallengerPage> challengerPages = new CopyOnWriteArrayList<StoreUIChallengerPage>();
	private StoreUIChallengerObject selectedChallengerObject;
	private int pageIndex = 0;

	private float x, y, width;
	private boolean isActive;
	private ChallengerToolTip toolTip;
	private boolean justPurchased;

	public StoreUIChallengerTab(float x, float y) {
		this.x = x;
		this.y = y;
		width = 190;
		previousButton = new MenuButton("<", x + 3, y + 195, 12, 9);
		nextButton = new MenuButton(">", x + 18, y + 195, 12, 9);

		initPages();

	}

	public void initPages() {

		challengerPages.clear();
		pageIndex = 0;

		ArrayList<StoreUIChallengerObject> storeChallengerObjects = new ArrayList<StoreUIChallengerObject>();
		ArrayList<StoreUIChallengerPage> storeChallengerPages = new ArrayList<StoreUIChallengerPage>();

		storeChallengerObjects.add(new StoreUIChallengerObject(StorePrice.getPrices().getChallengerStoreItem(EntityInfo.ILLUSIONISTCHALLENGER), x, y));
		storeChallengerObjects.add(new StoreUIChallengerObject(StorePrice.getPrices().getChallengerStoreItem(EntityInfo.VOIDLORDCHALLENGER), x, y));
		storeChallengerObjects.add(new StoreUIChallengerObject(StorePrice.getPrices().getChallengerStoreItem(EntityInfo.WATERQUEENCHALLENGER), x, y));
		storeChallengerObjects.add(new StoreUIChallengerObject(StorePrice.getPrices().getChallengerStoreItem(EntityInfo.SHAMANCHALLENGER), x, y));

		
		for (StoreUIChallengerObject sco : storeChallengerObjects) {
			if (LobbyManager.getManager().getUserAccount().hasChallengerUnlocked(sco.getChallengerInfo().getID())) {
				sco.setOwned(true);
			} else {
				sco.setOwned(false);
			}
		}

		Collections.sort(storeChallengerObjects, new Comparator<StoreUIChallengerObject>() {

			public int compare(StoreUIChallengerObject o1, StoreUIChallengerObject o2) {

				Boolean owned1 = o1.isOwned();
				Boolean owned2 = o2.isOwned();

				int sComp = owned1.compareTo(owned2);

				if (sComp != 0) {
					return sComp;
				} else {
					String name1 = o1.getChallengerInfo().getName();
					String name2 = o2.getChallengerInfo().getName();
					return name1.compareTo(name2);
				}

			}
		});

		storeChallengerPages.add(new StoreUIChallengerPage(x, y + 3));

		int pageIndexSAP = 0;
		int objectIndex = 0;
		for (int i = 0; i < storeChallengerObjects.size(); i++) {
			if (objectIndex == 12) {
				pageIndexSAP++;
				storeChallengerPages.add(new StoreUIChallengerPage(x, y + 3));
				objectIndex = 0;
			}
			
			StoreUIChallengerObject challengerObject = storeChallengerObjects.get(i);
			storeChallengerPages.get(pageIndexSAP).getChallengerItems().add(challengerObject);
			objectIndex++;
		}

		for (StoreUIChallengerPage scp : storeChallengerPages) {
			challengerPages.add(scp);
		}

		selectFirstItemOnPage();

	}

	public void selectChallenger(StoreUIChallengerObject selectedChallenger) {
		for (StoreUIChallengerPage page : challengerPages) {
			for (StoreUIChallengerObject item : page.getChallengerItems()) {
				item.deselect();
			}
		}
		selectedChallengerObject = selectedChallenger;
		selectedChallenger.select();
	}

	public void selectFirstItemOnPage() {
		selectChallenger(challengerPages.get(pageIndex).getChallengerItems().get(0));
	}

	public boolean anyHoverOver() {
		for (StoreUIChallengerPage page : challengerPages) {
			for (StoreUIChallengerObject item : page.getChallengerItems()) {
				if (item.isHoverOver()) {
					return true;
				}
			}
		}

		return false;
	}

	public void clearHoverOver() {
		for (StoreUIChallengerPage page : challengerPages) {
			for (StoreUIChallengerObject item : page.getChallengerItems()) {
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

		if (justPurchased) {
			if (!new Rectangle(x + 4, y + 49, width - 6, 31).contains(gc.getInput().getMouseX(),
					gc.getInput().getMouseY())) {
				justPurchased = false;
			}
		}

		challengerPages.get(pageIndex).update(gc, delta);

	}

	public void render(GameContainer gc, Graphics g) {
		g.setColor(Color.red);
		// g.fillRect(x, y, width, height);

		String indexText = "Page: " + (pageIndex + 1) + "/" + challengerPages.size();
		Res.bitFont.drawString(x + 36, y + 197, indexText, Color.white);

		challengerPages.get(pageIndex).render(gc, g);

		previousButton.render(g, 1, gc);
		nextButton.render(g, 1, gc);

		if (toolTip != null && !justPurchased) {
			toolTip.setX(10);
			toolTip.setY(96);
			toolTip.render(g);
		}
	}

	public void mouseWheelMoved(int m) {
		if (m > 0) { // downward wheel
			setPageIndex(true);
		} else if (m < 0) { // upward wheel
			setPageIndex(false);
		}
	}
	
	public void mousePressed(int button, int x, int y) {
		selectedChallengerObject.mousePressed(button, x, y);
	}

	public void setPageIndex(boolean add) {
		if (add) {
			pageIndex++;
			if (pageIndex >= challengerPages.size() - 1) {
				pageIndex = challengerPages.size() - 1;
			}
		} else {
			pageIndex--;
			if (pageIndex <= 0) {
				pageIndex = 0;
			}
		}
		clearHoverOver();
		selectFirstItemOnPage();
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
		selectChallenger(challengerPages.get(0).getChallengerItems().get(0));
		if (isActive){
			MainMenuManager.getMainMenu().getStoreUI().setBackgroundImage(Res.UI_RESOURCE.MAINMENU_STORE_UI_CHALLENGERS);
		}
	}

	public ChallengerToolTip getToolTip() {
		return toolTip;
	}

	public void setToolTip(ChallengerToolTip toolTip) {
		this.toolTip = toolTip;
	}

	public boolean justPurchased() {
		return justPurchased;
	}

	public void setJustPurchased(boolean justPurchased) {
		this.justPurchased = justPurchased;
	}
}
