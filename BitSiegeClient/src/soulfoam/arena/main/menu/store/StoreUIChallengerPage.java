package soulfoam.arena.main.menu.store;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class StoreUIChallengerPage {

	private CopyOnWriteArrayList<StoreUIChallengerObject> challengerItems = new CopyOnWriteArrayList<StoreUIChallengerObject>();

	private float x, y, width, height;

	public StoreUIChallengerPage(float x, float y) {
		this.x = x;
		this.y = y;
		width = 190;
		height = 127;

	}

	public void update(GameContainer gc, int delta) {

		int display = 0;
		int displayY = 8;
		for (int i = 0; i < challengerItems.size(); i++) {
			StoreUIChallengerObject challengerItem = challengerItems.get(i);

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

		for (StoreUIChallengerObject challengerItem : challengerItems) {
			challengerItem.render(gc, g);
		}

	}

	public CopyOnWriteArrayList<StoreUIChallengerObject> getChallengerItems() {
		return challengerItems;
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
