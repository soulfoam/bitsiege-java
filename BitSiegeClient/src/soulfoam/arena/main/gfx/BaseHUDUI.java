package soulfoam.arena.main.gfx;

public class BaseHUDUI {

	protected float x;
	protected float y;
	protected float width, height;
	protected float textScale;
	protected float scale;

	protected int opacity;

	protected float popUpX;
	protected float popUpY;

	protected boolean pickedUp;

	protected boolean showPopUpMenu;

	protected float popUpTimeoutTime = 0.2f * 1000;
	protected float popUpTimeoutTimer = popUpTimeoutTime;

	public void runTimer(int delta) {
		if (popUpTimeoutTimer > 0) {
			popUpTimeoutTimer -= delta;
		}
	}

	public boolean canLeftClick() {
		if (popUpTimeoutTimer <= 0) {
			popUpTimeoutTimer = popUpTimeoutTime;
			return true;
		}

		return false;
	}

	public void setDown() {
		showPopUpMenu = false;
		pickedUp = false;
		popUpTimeoutTimer = popUpTimeoutTime;
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

	public float getTextScale() {
		return textScale;
	}

	public void setTextScale(float textScale) {
		this.textScale = textScale;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public int getOpacity() {
		return opacity;
	}

	public void setOpacity(int opacity) {
		this.opacity = opacity;
	}

	public float getPopUpX() {
		return popUpX;
	}

	public void setPopUpX(float popUpX) {
		this.popUpX = popUpX;
	}

	public float getPopUpY() {
		return popUpY;
	}

	public void setPopUpY(float popUpY) {
		this.popUpY = popUpY;
	}

	public boolean isPickedUp() {
		return pickedUp;
	}

	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

	public boolean isShowPopUpMenu() {
		return showPopUpMenu;
	}

	public void setShowPopUpMenu(boolean showPopUpMenu) {
		this.showPopUpMenu = showPopUpMenu;
	}

	public float getPopUpTimeoutTime() {
		return popUpTimeoutTime;
	}

	public void setPopUpTimeoutTime(float popUpTimeoutTime) {
		this.popUpTimeoutTime = popUpTimeoutTime;
	}

	public float getPopUpTimeoutTimer() {
		return popUpTimeoutTimer;
	}

	public void setPopUpTimeoutTimer(float popUpTimeoutTimer) {
		this.popUpTimeoutTimer = popUpTimeoutTimer;
	}
}
