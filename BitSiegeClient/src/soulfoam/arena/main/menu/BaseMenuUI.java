package soulfoam.arena.main.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.StateBasedGame;

public abstract class BaseMenuUI {

	private float x, y;
	private float width, height;

	private boolean renderMe;
	private boolean isFocused;

	public abstract void update(GameContainer gc, StateBasedGame s, int delta);

	public abstract void render(GameContainer gc, Graphics g);

	public boolean isRendering() {
		return renderMe;
	}

	public void setRender(boolean renderMe) {
		this.renderMe = renderMe;
	}

	public boolean isFocused() {
		return isFocused;
	}

	public void setWindow(boolean set) {
		setFocus(set);
		setRender(set);
	}

	public void setFocus(boolean isFocused) {
		this.isFocused = isFocused;
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

	public Shape getBounds() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

}
