package soulfoam.arena.entities.abilities.waterqueen;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.waterqueen.WaterQueenBasicAttackInfo;

public class WaterQueenBasicAttack extends Ability {

	public WaterQueenBasicAttack(int gameID, float x, float y, float dx, float dy, Entity player) {

		abilityID = AbilityInfo.WATERQUEENBASICATTACK;
		tag = AbilityInfo.PROJECTILE;

		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;

		this.player = player;

		coolDown = WaterQueenBasicAttackInfo.COOLDOWN;
		damage = WaterQueenBasicAttackInfo.DAMAGE;
		moveSpeed = WaterQueenBasicAttackInfo.MOVE_SPEED;
		destroyTimer = WaterQueenBasicAttackInfo.DESTROY_TIMER;

		width = WaterQueenBasicAttackInfo.WIDTH;
		height = WaterQueenBasicAttackInfo.HEIGHT;
		hitBoxWidth = WaterQueenBasicAttackInfo.HITBOX_WIDTH;
		hitBoxHeight = WaterQueenBasicAttackInfo.HITBOX_HEIGHT;

		soundEffect = Res.WATERQUEEN_RESOURCE.SFX_WATERQUEEN[0];

		if (player != null) {
			animation = new Animation(player.getSkin().getWaterQueenSkin().getAutoAttack(), animSpeed);
		}
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

}
