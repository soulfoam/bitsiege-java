package soulfoam.arena.main.menu.friend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class FriendUIPage {

	private float x, y, width, height;
	private ArrayList<FriendUIObject> friendItems = new ArrayList<FriendUIObject>();

	public FriendUIPage(float x, float y) {
		setX(x);
		setY(y);
		setWidth(380);
		setHeight(240);

	}

	public void update(GameContainer gc, int delta) {

		int display = 0;
		for (FriendUIObject friend : friendItems) {

			display += 21;
			friend.setX(getX() + 1);
			friend.setY(getY() + display - 12);
			friend.setWidth(getWidth());
			friend.setHeight(18);
			friend.update(gc, delta);

		}
	}

	public void render(GameContainer gc, Graphics g) {

		for (FriendUIObject friend : friendItems) {
			friend.render(gc, g);
		}

	}

	public void sortFriends() {
		Collections.sort(friendItems, new Comparator<FriendUIObject>() {

			public int compare(FriendUIObject o1, FriendUIObject o2) {
				return Integer.compare(o1.getConnectionStatus(), o2.getConnectionStatus());
			}
		});
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public ArrayList<FriendUIObject> getFriendItems() {
		return friendItems;
	}

}
