package soulfoam.arena.main.menu.store;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.EntityBook;
import soulfoam.arena.entities.challengers.tooltips.ChallengerToolTip;
import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.store.Currency;
import soulfoam.arenashared.main.store.StoreItemChallenger;

public class StoreUIChallengerObject {

	private float x, y, width, height;
	private StoreItemChallenger challengerInfo;
	private Entity challengerEntity;
	private boolean selected;
	private boolean hoverOver;
	private ChallengerToolTip toolTip;
	private boolean owned;

	public StoreUIChallengerObject(StoreItemChallenger challengerInfo, float x, float y) {
		this.x = x;
		this.y = y;
		width = 47;
		height = 83;
		this.challengerInfo = challengerInfo;
		challengerEntity = EntityBook.getChallengerByID(challengerInfo.getID(), 0, 0, 0, -1);
		toolTip = ChallengerToolTip.getChallengerToolTip(challengerInfo.getID());
	}

	public void update(GameContainer gc, int delta) {
		if (getBounds().contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				MainMenuManager.getMainMenu().getStoreUI().getChallengerTab().selectChallenger(this);
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
		
		Res.UI_RESOURCE.MAINMENU_STORE_UI_STORE_OBJECT.draw(x, y);
		
		if (owned) {
			Res.UI_RESOURCE.MAINMENU_STORE_UI_STORE_OBJECT_BLANK.draw(x, y);
			Res.bitFont.drawString(x + 7, y + 58, "Owned", Res.COLOR_RESOURCE.GREEN_TEXT);
		}
		else{
			Res.bitFont.drawString(x + 17, y + 48, challengerInfo.getSiegePrice() + "");
			Res.bitFont.drawString(x + 17, y + 69, challengerInfo.getBitPrice() + "");
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
		
		int userFinalSiegePoints = (LobbyManager.getManager().getUserAccount().getSiegePoints() - challengerInfo.getSiegePrice());
		int userFinalBits = (LobbyManager.getManager().getUserAccount().getBits() - challengerInfo.getBitPrice());
		
		if (!owned){
			Res.UI_RESOURCE.MAINMENU_STORE_UI_CHALLENGERS_DISPLAY.draw(x, y);
		}
		else{
			Res.UI_RESOURCE.MAINMENU_STORE_UI_CHALLENGERS_DISPLAY_BLANK.draw(x, y);
			Res.bitFont.drawString(x + 23, y + 104, "Owned", Res.COLOR_RESOURCE.GREEN_TEXT);
		}
		
		Res.bitFont.drawString(x - Res.getTextCenter(challengerInfo.getName()) + width / 2, y + 3, challengerInfo.getName());
		
		challengerEntity.getAnimation().getDefaultPortrait().draw(x + 22, y + 16, 32, 32);
		
		challengerEntity.getAbility1().getSkillIcon().draw(x + 3, y + 58, 16, 16);
		challengerEntity.getAbility2().getSkillIcon().draw(x + 21, y + 58, 16, 16);
		challengerEntity.getAbility3().getSkillIcon().draw(x + 39, y + 58, 16, 16);
		challengerEntity.getAbility4().getSkillIcon().draw(x + 57, y + 58, 16, 16);
		
		if (!owned){
			if (new Rectangle(x + 1, y + 92, 74, 18).contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_STORE_UI_DISPLAY_SELECT.draw(x + 1, y + 92);
				
				String minusSPText = "-" + challengerInfo.getSiegePrice() + " =";
				
				if (userFinalSiegePoints < 0){
					Res.bitFont.drawString(x - Res.getTextCenter("Not Enough") + width / 2, y + 139, "Not Enough", Color.red);
					Res.bitFont.drawString(x - Res.getTextCenter("Siege Points") + width / 2, y + 147, "Siege Points", Color.red);
				}
				else{
					Res.bitFont.drawString(x - Res.getTextCenter(minusSPText) + width / 2, y + 138, minusSPText, Color.red);
					Res.bitFont.drawString(x - Res.getTextCenter(userFinalSiegePoints + " ") + width / 2, y + 149, userFinalSiegePoints + " ", Color.green);
				}
				
			}	 
			if (new Rectangle(x + 1, y + 113, 74, 18).contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_STORE_UI_DISPLAY_SELECT.draw(x + 1, y + 113);
				
				String minusBText = "-" + challengerInfo.getBitPrice() + " =";
				
				if (userFinalBits < 0){
					Res.bitFont.drawString(x - Res.getTextCenter("Not Enough") + width / 2, y + 139, "Not Enough", Color.red);
					Res.bitFont.drawString(x - Res.getTextCenter("Bits") + width / 2, y + 147, "Bits", Color.red);
				}
				else{
					Res.bitFont.drawString(x - Res.getTextCenter(minusBText) + width / 2, y + 138, minusBText, Color.red);
					Res.bitFont.drawString(x - Res.getTextCenter(userFinalBits + " ") + width / 2, y + 149, userFinalBits + " ", Color.green);
				}
			}	
			
			Res.bitFont.drawString(x + 20, y + 98, challengerInfo.getSiegePrice() + "");
			Res.bitFont.drawString(x + 20, y + 119, challengerInfo.getBitPrice() + "");
		}

	}
	
	public void mousePressed(int button, int x, int y) {
		if (selected){
			if (!owned){
				if (button == Input.MOUSE_LEFT_BUTTON) {
					float ipaX = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getX();
					float ipaY = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getY();
					if (new Rectangle(ipaX + 1, ipaY + 92, 74, 18).contains(x, y)) {
						LobbyManager.getManager().getStoreManager().buyChallenger(challengerInfo.getID(), Currency.SIEGE_POINTS);

					}	 
					if (new Rectangle(ipaX + 1, ipaY + 113, 74, 18).contains(x, y)) {
						LobbyManager.getManager().getStoreManager().buyChallenger(challengerInfo.getID(), Currency.BITS);
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

	public StoreItemChallenger getChallengerInfo() {
		return challengerInfo;
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
