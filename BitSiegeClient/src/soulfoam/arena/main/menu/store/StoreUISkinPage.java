package soulfoam.arena.main.menu.store;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class StoreUISkinPage {

	private CopyOnWriteArrayList<StoreUISkinObject> skinItems = new CopyOnWriteArrayList<StoreUISkinObject>();

	private float x, y, width, height;

	public StoreUISkinPage(float x, float y) {
		this.x = x;
		this.y = y;
		width = 190;
		height = 127;

	}

	public void update(GameContainer gc, int delta) {

		int display = 0;
		int displayY = 8;
		for (int i = 0; i < skinItems.size(); i++) {
			StoreUISkinObject challengerItem = skinItems.get(i);

			if (i == 6) {
				display = 0;
				displayY = 100;
			}

			display += 49;

			challengerItem.setX(x + display - 44);
			challengerItem.setY(y + displayY);
			challengerItem.update(gc, delta);

		}
	}

	public void render(GameContainer gc, Graphics g) {

		for (StoreUISkinObject challengerItem : skinItems) {
			challengerItem.render(gc, g);
		}

	}

	public CopyOnWriteArrayList<StoreUISkinObject> getSkinItems() {
		return skinItems;
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
