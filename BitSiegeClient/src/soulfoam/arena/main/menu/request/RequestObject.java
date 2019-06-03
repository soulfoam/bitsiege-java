package soulfoam.arena.main.menu.request;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.ids.AvatarLibrary;
import soulfoam.arenashared.main.lobbyopcode.RequestType;

public class RequestObject {

	private int partyID = -1;
	private int userID = -1;

	private int requestID = -1;
	private int requestType;
	private String requestText;
	private int bgID;
	private int borderID;
	private int iconID;
	private Color requestColor;

	private float x, y, width, height;
	private MenuButton acceptButton;
	private MenuButton declineButton;

	public RequestObject(int requestID, String requestText, int bgID, int borderID, int iconID, int r, int g, int b) {
		setRequestID(requestID);
		setRequestType(RequestType.FRIEND_REQUEST);
		setUsername(requestText);
		setBGID(bgID);
		setBorderID(borderID);
		setIconID(iconID);
		setNameColor(new Color(r, g, b));

	}

	public RequestObject(int partyID, int userID, String requestText, int bgID, int borderID, int iconID, int r, int g,
			int b) {
		setPartyID(partyID);
		setUserID(userID);
		setRequestType(RequestType.PARTY_REQUEST);
		setUsername(requestText);
		setBGID(bgID);
		setBorderID(borderID);
		setIconID(iconID);
		setNameColor(new Color(r, g, b));

	}

	public void update(GameContainer gc, int delta) {

		getAcceptButton().update(gc);
		getDeclineButton().update(gc);

		if (getAcceptButton().isClicked()) {
			if (requestType == RequestType.FRIEND_REQUEST) {
				LobbyManager.getManager().getRequestManager().acceptFriendRequest(requestID);
			}
			if (requestType == RequestType.PARTY_REQUEST) {
				LobbyManager.getManager().getRequestManager().acceptPartyRequest(partyID, userID);
			}
		}

		if (getDeclineButton().isClicked()) {
			if (requestType == RequestType.FRIEND_REQUEST) {
				LobbyManager.getManager().getRequestManager().declineFriendRequest(requestID);
			}
			if (requestType == RequestType.PARTY_REQUEST) {
				LobbyManager.getManager().getRequestManager().declinePartyRequest(partyID, userID);
			}
		}

	}

	public void render(GameContainer gc, Graphics g) {

		if (requestType == RequestType.FRIEND_REQUEST) {

			Res.UI_RESOURCE.MAINMENU_REQUEST_UI_FRIEND.draw(x, y);
			Res.AVATAR_RESOURCE.getAvatarImage(getBGID(), AvatarLibrary.SLOT_BACKGROUND).draw(x + 1 , y + 1);
			Res.AVATAR_RESOURCE.getAvatarImage(getBorderID(), AvatarLibrary.SLOT_BORDER).draw(x + 1, y + 1);
			Res.AVATAR_RESOURCE.getAvatarImage(getIconID(), AvatarLibrary.SLOT_ICON).draw(x + 1, y + 1);
			
			
			Res.bitFont.drawString(getX() + 48, getY() + 7, getUsername(), getRequestColor());

			getAcceptButton().render(g, 3, gc);
			getDeclineButton().render(g, 3, gc);
		}

		if (requestType == RequestType.PARTY_REQUEST) {
			
			Res.UI_RESOURCE.MAINMENU_REQUEST_UI_GAME.draw(x, y);
			Res.AVATAR_RESOURCE.getAvatarImage(getBGID(), AvatarLibrary.SLOT_BACKGROUND).draw(x + 1 , y + 1);
			Res.AVATAR_RESOURCE.getAvatarImage(getBorderID(), AvatarLibrary.SLOT_BORDER).draw(x + 1, y + 1);
			Res.AVATAR_RESOURCE.getAvatarImage(getIconID(), AvatarLibrary.SLOT_ICON).draw(x + 1, y + 1);
			
			
			Res.bitFont.drawString(getX() + 48, getY() + 7, getUsername(), getRequestColor());

			getAcceptButton().render(g, 3, gc);
			getDeclineButton().render(g, 3, gc);
		}
		
		
	}

	public void handleButtons() {

		getAcceptButton().setPosition(getX() + 274, getY() + 3);
		getDeclineButton().setPosition(getX() + 326, getY() + 3);
		
	}

	public int getRequestID() {
		return requestID;
	}

	public void setRequestID(int requestID) {
		this.requestID = requestID;
	}

	public String getUsername() {
		return requestText;
	}

	public void setUsername(String username) {
		requestText = username;
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

	public Color getRequestColor() {
		return requestColor;
	}

	public void setNameColor(Color nameColor) {
		requestColor = nameColor;
	}

	public int getRequestType() {
		return requestType;
	}

	public void setRequestType(int requestType) {
		this.requestType = requestType;
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

	public MenuButton getAcceptButton() {
		if (acceptButton == null) {
			setAcceptButton(new MenuButton("Accept", getX() + 274, getY() + 3, 46, 12));
		}

		return acceptButton;
	}

	public void setAcceptButton(MenuButton acceptButton) {
		this.acceptButton = acceptButton;
	}

	public MenuButton getDeclineButton() {
		if (declineButton == null) {
			setDeclineButton(new MenuButton("Decline", getX() + 326, getY() + 3, 46, 12));
		}

		return declineButton;
	}

	public void setDeclineButton(MenuButton declineButton) {
		this.declineButton = declineButton;
	}

	public int getPartyID() {
		return partyID;
	}

	public void setPartyID(int partyID) {
		this.partyID = partyID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

}
