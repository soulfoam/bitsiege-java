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

public class ProfileUIEmailTab {

	private float x, y, width, height;
	private boolean isActive;

	private TextField currentPasswordField;
	private TextField emailField;
	private TextField confirmEmailField;

	private MenuButton saveButton;

	private int tabIndex;

	public ProfileUIEmailTab(GameContainer gc, float x, float y) {
		setWidth(380);
		setHeight(240);
		setX(GameInfo.RES_WIDTH / 2 - getWidth() / 2);
		setY(GameInfo.RES_HEIGHT / 2 - getHeight() / 2 + 9);

		setCurrentPasswordField(new TextField(gc, Res.bitFont, getX() + 78, getY() + 82, 224, 7));
		setEmailField(new TextField(gc, Res.bitFont, getX() + 78, getY() + 122, 224, 7));
		setConfirmEmailField(new TextField(gc, Res.bitFont, getX() + 78, getY() + 162, 224, 7));

		setSaveButton(new MenuButton("Save Changes", getX() + 116, getY() + 213, 142, 11));
		
		currentPasswordField.makeOpaque();
		emailField.makeOpaque();
		confirmEmailField.makeOpaque();
		
		currentPasswordField.setMaskEnabled(true);

	}

	public void update(GameContainer gc, int delta) {

		if (getSaveButton().isClicked()) {
			if (!getEmailField().getText().isEmpty() && !getConfirmEmailField().getText().isEmpty()
					&& !getCurrentPasswordField().getText().isEmpty()) {
				LobbyManager.getManager().getAccountManager().changeEmail(getCurrentPasswordField().getText(),
						getEmailField().getText(), getConfirmEmailField().getText());
			}
		}

		if (getCurrentPasswordField().hasFocus()) {
			setTabIndex(0);
		}
		if (getEmailField().hasFocus()) {
			setTabIndex(1);
		}
		if (getConfirmEmailField().hasFocus()) {
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
				getEmailField().setFocus(true);
				getEmailField().setCursorPos(getEmailField().getText().length());
			}

			if (getTabIndex() == 2) {
				getConfirmEmailField().setFocus(true);
				getConfirmEmailField().setCursorPos(getConfirmEmailField().getText().length());
			}

		}

		getSaveButton().update(gc);
	}

	public void render(GameContainer gc, Graphics g) {

		String headerText = LobbyManager.getManager().getAccountManager().getEmailText();
		Res.bitFont.drawString(getX() - Res.getTextCenter(headerText) + getWidth() / 2, getY() + 26, headerText,
				LobbyManager.getManager().getAccountManager().getEmailColor());

		String currentEmailText = LobbyManager.getManager().getUserAccount().getEmail();
		Res.bitFont.drawString(getX() - Res.getTextCenter(currentEmailText) + getWidth() / 2, getY() + 45,
				currentEmailText, Color.green);


		getCurrentPasswordField().render(gc, g);
		getEmailField().render(gc, g);
		getConfirmEmailField().render(gc, g);

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
		getEmailField().setAcceptingInput(isActive);
		getConfirmEmailField().setAcceptingInput(isActive);

		clearTextFields();

		LobbyManager.getManager().getAccountManager()
				.setEmailText(LobbyManager.getManager().getAccountManager().getDefaultEmailText(), Color.yellow);
	}

	public void clearTextFields() {
		getCurrentPasswordField().setText("");
		getEmailField().setText("");
		getConfirmEmailField().setText("");
	}

	public TextField getEmailField() {
		return emailField;
	}

	public void setEmailField(TextField emailField) {
		this.emailField = emailField;
	}

	public TextField getConfirmEmailField() {
		return confirmEmailField;
	}

	public void setConfirmEmailField(TextField confirmEmailField) {
		this.confirmEmailField = confirmEmailField;
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
