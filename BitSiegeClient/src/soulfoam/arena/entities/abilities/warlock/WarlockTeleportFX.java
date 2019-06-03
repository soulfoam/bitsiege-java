package soulfoam.arena.entities.abilities.warlock;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockTeleportFXInfo;

public class WarlockTeleportFX extends Ability {

	public WarlockTeleportFX(int gameID, float x, float y, Entity player) {

		abilityID = AbilityInfo.WARLOCKTELEPORTFX;

		this.gameID = gameID;
		this.x = x;
		this.y = y;

		this.player = player;

		destroyTimer = WarlockTeleportFXInfo.DESTROY_TIMER;

		width = WarlockTeleportFXInfo.WIDTH;
		height = WarlockTeleportFXInfo.HEIGHT;
		hitBoxWidth = WarlockTeleportFXInfo.HITBOX_WIDTH;
		hitBoxHeight = WarlockTeleportFXInfo.HITBOX_HEIGHT;

		soundEffect = Res.WARLOCK_RESOURCE.SFX_WARLOCK[3];

		animation = new Animation(Res.WARLOCK_RESOURCE.TELEPORT_START, animSpeed);
		animation.setLooping(false);

	}

	public void update(int delta) {

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}

	}

	public float getRenderX() {
		return Res.roundForRendering(x);
	}

	public float getRenderY() {
		return Res.roundForRendering(y);
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void playSoundEffect() {

	}

	public float sortByY() {
		return y + height - 16;
	}

}
