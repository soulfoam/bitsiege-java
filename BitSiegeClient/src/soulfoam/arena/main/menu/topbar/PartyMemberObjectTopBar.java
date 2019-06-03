package soulfoam.arena.main.menu.topbar;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.ids.AvatarLibrary;

public class PartyMemberObjectTopBar {

	private int id;
	private String username = "";
	private int bgID;
	private int borderID;
	private int iconID;
	private Color nameColor;

	private float x, y, width = 16, height = 16;

	public PartyMemberObjectTopBar(int id, String username, int bgID, int borderID, int iconID, int r, int g, int b) {
		setID(id);
		setUsername(username);
		setBGID(bgID);
		setBorderID(borderID);
		setIconID(iconID);
		setNameColor(new Color(r, g, b));
	}

	public void update(GameContainer gc, int delta) {

	}

	public void render(GameContainer gc, Graphics g) {

		Res.AVATAR_RESOURCE.getAvatarImage(getBGID(), AvatarLibrary.SLOT_BACKGROUND).draw(getX(), getY(), width,
				height);
		Res.AVATAR_RESOURCE.getAvatarImage(getBorderID(), AvatarLibrary.SLOT_BORDER).draw(getX(), getY(), width,
				height);
		Res.AVATAR_RESOURCE.getAvatarImage(getIconID(), AvatarLibrary.SLOT_ICON).draw(getX(), getY(), width,
				height);

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

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}
}
