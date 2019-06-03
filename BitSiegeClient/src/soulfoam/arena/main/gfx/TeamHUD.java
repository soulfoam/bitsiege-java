package soulfoam.arena.main.gfx;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class TeamHUD extends BaseHUDUI {

	public int spacing;

	public HUDButton scalePlusButton;
	public HUDButton scaleMinusButton;
	public HUDButton opacityPlusButton;
	public HUDButton opacityMinusButton;

	public Rectangle teamRect;

	public TeamHUD(float x, float y, float scale, int opacity) {
		this.x = x;
		this.y = y;
		this.scale = scale;
		this.opacity = opacity;

		teamRect = new Rectangle(x, y, scale * 2, 204);

		scalePlusButton = new HUDButton("+", x, y, 10, 5);
		scaleMinusButton = new HUDButton("-", x, y, 10, 5);

		opacityPlusButton = new HUDButton("+", x, y, 10, 5);
		opacityMinusButton = new HUDButton("-", x, y, 10, 5);

		setTeamRect();
		setButtons();
	}

	public void update(GameContainer gc, int delta) {

		runTimer(delta);

		if (Game.getGame().getHUDDisplay().isEditingHUD()) {

			if (pickedUp) {
				x = gc.getInput().getMouseX() - scale * 2;
				y = gc.getInput().getMouseY() - scale * 4;

				if (x <= 0) {
					x = 0;
				}
				if (y <= 0 - scale - 4) {
					y = 0 - scale - 4;
				}
				if (x + scale * 4 >= GameInfo.RES_WIDTH) {
					x = GameInfo.RES_WIDTH - scale * 4;
				}

				if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					setDown();
				}
			} else {
				if (teamRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
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

				setButtons();
				setTeamRect();

				scalePlusButton.update(gc);
				scaleMinusButton.update(gc);

				opacityPlusButton.update(gc);
				opacityMinusButton.update(gc);
			}
		} else {
			pickedUp = false;
		}
	}

	public void render(Graphics g) {

		Color opac = new Color(255, 255, 255, opacity);

		Entity[] tempList = new Entity[Game.getGame().getPlayers().size()];
		Game.getGame().getPlayers().toArray(tempList);
		for (Entity p : tempList) {

			if (p.getTeam() == Game.getGame().getPlayer().getTeam()) {

				spacing += scale + 4;

				if (p.isDead()) {
					Res.UI_RESOURCE.MESSAGEBAR_RED.draw(x, y + spacing, scale * 4, scale, opac);
				} else {
					if (p.getAbility4CDTimer() <= 0) {
						Res.UI_RESOURCE.MESSAGEBAR_GOLD.draw(x, y + spacing, scale * 4, scale, opac);
					} else {
						Res.UI_RESOURCE.MESSAGEBAR_BLUE.draw(x, y + spacing, scale * 4, scale, opac);
					}
				}
				p.getAnimation().getPortrait().draw(Res.roundForRendering(x + scale / 2),
						Res.roundForRendering(y + spacing + 1), scale, scale, opac);

				float health = p.getHealth();
				float maxHealth = p.getBaseHealth();
				float healthBarSize = scale * 2;
				float healthBarWidth = healthBarSize * (health * 1.0f / (maxHealth * 1.0f));
				float healthBarHeight = scale - scale / 2 - 1;

				if (!p.isDead()) {
					g.setColor(new Color(0, 0, 0, opacity));
					g.fillRect(x + scale + scale / 2, y + spacing + scale / 2 - scale / 6, healthBarSize,
							healthBarHeight);

					g.setColor(new Color(0, 255, 0, opacity));
					g.fillRect(x + scale + scale / 2, y + spacing + scale / 2 - scale / 6, healthBarWidth,
							healthBarHeight);
				} else {
					g.setColor(new Color(0, 0, 0, opacity));
					g.fillRect(x + scale + scale / 2, y + spacing + scale / 2 - scale / 6, healthBarSize,
							healthBarHeight);
				}

			}
		}

		spacing = 0;

		if (Game.getGame().getHUDDisplay().isEditingHUD()) {
			if (!pickedUp) {
				g.setColor(Color.red);
				if (showPopUpMenu) {
					g.setColor(Color.yellow);
				}
				g.draw(teamRect);
			} else {
				g.setColor(Color.green);
				g.draw(teamRect);
			}

			popUpX = x;
			popUpY = y - 8;

			if (popUpX >= GameInfo.RES_WIDTH - 81) {
				popUpX = GameInfo.RES_WIDTH - 81;
			}
			if (popUpY <= 0) {
				popUpY = 0;
			}
			if (showPopUpMenu) {
				g.setColor(new Color(0, 0, 0, 180));
				g.fillRect(popUpX, popUpY, 80, 14);
				Res.bitFont.drawString(Res.roundForRendering(popUpX + 26), popUpY + 2, "Scale: " + (int) scale);
				Res.bitFont.drawString(popUpX + 26, popUpY + 8, "Opacity: " + opacity);

				scalePlusButton.render(g);
				scaleMinusButton.render(g);

				opacityPlusButton.render(g);
				opacityMinusButton.render(g);
			}
		}
	}

	public void setButtons() {
		scalePlusButton.setX(popUpX + 2);
		scalePlusButton.setY(popUpY + 2);

		scaleMinusButton.setX(popUpX + 14);
		scaleMinusButton.setY(popUpY + 2);

		opacityPlusButton.setX(popUpX + 2);
		opacityPlusButton.setY(popUpY + 8);

		opacityMinusButton.setX(popUpX + 14);
		opacityMinusButton.setY(popUpY + 8);

	}

	public void setTeamRect() {

		teamRect.setX(x);
		teamRect.setY(y + scale + scale / 2);
		teamRect.setWidth(scale * 4);
		teamRect.setHeight(scale * 4 + 12);

	}

}
