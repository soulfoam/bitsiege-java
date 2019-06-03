package soulfoam.arena.entities.abilities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.GameObject;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.world.MapLayer;
import soulfoam.arena.world.Tile;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public abstract class Ability extends GameObject {

	public static final int RENDER_GROUND = 0;
	public static final int RENDER_BACK = 1;
	public static final int RENDER_FRONT = 2;
	public static final int RENDER_SKY = 3;

	protected int gameID;
	protected int abilityID;

	protected Sound soundEffect;
	protected float soundEffectVolume = 1.0f;
	protected boolean loopSound;

	protected int renderLayer = RENDER_FRONT;
	protected int tag = AbilityInfo.NORMAL;

	protected int animSpeed = 100;

	protected float moveSpeed;
	protected float damage;
	protected float coolDown;
	protected float destroyTimer;
	protected float destroyTime;

	protected float stunDuration;
	protected float blindDuration;
	protected float slowDuration;
	protected float slowAmount;

	protected boolean playedSoundOnce;

	protected Entity player;

	protected Animation animation = new Animation();

	protected Image skillIcon;

	protected int ownerClassType;

	protected int playersToDamage = 1;
	protected AbilityCollision collision = new AbilityCollision();

	protected float remoteX, remoteY;
	protected float rotation;

	protected float dx, dy;

	protected float interpTime = 100;
	protected float interpTimer = 0;

	protected boolean changeDirectionOnCast = true;

	public boolean canCast(float x, float y) {
		return true;
	}

	public abstract void update(int delta);

	public void initForGame() {
	}

	public void calculateRotation() {
		rotation = (float) Math.atan2(dy, dx);
		rotation = (float) Math.toDegrees(rotation);
	}

	public void render(Graphics g) {
		animation.draw(getRenderX(), getRenderY(), width, height);
	}

	public void playSoundEffect() {
		if (!playedSoundOnce && soundEffect != null) {
			Game.getGame().getSoundSystem().playSound(soundEffect, soundEffectVolume);
			playedSoundOnce = true;
		}
	}

	public float getRenderX() {
		return Res.roundForRendering(x);
	}

	public float getRenderY() {
		return Res.roundForRendering(y);
	}

	public abstract Shape getBounds();

	public float getMoveSpeed(float moveSpeed, int delta) {
		return moveSpeed * delta;
	}

	public float getTotalDamage() {
		return EntityInfo.getOverallPower(damage, Game.getGame().getPlayer().getPowerBuffAmount());
	}

	public boolean move(int delta) {

		float xm = dx * moveSpeed * delta;
		float ym = dy * moveSpeed * delta;

		for (float xa = x + xm; xa < x + xm + hitBoxWidth; xa++) {
			for (float ya = y + ym; ya < y + ym + hitBoxHeight; ya++) {

				if (xa / Tile.TILE_SIZE <= 0 || xa / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth()
						|| ya / Tile.TILE_SIZE <= 0
						|| ya / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()) {
					return false;
				} else if (Game.getGame().getWorld().getTile(xa, ya, MapLayer.INFORMATION).isBlocksAbilities()) {
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
				if (collision) {
					if (xa / Tile.TILE_SIZE <= 0 || xa / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapWidth()
							|| ya / Tile.TILE_SIZE <= 0
							|| ya / Tile.TILE_SIZE >= Game.getGame().getWorld().getMap().getMapHeight()) {
						return false;
					} else if (Game.getGame().getWorld().getTile(xa, ya, MapLayer.INFORMATION).isBlocksAbilities()) {
						return false;
					}
				}
			}
		}

		x += xm;
		y += ym;

		return true;
	}

	public void handleInterpTimer(int delta) {
		interpTimer -= delta;

		if (interpTimer <= 0) {
			interpTimer = 0;
		}
	}

	public void interpMovement(int delta) {

		float differenceX = remoteX - x;
		if (differenceX > 48 || differenceX < -48) {
			x = remoteX;
		} else {
			x += differenceX * 0.025f * delta;
		}

		float differenceY = remoteY - y;

		if (differenceY > 48 || differenceY < -48) {
			y = remoteY;
		} else {
			y += differenceY * 0.025f * delta;
		}

	}

	public void removeThis() {
		Game.getGame().removeAbility(this);
	}

	public Tile getTile() {
		Tile t = new Tile(0, 0, -1, 1);
		t = Game.getGame().getWorld().getTile(x, y, MapLayer.INFORMATION);
		return t;
	}

	public boolean needsInterp() {
		if (interpTimer > 0) {
			return true;
		}

		return false;
	}

	public int getGameID() {
		return gameID;
	}

	public int getAbilityID() {
		return abilityID;
	}

	public Sound getSoundEffect() {
		return soundEffect;
	}

	public float getSoundEffectVolume() {
		return soundEffectVolume;
	}

	public boolean loopSound() {
		return loopSound;
	}

	public int getRenderLayer() {
		return renderLayer;
	}

	public int getTag() {
		return tag;
	}

	public int getAnimSpeed() {
		return animSpeed;
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

	public boolean playedSoundOnce() {
		return playedSoundOnce;
	}

	public Entity getPlayer() {
		return player;
	}

	public Animation getAnimation() {
		return animation;
	}

	public Image getSkillIcon() {
		return skillIcon;
	}

	public int getOwnerClassType() {
		return ownerClassType;
	}

	public int getPlayersToDamage() {
		return playersToDamage;
	}

	public AbilityCollision getCollision() {
		return collision;
	}

	public float getRemoteX() {
		return remoteX;
	}

	public float getRemoteY() {
		return remoteY;
	}

	public float getRotation() {
		return rotation;
	}

	public float getDX() {
		return dx;
	}

	public float getDY() {
		return dy;
	}

	public float getInterpTime() {
		return interpTime;
	}

	public float getInterpTimer() {
		return interpTimer;
	}

	public boolean changeDirectionOnCast() {
		return changeDirectionOnCast;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}

	public void setAbilityID(int abilityID) {
		this.abilityID = abilityID;
	}

	public void setSoundEffect(Sound soundEffect) {
		this.soundEffect = soundEffect;
	}

	public void setSoundEffectVolume(float soundEffectVolume) {
		this.soundEffectVolume = soundEffectVolume;
	}

	public void setLoopSound(boolean loopSound) {
		this.loopSound = loopSound;
	}

	public void setRenderLayer(int renderLayer) {
		this.renderLayer = renderLayer;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public void setAnimSpeed(int animSpeed) {
		this.animSpeed = animSpeed;
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

	public void setPlayedSoundOnce(boolean playedSoundOnce) {
		this.playedSoundOnce = playedSoundOnce;
	}

	public void setPlayer(Entity player) {
		this.player = player;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public void setSkillIcon(Image skillIcon) {
		this.skillIcon = skillIcon;
	}

	public void setOwnerClassType(int ownerClassType) {
		this.ownerClassType = ownerClassType;
	}

	public void setPlayersToDamage(int playersToDamage) {
		this.playersToDamage = playersToDamage;
	}

	public void setCollision(AbilityCollision collision) {
		this.collision = collision;
	}

	public void setRemoteX(float remoteX) {
		this.remoteX = remoteX;
	}

	public void setRemoteY(float remoteY) {
		this.remoteY = remoteY;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	public void setDX(float dx) {
		this.dx = dx;
	}

	public void setDY(float dy) {
		this.dy = dy;
	}

	public void setInterpTime(float interpTime) {
		this.interpTime = interpTime;
	}

	public void setInterpTimer(float interpTimer) {
		this.interpTimer = interpTimer;
	}

	public void setChangeDirectionOnCast(boolean changeDirectionOnCast) {
		this.changeDirectionOnCast = changeDirectionOnCast;
	}

}
