package soulfoam.arena.main.menu.store;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class StoreUIAvatarPage {

	private CopyOnWriteArrayList<StoreUIAvatarObject> avatarItems = new CopyOnWriteArrayList<StoreUIAvatarObject>();

	private float x, y, width, height;

	public StoreUIAvatarPage(float x, float y) {
		this.x = x;
		this.y = y;
		width = 190;
		height = 127;

	}

	public void update(GameContainer gc, int delta) {

		int display = 0;
		int displayY = 18;
		for (int i = 0; i < avatarItems.size(); i++) {
			StoreUIAvatarObject avatarItem = avatarItems.get(i);

			if (i == 6) {
				display = 0;
				displayY = 104;
			}

			display += 49;

			avatarItem.setX(x + display - 44);
			avatarItem.setY(y + displayY);
			avatarItem.update(gc, delta);

		}
	}

	public void render(GameContainer gc, Graphics g) {

		for (StoreUIAvatarObject avatarItem : avatarItems) {
			avatarItem.render(gc, g);
		}

	}

	public CopyOnWriteArrayList<StoreUIAvatarObject> getAvatarItems() {
		return avatarItems;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}
}
