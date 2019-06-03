package soulfoam.arena.entities.abilities.archer;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.archer.ArcherExplosionInfo;

public class ArcherExplosion extends Ability {

	public ArcherExplosion(int gameID, float x, float y, Entity player) {

		abilityID = AbilityInfo.ARCHEREXPLOSION;
		tag = AbilityInfo.AOE;
		renderLayer = Ability.RENDER_SKY;

		this.gameID = gameID;
		this.x = x;
		this.y = y;

		this.player = player;

		damage = ArcherExplosionInfo.DAMAGE;
		destroyTimer = ArcherExplosionInfo.DESTROY_TIMER;

		width = ArcherExplosionInfo.WIDTH;
		height = ArcherExplosionInfo.HEIGHT;
		hitBoxWidth = ArcherExplosionInfo.HITBOX_WIDTH;
		hitBoxHeight = ArcherExplosionInfo.HITBOX_HEIGHT;

		soundEffect = Res.ARCHER_RESOURCE.SFX_ARCHER[1];

		animation = new Animation(Res.ARCHER_RESOURCE.EXPLOSION, 50);

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
		return Res.roundForRendering(x - 40);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 40);
	}

	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}
}
