package soulfoam.arena.main.menu.request;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class RequestUIPage {

	private float x, y, width, height;
	private ArrayList<RequestObject> requestItems = new ArrayList<RequestObject>();

	public RequestUIPage(float x, float y) {
		setX(x);
		setY(y);
		setWidth(380);
		setHeight(240);

	}

	public void update(GameContainer gc, int delta) {

		int display = 0;
		for (RequestObject request : requestItems) {

			display += 21;
			request.setX(getX() + 1);
			request.setY(getY() + display - 1);
			request.setWidth(getWidth());
			request.setHeight(18);
			request.update(gc, delta);

		}
	}

	public void render(GameContainer gc, Graphics g) {

		for (RequestObject request : requestItems) {
			request.render(gc, g);
		}

	}

	public void sortRequests() {
		Collections.sort(requestItems, new Comparator<RequestObject>() {

			public int compare(RequestObject o1, RequestObject o2) {
				return Float.compare(o2.getRequestType(), o1.getRequestType());
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

	public ArrayList<RequestObject> getRequestItems() {
		return requestItems;
	}

}
