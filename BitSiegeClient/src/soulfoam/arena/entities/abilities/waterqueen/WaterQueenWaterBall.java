package soulfoam.arena.entities.abilities.waterqueen;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.waterqueen.WaterQueenWaterBallInfo;

public class WaterQueenWaterBall extends Ability {

	public WaterQueenWaterBall(int gameID, float x, float y, float dx, float dy, Entity player) {

		abilityID = AbilityInfo.WATERQUEENWATERBALL;
		tag = AbilityInfo.PROJECTILE;

		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;

		this.player = player;

		coolDown = WaterQueenWaterBallInfo.COOLDOWN;
		damage = WaterQueenWaterBallInfo.DAMAGE;
		moveSpeed = WaterQueenWaterBallInfo.MOVE_SPEED;
		destroyTimer = WaterQueenWaterBallInfo.DESTROY_TIMER;
		slowDuration = WaterQueenWaterBallInfo.SLOW_DURATION;
		slowAmount = WaterQueenWaterBallInfo.SLOW_AMOUNT;

		width = WaterQueenWaterBallInfo.WIDTH;
		height = WaterQueenWaterBallInfo.WIDTH;
		hitBoxWidth = WaterQueenWaterBallInfo.HITBOX_WIDTH;
		hitBoxHeight = WaterQueenWaterBallInfo.HITBOX_HEIGHT;

		skillIcon = Res.WATERQUEEN_RESOURCE.WATERQUEEN_HOTBAR_ICONS[1];
		soundEffect = Res.WATERQUEEN_RESOURCE.SFX_WATERQUEEN[2];

		animation = new Animation(player.getSkin().getWaterQueenSkin().getWaterBall(), animSpeed);

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
