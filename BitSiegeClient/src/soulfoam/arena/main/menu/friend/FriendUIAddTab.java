package soulfoam.arena.main.menu.friend;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.gui.TextField;

import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.ids.AvatarLibrary;

public class FriendUIAddTab {

	private float x, y, width, height;
	private boolean isActive;

	private TextField searchField;

	private boolean showFriendInfo;
	private String username;
	private int bgID = -1;
	private int borderID = -1;
	private int iconID = -1;
	private String creationDate;
	private String lastLoginDate;
	private Color nameColor;

	private MenuButton searchButton;
	private MenuButton addFriendButton;

	public FriendUIAddTab(GameContainer gc, float x, float y) {
		setX(x);
		setY(y);
		setWidth(380);
		setHeight(240);
		
		setSearchField(new TextField(gc, Res.bitFont, getX() + 76, getY() + 47, 230, 7));
		searchField.makeOpaque();

		setSearchButton(new MenuButton("Search", getX() + getWidth() / 2 - 50 , getY() + 190, 100, 10));
		setAddFriendButton(new MenuButton("Add Friend", getX() + getWidth() / 2 - 50 , getY() + 190, 100, 10));
	}

	public void update(GameContainer gc, int delta) {
		
		if (!getSearchField().hasFocus()) {
			getSearchField().setFocus(true);
		}

		if (gc.getInput().isKeyPressed(Input.KEY_ENTER) && !getSearchField().getText().trim().isEmpty()) {
			LobbyManager.getManager().getFriendManager().searchForFriend(getSearchField().getText().trim());
		}

		if (showFriendInfo()) {
			getAddFriendButton().update(gc);
			if (getAddFriendButton().isClicked()) {
				LobbyManager.getManager().getFriendManager().addFriend(username);
			}
		} else {
			getSearchButton().update(gc);
			if (getSearchButton().isClicked()) {
				LobbyManager.getManager().getFriendManager().searchForFriend(getSearchField().getText().trim());
			}
		}

	}

	public void render(GameContainer gc, Graphics g) {

		getSearchField().render(gc, g);

		String friendTextCenter = LobbyManager.getManager().getFriendManager().getFriendText();
		Res.bitFont.drawString(getX() + getWidth() / 2 - Res.getTextCenter(friendTextCenter), getY() + 9,
				friendTextCenter, LobbyManager.getManager().getFriendManager().getFriendColor());


		if (showFriendInfo()) {

			Res.AVATAR_RESOURCE.getAvatarImage(getBGID(), AvatarLibrary.SLOT_BACKGROUND)
					.draw(getX() - 16 + getWidth() / 2, getY() + 101 , 32, 32);
			Res.AVATAR_RESOURCE.getAvatarImage(getBorderID(), AvatarLibrary.SLOT_BORDER)
					.draw(getX() - 16 + getWidth() / 2, getY() + 101, 32, 32);
			Res.AVATAR_RESOURCE.getAvatarImage(getIconID(), AvatarLibrary.SLOT_ICON).draw(getX() - 16 + getWidth() / 2,
					getY() + 101, 32, 32);

			Res.bitFont.drawString(getX() + getWidth() / 2 - Res.getTextCenter(getUsername()), getY() + 80, getUsername(), getNameColor());
			
			Res.bitFont.drawString(getX() + getWidth() / 2 - Res.getTextCenter("Creation Date: " + getCreationDate()),
					getY() + 149, "Creation Date: " + getCreationDate(), Color.white);
			Res.bitFont.drawString(
					getX() + getWidth() / 2 - Res.getTextCenter("Last Login Date: " + getLastLoginDate()), getY() + 163,
					"Last Login Date: " + getLastLoginDate(), Color.white);

			getAddFriendButton().render(g, 2, gc);
		} else {
			getSearchButton().render(g, 2, gc);
		}

	}

	public void setFriendInfo(String username, int bgID, int borderID, int iconID, long creationDate,
			long lastLoginDate, int r, int g, int b) {
		setUsername(username);
		setBGID(bgID);
		setBorderID(borderID);
		setIconID(iconID);
		Date cd = new Date(creationDate);
		Date lld = new Date(lastLoginDate);
		DateFormat df = new SimpleDateFormat("MMMM dd, yyyy HH:mm:ss", Locale.US);
		setCreationDate(df.format(cd));
		setLastLoginDate(df.format(lld));
		setNameColor(new Color(r, g, b));
		setShowFriendInfo(true);
		getSearchField().setText("");
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
		getSearchField().setAcceptingInput(isActive);
		getSearchField().setText("");

		LobbyManager.getManager().getFriendManager()
				.setFriendText(LobbyManager.getManager().getFriendManager().getDefaultFriendText(), Color.yellow);
		LobbyManager.getManager().getFriendManager().setInteractionText(
				LobbyManager.getManager().getFriendManager().getDefaultInteractionText(), Color.yellow);
		if (!isActive) {
			setShowFriendInfo(false);
		}
	}

	public TextField getSearchField() {
		return searchField;
	}

	public void setSearchField(TextField searchField) {
		this.searchField = searchField;
	}

	public int getBGID() {
		return bgID;
	}

	public void setBGID(int bgID) {
		this.bgID = bgID;
	}

	public int getBorderID() {
		return borderID;
	}

	public void setBorderID(int borderID) {
		this.borderID = borderID;
	}

	public int getIconID() {
		return iconID;
	}

	public void setIconID(int iconID) {
		this.iconID = iconID;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public boolean showFriendInfo() {
		return showFriendInfo;
	}

	public void setShowFriendInfo(boolean showFriendInfo) {
		this.showFriendInfo = showFriendInfo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Color getNameColor() {
		return nameColor;
	}

	public void setNameColor(Color nameColor) {
		this.nameColor = nameColor;
	}

	public MenuButton getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(MenuButton searchButton) {
		this.searchButton = searchButton;
	}

	public MenuButton getAddFriendButton() {
		return addFriendButton;
	}

	public void setAddFriendButton(MenuButton addFriendButton) {
		this.addFriendButton = addFriendButton;
	}

}
