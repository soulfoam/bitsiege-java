package soulfoam.arena.entities.abilities.illusionist;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistBasicAttackInfo;

public class IllusionistBasicAttack extends Ability {

	public IllusionistBasicAttack(int gameID, float x, float y, float dx, float dy, Entity player) {

		abilityID = AbilityInfo.ILLUSIONISTBASICATTACK;
		tag = AbilityInfo.PROJECTILE;

		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;

		this.player = player;

		coolDown = IllusionistBasicAttackInfo.COOLDOWN;
		damage = IllusionistBasicAttackInfo.DAMAGE;
		moveSpeed = IllusionistBasicAttackInfo.MOVE_SPEED;
		destroyTimer = IllusionistBasicAttackInfo.DESTROY_TIMER;

		width = IllusionistBasicAttackInfo.WIDTH;
		height = IllusionistBasicAttackInfo.HEIGHT;
		hitBoxWidth = IllusionistBasicAttackInfo.HITBOX_WIDTH;
		hitBoxHeight = IllusionistBasicAttackInfo.HITBOX_HEIGHT;

		soundEffect = Res.ILLUSIONIST_RESOURCE.SFX_ILLUSIONIST[0];

		animation = new Animation(player.getSkin().getIllusionistSkin().getAutoAttack(), animSpeed);

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
