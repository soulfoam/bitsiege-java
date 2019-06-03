package soulfoam.arena.main.menu.profile;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame; 

import soulfoam.arena.main.menu.BaseMenuUI;
import soulfoam.arena.main.menu.BaseTab;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class ProfileUI extends BaseMenuUI {

	private Rectangle avatarTabButton;
	private Rectangle detailsTabButton;
	private Rectangle changePassButton;
	private Rectangle changeEmailButton;
	
	private ProfileUIDetailsTab detailsTab;
	private ProfileUIAvatarTab avatarTab;

	private ProfileUIPasswordTab passwordTab;
	private ProfileUIEmailTab emailTab;

	private int render;
	
	public ProfileUI(GameContainer gc) {

		setWidth(380);
		setHeight(240);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2 + 9);
		
		avatarTabButton = new Rectangle(getX() + 1, getY() + 11, 78, 9);
		detailsTabButton = new Rectangle(getX() + 81, getY() + 11, 78, 9);
		changePassButton = new Rectangle(getX() + 161, getY() + 11, 78, 9);
		changeEmailButton = new Rectangle(getX() + 241, getY() + 11, 78, 9);
		
		avatarTab = new ProfileUIAvatarTab(gc, getX(), getY() + 17);
		detailsTab = new ProfileUIDetailsTab(gc, getX(), getY() + 17);
		passwordTab = new ProfileUIPasswordTab(gc, getX(), getY() + 17);
		emailTab = new ProfileUIEmailTab(gc, getX(), getY() + 17);
		
		toggleTab(0);
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {
		
		if (avatarTab.isActive()) {
			avatarTab.update(gc, delta);
		}
		
		if (detailsTab.isActive()) {
			detailsTab.update(gc, delta);
		}
		
		if (passwordTab.isActive()) {
			passwordTab.update(gc, delta);
		}
		
		if (emailTab.isActive()) {
			emailTab.update(gc, delta);
		}

	}

	public void toggleTab(int tab) {
		render = tab;
		if (tab == 0) {
			avatarTab.setActive(true);
			detailsTab.setActive(false);
			passwordTab.setActive(false);
			emailTab.setActive(false);
		}
		if (tab == 1) {
			detailsTab.setActive(true);
			avatarTab.setActive(false);
			passwordTab.setActive(false);
			emailTab.setActive(false);
		}
		if (tab == 2) {
			avatarTab.setActive(false);
			detailsTab.setActive(false);
			passwordTab.setActive(true);
			emailTab.setActive(false);
		}
		if (tab == 3) {
			avatarTab.setActive(false);
			detailsTab.setActive(false);
			passwordTab.setActive(false);
			emailTab.setActive(true);
		}
	}

	public void render(GameContainer gc, Graphics g) {

		if (isRendering()) {
			renderWindow(g);

			if (detailsTab.isActive()) {
				detailsTab.render(gc, g);
			}
			if (avatarTab.isActive()) {
				avatarTab.render(gc, g);
			}
			if (passwordTab.isActive()) {
				passwordTab.render(gc, g);
			}
			if (emailTab.isActive()) {
				emailTab.render(gc, g);
			}
			
			if (detailsTabButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_FRIEND_UI_SELECT.draw(detailsTabButton.getX(), detailsTabButton.getY());
			}
			if (avatarTabButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_FRIEND_UI_SELECT.draw(avatarTabButton.getX(), avatarTabButton.getY());
			}
			if (changePassButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_FRIEND_UI_SELECT.draw(changePassButton.getX(), changePassButton.getY());
			}
			if (changeEmailButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				Res.UI_RESOURCE.MAINMENU_FRIEND_UI_SELECT.draw(changeEmailButton.getX(), changeEmailButton.getY());
			}

		}
	}

	public void renderWindow(Graphics g) {
		
		if (render == 0){
			Res.UI_RESOURCE.MAINMENU_PROFILE_UI_AVATAR.draw(getX(), getY());
		}
		if (render == 1){
			Res.UI_RESOURCE.MAINMENU_PROFILE_UI_PROFILE.draw(getX(), getY());
		}
		if (render == 2){
			Res.UI_RESOURCE.MAINMENU_PROFILE_UI_PASSWORD.draw(getX(), getY());
		}
		if (render == 3){
			Res.UI_RESOURCE.MAINMENU_PROFILE_UI_EMAIL.draw(getX(), getY());
		}
		
		String headerText = "Profile - " + LobbyManager.getManager().getUserAccount().getUsername();
		Res.bitFont.drawString(getX() - Res.getTextCenter(headerText) + getWidth() / 2, getY() + 3, headerText,
				Color.white);
	}

	public void mousePressed(int button, int x, int y) {
		if (button == Input.MOUSE_LEFT_BUTTON) {
			if (avatarTabButton.contains(x, y)) {
				toggleTab(0);
			}
			if (detailsTabButton.contains(x, y)) {
				toggleTab(1);
			}
			if (changePassButton.contains(x, y)) {
				toggleTab(2);
			}
			if (changeEmailButton.contains(x, y)) {
				toggleTab(3);
			}

		}
	}

	public void setWindow(boolean set) {
		setFocus(set);
		setRender(set);
		avatarTab.setAvatarPreviewToAccountAvatar();
		toggleTab(0);
	}

	public ProfileUIAvatarTab getAvatarTab() {
		return avatarTab;
	}

	public ProfileUIDetailsTab getDetailsTab() {
		return detailsTab;
	}

	public ProfileUIPasswordTab getPasswordTab() {
		return passwordTab;
	}

	public ProfileUIEmailTab getEmailTab() {
		return emailTab;
	}

	public Rectangle getChangeEmailButton() {
		return changeEmailButton;
	}

	public void setChangeEmailButton(Rectangle changeEmailButton) {
		this.changeEmailButton = changeEmailButton;
	}

}
