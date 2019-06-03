package soulfoam.arena.main.gfx;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.entities.challengers.Illusionist;
import soulfoam.arena.entities.objectives.CapturePoint;
import soulfoam.arena.entities.objectives.Objective;
import soulfoam.arena.entities.objectives.SmallCapturePoint;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.world.Tile;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.gameinfo.GameInfo;
import soulfoam.arenashared.main.objectiveinfo.ObjectiveInfo;

public class MinimapHUD extends BaseHUDUI {

	public float miniMapScale;
	public float playerScale;
	public Image map;

	public Rectangle miniMapRect;

	public HUDButton miniMapScaleSizePlusButton;
	public HUDButton miniMapScaleSizeMinusButton;
	public HUDButton playerScaleSizePlusButton;
	public HUDButton playerScaleSizeMinusButton;
	public HUDButton textScalePlusButton;
	public HUDButton textScaleMinusButton;
	public HUDButton opacityPlusButton;
	public HUDButton opacityMinusButton;

	public int glowTimer = 0;
	private boolean addGlowTime = true;
	public int glowSpeed = 7;

	public MinimapHUD(float x, float y, float miniMapScale, float playerScale, float textScale, int opacity) {
		this.x = x;
		this.y = y;

		this.miniMapScale = miniMapScale;
		this.playerScale = playerScale;
		this.opacity = opacity;
		this.textScale = textScale;

		miniMapRect = new Rectangle(x, y, miniMapScale, miniMapScale);

		miniMapScaleSizePlusButton = new HUDButton("+", x, y, 10, 5);
		miniMapScaleSizeMinusButton = new HUDButton("-", x, y, 10, 5);
		playerScaleSizePlusButton = new HUDButton("+", x, y, 10, 5);
		playerScaleSizeMinusButton = new HUDButton("-", x, y, 10, 5);
		textScalePlusButton = new HUDButton("+", x, y, 10, 5);
		textScaleMinusButton = new HUDButton("-", x, y, 10, 5);
		opacityPlusButton = new HUDButton("+", x, y, 10, 5);
		opacityMinusButton = new HUDButton("-", x, y, 10, 5);

	}

	public void update(GameContainer gc, int delta) {

		runTimer(delta);

		map = Game.getGame().getWorld().getMap().getMiniMap();

		if (addGlowTime) {
			glowTimer += glowSpeed;
			if (glowTimer >= opacity) {
				addGlowTime = false;
			}
		} else {
			glowTimer -= glowSpeed;
			if (glowTimer <= 0) {
				addGlowTime = true;
			}
		}

		if (Game.getGame().getHUDDisplay().isEditingHUD()) {
			if (pickedUp) {
				x = gc.getInput().getMouseX() - miniMapScale / 2;
				y = gc.getInput().getMouseY() - miniMapScale / 2;

				if (x <= 0) {
					x = 0;
				}
				if (y <= 0) {
					y = 0;
				}
				if (x + miniMapScale >= GameInfo.RES_WIDTH) {
					x = GameInfo.RES_WIDTH - miniMapScale;
				}
				if (y + miniMapScale >= GameInfo.RES_HEIGHT) {
					y = GameInfo.RES_HEIGHT - miniMapScale;
				}

				if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					setDown();
					setMiniMap();
				}
			} else {
				if (miniMapRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
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
				miniMapScaleSizePlusButton.update(gc);
				miniMapScaleSizeMinusButton.update(gc);
				playerScaleSizePlusButton.update(gc);
				playerScaleSizeMinusButton.update(gc);
				textScalePlusButton.update(gc);
				textScaleMinusButton.update(gc);
				opacityPlusButton.update(gc);
				opacityMinusButton.update(gc);

				if (miniMapScaleSizePlusButton.isClicked()) {
					miniMapScale += 5;
					setMiniMap();
				}
				if (miniMapScaleSizeMinusButton.isClicked()) {
					miniMapScale -= 5;
					if (miniMapScale <= 0) {
						miniMapScale = 1;
					}
					setMiniMap();
				}

				if (playerScaleSizePlusButton.isClicked()) {
					playerScale += 0.5f;
					setMiniMap();
				}
				if (playerScaleSizeMinusButton.isClicked()) {
					playerScale -= 0.5f;
					if (playerScale <= 0) {
						playerScale = 0;
					}
					setMiniMap();
				}

				if (textScalePlusButton.isClicked()) {
					textScale += 0.25f;
					setMiniMap();
				}
				if (textScaleMinusButton.isClicked()) {
					textScale -= 0.25f;
					if (textScale <= 0) {
						textScale = 0;
					}
					setMiniMap();
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

			}
			setButtons();

		} else {
			pickedUp = false;
		}

	}

	public void setMiniMap() {

		miniMapRect.setX(x);
		miniMapRect.setY(y);
		miniMapRect.setWidth(miniMapScale);
		miniMapRect.setHeight(miniMapScale);

	}

	public void setButtons() {
		miniMapScaleSizePlusButton.setX(popUpX + 2);
		miniMapScaleSizePlusButton.setY(popUpY + 2);

		miniMapScaleSizeMinusButton.setX(popUpX + 14);
		miniMapScaleSizeMinusButton.setY(popUpY + 2);

		playerScaleSizePlusButton.setX(popUpX + 2);
		playerScaleSizePlusButton.setY(popUpY + 8);

		playerScaleSizeMinusButton.setX(popUpX + 14);
		playerScaleSizeMinusButton.setY(popUpY + 8);

		textScalePlusButton.setX(popUpX + 2);
		textScalePlusButton.setY(popUpY + 14);

		textScaleMinusButton.setX(popUpX + 14);
		textScaleMinusButton.setY(popUpY + 14);

		opacityPlusButton.setX(popUpX + 2);
		opacityPlusButton.setY(popUpY + 20);

		opacityMinusButton.setX(popUpX + 14);
		opacityMinusButton.setY(popUpY + 20);

	}

	public void render(Graphics g) {

		if (map != null) {
			map.draw(x, y, miniMapScale, miniMapScale, new Color(255, 255, 255, opacity));
		}

		for (CapturePoint capturePoint : Game.getGame().getUtil().getCapturePoints()) {

			float ratioX = capturePoint.getX()
					/ (Game.getGame().getWorld().getMap().getMapWidth() * Tile.TILE_SIZE / miniMapScale) + x - playerScale;
			float ratioY = capturePoint.getY()
					/ (Game.getGame().getWorld().getMap().getMapHeight() * Tile.TILE_SIZE / miniMapScale) + y
					+ playerScale / 2;
			float fX = ratioX - playerScale / 2 - playerScale / 2 - 2.5f;
			float fY = ratioY - playerScale / 2 - playerScale;

			if (capturePoint.getObjectiveTeam() == ObjectiveInfo.CAPTURE_POINT) {
				if (capturePoint.isBeingHeld()) {
					Res.OBJECTIVE_RESOURCE.CAPTURE_POINT[0].draw(fX, fY, playerScale * 5, playerScale * 2.5f,
							new Color(219, 40, 49, glowTimer));
				} else {
					Res.OBJECTIVE_RESOURCE.CAPTURE_POINT[0].draw(fX, fY, playerScale * 5, playerScale * 2.5f,
							new Color(255, 255, 255, opacity));
				}
			} 
		}
		
		for (SmallCapturePoint capturePoint : Game.getGame().getUtil().getSmallCapturePoints()) {

			float ratioX = capturePoint.getX()
					/ (Game.getGame().getWorld().getMap().getMapWidth() * Tile.TILE_SIZE / miniMapScale) + x - playerScale;
			float ratioY = capturePoint.getY()
					/ (Game.getGame().getWorld().getMap().getMapHeight() * Tile.TILE_SIZE / miniMapScale) + y
					+ playerScale / 2;
			float fX = ratioX - playerScale / 2 - playerScale / 2;
			float fY = ratioY - playerScale / 2 - playerScale;

			if (capturePoint.getObjectiveTeam() == ObjectiveInfo.SMALL_CAPTURE_POINT) {
				if (capturePoint.isBeingHeld()) {
					Res.OBJECTIVE_RESOURCE.SMALL_CAPTURE_POINT[0].draw(fX, fY, playerScale * 4, playerScale * 2,
							new Color(219, 40, 49, glowTimer));
				} else {
					Res.OBJECTIVE_RESOURCE.SMALL_CAPTURE_POINT[0].draw(fX, fY, playerScale * 4, playerScale * 2,
							new Color(255, 255, 255, opacity));
				}
			} 
		}

		Objective[] tempListObjectives = new Objective[Game.getGame().getObjectives().size()];
		Game.getGame().getObjectives().toArray(tempListObjectives);

		for (Objective o : tempListObjectives) {
			if (o != null) {
				if (o.getObjectiveTeam() == ObjectiveInfo.HEALTHPICKUP_OBJECTIVE) {
					if (!o.isBeingHeld()) {
						float ratioX = o.getX()
								/ (Game.getGame().getWorld().getMap().getMapWidth() * Tile.TILE_SIZE / miniMapScale) + x;
						float ratioY = o.getY()
								/ (Game.getGame().getWorld().getMap().getMapHeight() * Tile.TILE_SIZE / miniMapScale) + y;
						float fX = ratioX - playerScale / 2;
						float fY = ratioY - playerScale / 2;

						o.getAnimation().draw(fX, fY, playerScale, playerScale, new Color(255, 255, 255, opacity));
					}
				}
			}
		}

		Entity[] tempList = new Entity[Game.getGame().getPlayers().size()];
		Game.getGame().getPlayers().toArray(tempList);

		for (Entity p : tempList) {
			if (p != null) {
				Color nameColor = new Color(255, 255, 255, opacity);

				if (p.getUsername().equals(Game.getGame().getPlayer().getUsername())) {
					nameColor = new Color(255, 255, 0, opacity);
				}

				if (p.getTeam() == Game.getGame().getPlayer().getTeam()) {
					if (!p.getUsername().equals(Game.getGame().getPlayer().getUsername())) {
						nameColor = new Color(255, 255, 255, opacity);
					}

					if (!p.isDead()) {

						float ratioX = p.getX()
								/ (Game.getGame().getWorld().getMap().getMapWidth() * Tile.TILE_SIZE / miniMapScale) + x;
						float ratioY = p.getY()
								/ (Game.getGame().getWorld().getMap().getMapHeight() * Tile.TILE_SIZE / miniMapScale) + y;
						float fX = ratioX - playerScale / 2;
						float fY = ratioY - playerScale / 2;

						p.getAnimation().getPortrait().draw(fX, fY, playerScale, playerScale,
								new Color(255, 255, 255, opacity));
						
						Res.UI_RESOURCE.PORTRAIT_BORDER.draw(fX, fY, playerScale, playerScale,
								new Color(100, 255, 100, opacity));

						float centerOfText = Res.bitFont.getWidth(p.getUsername(), textScale) / 2;

						Res.bitFont.drawString(fX - centerOfText + playerScale / 2, fY - 5, p.getUsername(), nameColor,
								textScale);

					}
				}
			}
		}

		for (Entity p : Game.getGame().getUtil().enemiesSeenByTeam()) {
			if (p != null) {
				float ratioX = p.getX() / (Game.getGame().getWorld().getMap().getMapWidth() * Tile.TILE_SIZE / miniMapScale)
						+ x;
				float ratioY = p.getY() / (Game.getGame().getWorld().getMap().getMapHeight() * Tile.TILE_SIZE / miniMapScale)
						+ y;

				float fX = ratioX - playerScale / 2;
				float fY = ratioY - playerScale / 2;

				p.getAnimation().getPortrait().draw(fX, fY, playerScale, playerScale,
						new Color(255, 100, 100, opacity));
				Res.UI_RESOURCE.PORTRAIT_BORDER.draw(fX, fY, playerScale, playerScale,
						new Color(255, 100, 100, opacity));
			}
		}

		Ability[] tempAbilityList = new Ability[Game.getGame().getAbilities().size()];
		Game.getGame().getAbilities().toArray(tempAbilityList);

		for (Ability a : tempAbilityList) {

			if (a.getAbilityID() == AbilityInfo.ILLUSIONISTCLONE) {
				float ratioX = a.getX() / (Game.getGame().getWorld().getMap().getMapWidth() * Tile.TILE_SIZE / miniMapScale)
						+ x;
				float ratioY = a.getY() / (Game.getGame().getWorld().getMap().getMapHeight() * Tile.TILE_SIZE / miniMapScale)
						+ y;
				float fX = ratioX - playerScale / 2;
				float fY = ratioY - playerScale / 2;

				if (a.getPlayer().getTeam() != Game.getGame().getPlayer().getTeam()) {
					if (!Game.getGame().getPlayer().isDead()
							&& Game.getGame().getPlayer().getView().intersects(a.getBounds())
							|| Game.getGame().getPlayer().getView().contains(a.getBounds())) {
						Color c = new Color(255, 100, 100, opacity);
						a.getPlayer().getAnimation().getPortrait().draw(fX, fY, playerScale, playerScale, c);
					}
				}
				if (a.getPlayer().getPlayerID() == Game.getGame().getPlayer().getPlayerID()) {
					Color c = new Color(255, 255, 0, opacity);
					if (Game.getGame().getPlayer().getChallengerType() == EntityInfo.ILLUSIONISTCHALLENGER) {
						Illusionist il = (Illusionist) Game.getGame().getPlayer();
						if (!il.passive.getCloneList().isEmpty() && a.getGameID() == il.passive.getCloneList()
								.get(il.passive.getCloneList().size() - 1).getGameID()) {
							c = new Color(0, 255, 0, opacity);
						}
					}
					a.getPlayer().getAnimation().getPortrait().draw(fX, fY, playerScale, playerScale, c);
				}

			}

		}

		if (Game.getGame().getHUDDisplay().isEditingHUD()) {
			if (!pickedUp) {
				g.setColor(Color.red);
				if (showPopUpMenu) {
					g.setColor(Color.yellow);
				}
				g.drawRect(x, y, miniMapScale, miniMapScale);
			} else {
				g.setColor(Color.green);
				g.drawRect(x, y, miniMapScale, miniMapScale);
			}

			if (showPopUpMenu) {
				popUpX = x;
				popUpY = y - 35;

				if (popUpX >= GameInfo.RES_WIDTH - 124) {
					popUpX = GameInfo.RES_WIDTH - 124;
				}
				if (popUpY <= 0) {
					popUpY = 0;
				}

				g.setColor(new Color(0, 0, 0, 180));
				g.fillRect(popUpX, popUpY, 124, 26);
				Res.bitFont.drawString(popUpX + 26, popUpY + 2, "Mini Map Scale: " + (int) miniMapScale);
				Res.bitFont.drawString(popUpX + 26, popUpY + 8, "Objects Scale: " + playerScale);
				Res.bitFont.drawString(popUpX + 26, popUpY + 14, "Player Name Scale: " + textScale);
				Res.bitFont.drawString(popUpX + 26, popUpY + 20, "Opacity: " + opacity);

				miniMapScaleSizePlusButton.render(g);
				miniMapScaleSizeMinusButton.render(g);
				playerScaleSizePlusButton.render(g);
				playerScaleSizeMinusButton.render(g);
				textScalePlusButton.render(g);
				textScaleMinusButton.render(g);
				opacityPlusButton.render(g);
				opacityMinusButton.render(g);
			}

		}

	}

}
