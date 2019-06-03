package soulfoam.arena.main.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.main.resources.Res;

public class MenuButton {
	private Rectangle button;
	private boolean buttonDown = false;
	private boolean buttonReleased = false;
	private Image renderImage;

	private String text;
	private Color textColor = Color.white;

	private boolean toggled;
	private float width;
	private float height;
	private float x, y;
	private Color buttonColor = Res.COLOR_RESOURCE.BLUE_BUTTON;

	private Sound buttonSound;
	private Sound buttonSoundHover;
	private boolean playedSound;
	private boolean playedSoundHover;
	private boolean enabled = true;

	public MenuButton(String text, float x, float y, float width, float height) {
		setText(text);
		setWidth(width);
		setHeight(height);
		setX(x);
		setY(y);

		setButton(new Rectangle(x, y, width, height));
		renderImage = Res.UI_RESOURCE.MENUBUTTON;
		buttonSound = Res.MENU_SFX[1];
		buttonSoundHover = Res.MENU_SFX[0];
	}

	public void update(GameContainer gc) {
		buttonReleased = false;
		if (buttonSound == null) {
			buttonSound = Res.MENU_SFX[1];
		}
		if (buttonSoundHover == null) {
			buttonSoundHover = Res.MENU_SFX[0];
		}
		if (getButton().contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
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
				renderImage = Res.UI_RESOURCE.MENUBUTTON_HOVER;
			}
			if (!playedSoundHover) {
				// buttonSoundHover.play();
				playedSoundHover = true;
			}
		} else {
			playedSoundHover = false;
			renderImage = Res.UI_RESOURCE.MENUBUTTON;
			buttonDown = false;
		}
	}

	public void render(Graphics g) {

		renderImage.draw(getX(), getY(), getWidth(), getHeight(), getButtonColor());

		int margin = ((int) getWidth() - getTextWidth(getText())) / 2;
		int height = (int) getButton().getMinY() + 2;
		if (getButton().getHeight() == 16) {
			height = (int) getButton().getMinY() + 2;
		}
		if (getButton().getHeight() == 12 || getButton().getHeight() == 93) {
			height = (int) getButton().getMinY() + 1;
		}
		if (getButton().getHeight() == 10) {
			height = (int) getButton().getMinY();
		}

		Res.bitFont.drawString(getX() + margin, height, getText(), getTextColor(), 1f);
	}

	public void render(Graphics g, GameContainer gc, float textWidth, float textHeight) {

		renderImage.draw(getX(), getY(), getWidth(), getHeight(), getButtonColor());

		if (gc != null && getButton().contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			Res.bitFont.drawString(getX() + textWidth, getY() + textHeight + 1, getText(), getTextColor());
		} else {
			Res.bitFont.drawString(getX() + textWidth, getY() + textHeight, getText(), getTextColor());
		}
	}

	public void render(Graphics g, float textHeight, GameContainer gc) {

		if (renderImage != null) {
			renderImage.draw(getX(), getY(), getWidth(), getHeight(), getButtonColor());
		}

		if (gc != null && getButton().contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			Res.bitFont.drawString(getX() - Res.getTextCenter(getText()) + getWidth() / 2, getY() + textHeight + 1, getText(), getTextColor());
		} else {
			Res.bitFont.drawString(getX() - Res.getTextCenter(getText()) + getWidth() / 2, getY() + textHeight, getText(), getTextColor());
		}

	}

	public void renderHalf(Graphics g, float textHeight) {

		if (renderImage != null) {
			renderImage.draw(getX(), getY(), getWidth(), getHeight(), getButtonColor());
		}

		float margin = ((int) getWidth() - getTextWidth(getText())) / 2;


		Res.bitFont.drawString(getX() + margin, getY() + textHeight, getText(), getTextColor());
		
	}

	public void render(Graphics g, float textWidth, float textHeight, GameContainer gc) {

		renderImage.draw(getX(), getY(), getWidth(), getHeight(), getButtonColor());

		if (gc != null && getButton().contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			Res.bitFont.drawString(getX() + textWidth, getY() + textHeight + 1, getText(), getTextColor());
		} else {
			Res.bitFont.drawString(getX() + textWidth, getY() + textHeight, getText(), getTextColor());
		}

	}

	public void setToggle(boolean on) {
		if (on) {
			setToggled(true);
			setButtonColor(new Color(0, 255, 100));
		} else {
			setToggled(false);
			setButtonColor(new Color(255, 255, 255));
		}
	}

	public boolean isClicked() {
		if (!enabled) {
			return false;
		}
		if (buttonReleased) {
			buttonReleased = false;
			return true;
		}
		return false;
	}

	private int getTextWidth(String text) {
		int width = 0;

		for (char ch : text.toCharArray()) {
			if (ch == ' ') {
				width += 2;
			} else {
				width += Res.bitFont.getWidth(String.valueOf(ch));
			}
		}

		return width;
	}

	public void setPosition(float x, float y) {
		setX(x);
		setY(y);
		getButton().setX(x);
		getButton().setY(y);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
		if (enabled) {
			setButtonColor(Res.COLOR_RESOURCE.BLUE_BUTTON);
		} else {
			setButtonColor(Res.COLOR_RESOURCE.BUTTON_DISABLED);
		}
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
		getButton().setHeight(height);
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
		getButton().setWidth(width);
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Color getButtonColor() {
		return buttonColor;
	}

	public void setButtonColor(Color buttonColor) {
		this.buttonColor = buttonColor;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public boolean isToggled() {
		return toggled;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}

	public Rectangle getButton() {
		if (button == null){
			button = new Rectangle(x, y, width, height);
		}
		return button;
	}

	public void setButton(Rectangle button) {
		this.button = button;
	}
}
