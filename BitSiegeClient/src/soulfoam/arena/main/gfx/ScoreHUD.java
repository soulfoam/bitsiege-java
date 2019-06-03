package soulfoam.arena.main.gfx;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class ScoreHUD extends BaseHUDUI {

	public Rectangle scoreRect;

	public HUDButton opacityPlusButton;
	public HUDButton opacityMinusButton;

	public HUDButton showTipsButton;

	public boolean showTips;

	public ScoreHUD(float x, float y, float scale, int opacity, boolean showTips) {
		this.x = x;
		this.y = y;
		this.scale = 4;
		this.opacity = opacity;
		this.showTips = showTips;

		scoreRect = new Rectangle(x, y, 15, 10);

		opacityPlusButton = new HUDButton("+", x, y, 10, 5);
		opacityMinusButton = new HUDButton("-", x, y, 10, 5);

		showTipsButton = new HUDButton("", x, y, 22, 8);

		setScoreRect();
	}

	public void update(GameContainer gc, int delta) {

		runTimer(delta);

		if (Game.getGame().getHUDDisplay().isEditingHUD()) {
			if (pickedUp) {
				x = gc.getInput().getMouseX() - scale * 6 / 2;
				y = gc.getInput().getMouseY() - scale * 2 / 2;

				if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					setDown();
				}
			} else {
				if (scoreRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
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

				opacityPlusButton.update(gc);
				opacityMinusButton.update(gc);

				showTipsButton.update(gc);

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

		setScoreRect();

	}

	public void render(Graphics g, GameContainer gc) {

		if (Game.getGame().getPlayer() != null) {
			g.setColor(new Color(1, 16, 27, opacity));
			g.fillRect(x - 2, y - 2, scale * 6, scale * 2);
			g.setColor(new Color(53, 146, 212, opacity));
			g.drawRect(x - 2, y - 2, scale * 6, scale * 2);
			// Res.mainHUDDisplay[6].draw(x, y, scale * 4, scale, new Color(255,
			// 255, 255, opacity));

			g.setColor(new Color(0, 0, 0, opacity));
			Color misc = new Color(255, 255, 255, opacity);

			Color t0 = new Color(0, 200, 255, opacity);
			Color t1 = new Color(255, 70, 0, opacity);

			Res.bitFont.drawString(x + 4, y - 0.5f, Game.getGame().getClientFunctions().getTeamDPoints() + " ",
					t0);
			Res.bitFont.drawString(x + 8, y - 0.5f, "-", misc, 0.5f);
			Res.bitFont.drawString(x + 12, y - 0.5f, Game.getGame().getClientFunctions().getTeamAPoints() + " ",
					t1);

		}

		if (Game.getGame().getHUDDisplay().isEditingHUD()) {
			if (!pickedUp) {
				g.setColor(Color.red);
				if (showPopUpMenu) {
					g.setColor(Color.yellow);
				}
				g.draw(scoreRect);
			} else {
				g.setColor(Color.green);
				g.draw(scoreRect);
			}

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
			if (showPopUpMenu) {
				g.setColor(new Color(0, 0, 0, 180));
				g.fillRect(popUpX, popUpY, 96, 20);
				Res.bitFont.drawString(popUpX + 26, popUpY + 2, "Opacity: " + opacity);
				Res.bitFont.drawString(popUpX + 26, popUpY + 8, "Show Tips On ");
				Res.bitFont.drawString(popUpX + 26, popUpY + 14, "Mouse Hover:" + showTips);

				opacityPlusButton.render(g);
				opacityMinusButton.render(g);
				showTipsButton.render(g);
			}
		} else {
			if (showTips) {
				popUpX = x - 2;
				popUpY = y - 15;

				if (popUpX >= 274) {
					popUpX = 274;
				}
				if (popUpY <= 0) {
					popUpY = 0;
				}
				Color opact0 = new Color(255, 255, 255, opacity);

				if (scoreRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
					g.setColor(new Color(0, 0, 0, opacity));
					g.fillRect(popUpX, popUpY, 47, 8);

				}

			}
		}

	}

	public void setButtons() {

		opacityPlusButton.setX(popUpX + 2);
		opacityPlusButton.setY(popUpY + 2);

		opacityMinusButton.setX(popUpX + 14);
		opacityMinusButton.setY(popUpY + 2);

		showTipsButton.setX(popUpX + 2);
		showTipsButton.setY(popUpY + 10);

		if (!showTips) {
			showTipsButton.setButtonColor(new Color(255, 0, 0));
		} else {
			showTipsButton.setButtonColor(new Color(0, 255, 0));
		}

	}

	public void setScoreRect() {

		scoreRect.setX(x - 2);
		scoreRect.setY(y - 2);
		scoreRect.setWidth(scale * 6);
		scoreRect.setHeight(scale * 2);

	}

}
