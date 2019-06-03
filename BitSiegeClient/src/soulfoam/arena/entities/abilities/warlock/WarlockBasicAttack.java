package soulfoam.arena.entities.abilities.warlock;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockBasicAttackInfo;

public class WarlockBasicAttack extends Ability {

	public WarlockBasicAttack(int gameID, float x, float y, float dx, float dy, Entity player) {

		abilityID = AbilityInfo.WARLOCKBASICATTACK;
		tag = AbilityInfo.PROJECTILE;

		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;

		this.player = player;

		coolDown = WarlockBasicAttackInfo.COOLDOWN;
		damage = WarlockBasicAttackInfo.DAMAGE;
		moveSpeed = WarlockBasicAttackInfo.MOVE_SPEED;
		destroyTimer = WarlockBasicAttackInfo.DESTROY_TIMER;

		width = WarlockBasicAttackInfo.WIDTH;
		height = WarlockBasicAttackInfo.HEIGHT;
		hitBoxWidth = WarlockBasicAttackInfo.HITBOX_WIDTH;
		hitBoxHeight = WarlockBasicAttackInfo.HITBOX_HEIGHT;

		soundEffect = Res.WARLOCK_RESOURCE.SFX_WARLOCK[0];

		animation = new Animation(player.getSkin().getWarlockSkin().getAutoAttack(), animSpeed);

	}

	public void update(int delta) {

		handleMovement(delta);

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}

	}

	public void handleMovement(int delta) {
		if (!move(delta)) {
			removeThis();
		}
	}

	public void render(Graphics g) {
		animation.setRotation(rotation);
		animation.draw(getRenderX(), getRenderY(), width, height);
		animation.setRotation(rotation);
	}

	public float getRenderX() {
		return Res.roundForRendering(x - 4);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 4);
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void playSoundEffect() {
		if (!playedSoundOnce) {
			Game.getGame().getSoundSystem().playSound(soundEffect, soundEffectVolume);
			playedSoundOnce = true;
		}
	}

	public float sortByY() {
		return y + 9;
	}

}
