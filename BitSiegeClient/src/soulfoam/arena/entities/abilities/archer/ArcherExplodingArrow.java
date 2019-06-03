package soulfoam.arena.entities.abilities.archer;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.archer.ArcherExplosiveArrowInfo;

public class ArcherExplodingArrow extends Ability {

	public ArcherExplodingArrow(int gameID, float x, float y, float dx, float dy, Entity player) {

		abilityID = AbilityInfo.ARCHEREXPLODINGARROW;
		tag = AbilityInfo.PROJECTILE;

		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;

		this.player = player;

		coolDown = ArcherExplosiveArrowInfo.COOLDOWN;
		damage = ArcherExplosiveArrowInfo.DAMAGE;
		moveSpeed = ArcherExplosiveArrowInfo.MOVE_SPEED;
		destroyTimer = ArcherExplosiveArrowInfo.DESTROY_TIMER;

		width = ArcherExplosiveArrowInfo.WIDTH;
		height = ArcherExplosiveArrowInfo.HEIGHT;

		soundEffect = Res.ARCHER_RESOURCE.SFX_ARCHER[0];

		hitBoxWidth = 8;
		hitBoxHeight = 8;
		animation = new Animation(Res.ARCHER_RESOURCE.EXPLOSIVE_ARROW, animSpeed);

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

	public float sortByY() {
		return y + 9;
	}
}
