package soulfoam.arena.main.menu.store;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.menu.BaseMenuUI;
import soulfoam.arena.main.menu.BaseTab;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;
import soulfoam.arenashared.main.lobbyopcode.LobbyReturnCode;

public class StoreUI extends BaseMenuUI {

	private Rectangle challengersTabButton;
	private Rectangle skinsTabButton;
	private Rectangle avatarsTabButton;
	
	private StoreUIChallengerTab challengerTab;
	private StoreUISkinTab skinTab;
	private StoreUIFlagTab flagTab;
	private StoreUIAvatarTab avatarTab;
	
	private Image backgroundImage = Res.UI_RESOURCE.MAINMENU_STORE_UI_CHALLENGERS;

	public StoreUI() {

		setWidth(380);
		setHeight(240);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2 + 9);

		challengersTabButton = new Rectangle(getX() + 1, getY() + 29, 77, 9);
		skinsTabButton = new Rectangle(getX() + 80, getY() + 29, 77, 9);
		avatarsTabButton = new Rectangle(getX() + 159, getY() + 29, 77, 9);

		challengerTab = new StoreUIChallengerTab(getX(), getY() + 33);
		skinTab = new StoreUISkinTab(getX(), getY() + 33);
		flagTab = new StoreUIFlagTab(getX(), getY() + 33);
		avatarTab = new StoreUIAvatarTab(getX(), getY() + 33);
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {

		if (challengerTab.isActive()) {
			challengerTab.update(gc, delta);
		}
		if (skinTab.isActive()) {
			skinTab.update(gc, delta);
		}
		if (flagTab.isActive()) {
			flagTab.update(gc, delta);
		}
		if (avatarTab.isActive()) {
			avatarTab.update(gc, delta);
		}

	}

	public void toggleTab(int tab) {
		if (tab == 0) {
			challengerTab.setActive(true);
			skinTab.setActive(false);
			avatarTab.setActive(false);
			
			flagTab.setActive(false);
		}
		if (tab == 1) {
			challengerTab.setActive(false);
			skinTab.setActive(true);
			avatarTab.setActive(false);
			
			flagTab.setActive(false);
		}
		if (tab == 2) {
			challengerTab.setActive(false);
			skinTab.setActive(false);
			avatarTab.setActive(true);
			
			flagTab.setActive(false);
			
		}

	}

	public void setWindow(boolean set) {
		setFocus(set);
		setRender(set);
		setOwnedItems();
		LobbyManager.getManager().getStoreManager()
				.setStoreText(LobbyManager.getManager().getStoreManager().getDefaultStoreText(), Color.yellow);
		toggleTab(0);
	}

	public void setOwnedItems() {
		challengerTab.initPages();
		skinTab.initPages();
		flagTab.initPages();
		avatarTab.setOwnedAvatars(LobbyReturnCode.STORE_BOUGHT_SUCCESS_AVATAR_BG);
	}

	public void render(GameContainer gc, Graphics g) {

		if (isRendering()) {
			renderWindow(g);

			if (challengerTab.isActive()) {
				challengerTab.render(gc, g);
			}
			if (skinTab.isActive()) {
				skinTab.render(gc, g);
			}
			if (flagTab.isActive()) {
				flagTab.render(gc, g);
			}
			if (avatarTab.isActive()) {
				avatarTab.render(gc, g);
			}
			
			if (challengersTabButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_STORE_UI_TAB_SELECT.draw(challengersTabButton.getX(), challengersTabButton.getY());
			}
			if (skinsTabButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_STORE_UI_TAB_SELECT.draw(skinsTabButton.getX(), skinsTabButton.getY());
			}
			if (avatarsTabButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_STORE_UI_TAB_SELECT.draw(avatarsTabButton.getX(), avatarsTabButton.getY());
			}

		}
		
	}

	public void renderWindow(Graphics g) {
		
		backgroundImage.draw(getX(), getY());
		
		String headerText = "Store";
		Res.bitFont.drawString(getX() - Res.getTextCenter(headerText) + getWidth() / 2, getY() + 3, headerText, Color.white);

		Res.bitFont.drawString(getX() - Res.getTextCenter(LobbyManager.getManager().getStoreManager().getStoreText()) + getWidth() / 2, getY() + 17, LobbyManager.getManager().getStoreManager().getStoreText(),
				LobbyManager.getManager().getStoreManager().getStoreColor());

	}

	public void mousePressed(int button, int x, int y) {
		if (button == Input.MOUSE_LEFT_BUTTON) {
			if (challengersTabButton.contains(x, y)) {
				toggleTab(0);
			}
			if (skinsTabButton.contains(x, y)) {
				toggleTab(1);
			}
			if (avatarsTabButton.contains(x, y)) {
				toggleTab(2);
			}
		}

		if (challengerTab.isActive()){
			challengerTab.mousePressed(button, x, y);
		}
		if (skinTab.isActive()){
			skinTab.mousePressed(button, x, y);
		}
		if (avatarTab.isActive()){
			avatarTab.mousePressed(button, x, y);
		}
		if (flagTab.isActive()){
			flagTab.mousePressed(button, x, y);
		}
	}

	public void mouseWheelMoved(int m) {
		challengerTab.mouseWheelMoved(m);
		skinTab.mouseWheelMoved(m);
		flagTab.mouseWheelMoved(m);
		avatarTab.mouseWheelMoved(m);

	}

	public StoreUIChallengerTab getChallengerTab() {
		return challengerTab;
	}

	public Rectangle getItemPreviewArea() {
		return new Rectangle(getX() + 300, getY() + 41, Res.UI_RESOURCE.MAINMENU_STORE_UI_CHALLENGERS_DISPLAY.getWidth(), Res.UI_RESOURCE.MAINMENU_STORE_UI_CHALLENGERS_DISPLAY.getHeight());
	}

	public StoreUISkinTab getSkinTab() {
		return skinTab;
	}

	public StoreUIFlagTab getUnderglowTab() {
		return flagTab;
	}

	public StoreUIAvatarTab getAvatarTab() {
		return avatarTab;
	}

	public Image getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

}
