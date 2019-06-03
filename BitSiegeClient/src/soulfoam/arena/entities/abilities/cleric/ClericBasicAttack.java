package soulfoam.arena.entities.abilities.cleric;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericBasicAttackInfo;

public class ClericBasicAttack extends Ability {

	public ClericBasicAttack(int gameID, float x, float y, float dx, float dy, Entity player) {

		abilityID = AbilityInfo.CLERICBASICATTACK;

		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;

		this.player = player;

		coolDown = ClericBasicAttackInfo.COOLDOWN;
		damage = ClericBasicAttackInfo.DAMAGE;
		moveSpeed = ClericBasicAttackInfo.MOVE_SPEED;
		destroyTimer = ClericBasicAttackInfo.DESTROY_TIMER;

		width = ClericBasicAttackInfo.WIDTH;
		height = ClericBasicAttackInfo.HEIGHT;
		hitBoxWidth = ClericBasicAttackInfo.HITBOX_WIDTH;
		hitBoxHeight = ClericBasicAttackInfo.HITBOX_HEIGHT;

		soundEffect = Res.CLERIC_RESOURCE.SFX_CLERIC[0];

		animation = new Animation(Res.CLERIC_RESOURCE.AUTOATTACK_DAMAGE, animSpeed);
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
