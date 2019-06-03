package soulfoam.arena.main.gfx;

import java.text.DecimalFormat;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class HotbarHUD extends BaseHUDUI {

	private Rectangle abilityRect;
	private HUDButton textScalePlusButton;
	private HUDButton textScaleMinusButton;
	private HUDButton scalePlusButton;
	private HUDButton scaleMinusButton;
	private HUDButton spacingPlusButton;
	private HUDButton spacingMinusButton;
	private HUDButton opacityPlusButton;
	private HUDButton opacityMinusButton;

	private HUDButton verticalButton;

	private boolean vertical;

	private int spacing = 5;

	public HotbarHUD(float x, float y, float scale, float textScale, int spacing, int opacity, boolean vertical) {
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.textScale = textScale;
		setSpacing(spacing);
		this.opacity = opacity;
		setVertical(vertical);
		abilityRect = new Rectangle(x, y, scale * 4 + spacing * 3, scale);

		scalePlusButton = new HUDButton("+", x, y, 10, 5);
		scaleMinusButton = new HUDButton("-", x, y, 10, 5);
		textScalePlusButton = new HUDButton("+", x, y, 10, 5);
		textScaleMinusButton = new HUDButton("-", x, y, 10, 5);
		spacingPlusButton = new HUDButton("+", x, y, 10, 5);
		spacingMinusButton = new HUDButton("-", x, y, 10, 5);
		opacityPlusButton = new HUDButton("+", x, y, 10, 5);
		opacityMinusButton = new HUDButton("-", x, y, 10, 5);
		verticalButton = new HUDButton("", x, y, 22, 5);

		setButtons();
		setHotbar();
	}

	public void update(GameContainer gc, int delta) {

		runTimer(delta);

		if (Game.getGame().getHUDDisplay().isEditingHUD()) {
			if (pickedUp) {
				x = gc.getInput().getMouseX() - scale / 2;
				y = gc.getInput().getMouseY() - scale / 2;

				if (x <= 0) {
					x = 0;
				}
				if (y <= 0) {
					y = 0;
				}
				if (x + abilityRect.getWidth() >= GameInfo.RES_WIDTH) {
					x = GameInfo.RES_WIDTH - abilityRect.getWidth();
				}
				if (y + scale >= GameInfo.RES_HEIGHT) {
					y = GameInfo.RES_HEIGHT - scale;
				}

				if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					setDown();
				}
			} else {
				if (abilityRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
					if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
						if (canLeftClick()) {
							showPopUpMenu = !showPopUpMenu;
						}
					}
					if (gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
						Game.getGame().getHUDDisplay().attemptToPickUpHUD(this);
						showPopUpMenu = true;
					}
				}
			}

			if (showPopUpMenu) {
				if (scalePlusButton.isClicked()) {
					scale += 1;
				}
				if (scaleMinusButton.isClicked()) {
					scale -= 1;
					if (scale <= 0) {
						scale = 1;
					}
				}

				if (textScalePlusButton.isClicked()) {
					textScale += 0.25f;
				}
				if (textScaleMinusButton.isClicked()) {
					textScale -= 0.25;
					if (textScale <= 0) {
						textScale = 0;
					}
				}

				if (spacingPlusButton.isClicked()) {
					setSpacing(getSpacing() + 1);
				}
				if (spacingMinusButton.isClicked()) {
					setSpacing(getSpacing() - 1);
					if (getSpacing() <= 0) {
						setSpacing(0);
					}
				}

				if (opacityPlusButton.isClicked()) {
					opacity += 20;
					if (opacity >= 255) {
						opacity = 255;
					}
				}
				if (opacityMinusButton.isClicked()) {
					opacity -= 20;
					if (opacity <= 0) {
						opacity = 0;
					}
				}

				if (verticalButton.isClicked()) {
					setVertical(!isVertical());
				}

				setButtons();
				setHotbar();

				scalePlusButton.update(gc);
				scaleMinusButton.update(gc);
				textScalePlusButton.update(gc);
				textScaleMinusButton.update(gc);
				spacingPlusButton.update(gc);
				spacingMinusButton.update(gc);
				opacityPlusButton.update(gc);
				opacityMinusButton.update(gc);
				verticalButton.update(gc);
			}
		} else {
			pickedUp = false;
		}
	}

	public void render(Graphics g) {
		if (Game.getGame().getPlayer() != null) {

			Color opac = new Color(255, 255, 255, opacity);
			Color greenOpac = new Color(0, 255, 0, opacity);
			Color orangeOpac = new Color(255, 150, 0, opacity);
			Color redOpac = new Color(255, 0, 0, opacity);
			
			String ability1CDString = "";
			String ability2CDString = "";
			String ability3CDString = "";
			String ability4CDString = "";
			
			ability1CDString = "" + (int) ((Game.getGame().getPlayer().getAbility1CDTimer()+1000)/1000);
			ability2CDString = "" + (int) ((Game.getGame().getPlayer().getAbility2CDTimer()+1000)/1000);
			ability3CDString = "" + (int) ((Game.getGame().getPlayer().getAbility3CDTimer()+1000)/1000);
			ability4CDString = "" + (int) ((Game.getGame().getPlayer().getAbility4CDTimer()+1000)/1000);
			
			if (!isVertical()) {
				if (Game.getGame().getPlayer().getSelectedAbility() == 1) {
					g.setColor(greenOpac);
					if (Game.getGame().getPlayer().getAbility1CDTimer() > 0) {
						g.setColor(orangeOpac);
					}
					g.fillRect(x - 1, y - 1, scale + 2, scale + 2);
				}
				if (Game.getGame().getPlayer().getSelectedAbility() == 2) {
					g.setColor(greenOpac);
					if (Game.getGame().getPlayer().getAbility2CDTimer() > 0) {
						g.setColor(orangeOpac);
					}
					g.fillRect(x + scale + getSpacing() - 1, y - 1, scale + 2, scale + 2);
				}
				if (Game.getGame().getPlayer().getSelectedAbility() == 3) {
					g.setColor(greenOpac);
					if (Game.getGame().getPlayer().getAbility3CDTimer() > 0) {
						g.setColor(orangeOpac);
					}
					g.fillRect(x + scale * 2 + getSpacing() * 2 - 1, y - 1, scale + 2, scale + 2);
				}
				if (Game.getGame().getPlayer().getSelectedAbility() == 4) {
					g.setColor(greenOpac);
					if (Game.getGame().getPlayer().getAbility4CDTimer() > 0) {
						g.setColor(orangeOpac);
					}
					g.fillRect(x + scale * 3 + getSpacing() * 3 - 1, y - 1, scale + 2, scale + 2);
				}

				Game.getGame().getPlayer().getAbility1().getSkillIcon().draw(x, y, scale, scale, opac);
				Game.getGame().getPlayer().getAbility2().getSkillIcon().draw(x + scale + getSpacing(), y, scale,
						scale, opac);
				Game.getGame().getPlayer().getAbility3().getSkillIcon().draw(x + scale * 2 + getSpacing() * 2, y,
						scale, scale, opac);
				Game.getGame().getPlayer().getAbility4().getSkillIcon().draw(x + scale * 3 + getSpacing() * 3, y,
						scale, scale, opac);

				g.setColor(new Color(0, 0, 0, opacity - 20));
				float cd1Box = -(scale * (Game.getGame().getPlayer().getAbility1CDTimer()
						/ Game.getGame().getPlayer().getAbility1CDTime()));
				float cd2Box = -(scale * (Game.getGame().getPlayer().getAbility2CDTimer()
						/ Game.getGame().getPlayer().getAbility2CDTime()));
				float cd3Box = -(scale * (Game.getGame().getPlayer().getAbility3CDTimer()
						/ Game.getGame().getPlayer().getAbility3CDTime()));
				float cd4Box = -(scale * (Game.getGame().getPlayer().getAbility4CDTimer()
						/ Game.getGame().getPlayer().getAbility4CDTime()));
				g.fillRect(x, y + scale, scale, cd1Box);
				g.fillRect(x + scale + getSpacing(), y + scale, scale, cd2Box);
				g.fillRect(x + scale * 2 + getSpacing() * 2, y + scale, scale, cd3Box);
				g.fillRect(x + scale * 3 + getSpacing() * 3, y + scale, scale, cd4Box);

				if (Game.getGame().getPlayer().getAbility1CDTimer() > 0) {
					Res.bitFont.drawString(x - 1, y - 4, ability1CDString, redOpac, textScale);
				}
				if (Game.getGame().getPlayer().getAbility2CDTimer() > 0) {
					Res.bitFont.drawString(x + scale + getSpacing() - 1, y - 4, ability2CDString, redOpac, textScale);
				}
				if (Game.getGame().getPlayer().getAbility3CDTimer() > 0) {
					Res.bitFont.drawString(x + scale * 2 + getSpacing() * 2 - 1, y - 4, ability3CDString, redOpac, textScale);
				}
				if (Game.getGame().getPlayer().getAbility4CDTimer() > 0) {
					Res.bitFont.drawString(x + scale * 3 + getSpacing() * 3 - 1, y - 4, ability4CDString, redOpac, textScale);
				}
			} else {

				if (Game.getGame().getPlayer().getSelectedAbility() == 1) {
					g.setColor(greenOpac);
					if (Game.getGame().getPlayer().getAbility1CDTimer() > 0) {
						g.setColor(orangeOpac);
					}
					g.fillRect(x - 1, y - 1, scale + 2, scale + 2);
				}
				if (Game.getGame().getPlayer().getSelectedAbility() == 2) {
					g.setColor(greenOpac);
					if (Game.getGame().getPlayer().getAbility2CDTimer() > 0) {
						g.setColor(orangeOpac);
					}
					g.fillRect(x - 1, y + scale + getSpacing() - 1, scale + 2, scale + 2);
				}
				if (Game.getGame().getPlayer().getSelectedAbility() == 3) {
					g.setColor(greenOpac);
					if (Game.getGame().getPlayer().getAbility3CDTimer() > 0) {
						g.setColor(orangeOpac);
					}
					g.fillRect(x - 1, y + scale * 2 + getSpacing() * 2 - 1, scale + 2, scale + 2);
				}
				if (Game.getGame().getPlayer().getSelectedAbility() == 4) {
					g.setColor(greenOpac);
					if (Game.getGame().getPlayer().getAbility4CDTimer() > 0) {
						g.setColor(orangeOpac);
					}
					g.fillRect(x - 1, y + scale * 3 + getSpacing() * 3 - 1, scale + 2, scale + 2);
				}

				Game.getGame().getPlayer().getAbility1().getSkillIcon().draw(x, y, scale, scale, opac);
				Game.getGame().getPlayer().getAbility2().getSkillIcon().draw(x, y + scale + getSpacing(), scale,
						scale, opac);
				Game.getGame().getPlayer().getAbility3().getSkillIcon().draw(x, y + scale * 2 + getSpacing() * 2,
						scale, scale, opac);
				Game.getGame().getPlayer().getAbility4().getSkillIcon().draw(x, y + scale * 3 + getSpacing() * 3,
						scale, scale, opac);

				g.setColor(new Color(0, 0, 0, opacity - 20));
				float cd1Box = -(scale * (Game.getGame().getPlayer().getAbility1CDTimer()
						/ Game.getGame().getPlayer().getAbility1CDTime()));
				float cd2Box = -(scale * (Game.getGame().getPlayer().getAbility2CDTimer()
						/ Game.getGame().getPlayer().getAbility2CDTime()));
				float cd3Box = -(scale * (Game.getGame().getPlayer().getAbility3CDTimer()
						/ Game.getGame().getPlayer().getAbility3CDTime()));
				float cd4Box = -(scale * (Game.getGame().getPlayer().getAbility4CDTimer()
						/ Game.getGame().getPlayer().getAbility4CDTime()));
				g.fillRect(x, y + scale, scale, cd1Box);
				g.fillRect(x, y + scale + getSpacing() + scale, scale, cd2Box);
				g.fillRect(x, y + scale * 2 + getSpacing() * 2 + scale, scale, cd3Box);
				g.fillRect(x, y + scale * 3 + getSpacing() * 3 + scale, scale, cd4Box);

				if (Game.getGame().getPlayer().getAbility1CDTimer() > 0) {
					Res.bitFont.drawString(x - 1, y - 4, ability1CDString, redOpac, textScale);
				}
				if (Game.getGame().getPlayer().getAbility2CDTimer() > 0) {
					Res.bitFont.drawString(x - 1, y + scale + getSpacing() - 4, ability2CDString, redOpac, textScale);
				}
				if (Game.getGame().getPlayer().getAbility3CDTimer() > 0) {
					Res.bitFont.drawString(x - 1, y + scale * 2 + getSpacing() * 2 - 4, ability3CDString, redOpac, textScale);
				}
				if (Game.getGame().getPlayer().getAbility4CDTimer() > 0) {
					Res.bitFont.drawString(x - 1, y + scale * 3 + getSpacing() * 3 - 4, ability4CDString, redOpac, textScale);
				}
			}

			if (Game.getGame().getHUDDisplay().isEditingHUD()) {
				if (!pickedUp) {
					g.setColor(Color.red);
					if (showPopUpMenu) {
						g.setColor(Color.yellow);
					}
					g.draw(abilityRect);
				} else {
					g.setColor(Color.green);
					g.draw(abilityRect);
				}

				if (showPopUpMenu) {
					popUpX = x;
					popUpY = y - 35;

					if (popUpX >= GameInfo.RES_WIDTH - 100) {
						popUpX = GameInfo.RES_WIDTH - 100;
					}
					if (popUpY <= 0) {
						popUpY = 0;
					}

					g.setColor(new Color(0, 0, 0, 180));
					g.fillRect(popUpX, popUpY, 100, 32);
					Res.bitFont.drawString(popUpX + 26, popUpY + 2, "Scale: " + scale);
					Res.bitFont.drawString(popUpX + 26, popUpY + 8, "Text Scale: " + textScale);
					Res.bitFont.drawString(popUpX + 26, popUpY + 14, "Spacing: " + getSpacing());
					Res.bitFont.drawString(popUpX + 26, popUpY + 20, "Opacity: " + opacity);
					Res.bitFont.drawString(popUpX + 26, popUpY + 26, "Vertical: " + isVertical());

					scalePlusButton.render(g);
					scaleMinusButton.render(g);
					textScalePlusButton.render(g);
					textScaleMinusButton.render(g);
					spacingPlusButton.render(g);
					spacingMinusButton.render(g);
					opacityPlusButton.render(g);
					opacityMinusButton.render(g);
					verticalButton.render(g);
				}
			}
		}

	}

	public void setHotbar() {
		abilityRect.setX(x);
		abilityRect.setY(y);
		if (!isVertical()) {
			abilityRect.setWidth(scale * 4 + getSpacing() * 3);
			abilityRect.setHeight(scale);
		} else {
			abilityRect.setWidth(scale);
			abilityRect.setHeight(scale * 4 + getSpacing() * 3);
		}
	}

	public void setButtons() {
		scalePlusButton.setX(popUpX + 2);
		scalePlusButton.setY(popUpY + 2);

		scaleMinusButton.setX(popUpX + 14);
		scaleMinusButton.setY(popUpY + 2);

		textScalePlusButton.setX(popUpX + 2);
		textScalePlusButton.setY(popUpY + 8);

		textScaleMinusButton.setX(popUpX + 14);
		textScaleMinusButton.setY(popUpY + 8);

		spacingPlusButton.setX(popUpX + 2);
		spacingPlusButton.setY(popUpY + 14);

		spacingMinusButton.setX(popUpX + 14);
		spacingMinusButton.setY(popUpY + 14);

		opacityPlusButton.setX(popUpX + 2);
		opacityPlusButton.setY(popUpY + 20);

		opacityMinusButton.setX(popUpX + 14);
		opacityMinusButton.setY(popUpY + 20);

		verticalButton.setX(popUpX + 2);
		verticalButton.setY(popUpY + 26);

		if (!isVertical()) {
			verticalButton.setButtonColor(new Color(255, 0, 0));
		} else {
			verticalButton.setButtonColor(new Color(0, 255, 0));
		}
	}

	public boolean isVertical() {
		return vertical;
	}

	public void setVertical(boolean vertical) {
		this.vertical = vertical;
	}

	public int getSpacing() {
		return spacing;
	}

	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}
}
