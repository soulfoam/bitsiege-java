package soulfoam.arena.main.menu.login;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.menu.BaseMenuUI;
import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.account.AccountValues;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class RegisterUI extends BaseMenuUI {

	private MenuButton registerButton;
	private MenuButton backButton;

	private TextField usernameField;
	private TextField passwordField;
	private TextField confirmPasswordField;
	private TextField emailField;
	private int tabIndex = 0;

	public RegisterUI(GameContainer gc) {
		setWidth(265);
		setHeight(170);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2);

		registerButton = new MenuButton("Register", getX() + 62, getY() + 153, 141, 10);
		backButton = new MenuButton("<", getX() + 2, getY() + 2, 7, 7);

		usernameField = new TextField(gc, Res.bitFont, getX() + 40, getY() + 45, 187, 7);
		passwordField = new TextField(gc, Res.bitFont, getX() + 40, getY() + 75, 187, 7);
		confirmPasswordField = new TextField(gc, Res.bitFont, getX() + 40, getY() + 105, 187, 7);
		emailField = new TextField(gc, Res.bitFont, getX() + 40, getY() + 135, 187, 7);
		
		usernameField.makeOpaque();
		passwordField.makeOpaque();
		confirmPasswordField.makeOpaque();
		emailField.makeOpaque();
		
		passwordField.setMaskEnabled(true);
		confirmPasswordField.setMaskEnabled(true);
		
		usernameField.setMaxLength(AccountValues.MAX_USERNAME_LENGTH);
		usernameField.setFocus(true);

	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {
		
		registerButton.update(gc);
		backButton.update(gc);

		if (registerButton.isClicked()) {
			LobbyManager.getManager().getRegisterManager().registerAccount(usernameField.getText().trim(), passwordField.getText().trim(), confirmPasswordField.getText().trim(), emailField.getText().trim());
		}

		if (backButton.isClicked()) {
			MainMenuManager.getMainMenu().getLoginUI().setWindow(true);
			setWindow(false);
		}

		if (usernameField.hasFocus()) {
			tabIndex = 0;
		}
		if (passwordField.hasFocus()) {
			tabIndex = 1;
		}
		if (confirmPasswordField.hasFocus()) {
			tabIndex = 2;
		}
		if (emailField.hasFocus()) {
			tabIndex = 3;
		}

		if (gc.getInput().isKeyPressed(Input.KEY_TAB)) {
			tabIndex++;
			if (tabIndex > 3) {
				tabIndex = 0;
			}
			if (tabIndex == 0) {
				usernameField.setFocus(true);
				usernameField.setCursorPos(usernameField.getText().length());
			}
			if (tabIndex == 1) {
				passwordField.setFocus(true);
				passwordField.setCursorPos(passwordField.getText().length());
			}
			if (tabIndex == 2) {
				confirmPasswordField.setFocus(true);
				confirmPasswordField.setCursorPos(confirmPasswordField.getText().length());
			}
			if (tabIndex == 3) {
				emailField.setFocus(true);
				emailField.setCursorPos(emailField.getText().length());
			}
		}

	}

	public void render(GameContainer gc, Graphics g) {
		
		if (isRendering()) {
			
			renderWindow(g);

			registerButton.render(g, 2, gc);
			backButton.render(g, 0, gc);

			usernameField.render(gc, g);
			passwordField.render(gc, g);
			confirmPasswordField.render(gc, g);
			emailField.render(gc, g);

		}

	}

	public void renderWindow(Graphics g) {
		
		Res.UI_RESOURCE.MAINMENU_REGISTER_UI.draw(getX(), getY());

		String headerText = "Register Account";
		Res.bitFont.drawString(getX() + getWidth() / 2 - Res.getTextCenter(headerText), getY() + 3, headerText, Color.white);

		float centerLoginText = Res.bitFont.getWidth(LobbyManager.getManager().getRegisterManager().getRegisterText()) / 2;

		Res.bitFont.drawString(getX() + getWidth() / 2 - centerLoginText, getY() + 17,
				LobbyManager.getManager().getRegisterManager().getRegisterText(),
				LobbyManager.getManager().getRegisterManager().getRegisterColor());

	}

	public void mousePressed(int button, int x, int y) {

	}

	public void setWindow(boolean set) {

		usernameField.setAcceptingInput(set);
		passwordField.setAcceptingInput(set);
		confirmPasswordField.setAcceptingInput(set);
		emailField.setAcceptingInput(set);

		usernameField.setFocus(true);

		setRender(set);
		setFocus(set);
	}
	
	public MenuButton getRegisterButton() {
		return registerButton;
	}

	public TextField getUsernameField() {
		return usernameField;
	}

	public TextField getPasswordField() {
		return passwordField;
	}

	public TextField getConfirmPasswordField() {
		return confirmPasswordField;
	}

	public TextField getEmailField() {
		return emailField;
	}
}
