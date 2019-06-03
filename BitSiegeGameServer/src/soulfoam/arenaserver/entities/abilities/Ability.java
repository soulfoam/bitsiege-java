package soulfoam.arenaserver.entities.abilities;

import org.newdawn.slick.geom.Shape;

import soulfoam.arenaserver.entities.Entity;
import soulfoam.arenaserver.entities.GameObject;
import soulfoam.arenaserver.main.game.Game;
import soulfoam.arenaserver.world.MapLayer;
import soulfoam.arenaserver.world.Tile;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;

public abstract class Ability extends GameObject{

	protected int gameID;
	protected int abilityID;

	protected int tag = AbilityInfo.NORMAL;
	
	protected float dx;
	protected float dy;
	
	protected float moveSpeed;
	protected float damage;
	protected float coolDown;
	protected float destroyTimer;
	protected float destroyTime;
	
	protected float stunDuration;
	protected float blindDuration;
	protected float slowDuration;
	protected float slowAmount;
	
	protected boolean remove;
	
	protected Entity player;


	protected int playersToDamage = 1;
	protected AbilityCollision collision = new AbilityCollision();

	protected boolean changeDirectionOnCast = true;
	
	public boolean canCast(float x, float y){
		return true;
	}
	
	public abstract void update(int delta);	
	

	public abstract Shape getBounds();
	
	public boolean move(int delta) {
		
		float xm = dx * moveSpeed * delta;
		float ym = dy * moveSpeed * delta;
        
		for (float xa = x + xm; xa < x + xm + hitBoxWidth; xa++) {
			for (float ya = y + ym; ya < y + ym + hitBoxHeight; ya++) {

				if (xa / Tile.TILE_SIZE <= 0 || xa / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth() || ya / Tile.TILE_SIZE <= 0 || ya / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()){
					return false;
				}
				else if (Game.getGame().getWorld().getTile(xa, ya, MapLayer.INFORMATION).isBlocksAbilities()) {
					return false;
				}
			}
		}
		
        x += xm;
        y += ym;
        
        return true;
	}
	
	public boolean move(int delta, boolean collision) {
		
		float xm = dx * moveSpeed * delta;
		float ym = dy * moveSpeed * delta;
        
		for (float xa = x + xm; xa < x + xm + hitBoxWidth; xa++) {
			for (float ya = y + ym; ya < y + ym + hitBoxHeight; ya++) {
				if (collision){
					if (xa / Tile.TILE_SIZE <= 0 || xa / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth() || ya / Tile.TILE_SIZE <= 0 || ya / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()){
						return false;
					}
					else if (Game.getGame().getWorld().getTile(xa, ya, MapLayer.INFORMATION).isBlocksAbilities()) {
						return false;
					}
				}
			}
		}
		
        x += xm;
        y += ym;
        
        return true;
	}
	
	public void removeThis(boolean broadcastRemove){
		
		Game.getGame().removeAbility(this, broadcastRemove);

	}

	public Tile getTile(){
		Tile t = new Tile(0, 0, -1, 1);
		
		t = Game.getGame().getWorld().getTile(x, y, MapLayer.INFORMATION);
		
		return t;
	}

	public int getGameID() {
		return gameID;
	}

	public int getAbilityID() {
		return abilityID;
	}

	public int getTag() {
		return tag;
	}

	public float getDx() {
		return dx;
	}

	public float getDy() {
		return dy;
	}

	public float getMoveSpeed() {
		return moveSpeed;
	}

	public float getDamage() {
		return damage;
	}

	public float getCoolDown() {
		return coolDown;
	}

	public float getDestroyTimer() {
		return destroyTimer;
	}

	public float getDestroyTime() {
		return destroyTime;
	}

	public float getStunDuration() {
		return stunDuration;
	}

	public float getBlindDuration() {
		return blindDuration;
	}

	public float getSlowDuration() {
		return slowDuration;
	}

	public float getSlowAmount() {
		return slowAmount;
	}

	public boolean isRemove() {
		return remove;
	}

	public Entity getPlayer() {
		return player;
	}

	public int getPlayersToDamage() {
		return playersToDamage;
	}

	public AbilityCollision getCollision() {
		return collision;
	}

	public boolean isChangeDirectionOnCast() {
		return changeDirectionOnCast;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public void setAbilityID(int abilityID) {
		this.abilityID = abilityID;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public void setDx(float dx) {
		this.dx = dx;
	}

	public void setDy(float dy) {
		this.dy = dy;
	}

	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public void setDamage(float damage) {
		this.damage = damage;
	}

	public void setCoolDown(float coolDown) {
		this.coolDown = coolDown;
	}

	public void setDestroyTimer(float destroyTimer) {
		this.destroyTimer = destroyTimer;
	}

	public void setDestroyTime(float destroyTime) {
		this.destroyTime = destroyTime;
	}

	public void setStunDuration(float stunDuration) {
		this.stunDuration = stunDuration;
	}

	public void setBlindDuration(float blindDuration) {
		this.blindDuration = blindDuration;
	}

	public void setSlowDuration(float slowDuration) {
		this.slowDuration = slowDuration;
	}

	public void setSlowAmount(float slowAmount) {
		this.slowAmount = slowAmount;
	}

	public void setRemove(boolean remove) {
		this.remove = remove;
	}

	public void setPlayer(Entity player) {
		this.player = player;
	}

	public void setPlayersToDamage(int playersToDamage) {
		this.playersToDamage = playersToDamage;
	}

	public void setCollision(AbilityCollision collision) {
		this.collision = collision;
	}

	public void setChangeDirectionOnCast(boolean changeDirectionOnCast) {
		this.changeDirectionOnCast = changeDirectionOnCast;
	}
	
	
}
