package soulfoam.arena.main.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.ClientEngine;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class NavigationBarUI extends BaseMenuUI {

	private Rectangle playButton;
	private Rectangle newsButton;
	private Rectangle storeButton;
	private Rectangle settingsButton;
	private Rectangle quitButton;

	public NavigationBarUI() {
		setWidth(39);
		setHeight(GameInfo.RES_HEIGHT);
		setX(-1);
		setY(0);

		playButton = new Rectangle(getX() + 3, getY() + 45, 40, 16);
		newsButton = new Rectangle(getX() + 3, getY() + 63, 40, 16);
		storeButton = new Rectangle(getX() + 3, getY() + 81, 40, 16);
		settingsButton = new Rectangle(getX() + 3, getY() + 99, 40, 16);

		quitButton = new Rectangle(getX() + 3, getY() + 249, 40, 19);
		
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {

	}

	public void render(GameContainer gc, Graphics g) {

		if (isRendering()) {
			
			if (playButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_BUTTON_SELECT.draw(playButton.getX(), playButton.getY());
			}
			if (newsButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_BUTTON_SELECT.draw(newsButton.getX(), newsButton.getY());
			}
			if (storeButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_BUTTON_SELECT.draw(storeButton.getX(), storeButton.getY());
			}
			if (settingsButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_BUTTON_SELECT.draw(settingsButton.getX(), settingsButton.getY());
			}
			if (quitButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_BUTTON_SELECT.draw(quitButton.getX(), quitButton.getY(), 40, 19, Color.red);
			}

			Res.bitFont.drawString(getX() + 11, getY() + 149, String.format("%04d", LobbyManager.getManager().getServerInfoManager().getPlayersOnline()), Res.COLOR_RESOURCE.GREEN_TEXT);

		}
	}

	public void mousePressed(int button, int x, int y) {
		if (button == Input.MOUSE_LEFT_BUTTON) {
			if (playButton.contains(x, y)) {
				MainMenuManager.getMainMenu().setWindowUI(MainMenuManager.getMainMenu().getPlayUI());
				MainMenuManager.getMainMenu().setSelectedButton(playButton);
			}
			if (newsButton.contains(x, y)) {
				MainMenuManager.getMainMenu().setWindowUI(MainMenuManager.getMainMenu().getNewsUI());
				MainMenuManager.getMainMenu().setSelectedButton(newsButton);
			}
			if (storeButton.contains(x, y)) {
				MainMenuManager.getMainMenu().setWindowUI(MainMenuManager.getMainMenu().getStoreUI());
				MainMenuManager.getMainMenu().setSelectedButton(storeButton);
			}
			if (settingsButton.contains(x, y)) {
				MainMenuManager.getMainMenu().setWindowUI(MainMenuManager.getMainMenu().getSettingsUI());
				MainMenuManager.getMainMenu().setSelectedButton(settingsButton);
			}
			if (quitButton.contains(x, y)) {
				LobbyManager.getManager().logoutUserAccount();
				ClientEngine.app.exit();
			}
		}
	}
	
	public Rectangle getStoreButton(){
		return storeButton;
	}
}
