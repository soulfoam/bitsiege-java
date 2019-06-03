package soulfoam.arena.main.menu.chat;

import java.sql.Timestamp;
import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.main.menu.MainMenuManager;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.ids.AvatarLibrary;

public class ChatUIPrivateObject {

	private int id;
	private String username = "";
	private int bgID;
	private int borderID;
	private int iconID;
	private Color nameColor;
	private int connectionStatus;

	private int status;

	private float x, y, width, height;

	private MenuButton chatButton;
	
	private CopyOnWriteArrayList<ChatUIHistoryMessage> chatHistory = new CopyOnWriteArrayList<ChatUIHistoryMessage>();
	private boolean unreadMessages;
	private Timestamp lastMessageTime = new Timestamp(System.currentTimeMillis());
	
	public ChatUIPrivateObject(int id, String username, int bgID, int borderID, int iconID, int r, int g, int b, int connectionStatus) {
		setID(id);
		setUsername(username);
		setBGID(bgID);
		setBorderID(borderID);
		setIconID(iconID);
		setNameColor(new Color(r, g, b));
		setConnectionStatus(connectionStatus);

		chatButton = new MenuButton("Chat", getX() + 340, getY() + 3, 40, 12);


	}

	public void update(GameContainer gc, int delta) {

		chatButton.update(gc);

		if (chatButton.isClicked()) {
			MainMenuManager.getMainMenu().getChatUI().startPrivateChat(this);
		}

		handleButtons();
	}

	public void render(GameContainer gc, Graphics g) {
		
		if (!unreadMessages){
			Res.UI_RESOURCE.MAINMENU_CHAT_UI_BAR.draw(x, y);
		}
		else{
			Res.UI_RESOURCE.MAINMENU_FRIEND_UI_FRIENDONLINE.draw(x, y);
		}

		Res.AVATAR_RESOURCE.getAvatarImage(getBGID(), AvatarLibrary.SLOT_BACKGROUND).draw(getX() + 1, getY() + 1);
		Res.AVATAR_RESOURCE.getAvatarImage(getBorderID(), AvatarLibrary.SLOT_BORDER).draw(getX() + 1, getY() + 1);
		Res.AVATAR_RESOURCE.getAvatarImage(getIconID(), AvatarLibrary.SLOT_ICON).draw(getX() + 1, getY() + 1);

		Res.bitFont.drawString(getX() + 22, getY() + 7, getUsername(), getNameColor());

		chatButton.render(g, 3, gc);

	}

	public void addChatHistory(ChatUIHistoryMessage message){
		if (chatHistory.size() >= 24){
			chatHistory.remove(0);
		}
		
		chatHistory.add(message);
	}
	
	public void handleButtons() {

		chatButton.setPosition(getX() + 332, getY() + 3);

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

	public CopyOnWriteArrayList<ChatUIHistoryMessage> getChatHistory() {
		return chatHistory;
	}

	public Timestamp getLastMessageTime() {
		return lastMessageTime;
	}

	public void setLastMessageTime(Timestamp lastMessageTime) {
		this.lastMessageTime = lastMessageTime;
	}

	public boolean hasUnreadMessages() {
		return unreadMessages;
	}

	public void setUnreadMessages(boolean unreadMessages) {
		this.unreadMessages = unreadMessages;
	}
}
