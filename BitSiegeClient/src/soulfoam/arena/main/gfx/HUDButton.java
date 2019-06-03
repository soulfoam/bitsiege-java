package soulfoam.arena.main.gfx;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.main.resources.Res;

public class HUDButton {
	private Rectangle button;
	private boolean buttonDown = false;
	private boolean buttonReleased = false;
	private Image renderImage;

	private String text;

	private float x, y;

	private float width;
	private float height;
	private Color buttonColor = Res.COLOR_RESOURCE.BLUE_BUTTON;
	private Color textColor = Color.white;

	private Sound buttonSound = Res.MENU_SFX[0];
	private boolean playedSound;

	public boolean toggled;

	public HUDButton(String text, float x, float y, float width, float height) {
		setText(text);
		this.width = width;
		this.height = height;

		button = new Rectangle(x, y, width, height);
		renderImage = Res.UI_RESOURCE.MENUBUTTON;
	}

	public void update(GameContainer gc) {
		buttonReleased = false;

		if (button.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				buttonDown = true;
				if (!playedSound) {
					buttonSound.play();
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

		} else {
			renderImage = Res.UI_RESOURCE.MENUBUTTON;
			buttonDown = false;
		}

		button.setX(getX());
		button.setY(getY());
		button.setWidth(width);
		button.setHeight(height);
	}

	public void render(Graphics g) {

		// renderImage.draw(x, y, width, height, buttonColor);
		renderImage.draw(Res.roundForRendering(getX()), Res.roundForRendering(getY()), width, height, getButtonColor());

		float centerOfText = Res.bitFont.getWidth(getText());

		if (renderImage != Res.UI_RESOURCE.MENUBUTTON_HOVER) {
			Res.bitFont.drawString(Res.roundForRendering(getX() - centerOfText + width),
					Res.roundForRendering(getY()), getText(), getTextColor());
		} else {
			Res.bitFont.drawString(Res.roundForRendering(getX() - centerOfText + width),
					Res.roundForRendering(getY() + 0.5f), getText(), getTextColor());
		}
	}

	public void render(Graphics g, float textX, float textY) {

		// renderImage.draw(x, y, width, height, buttonColor);
		renderImage.draw(Res.roundForRendering(getX()), Res.roundForRendering(getY()), width, height, getButtonColor());

		float centerOfText = Res.bitFont.getWidth(getText());

		if (renderImage != Res.UI_RESOURCE.MENUBUTTON_HOVER) {
			Res.bitFont.drawString(Res.roundForRendering(getX() - centerOfText + width *  + textX),
					Res.roundForRendering(getY() + textY), getText(), getTextColor());
		} else {
			Res.bitFont.drawString(Res.roundForRendering(getX() - centerOfText + width *  + textX),
					Res.roundForRendering(getY() + textY + 0.5f), getText(), getTextColor());
		}
	}

	public void render(Graphics g, float textY) {

		// renderImage.draw(x, y, width, height, buttonColor);
		renderImage.draw(Res.roundForRendering(getX()), Res.roundForRendering(getY()), width, height, getButtonColor());

		float centerOfText = Res.bitFont.getWidth(getText());

		if (renderImage != Res.UI_RESOURCE.MENUBUTTON_HOVER) {
			Res.bitFont.drawString(Res.roundForRendering(getX() - centerOfText + width),
					Res.roundForRendering(getY() + textY), getText(), getTextColor());
		} else {
			Res.bitFont.drawString(Res.roundForRendering(getX() - centerOfText + width),
					Res.roundForRendering(getY() + textY + 0.5f), getText(), getTextColor());
		}
	}

	public void renderScaleSelf(Graphics g) {
		width = Res.bitFont.getWidth(getText()) + 8;
		// renderImage.draw(x, y, width, height, buttonColor);
		renderImage.draw(Res.roundForRendering(getX()), Res.roundForRendering(getY()), width, height, getButtonColor());

		if (renderImage != Res.UI_RESOURCE.MENUBUTTON_HOVER) {
			Res.bitFont.drawString(Res.roundForRendering(getX() + 4), Res.roundForRendering(getY()), getText(),
					getTextColor());
		} else {
			Res.bitFont.drawString(Res.roundForRendering(getX() + 4), Res.roundForRendering(getY() + 0.5f), getText(),
					getTextColor());
		}
	}

	public void renderScaleSelf(Graphics g, float textScale) {

		width = Res.bitFont.getWidth(getText(), textScale) + 8;
		// renderImage.draw(x, y, width, height, buttonColor);
		renderImage.draw(Res.roundForRendering(getX()), Res.roundForRendering(getY()),
				Res.bitFont.getWidth(getText(), textScale) + 8, height, getButtonColor());

		if (renderImage != Res.UI_RESOURCE.MENUBUTTON_HOVER) {
			Res.bitFont.drawString(Res.roundForRendering(getX() + 4), Res.roundForRendering(getY()), getText(),
					getTextColor(), textScale);
		} else {
			Res.bitFont.drawString(Res.roundForRendering(getX() + 4), Res.roundForRendering(getY() + 0.5f), getText(),
					getTextColor(), textScale);
		}

	}

	public void renderScaleSelf(Graphics g, float textY, float textScale) {

		width = Res.bitFont.getWidth(getText(), textScale) + 8;
		// renderImage.draw(x, y, width, height, buttonColor);
		renderImage.draw(Res.roundForRendering(getX()), Res.roundForRendering(getY()),
				Res.bitFont.getWidth(getText(), textScale) + 8, height, getButtonColor());

		if (renderImage != Res.UI_RESOURCE.MENUBUTTON_HOVER) {
			Res.bitFont.drawString(Res.roundForRendering(getX() + 4), Res.roundForRendering(getY() + textY), getText(),
					getTextColor(), textScale);
		} else {
			Res.bitFont.drawString(Res.roundForRendering(getX() + 4), Res.roundForRendering(getY() + textY + 0.5f),
					getText(), getTextColor(), textScale);
		}

	}

	public void setToggle() {
		toggled = !toggled;

		if (toggled) {
			setButtonColor(new Color(9, 183, 25));
		} else {
			setButtonColor(new Color(183, 29, 9));
		}
	}

	public void setToggle(boolean on) {
		toggled = on;

		if (on) {
			setButtonColor(Res.COLOR_RESOURCE.TOGGLE_BUTTON_ON);
		} else {
			setButtonColor(Res.COLOR_RESOURCE.TOGGLE_BUTTON_OFF);
		}
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
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
