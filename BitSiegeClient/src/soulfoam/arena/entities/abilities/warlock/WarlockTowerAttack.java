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
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockTowerAttackInfo;

public class WarlockTowerAttack extends Ability {

	public WarlockTowerAttack(int gameID, float x, float y, float dx, float dy, Entity player) {

		abilityID = AbilityInfo.WARLOCKTOWERATTACK;

		this.gameID = gameID;
		tag = AbilityInfo.PROJECTILE;

		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;

		this.player = player;

		damage = WarlockTowerAttackInfo.DAMAGE;
		moveSpeed = WarlockTowerAttackInfo.MOVE_SPEED;
		destroyTimer = WarlockTowerAttackInfo.DESTROY_TIMER;

		width = WarlockTowerAttackInfo.WIDTH;
		height = WarlockTowerAttackInfo.HEIGHT;
		hitBoxWidth = WarlockTowerAttackInfo.HITBOX_WIDTH;
		hitBoxHeight = WarlockTowerAttackInfo.HITBOX_HEIGHT;

		animation = new Animation(player.getSkin().getWarlockSkin().getTowerAttack(), animSpeed);

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
		if (animation != null) {
			animation.setRotation(rotation);
			animation.draw(getRenderX(), getRenderY(), width, height);
			animation.setRotation(rotation);
		}
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
