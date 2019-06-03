package soulfoam.arena.main.menu.store;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.EntityBook;
import soulfoam.arena.entities.challengers.underglows.Underglow;
import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.main.util.GlowTimer;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.lobbyutil.BSTimer;
import soulfoam.arenashared.main.store.Currency;
import soulfoam.arenashared.main.store.StoreItemUnderglow;

public class StoreUIUnderglowObject {

	private float x, y, width, height;
	private StoreItemUnderglow underglowInfo;
	private Underglow underglow;

	private boolean selected;
	private boolean hoverOver;
	private boolean owned;
	private int currency = -1;
	private MenuButton purchaseButton;
	private MenuButton cancelButton;

	private ArrayList<Entity> displayEntities = new ArrayList<Entity>();
	private int randomEntityIndex = 0;

	private GlowTimer glowTimer = new GlowTimer(90, 255, 6);
	private BSTimer entityChangeTimer = new BSTimer(1500);

	public StoreUIUnderglowObject(StoreItemUnderglow underglowInfo, float x, float y) {
		this.x = x;
		this.y = y;
		width = 32;
		height = 40;
		this.underglowInfo = underglowInfo;
		purchaseButton = new MenuButton("Purchase", x + 3, y + 103, 44, 8);
		cancelButton = new MenuButton("Cancel", x + 3, y + 116, 44, 8);
		underglow = new Underglow(null, underglowInfo.getID());

		initChallengerDisplay();
	}

	public void initChallengerDisplay() {

		for (int i = 0; i < 8; i++) {
			displayEntities.add(EntityBook.getChallengerByID(i, x, y, 0, underglowInfo.getID()));
			displayEntities.add(EntityBook.getChallengerByID(i, x, y, 1, underglowInfo.getID()));
		}
	}

	public void update(GameContainer gc, int delta) {
		height = 31;
		if (getBounds().contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				MainMenuManager.getMainMenu().getStoreUI().getUnderglowTab().selectUnderglow(this);
			}
			hoverOver = true;
		} else {
			hoverOver = false;
		}

		if (selected) {
			updatePreview(gc, delta);
		} else {
			currency = -1;
		}

		if (entityChangeTimer.update()) {
			if (randomEntityIndex < 15) {
				randomEntityIndex++;
			} else {
				randomEntityIndex = 0;
			}
			entityChangeTimer.reset();
		}

		glowTimer.update(delta);
	}

	public void updatePreview(GameContainer gc, int delta) {

		float x = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getX();
		float y = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getY();
		float width = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getWidth();
		MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getHeight();

		purchaseButton.setPosition(x + 3, y + 67);
		cancelButton.setPosition(x + 3, y + 79);

		if (currency == -1) {
			if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !owned) {
				if (new Rectangle(x, y + 106, width, 10).contains(gc.getInput().getMouseX(),
						gc.getInput().getMouseY())) {
					currency = Currency.SIEGE_POINTS;
				}
			}
		} else {

			if (purchaseButton.isClicked()) {
				LobbyManager.getManager().getStoreManager().buyUnderglow(underglowInfo.getID());
			}

			if (cancelButton.isClicked()) {
				currency = -1;
			}

			purchaseButton.update(gc);
			cancelButton.update(gc);
		}
	}

	public void select() {
		selected = true;
	}

	public void deSelect() {
		selected = false;
	}

	public void render(GameContainer gc, Graphics g) {

		g.setColor(new Color(50, 50, 50));
		g.fillRect(x, y, width, height);

		g.setColor(Res.COLOR_RESOURCE.SIEGE_POINTS_BG);
		g.fillRect(x, y + 20, width, 10);

		Res.bitFont.drawString(x + 10, y + 22, underglowInfo.getSiegePrice() + "");

		underglow.getAnimation().draw(x + 4, y - 1, 24, 24);

		if (getBounds().contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			g.setColor(new Color(200, 200, 200));
		} else {
			g.setColor(new Color(100, 100, 100));
		}

		if (owned) {
			g.setColor(new Color(30, 30, 30, 255));
			g.fillRect(x, y + 20, width, height - 20);
			Res.bitFont.drawString(x + 6, y + 23, "Owned", Color.green);
		}

		if (selected && !MainMenuManager.getMainMenu().getStoreUI().getUnderglowTab().anyHoverOver()) {
			renderPreview(gc, g);
		}

		if (hoverOver) {
			renderPreview(gc, g);
			g.setColor(Color.white);
		}

		if (selected) {
			g.setColor(Color.green);
		}

		g.drawRect(x, y, width, height);

	}

	public void renderPreview(GameContainer gc, Graphics g) {

		float x = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getX();
		float y = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getY();
		float width = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getWidth();
		float height = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getHeight();

		g.setColor(new Color(30, 30, 30));
		g.fillRect(x, y, width, height);

		g.setColor(Res.COLOR_RESOURCE.getDefendersColor(60));
		g.fillRect(x, y, width, 43);

		int centerOfUnderglowName = (int) Res.bitFont.getWidth(underglowInfo.getName());
		Res.bitFont.drawString(x + width / 2 - centerOfUnderglowName, y + 3, underglowInfo.getName());

		int centerOfUnderglow = (int) Res.bitFont.getWidth("Underglow");
		Res.bitFont.drawString(x + width / 2 - centerOfUnderglow, y + 9, "Underglow");

		underglow.getAnimation().draw(x + 5, y + 8, 40, 40, new Color(255, 255, 255, glowTimer.getTimer()));

		if (currency == -1) {

			g.setColor(new Color(40, 40, 40));
			g.fillRect(x, y + 89, width, 37);
			int centerOfPurchase = (int) Res.bitFont.getWidth("Purchase");
			Res.bitFont.drawString(x + width / 2 - centerOfPurchase, y + 94, "Purchase");

			g.setColor(Res.COLOR_RESOURCE.SIEGE_POINTS_BG);
			g.fillRect(x, y + 106, width, 10);

			Res.bitFont.drawString(x + 11, y + 108, underglowInfo.getSiegePrice() + "");

			g.setColor(new Color(55, 55, 55));
			g.fillRect(x, y + 43, width, 46);

			underglow.getAnimation().draw(x + 9, y + 62, 32, 32, new Color(255, 255, 255, glowTimer.getTimer()));
			displayEntities.get(randomEntityIndex).getAnimation().getWalkDown().draw(x + 1, y + 40, 48, 48);

		}

		else if (currency == Currency.SIEGE_POINTS) {

			g.setColor(Res.COLOR_RESOURCE.SIEGE_POINTS_BG);
			g.fillRect(x, y + 53, width, 10);

			Res.bitFont.drawString(x + 10, y + 55, underglowInfo.getSiegePrice() + "");

			/////////////////////

			int currentSiegePoints = LobbyManager.getManager().getUserAccount().getSiegePoints();
			int itemCost = underglowInfo.getSiegePrice();
			int finalSiegePoints = currentSiegePoints - itemCost;

			Color color = Color.green;

			if (finalSiegePoints < 0) {
				color = Color.red;
			}

			g.setColor(new Color(15, 15, 15));
			g.fillRect(x, y + 89, width, 37);

			int centerOfConfirm = (int) Res.bitFont.getWidth("Confirm");
			Res.bitFont.drawString(x + width / 2 - centerOfConfirm, y + 45, "Confirm", Color.green);

			int centerOfCurrentSiegePoints = (int) Res.bitFont.getWidth(currentSiegePoints + "");
			Res.bitFont.drawString(x + width / 2 - centerOfCurrentSiegePoints, y + 93, currentSiegePoints + "",
					Color.green);

			g.setColor(new Color(90, 40, 40));
			g.fillRect(x, y + 102, width, 10);
			Res.bitFont.drawString(x + 2, y + 102, "-", Color.red, 1);

			int centerOfItemCost = (int) Res.bitFont.getWidth(itemCost + "");
			Res.bitFont.drawString(x + width / 2 - centerOfItemCost, y + 104, itemCost + "", Color.red);

			Res.bitFont.drawString(x + 2, y + 114, "=", Color.white, 1);
			int centerOfFinalBits = (int) Res.bitFont.getWidth(finalSiegePoints + "");
			Res.bitFont.drawString(x + width / 2 - centerOfFinalBits, y + 116, finalSiegePoints + "", color);

		}

		if (owned) {
			g.setColor(new Color(50, 50, 50, 255));
			g.fillRect(x, y + 89, width, 37);
			Res.bitFont.drawString(x + 15, y + 105, "Owned", Color.green);
		}

		if (currency == -1) {
			if (!owned) {
				if (new Rectangle(x, y + 106, width, 10).contains(gc.getInput().getMouseX(),
						gc.getInput().getMouseY())) {
					g.setColor(Color.green);
					g.drawRect(x, y + 106, width, 10);
				}
			}
		} else {
			purchaseButton.render(g, 1, gc);
			cancelButton.render(g, 1, gc);
		}

		g.setColor(Res.COLOR_RESOURCE.DEFENDERS);
		g.drawRect(x, y + 43, width, height - 80);

		g.setColor(Res.COLOR_RESOURCE.DEFENDERS);
		g.drawRect(x, y, width, 43);

		g.setColor(Res.COLOR_RESOURCE.DEFENDERS);
		g.drawRect(x, y + 89, width, 37);

	}

	public Rectangle getBounds() {
		return new Rectangle(x - 2, y, width + 7, height);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public StoreItemUnderglow getUnderglowInfo() {
		return underglowInfo;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isHoverOver() {
		return hoverOver;
	}

	public void setHoverOver(boolean hoverOver) {
		this.hoverOver = hoverOver;
	}

	public boolean isOwned() {
		return owned;
	}

	public void setOwned(boolean owned) {
		this.owned = owned;
	}

}
