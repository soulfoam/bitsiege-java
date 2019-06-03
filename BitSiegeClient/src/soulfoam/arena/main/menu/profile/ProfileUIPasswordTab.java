package soulfoam.arena.main.menu.profile;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.TextField;

import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class ProfileUIPasswordTab {

	private float x, y, width, height;
	private boolean isActive;

	private TextField currentPasswordField;
	private TextField passwordField;
	private TextField confirmPasswordField;

	private MenuButton saveButton;

	private int tabIndex;

	public ProfileUIPasswordTab(GameContainer gc, float x, float y) {
		setWidth(380);
		setHeight(240);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2 + 9);

		setCurrentPasswordField(new TextField(gc, Res.bitFont, getX() + 78, getY() + 63, 224, 7));
		setPasswordField(new TextField(gc, Res.bitFont, getX() + 78, getY() + 122, 224, 7));
		setConfirmPasswordField(new TextField(gc, Res.bitFont, getX() + 78, getY() + 162, 224, 7));
		setSaveButton(new MenuButton("Save Changes", getX() + 116, getY() + 213, 142, 11));

		currentPasswordField.makeOpaque();
		passwordField.makeOpaque();
		confirmPasswordField.makeOpaque();
		
		currentPasswordField.setMaskEnabled(true);
		passwordField.setMaskEnabled(true);
		confirmPasswordField.setMaskEnabled(true);

	}

	public void update(GameContainer gc, int delta) {
		
		if (getSaveButton().isClicked()) {
			if (!getPasswordField().getText().isEmpty() && !getConfirmPasswordField().getText().isEmpty()
					&& !getCurrentPasswordField().getText().isEmpty()) {
				LobbyManager.getManager().getAccountManager().changePassword(getCurrentPasswordField().getText(),
						getPasswordField().getText(), getConfirmPasswordField().getText());
			}
		}

		if (getCurrentPasswordField().hasFocus()) {
			setTabIndex(0);
		}
		if (getPasswordField().hasFocus()) {
			setTabIndex(1);
		}
		if (getConfirmPasswordField().hasFocus()) {
			setTabIndex(2);
		}

		if (gc.getInput().isKeyPressed(Input.KEY_TAB)) {

			setTabIndex(getTabIndex() + 1);

			if (getTabIndex() > 2) {
				setTabIndex(0);
			}

			if (getTabIndex() == 0) {
				getCurrentPasswordField().setFocus(true);
				getCurrentPasswordField().setCursorPos(getCurrentPasswordField().getText().length());
			}

			if (getTabIndex() == 1) {
				getPasswordField().setFocus(true);
				getPasswordField().setCursorPos(getPasswordField().getText().length());
			}

			if (getTabIndex() == 2) {
				getConfirmPasswordField().setFocus(true);
				getConfirmPasswordField().setCursorPos(getConfirmPasswordField().getText().length());
			}

		}

		getSaveButton().update(gc);
	}

	public void render(GameContainer gc, Graphics g) {

		String headerText = LobbyManager.getManager().getAccountManager().getPasswordText();
		Res.bitFont.drawString(getX() - Res.getTextCenter(headerText) + getWidth() / 2, getY() + 26, headerText,
				LobbyManager.getManager().getAccountManager().getPasswordColor());

		getCurrentPasswordField().render(gc, g);
		getPasswordField().render(gc, g);
		getConfirmPasswordField().render(gc, g);

		getSaveButton().render(g, 2, gc);
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;

		getCurrentPasswordField().setFocus(isActive);

		getCurrentPasswordField().setAcceptingInput(isActive);
		getPasswordField().setAcceptingInput(isActive);
		getConfirmPasswordField().setAcceptingInput(isActive);

		clearTextFields();

		LobbyManager.getManager().getAccountManager().setPasswordText(LobbyManager.getManager().getAccountManager().getDefaultPasswordText(), Color.yellow);
	}

	public void clearTextFields() {
		getCurrentPasswordField().setText("");
		getPasswordField().setText("");
		getConfirmPasswordField().setText("");
	}

	public TextField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(TextField passwordField) {
		this.passwordField = passwordField;
	}

	public TextField getConfirmPasswordField() {
		return confirmPasswordField;
	}

	public void setConfirmPasswordField(TextField confirmPasswordField) {
		this.confirmPasswordField = confirmPasswordField;
	}

	public MenuButton getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(MenuButton saveButton) {
		this.saveButton = saveButton;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public TextField getCurrentPasswordField() {
		return currentPasswordField;
	}

	public void setCurrentPasswordField(TextField currentPasswordField) {
		this.currentPasswordField = currentPasswordField;
	}

}
