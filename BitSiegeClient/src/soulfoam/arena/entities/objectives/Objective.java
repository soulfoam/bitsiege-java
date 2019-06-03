package soulfoam.arena.entities.objectives;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.GameObject;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.world.MapLayer;
import soulfoam.arena.world.Tile;
import soulfoam.arenashared.main.objectiveinfo.ObjectiveInfo;

public abstract class Objective extends GameObject {

	protected float remoteX, remoteY;
	protected float health, baseHealth, baseHealthStart;

	protected float clientRemoveTime = 10 * 1000;
	protected float clientRemoveTimer = clientRemoveTime;

	protected boolean beingHeld;
	protected byte captureTeam;

	protected boolean needsRemoved;

	protected byte objectiveTeam;

	protected int objectiveGameID;
	
	protected Entity lastAttacker;
	protected int objectiveHolderID;

	protected Animation animation = new Animation();

	public abstract void update(int delta);

	public abstract Shape getBounds();

	public abstract void removeObjectiveClient();

	public void render(Graphics g, float x, float y) {
	};

	public void removeClientIfNoUpdate(int delta) {
		if (clientRemoveTimer > 0) {
			clientRemoveTimer -= delta;
		}
		if (clientRemoveTimer <= 0) {
			Game.getGame().getObjectives().remove(this);
		}
	}

	public void takeDamageClient(Entity attacker) {
		if (attacker.getTeam() != Res.TEAM_D && !beingHeld) {
			for (int i = 0; i < 25; i++) {
				Game.getGame().getParticleSystem().addParticle(x + Tile.TILE_SIZE, y + Tile.TILE_SIZE, Color.blue,
						1);
			}
		}
	}

	public void interpolateMovement(int delta) {
		if (objectiveTeam != ObjectiveInfo.SMALL_CAPTURE_POINT
				|| objectiveTeam != ObjectiveInfo.CAPTURE_POINT) {
			float differenceX = remoteX - x;

			if (differenceX > 48 || differenceX < -48) {
				x = remoteX;

			} else {
				x += differenceX * delta * 0.01;
			}

			float differenceY = remoteY - y;

			if (differenceY > 48 || differenceY < -48) {
				y = remoteY;

			} else {
				y += differenceY * delta * 0.01;
			}
		}
	}

	public boolean move(float xm, float ym) {

		for (float xa = x + xm; xa < x + xm + width; xa++) {
			for (float ya = y + ym; ya < y + ym + height; ya++) {
				if (xa / Tile.TILE_SIZE <= 0 || xa / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth()
						|| ya / Tile.TILE_SIZE <= 0
						|| ya / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()) {
					return false;
				} else if (Game.getGame().getWorld().getTile(xa, ya, MapLayer.INFORMATION).isBlocked()) {
					return false;
				}
			}
		}

		x += xm;
		y += ym;

		return true;
	}

	public float getRemoteX() {
		return remoteX;
	}

	public float getRemoteY() {
		return remoteY;
	}

	public float getHealth() {
		return health;
	}

	public float getBaseHealth() {
		return baseHealth;
	}

	public float getBaseHealthStart() {
		return baseHealthStart;
	}

	private float getClientRemoveTime() {
		return clientRemoveTime;
	}

	private float getClientRemoveTimer() {
		return clientRemoveTimer;
	}

	public boolean isBeingHeld() {
		return beingHeld;
	}

	public boolean isNeedsRemoved() {
		return needsRemoved;
	}

	public byte getObjectiveTeam() {
		return objectiveTeam;
	}

	public int getObjectiveGameID() {
		return objectiveGameID;
	}

	public Entity getLastAttacker() {
		return lastAttacker;
	}

	public int getObjectiveHolderID() {
		return objectiveHolderID;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setRemoteX(float remoteX) {
		this.remoteX = remoteX;
	}

	public void setRemoteY(float remoteY) {
		this.remoteY = remoteY;
	}

	public void setHealth(float health) {
		this.health = health;
	}

	public void setBaseHealth(float baseHealth) {
		this.baseHealth = baseHealth;
	}

	public void setBaseHealthStart(float baseHealthStart) {
		this.baseHealthStart = baseHealthStart;
	}

	public void setClientRemoveTime(float clientRemoveTime) {
		this.clientRemoveTime = clientRemoveTime;
	}

	public void setClientRemoveTimer(float clientRemoveTimer) {
		this.clientRemoveTimer = clientRemoveTimer;
	}

	public void setBeingHeld(boolean beingHeld) {
		this.beingHeld = beingHeld;
	}

	public void setNeedsRemoved(boolean needsRemoved) {
		this.needsRemoved = needsRemoved;
	}

	public void setObjectiveTeam(byte objectiveTeam) {
		this.objectiveTeam = objectiveTeam;
	}

	public void setObjectiveGameID(int objectiveGameID) {
		this.objectiveGameID = objectiveGameID;
	}

	public void setLastAttacker(Entity lastAttacker) {
		this.lastAttacker = lastAttacker;
	}

	public void setObjectiveHolderID(int objectiveHolderID) {
		this.objectiveHolderID = objectiveHolderID;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public byte getCaptureTeam() {
		return captureTeam;
	}

	public void setCaptureTeam(byte captureTeam) {
		this.captureTeam = captureTeam;
	}

}
