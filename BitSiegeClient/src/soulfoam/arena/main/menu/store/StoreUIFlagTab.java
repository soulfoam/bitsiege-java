package soulfoam.arena.main.menu.store;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;
import soulfoam.arenashared.main.store.StorePrice;

public class StoreUIFlagTab {

	private MenuButton previousButton;
	private MenuButton nextButton;

	private CopyOnWriteArrayList<StoreUIUnderglowPage> underglowPages = new CopyOnWriteArrayList<StoreUIUnderglowPage>();
	private int pageIndex = 0;

	private float x, y;
	private boolean isActive;

	public StoreUIFlagTab(float x, float y) {
		this.x = x;
		this.y = y;
		previousButton = new MenuButton("<", x + 3, y + 118, 12, 7);
		nextButton = new MenuButton(">", x + 18, y + 118, 12, 7);

		initPages();

	}

	public void initPages() {

		underglowPages.clear();
		pageIndex = 0;

		ArrayList<StoreUIUnderglowObject> storeUnderglowObjects = new ArrayList<StoreUIUnderglowObject>();
		ArrayList<StoreUIUnderglowPage> storeUnderglowPages = new ArrayList<StoreUIUnderglowPage>();

		storeUnderglowObjects.add(new StoreUIUnderglowObject(
				StorePrice.getPrices().getUnderglowStoreItem(CosmeticLibrary.UNDERGLOW_ORANGE), x, y));
		storeUnderglowObjects.add(new StoreUIUnderglowObject(
				StorePrice.getPrices().getUnderglowStoreItem(CosmeticLibrary.UNDERGLOW_BLUE), x, y));
		storeUnderglowObjects.add(new StoreUIUnderglowObject(
				StorePrice.getPrices().getUnderglowStoreItem(CosmeticLibrary.UNDERGLOW_WHITE), x, y));
		storeUnderglowObjects.add(new StoreUIUnderglowObject(
				StorePrice.getPrices().getUnderglowStoreItem(CosmeticLibrary.UNDERGLOW_PURPLE), x, y));
		storeUnderglowObjects.add(new StoreUIUnderglowObject(
				StorePrice.getPrices().getUnderglowStoreItem(CosmeticLibrary.UNDERGLOW_GREEN), x, y));
		storeUnderglowObjects.add(new StoreUIUnderglowObject(
				StorePrice.getPrices().getUnderglowStoreItem(CosmeticLibrary.UNDERGLOW_RED), x, y));
		storeUnderglowObjects.add(new StoreUIUnderglowObject(
				StorePrice.getPrices().getUnderglowStoreItem(CosmeticLibrary.UNDERGLOW_RAINBOW), x, y));
		storeUnderglowObjects.add(new StoreUIUnderglowObject(
				StorePrice.getPrices().getUnderglowStoreItem(CosmeticLibrary.UNDERGLOW_YELLOW), x, y));
		storeUnderglowObjects.add(new StoreUIUnderglowObject(
				StorePrice.getPrices().getUnderglowStoreItem(CosmeticLibrary.UNDERGLOW_BLOOD_SPIN), x, y));

		for (StoreUIUnderglowObject suo : storeUnderglowObjects) {
			if (LobbyManager.getManager().getUserAccount().hasUnderglowUnlocked(suo.getUnderglowInfo().getID())) {
				suo.setOwned(true);
			} else {
				suo.setOwned(false);
			}
		}

		Collections.sort(storeUnderglowObjects, new Comparator<StoreUIUnderglowObject>() {

			public int compare(StoreUIUnderglowObject o1, StoreUIUnderglowObject o2) {

				Boolean owned1 = o1.isOwned();
				Boolean owned2 = o2.isOwned();

				int sComp = owned1.compareTo(owned2);

				if (sComp != 0) {
					return sComp;
				} else {
					String name1 = o1.getUnderglowInfo().getName();
					String name2 = o2.getUnderglowInfo().getName();
					return name1.compareTo(name2);
				}

			}
		});

		storeUnderglowPages.add(new StoreUIUnderglowPage(x, y + 3));

		int pageIndexSAP = 0;
		int objectIndex = 0;
		for (int i = 0; i < storeUnderglowObjects.size(); i++) {
			StoreUIUnderglowObject underglowObject = storeUnderglowObjects.get(i);
			storeUnderglowPages.get(pageIndexSAP).getUnderglowItems().add(underglowObject);
			objectIndex++;

			if (objectIndex == 10) {
				pageIndexSAP++;
				storeUnderglowPages.add(new StoreUIUnderglowPage(x, y + 3));
				objectIndex = 0;
			}
		}

		for (StoreUIUnderglowPage ugp : storeUnderglowPages) {
			underglowPages.add(ugp);
		}

		selectFirstItemOnPage();

	}

	public void selectUnderglow(StoreUIUnderglowObject selectedUnderglow) {
		for (StoreUIUnderglowPage page : underglowPages) {
			for (StoreUIUnderglowObject item : page.getUnderglowItems()) {
				item.deSelect();
			}
		}

		selectedUnderglow.select();
	}

	public void selectFirstItemOnPage() {
		selectUnderglow(underglowPages.get(pageIndex).getUnderglowItems().get(0));
	}

	public boolean anyHoverOver() {
		for (StoreUIUnderglowPage page : underglowPages) {
			for (StoreUIUnderglowObject item : page.getUnderglowItems()) {
				if (item.isHoverOver()) {
					return true;
				}
			}
		}

		return false;
	}

	public void clearHoverOver() {
		for (StoreUIUnderglowPage page : underglowPages) {
			for (StoreUIUnderglowObject item : page.getUnderglowItems()) {
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

		underglowPages.get(pageIndex).update(gc, delta);

	}

	public void render(GameContainer gc, Graphics g) {
		g.setColor(Color.red);
		// g.fillRect(x, y, width, height);

		String indexText = "Page: " + (pageIndex + 1) + "/" + underglowPages.size();
		Res.bitFont.drawString(x + 36, y + 119, indexText, Color.white);

		underglowPages.get(pageIndex).render(gc, g);

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
		
	}

	public void setPageIndex(boolean add) {
		if (add) {
			pageIndex++;
			if (pageIndex >= underglowPages.size() - 1) {
				pageIndex = underglowPages.size() - 1;
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
		selectUnderglow(underglowPages.get(0).getUnderglowItems().get(0));
	}

}
