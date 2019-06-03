package soulfoam.arena.entities.abilities.knight;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.knight.KnightShieldThrowInfo;

public class KnightShieldThrow extends Ability {

	public KnightShieldThrow(int gameID, float x, float y, float dx, float dy, Entity player) {

		abilityID = AbilityInfo.KNIGHTSHIELDTHROW;
		tag = AbilityInfo.PROJECTILE;

		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;

		this.player = player;

		coolDown = KnightShieldThrowInfo.COOLDOWN;
		damage = KnightShieldThrowInfo.DAMAGE;
		moveSpeed = KnightShieldThrowInfo.MOVE_SPEED;
		destroyTimer = KnightShieldThrowInfo.DESTROY_TIMER;
		stunDuration = KnightShieldThrowInfo.STUN_DURATION;

		width = KnightShieldThrowInfo.WIDTH;
		height = KnightShieldThrowInfo.HEIGHT;
		hitBoxWidth = KnightShieldThrowInfo.HITBOX_WIDTH;
		hitBoxHeight = KnightShieldThrowInfo.HITBOX_HEIGHT;

		skillIcon = Res.KNIGHT_RESOURCE.KNIGHT_HOTBAR_ICONS[0];
		soundEffect = Res.KNIGHT_RESOURCE.SFX_KNIGHT[1];

		animation = new Animation(player.getSkin().getKnightSkin().getShieldThrow(), animSpeed);

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

	public float getRenderX() {
		return Res.roundForRendering(x - 4);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 4);
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

}
