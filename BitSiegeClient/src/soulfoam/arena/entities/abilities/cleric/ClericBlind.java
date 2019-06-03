package soulfoam.arena.entities.abilities.cleric;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericBlindInfo;

public class ClericBlind extends Ability {

	public ClericBlind(int gameID, float x, float y, float dx, float dy, Entity player) {

		abilityID = AbilityInfo.CLERICBLIND;

		this.gameID = gameID;
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;

		this.player = player;

		coolDown = ClericBlindInfo.COOLDOWN;
		damage = ClericBlindInfo.DAMAGE;
		moveSpeed = ClericBlindInfo.MOVE_SPEED;
		destroyTimer = ClericBlindInfo.DESTROY_TIMER;
		blindDuration = ClericBlindInfo.BLIND_DURATION;

		width = ClericBlindInfo.WIDTH;
		height = ClericBlindInfo.HEIGHT;
		hitBoxWidth = ClericBlindInfo.HITBOX_WIDTH;
		hitBoxHeight = ClericBlindInfo.HITBOX_HEIGHT;

		skillIcon = Res.CLERIC_RESOURCE.CLERIC_HOTBAR_ICONS[1];
		soundEffect = Res.CLERIC_RESOURCE.SFX_CLERIC[3];

		animation = new Animation(player.getSkin().getClericSkin().getBlind(), animSpeed);

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
