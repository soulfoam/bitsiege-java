package soulfoam.arena.main.menu.login;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.SettingManager;
import soulfoam.arena.main.menu.BaseMenuUI;
import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.account.AccountValues;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class LoginUI extends BaseMenuUI {

	public MenuButton loginButton;
	public MenuButton registerButton;
	public MenuButton exitButton;
	public MenuButton settingsButton;

	public TextField usernameField;
	public TextField passwordField;

	public boolean foundUpdate;
	public String serverVer;

	private boolean setFocusOnStart;

	public LoginUI(GameContainer gc) {
		setWidth(265);
		setHeight(166);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2);
		loginButton = new MenuButton("Login", getX() + 72, getY() + 90, 124, 10);
		registerButton = new MenuButton("Register Account", getX() + 72, getY() + 121, 124, 10);
		settingsButton = new MenuButton("Settings", getX() + 72, getY() + 136, 124, 10);
		exitButton = new MenuButton("Exit Game", getX() + 72, getY() + 151, 124, 10);
		
		usernameField = new TextField(gc, Res.bitFont, getX() + 38, getY() + 45, 184, 7);
		usernameField.makeOpaque();
		usernameField.setMaxLength(AccountValues.MAX_USERNAME_LENGTH);
		
		passwordField = new TextField(gc, Res.bitFont, getX() + 38, getY() + 75, 184, 7);
		passwordField.makeOpaque();
		passwordField.setMaskEnabled(true);

		MainMenuManager.getMainMenu().setWindowUI(this);

	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {
		
		if (!setFocusOnStart) {
			if (!SettingManager.CONFIG_USERNAME.isEmpty()) {
				usernameField.setText(SettingManager.CONFIG_USERNAME);
				usernameField.setCursorPos(SettingManager.CONFIG_USERNAME.length());
				passwordField.setFocus(true);
				usernameField.setFocus(false);
			} else {
				usernameField.setFocus(true);
				passwordField.setFocus(false);
			}
			setFocusOnStart = true;
		}

		loginButton.update(gc);
		registerButton.update(gc);
		exitButton.update(gc);
		settingsButton.update(gc);

		if (loginButton.isClicked()) {
			attemptLogin();
		}

		if (registerButton.isClicked()) {
			MainMenuManager.getMainMenu().getRegisterUI().setWindow(true);
			setWindow(false);
		}

		if (exitButton.isClicked()) {
			gc.exit();
		}

		if (settingsButton.isClicked()) {
			MainMenuManager.getMainMenu().setWindowUI(MainMenuManager.getMainMenu().getSettingsUI());
			setWindow(false);
		}

		if (gc.getInput().isKeyPressed(Input.KEY_TAB)) {
			if (usernameField.hasFocus()) {
				passwordField.setFocus(true);
				passwordField.setCursorPos(passwordField.getText().length());
				usernameField.setFocus(false);
			} else {
				usernameField.setFocus(true);
				usernameField.setCursorPos(usernameField.getText().length());
				passwordField.setFocus(false);
			}
		}

		if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
			attemptLogin();
		}

	}

	private void attemptLogin(){
		LobbyManager.getManager().getLoginManager().setLoginText("Attempting to login...", Color.yellow);
		LobbyManager.getManager().getLoginManager().loginAccount(usernameField.getText().trim(), passwordField.getText().trim());
		SettingManager.setConfigFile("username", usernameField.getText().trim());
	}
	
	public void render(GameContainer gc, Graphics g) {

		if (isRendering()) {
		
			renderWindow(g);

			loginButton.render(g, 2, gc);
			registerButton.render(g, 2, gc);
			exitButton.render(g, 2, gc);
			settingsButton.render(g, 2, gc);

			usernameField.render(gc, g);
			passwordField.render(gc, g);
		}

	}

	public void renderWindow(Graphics g) {
		
		Res.UI_RESOURCE.MAINMENU_LOGIN_UI.draw(getX(), getY());
		
		String headerText = "Login";
		Res.bitFont.drawString(getX() + getWidth() / 2 - Res.getTextCenter(headerText), getY() + 3, headerText, Color.white);

		float centerLoginText = Res.bitFont.getWidth(LobbyManager.getManager().getLoginManager().getLoginText()) / 2;

		Res.bitFont.drawString(getX() + getWidth() / 2 - centerLoginText, getY() + 17,
				LobbyManager.getManager().getLoginManager().getLoginText(),
				LobbyManager.getManager().getLoginManager().getLoginColor());

	}


	public void mousePressed(int button, int x, int y) {

	}

	public void setWindow(boolean set) {
		
		usernameField.setText(SettingManager.CONFIG_USERNAME);
		usernameField.setCursorPos(SettingManager.CONFIG_USERNAME.length());
		
		passwordField.setText("");

		usernameField.setAcceptingInput(set);
		passwordField.setAcceptingInput(set);

		setRender(set);
		setFocus(set);
	}
}
