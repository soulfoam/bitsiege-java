package soulfoam.arena.entities.abilities.knight;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.knight.KnightDashInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class KnightDash extends Ability {

	public KnightDash(int gameID, Entity player) {

		abilityID = AbilityInfo.KNIGHTDASH;
		this.tag = AbilityInfo.DASHSTOPONHIT;
		
		this.gameID = gameID;

		this.player = player;

		coolDown = KnightDashInfo.COOLDOWN;
		damage = KnightDashInfo.DAMAGE;
		moveSpeed = KnightDashInfo.MOVE_SPEED;
		destroyTimer = KnightDashInfo.DESTROY_TIMER;
		stunDuration = KnightDashInfo.STUN_DURATION;

		width = KnightDashInfo.WIDTH;
		height = KnightDashInfo.HEIGHT;
		hitBoxWidth = KnightDashInfo.HITBOX_WIDTH;
		hitBoxHeight = KnightDashInfo.HITBOX_HEIGHT;

		skillIcon = Res.KNIGHT_RESOURCE.KNIGHT_HOTBAR_ICONS[2];
		soundEffect = Res.KNIGHT_RESOURCE.SFX_KNIGHT[3];

	}

	public void update(int delta) {

		x = player.getX();
		y = player.getY();
		player.setCurrentAction(EntityInfo.WALKING);

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}

	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

}
