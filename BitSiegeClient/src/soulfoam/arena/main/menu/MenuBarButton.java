package soulfoam.arena.main.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.main.resources.Res;

public class MenuBarButton {

	private float x, y, width, height;
	private String text = "";
	private Rectangle button = new Rectangle(0, 0, 0, 0);
	private boolean buttonDown = false;
	private boolean buttonReleased = false;
	private boolean toggle;

	private int state = 0;

	private Sound buttonSound;
	private Sound buttonSoundHover;
	private boolean playedSound;
	private boolean playedSoundHover;

	public MenuBarButton(float x, float y, float width, float height, String text) {
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		setText(text);
	}

	public void update(GameContainer gc, int delta) {
		buttonReleased = false;
		if (buttonSound == null) {
			buttonSound = Res.MENU_SFX[1];
		}
		if (buttonSoundHover == null) {
			buttonSoundHover = Res.MENU_SFX[0];
		}
		if (button.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				buttonDown = true;
				if (!playedSound) {
					if (buttonSound != null) {
						buttonSound.play();
					}
				}
				playedSound = true;
			} else {
				if (buttonDown) {
					buttonDown = false;
					buttonReleased = true;
					playedSound = false;
				}

				if (isToggled()) {
					state = 2;
				} else {
					state = 1;
				}
			}
			if (!playedSoundHover) {
				// buttonSoundHover.play();
				playedSoundHover = true;
			}
		} else {
			playedSoundHover = false;

			state = 0;
			buttonDown = false;
		}
	}

	public void render(Graphics g) {
		g.setColor(new Color(0, 0, 0, 100));
		g.fillRect(x, y, width, height);

		g.setColor(new Color(53, 146, 212));
		g.drawRect(x, y, width, height);

		if (state == 1 || isToggled()) {
			g.setColor(new Color(9, 183, 25));
			g.fillRect(x, y, width, height);
		}
		if (state == 2) {
			g.setColor(new Color(183, 29, 9));
			g.fillRect(x, y, width, height);
		}

		float centerOfText = Res.bitFont.getWidth(text);
		Res.bitFont.drawString(x - centerOfText + width / 2, y + 2, text);

	}

	public boolean isClicked() {
		if (buttonReleased) {
			buttonReleased = false;
			return true;
		}
		return false;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
		button.setX(x);
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
		button.setY(y);
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
		button.setWidth(width);
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
		button.setHeight(height);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Rectangle getButton() {
		return button;
	}

	public void setButton(Rectangle button) {
		this.button = button;
	}

	public boolean isButtonDown() {
		return buttonDown;
	}

	public void setButtonDown(boolean buttonDown) {
		this.buttonDown = buttonDown;
	}

	public boolean isButtonReleased() {
		return buttonReleased;
	}

	public void setButtonReleased(boolean buttonReleased) {
		this.buttonReleased = buttonReleased;
	}

	public boolean isToggled() {
		return toggle;
	}

	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
