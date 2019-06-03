package soulfoam.arenaserver.entities.objectives;

import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.GameObject;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.world.MapLayer;
import soulfoam.arenaserver.world.Tile;

public abstract class Objective extends GameObject{

	protected float remoteX, remoteY;
	protected float health, baseHealth, baseHealthStart;

	protected float clientRemoveTime = 10 * 1000;
	protected float clientRemoveTimer = clientRemoveTime;
	
	protected boolean isBeingHeld;
	protected byte captureTeam;
	
	protected boolean needsRemoved;
	
	protected byte objectiveTeam;
	
	protected int objectiveGameID;
	
	protected Entity lastAttacker;
	protected int objectiveHolderID;

	public abstract void update(int delta);	
	public abstract Shape getBounds();
	
	public boolean move(float xm, float ym) {

		for (float xa = x + xm; xa < x + xm + width; xa++) {
			for (float ya = y + ym; ya < y + ym + height; ya++) {
				if (xa / Tile.TILE_SIZE <= 0 || xa / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth() || ya / Tile.TILE_SIZE <= 0 || ya / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()){
					return false;
				}
				else if (Game.getGame().getWorld().getTile(xa, ya, MapLayer.INFORMATION).isBlocked()) {
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
	public float getClientRemoveTime() {
		return clientRemoveTime;
	}
	public float getClientRemoveTimer() {
		return clientRemoveTimer;
	}
	public boolean isBeingHeld() {
		return isBeingHeld;
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
	public void setBeingHeld(boolean isBeingHeld) {
		this.isBeingHeld = isBeingHeld;
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
	public byte getCaptureTeam() {
		return captureTeam;
	}
	public void setCaptureTeam(byte captureTeam) {
		this.captureTeam = captureTeam;
	}

}
