package soulfoam.arena.main.menu.chat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class ChatUIPrivatePage {

	private float x, y, width, height;
	private ArrayList<ChatUIPrivateObject> privateItems = new ArrayList<ChatUIPrivateObject>();

	public ChatUIPrivatePage(float x, float y) {
		setX(x);
		setY(y);
		setWidth(380);
		setHeight(240);

	}

	public void update(GameContainer gc, int delta) {

		int display = 0;
		for (ChatUIPrivateObject privateItem : privateItems) {

			display += 21;
			privateItem.setX(getX() + 1);
			privateItem.setY(getY() + display - 22);
			privateItem.setWidth(getWidth());
			privateItem.setHeight(18);
			privateItem.update(gc, delta);

		}
	}

	public void render(GameContainer gc, Graphics g) {

		for (ChatUIPrivateObject privateItem : privateItems) {
			privateItem.render(gc, g);
		}

	}

	public void sortPrivateItems() {
		Collections.sort(privateItems, new Comparator<ChatUIPrivateObject>() {

			@Override
			public int compare(ChatUIPrivateObject o1, ChatUIPrivateObject o2) {
	            Boolean x1 = ((ChatUIPrivateObject) o1).hasUnreadMessages();
	            Boolean x2 = ((ChatUIPrivateObject) o2).hasUnreadMessages();
	            int sComp = x2.compareTo(x1);

	            if (sComp != 0) {
	               return sComp;
	            } else {
	               Timestamp t1 = ((ChatUIPrivateObject) o1).getLastMessageTime();
	               Timestamp t2 = ((ChatUIPrivateObject) o2).getLastMessageTime();
	               return t2.compareTo(t1);
	            }
			
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

	public ArrayList<ChatUIPrivateObject> getPrivateItems() {
		return privateItems;
	}

}
