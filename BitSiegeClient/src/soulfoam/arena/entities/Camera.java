package soulfoam.arena.entities;

import java.util.ArrayList;

import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.world.Tile;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class Camera {

	private float x, y;
	private float offSetMaxX;
	private float offSetMaxY;
	private float offSetMinX;
	private float offSetMinY;
	private int spectateIndex = 0;
	
	private Entity currentPlayer;
	private boolean autoSpectateOnDeath = true;
	private boolean spectating;

	public Camera(float x, float y) {
		this.x = x;
		this.y = y;

	}

	public void update(int delta) {

		offSetMaxX = Tile.TILE_SIZE * Game.getGame().getWorld().getMap().getMapWidth() - GameInfo.RES_WIDTH;
		offSetMaxY = Tile.TILE_SIZE * Game.getGame().getWorld().getMap().getMapHeight() - GameInfo.RES_HEIGHT;
		offSetMinX = 0;
		offSetMinY = 0;

		if (currentPlayer != null) {
			if (!Game.getGame().getPlayer().isDead()) {
				if (currentPlayer.playerID != Game.getGame().getPlayer().playerID) {
					currentPlayer = Game.getGame().getPlayer();
				}
			}
			if (!currentPlayer.isDead()) {
				x = currentPlayer.x - GameInfo.RES_WIDTH / 2;
				y = currentPlayer.y - GameInfo.RES_HEIGHT / 2 + 8;
			} else {
				setSpectator(true);
			}
		} else {
			x = Game.getGame().getWorld().getMap().getMapWidth() * Tile.TILE_SIZE / 2 - GameInfo.RES_WIDTH / 2;
			y = Game.getGame().getWorld().getMap().getMapHeight() * Tile.TILE_SIZE / 2 - GameInfo.RES_HEIGHT / 2;
		}

		if (x < offSetMinX) {
			x = offSetMinX;
		}

		if (y < offSetMinY) {
			y = offSetMinY;
		}

		if (x > offSetMaxX) {
			x = offSetMaxX;
		}

		if (y > offSetMaxY) {
			y = offSetMaxY;
		}

	}

	public Rectangle getBounds() {
		return new Rectangle(x / Tile.TILE_SIZE, y / Tile.TILE_SIZE, GameInfo.RES_WIDTH, GameInfo.RES_HEIGHT);
	}

	public float getX() {
		return Res.roundForRendering(x);
	}

	public float getY() {
		return Res.roundForRendering(y);
	}

	public void setTarget(Entity p) {
		if (p != null) {
			if (currentPlayer != null) {
				if (currentPlayer.playerID == p.playerID) {
					return;
				}
			}
			currentPlayer = p;
		}

	}

	public void setSpectator(boolean add) {

		ArrayList<Entity> teamPlayers = new ArrayList<Entity>();

		for (Entity p : Game.getGame().getPlayers()) {
			if (p != null && p.getTeam() == Game.getGame().getPlayer().getTeam()
					&& p.playerID != Game.getGame().getPlayer().playerID && !p.isDead()) {
				teamPlayers.add(p);
			}
		}

		if (add) {
			spectateIndex++;
		} else {
			spectateIndex--;
		}

		if (spectateIndex > teamPlayers.size() - 1) {
			spectateIndex = 0;
		}
		if (spectateIndex < 0) {
			spectateIndex = teamPlayers.size() - 1;
		}

		if (!teamPlayers.isEmpty()) {
			if (teamPlayers.size() == 1) {
				setTarget(teamPlayers.get(0));
			}
			if (teamPlayers.size() > 1) {
				setTarget(teamPlayers.get(spectateIndex));
			}
		} else {
			setTarget(null);
		}

	}

	public void setSpectator(byte team, int id) {

		ArrayList<Entity> teamPlayers = new ArrayList<Entity>();

		for (Entity p : Game.getGame().getPlayers()) {
			if (p.getTeam() == team && !p.isDead()) {
				teamPlayers.add(p);
			}
		}
		
		if (teamPlayers.size() > 0 && Res.inBounds(id, teamPlayers.size())) {
			currentPlayer = teamPlayers.get(id);
		}

		if (teamPlayers.size() == 0) {
			currentPlayer = null;
		}

		setTarget(currentPlayer);
	}

	public void setSpectatorRandom() {
		ArrayList<Entity> teamPlayers = new ArrayList<Entity>();

		byte team = (byte) Res.randInt(0, 1);

		for (Entity p : Game.getGame().getPlayers()) {
			if (p.getTeam() == team && p.playerID != Game.getGame().getPlayer().playerID && !p.isDead()) {
				teamPlayers.add(p);
			}
		}

		if (teamPlayers.size() > 0) {
			setTarget(teamPlayers.get(Res.randInt(0, teamPlayers.size() - 1)));
		}

		if (teamPlayers.size() == 0) {
			if (team == Res.TEAM_D) {
				team = Res.TEAM_A;
			}
			if (team == Res.TEAM_A) {
				team = Res.TEAM_D;
			}

			for (Entity p : Game.getGame().getPlayers()) {
				if (p.getTeam() == team && p.playerID != Game.getGame().getPlayer().playerID && !p.isDead()) {
					teamPlayers.add(p);
				}
			}

			if (teamPlayers.size() > 0) {
				setTarget(teamPlayers.get(Res.randInt(0, teamPlayers.size() - 1)));
			}
		}
	}

	public float getOffSetMaxX() {
		return offSetMaxX;
	}

	public float getOffSetMaxY() {
		return offSetMaxY;
	}

	public float getOffSetMinX() {
		return offSetMinX;
	}

	public float getOffSetMinY() {
		return offSetMinY;
	}

	public int getSpectateIndex() {
		return spectateIndex;
	}

	public Entity getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean isAutoSpectateOnDeath() {
		return autoSpectateOnDeath;
	}

	public boolean isSpectating() {
		return spectating;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setCurrentPlayer(Entity currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public void setSpectating(boolean spectating) {
		this.spectating = spectating;
	}
}
