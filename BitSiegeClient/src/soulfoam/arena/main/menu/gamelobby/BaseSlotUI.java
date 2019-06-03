package soulfoam.arena.main.menu.gamelobby;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.main.resources.Res;

public abstract class BaseSlotUI {

	protected int slotID;
	public float x, y;
	protected float width, height;
	protected boolean owned;
	protected boolean selected;
	protected boolean empty = true;

	protected Image slotImage;

	protected Sound buttonSoundHover = Res.MENU_SFX[0];
	protected Sound buttonSound = Res.MENU_SFX[1];
	protected boolean playedSound;
	protected boolean playedSoundHover;
	protected boolean buttonDown = false;

	public BaseSlotUI(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public abstract void update(int delta, GameContainer gc);

	public abstract void render(Graphics g, GameContainer gc);

	public abstract void select();

	public Rectangle getBounds() {
		return new Rectangle(x, y, width, height);
	}

	public int getSlotID() {
		return slotID;
	}

	public void setSlotID(int slotID) {
		this.slotID = slotID;
	}

	public boolean isOwned() {
		return owned;
	}

	public void setOwned(boolean owned) {
		this.owned = owned;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean isSelected) {
		selected = isSelected;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean isEmpty) {
		empty = isEmpty;
	}

	public Image getSlotImage() {
		return slotImage;
	}

	public void setSlotImage(Image slotImage) {
		this.slotImage = slotImage;
	}

}
