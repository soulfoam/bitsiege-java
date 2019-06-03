package soulfoam.arena.main.gfx;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class GoldHUD extends BaseHUDUI {

	private Rectangle goldRect;

	private HUDButton opacityPlusButton;
	private HUDButton opacityMinusButton;

	public GoldHUD(float x, float y, int opacity) {
		this.x = x;
		this.y = y;
		scale = 4;
		this.opacity = opacity;

		goldRect = new Rectangle(x, y, 15, 10);

		opacityPlusButton = new HUDButton("+", x, y, 10, 5);
		opacityMinusButton = new HUDButton("-", x, y, 10, 5);

		setGoldRect();
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
				if (goldRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
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
			}

			setButtons();
		} else {
			pickedUp = false;
		}

		setGoldRect();

	}

	public void render(Graphics g) {


		g.setColor(new Color(1, 16, 27, opacity));
		g.fillRect(x - 2, y - 2, scale * 6, scale * 2);
		g.setColor(new Color(53, 146, 212, opacity));
		g.drawRect(x - 2, y - 2, scale * 6, scale * 2);
		// Res.mainHUDDisplay[6].draw(x, y, scale * 4, scale, new Color(255,
		// 255, 255, opacity));
		Color opac = new Color(255, 255, 255, opacity);


		Res.bitFont.drawString(x + scale * 2 - scale + 1, y - 0.5f, Game.getGame().getPlayer().getGold() + "",
				opac, 0.5f);

		

		if (Game.getGame().getHUDDisplay().isEditingHUD()) {
			if (!pickedUp) {
				g.setColor(Color.red);
				if (showPopUpMenu) {
					g.setColor(Color.yellow);
				}
				g.draw(goldRect);
			} else {
				g.setColor(Color.green);
				g.draw(goldRect);
			}

			if (showPopUpMenu) {
				popUpX = x - 2;
				popUpY = y - 12;

				if (popUpX <= 0) {
					popUpX = 0;
				}

				if (popUpX >= GameInfo.RES_WIDTH - 81) {
					popUpX = GameInfo.RES_WIDTH - 81;
				}
				if (popUpY <= 0) {
					popUpY = 0;
				}

				g.setColor(new Color(0, 0, 0, 180));
				g.fillRect(popUpX, popUpY, 76, 8);
				Res.bitFont.drawString(popUpX + 26, popUpY + 2, "Opacity: " + opacity);

				opacityPlusButton.render(g);
				opacityMinusButton.render(g);
			}
		}

	}

	public void setButtons() {

		opacityPlusButton.setX(popUpX + 2);
		opacityPlusButton.setY(popUpY + 2);

		opacityMinusButton.setX(popUpX + 14);
		opacityMinusButton.setY(popUpY + 2);

	}

	public void setGoldRect() {

		goldRect.setX(x - 2);
		goldRect.setY(y - 2);
		goldRect.setWidth(scale * 6);
		goldRect.setHeight(scale * 2);

	}

}
