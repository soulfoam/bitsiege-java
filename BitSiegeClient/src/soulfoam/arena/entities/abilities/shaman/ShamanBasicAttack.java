package soulfoam.arena.entities.abilities.shaman;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.shaman.ShamanBasicAttackInfo;

public class ShamanBasicAttack extends Ability {

	public ShamanBasicAttack(int gameID, float x, float y, float dx, float dy, Entity player) {

		abilityID = AbilityInfo.SHAMANBASICATTACK;
		tag = AbilityInfo.PROJECTILE;

		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;

		this.player = player;

		coolDown = ShamanBasicAttackInfo.COOLDOWN;
		damage = ShamanBasicAttackInfo.DAMAGE;
		moveSpeed = ShamanBasicAttackInfo.MOVE_SPEED;
		destroyTimer = ShamanBasicAttackInfo.DESTROY_TIMER;

		width = ShamanBasicAttackInfo.WIDTH;
		height = ShamanBasicAttackInfo.HEIGHT;
		hitBoxWidth = ShamanBasicAttackInfo.HITBOX_WIDTH;
		hitBoxHeight = ShamanBasicAttackInfo.HITBOX_HEIGHT;

		soundEffect = Res.SHAMAN_RESOURCE.SFX_SHAMAN[0];

		animation = new Animation(player.getSkin().getShamanSkin().getAutoAttack(), animSpeed);

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
