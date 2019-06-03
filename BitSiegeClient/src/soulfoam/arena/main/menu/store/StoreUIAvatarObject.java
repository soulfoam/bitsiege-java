package soulfoam.arena.main.menu.store;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.store.Currency;
import soulfoam.arenashared.main.store.StoreItemAvatar;

public class StoreUIAvatarObject {

	private float x, y, width, height;
	private StoreItemAvatar avatarInfo;

	private boolean selected;
	private boolean hoverOver;

	private boolean owned;


	public StoreUIAvatarObject(StoreItemAvatar avatarInfo, float x, float y) {
		this.x = x;
		this.y = y;
		width = 47;
		height = 83;
		this.avatarInfo = avatarInfo;

	}

	public void update(GameContainer gc, int delta) {

		if (getBounds().contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				MainMenuManager.getMainMenu().getStoreUI().getAvatarTab().selectAvatarItem(this);
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
			Res.bitFont.drawString(x + 17, y + 48, avatarInfo.getSiegePrice() + "");
			Res.bitFont.drawString(x + 17, y + 69, avatarInfo.getBitPrice() + "");
		}

		Res.AVATAR_RESOURCE.getAvatarImage(avatarInfo.getID(), avatarInfo.getSlotID()).draw(x + 7, y + 5, 32, 32);

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
		
		int userFinalSiegePoints = (LobbyManager.getManager().getUserAccount().getSiegePoints() - avatarInfo.getSiegePrice());
		int userFinalBits = (LobbyManager.getManager().getUserAccount().getBits() - avatarInfo.getBitPrice());
		
		if (!owned){
			Res.UI_RESOURCE.MAINMENU_STORE_UI_AVATARS_DISPLAY.draw(x, y);
		}
		else{
			Res.UI_RESOURCE.MAINMENU_STORE_UI_AVATARS_DISPLAY_BLANK.draw(x, y);
			Res.bitFont.drawString(x + 23, y + 122, "Owned", Res.COLOR_RESOURCE.GREEN_TEXT);
		}

		
		if (!owned){
			if (new Rectangle(x + 1, y + 110, 74, 18).contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_STORE_UI_DISPLAY_SELECT.draw(x + 1, y + 110);
				
				String minusSPText = "-" + avatarInfo.getSiegePrice() + " =";
				
				if (userFinalSiegePoints < 0){
					Res.bitFont.drawString(x - Res.getTextCenter("Not Enough") + width / 2, y + 157, "Not Enough", Color.red);
					Res.bitFont.drawString(x - Res.getTextCenter("Siege Points") + width / 2, y + 165, "Siege Points", Color.red);
				}
				else{
					Res.bitFont.drawString(x - Res.getTextCenter(minusSPText) + width / 2, y + 156, minusSPText, Color.red);
					Res.bitFont.drawString(x - Res.getTextCenter(userFinalSiegePoints + " ") + width / 2, y + 167, userFinalSiegePoints + " ", Color.green);
				}
			}	 
			if (new Rectangle(x + 1, y + 131, 74, 18).contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_STORE_UI_DISPLAY_SELECT.draw(x + 1, y + 131);
				
				String minusBText = "-" + avatarInfo.getBitPrice() + " =";
				
				if (userFinalBits < 0){
					Res.bitFont.drawString(x - Res.getTextCenter("Not Enough") + width / 2, y + 157, "Not Enough", Color.red);
					Res.bitFont.drawString(x - Res.getTextCenter("Bits") + width / 2, y + 165, "Bits", Color.red);
				}
				else{
					Res.bitFont.drawString(x - Res.getTextCenter(minusBText) + width / 2, y + 156, minusBText, Color.red);
					Res.bitFont.drawString(x - Res.getTextCenter(userFinalBits + " ") + width / 2, y + 167, userFinalBits + " ", Color.green);
				}
			}	
			
			Res.bitFont.drawString(x + 20, y + 116, avatarInfo.getSiegePrice() + "");
			Res.bitFont.drawString(x + 20, y + 137, avatarInfo.getBitPrice() + "");
		}
		
		Res.bitFont.drawString(x - Res.getTextCenter(avatarInfo.getName()) + width / 2, y + 3, avatarInfo.getName());

		Res.AVATAR_RESOURCE.getAvatarImage(avatarInfo.getID(), avatarInfo.getSlotID()).draw(x + 22, y + 16, 32, 32);

		if (avatarInfo.getSlotID() == AvatarLibrary.SLOT_BACKGROUND) {
			Res.AVATAR_RESOURCE.getAvatarImage(avatarInfo.getID(), avatarInfo.getSlotID()).draw(x + 22, y + 59, 32, 32);
			LobbyManager.getManager().getUserAccount().getAvatarInfo()[AvatarLibrary.SLOT_BORDER].getImage().draw(x + 22, y + 59, 32, 32);
			LobbyManager.getManager().getUserAccount().getAvatarInfo()[AvatarLibrary.SLOT_ICON].getImage().draw(x + 22, y + 59, 32, 32);
		}
		if (avatarInfo.getSlotID() == AvatarLibrary.SLOT_BORDER) {
			LobbyManager.getManager().getUserAccount().getAvatarInfo()[AvatarLibrary.SLOT_BACKGROUND].getImage().draw(x + 22, y + 59, 32, 32);
			Res.AVATAR_RESOURCE.getAvatarImage(avatarInfo.getID(), avatarInfo.getSlotID()).draw(x + 22, y + 59, 32,32);
			LobbyManager.getManager().getUserAccount().getAvatarInfo()[AvatarLibrary.SLOT_ICON].getImage().draw(x + 22, y + 59, 32, 32);
		}
		if (avatarInfo.getSlotID() == AvatarLibrary.SLOT_ICON) {
			LobbyManager.getManager().getUserAccount().getAvatarInfo()[AvatarLibrary.SLOT_BACKGROUND].getImage().draw(x + 22, y + 59, 32, 32);
			LobbyManager.getManager().getUserAccount().getAvatarInfo()[AvatarLibrary.SLOT_BORDER].getImage().draw(x + 22, y + 59, 32, 32);
			Res.AVATAR_RESOURCE.getAvatarImage(avatarInfo.getID(), avatarInfo.getSlotID()).draw(x + 22, y + 59, 32, 32);
		}

	}
	
	public void mousePressed(int button, int x, int y){
		if (selected){
			if (!owned){
				if (button == Input.MOUSE_LEFT_BUTTON) {
					float ipaX = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getX();
					float ipaY = MainMenuManager.getMainMenu().getStoreUI().getItemPreviewArea().getY();
					if (new Rectangle(ipaX + 1, ipaY + 110, 74, 18).contains(x, y)) {
						LobbyManager.getManager().getStoreManager().buyAvatar(avatarInfo.getID(), avatarInfo.getSlotID(), Currency.SIEGE_POINTS);
					}	 
					if (new Rectangle(ipaX + 1, ipaY + 131, 74, 18).contains(x, y)) {
						LobbyManager.getManager().getStoreManager().buyAvatar(avatarInfo.getID(), avatarInfo.getSlotID(), Currency.BITS);
					}
				}
			}
		}
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

	public StoreItemAvatar getAvatarInfo() {
		return avatarInfo;
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
