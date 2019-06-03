package soulfoam.arena.main.menu.friend;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.ConnectionStatus;

public class FriendUIObject {

	private int id;
	private String username = "";
	private int bgID;
	private int borderID;
	private int iconID;
	private Color nameColor;
	private int connectionStatus;

	private int status;

	private float x, y, width, height;
	private MenuButton partyInviteButton;

	public FriendUIObject(int id, String username, int bgID, int borderID, int iconID, int r, int g, int b, int connectionStatus) {
		setID(id);
		setUsername(username);
		setBGID(bgID);
		setBorderID(borderID);
		setIconID(iconID);
		setNameColor(new Color(r, g, b));
		setConnectionStatus(connectionStatus);

		partyInviteButton = new MenuButton("Invite To Party", getX() + 268, getY() + 3, 99, 12);

	}

	public void update(GameContainer gc, int delta) {

		partyInviteButton.update(gc);

		if (partyInviteButton.isClicked()) {
			LobbyManager.getManager().getFriendManager().inviteFriendToParty(getID());
		}

		handleButtons();
	}

	public void render(GameContainer gc, Graphics g) {

		if (connectionStatus == ConnectionStatus.ONLINE) {
			Res.UI_RESOURCE.MAINMENU_FRIEND_UI_FRIENDONLINE.draw(x, y);
		} else {
			Res.UI_RESOURCE.MAINMENU_FRIEND_UI_FRIENDOFFLINE.draw(x, y);
		}

		Res.AVATAR_RESOURCE.getAvatarImage(getBGID(), AvatarLibrary.SLOT_BACKGROUND).draw(getX() + 1, getY() + 1);
		Res.AVATAR_RESOURCE.getAvatarImage(getBorderID(), AvatarLibrary.SLOT_BORDER).draw(getX() + 1, getY() + 1);
		Res.AVATAR_RESOURCE.getAvatarImage(getIconID(), AvatarLibrary.SLOT_ICON).draw(getX() + 1, getY() + 1);

		Res.bitFont.drawString(getX() + 22, getY() + 7, getUsername(), getNameColor());

		partyInviteButton.render(g, 4, 3, gc);

	}

	public void handleButtons() {

		partyInviteButton.setPosition(getX() + 274, getY() + 3);

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public Color getNameColor() {
		return nameColor;
	}

	public void setNameColor(Color nameColor) {
		this.nameColor = nameColor;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
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

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(int connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

}
