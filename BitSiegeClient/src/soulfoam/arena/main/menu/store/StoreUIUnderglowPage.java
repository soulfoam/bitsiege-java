package soulfoam.arena.main.menu.store;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class StoreUIUnderglowPage {

	private CopyOnWriteArrayList<StoreUIUnderglowObject> underglowItems = new CopyOnWriteArrayList<StoreUIUnderglowObject>();

	private float x, y, width, height;

	public StoreUIUnderglowPage(float x, float y) {
		this.x = x;
		this.y = y;
		width = 190;
		height = 127;

	}

	public void update(GameContainer gc, int delta) {

		int display = 0;
		int displayY = 3;
		for (int i = 0; i < underglowItems.size(); i++) {
			StoreUIUnderglowObject underglowGlowObject = underglowItems.get(i);

			if (i == 5) {
				display = 0;
				displayY = 39;
			}
			if (i == 10) {
				display = 0;
				displayY = 75;
			}

			display += 38;

			underglowGlowObject.setX(x + display - 35);
			underglowGlowObject.setY(y + displayY);
			underglowGlowObject.update(gc, delta);

		}
	}

	public void render(GameContainer gc, Graphics g) {

		for (StoreUIUnderglowObject underglowObject : underglowItems) {
			underglowObject.render(gc, g);
		}

	}

	public CopyOnWriteArrayList<StoreUIUnderglowObject> getUnderglowItems() {
		return underglowItems;
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
