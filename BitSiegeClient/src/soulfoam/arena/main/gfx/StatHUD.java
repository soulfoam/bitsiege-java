package soulfoam.arena.main.gfx;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class StatHUD extends BaseHUDUI {

	public Rectangle statRect;

	public HUDButton scaleSizePlusButton;
	public HUDButton scaleSizeMinusButton;
	public HUDButton opacityPlusButton;
	public HUDButton opacityMinusButton;
	public HUDButton showTipsButton;

	public Rectangle hpRect;
	public Rectangle dmgRect;
	public Rectangle atkspdRect;
	public Rectangle spdRect;
	public Rectangle aaRect;

	public boolean showTips;

	public StatHUD(float x, float y, float scale, int opacity, boolean showTips) {
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.opacity = opacity;

		this.showTips = showTips;

		statRect = new Rectangle(x, y, 15, 10);

		scaleSizePlusButton = new HUDButton("+", x, y, 10, 5);
		scaleSizeMinusButton = new HUDButton("-", x, y, 10, 5);
		opacityPlusButton = new HUDButton("+", x, y, 10, 5);
		opacityMinusButton = new HUDButton("-", x, y, 10, 5);
		showTipsButton = new HUDButton("", x, y, 22, 8);

		hpRect = new Rectangle(0, 0, 0, 0);
		dmgRect = new Rectangle(0, 0, 0, 0);
		atkspdRect = new Rectangle(0, 0, 0, 0);
		spdRect = new Rectangle(0, 0, 0, 0);
		aaRect = new Rectangle(0, 0, 0, 0);

		setStatRect();
	}

	public void update(GameContainer gc, int delta) {

		runTimer(delta);

		if (Game.getGame().getHUDDisplay().isEditingHUD()) {
			if (pickedUp) {
				x = gc.getInput().getMouseX() - scale * 8 / 2;
				y = gc.getInput().getMouseY() - scale * 5 / 2;

				if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					setDown();
				}
			} else {
				if (statRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
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
				scaleSizePlusButton.update(gc);
				scaleSizeMinusButton.update(gc);
				showTipsButton.update(gc);
				opacityPlusButton.update(gc);
				opacityMinusButton.update(gc);

				if (scaleSizePlusButton.isClicked()) {
					scale += 2.0f;

				}
				if (scaleSizeMinusButton.isClicked()) {
					scale -= 2.0f;
					if (scale <= 4) {
						scale = 4;
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
				if (showTipsButton.isClicked()) {
					showTips = !showTips;
				}
			}
			setButtons();
		} else {
			pickedUp = false;
		}

		setStatRect();

	}

	public void render(Graphics g, GameContainer gc) {

		g.setColor(new Color(1, 16, 27, opacity));
		g.fillRect(x - 2, y - 2, scale * 8, scale * 5 + 3);
		g.setColor(new Color(53, 146, 212, opacity));
		g.drawRect(x - 2, y - 2, scale * 8, scale * 5 + 3);
		// Res.mainHUDDisplay[6].draw(x, y, scale * 4, scale, new Color(255,
		// 255, 255, opacity));
		Color opac = new Color(255, 255, 255, opacity);

		float playerDamage = 0;
		if (Game.getGame().getPlayer().getSelectedAbility() == 1) {
			playerDamage = Game.getGame().getPlayer().getAbility1().getTotalDamage();
		}
		if (Game.getGame().getPlayer().getSelectedAbility() == 2) {
			playerDamage = Game.getGame().getPlayer().getAbility2().getTotalDamage();
		}
		if (Game.getGame().getPlayer().getSelectedAbility() == 3) {
			playerDamage = Game.getGame().getPlayer().getAbility3().getTotalDamage();
		}
		if (Game.getGame().getPlayer().getSelectedAbility() == 4) {
			playerDamage = Game.getGame().getPlayer().getAbility4().getTotalDamage();
		}

		Res.bitFont.drawString(x + scale * 2 - scale / 2, y + scale / 12,
				(int) Math.ceil(Game.getGame().getPlayer().getHealth()) + "/"
						+ (int) Math.ceil(Game.getGame().getPlayer().getBaseHealth()),
				new Color(0, 255, 0, opacity), textScale);

		Res.bitFont.drawString(x + scale * 2 - scale / 2, y + scale + scale / 12, (int) playerDamage + "", opac,
				textScale);

		Res.bitFont
				.drawString(x + scale * 2 - scale / 2, y + scale * 2 + scale / 12,
						Res.getAttackSpeedDisplay(EntityInfo.getAttackSpeed(
								Game.getGame().getPlayer().getAbilityBasicAttackCDTime(),
								Game.getGame().getPlayer().getAttackSpeedBuffAmount())) + "",
						opac, textScale);

		Res.bitFont.drawString(x + scale * 2 - scale / 2, y + scale * 3 + scale / 12,
				Res.getMoveSpeedDisplay(Game.getGame().getPlayer().getMoveSpeed()) + "", opac, textScale);

		Res.bitFont
				.drawString(x + scale * 2 - scale / 2, y + scale * 4 + scale / 12,
						Math.round(EntityInfo.getOverallPower(
								Game.getGame().getPlayer().getAbilityBasicAttack().getDamage(),
								Game.getGame().getPlayer().getPowerBuffAmount())) + "",
						opac, textScale);

		if (Game.getGame().getHUDDisplay().isEditingHUD()) {
			if (!pickedUp) {
				g.setColor(Color.red);
				if (showPopUpMenu) {
					g.setColor(Color.yellow);
				}
				g.draw(statRect);
			} else {
				g.setColor(Color.green);
				g.draw(statRect);
			}

			if (showPopUpMenu) {
				popUpX = x - 2;
				popUpY = y - 30;

				if (popUpX <= 0) {
					popUpX = 0;
				}

				if (popUpX >= GameInfo.RES_WIDTH - 100) {
					popUpX = GameInfo.RES_WIDTH - 100;
				}
				if (popUpY <= 0) {
					popUpY = 0;
				}

				g.setColor(new Color(0, 0, 0, 180));
				g.fillRect(popUpX, popUpY, 96, 26);
				Res.bitFont.drawString(popUpX + 26, popUpY + 2, "Scale: " + scale);
				Res.bitFont.drawString(popUpX + 26, popUpY + 8, "Opacity: " + opacity);
				Res.bitFont.drawString(popUpX + 26, popUpY + 14, "Show Tips On ");
				Res.bitFont.drawString(popUpX + 26, popUpY + 20, "Mouse Hover:" + showTips);

				scaleSizePlusButton.render(g);
				scaleSizeMinusButton.render(g);
				showTipsButton.render(g);
				opacityPlusButton.render(g);
				opacityMinusButton.render(g);
			}
		} else {
			if (showTips) {
				popUpX = x - 2;
				popUpY = y - 10;

				if (popUpX >= GameInfo.RES_WIDTH - 100) {
					popUpX = GameInfo.RES_WIDTH - 100;
				}
				if (popUpY <= 0) {
					popUpY = 0;
				}
				Color hopac = new Color(255, 255, 255, opacity);

				if (hpRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
					g.setColor(new Color(0, 0, 0, opacity));
					g.fillRect(popUpX, popUpY, 28, 8);
					Res.bitFont.drawString(popUpX + 2, popUpY + 2, "Health", hopac);
				}
				if (dmgRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
					g.setColor(new Color(0, 0, 0, opacity));
					g.fillRect(popUpX, popUpY, 88, 8);
					Res.bitFont.drawString(popUpX + 2, popUpY + 2, "Overall Ability Power", hopac);
				}
				if (atkspdRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
					g.setColor(new Color(0, 0, 0, opacity));
					g.fillRect(popUpX, popUpY, 52, 8);
					Res.bitFont.drawString(popUpX + 2, popUpY + 2, "Attack Speed", hopac);
				}

				if (spdRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
					g.setColor(new Color(0, 0, 0, opacity));
					g.fillRect(popUpX, popUpY, 44, 8);
					Res.bitFont.drawString(popUpX + 2, popUpY + 2, "Move Speed", hopac);
				}

				if (aaRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
					g.setColor(new Color(0, 0, 0, opacity));
					g.fillRect(popUpX, popUpY, 76, 8);
					Res.bitFont.drawString(popUpX + 2, popUpY + 2, "Auto Attack Damage", hopac);
				}
			}
		}

	}

	public void setButtons() {

		scaleSizePlusButton.setX(popUpX + 2);
		scaleSizePlusButton.setY(popUpY + 2);

		scaleSizeMinusButton.setX(popUpX + 14);
		scaleSizeMinusButton.setY(popUpY + 2);

		showTipsButton.setX(popUpX + 2);
		showTipsButton.setY(popUpY + 16);

		opacityPlusButton.setX(popUpX + 2);
		opacityPlusButton.setY(popUpY + 8);

		opacityMinusButton.setX(popUpX + 14);
		opacityMinusButton.setY(popUpY + 8);

		if (!showTips) {
			showTipsButton.setButtonColor(new Color(255, 0, 0));
		} else {
			showTipsButton.setButtonColor(new Color(0, 255, 0));
		}

	}

	public void setStatRect() {

		statRect.setX(x - 2);
		statRect.setY(y - 2);
		statRect.setWidth(scale * 8);
		statRect.setHeight(scale * 5 + 3);

		hpRect.setX(x - scale / 2);
		hpRect.setY(y);
		hpRect.setWidth(scale * 8);
		hpRect.setHeight(scale);

		dmgRect.setX(x - scale / 2);
		dmgRect.setY(y + scale);
		dmgRect.setWidth(scale * 8);
		dmgRect.setHeight(scale);

		atkspdRect.setX(x - scale / 2);
		atkspdRect.setY(y + scale * 2);
		atkspdRect.setWidth(scale * 8);
		atkspdRect.setHeight(scale);

		spdRect.setX(x - scale / 2);
		spdRect.setY(y + scale * 3);
		spdRect.setWidth(scale * 8);
		spdRect.setHeight(scale);

		aaRect.setX(x - scale / 2);
		aaRect.setY(y + scale * 4);
		aaRect.setWidth(scale * 8);
		aaRect.setHeight(scale);

		if (scale <= 4) {
			textScale = 0.25f;
		}
		if (scale >= 6 && scale < 12) {
			textScale = 0.5f;
		}
		if (scale >= 12) {
			textScale = 1.25f;
		}

	}

}
