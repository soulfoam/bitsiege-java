package soulfoam.arena.main.menu.party;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.ids.AvatarLibrary;

public class PartyMemberObjectPartyUI {

	private int id;
	private String username = "";
	private int bgID;
	private int borderID;
	private int iconID;
	private Color nameColor;
	private boolean isLeader;

	private MenuButton disbandButton;
	private MenuButton makeLeaderButton;

	private float x, y, width, height;

	public PartyMemberObjectPartyUI(int id, String username, int bgID, int borderID, int iconID, int r, int g, int b) {
		setID(id);
		setUsername(username);
		setBGID(bgID);
		setBorderID(borderID);
		setIconID(iconID);
		setNameColor(new Color(r, g, b));
		setMakeLeaderButton(new MenuButton("Make Leader", getX() + 186, getY() + 4, 90, 10));
		setDisbandButton(new MenuButton("Disband", getX() + 282, getY() + 4, 90, 10));
		getDisbandButton().setButtonColor(Res.COLOR_RESOURCE.TOGGLE_BUTTON_OFF);

	}

	public void update(GameContainer gc, int delta) {
		
		getMakeLeaderButton().update(gc);
		getDisbandButton().update(gc);

		if (getMakeLeaderButton().isClicked()) {
			LobbyManager.getManager().getPartyManager().makeNewLeader(getID());
		}

		if (getDisbandButton().isClicked()) {
			LobbyManager.getManager().getPartyManager().leaveParty(getID());
		}

		handleButtons();
	}

	public void render(GameContainer gc, Graphics g) {
		
		Res.UI_RESOURCE.MAINMENU_CHAT_UI_BAR.draw(getX(), getY());

		Res.AVATAR_RESOURCE.getAvatarImage(getBGID(), AvatarLibrary.SLOT_BACKGROUND).draw(getX() + 1, getY() + 1);
		Res.AVATAR_RESOURCE.getAvatarImage(getBorderID(), AvatarLibrary.SLOT_BORDER).draw(getX() + 1, getY() + 1);
		Res.AVATAR_RESOURCE.getAvatarImage(getIconID(), AvatarLibrary.SLOT_ICON).draw(getX() + 1, getY() + 1);

		Res.bitFont.drawString(getX() + 21, getY() + 7, getUsername(), getNameColor());

		getMakeLeaderButton().render(g, 2, gc);
		getDisbandButton().render(g, 2, gc);
	}

	public void handleButtons() {
		
		makeLeaderButton.setPosition(getX() + 186, getY() + 4);
		disbandButton.setPosition(getX() + 282, getY() + 4);

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public MenuButton getDisbandButton() {
		return disbandButton;
	}

	public void setDisbandButton(MenuButton disbandButton) {
		this.disbandButton = disbandButton;
	}

	public MenuButton getMakeLeaderButton() {
		return makeLeaderButton;
	}

	public void setMakeLeaderButton(MenuButton makeLeaderButton) {
		this.makeLeaderButton = makeLeaderButton;
	}

	public boolean isLeader() {
		return isLeader;
	}

	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}
}
