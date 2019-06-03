package soulfoam.arena.entities.abilities.warlock;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockStormBitInfo;

public class WarlockStormBit extends Ability {

	public WarlockStormBit(int gameID, float x, float y, float dx, float dy, Entity player) {

		abilityID = AbilityInfo.WARLOCKSTORMBIT;
		renderLayer = Ability.RENDER_SKY;

		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;

		this.player = player;

		damage = WarlockStormBitInfo.DAMAGE;
		moveSpeed = WarlockStormBitInfo.MOVE_SPEED;
		destroyTimer = WarlockStormBitInfo.DESTROY_TIMER;

		width = WarlockStormBitInfo.WIDTH;
		height = WarlockStormBitInfo.HEIGHT;
		hitBoxWidth = WarlockStormBitInfo.HITBOX_WIDTH;
		hitBoxHeight = WarlockStormBitInfo.HITBOX_HEIGHT;

		animation = new Animation(player.getSkin().getWarlockSkin().getStormBit(), animSpeed);

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
		move(delta, false);
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
		// TODO Auto-generated method stub

	}
}