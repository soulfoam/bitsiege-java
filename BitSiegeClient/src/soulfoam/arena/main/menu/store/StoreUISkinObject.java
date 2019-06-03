package soulfoam.arena.main.menu.store;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.EntityBook;
import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.store.Currency;
import soulfoam.arenashared.main.store.StoreItemChallenger;
import soulfoam.arenashared.main.store.StoreItemSkin;
import soulfoam.arenashared.main.store.StorePrice;

public class StoreUISkinObject {

	private float x, y, width, height;
	private StoreItemSkin skinInfo;
	private StoreItemChallenger challengerInfo;
	private Entity challengerEntity;
	private boolean selected;
	private boolean hoverOver;
	private boolean owned;
	private boolean challengerUnlocked;

	public StoreUISkinObject(StoreItemSkin skinInfo, float x, float y) {
		this.x = x;
		this.y = y;
		width = 47;
		height = 83;
		this.skinInfo = skinInfo;
		challengerInfo = StorePrice.getPrices().getChallengerStoreItem(skinInfo.getChallengerID());
		challengerEntity = EntityBook.getChallengerByID(skinInfo.getChallengerID(), 0, 0, skinInfo.getID(), -1);

	}

	public void update(GameContainer gc, int delta) {

		if (getBounds().contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				MainMenuManager.getMainMenu().getStoreUI().getSkinTab().selectSkin(this);
			}
			hoverOver = true;
		} else {
			hoverOver = false;
		}

	}

	public void select() {
		selected = true;
	}

	public void deselect() {
		selected = false;
	}

	public void render(GameContainer gc, Graphics g) {
		Res.UI_RESOURCE.MAINMENU_STORE_UI_STORE_OBJECT_SIEGE.draw(x, y);
		
		if (owned) {
			Res.UI_RESOURCE.MAINMENU_STORE_UI_STORE_OBJECT_BLANK.draw(x, y);
			Res.bitFont.drawString(x + 7, y + 58, "Owned", Res.COLOR_RESOURCE.GREEN_TEXT);
		}
		else if(!challengerUnlocked){
			Res.UI_RESOURCE.MAINMENU_STORE_UI_STORE_OBJECT_BLANK.draw(x, y);
			Res.bitFont.drawString(x + 8, y + 54, "Chall", Res.COLOR_RESOURCE.RED_TEXT);
			Res.bitFont.drawString(x + 14, y + 61, "Not", Res.COLOR_RESOURCE.RED_TEXT);
			Res.bitFont.drawString(x + 8, y + 68, "Owned", Res.COLOR_RESOURCE.RED_TEXT);
		}
		else{
			Res.bitFont.drawString(x + 17, y + 58, skinInfo.getSiegePrice() + "");
		}

		challengerEntity.getAnimation().getIdleAnimation().draw(x + 7, y + 5, 32, 32);

		if (getBounds().contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			Res.UI_RESOURCE.MAINMENU_STORE_UI_STORE_OBJECT_SELECT.draw(x, y);
		}

		if (selected && !MainMenuManager.getMainMenu().getStoreUI().getChallengerTab().anyHoverOver()) {
			renderPreview(gc, g);
		}

		if (hoverOver) {
			renderPreview(gc, g);
		}
		
		if (selected){
			Res.UI_RESOURCE.MAINMENU_STORE_UI_STORE_OBJECT_SELECT.draw(x, y, new Color(Color.green));
		}

	}

	public void renderPreview(GameContainer gc, Graphics g) {

		float x = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getX();
		float y = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getY();
		float width = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getWidth();
		float height = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getHeight();
		
		int userFinalSiegePoints = (LobbyManager.getManager().getUserAccount().getSiegePoints() - skinInfo.getSiegePrice());

		if (owned){
			Res.UI_RESOURCE.MAINMENU_STORE_UI_SKINS_DISPLAY_BLANK.draw(x, y);
			Res.bitFont.drawString(x + 23, y + 96, "Owned", Res.COLOR_RESOURCE.GREEN_TEXT);
		}
		else if(!challengerUnlocked){
			Res.UI_RESOURCE.MAINMENU_STORE_UI_SKINS_DISPLAY_BLANK.draw(x, y);
			Res.bitFont.drawString(x + 8, y + 91, "Challenger", Res.COLOR_RESOURCE.RED_TEXT);
			Res.bitFont.drawString(x + 12, y + 98, "Not Owned", Res.COLOR_RESOURCE.RED_TEXT);
		}
		else{
			Res.UI_RESOURCE.MAINMENU_STORE_UI_CHALLENGERS_DISPLAY_SIEGE_POINTS_ONLY.draw(x, y);
		}
		
		Res.bitFont.drawString(x - Res.getTextCenter(skinInfo.getName()) + width / 2, y + 3, skinInfo.getName());
		Res.bitFont.drawString(x - Res.getTextCenter(challengerInfo.getName()) + width / 2, y + 11, challengerInfo.getName());
		
		challengerEntity.getAnimation().getDefaultPortrait().draw(x + 22, y + 24, 32, 32);
		
		if (!owned && challengerUnlocked){
			if (new Rectangle(x + 1, y + 92, 74, 18).contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_STORE_UI_DISPLAY_SELECT.draw(x + 1, y + 92);
				
				String minusSPText = "-" + skinInfo.getSiegePrice() + " =";
				
				if (userFinalSiegePoints < 0){
					Res.bitFont.drawString(x - Res.getTextCenter("Not Enough") + width / 2, y + 121, "Not Enough", Color.red);
					Res.bitFont.drawString(x - Res.getTextCenter("Siege Points") + width / 2, y + 129, "Siege Points", Color.red);
				}
				else{
					Res.bitFont.drawString(x - Res.getTextCenter(minusSPText) + width / 2, y + 120, minusSPText, Color.red);
					Res.bitFont.drawString(x - Res.getTextCenter(userFinalSiegePoints + " ") + width / 2, y + 131, userFinalSiegePoints + " ", Color.green);
				}
			}	 
			
			Res.bitFont.drawString(x + 20, y + 98, skinInfo.getSiegePrice() + "");
		}

	}

	public void mousePressed(int button, int x, int y) {
		if (selected){
			if (!owned){
				if (button == Input.MOUSE_LEFT_BUTTON) {
					float ipaX = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getX();
					float ipaY = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getY();
					if (new Rectangle(ipaX + 1, ipaY + 92, 74, 18).contains(x, y)) {
						LobbyManager.getManager().getStoreManager().buySkin(skinInfo.getChallengerID(), skinInfo.getID());
					}	 
				}
			}
		}
	}
	
	public Rectangle getBounds() {
		return new Rectangle(x, y, width + 3, height);
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

	public StoreItemSkin getSkinInfo() {
		return skinInfo;
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

	public boolean isChallengerUnlocked() {
		return challengerUnlocked;
	}

	public void setChallengerUnlocked(boolean challengerUnlocked) {
		this.challengerUnlocked = challengerUnlocked;
	}

}
