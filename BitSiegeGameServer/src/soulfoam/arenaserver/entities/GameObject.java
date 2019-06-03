package soulfoam.arenaserver.entities;


public abstract class GameObject {

	protected float x, y;
	protected float width;
	protected float height;
	
	protected float hitBoxWidth;
	protected float hitBoxHeight;
	
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
	public float getHitBoxWidth() {
		return hitBoxWidth;
	}
	public void setHitBoxWidth(float hitBoxWidth) {
		this.hitBoxWidth = hitBoxWidth;
	}
	public float getHitBoxHeight() {
		return hitBoxHeight;
	}
	public void setHitBoxHeight(float hitBoxHeight) {
		this.hitBoxHeight = hitBoxHeight;
	}
	
}
