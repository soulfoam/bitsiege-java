package soulfoam.arena.main.menu.friend;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.ClientEngine;
import soulfoam.arena.main.menu.BaseMenuUI;
import soulfoam.arena.main.menu.BaseTab;
import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class FriendUI extends BaseMenuUI {

	private Rectangle baseFriendsTab;
	private Rectangle baseAddFriendTab;

	private FriendUIFriendTab friendTab;
	private FriendUIAddTab addFriendTab;
	
	private int render = 0;

	public FriendUI(GameContainer gc) {

		setWidth(380);
		setHeight(240);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2 + 9);
		
		baseFriendsTab = new Rectangle(getX() + 1, getY() + 11, 78, 9);
		baseAddFriendTab = new Rectangle(getX() + 81, getY() + 11, 78, 9);
		
		friendTab = new FriendUIFriendTab(getX(), getY() + 17);
		addFriendTab = new FriendUIAddTab(gc, getX(), getY() + 17);

		toggleTab(0);

	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {
		
		if (getFriendTab().isActive()) {
			getFriendTab().update(gc, delta);
		}
		if (getAddFriendTab().isActive()) {
			getAddFriendTab().update(gc, delta);
		}
	}

	public void toggleTab(int tab) {
		render = tab;
		if (tab == 0) {
			getFriendTab().setActive(true);
			getAddFriendTab().setActive(false);
		}
		if (tab == 1) {
			getFriendTab().setActive(false);
			getAddFriendTab().setActive(true);
		}
	}

	public void setWindow(boolean set) {
		setFocus(set);
		setRender(set);
		getFriendTab().setPageIndex(0);
		toggleTab(0);
	}

	public void render(GameContainer gc, Graphics g) {

		if (isRendering()) {
			renderWindow(g);

			if (getAddFriendTab().isActive()) {
				getAddFriendTab().render(gc, g);
			}
			if (getFriendTab().isActive()) {
				getFriendTab().render(gc, g);
			}
			
			if (baseFriendsTab.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_FRIEND_UI_SELECT.draw(baseFriendsTab.getX(), baseFriendsTab.getY());
			}
			if (baseAddFriendTab.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_FRIEND_UI_SELECT.draw(baseAddFriendTab.getX(), baseAddFriendTab.getY());
			}
		}
	}

	public void renderWindow(Graphics g) {
		
		if (render == 0){
			Res.UI_RESOURCE.MAINMENU_FRIEND_UI_LIST.draw(getX(), getY());
		}
		else if (render == 1){
			Res.UI_RESOURCE.MAINMENU_FRIEND_UI_ADD.draw(getX(), getY());
		}
		String headerText = "Friends (" + LobbyManager.getManager().getUserAccount().getFriendsOnlineCount() + "/" + LobbyManager.getManager().getUserAccount().getFriends().size() + ")";
		Res.bitFont.drawString(getX() + getWidth()/2 - Res.getTextCenter(headerText), getY() + 3, headerText, Color.white);

	}

	public void mousePressed(int button, int x, int y) {
		if (button == Input.MOUSE_LEFT_BUTTON) {
			if (baseFriendsTab.contains(x, y)) {
				toggleTab(0);
			}
			if (baseAddFriendTab.contains(x, y)) {
				toggleTab(1);
			}

		}
	}

	public FriendUIAddTab getAddFriendTab() {
		return addFriendTab;
	}

	public FriendUIFriendTab getFriendTab() {
		return friendTab;
	}

}
