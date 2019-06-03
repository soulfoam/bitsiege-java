package soulfoam.arena.entities.abilities.cleric;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericBasicAttackHealInfo;

public class ClericBasicAttackHeal extends Ability {

	public ClericBasicAttackHeal(int gameID, float x, float y, float dx, float dy, Entity player) {

		abilityID = AbilityInfo.CLERICBASICATTACKHEAL;

		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;

		this.player = player;

		coolDown = ClericBasicAttackHealInfo.COOLDOWN;
		damage = ClericBasicAttackHealInfo.DAMAGE;
		moveSpeed = ClericBasicAttackHealInfo.MOVE_SPEED;
		destroyTimer = ClericBasicAttackHealInfo.DESTROY_TIMER;

		width = ClericBasicAttackHealInfo.WIDTH;
		height = ClericBasicAttackHealInfo.HEIGHT;
		hitBoxWidth = ClericBasicAttackHealInfo.HITBOX_WIDTH;
		hitBoxHeight = ClericBasicAttackHealInfo.HITBOX_HEIGHT;

		soundEffect = Res.CLERIC_RESOURCE.SFX_CLERIC[1];

		animation = new Animation(Res.CLERIC_RESOURCE.AUTOATTACK_HEAL, animSpeed);
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
