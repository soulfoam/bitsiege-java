package soulfoam.arena.main.gfx;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class TimerHUD extends BaseHUDUI {

	public Rectangle timerRect;

	public HUDButton opacityPlusButton;
	public HUDButton opacityMinusButton;

	public TimerHUD(float x, float y, float scale, int opacity) {
		this.x = x;
		this.y = y;
		this.scale = 4;
		this.opacity = opacity;

		timerRect = new Rectangle(x, y, 15, 10);

		opacityPlusButton = new HUDButton("+", x, y, 10, 5);
		opacityMinusButton = new HUDButton("-", x, y, 10, 5);

		setTimerRect();
	}

	public void update(GameContainer gc, int delta) {

		runTimer(delta);
		scale = 4;
		if (Game.getGame().getHUDDisplay().isEditingHUD()) {
			if (pickedUp) {
				x = gc.getInput().getMouseX() - scale * 6 / 2;
				y = gc.getInput().getMouseY() - scale * 2 / 2;

				if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					setDown();
				}
			} else {
				if (timerRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
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

		setTimerRect();

	}

	public void render(Graphics g, GameContainer gc) {


		g.setColor(new Color(1, 16, 27, opacity));
		g.fillRect(x - 2, y - 2, scale * 6, scale * 2);
		g.setColor(new Color(53, 146, 212, opacity));
		g.drawRect(x - 2, y - 2, scale * 6, scale * 2);
		// Res.mainHUDDisplay[6].draw(x, y, scale * 4, scale, new Color(255,
		// 255, 255, opacity));

		g.setColor(new Color(0, 0, 0, opacity));
		Color misc = new Color(255, 255, 0, opacity);

		int gcm = (int) Math.floor((Game.getGame().getClientFunctions().getGameClock()/1000) / 60);
		int gcs = (Game.getGame().getClientFunctions().getGameClock()/1000) - gcm * 60;

		float centerOfTime = Res.bitFont.getWidth(String.format("%02d", gcm) + ":" + String.format("%02d", gcs)) / 2;
		Res.bitFont.drawString(x - centerOfTime + scale *  6 - 1.5f, y - 0.5f,
				String.format("%02d", gcm) + ":" + String.format("%02d", gcs), misc);
		

		if (Game.getGame().getHUDDisplay().isEditingHUD()) {
			if (!pickedUp) {
				g.setColor(Color.red);
				if (showPopUpMenu) {
					g.setColor(Color.yellow);
				}
				g.draw(timerRect);
			} else {
				g.setColor(Color.green);
				g.draw(timerRect);
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
				g.fillRect(popUpX, popUpY, 96, 8);
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

	public void setTimerRect() {

		timerRect.setX(x - 2);
		timerRect.setY(y - 2);
		timerRect.setWidth(scale * 6);
		timerRect.setHeight(scale * 2);

	}

}
