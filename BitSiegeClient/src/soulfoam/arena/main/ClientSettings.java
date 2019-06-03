package soulfoam.arena.main;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.entities.objectives.Objective;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.world.MapLayer;
import soulfoam.arena.world.MapObject;
import soulfoam.arena.world.Tile;

public class ClientSettings {

	private boolean showPlayerNames;
	private boolean showHealthBars = true;

	private boolean showBoundsTiles;
	private boolean showBoundsObjectives;
	private boolean showBoundsPlayers;
	private boolean showBoundsAbilities;
	private boolean showBoundsMapObjects;

	public ClientSettings() {
		showPlayerNames = SettingManager.CONFIG_SHOW_PLAYER_NAMES;
	}

	public void update(int delta) {

	}

	public void render(Graphics g) {

		g.setColor(Color.red);

		if (showBoundsTiles) {
			for (int y = 0; y < Game.getGame().getWorld().getMap().getLayers().get(0)[0].length; y++) {
				for (int x = 0; x < Game.getGame().getWorld().getMap().getLayers().get(0).length; x++) {
					Tile t = Game.getGame().getWorld().getMap().getLayers().get(0)[x][y];
					if (t != null) {
						if (t.isBlocked()) {
							g.setColor(Color.red);
							g.draw(t.getBounds());
						}
						if (t.isBlocksAbilities()) {
							Res.bitFont.drawString((int) t.getX(), (int) t.getY(), "B");
						}
						if (t.getTileType() != -1) {
							g.setColor(Color.black);
							Res.bitFont.drawString((int) t.getX(), (int) t.getY(), t.getTileType() + "",
									new Color(255, 100, 255));
						}
					}
				}
			}

			for (int y = 0; y < Game.getGame().getWorld().getMap().getLayers().get(MapLayer.YSORTED)[0].length; y++) {
				for (int x = 0; x < Game.getGame().getWorld().getMap().getLayers().get(MapLayer.YSORTED).length; x++) {
					Tile t = Game.getGame().getWorld().getMap().getLayers().get(MapLayer.YSORTED)[x][y];
					if (t != null) {
						if (t.getTileID() != -1) {
							g.setColor(Color.black);
							Res.bitFont.drawString(t.getX() + 4, t.getY(), t.getHeightLayer() + "");
						}
					}
				}
			}
		}

		if (showBoundsObjectives) {
			Objective[] tempListObjectives = new Objective[Game.getGame().getObjectives().size()];
			Game.getGame().getObjectives().toArray(tempListObjectives);

			for (Objective o : tempListObjectives) {
				if (o != null) {
					g.setColor(Color.blue);
					g.draw(o.getBounds());
				}
			}
		}
		showBoundsPlayers = false;
		if (showBoundsPlayers) {
			Entity[] playerTempList = new Entity[Game.getGame().getPlayers().size()];
			Game.getGame().getPlayers().toArray(playerTempList);

			for (Entity p : playerTempList) {
				if (p != null) {
					if (p != null) {
						if (p.getTeam() == Game.getGame().getPlayer().getTeam()) {
							g.setColor(Color.green);
						} else {
							g.setColor(Color.red);
						}
					}
					g.draw(p.getBounds());
					g.setColor(Color.blue);
					g.draw(p.getBoundsLowerBody());
					g.setColor(Color.pink);
					g.draw(p.getBoundsFeet());
					
					g.setColor(Color.orange);
					g.draw(p.getBoundsCenterBody());

				}
			}
		}

		if (showBoundsMapObjects) {
			MapObject[] mapObjectTempList = new MapObject[Game.getGame().getWorld().getMap().getMapObjects().size()];
			Game.getGame().getWorld().getMap().getMapObjects().toArray(mapObjectTempList);

			for (MapObject o : mapObjectTempList) {
				if (o != null) {
					g.setColor(Color.red);
					g.draw(o.getBounds());
				}
			}
		}

		if (showBoundsAbilities) {

			Ability[] tempAbilityList = new Ability[Game.getGame().getAbilities().size()];
			Game.getGame().getAbilities().toArray(tempAbilityList);

			for (Ability a : tempAbilityList) {
				if (a != null) {
					g.setColor(Color.green);
					g.draw(new Rectangle(a.getX(), a.getY(), a.getWidth(), a.getHeight()));

					if (a.getPlayer().getTeam() == Game.getGame().getPlayer().getTeam()) {
						g.setColor(Color.red);
					} else {
						g.setColor(Color.red);
					}

					// Res.bitFont.drawString(a.x, a.y, a.abilityID + " " +
					// a.gameID);
					g.draw(a.getBounds());
				}
			}
		}

	}

	public void handleCommand(String command) {

		if (command.trim().startsWith("/sbtiles")) {
			setShowBoundsTiles(!isShowBoundsTiles());
		}

		if (command.trim().startsWith("/sbobjectives")) {
			showBoundsObjectives = !showBoundsObjectives;
		}

		if (command.trim().startsWith("/sbplayers")) {
			showBoundsPlayers = !showBoundsPlayers;
		}

		if (command.trim().startsWith("/sbabilities")) {
			showBoundsAbilities = !showBoundsAbilities;
		}

		if (command.trim().startsWith("/showhud")) {
			Game.getGame().getHUDDisplay().setShowHUD(!Game.getGame().getHUDDisplay().showHUD());
		}

		if (command.trim().startsWith("/fps")) {
			SettingManager.CONFIG_SHOWFPS = !SettingManager.CONFIG_SHOWFPS;
		}

	}

	public boolean isShowPlayerNames() {
		return showPlayerNames;
	}

	public void setShowPlayerNames(boolean showPlayerNames) {
		this.showPlayerNames = showPlayerNames;
	}

	public boolean isShowHealthBars() {
		return showHealthBars;
	}

	public void setShowHealthBars(boolean showHealthBars) {
		this.showHealthBars = showHealthBars;
	}

	public boolean isShowBoundsMapObjects() {
		return showBoundsMapObjects;
	}

	public void setShowBoundsMapObjects(boolean showBoundsMapObjects) {
		this.showBoundsMapObjects = showBoundsMapObjects;
	}

	public boolean isShowBoundsTiles() {
		return showBoundsTiles;
	}

	public void setShowBoundsTiles(boolean showBoundsTiles) {
		this.showBoundsTiles = showBoundsTiles;
	}

}
